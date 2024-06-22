package com.cakeshoppingapp.system;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Result {
	private boolean flag;
	private int code;
	private String message;
	private Object data;

	public Result(boolean flag, int code, String message) {
		this.flag = flag;
		this.code = code;
		this.message = message;
	}

}
