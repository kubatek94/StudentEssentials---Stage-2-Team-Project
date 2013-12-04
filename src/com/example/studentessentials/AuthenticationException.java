package com.example.studentessentials;

/**
 * Author: Jakub Gawron
 * Date: 03.12.2013
 */

public class AuthenticationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

		public AuthenticationException(String message)
		{
			super(message);
		}
}
