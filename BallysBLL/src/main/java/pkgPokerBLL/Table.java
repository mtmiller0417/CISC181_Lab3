package pkgPokerBLL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Table {

	private UUID TableID;
	
	//	Change this from ArrayList to HashMap.
	//private ArrayList<Player> TablePlayers = new ArrayList<Player>();
	private HashMap<UUID, Player> TablePlayers = new HashMap<UUID, Player>();
	
	public Table() {
		super();
		TableID = UUID.randomUUID();
	}
	
	public Table AddPlayerToTable(Player p)
	{
		//TODO: Add a player to the table (should be pretty easy)	
		TablePlayers.put(p.getPlayerID(), p);
		return this;
	}
	
	public Table RemovePlayerFromTable(Player p)
	{
		//TODO: Remove a player from the table
		TablePlayers.remove(p.getPlayerID());
		return this;
	}
}
