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
	private UnoGame game;
	private Card card;
	private Hand hand;
	
	// Constructor
	public CardListener(UnoGame gameModel, Card newCard, Hand initHand)
	{
		game = gameModel;
		card = newCard;
		hand = initHand;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		boolean cardCheck = game.checkMatchingCard(card);
		
		if (cardCheck)
		{
			// Updates discard pile with attributes of card that was clicked on
			game.updateDiscardPile(card, hand);
			
			System.out.println("PLAYER match found");
			
			game.opponentTurn(); // start opponent's turn
		}
	}
}
