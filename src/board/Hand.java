/**
 * 
 */
package board;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author mjschaub
 * the hand class containing the cards in each hand and the blackjack value they represent
 */
public class Hand implements Serializable
{

	private ArrayList<Card> hand;
	/**
	 * the constructor which initializes a new arraylist of Card objects 
	 */
	public Hand() 
	{
		hand = new ArrayList<Card>();
	}
	/**
	 * adds a card to the hand, presumably from the dealCard method  
	 * @param newCard
	 */
	public void addCard(Card newCard) 
	{
		if (newCard != null)
			hand.add(newCard);
	}
	/**
	 * clears all the cards in a hand to restart the next hand
	 */
	public void emptyHand() 
	{
		hand.clear();
	}
	/**
	 * removes a card given the Card object to remove   
	 * @param removeCard the card to be taken out of the hand
	 */
	public void removeCard(Card removeCard) 
	{
	
		hand.remove(removeCard);
	}
	/**
	 * removes a card given the index in the hand
	 * @param idx the index to remove
	 */
	public void removeCard(int idx) 
	{

		if (idx >= 0 && idx < hand.size())
			hand.remove(idx);
	}
	/**
	 * gets the number of cards in the hand
	 * @return
	 */
	public int getCardCount() 
	{
		return hand.size();
	}
    /**
     * gets the card at the index given
     * @param idx the integer index value
     * @return the Card object
     */
	public Card getCard(int idx) 
	{

		if (idx > -1 && idx < hand.size())
			return (Card)hand.get(idx);
		else
			return null;
	}
	/**
	 * gets the blackjack game's value of the hand, correcting for the possibility for the ace to be either 1 or 11
	 * @return the integer number of value
	 */
	public int getBlackjacktotalValue() 
	{
  
		int totalVal = 0;
		boolean isAce = false;
		int numCards = getCardCount();

		for ( int i = 0;  i < numCards;  i++ ) 
		{
			
		    Card currCard = getCard(i);
		    int newCardtotalVal = currCard.getValue(); 
		    if(newCardtotalVal > 10) 
		    	newCardtotalVal = 10;   
		     
		    if(newCardtotalVal == 1) 
		    	isAce = true;     
		    
		    totalVal = totalVal + newCardtotalVal;
		}
		
		if(isAce && totalVal + 10 <= 21)
			totalVal = totalVal + 10;
		
		return totalVal;

	}
	/**
	 * gets the cards in the hand
	 * @return the arraylist of card objects
	 */
	public ArrayList<Card> getCards()
	{
		return this.hand;
	}
	
}