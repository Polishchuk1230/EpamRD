package com.epam.rd.taglib;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.context.util.BeanNames;
import com.epam.rd.context.util.CaptchaStorageMethod;
import com.epam.rd.service.ICaptchaService;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;

public class CaptchaTag extends TagSupport {

    @Override
    public int doStartTag() {
        ICaptchaService captchaService = (ICaptchaService) ApplicationContext.getInstance().getAttribute(BeanNames.CAPTCHA_SERVICE);
        String key = captchaService.generateKey();
        captchaService.putKey(key, pageContext);

        String httpResult = String.format("<img src=\"%s?key=%s\">", pageContext.getServletContext().getContextPath() + "/cptch", key);

        if (captchaService.getStorageMethod().equals(CaptchaStorageMethod.HIDDEN_TAG)) {
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
