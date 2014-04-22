package work;

import gui.UI;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {
	
	public static void runServer() throws Exception {
	    int PORT = 4587;
	    byte[] buf = new byte[1000];
	    DatagramPacket dgp = new DatagramPacket(buf, buf.length);
	    DatagramSocket sk;

	    sk = new DatagramSocket(PORT);
	    while (true) {
	    	String message = "";
	    	int fragment_count = 1;
	    	while(true){
	    		sk.receive(dgp);
	    		String small_message = new String(dgp.getData(), 1, dgp.getLength()-1);
	    		message = message + small_message;
	    		String head = new String(dgp.getData(), 0, 1);
	    		if("1".equals(head)){
	    			break;
	    		}
	    		fragment_count++;	    	}
	      
	      SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");
	      String rcvd = fmt.format(new Date()) + ": "+ message;
	      UI.textPane.appendText(rcvd, "s");
	      String count = " (fragment count: " +Integer.toString(fragment_count) + " )\n\n";
	      UI.textPane.appendText(count, "b");
	    }
	    }
}
