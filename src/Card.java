import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;

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
* Version: 2026-04-15
*/

public class Card extends JButton // Card IS-A Button
{
	private Color color; // Card HAS-A color
	private int number; // Card HAS-A number
	
	// Constructor
	public Card(Color initColor, int initNumber, boolean playerCheck)
	{
		color = initColor;
		number = initNumber;
		
		if (playerCheck) // if this card is in the player's hand
		{
			this.setBackground(initColor);
			this.setText(String.valueOf(number));
		}
		else // if this card is in the opponent's hand (needs to be hidden and cannot be clicked)
		{
			this.setText("?");
			this.setEnabled(false);
		}
		
		this.setForeground(Color.GRAY);
		this.setFont(new Font("Arial", Font.BOLD, 20));
		this.setPreferredSize(new Dimension(70, 60));
	}
	
	// Getters
	public Color getColor()
	{
		return color;
	}
	
	public int getNumber()
	{
		return number;
	}
	
	// Setters - used for discard pile
	public void setColor(Color newColor)
	{
		color = newColor;
		this.setBackground(newColor);
	}
	
	public void setNumber(int newNumber)
	{
		number = newNumber;
		this.setText(String.valueOf(newNumber));
	}
}
