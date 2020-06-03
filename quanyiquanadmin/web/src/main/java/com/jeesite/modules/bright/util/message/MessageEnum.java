
package com.jeesite.modules.bright.util.message;

public enum MessageEnum {
	
	TIMESTYLEERROR("时间格式错误",50002,500),
	NULL("请求参数有空",40001,404),
	Success("Success",200,200),
	ErrorVerify("验证码错误",50001,500),
	QRCODE_ERR("二维码生成失败",50001,500),
	ADMIN_SQL_ERROR("sql错误",50001,500),
	ERROR("内部错误,请稍后重试..",500,500);


	private String message;
	
	private int code;
	
	private int status;
	private MessageEnum(String message, int code,int status) {
		this.message = message;
		this.code = code;
		this.status=status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public void toMessage(Message message){
		if(message.getMessage()==null || "".equals(message.getMessage()))
			message.setMessage(this.message);
		if(message.getStatus()==null || "".equals(message.getStatus()))
			message.setStatus(this.status);
		message.setCode(this.code);
	}
}
