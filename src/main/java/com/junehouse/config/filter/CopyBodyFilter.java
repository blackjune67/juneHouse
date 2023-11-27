package com.junehouse.config.filter;

import com.junehouse.config.requestFilter.RequestFilter;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;


public class CopyBodyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        try {
            RequestFilter requestFilter = new RequestFilter((HttpServletRequest) request);
            requestFilter.setAttribute("requestBody", requestFilter.getRequestBody());
            chain.doFilter(requestFilter, response);
        } catch (Exception e) {
            chain.doFilter(request, response);
        }

    }
}
