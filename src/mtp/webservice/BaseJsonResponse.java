package mtp.webservice;

import com.google.gson.Gson;

public class BaseJsonResponse {
	
	private String tag;
	private boolean status;
	private String error_msg;
	
	public BaseJsonResponse(String tag, boolean status, String error_msg)
	{
		this.tag = tag;
		this.status = status;
		this.error_msg = error_msg;
	}
	
	public BaseJsonResponse()
	{
		
	}
	
	public BaseJsonResponse(String tag, boolean status)
	{
		this.tag = tag;
		this.status = status;
	}
	
	public String toJson()
	{
		Gson gson = new Gson();
		return gson.toJson(this);
	}

}
