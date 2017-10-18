package main;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import parse.DisplayGui;

@SuppressWarnings("serial")
public class Home extends JInternalFrame implements ActionListener {
	
	public JFrame frame;
	String cmd = null;
	
	private Container content;

	private JPanel PlayerDetailsPanel;
	private JLabel NameLabel = new JLabel("Name:               ");
	private JLabel PositionLabel = new JLabel("Position:      ");
	private JLabel AgeLabel = new JLabel("Age:        ");

	private JTextField IDTF = new JTextField(10);
	private JTextField NameTF = new JTextField(10);
	private JTextField PositionTF = new JTextField(10);
	private JTextField AgeTF = new JTextField(10);

	private JButton updateButton = new JButton("Update");
	private JButton insertButton = new JButton("Insert");
	private JButton deleteButton = new JButton("Delete");
	private JButton clearButton = new JButton("Clear");
	private JButton displayButton = new JButton("Search for Players");


	public Home(String aTitle) {

		super(aTitle, false, false, false, false);
		setEnabled(true);

		content = getContentPane();
		content.setLayout(null);
		content.setBackground(UIManager
				.getColor("RadioButtonMenuItem.selectionBackground"));
		BorderFactory.createEtchedBorder(15, Color.white, Color.black);

		PlayerDetailsPanel = new JPanel();
		PlayerDetailsPanel.setBackground(UIManager
				.getColor("RadioButtonMenuItem.selectionBackground"));
		PlayerDetailsPanel.setBorder(new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, new Color(255, 255, 255)),
				"CRUD Actions", TitledBorder.LEADING,
				TitledBorder.ABOVE_BOTTOM, null, Color.WHITE));
		PlayerDetailsPanel.setLayout(null);
		IDTF.setBounds(156, 17, 225, 25);
		PlayerDetailsPanel.add(IDTF);
		NameLabel.setBounds(48, 42, 106, 25);
		PlayerDetailsPanel.add(NameLabel);
		NameTF.setBounds(156, 42, 225, 25);
		PlayerDetailsPanel.add(NameTF);
		PositionLabel.setBounds(48, 67, 106, 25);
		PlayerDetailsPanel.add(PositionLabel);
		PositionTF.setBounds(156, 67, 225, 25);
		PlayerDetailsPanel.add(PositionTF);
		AgeLabel.setBounds(48, 92, 106, 25);
		PlayerDetailsPanel.add(AgeLabel);
		AgeTF.setBounds(156, 92, 225, 25);
		PlayerDetailsPanel.add(AgeTF);
		;

		PlayerDetailsPanel.setSize(399, 255);
		PlayerDetailsPanel.setLocation(5, 5);

		content.add(PlayerDetailsPanel);
		insertButton.setBounds(156, 128, 106, 30);
		PlayerDetailsPanel.add(insertButton);
		deleteButton.setBounds(276, 169, 105, 30);
		PlayerDetailsPanel.add(deleteButton);
		updateButton.setBounds(276, 128, 105, 30);
		PlayerDetailsPanel.add(updateButton);
		clearButton.setBounds(156, 169, 106, 30);
		PlayerDetailsPanel.add(clearButton);
		displayButton.setBounds(156, 210, 225, 30);
		PlayerDetailsPanel.add(displayButton);
		
		JLabel lblId = new JLabel("Id:");
		lblId.setBounds(48, 17, 106, 25);
		PlayerDetailsPanel.add(lblId);

		clearButton.addActionListener(this);
		updateButton.addActionListener(this);
		deleteButton.addActionListener(this);
		insertButton.addActionListener(this);
		displayButton.addActionListener(this);
		
	
		setSize(430, 300);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		Object target = e.getSource();

		
		if (target == insertButton) {
			try {	
				URI uri = new URIBuilder().setScheme("http")
						.setHost("localhost").setPort(8080)
						.setPath("/A00211953_BrianM/rest/players").build();

				System.out.println(uri.toString());
				HttpPost httpPost = new HttpPost(uri);
				httpPost.setHeader("Accept", "text/html");

				CloseableHttpClient httpClient = HttpClients.createDefault();
				
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
				nameValuePairs.add(new BasicNameValuePair("id", IDTF.getText()));
				nameValuePairs.add(new BasicNameValuePair("name", NameTF.getText()));
				nameValuePairs.add(new BasicNameValuePair("position", PositionTF.getText()));
				nameValuePairs.add(new BasicNameValuePair("age", AgeTF.getText()));
				              
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs)) ;
				System.out.println("Sending request");
				@SuppressWarnings("unused")
				CloseableHttpResponse response = httpClient.execute(httpPost);
					
				Class.forName("org.hsqldb.jdbcDriver");
				Connection con = DriverManager.getConnection(
				"jdbc:hsqldb:hsql://localhost/oneDB", "sa", "");
				Statement stmt =con.createStatement();
				
				stmt.executeUpdate("insert into players (name, role, age)" + "values ('"  
				+ NameTF.getText() + "','" + PositionTF.getText() + "', " 
				+ Integer.parseInt(AgeTF.getText()) + ");");
				
				stmt.close();
				con.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
		}
		

		if (target == updateButton) {
			try {
				URI uri = new URIBuilder().setScheme("http")
						.setHost("localhost").setPort(8080)
						.setPath("/A00211953_BrianM/rest/players/"+IDTF.getText()).build();

				System.out.println(uri.toString());
				HttpPut httpPut = new HttpPut(uri);
				httpPut.setHeader("Accept", "text/html");

				CloseableHttpClient httpClient = HttpClients.createDefault();
				
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
				nameValuePairs.add(new BasicNameValuePair("id", IDTF.getText()));
				nameValuePairs.add(new BasicNameValuePair("name", NameTF.getText()));
				nameValuePairs.add(new BasicNameValuePair("position", PositionTF.getText()));
				nameValuePairs.add(new BasicNameValuePair("age", AgeTF.getText()));
				              
				httpPut.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				System.out.println("Sending request");
				@SuppressWarnings("unused")
				CloseableHttpResponse response = httpClient.execute(httpPut);
				
				Class.forName("org.hsqldb.jdbcDriver");
				Connection con = DriverManager.getConnection(
				"jdbc:hsqldb:hsql://localhost/oneDB", "sa", "");
				
				Statement stmt =con.createStatement();			
				stmt.executeUpdate("UPDATE players SET name = '"
						+ NameTF.getText() + "', role = '"
						+ PositionTF.getText() + "', age = " + 
						Integer.parseInt(AgeTF.getText())  + 
						" where id = " + IDTF.getText() + ")");
	
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
		}
		

		if (target == clearButton) {
			IDTF.setText("");
			NameTF.setText("");
			PositionTF.setText("");
			AgeTF.setText("");		
		}
		
		
		if (target == deleteButton) {
			try {
				URI uri = new URIBuilder().setScheme("http")
						.setHost("localhost").setPort(8080)
						.setPath("/A00211953_BrianM/rest/players/"+IDTF.getText()).build();

				System.out.println(uri.toString());
				HttpDelete httpDelete = new HttpDelete(uri);
				httpDelete.setHeader("Accept", "text/html");

				CloseableHttpClient httpClient = HttpClients.createDefault();
				System.out.println("Sending Delete request");
				@SuppressWarnings("unused")
				CloseableHttpResponse response = httpClient.execute(httpDelete);

				
				Class.forName("org.hsqldb.jdbcDriver");
				Connection con = DriverManager.getConnection(
				"jdbc:hsqldb:hsql://localhost/oneDB", "sa", "");
				
				Statement stmt =con.createStatement();			
				stmt.executeUpdate("Delete from players where id = " + IDTF.getText() + ")");
	
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
		}
		
		if (target == displayButton) {
			DisplayGui frame = new DisplayGui();
			frame.setVisible(true);
		     this.dispose();
			
	}
}		
	
		// streams data into a StringBuffer
		static String getASCIIContentFromEntity(HttpEntity entity)
				throws IllegalStateException, IOException {
			InputStream in = entity.getContent();
			StringBuffer out = new StringBuffer();
			int n = 1;
			while (n > 0) {
				byte[] b = new byte[4096];
				n = in.read(b);
				if (n > 0)
					out.append(new String(b, 0, n));
			}
			return out.toString();
		}
	}