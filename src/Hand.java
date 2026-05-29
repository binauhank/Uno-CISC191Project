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

public class Hand extends JPanel // Hand IS-A JPanel
{
	private ArrayList<Card> cards; // Hand HAS-MANY Cards
	private UnoGame gameModel; // Hand HAS-A game model
	private boolean isPlayer; // this boolean checks for if this object is the player's hand or the opponent's hand
	
	// Constructor
	public Hand(boolean playerCheck, UnoGame game)
	{
		this.setLayout(new FlowLayout());
		
		cards = new ArrayList<Card>();
		isPlayer = playerCheck;
		gameModel = game;
		
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
			Color randomColor = gameModel.randomColor();
			
			// Generate random number between 1-12
			int randomNumber = rand.nextInt(12) + 1;
			
			// Create new card using randomColor and randomNumber, adds it to list and JPanel
			// Numbers 10-12 are not real in Uno, so I'll use them for the special cards
			if (randomNumber == 10) // Draw Two card
			{
				DrawTwoCard drawTwoCard = new DrawTwoCard(randomColor, randomNumber, isPlayer);
				drawTwoCard.addActionListener(new CardListener(gameModel, drawTwoCard, this));
				cards.add(drawTwoCard);
				this.add(drawTwoCard);
			}
			else if (randomNumber == 11) // Draw Four card
			{
				DrawFourCard drawFourCard = new DrawFourCard(randomNumber, isPlayer);
				drawFourCard.addActionListener(new CardListener(gameModel, drawFourCard, this));
				cards.add(drawFourCard);
				this.add(drawFourCard);
			}
			else if (randomNumber == 12) // Wild card
			{
				WildCard wildCard = new WildCard(randomNumber, isPlayer);
				wildCard.addActionListener(new CardListener(gameModel, wildCard, this));
				cards.add(wildCard);
				this.add(wildCard);
			}
			else // Normal card (1-9)
			{
				Card card = new Card(randomColor, randomNumber, isPlayer);
				card.addActionListener(new CardListener(gameModel, card, this));
				cards.add(card);
				this.add(card);
			}		
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
	
	public boolean checkPlayer()
	{
		return isPlayer; // returns true if this object is the player's hand, false if opponent's hand
	}
	
	// Used during opponent's turn
	public Card findMatch(Card discardPile)
	{
		Card match = null;
		
		for (Card c : cards) // checks each card in the hand for matching color or number with the discard pile
		{
			if (c.getColor().equals(discardPile.getColor()) || c.getNumber() == discardPile.getNumber())
			{
				match = c;
			}
		}
		
		return match;
	}
}
