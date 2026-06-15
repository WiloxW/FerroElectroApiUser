
package com.API.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, 
                            HttpServletResponse response, 
                            Object handler) throws Exception {
        
        String path = request.getRequestURI();
        if (path.equals("/usuarios/login") || 
            path.equals("/usuarios") && request.getMethod().equals("POST") ||
            path.startsWith("/usuarios/public/")) {
            return true;
        }
        
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("logueado") != null) {
            return true;
        }
        
        response.setStatus(401);
        response.getWriter().write("{\"error\":\"No autenticado\"}");
        return false;
    }
}
