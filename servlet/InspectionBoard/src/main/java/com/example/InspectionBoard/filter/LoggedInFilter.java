package com.example.InspectionBoard.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;

@WebFilter("/faculty")
public class LoggedInFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

  /*  @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        Cookie token = getCookie("token", (HttpServletRequest) request);
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if (token != null){
            try{
                UUID tokenValue = UUID.fromString(token.getValue());
                if (TokenRepository.contains(tokenValue)){
                    filterChain.doFilter(request, response);
                    return;
                }
            }catch (IllegalArgumentException ignore){ }
        }
        httpResponse.sendError(403);
    }
*/

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        if (session.getAttribute("id") == null){
            ((HttpServletResponse) response).sendError(403);
        }
        filterChain.doFilter(request, response);
    }


        private Cookie getCookie(String name, HttpServletRequest request){
        for (Cookie item : request.getCookies()){
            if (Objects.equals(item.getName(), name)){
                return item;
            }
        }
        return null;
    }
}
