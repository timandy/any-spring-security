package com.spring4all.config;

import com.spring4all.io.CharsetNames;
import com.spring4all.io.StreamWriter;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by 许崇雷 on 2017/8/18.
 */
public class LoginFailureHandler implements AuthenticationFailureHandler {
    private String failureString = "failure";
    private String lockedString = "locked";

    //region property

    public String getFailureString() {
        return this.failureString;
    }

    public LoginFailureHandler setFailureString(String failureString) {
        this.failureString = failureString;
        return this;
    }

    public String getLockedString() {
        return this.lockedString;
    }

    public LoginFailureHandler setLockedString(String lockedString) {
        this.lockedString = lockedString;
        return this;
    }

    //endregion

    //constructor
    public LoginFailureHandler() {
    }

    //onFailure
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        if (e instanceof LockedException) {
            try (Writer writer = new StreamWriter(response.getOutputStream(), CharsetNames.UTF_8)) {
                writer.write(this.lockedString);
            }
        } else {
            try (Writer writer = new StreamWriter(response.getOutputStream(), CharsetNames.UTF_8)) {
                writer.write(this.failureString);
            }
        }
    }
}
