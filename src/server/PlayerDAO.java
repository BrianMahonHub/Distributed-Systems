package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Player;

public enum PlayerDAO {
	instance;

	private Map<Integer, Player> playersMap = new HashMap<Integer, Player>();

	private PlayerDAO() {

		Player player1 = new Player();
		player1.setId(1);
		player1.setName("Karl Darlow");
		player1.setPosition("Goalkeeper");
		player1.setAge(26);
		playersMap.put(1, player1);

		Player player2 = new Player();
		player2.setId(2);
		player2.setName("Chancel Mbemba");
		player2.setPosition("Centre-Back");
		player2.setAge(22);
		playersMap.put(2, player2);

		Player player3 = new Player();
		player3.setId(3);
		player3.setName("Ciaran Clark");
		player3.setPosition("Centre-Back");
		player3.setAge(27);
		playersMap.put(3, player3);
		
		Player player4 = new Player();
		player4.setId(4);
		player4.setName("Achraf Lazaar");
		player4.setPosition("Left-Back");
		player4.setAge(25);
		playersMap.put(4, player4);
		
		Player player5 = new Player();
		player5.setId(5);
		player5.setName("DeAndre Yedlin");
		player5.setPosition("Right-Back");
		player5.setAge(23);
		playersMap.put(5, player5);
		
		Player player6 = new Player();
		player6.setId(6);
		player6.setName("Vernon Anita");
		player6.setPosition("Defensive-Midfield");
		player6.setAge(27);
		playersMap.put(6, player6);
		
		Player player7 = new Player();
		player7.setId(7);
		player7.setName("Jonjo Shelvey");
		player7.setPosition("Central Midfield");
		player7.setAge(24);
		playersMap.put(7, player7);
		
		Player player8 = new Player();
		player8.setId(8);
		player8.setName("Matt Ritchie");
		player8.setPosition("Right Midfield");
		player8.setAge(27);
		playersMap.put(8, player8);
		
		Player player9 = new Player();
		player9.setId(9);
		player9.setName("Yoan Gouffran");
		player9.setPosition("Left Wing");
		player9.setAge(30);
		playersMap.put(9, player9);
		
		Player player10 = new Player();
		player10.setId(10);
		player10.setName("Dwight Gayle");
		player10.setPosition("Centre Forward");
		player10.setAge(26);
		playersMap.put(10, player10);
		
		Player player11 = new Player();
		player11.setId(11);
		player11.setName("Daryl Murphy");
		player11.setPosition("Centre Forward");
		player11.setAge(33);
		playersMap.put(11, player11);
	}

	public List<Player> getPlayers() {
		List<Player> players = new ArrayList<Player>();
		players.addAll(playersMap.values());
		return players;
	}

	public Player getPlayer(int id) {
		return playersMap.get(id);
	}

	public void create(Player player) {
		playersMap.put(player.getId(), player);

	}

	public void delete(int id) {
		if (playersMap.remove(id) != null) {
			System.out.println("Removed");
		} else {
			System.out.println("Not Removed");
		}
	}
}