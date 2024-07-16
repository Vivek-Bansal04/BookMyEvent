package com.bookmyshow.api.utils.request;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import static com.bookmyshow.api.utils.ValidationUtils.isNullOrBlank;
import static com.bookmyshow.api.utils.constants.GlobalConstants.REQUEST_ID;

@Component
public class Slf4jMDCFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            populateRequestId(request);
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(REQUEST_ID);
        }
    }

    public static void populateRequestId(final HttpServletRequest request) {
        String requestId = request.getHeader(REQUEST_ID);
        if (isNullOrBlank(requestId)) {
            requestId = UUID.randomUUID().toString();
        }
        MDC.put(REQUEST_ID, requestId);
    }

    public static void populateRequestId() {
        final String requestId = UUID.randomUUID().toString();
        MDC.put(REQUEST_ID, requestId);
    }
}
