package pkgPokerBLL;

import java.lang.reflect.InvocationTargetException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;
import pkgException.TieException;
import pkgPokerEnum.eCardNo;
import pkgPokerEnum.eHandStrength;
import pkgPokerEnum.eRank;
import pkgPokerEnum.eSuit;
 

public class Hand {

	private UUID HandID;
	private boolean bIsScored;
	private HandScore HS;
	private ArrayList<Card> CardsInHand = new ArrayList<Card>();

	public Hand() {

	}

	public void AddCardToHand(Card c) {
		CardsInHand.add(c);
	}

	public ArrayList<Card> getCardsInHand() {
		return CardsInHand;
	}

	public HandScore getHandScore() {
		return HS;
	}

	public void AddToCardsInHand(Card c) {
		CardsInHand.add(c);
	}

	
	public Hand EvaluateHand() {

		Hand h = null;

		ArrayList<Hand> ExplodedHands = ExplodeHands(this);
		
		for (Hand hand : ExplodedHands) {
			hand = Hand.EvaluateHand(hand);
		}
		System.out.println("Size: " + ExplodedHands.size());
		
		//	Figure out best hand
		Collections.sort(ExplodedHands, Hand.HandRank);
		System.out.println("After ExplodedHands");
		
		//	Return best hand.  
		//	TODO: Fix...  what to do if there is a tie?
		//   Return the first one bc it doesnt matter? 
		System.out.println("HandScore: "+ExplodedHands.get(0).getHandScore().getHandStrength().toString());
		return ExplodedHands.get(0);
	}

	
	//TODO: one hand is passed in, 1, 52, 2704, etc are passed back
	//		No jokers, 'ReturnHands' should have one hand
	//		One Wild/joker 'ReturnHands' should have 52 hands, etc	
	//mess with this?
	static int counter = 0;
	static int counter2 = 0;
	static int counter3 = 0;
	static int counter4 = 0;
	static int counter5 = 0;

