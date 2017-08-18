package com.spring4all.config;

import com.spring4all.io.CharsetNames;
import com.spring4all.io.StreamWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by 许崇雷 on 2017/8/18.
 */
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final Log logger = LogFactory.getLog(this.getClass());
    private RequestCache requestCache = new HttpSessionRequestCache();
    private String successString = "success";

    //region property

    public RequestCache getRequestCache() {
        return this.requestCache;
    }

    public LoginSuccessHandler setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
        return this;
    }

    public String getSuccessString() {
        return this.successString;
    }

    public LoginSuccessHandler setSuccessString(String successString) {
        this.successString = successString;
        return this;
    }

    //endregion

    //constructor
    public LoginSuccessHandler() {
    }

    //constructor
    public LoginSuccessHandler(String defaultSuccessUrl) {
        this(defaultSuccessUrl, false);
    }

    //constructor
    public LoginSuccessHandler(String defaultSuccessUrl, boolean alwaysUse) {
        this.setDefaultTargetUrl(defaultSuccessUrl);
        this.setAlwaysUseDefaultTargetUrl(alwaysUse);
    }

    //onSuccess
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        SavedRequest savedRequest = this.requestCache.getRequest(request, response);
        if (savedRequest == null) {
            try (Writer writer = new StreamWriter(response.getOutputStream(), CharsetNames.UTF_8)) {
                writer.write(this.successString);
            }
        } else {
            String targetUrlParameter = this.getTargetUrlParameter();
            if (!this.isAlwaysUseDefaultTargetUrl() && (targetUrlParameter == null || !StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
                this.clearAuthenticationAttributes(request);
                String targetUrl = savedRequest.getRedirectUrl();
                this.logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
                this.getRedirectStrategy().sendRedirect(request, response, targetUrl);
            } else {
                this.requestCache.removeRequest(request, response);
                super.onAuthenticationSuccess(request, response, authentication);
            }
        }
    }
}
