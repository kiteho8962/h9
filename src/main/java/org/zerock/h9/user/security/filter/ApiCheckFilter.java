package org.zerock.h9.user.security.filter;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.zerock.h9.user.security.util.JWTUtil;
import org.zerock.h9.user.service.MemberDetailsService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class ApiCheckFilter extends OncePerRequestFilter {

    private String pattern;
    private AntPathMatcher matcher;

    @Autowired
    private MemberDetailsService memberDetailsService;

    public ApiCheckFilter(String pattern) {
        this.pattern = pattern;
        this.matcher = new AntPathMatcher();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Checking -------------------------->");
        String requestURI = request.getRequestURI();
        boolean matchResult = matcher.match(pattern, requestURI);
        if(matchResult == false) {
            log.info("Pass-------------------->");
            filterChain.doFilter(request, response);
            return;
        }

        log.info("Target Checking------------------------------->"+memberDetailsService);

        String tokenValue = request.getHeader("Authorization");
        // 헤더에 Authorization 안에 들어가있는 Bearer 토큰값이 같이 들어가있음
        if(tokenValue != null) {
            String jwtStr = tokenValue.substring(7); // tokenValue에서 Bearer를 자른 jwt 토큰값만 들어가있음
            try {
                String email = new JWTUtil().validateAndExtract(jwtStr); // 토큰값 검사
                log.info("result"+email);// 검사값 true or false만 나옴
                UserDetails userDetails = memberDetailsService.loadUserByUsername(email);
                log.info(userDetails);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); //????
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                String content = "{\"msg\" : \"TOKEN ERROR\"}";
                response.getWriter().println(content);
            }
        }else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            String content = "{\"msg\" : \"TOKEN ERROR\"}";
            response.getWriter().println(content);
        } // end if else


    }
}