	public static ArrayList<Hand> ExplodeHands(Hand h) {
		// J J J J J
		ArrayList<Hand> ReturnHands = new ArrayList<Hand>();
		ArrayList<Hand> FinalHands = new ArrayList<Hand>();
		int numJokers = 0;		
		Deck deck = new Deck();
		Collections.sort(h.getCardsInHand());
		
		for(Card c: h.getCardsInHand())
		{
			if(c.geteRank().getiRankNbr() == eRank.JOKER.getiRankNbr())
			{
				numJokers++;
			}
		}
		
		if(numJokers == 0)
		{
			FinalHands.add(h);
		}
		else if(numJokers == 1)
		{
			for(Card c: deck.getDeckCards())
			{
				Hand h1 = h;
				h1.getCardsInHand().set(0, c);
				//ReturnHands.add(h);
				ReturnHands.add(h1);
				//return ReturnHands;
			}	
			System.out.println("RH : " + ReturnHands.size());
			return ReturnHands;
			//System.out.println(ReturnHands.size()); //checking to make sure number of cards is right
		}
		else if(numJokers == 2)
		{
			
			for(Card c: deck.getDeckCards())
			{
				Hand h1 = h;
				h1.getCardsInHand().set(0, c);
				ReturnHands.add(h);
			}
			// 2 J 5 4 3
			ArrayList<Hand> newHands = expansion(1, deck, ReturnHands);
			FinalHands = newHands;
			//System.out.println(FinalHands.size()); checking to make sure number of cards is right
		}
		else if(numJokers == 3)
		{
			for(Card c: deck.getDeckCards())
			{
				Hand h1 = h;
				h1.getCardsInHand().set(0, c);
				ReturnHands.add(h);
			}
			// 2 J 5 4 3
			ArrayList<Hand> newHands = expansion(1, deck, ReturnHands);
			ArrayList<Hand> newHands1 = expansion(2, deck, newHands);
			FinalHands = newHands1;
			//System.out.println(FinalHands.size()); checking to make sure number of cards is right
		}
		else if(numJokers == 4)
		{
			for(Card c: deck.getDeckCards())
			{
				Hand h1 = h;
				h1.getCardsInHand().set(0, c);
				ReturnHands.add(h);
			}
			// 2 J 5 4 3
			ArrayList<Hand> newHands = expansion(1, deck, ReturnHands);
			ArrayList<Hand> newHands1 = expansion(2, deck, newHands);
			ArrayList<Hand> newHands2 = expansion(3, deck, newHands1);
			FinalHands = newHands2;
			//System.out.println(FinalHands.size()); checking to make sure number of cards is right
			
		}
		else if(numJokers == 5)
		{
			for(Card c: deck.getDeckCards())
			{
				Hand h1 = h;
				h1.getCardsInHand().set(0, c);
				ReturnHands.add(h);
			}
			// 2 J 5 4 3
			ArrayList<Hand> newHands = expansion(1, deck, ReturnHands);
			ArrayList<Hand> newHands1 = expansion(2, deck, newHands);
			ArrayList<Hand> newHands2 = expansion(3, deck, newHands1);
			ArrayList<Hand> newHands3 = expansion(4, deck, newHands2);      
			FinalHands = newHands3;
			//System.out.println(FinalHands.size()); checking to make sure number of cards is right
		}
		
		
		//System.out.println("FinalHands Size: " + FinalHands.size());
		return FinalHands;
	}
	
	
	public  static ArrayList<Hand> expansion(int index, Deck deck, ArrayList<Hand> ReturnHands)
	{
		ArrayList<Hand> newHands = new ArrayList<Hand>();
		Deck d = new Deck();
		for(Hand h2 : ReturnHands)
		{
			for(Card c: d.getDeckCards())
			{
				Hand h3 = h2;//pass in hand and index
				h3.getCardsInHand().set(index, c);
				newHands.add(h3);
			}
		}
		return newHands;
	}
//***********************************************************************************************************
	public static void main(String args [])
	{
		Hand h = new Hand();
		h.AddCardToHand(new Card(eRank.FIVE, eSuit.SPADES,5));
		h.AddCardToHand(new Card(eRank.FOUR, eSuit.CLUBS,4));
		h.AddCardToHand(new Card(eRank.THREE, eSuit.CLUBS,3));
		h.AddCardToHand(new Card(eRank.TWO, eSuit.CLUBS,2));
		h.AddCardToHand(new Card(eRank.JOKER, eSuit.JOKER,1));
		
		Collections.sort(h.getCardsInHand());
		ExplodeHands(h);
		
		
		//Collections.sort(h.getCardsInHand());
		String testt = h.getCardsInHand().get(0).geteRank().toString();
		String testt2 = h.getCardsInHand().get(1).geteRank().toString();
		String testt3 = h.getCardsInHand().get(2).geteRank().toString();
		String testt4 = h.getCardsInHand().get(3).geteRank().toString();
		String testt5 = h.getCardsInHand().get(4).geteRank().toString();
		String testtSuit = h.getCardsInHand().get(0).geteSuit().toString();
		String testtSuit2 = h.getCardsInHand().get(1).geteSuit().toString();
		String testtSuit3 = h.getCardsInHand().get(2).geteSuit().toString();
		String testtSuit4 = h.getCardsInHand().get(3).geteSuit().toString();
		String testtSuit5 = h.getCardsInHand().get(4).geteSuit().toString();
		System.out.println("MAIN: "+ testt + "/" + testtSuit + ", " +testt2+ "/" + testtSuit2 + ", " +testt3+ "/" + testtSuit3 + ", " +testt4+ "/" + testtSuit4 + ", " +testt5+ "/" + testtSuit5 );
		Hand hh = h.EvaluateHand();
		//hh.get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr()) - 1 == (cards.get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNbr()
		int x = hh.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr();
		System.out.println(hh.getHandScore().getHandStrength().toString());
	}

//***********************************************************************************************************
	
	//Check for best hand, if two are tied throw an exception
	public static Hand PickBestHand(ArrayList<Hand> Hands)throws TieException
	{
		final int FIRSTHAND = 0;
		final int SECONDHAND = 1;
		//Sort the Hands by HandStrength, highest is first
		Collections.sort(Hands, Hand.HandRank);
		// If the first Hand is equal to the second, there is a tie
		if(Hands.get(FIRSTHAND).equals(Hands.get(SECONDHAND)))
		{
			//Throw Exception when if a tie
			throw new TieException();
		}
		else
		{
			//Otherwise return the first card as the best
			return Hands.get(FIRSTHAND);
		}
		
	}
	

