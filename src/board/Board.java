/**
 * 
 */
package board;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;

import players.Dealer;
import players.Player;

/**
 * @author mjschaub
 * the class for board logic and the game loop
 */
public class Board implements Serializable
{
	
	public Dealer dealer;
	public ArrayList<Player> players;
	private Deck gameDeck;
	public int turn; // -1 for dealer, player id from 0 - numPlayers
	private int numPlayers;
	private boolean isRunning;
	/**
	 * the board constructor to initialize the player ArrayList and the dealer
	 */
	public Board()
	{
		players = new ArrayList<Player>();
		dealer = new Dealer();
		gameDeck = new Deck();
		turn = 0;
		isRunning=true;
	}
	/**
	 * runs the game loop and end game logic for if a player will win or not and lets a player restart the game
	 */
	public void runGameLoop()
	{
		do
		{
			gameDeck.shuffle();
			dealDealerCard();
			dealDealerCard();
			for(int i = 0; i < numPlayers;i++)
			{
				Player currPlayer = this.getPlayerByID(i);
				dealPlayerCard(i);
				dealPlayerCard(i);
				if(dealer.getBlackJackScore() == 21)
				{	
					if(currPlayer.getBlackjackScore() == 21)
					{
						setPlayerWonOrLoss(currPlayer, 2);
					}
					else
					{
						setPlayerWonOrLoss(currPlayer, 0);
					}
				}
				if(currPlayer.getBlackjackScore() == 21)
				{
					setPlayerWonOrLoss(currPlayer, 1);
				}
				//show user his/her own and dealer cards
				//prompt to ask whether they want to hit or stand
				int action = 0; //connect to user depending on what he wants to do
				while(action == 0)
				{
					dealPlayerCard(i);
					if(currPlayer.getBlackjackScore() > 21)
					{
						setPlayerWonOrLoss(currPlayer, 0);
					}
					//prompt again
					if(action == 1)
						break;
				}
					
			}
			
			while(dealer.getBlackJackScore() <= 16)
			{
				this.dealDealerCard();
				if(dealer.getBlackJackScore() > 21)
				{
					for(int i = 0; i < numPlayers; i++)
						setPlayerWonOrLoss(getPlayerByID(i),1);
				}
			}
			for(int i = 0; i < numPlayers;i++)
			{
				Player currPlayer = this.getPlayerByID(i);
				if(currPlayer.getBlackjackScore() > dealer.getBlackJackScore())
				{
					setPlayerWonOrLoss(currPlayer, 1);
				}
				else if(currPlayer.getBlackjackScore() < dealer.getBlackJackScore())
				{
					setPlayerWonOrLoss(currPlayer, 0);
				}
				else
				{
					setPlayerWonOrLoss(currPlayer, 2);
				}
			}
			
			
		} while(isRunning);
		/*Create and shuffle a deck of cards
         Create two BlackjackHands, userHand and dealerHand
         Deal two cards into each hand
         if dealer has blackjack
             User loses and the game ends now
         If user has blackjack
             User wins and the game ends now
         Repeat:
             Ask whether user wants to hit or stand
             if user stands:
                 break out of loop
             if user hits:
                 Give user a card
                 if userHand.getBlackjackValue() > 21:
                     User loses and the game ends now
         while  dealerHand.getBlackJackValue() <= 16 :
             Give dealer a card
             if dealerHand.getBlackjackValue() > 21:
                 User wins and game ends now
         if dealerHand.getBlackjackValue() >= userHand.getBlackjackValue()
             User loses
         else
             User wins
             */
		
		
	}
	/**
	 * adds a player to the game
	 * @param name player's name
	 * @param startingMoney player's money amount
	 * @param currBet player's bet
	 */
	public void addPlayer(String name, double startingMoney, double currBet,InetAddress address,int port)
	{
		int newPlayerNum = players.size();
		numPlayers++;
		players.add(new Player(newPlayerNum,name,startingMoney,currBet,address,port));
		System.out.println("player was created and added to game");
	}
	/**
	 * when a player leaves the game, remove them by ID
	 * @param ID the id of the player
	 */
	public void playerHasLeft(int ID)
	{
		players.remove(getPlayerByID(ID));
		numPlayers--;
	}
	/**
	 * removes a player from the game by object
	 * @param player the player object
	 */
	public void playerHasLeft(Player player)
	{
		players.remove(player);
		numPlayers--;
	}
	/**
	 * changes the turn in the game, either letting it know it's the dealer's turn or another players
	 */
	public void changeTurns()
	{
		if(turn != -1 && turn == players.size()-1)
			turn = -1;
		else
			turn++;
	}
	/**
	 * gets player by Id
	 * @param ID the ID of the player
	 * @return the player object
	 */
	public Player getPlayerByID(int ID)
	{
		for(Player x : players)
		{
			
			if(x.getPlayerNum() == ID)
				return x;
			
		}
		return null;
	}
	/**
	 * gets the number of players in the game
	 * @return
	 */
	public int getNumPlayers()
	{
		return numPlayers;
	}
	/**
	 * gets whoever's turn it is
	 * @return the integer id of the player/dealer
	 */
	public int getTurn()
	{
		return this.turn;
	}
	/**
	 * deals a player a card for whoever's turn it is
	 * @param turn the turn it is
	 */
	public void dealPlayerCard(int turn)
	{
		Player currPlayer = getPlayerByID(turn);
		currPlayer.hit(gameDeck.dealCard());
	
	}
	/**
	 * gives the dealer a card
	 */
	public void dealDealerCard()
	{
		dealer.giveCard(gameDeck.dealCard());
	}
	/**
	 * sets the player to either win/lose/tie 
	 * @param curr the current player
	 * @param won the integer value of winning/losing/a tie
	 */
	public void setPlayerWonOrLoss(Player curr, int won)
	{
		curr.setWon(won);
		curr.prepForNewGame();
	}
	public void shuffle()
	{
		gameDeck.shuffle();
	}
	

}
