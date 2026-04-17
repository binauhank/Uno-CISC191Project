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
	private Hand playerHand; // DrawListener HAS-A player hand
	
	public DrawListener(Hand hand)
	{
		playerHand = hand;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// Draw one card
		playerHand.drawCard(1);
		
		// Updates UI for newly added components
		playerHand.revalidate();
		playerHand.repaint();
		
		// TODO - check if card drawn matches discard pile, play card if so
		// TODO - end turn, start opponent's turn
	}
}
