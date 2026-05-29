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
* Version: 2026-05-28
*/

public class InvalidOptionException extends Exception // InvalidOptionException IS-A Exception
{
	private String cardName; // InvalidOptionExpception HAS-A card name
	
	public InvalidOptionException(String cardType)
	{
		cardName = cardType;
	}
	
	public String getMessage()
	{
		return "Option not selected for " + cardName + " - Random color selected";
	}
}
