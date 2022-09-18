package com.epam.rd.taglib;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.servlet.Captcha;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;

public class CaptchaTag extends TagSupport {

    @Override
    public int doStartTag() {
        String key = Captcha.generateKey();

        String httpResult = String.format("<img src=\"%s?key=%s\">", pageContext.getServletContext().getContextPath() + "/cptch", key);

        // put a key with one of three ways
        String captchaStorageMethod = (String) ApplicationContext.getInstance().getAttribute("captchaStorageMethod");
        // session
        if (captchaStorageMethod.equals("session")) {
            pageContext.getSession().setAttribute("captcha", key);
        }
        // cookie
        else if (captchaStorageMethod.equals("cookie")) {
            ((HttpServletResponse) pageContext.getResponse()).addCookie(new Cookie("captcha", key));
        }
        // html tag
        else if (captchaStorageMethod.equals("hiddenTag")) {
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
