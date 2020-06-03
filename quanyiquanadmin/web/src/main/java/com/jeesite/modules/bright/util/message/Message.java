
package com.jeesite.modules.bright.util.message;
/**
 * 返回消息结果
 * @author Owen
 *
 */
public class Message{
	
	private String message;
	private Integer code;
	private Integer status;
	private Object datas;

	public Object getDatas() {
		return datas;
	}
	public void setDatas(Object datas) {
		this.datas = datas;
	}
	public Message(String message, Integer code) {
		super();
		this.message = message;
		this.code = code;
	}
	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getMessage() {
		return message;
	}
	/**
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message=message;
		
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
