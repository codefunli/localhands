package com.nineplus.localhand.config.filter;

import com.nineplus.localhand.dto.ApiResponseDto;
import com.nineplus.localhand.dto.AuthenDto;
import com.nineplus.localhand.dto.UserDto;
import com.nineplus.localhand.service.UserService;
import com.nineplus.localhand.utils.*;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

    public String[] PUBLIC_URL;

    public String PREFIX_TOKEN;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserAuthUtils authUtils;

    @Autowired
    private MessageUtils messageUtils;

    @Autowired
    private LocalHandBeanUtil localHandBeanUtil;

    public CustomAuthorizationFilter(String prefixToken, String[] publicUrl) {
        super();
        this.PREFIX_TOKEN = prefixToken;
        this.PUBLIC_URL = Stream.of(publicUrl).map(item -> item.replace("/**", "")).toList()
                .toArray(new String[publicUrl.length]);
    }

    private Boolean isPublicUrl(String path) {
        for (String string : PUBLIC_URL) {
            if (path.startsWith(string)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String accessToken = request.getHeader(CommonConstants.Authentication.ACCESS_TOKEN);
        String refreshToken = request.getHeader(CommonConstants.Authentication.REFRESH_TOKEN);
        if (userService == null || authUtils == null || tokenUtils == null || messageUtils == null || localHandBeanUtil == null) {
            ServletContext servletContext = request.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils
                    .getWebApplicationContext(servletContext);
            assert webApplicationContext != null;
            userService = webApplicationContext.getBean(UserService.class);
            authUtils = webApplicationContext.getBean(UserAuthUtils.class);
            tokenUtils = webApplicationContext.getBean(TokenUtils.class);
            messageUtils = webApplicationContext.getBean(MessageUtils.class);
            localHandBeanUtil = webApplicationContext.getBean(LocalHandBeanUtil.class);
        }
        if (!(isPublicUrl(request.getServletPath()))) {
            if (refreshToken != null && (accessToken == null || tokenUtils.isAccessTokenExpired(accessToken)) &&
                    !tokenUtils.isRefreshTokenExpired(refreshToken)
                    && request.getHeader(CommonConstants.Authentication.PREFIX_TOKEN).startsWith(PREFIX_TOKEN)) {
                try {
                    User user = (User) userService.loadUserByUsername(tokenUtils.decodedRefreshToken(refreshToken).getSubject());
                    if (ObjectUtils.isEmpty(user)) {
                        throw new Exception();
                    }
                    String newAccessToken = tokenUtils.generateAccessToken(request, user);
                    authUtils.grantAuthenticateByAccessToken(newAccessToken);
                    UserDto userDto;
                    try {
                        userDto = authUtils.getUserInfo(user.getUsername());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    AuthenDto authenDto = new AuthenDto(newAccessToken,refreshToken,"Bearer",userDto);
                    ApiResponseDto apiResponseDto = ApiResponseDto.builder().code(CommonConstants.MessageSuccess.SC007)
                            .message(messageUtils.getMessage(CommonConstants.MessageSuccess.SC007, null)).data(authenDto)
                            .status(CommonConstants.ApiStatus.STATUS_OK).build();
                    response.getOutputStream().write(localHandBeanUtil.objectToJsonString(apiResponseDto).getBytes());
                } catch (Exception exception) {
                    response.setHeader("error", exception.getMessage());
                    response.sendError(HttpStatus.FORBIDDEN.value());
                }
            }
            if (accessToken != null && request.getHeader(CommonConstants.Authentication.PREFIX_TOKEN) != null
                    && request.getHeader(CommonConstants.Authentication.PREFIX_TOKEN).startsWith(PREFIX_TOKEN)
                    && !tokenUtils.isAccessTokenExpired(accessToken)) {
                try {
                    authUtils.grantAuthenticateByAccessToken(accessToken);
                } catch (Exception exception) {
                    response.setHeader("error", exception.getMessage());
                    response.sendError(HttpStatus.FORBIDDEN.value());
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private void addTokenToHeader(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setHeader(CommonConstants.Authentication.REFRESH_TOKEN, refreshToken);
        response.setHeader(CommonConstants.Authentication.ACCESS_TOKEN, accessToken);
        response.setHeader("Access-Control-Expose-Headers",
                CommonConstants.Authentication.REFRESH_TOKEN + "," + CommonConstants.Authentication.ACCESS_TOKEN
                        + ", x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, "
                        + "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
    }
}
