package pkgPokerBLL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import pkgPokerEnum.eRank;
import pkgPokerEnum.eSuit;

public class Deck {

	private UUID DeckID;
	private ArrayList<Card> DeckCards = new ArrayList<Card>();

	public Deck() {

		super();
		int iCardNbr = 0;
		for (eSuit suit : eSuit.values()) {
			for (eRank rank : eRank.values()) {
				if ((suit != eSuit.JOKER) && (rank != eRank.JOKER)) {
					DeckCards.add(new Card(rank, suit, ++iCardNbr));
				}
			}
		}
		Collections.shuffle(DeckCards);
	}
	
	public Deck(int jokers)
	{
		this();
		for(int x = 1; x <= jokers; x++)
		{
			DeckCards.add(new Card(eRank.JOKER, eSuit.JOKER, 100));
			DeckCards.get(-1).setWild(DeckCards.get(-1)); //could be weird
		}	
//		for(Card c: DeckCards)
//		{
//			if(c.geteRank() == eRank.JOKER || c.geteSuit() == eSuit.JOKER)
//			{
//				c.setWild(c);
//			}
//		}
		Collections.shuffle(DeckCards);
	}

	public Card DrawCard() 
	{

		return DeckCards.remove(0);
	}
	
	public ArrayList<Card> getDeckCards()
	{
		return DeckCards;
	}
}
