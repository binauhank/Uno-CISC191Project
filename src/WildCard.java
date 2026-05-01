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

public class WildCard extends Card
{
	public WildCard(Color initColor, int initNumber, boolean playerCheck)
	{
		super(initColor, initNumber, playerCheck);
		
		if (playerCheck)
		{
			this.setText("W");
		}
	}
}
