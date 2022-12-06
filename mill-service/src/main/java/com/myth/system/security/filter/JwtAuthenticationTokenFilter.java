package com.myth.system.security.filter;

import com.alibaba.fastjson.JSON;
import com.myth.common.result.JsonResult;
import com.myth.common.result.ResultCode;
import com.myth.common.result.ResultTool;
import com.myth.common.utils.JwtUtils;
import com.myth.system.dataSource.DynamicDataSource;
import com.myth.system.service.imple.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xuqiang
 * @version 1.0
 * &#064;date  2022/11/8 10:28
 * &#064;description  这个过滤器用来判断JWT是否有效
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    /**
     * 直接将我们前面写好的service注入进来，通过service获取到当前用户的权限
     * */
    @Autowired
    private SecurityUserService userDetailsService;
    @Autowired
    private DynamicDataSource dynamicDataSource;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // 获取当请求头中的token，其实这里多余，完全可以使用HttpServletRequest来获取
        String authToken = httpServletRequest.getHeader("UserToken");
        String account = JwtUtils.getUserAccountByJwtToken(httpServletRequest);
        if(account.equals("")&&authToken!=null&&!authToken.equals("")){
            JsonResult noPermission = ResultTool.fail(ResultCode.USER_ACCOUNT_EXPIRE);
            //处理编码方式，防止中文乱码的情况
            httpServletResponse.setContentType("text/json;charset=utf-8");
            // 把Json数据放到HttpServletResponse中返回给前台
            httpServletResponse.getWriter().write(JSON.toJSONString(noPermission));

            return;
        }       // 获取到当前用户的account
        //System.out.println("自定义JWT过滤器获得用户名为"+account);
        // 当token中的username不为空时进行验证token是否是有效的token
        if (!account.equals("") && SecurityContextHolder.getContext().getAuthentication() == null) {
            // token中username不为空，并且Context中的认证为空，进行token验证
            // 获取到用户的信息，也就是获取到用户的权限

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(account);
            if (JwtUtils.checkToken(authToken)) {   // 验证当前token是否有效
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                //将authentication放入SecurityContextHolder中
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }
        // 放行给下个过滤器
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}

