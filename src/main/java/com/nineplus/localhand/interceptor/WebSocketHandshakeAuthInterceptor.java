package com.nineplus.localhand.interceptor;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import org.springframework.web.util.UriComponentsBuilder;

import com.nineplus.localhand.model.User;
import com.nineplus.localhand.service.UserService;
import com.nineplus.localhand.utils.TokenUtils;

@Component
public class WebSocketHandshakeAuthInterceptor extends HttpSessionHandshakeInterceptor {
//    final Cache authCache;

    @Autowired
    TokenUtils tokenUtils;
    
    @Autowired
    UserService userService;
    
//    @Autowired
//    public WebSocketHandshakeAuthInterceptor(CacheManager cacheManager) {
//        this.authCache = cacheManager.getCache("AuthCache");
//    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
    	
    	// Verify user exist from access token
    	String token = UriComponentsBuilder.fromHttpRequest(request).build()
                .getQueryParams().get("authentication").get(0);
    	String username = tokenUtils.decodedAccessToken(token).getSubject();
    	User user = userService.getUserByUsername(username);
    	if(user == null ) {
    		response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return false;
    	}
    	
    	//verify token exist in cache
//        UUID authToken = getAuthToken(request);
//        WebSocketAuthInfo webSocketAuthInfo = getWebSocketAuthInfo(authToken);
//
//        if (webSocketAuthInfo == null) {
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            return false;
//        }
//
        return true;
    }

//    UUID getAuthToken(ServerHttpRequest request) {
//        try {
//            return UUID.fromString(UriComponentsBuilder.fromHttpRequest(request).build()
//                    .getQueryParams().get("authentication").get(0));
//        } catch (NullPointerException e) {
//            return null;
//        }
//    }

//    WebSocketAuthInfo getWebSocketAuthInfo(UUID authToken) {
//        if (authToken == null) {
//            return null;
//        }
//        Cache.ValueWrapper cacheResult = authCache.get(authToken);
//        if (cacheResult == null) {
//            return null;
//        }
//        authCache.evict(authToken);
//        return (WebSocketAuthInfo) cacheResult.get();
//    }
}
