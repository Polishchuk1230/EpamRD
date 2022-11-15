package com.epam.rd.wrapper;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class GZipResponseWrapper extends HttpServletResponseWrapper {
    private GZipServletOutputStream gzipOutputStream;
    private PrintWriter printWriter;

    public GZipResponseWrapper(HttpServletResponse response)
            throws IOException {
        super(response);
    }

    public void close() throws IOException {
        if (this.printWriter != null) {
            this.printWriter.close();
        }

        if (this.gzipOutputStream != null) {
            this.gzipOutputStream.close();
        }
    }

    @Override
    public void flushBuffer() throws IOException {
        if (this.printWriter != null) {
            this.printWriter.flush();
        }

        if(this.gzipOutputStream != null) {
            this.gzipOutputStream.flush();
        }

        super.flushBuffer();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (this.printWriter != null) {
            throw new IllegalStateException(
                    "PrintWriter obtained already - cannot get OutputStream");
        }
        if (this.gzipOutputStream == null) {
            this.gzipOutputStream = new GZipServletOutputStream(
                    getResponse().getOutputStream());
        }
        return this.gzipOutputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (this.printWriter == null && this.gzipOutputStream != null) {
            throw new IllegalStateException(
                    "OutputStream obtained already - cannot get PrintWriter");
        }
        if (this.printWriter == null) {
            this.gzipOutputStream = new GZipServletOutputStream(
                    getResponse().getOutputStream());
            this.printWriter =
                    new PrintWriter(
                            new OutputStreamWriter(
                                    this.gzipOutputStream, getResponse().getCharacterEncoding()));
        }
        return this.printWriter;
    }

    @Override
    public void setContentLength(int len) {
        // ignore, since content length of zipped content
        // does not match content length of unzipped content.
    }
}
