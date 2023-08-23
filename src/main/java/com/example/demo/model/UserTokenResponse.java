package com.example.demo.model;

import java.util.Optional;

public class UserTokenResponse {
	private String token;
	private Optional<Login> user;
	
	public UserTokenResponse(String token,Optional<Login>user) {
		this.token=token;
		this.user=user;
	}
	public String getToken() {
		return token;
	}
	public Optional<Login> getUser(){
		return user;
	}
}
