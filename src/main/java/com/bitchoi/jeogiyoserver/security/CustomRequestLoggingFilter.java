package com.bitchoi.jeogiyoserver.security;

import com.bitchoi.jeogiyoserver.config.SecurityConfig;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * Configuration to log request data during development, and skip product
 * profile.
 */

@Component
public class CustomRequestLoggingFilter extends CommonsRequestLoggingFilter {

    public CustomRequestLoggingFilter() {
        this.setIncludeQueryString(true);
        this.setIncludePayload(true);
        this.setMaxPayloadLength(10000);
        this.setIncludeHeaders(false);
        this.setAfterMessagePrefix("REQUEST DATA: ");
    }

    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        String reqPath = request.getServletPath();
        if(!doMatch(reqPath, SecurityConfig.API_DOCS_PATHS)) {
            return logger.isDebugEnabled();
        } else return false;
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        // Disable before request logging
    }
    private boolean doMatch(String path, String... antPaths) {
        AntPathMatcher antMatcher = new AntPathMatcher();
        for(String antPath : antPaths) {
            if(antMatcher.match(antPath, path)) return true;
        }

        return false;
    }
}
