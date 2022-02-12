package com.devendrabrain.StudentMgr.filter;

import com.devendrabrain.StudentMgr.configbean.CustomUserDetails;
import com.devendrabrain.StudentMgr.dao.CustomUserDetailsService;
import com.devendrabrain.StudentMgr.helper.JwtHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JwtHelper jwtHelper;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        String accessToken = httpServletRequest.getHeader("Authorization");
        String jwt = null;
        String username = null;
        if(StringUtils.isNotBlank(accessToken)) {
            jwt = accessToken.substring(7);
            username = jwtHelper.extractUsername(jwt);
        }

        if(StringUtils.isNotBlank(username) && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails customUserDetails = userDetailsService.loadUserByUsername(username);
            if(jwtHelper.validateToken(jwt,customUserDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(customUserDetails, null,
                        customUserDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