	private static Hand EvaluateHand(Hand h) {

		Collections.sort(h.getCardsInHand());

		// Another way to sort
		// Collections.sort(h.getCardsInHand(), Card.CardRank);

		HandScore hs = new HandScore();
		try {
			Class<?> c = Class.forName("pkgPokerBLL.Hand");

			for (eHandStrength hstr : eHandStrength.values()) {
				Class[] cArg = new Class[2];
				cArg[0] = pkgPokerBLL.Hand.class;
				cArg[1] = pkgPokerBLL.HandScore.class;

				Method meth = c.getMethod(hstr.getEvalMethod(), cArg);
				Object o = meth.invoke(null, new Object[] { h, hs });

				// If o = true, that means the hand evaluated- skip the rest of
				// the evaluations
				if ((Boolean) o) {
					break;
				}
			}

			h.bIsScored = true;
			h.HS = hs;

		} catch (ClassNotFoundException x) {
			x.printStackTrace();
		} catch (IllegalAccessException x) {
			x.printStackTrace();
		} catch (NoSuchMethodException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return h;
	}

	public static boolean isHandFiveOfAKind(Hand h, HandScore hs)
	{
		boolean isFiveOfAKind = false;
		//Assumes cards are in order by rank before.
		if(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank()
				.equals(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).geteRank()))
		{
			isFiveOfAKind = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			hs.setHandStrength(eHandStrength.FiveOfAKind);
		}
		
		return isFiveOfAKind;
	}
	
	public static boolean isStraight(ArrayList<Card> cards)// Helper Method
	{
		boolean isStraight = false;
	boolean isAce = isAce(cards);
		
		
		if ((cards.get(eCardNo.SecondCard.getCardNo()).geteRank() == eRank.FIVE) &&
				(cards.get(eCardNo.ThirdCard.getCardNo()).geteRank() == eRank.FOUR) &&
				(cards.get(eCardNo.FourthCard.getCardNo()).geteRank() == eRank.THREE) &&
				(cards.get(eCardNo.FifthCard.getCardNo()).geteRank() == eRank.TWO)&&
				(cards.get(eCardNo.FirstCard.getCardNo()).geteRank() == eRank.ACE))
		{
			isStraight = true;		
		}
		else if((cards.get(eCardNo.FirstCard.getCardNo()).geteRank() == eRank.ACE) &&
				(cards.get(eCardNo.SecondCard.getCardNo()).geteRank() == eRank.KING) &&
				(cards.get(eCardNo.ThirdCard.getCardNo()).geteRank() == eRank.QUEEN) &&
				(cards.get(eCardNo.FourthCard.getCardNo()).geteRank() == eRank.JACK)&&
				(cards.get(eCardNo.FifthCard.getCardNo()).geteRank() == eRank.TEN))
		{
			isStraight = true;
		}
			
		else if((cards.get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr()) - 1 == (cards.get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNbr()) &&
				(cards.get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNbr()) - 1 == (cards.get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNbr()) &&
				(cards.get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNbr()) - 1 == (cards.get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNbr()) &&
				(cards.get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNbr()) - 1 == (cards.get(eCardNo.FifthCard.getCardNo()).geteRank().getiRankNbr()) &&
				isAce != true)
		{
			isStraight = true;
		}	
		
		
		return isStraight;
		
	}

