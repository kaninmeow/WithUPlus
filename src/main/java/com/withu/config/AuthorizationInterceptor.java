package com.withu.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.withu.annotation.IgnoreAuth;
import com.withu.properties.JwtProperties;
import com.withu.result.Result;
import com.withu.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

import io.jsonwebtoken.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;

/**
 * 权限(Token)验证拦截器
 */
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);
    public static final String LOGIN_TOKEN_KEY = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String[] ALLOWED_HEADERS = {
            "x-requested-with", "request-source", "Token", "Origin", "imgType",
            "Content-Type", "cache-control", "postman-token", "Cookie",
            "Accept", "authorization", LOGIN_TOKEN_KEY
    };

    private final JwtProperties jwtProperties;

    public AuthorizationInterceptor(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 记录请求开始时间
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        // 设置跨域相关头信息
        setCorsHeaders(request, response);

        // 处理OPTIONS预检请求
        if (isPreflightRequest(request)) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return false;
        }
        // 检查是否需要跳过认证
        if (shouldSkipAuth(handler)) {
            return true;
        }

        // 验证Token
        return validateToken(request, response);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        // 记录请求处理时间
        Long startTime = (Long) request.getAttribute("startTime");
        if (startTime != null) {
            long duration = System.currentTimeMillis() - startTime;
            logger.info("Request {} {} processed in {} ms",
                    request.getMethod(),
                    request.getRequestURI(),
                    duration);
        }
    }

    private void setCorsHeaders(HttpServletRequest request, HttpServletResponse response) {
        String origin = request.getHeader("Origin");
        if (origin != null) {
            response.setHeader("Access-Control-Allow-Origin", origin);
        }
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, PATCH");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", String.join(", ", ALLOWED_HEADERS));
        response.setHeader("Access-Control-Expose-Headers", "Authorization, Content-Disposition");
    }

    private boolean isPreflightRequest(HttpServletRequest request) {
        return RequestMethod.OPTIONS.name().equals(request.getMethod());
    }

    private boolean  shouldSkipAuth(Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 检查方法或类上是否有IgnoreAuth注解
        return handlerMethod.getMethodAnnotation(IgnoreAuth.class) != null ||
                handlerMethod.getBeanType().getAnnotation(IgnoreAuth.class) != null;
    }

    private boolean validateToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = extractToken(request);
        if (token == null || token.isEmpty()) {
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "缺少Token");
            return false;
        }

        try {
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
            if (claims == null) {
                sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "无效的Token");
                return false;
            }

            // 将用户信息存入request，方便后续使用
            request.setAttribute("userId", claims.getSubject());
            request.setAttribute("userClaims", claims);

            return true;
        } catch (ExpiredJwtException e) {
            logger.warn("Token已过期: {}", e.getMessage());
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Token已过期");
            return false;
        } catch (SignatureException e) {
            logger.warn("Token签名无效: {}", e.getMessage());
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Token签名无效");
            return false;
        } catch (Exception e) {
            logger.error("Token验证失败: {}", e.getMessage(), e);
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Token验证失败");
            return false;
        }
    }

    private String extractToken(HttpServletRequest request) {
        String token = request.getHeader(LOGIN_TOKEN_KEY);
        if (token != null && token.startsWith(TOKEN_PREFIX)) {
            return token.substring(TOKEN_PREFIX.length());
        }
        return token;
    }

    private void sendErrorResponse(HttpServletResponse response,
                                   HttpStatus status,
                                   String message) throws IOException {
        // 设置响应状态码
        response.setStatus(status.value());
        // 设置Content-Type为JSON
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // 设置字符编码
        response.setCharacterEncoding("UTF-8");

        // 构建统一的JSON响应结构
        Result errorResult = Result.error(message);

        // 使用ObjectMapper将对象序列化为JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(errorResult);

        // 写入响应
        try (PrintWriter out = response.getWriter()) {
            out.write(jsonResponse);
            out.flush();
        }

    }
}