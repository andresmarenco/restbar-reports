package restbar.reports;

public class ReportsException extends Exception {

	private static final long serialVersionUID = 201512311813L;

	/**
	 * Default Constructor
	 */
	public ReportsException() {
		super();
	}

	/**
	 * @param message
	 * @param ex
	 */
	public ReportsException(String message, Throwable ex) {
		super(message, ex);
	}

	/**
	 * @param message
	 */
	public ReportsException(String message) {
		super(message);
	}

	/**
	 * @param ex
	 */
	public ReportsException(Throwable ex) {
		super(ex);
	}

	
}
