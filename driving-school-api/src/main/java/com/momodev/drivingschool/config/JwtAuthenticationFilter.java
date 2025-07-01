package com.momodev.drivingschool.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwt;
    private final com.momodev.drivingschool.repository.UserRepository users;

    public JwtAuthenticationFilter(JwtTokenProvider jwt,
            com.momodev.drivingschool.repository.UserRepository users) {
        this.jwt = jwt;
        this.users = users;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String header = ((HttpServletRequest) request).getHeader("Authorization");
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (jwt.isValid(token)) {
                String username = jwt.getUsername(token);
                users.findByUsername(username).ifPresent(u -> {
                    var auth = new UsernamePasswordAuthenticationToken(
                            u, null, u.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource()
                            .buildDetails((HttpServletRequest) request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                });
            }
        }
        chain.doFilter(request, response);
    }
}
