
public class ReceiveLogsError {

	/**
	 * Receives only the log messages in the error category.
	 */
	public static void main(String[] args) throws Exception {
		new ReceiveLogsDirect("error").run();
	}
	
}
