
public class ReceiveLogsAll {
	
	/**
	 * Receives log messages in all categories (i.e. error, info, warning)
	 */
	public static void main(String[] args) throws Exception {
		new ReceiveLogs("error", "info", "warning").run();
	}

}
