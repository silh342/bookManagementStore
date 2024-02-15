package com.younes.reskillingproject.userManagement.security.jwtAuthentication;

import com.younes.reskillingproject.userManagement.security.Service.UserServiceImpl;
import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtGenerator jwtGenerator;
    @Autowired
    private UserServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(@Nullable HttpServletRequest request,
                                    @Nullable HttpServletResponse response,
                                    @Nullable FilterChain filterChain) throws ServletException, IOException {

        // Step 1 : Extract token from request Header using the helper method i created in the JwtGenerator Class
        // Used Assert to avoid the null warning for the parameters
        // need to go back to this assert to implement a better solution
        assert request != null;
        assert filterChain != null;
        String token = extractTokenFromRequest(request);
        // Step 2 : valid token with helper method
        if(StringUtils.hasText(token) && jwtGenerator.validateToken(token)) {
            String username = jwtGenerator.getUsernameFromJWT(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // this is confusing , too much new stuff i need to get back to this later
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        // Continue the chaining after authenticating the user
        filterChain.doFilter(request, response);
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        // Parameter name called s ??? why ?
        String tokenHeader = request.getHeader("Authorization");
        if(StringUtils.hasText(tokenHeader) && tokenHeader.startsWith("Bearer ")) {
            return tokenHeader.substring(7);
        }
        return null;
    }
}
