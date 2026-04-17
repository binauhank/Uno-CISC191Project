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
	
	// Constructor
	public Hand(boolean playerCheck)
	{
		this.setLayout(new FlowLayout());
		
		cards = new ArrayList<Card>();
		isPlayer = playerCheck;
		
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
			
			// Create new card using randomColor and randomNumber, adds it to list and JPanel
			Card card = new Card(randomColor, randomNumber, isPlayer);
			// card.addActionListener(new CardListener()); TODO - uncomment once CardListener is implemented
			cards.add(card);
			this.add(card);
		}
	}
	
	public void removeCard(Card cardToRemove)
	{
		cards.remove(cardToRemove);
	}
	
	public int getNumCards()
	{
		return cards.size();
	}
}
