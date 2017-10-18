package parse;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import model.Player;

public class ParsePlayer {

	boolean inPlayers = false;
	boolean inPlayer = false;
	boolean inId = false;
	boolean inPosition = false;
	boolean inName = false;
	boolean inAge = false;

	Player currentPlayer;
	List<Player> currentPlayerList;

	public Player doParseBooks(String s) {
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser pullParser = factory.newPullParser();
			pullParser.setInput(new StringReader(s));
			processDocument(pullParser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return currentPlayer;
	}

	public void processDocument(XmlPullParser pullParser)
			throws XmlPullParserException, IOException {
		int eventType = pullParser.getEventType();
		do {
			if (eventType == XmlPullParser.START_DOCUMENT) {
			} else if (eventType == XmlPullParser.END_DOCUMENT) {
			} else if (eventType == XmlPullParser.START_TAG) {
				processStartElement(pullParser);
			} else if (eventType == XmlPullParser.END_TAG) {
				processEndElement(pullParser);
			} else if (eventType == XmlPullParser.TEXT) {
				processText(pullParser);
			}
			eventType = pullParser.next();
		} while (eventType != XmlPullParser.END_DOCUMENT);
	}

	public void processStartElement(XmlPullParser event) {
		String name = event.getName();
		if (name.equals("players")) {
			inPlayer = true;
			currentPlayer = new Player();
		} else if (name.equals("player")) {
			inPlayer = true;
			currentPlayer = new Player();
		} else if (name.equals("id")) {
			inId = true;
		} else if (name.equals("position")) {
			inPosition = true;
		} else if (name.equals("name")) {
			inName = true;
		} else if (name.equals("age")) {
			inAge = true;
		}
	}

	public void processText(XmlPullParser event) throws XmlPullParserException {
		if (inId) {
			String s = event.getText();
			currentPlayer.setId(Integer.parseInt(s));
		}
		if (inPosition) {
			String s = event.getText();
			currentPlayer.setPosition(s);
		}
		if (inName) {
			String s = event.getText();
			currentPlayer.setName(s);
		}
		if (inAge) {
			String s = event.getText();
			currentPlayer.setAge(Integer.parseInt(s));
		}
	}

	public void processEndElement(XmlPullParser event) {
		String name = event.getName();

		if (name.equals("players")) {
			inPlayers = false;
			currentPlayerList.add(currentPlayer);
		} else if (name.equals("id")) {
			inId = false;
		} else if (name.equals("position")) {
			inPosition = false;
		} else if (name.equals("name")) {
			inName = false;
		} else if (name.equals("age")) {
			inAge = false;
		}
	}
}