package com.example.cartview.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

// 服务接口有响应  统一规范响应服务接口信息
@Data
@Slf4j
public class ResponseBase<T> implements Serializable {

	private static final long serialVersionUID = 4L;
	private Integer rtnCode;
	private String msg;
	private Object data;



	/**
	 * 存放数据对象
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T t;
	/**
	 * 获取数据对象
	 *
	 * @return T
	 */
	public T getT() {
		return t;
	}

	/**
	 * 设置数据对象
	 *
	 * @param t t
	 */
	public void setT(T t) {
		this.t = t;
	}

	public ResponseBase() {

	}


	public ResponseBase(Integer rtnCode, String msg, T t) {
		super();
		this.rtnCode = rtnCode;
		this.msg = msg;
		this.t=t;
	}

	public static void main(String[] args) {
		ResponseBase responseBase = new ResponseBase();

		responseBase.setMsg("success");
		responseBase.setRtnCode(200);
		System.out.println(responseBase.toString());
		log.info("fitness...");
	}

	@Override
	public String toString() {
		return "ResponseBase [rtnCode=" + rtnCode + ", msg=" + msg + ", data=" + data + "]";
	}

}