	public static boolean isAce(ArrayList<Card> cards)//Helper Method
	{
		//returns true if the first card in the hand is an ace
		if (cards.get(eCardNo.FirstCard.getCardNo()).geteRank() == eRank.ACE)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	

	public static boolean isFlush(ArrayList<Card> cards) {
		boolean isFlush = false;

		int iCount = 0;
		for (eSuit Suit : eSuit.values()) {
			iCount = 0;
			for (Card c : cards) {
				if (c.geteSuit() == Suit)
					iCount++;
			}

			if (iCount == 5) {
				isFlush = true;
				break;
			}
			if (iCount > 0)
				break;

		}

		return isFlush;

	}

	public static boolean isHandRoyalFlush(Hand h, HandScore hs) {

		boolean isHandRoyalFlush = false;
		Card c = new Card();

		if ((Hand.isFlush(h.getCardsInHand())) && (Hand.isStraight(h.getCardsInHand()))
				&& (Hand.isAce(h.getCardsInHand()))) {
			hs.setHandStrength(eHandStrength.RoyalFlush);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			hs.setLoHand(null);
			isHandRoyalFlush = true;
		}

		return isHandRoyalFlush;
	}

	public static boolean isHandStraightFlush(Hand h, HandScore hs) {

		boolean isHandStraightFlush = false;
		Card c = new Card();
		if ((Hand.isFlush(h.getCardsInHand())) && (Hand.isStraight(h.getCardsInHand()))) {
			hs.setHandStrength(eHandStrength.StraightFlush);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			hs.setLoHand(null);
			isHandStraightFlush = true;
		}

		return isHandStraightFlush;

	}

	// TODO: Implement This Method
	public static boolean isHandFourOfAKind(Hand h, HandScore hs) {

		boolean isHandFourOfAKind = false;

		ArrayList<Card> kickers = new ArrayList<Card>();
		if (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FourthCard.getCardNo()).geteRank()) {
			isHandFourOfAKind = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
		} else if (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FifthCard.getCardNo()).geteRank()) {
			isHandFourOfAKind = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank());
			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
		}

		if (isHandFourOfAKind) {
			hs.setHandStrength(eHandStrength.FourOfAKind);
			hs.setLoHand(null);
			hs.setKickers(kickers);
		}

		return isHandFourOfAKind;
	}

	public static boolean isHandFlush(Hand h, HandScore hs) {

		boolean bIsFlush = false;
		if (isFlush(h.getCardsInHand())) {
			hs.setHandStrength(eHandStrength.Flush);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			hs.setLoHand(null);

			hs.getKickers().add(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()));
			hs.getKickers().add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));
			hs.getKickers().add(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()));
			hs.getKickers().add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));

			bIsFlush = true;
		}

		return bIsFlush;
	}

	//TODO: Implement This Method
		public static boolean isHandStraight(Hand h, HandScore hs)
		{
			boolean isHandStraight = false;
			boolean isAce = isAce(h.getCardsInHand());
			
			if( (isStraight(h.getCardsInHand()) == true) && !isAce)
			{
				isHandStraight = true;	
				hs.setHandStrength(eHandStrength.Straight);
				hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());// Only a high hand for straight b/c it requires all 5 cards
				hs.setLoHand(null);
			}
			else if(isAce && isStraight(h.getCardsInHand()))
			{
				
				isHandStraight = true;	
				hs.setHandStrength(eHandStrength.Straight);
				hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank());// Only a high hand for straight b/c it requires all 5 cards
				hs.setLoHand(null);
			
			}
			else
			{
				isHandStraight = false;
			}
			return isHandStraight;
		}

	public static boolean isHandThreeOfAKind(Hand h, HandScore hs) {

		boolean isThreeOfAKind = false;
		ArrayList<Card> kickers = new ArrayList<Card>();
		if (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank()) {
			isThreeOfAKind = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			kickers.add(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
		} else if (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FourthCard.getCardNo()).geteRank()) {
			isThreeOfAKind = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank());
			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));

		} else if (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FifthCard.getCardNo()).geteRank()) {
			isThreeOfAKind = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank());
			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()));

		}

		if (isThreeOfAKind) {
			hs.setHandStrength(eHandStrength.ThreeOfAKind);
			hs.setLoHand(null);
			hs.setKickers(kickers);
		}

		return isThreeOfAKind;
	}

	public static boolean isHandTwoPair(Hand h, HandScore hs) {

		boolean isHandTwoPair = false;

		ArrayList<Card> kickers = new ArrayList<Card>();
		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FourthCard.getCardNo()).geteRank())) {
			isHandTwoPair = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank());
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));

		} else if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isHandTwoPair = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank());
			kickers.add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));

		} else if ((h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isHandTwoPair = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank());
			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));

		}

		if (isHandTwoPair) {
			hs.setHandStrength(eHandStrength.TwoPair);
			hs.setKickers(kickers);
		}
		return isHandTwoPair;

	}

	public static boolean isHandPair(Hand h, HandScore hs) {

		boolean isHandPair = false;

		ArrayList<Card> kickers = new ArrayList<Card>();
		if (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank()) {
			isHandPair = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			kickers.add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
		} else if (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank()) {
			isHandPair = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank());
			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
		} else if (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FourthCard.getCardNo()).geteRank()) {
			isHandPair = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank());
			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
		} else if (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FifthCard.getCardNo()).geteRank()) {
			isHandPair = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank());
			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));
		}

		if (isHandPair) {
			hs.setHandStrength(eHandStrength.Pair);
			hs.setLoHand(null);
			hs.setKickers(kickers);
		}

		return isHandPair;

	}

	public static boolean isHandHighCard(Hand h, HandScore hs) {

		hs.setHandStrength(eHandStrength.HighCard);
		hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
		hs.getKickers().add(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()));
		hs.getKickers().add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));
		hs.getKickers().add(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()));
		hs.getKickers().add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
		hs.setLoHand(null);
		return true;
	}

	public static boolean isAcesAndEights(Hand h, HandScore hs) {

		boolean isAcesAndEights = false;
		if (Hand.isHandTwoPair(h, hs) == true) {
			if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == eRank.ACE)
					&& (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == eRank.EIGHT)) {
				hs.setHandStrength(eHandStrength.AcesAndEights);
				isAcesAndEights = true;
			}
		}

		return isAcesAndEights;
	}

	public static boolean isHandFullHouse(Hand h, HandScore hs) {

		boolean isFullHouse = false;

		ArrayList<Card> kickers = new ArrayList<Card>();
		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isFullHouse = true;

			hs.setHandStrength(eHandStrength.FullHouse);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank());

		} else if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isFullHouse = true;
			hs.setHandStrength(eHandStrength.FullHouse);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
		}

		return isFullHouse;

	}

	public static Comparator<Hand> HandRank = new Comparator<Hand>() {

		public int compare(Hand h1, Hand h2) {

			int result = 0;

			result = h2.getHandScore().getHandStrength().getHandStrength()
					- h1.getHandScore().getHandStrength().getHandStrength();

			if (result != 0) {
				return result;
			}

			result = h2.getHandScore().getHiHand().getiRankNbr() - h1.getHandScore().getHiHand().getiRankNbr();
			if (result != 0) {
				return result;
			}

			if ((h2.getHandScore().getLoHand() != null) && (h1.getHandScore().getLoHand() != null)) {
				result = h2.getHandScore().getLoHand().getiRankNbr() - h1.getHandScore().getLoHand().getiRankNbr();
			}

			if (result != 0) {
				return result;
			}

			if (h2.getHandScore().getKickers().size() > 0) {
				if (h1.getHandScore().getKickers().size() > 0) {
					result = h2.getHandScore().getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr()
							- h1.getHandScore().getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank()
									.getiRankNbr();
				}
				if (result != 0) {
					return result;
				}
			}

			if (h2.getHandScore().getKickers().size() > 1) {
				if (h1.getHandScore().getKickers().size() > 1) {
					result = h2.getHandScore().getKickers().get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNbr()
							- h1.getHandScore().getKickers().get(eCardNo.SecondCard.getCardNo()).geteRank()
									.getiRankNbr();
				}
				if (result != 0) {
					return result;
				}
			}

			if (h2.getHandScore().getKickers().size() > 2) {
				if (h1.getHandScore().getKickers().size() > 2) {
					result = h2.getHandScore().getKickers().get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNbr()
							- h1.getHandScore().getKickers().get(eCardNo.ThirdCard.getCardNo()).geteRank()
									.getiRankNbr();
				}
				if (result != 0) {
					return result;
				}
			}

			if (h2.getHandScore().getKickers().size() > 3) {
				if (h1.getHandScore().getKickers().size() > 3) {
					result = h2.getHandScore().getKickers().get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNbr()
							- h1.getHandScore().getKickers().get(eCardNo.FourthCard.getCardNo()).geteRank()
									.getiRankNbr();
				}
				if (result != 0) {
					return result;
				}
			}
			return 0;
		}
	};
}
