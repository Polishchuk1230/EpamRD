package com.epam.rd.servlet;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.context.util.BeanName;
import com.epam.rd.service.ICaptchaService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.imageio.ImageIO;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;
import java.util.Random;

@WebServlet("/cptch")
public class CaptchaServlet extends HttpServlet {
    private static Random random = new Random();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processRequest(req, resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ICaptchaService captchaService = (ICaptchaService) ApplicationContext.getInstance().getAttribute(BeanName.CAPTCHA_SERVICE);
        response.setContentType("image/jsp");

        BufferedImage biImage = generateImage(request);

        OutputStream osImage = response.getOutputStream();
        ImageIO.write(biImage, "jpeg", osImage);
    }

    private static BufferedImage generateImage(HttpServletRequest request) {
        ICaptchaService captchaService = (ICaptchaService) ApplicationContext.getInstance().getAttribute(BeanName.CAPTCHA_SERVICE);

        int iHeight = 40;
        int iWidth = 150;

        Font fntStyle = new Font("Arial", Font.BOLD, 30);

        String sImageCode = Optional.ofNullable(captchaService.findByKey(request.getParameter("key")))
                .orElseThrow(() -> new RuntimeException("Wrong captcha key"));

        BufferedImage biImage = new BufferedImage(iWidth, iHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2dImage = (Graphics2D) biImage.getGraphics();

        g2dImage.setFont(fntStyle);
        for (int i = 0; i < sImageCode.length(); i++) {
            g2dImage.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            if (i % 2 == 0) {
                g2dImage.drawString(sImageCode.substring(i, i + 1), 25 * i, 24);
            } else {
                g2dImage.drawString(sImageCode.substring(i, i + 1), 25 * i, 35);
            }
        }

        g2dImage.dispose();
        return biImage;
    }
}
