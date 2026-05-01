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

public class CardListener implements ActionListener // CardListener IS ActionListener
{
	private UnoGame gameModel; // CardListener HAS-A game model
	private Card card; // CardListener HAS-A card
	private Hand hand; // CardListener HAS-A hand
	
	// Constructor
	public CardListener(UnoGame game, Card newCard, Hand initHand)
	{
		gameModel = game;
		card = newCard;
		hand = initHand;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		boolean cardCheck = gameModel.checkMatchingCard(card);
		
		if (cardCheck)
		{
			// Updates discard pile with attributes of card that was clicked on
			gameModel.updateDiscardPile(card, hand);
			
			System.out.println("PLAYER match found");
			
			gameModel.opponentTurn(); // start opponent's turn
		}
	}
}
