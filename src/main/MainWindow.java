package main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class MainWindow extends JFrame implements ActionListener {
	private JMenuItem exitItem;
	private Toolkit t;
	private Dimension s;
	
	public MainWindow() {
		super("Distributed Systems");

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		exitItem = new JMenuItem("Exit");

		fileMenu.add(exitItem);
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);

		exitItem.addActionListener(this);
		
		Home aWindowContent = new Home("Players Database");
		getContentPane().add(aWindowContent);
		
		t = getToolkit();
		s = t.getScreenSize();
		setLocation(s.width/4-getWidth()/2,s.height/4-getHeight()/2);

		setSize(440, 360);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(exitItem)) {
			this.dispose();
		}
	}
}