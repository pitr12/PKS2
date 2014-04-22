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
	    System.out.println("Server started");
	    while (true) {
	    	
	      sk.receive(dgp);
	      
	      SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");
	      String rcvd = fmt.format(new Date()) + ": "+ new String(dgp.getData(), 0, dgp.getLength()) + "\n";
	     
	      UI.textPane.appendText(rcvd, "s");
	    }
	    }
}
