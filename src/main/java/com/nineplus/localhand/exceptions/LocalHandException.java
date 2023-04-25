package com.nineplus.localhand.exceptions;

import com.nineplus.localhand.utils.MessageUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@EqualsAndHashCode(callSuper = false)
public class LocalHandException extends Exception {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private MessageUtils messageUtils;

	private String msgCode;

	private Object param[];

	/**
	 * Constructor
	 * 
	 * @param String   msgCode
	 * @param Object[] param
	 */
	public LocalHandException(String msgCode, Object[] param) {
		this.msgCode = msgCode;
		this.param = param;
	}

}
