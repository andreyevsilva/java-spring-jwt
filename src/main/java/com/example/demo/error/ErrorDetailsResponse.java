package com.example.demo.error;

import javax.servlet.http.HttpServletResponse;

public interface ErrorDetailsResponse {
	
	public void sendErrorDetailsResponse(String title,HttpServletResponse response,Exception exception);

}
