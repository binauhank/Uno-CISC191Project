import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class DrawListener implements ActionListener // DrawListener IS ActionListener
{
	private UnoGame game;
	private Hand playerHand; // DrawListener HAS-A player hand
	
	// Constructor
	public DrawListener(UnoGame gameModel, Hand hand)
	{
		game = gameModel;
		playerHand = hand;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// Draw one card
		playerHand.drawCard(1);
		
		System.out.println("PLAYER draw one");
		
		// Check if newly drawn card matches discard pile
		boolean cardCheck = game.checkMatchingCard(playerHand.getLastCard());
		
		if (cardCheck) // if there is a match, update discard pile
		{
			game.updateDiscardPile(playerHand.getLastCard(), playerHand);
			
			System.out.println("PLAYER card draw matches");
		}
		
		game.opponentTurn(); // start opponent's turn
		
		// Updates UI for newly added components
		playerHand.revalidate();
		playerHand.repaint();
		
	}
}
