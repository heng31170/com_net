package com.zaizi.interceptor;

import com.zaizi.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle...");

        // 1. 获取请求url
        String url = request.getRequestURL().toString();
        log.info("请求的url: {}", url);

        // 允许对图片等静态资源的访问
        if (url.contains(".png") || url.contains(".jpg") || url.contains(".jpeg")) {
            return true; // 直接放行，不需要令牌
        }

        // 2. 判断请求url中是否包含login，是则放行
        if (url.contains("login")) {
            log.info("登录操作，放行...");
            return true;
        }

        // 3. 处理预检请求
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        // 4. 获取请求头中的令牌（token）
        String jwt = request.getHeader("Authorization");

        // 5. 判断令牌是否存在，如果不存在，返回错误结果（未登录）
        if (!StringUtils.hasLength(jwt)) {
            log.info("请求头token为空，返回未登录的信息");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 设置状态码为401
            response.getWriter().write("NOT_LOGIN");
            response.getWriter().flush(); // 确保输出缓冲区被写入
            return false; // 拦截请求
        }

        // 6. 解析token，解析失败返回错误结果（未登录）
        try {
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) {
            log.info("解析令牌失败，返回未登录错误信息");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 设置状态码为401
            response.getWriter().write("NOT_LOGIN");
            response.getWriter().flush(); // 确保输出缓冲区被写入
            return false; // 拦截请求
        }

        // 7. 放行
        log.info("令牌合法，放行");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle...");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("afterCompletion...");
    }
}