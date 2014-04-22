package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ClientInfoStatus;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import work.Client;
import work.Server;
import javax.swing.JTextField;

public class UI extends JFrame {

	private JPanel contentPane;
	public static UI frame;
	public static JButton SendMessage;
	public static JTextPane textPane_down;
	public static AppendingTextPane textPane;
	public static JTextField ipAdress;
	public static JTextField fragmentSize;
	public static JLabel lblIpAddress;
	public static JLabel lblFragmentSize;
	public static Thread one;
	public static Thread two;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					/**
					 * set program theme*/
					try {
					    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
					        if ("Nimbus".equals(info.getName())) {
					            UIManager.setLookAndFeel(info.getClassName());
					            break;
					        }
					    }
					} catch (Exception e) {
						UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
					}

					/**create gui window*/
					frame = new UI();
					frame.setVisible(true);
					
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
	}

	/**
	 * Create the frame.
	 */
	public UI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1597, 951);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnProgram = new JMenu("Program");
		menuBar.add(mnProgram);
		mnProgram.setFont(new Font( "Dialog", Font.BOLD|Font.ITALIC, 24 ));
		
		JMenuItem mntmServer = new JMenuItem("Server");
		mnProgram.add(mntmServer);
		mntmServer.setFont(new Font( "Dialog", Font.BOLD|Font.ITALIC, 24 ));
		
		JMenuItem mntmClient = new JMenuItem("Client");
		mnProgram.add(mntmClient);
		mntmClient.setFont(new Font( "Dialog", Font.BOLD|Font.ITALIC, 24 ));
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		SendMessage = new JButton("Send Message");
		SendMessage.setFont(new Font("Tahoma", Font.PLAIN, 27));
		SendMessage.setVisible(false);
		
		JScrollPane scrollPane = new JScrollPane();
		final JScrollPane scrollPane_down = new JScrollPane();
		scrollPane_down.setVisible(false);
		
		textPane_down = new JTextPane();
		scrollPane_down.setViewportView(textPane_down);
		
		textPane = new AppendingTextPane();
		scrollPane.setViewportView(textPane);
		textPane.setEditable(false);
		textPane.setFont(new Font("Tahoma", Font.PLAIN, 24));
		textPane_down.setFont(new Font("Tahoma", Font.PLAIN, 24));
		
		lblIpAddress = new JLabel("IP Address:");
		lblIpAddress.setFont(new Font("Tahoma", Font.PLAIN, 24));
		
		
		ipAdress = new JTextField();
		ipAdress.setColumns(10);
		ipAdress.setFont(new Font("Tahoma", Font.PLAIN, 24));
		
		lblFragmentSize = new JLabel("Fragment size:");
		lblFragmentSize.setFont(new Font("Tahoma", Font.PLAIN, 24));
		
		fragmentSize = new JTextField();
		fragmentSize.setColumns(10);
		fragmentSize.setFont(new Font("Tahoma", Font.PLAIN, 24));
		
		ipAdress.setVisible(false);
		fragmentSize.setVisible(false);
		lblFragmentSize.setVisible(false);
		lblIpAddress.setVisible(false);
		
		
		/**
		 * run Server*/
		mntmServer.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{	
						if(one != null && one.isAlive())
							one.stop();
						frame.setTitle("Server");
						SendMessage.setVisible(false);
						scrollPane_down.setVisible(false);
						ipAdress.setVisible(false);
						fragmentSize.setVisible(false);
						lblFragmentSize.setVisible(false);
						lblIpAddress.setVisible(false);
						textPane.setText("");
						
						two = new Thread() {
						    public void run() {
						        try {
						            Server.runServer();
						        } catch (Exception e) {
									e.printStackTrace();
								}
						    }  
						};

						two.start();
					}
				}
		);
		
		/**
		 * run Client*/
		mntmClient.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{	
						if(two != null && two.isAlive())
							two.stop();
						frame.setTitle("Client");
						SendMessage.setVisible(true);
						textPane.setText("");
						textPane_down.setText("");
						scrollPane_down.setVisible(true);
						ipAdress.setVisible(true);
						fragmentSize.setVisible(true);
						lblFragmentSize.setVisible(true);
						lblIpAddress.setVisible(true);

						one = new Thread() {
						    public void run() {
						        try {
						            Client.runClient();
						        } catch (Exception e) {
									e.printStackTrace();
								}
						    }  
						};

						one.start();
					}
				}
		);
		

		/**
		 * Layout options*/
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 1519, Short.MAX_VALUE)
						.addComponent(scrollPane_down, GroupLayout.DEFAULT_SIZE, 1519, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(SendMessage, GroupLayout.PREFERRED_SIZE, 345, GroupLayout.PREFERRED_SIZE)
							.addGap(83)
							.addComponent(lblIpAddress, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(ipAdress, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
							.addGap(93)
							.addComponent(lblFragmentSize, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(fragmentSize, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(scrollPane_down, GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
					.addGap(14)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(SendMessage, GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(20)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(ipAdress, GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
								.addComponent(lblIpAddress, GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
								.addComponent(lblFragmentSize, GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
								.addComponent(fragmentSize, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))))
					.addGap(12))
		);
		
		contentPane.setLayout(gl_contentPane);
	}
}
