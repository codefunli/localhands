package com.nineplus.localhand.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.nineplus.localhand.dto.GreetingDto;
import com.nineplus.localhand.dto.LocationBeanDto;
import com.nineplus.localhand.model.User;
import com.nineplus.localhand.service.UserService;

import lombok.extern.slf4j.Slf4j;

//controller for message send and receive form web socket
@Controller
@Slf4j
public class CommunicationController {

	@Autowired
	UserService userService;

	@MessageMapping("/saveLocation") // for saving current user's location in memory
	@SendTo("/topic/getData") // for transmitting all connected user's latest location
	public GreetingDto saveLocation(@Payload LocationBeanDto payload, SimpMessageHeaderAccessor headerAccessor) {
		String username = "Anonymous";
		User user = userService.findById(Long.parseLong(payload.getUser()));
		if(user != null && user.getUsername() != null) {
			username =  user.getUsername();
		}
		
		// getting current user's location from web socket
		headerAccessor.getSessionAttributes().put("username", payload.getUser());
		log.debug("\tUser:" + payload.getUser() + " >>> LocationBean:" + payload.toString());

		userService.updateUserLocation(payload);

		// send all user's latest location feed
		return new GreetingDto("Hello " + username);
	}
}
