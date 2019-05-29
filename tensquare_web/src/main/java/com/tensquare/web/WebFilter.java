package com.tensquare.web;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 过滤器
 */
@Component
public class WebFilter extends ZuulFilter {


    @Override
    public String filterType() {
        return "pre";//过滤器类型
    }

    @Override
    public int filterOrder() {
        return 0;//排序序号  序号越大，越靠后
    }

    @Override
    public boolean shouldFilter() {
        return true;//开关 ，是否执行该过滤器
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("经过了zuul过滤器....");
        RequestContext requestContext = RequestContext.getCurrentContext();

        HttpServletRequest request = requestContext.getRequest();
        String header = request.getHeader("Authorization");
        if(header!=null){
            requestContext.addZuulRequestHeader("Authorization",header);
        }

        return null;
    }
}
