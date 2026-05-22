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
	private UnoGame gameModel; // DrawListener HAS-A game model
	private Hand playerHand; // DrawListener HAS-A player hand
	
	// Constructor
	public DrawListener(UnoGame game, Hand hand)
	{
		gameModel = game;
		playerHand = hand;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// Draw one card
		playerHand.drawCard(1);
		
		String str = "Player - Draw 1";
		gameModel.updateGameLog(str);
		
		// Check if newly drawn card matches discard pile
		boolean cardCheck = gameModel.checkMatchingCard(playerHand.getLastCard());
		
		if (cardCheck) // if there is a match, update discard pile
		{
			if (playerHand.getLastCard().getNumber() < 10)
			{
				str = "Player - " + gameModel.convertColorToString(playerHand.getLastCard().getColor())
				+ " " + playerHand.getLastCard().getNumber();
				
				gameModel.updateGameLog(str);
			}
			
			gameModel.updateDiscardPile(playerHand.getLastCard(), playerHand);
		}
		
		gameModel.opponentTurn(); // start opponent's turn
		
		// Updates UI for newly added components
		playerHand.revalidate();
		playerHand.repaint();
		
	}
}
