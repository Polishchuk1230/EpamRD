package com.epam.rd.servlet;

import com.epam.rd.context.ApplicationContext;
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
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@WebServlet("/cptch")
public class CaptchaServlet extends HttpServlet {
    private static Random random = new Random();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processRequest(req, resp);
    }

    public static String generateKey() {
        int iTotalChars = 6;

        Map<String, String> captchaStorage = (Map<String, String>) ApplicationContext.getInstance().getAttribute("captchaStorage");
        String key = System.currentTimeMillis() + "";

        long randomLong = random.nextLong();
        captchaStorage.put(key, (Long.toString(Math.abs(randomLong), 36)).substring(0, iTotalChars));

        return key;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> captchaStorage = (Map<String, String>) ApplicationContext.getInstance().getAttribute("captchaStorage");
        response.setContentType("image/jsp");

        int iHeight = 40;
        int iWidth = 150;

        Font fntStyle = new Font("Arial", Font.BOLD, 30);

        String sImageCode = Optional.ofNullable(captchaStorage.get(request.getParameter("key")))
                .orElseThrow(() -> new RuntimeException("create captcha"));

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

        OutputStream osImage = response.getOutputStream();
        ImageIO.write(biImage, "jpeg", osImage);

        g2dImage.dispose();
    }
}
