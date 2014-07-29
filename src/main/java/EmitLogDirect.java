import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLogDirect {

    public static class Message {
    	public final String severity; //one of: 'error', 'warning', 'info'
    	public final String msg;
		public Message(String severity, String msg) {
			this.severity = severity;
			this.msg = msg;
		}
		public static Message parse(String str) {
			if (str==null || "".equals(str)) {
				return null;
			}
			int pos = str.indexOf(':');
			if (pos>0) {
				return new Message(
					str.substring(0, pos),
					str.substring(pos+1)
				);
			} else {
				return new Message("error", str);
			}
		}
		@Override
		public String toString() {
			return severity +" : "+msg;
		}
	}

	private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv)
                  throws java.io.IOException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

	    System.out.println("Type message to send to logs.\n"
	    		+ "Format ${severity}:${message}\n"
	    		+ "    or ${message} (uses default severity 'error')\n"
	    		+ "Type an empty message to exit this process.");
	    try {
		    while (true) {
		    	Message message = getMessage();
		    	if (message==null) {
		    		break;
		    	}
			    channel.basicPublish(EXCHANGE_NAME, message.severity, null, message.msg.getBytes());
			    System.out.println(" [x] Sent '" + message + "'");
		    }
	    } finally {
		    channel.close();
		    connection.close();
	    }
    }
    
	private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	
	private static Message getMessage() throws IOException {
		return Message.parse(input.readLine());
	}
    
    //...
}
