package com.tensquare.manager;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 过滤器
 */
@Component
public class ManagerFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Object run() throws ZuulException {
        System.out.println("经过网站后台网关过滤器....");
        RequestContext requestContext = RequestContext.getCurrentContext();

        HttpServletRequest request = requestContext.getRequest();

        //处理跨域  ，第一次请求是预请求  ，不带头信息
        if(request.getMethod().equals("OPTIONS")){
            System.out.println("跨域的预请求，放行");
            return null;
        }

        //判断是否为登录请求，如果是登陆，直接放行
        String url = request.getRequestURL().toString();
        if(url.indexOf("/admin/login")>0){
            return null;
        }

        String header = request.getHeader("Authorization");

        if(header!=null && header.startsWith("Bearer ")){
            String token = header.substring(7);
            Claims claims = jwtUtil.parseJWT(token);
            if(claims!=null){
                if(claims.get("roles").equals("admin")){//角色是管理员
                    requestContext.addZuulRequestHeader("Authorization",header);
                    return null;
                }
            }
        }
        //无权访问微服务
        requestContext.setSendZuulResponse(false);//终止运行
        requestContext.setResponseStatusCode(401);//http状态码
        requestContext.setResponseBody("无权访问");
        requestContext.getResponse().setContentType("text/html;charset=UTF-8");
        return null;
    }
}
