
package pkgPokerBLL;

import java.util.ArrayList;
import java.util.Comparator;

import pkgPokerEnum.eHandStrength;
import pkgPokerEnum.eRank;

public class HandScore implements Comparable{

	private eHandStrength HandStrength;
	private eRank HiHand;
	private eRank LoHand = null;
	private ArrayList<Card> Kickers = new ArrayList<Card>();

	public HandScore() {
	}

	public eHandStrength getHandStrength() {
		return HandStrength;
	}

	public void setHandStrength(eHandStrength handStrength) {
		HandStrength = handStrength;
	}

	public eRank getHiHand() {
		return HiHand;
	}

	public void setHiHand(eRank hiHand) {
		HiHand = hiHand;
	}

	public eRank getLoHand() {
		return LoHand;
	}

	public void setLoHand(eRank loHand) {
		LoHand = loHand;
	}

	public ArrayList<Card> getKickers() {
		return Kickers;
	}

	public void setKickers(ArrayList<Card> kickers) {
		Kickers = kickers;
	}

	

	@Override
	public int compareTo(Object o) {
		HandScore h = (HandScore) o;
		return h.getHandStrength().compareTo(this.getHandStrength());
	}
}
