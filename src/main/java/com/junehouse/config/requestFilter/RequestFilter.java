package com.junehouse.config.requestFilter;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class RequestFilter extends HttpServletRequestWrapper {
    class ServletInputStreamImpl extends ServletInputStream {
        private InputStream inputStream;

        public ServletInputStreamImpl(final InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public boolean isFinished() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean isReady() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public int read() throws IOException {
            return this.inputStream.read();
        }

        @Override
        public int read(final byte[] b) throws IOException {
            return this.inputStream.read(b);
        }

        @Override
        public void setReadListener(final ReadListener listener) {
            // TODO Auto-generated method stub
        }
    }

    private byte[] bytes;
    private String requestBody;

    public RequestFilter(HttpServletRequest request) throws IOException {
        super(request);

        ServletInputStream inputStream = super.getInputStream();
        bytes = IOUtils.toByteArray(inputStream);
        requestBody = new String(bytes);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        return new ServletInputStreamImpl(byteArrayInputStream);
    }

    public String getRequestBody() {
        return this.requestBody;
    }
}
