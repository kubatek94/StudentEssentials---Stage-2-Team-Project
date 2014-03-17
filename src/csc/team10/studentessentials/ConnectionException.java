package csc.team10.studentessentials;

public class ConnectionException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ConnectionException(String message)
	{
		super("ConnectionException:" + message);
	}
}
