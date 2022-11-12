package com.epam.rd.filter;

import com.epam.rd.filter.wrapper.GZipResponseWrapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;

public class GZIPCompressingFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        super.doFilter(req, res, chain);
        Enumeration<String> headers = req.getHeaders("Accept-Encoding");
        if (headers.hasMoreElements() && isGZipAccepted(headers.nextElement().split(", "))) {
            res.addHeader("Content-Encoding", "gzip");
            GZipResponseWrapper gzipResponse = new GZipResponseWrapper(res);
            chain.doFilter(req, gzipResponse);
            gzipResponse.close();
        } else {
            chain.doFilter(req, res);
        }
    }

    private boolean isGZipAccepted(String[] acceptedEncodings) {
        for (int i = 0; i < acceptedEncodings.length; i++) {
            if (acceptedEncodings[i].equalsIgnoreCase("gzip")) {
                return true;
            }
        }
        return false;
    }
}
