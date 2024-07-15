package com.medical.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medical.model.CommonResponse;
import com.medical.properties.JwtProperties;
import com.medical.utils.BaseContext;
import com.medical.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

/**
 * jwt令牌校验的拦截器
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenUserInterceptor implements HandlerInterceptor {
    private final JwtProperties jwtProperties;
    private final ObjectMapper objectMapper;

    /**
     * 校验jwt
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }

        //1、从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getUserTokenName());


        //2、校验令牌
        try {
            log.info("jwt校验:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            Long userId = Long.valueOf(claims.get("account").toString());
            BaseContext.setCurrentId(userId);
            log.info("当前用户id：", userId);
            //3、通过，放行
            return true;
        } catch (Exception ex) {
            //4、不通过，响应401状态码
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
    }

    private void forbidden(HttpServletResponse response) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setCharacterEncoding("UTF-8"); // 设置字符编码为 UTF-8
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try {
            response.getWriter().write(objectMapper.writeValueAsString(CommonResponse.fail("没有权限")));
        } catch (IOException e) {
            log.error("暂无权限时写入响应发生异常: " + e);
        }
    }
}
