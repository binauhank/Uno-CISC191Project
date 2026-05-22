import java.awt.Color;

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
* Version: 2026-04-30
*/

public class DrawFourCard extends Card // DrawFourCard IS-A Card
{
	public DrawFourCard(int initNumber, boolean playerCheck) // this card starts as white, no need to set a specific color
	{
		super(Color.WHITE, initNumber, playerCheck);
		
		if (playerCheck) // if card is in player's hand, set text
		{
			this.setText("+4");
		}
	}
}
