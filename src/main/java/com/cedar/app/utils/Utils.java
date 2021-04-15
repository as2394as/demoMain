package com.cedar.app.utils;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class Utils {

	private String randomeString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789";
	private Random r = new Random();
	
	public String getUserId()
	{
		StringBuffer st = new StringBuffer();
		for(int i =0;i<30;i++)
		{
			
			int index = r.nextInt(60);
			st.append(randomeString.charAt(index));
		}
		return st.toString();
	}
	
	
}
