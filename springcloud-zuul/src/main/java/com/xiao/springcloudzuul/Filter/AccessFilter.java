package com.xiao.springcloudzuul.Filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.xiao.springcloudcommon.util.ApiCodeEnum;
import com.xiao.springcloudcommon.util.ApiOutput;
import com.xiao.springcloudcommon.util.WebUtil;
import com.xiao.springcloudproducer.dto.user.out.LoginDtoOut;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

public class AccessFilter extends ZuulFilter {

    // 此处配置不拦截url
    private String urls[] = {"/springcloud-producer/auth/login"};

    @Resource(name = "redisTemplate")
    private RedisTemplate<String, LoginDtoOut> sessionDTORedisTemplate;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return !PatternMatchUtils.simpleMatch(urls, RequestContext.getCurrentContext().getRequest().getRequestURI());
    }


    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = null;
        JSONObject jsonObject = WebUtil.getJsonRequestParams(request);
        if (!StringUtils.isEmpty(jsonObject)) {
            token = jsonObject.getString("token");
        }
        if (StringUtils.isEmpty(token)) {
            remindMessage(ctx);
        }
        LoginDtoOut loginDtoOut = sessionDTORedisTemplate.opsForValue().get(token);
        if (StringUtils.isEmpty(loginDtoOut)) {
            remindMessage(ctx);
        }
        sessionDTORedisTemplate.expire(token, 10800, TimeUnit.SECONDS);
        return null;
    }

    private void remindMessage(RequestContext ctx) {
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(200);
        // 为使得中文字符不乱码
        ctx.getResponse().setCharacterEncoding("UTF-8");
        ctx.setResponseBody(JSON.toJSONString(new ApiOutput(ApiCodeEnum.NEED_LOGIN.getCode(), ApiCodeEnum.NEED_LOGIN.getMessage())));
    }
}
