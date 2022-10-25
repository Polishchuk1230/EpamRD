package com.epam.rd.servlet;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet("/img")
public class ImagesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");

        File file = new File(getServletContext().getInitParameter("images") + request.getParameter("file"));
        if (!file.exists()) {
            return;
        }

        // return an image
        try (FileInputStream in = new FileInputStream(file);
             ServletOutputStream out = response.getOutputStream()) {

            byte[] buffer = new byte[1024];
            for (int count; (count = in.read(buffer)) >= 0;) {
                out.write(buffer, 0, count);
            }
        }
    }
}
