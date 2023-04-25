package com.nineplus.localhand.config.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.nineplus.localhand.dto.ApiResponseDto;
import com.nineplus.localhand.dto.AuthenDto;
import com.nineplus.localhand.dto.LogInDto;
import com.nineplus.localhand.dto.UserDto;
import com.nineplus.localhand.service.UserService;
import com.nineplus.localhand.utils.*;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@PropertySource("classpath:application.properties")
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static String currentUsername = "";

    private AuthenticationManager authenticationManager;

    @Autowired
    private UserAuthUtils authUtils;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private LocalHandBeanUtil localHandBeanUtil;

    @Autowired
    MessageUtils messageUtils;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManage) {
        this.authenticationManager = authenticationManage;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String requestData = "";
        implementBean(request);
        try {
            requestData = request.getReader().lines().collect(Collectors.joining());
        } catch (IOException ex) {

        }
        LogInDto loginInfor = null;
        String username = null;
        String password = null;
        try {
            loginInfor = new Gson().fromJson(requestData, LogInDto.class);
            username = loginInfor.getUsername();
            password = loginInfor.getPassword();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        currentUsername = username;
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication auth = authenticationManager.authenticate(authToken);
        return auth;
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        // set content type as json
        response.setContentType("application/json");
        String userName = currentUsername;
        implementBean(request);
        ApiResponseDto apiResponseDto = ApiResponseDto.builder().code(CommonConstants.MessageError.ER017)
                .message(messageUtils.getMessage(CommonConstants.MessageError.ER017, null)).data(null)
                .status(CommonConstants.ApiStatus.STATUS_ERROR).build();
		try {
			response.getWriter().write(localHandBeanUtil.objectToJsonString(apiResponseDto));
            response.setContentType("application/json");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
		implementBean(request);
		String accessToken = tokenUtils.generateAccessToken(request, (User) authResult.getPrincipal());
        String refreshToken = tokenUtils.generateRefreshToken(request,(User) authResult.getPrincipal());
        UserDto userDto = new UserDto();
        try {
             userDto = authUtils.getUserInfo(((User) authResult.getPrincipal()).getUsername());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        AuthenDto authenDto = new AuthenDto(accessToken,refreshToken,"Bearer",userDto);
        ApiResponseDto apiResponseDto = ApiResponseDto.builder().code(CommonConstants.MessageSuccess.SC007)
                .message(messageUtils.getMessage(CommonConstants.MessageSuccess.SC007, null)).data(authenDto)
                .status(CommonConstants.ApiStatus.STATUS_OK).build();
        try {
            response.getWriter().write(localHandBeanUtil.objectToJsonString(apiResponseDto));
            response.setContentType("application/json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
		response.setHeader("Access-Control-Expose-Headers",
				CommonConstants.Authentication.ACCESS_TOKEN + "," + CommonConstants.Authentication.REFRESH_TOKEN
						+ ", x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, "
						+ "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
	}

    private void implementBean(HttpServletRequest request) {
        if (tokenUtils == null || objectMapper == null || authUtils == null || messageUtils == null || localHandBeanUtil == null) {
            ServletContext servletContext = request.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils
                    .getWebApplicationContext(servletContext);
            assert webApplicationContext != null;
            authUtils = webApplicationContext.getBean(UserAuthUtils.class);
            objectMapper = webApplicationContext.getBean(ObjectMapper.class);
            tokenUtils = webApplicationContext.getBean(TokenUtils.class);
            messageUtils = webApplicationContext.getBean(MessageUtils.class);
            localHandBeanUtil = webApplicationContext.getBean((LocalHandBeanUtil.class));
        }
    }

}
