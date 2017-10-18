package parse;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URI;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import model.Player;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

@SuppressWarnings("serial")
public class DisplayGui extends JFrame {
	/**
	 * The text area which is used for displaying logging information.
	 */
	private JTextArea textArea;

	private JButton buttonStart = new JButton("Display All");
	private JButton buttonSearch = new JButton("Search by Id:");
	private JButton buttonClear = new JButton("Clear List");
	private TextField IDTF = new TextField("");

//	private PrintStream standardOut;

	public DisplayGui() {
		super("Display all or search by shirt number");

		textArea = new JTextArea(50, 10);
		textArea.setEditable(false);
		PrintStream printStream = new PrintStream(new PrintToGui(textArea));

//		standardOut = System.out;

		System.setOut(printStream);
		System.setErr(printStream);

		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.anchor = GridBagConstraints.WEST;

		add(buttonStart, constraints);
		constraints.gridx = 1;
		
		add(buttonClear, constraints);
		
		constraints.gridx = 2;
		add(buttonSearch, constraints);
		
		constraints.gridx = 3;
		add(IDTF, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 4;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;

		add(new JScrollPane(textArea), constraints);

		buttonStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				try {
					CloseableHttpResponse response = null;
					try {
						URI uri = new URIBuilder().setScheme("http")
								.setHost("localhost").setPort(8080)
								.setPath("/A00211953_BrianM/rest/players").build();


						HttpGet httpGet = new HttpGet(uri);
						httpGet.setHeader("Accept", "application/xml");
						CloseableHttpClient httpClient = HttpClients.createDefault();
						response = httpClient.execute(httpGet);

						HttpEntity entity = response.getEntity();
						String text = getASCIIContentFromEntity(entity);
						List<Player> bookList = new ParsePlayers().doParseBooks(text);
						for (Player book : bookList) {
							System.out.println(" "+book.getId()+"	" + book.getName() + ",");
							System.out.println(" 	" + book.getPosition() + ",");
							System.out.println(" 	Age: " + book.getAge() + ".");
							System.out.println("----------------------------------------------------------------------------------------------------------");
						}
					} finally {
						response.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			String getASCIIContentFromEntity(HttpEntity entity)
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

		});

		buttonSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				try {
					CloseableHttpResponse response = null;
					try {
						URI uri = new URIBuilder().setScheme("http")
								.setHost("localhost").setPort(8080)
								.setPath("/A00211953_BrianM/rest/players/"+IDTF.getText()).build();

						HttpGet httpGet = new HttpGet(uri);
						httpGet.setHeader("Accept", "application/xml");
						CloseableHttpClient httpClient = HttpClients.createDefault();
						response = httpClient.execute(httpGet);

						HttpEntity entity = response.getEntity();
						String text = getASCIIContentFromEntity(entity);
						Player book = new ParsePlayer().doParseBooks(text);
						{
							System.out.println(" "+book.getId()+"	" + book.getName() + ",");
							System.out.println(" 	" + book.getPosition() + ",");
							System.out.println(" 	Age: " + book.getAge() + ".");
							System.out.println("----------------------------------------------------------------------------------------------------------");
						}
					} finally {
						response.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			String getASCIIContentFromEntity(HttpEntity entity)
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

		});

		
		buttonClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				try {
					textArea.getDocument().remove(0,
							textArea.getDocument().getLength());
				} catch (BadLocationException ex) {
					ex.printStackTrace();
				}
			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(480, 320);
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new DisplayGui().setVisible(true);
			}
		});
	}
}