package work;

import gui.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client {
	public static byte[] buffer;
	public static DatagramSocket socket;
	
	public static void runClient() throws Exception{
	    socket = new DatagramSocket();
	    buffer = new byte[10000];

	    final InetAddress hostAddress = InetAddress.getByName("localhost");
	      
	      UI.SendMessage.addActionListener(
					new ActionListener(){
						public void actionPerformed(ActionEvent e)
						{		
							SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");
							String msg = UI.textPane_down.getText();
							
							buffer = msg.getBytes();
							DatagramPacket out = new DatagramPacket(buffer, buffer.length, hostAddress, 4587);
							try {
								socket.send(out);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							
							
							if(!"".equals(msg))
								msg = fmt.format(new Date()) + ": "+ msg + "\n";
							
							UI.textPane.appendText(msg, "c");
							UI.textPane_down.setText("");
						}
					}
			);
	  }
}
