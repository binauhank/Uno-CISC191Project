import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

/**
* Lead Author(s):
* @author Kristian Binauhan
*
* Other Contributors:
* None
*
* References:
* Morelli, R., & Walde, R. (2016).
* Java, Java, Java: Object-Oriented Problem Solving
* https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
*
* Version: 2026-04-16
*/

public class Hand extends JPanel
{
	private ArrayList<Card> cards; // Hand HAS-MANY Cards
	private boolean isPlayer; // this boolean checks for if this object is the player's hand or the opponent's hand
	private UnoGame game;
	
	// Constructor
	public Hand(boolean playerCheck, UnoGame gameModel)
	{
		this.setLayout(new FlowLayout());
		
		cards = new ArrayList<Card>();
		isPlayer = playerCheck;
		game = gameModel;
		
		this.drawCard(7); // start game with 7 cards
	}
	
	public void drawCard(int howManyCards)
	{
		// Array of all four colors that are in Uno
		Color[] colors = {Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN};
		Random rand = new Random();
		
		for (int i = 0; i < howManyCards; i++)
		{
			// Generate card color using a randomly generated array index
			int randomIndex = rand.nextInt(4);
			Color randomColor = colors[randomIndex];
			
			// Generate random number between 1-9
			int randomNumber = rand.nextInt(9) + 1;
			
			// TODO - replace above
//			int randomNumber = rand.nextInt(9);
//			if (randomNumber == 0)
//			{
//				generate draw two card
//			}
			
			// Create new card using randomColor and randomNumber, adds it to list and JPanel
			// TODO - if (randomNumber > 0) ... insert code below
			Card card = new Card(randomColor, randomNumber, isPlayer);
			card.addActionListener(new CardListener(game, card, this));
			cards.add(card);
			this.add(card);
		}
	}
	
	public void removeCard(Card cardToRemove)
	{
		// Remove from list and JPanel
		cards.remove(cardToRemove);
		this.remove(cardToRemove);
		
		// Update UI
		this.revalidate();
		this.repaint();
	}
	
	public int getNumCards()
	{
		return cards.size();
	}
	
	public Card getLastCard()
	{
		return cards.getLast(); // retrieve latest card drawn to hand
	}
	
	// Used during opponent's turn
	public Card findMatch(Card discardPile)
	{
		Card match = null;
		
		for (Card c : cards) // checks each card in the hand for matching color or number with the discard pile
		{
			if (c.getColor() == discardPile.getColor() || c.getNumber() == discardPile.getNumber())
			{
				match = c;
			}
		}
		
		return match;
	}
}
