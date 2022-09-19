package com.epam.rd.taglib;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.context.util.BeanName;
import com.epam.rd.context.util.CaptchaStoreMethod;
import com.epam.rd.servlet.CaptchaServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;

public class CaptchaTag extends TagSupport {

    @Override
    public int doStartTag() {
        String key = CaptchaServlet.generateKey();

        String httpResult = String.format("<img src=\"%s?key=%s\">", pageContext.getServletContext().getContextPath() + "/cptch", key);

        // put a key with one of three ways
        String captchaStorageMethod = (String) ApplicationContext.getInstance().getAttribute(BeanName.CAPTCHA_STORAGE_METHOD);
        // session
        if (captchaStorageMethod.equals(CaptchaStoreMethod.SESSION.toString())) {
            pageContext.getSession().setAttribute("captcha", key);
        }
        // cookie
        else if (captchaStorageMethod.equals(CaptchaStoreMethod.COOKIE.toString())) {
            ((HttpServletResponse) pageContext.getResponse()).addCookie(new Cookie("captcha", key));
        }
        // html tag
        else if (captchaStorageMethod.equals(CaptchaStoreMethod.HIDDEN_TAG.toString())) {
            httpResult += "\n<input type=\"hidden\" name=\"captchaKey\" value=\"" + key + "\">";
        }

        try {
            pageContext.getOut().print(httpResult);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return SKIP_BODY;
    }
}
