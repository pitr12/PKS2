package work;

import gui.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

public class Client {
	public static byte[] buffer;
	public static DatagramSocket socket;
	public static InetAddress hostAddress;
	public static int fragment_size;
	
	public static void runClient() throws Exception{
	    socket = new DatagramSocket();
	    buffer = new byte[10000];
	      
	      UI.SendMessage.addActionListener(
					new ActionListener(){
						public void actionPerformed(ActionEvent e)
						{		
							SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");
							String msg = UI.textPane_down.getText();
							
							
								try {
									hostAddress = InetAddress.getByName(UI.ipAdress.getText());
								} catch (UnknownHostException e2) {
									e2.printStackTrace();
								}
								
								if(!"".equals(UI.fragmentSize.getText()))
									fragment_size = Integer.parseInt(UI.fragmentSize.getText());
								else
									fragment_size = 0;
							
							
							/**
							 * POP UP windows*/
							if("".equals(UI.ipAdress.getText())){
								String adress = JOptionPane.showInputDialog(UI.frame,"Enter Host IP Adress",null);
								try {
				
									hostAddress = InetAddress.getByName(adress);
								} catch (UnknownHostException e1) {
									e1.printStackTrace();
								}
								UI.ipAdress.setText(adress);
							}	
							
							if("".equals(UI.fragmentSize.getText())){
								String fragment = JOptionPane.showInputDialog(UI.frame,"Enter fragment size",null);
								fragment_size = Integer.parseInt(fragment);
								UI.fragmentSize.setText(fragment);
							}	
							
							buffer = msg.getBytes();	
							int fragment_count = 1;
							
							if(buffer.length > 0){
								if(buffer.length > fragment_size){
									fragment_count = (int)Math.ceil((double)buffer.length/(double)(fragment_size-1));
									String [] messages = fragmentMessage(msg, fragment_count, fragment_size);
									for(int i=0; i<fragment_count; i++){
										buffer = messages[i].getBytes();
										DatagramPacket out = new DatagramPacket(buffer, buffer.length, hostAddress, 4587);
										try {
											socket.send(out);
										} catch (IOException e1) {
											e1.printStackTrace();
										}
									}
								}
								else{
									msg = "1" + msg;
									buffer = msg.getBytes();
									DatagramPacket out = new DatagramPacket(buffer, buffer.length, hostAddress, 4587);
									try {
										socket.send(out);
									} catch (IOException e1) {
										e1.printStackTrace();
									}
									msg = msg.substring(1, msg.length());
								}
								
								
								if(!"".equals(msg))
									msg = fmt.format(new Date()) + ": "+ msg + " (fragment count: " +fragment_count + " ) \n";
								
								UI.textPane.appendText(msg, "c");
								UI.textPane_down.setText("");
							}
						}
					}
			);
	  }
	public static String[] fragmentMessage(String msg, int f_c, int f_length){
		String [] messages = new String[f_c];
		for(int i=0; i<f_c; i++){
			for(int j=0; j<f_length; j++){
				if((i*(f_length-1) + j) == msg.length() + 1)
					break;
				if(j == 0){
					messages[i] = "0";
					if(i == f_c-1)
						messages[i] = "1";
				}
				else{
					messages[i] = messages[i] + msg.charAt(i*(f_length-1) + j - 1);
					
				}
			}
		}
		return messages;
	}
}
