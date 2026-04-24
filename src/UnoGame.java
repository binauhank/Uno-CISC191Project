import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
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

public class UnoGame extends JFrame
{
	private Hand playerHand; // UnoGame HAS-A player hand
	private Hand opponentHand; // UnoGame HAS-AN opponent hand
	private Card discardPile; // UnoGame HAS-A discard pile
	
	public UnoGame()
	{
		this.setTitle("Uno");
		this.setLayout(new BorderLayout());
		this.setMinimumSize(new Dimension(1200, 600)); // placeholder until I figure out sizes I want for each component
		
		// Creates two Hands (one for the player, one for the opponent)
		playerHand = new Hand(true, this);
		opponentHand = new Hand(false, this);
		
		this.add(playerHand, BorderLayout.SOUTH);
		this.add(opponentHand, BorderLayout.NORTH);
		
		// Generate random color and number for card on top of discard pile at the start of the game
		Color[] colors = {Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN};
		Random rand = new Random();
		
		int randomIndex = rand.nextInt(4);
		Color randomColor = colors[randomIndex];
		int randomNumber = rand.nextInt(9) + 1;
		
		discardPile = new Card(randomColor, randomNumber, true); // setting true to not affect button's color and size, will not be adding listener to prevent an event on click
		
		JPanel discardPanel = new JPanel();
		discardPanel.setLayout(new GridBagLayout()); // centers component without affecting its size
		discardPanel.add(discardPile);
		this.add(discardPanel, BorderLayout.CENTER);
		
		// Draw Button
		JButton drawButton = new JButton();
		drawButton.setText("Draw");
		drawButton.addActionListener(new DrawListener(this, playerHand));
		this.add(drawButton, BorderLayout.EAST);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	// Starts game
	public static void main(String[] args)
	{
		new UnoGame();
	}
	
	public boolean checkMatchingCard(Card cardToCheck)
	{
		if (cardToCheck.getColor() == discardPile.getColor() || cardToCheck.getNumber() == discardPile.getNumber()) // color matches OR number matches
		{
			return true;
		}
		
		return false; // otherwise, return false
	}
	
	public void updateDiscardPile(Card card, Hand hand)
	{
		// Set discard pile's color and number to card that was clicked on
		discardPile.setColor(card.getColor());
		discardPile.setNumber(card.getNumber());
								
		// Remove card from hand
		hand.removeCard(card);
	}
	
	public void opponentTurn()
	{
		Card opponentCard = opponentHand.findMatch(discardPile);
		
		if (opponentCard != null) // if there is a matching card in the opponent's hand
		{
			this.updateDiscardPile(opponentCard, opponentHand);
			
			System.out.println("OPPONENT match found"); // temporary prints for making sure things work properly
		}
		else // otherwise, draw one card
		{
			opponentHand.drawCard(1);
			
			System.out.println("OPPONENT draw one");
			
			// Check if newly drawn card matches discard pile
			boolean cardCheck = this.checkMatchingCard(opponentHand.getLastCard());
			
			if (cardCheck) // if there is a match, update discard pile
			{
				this.updateDiscardPile(opponentHand.getLastCard(), opponentHand);
				
				System.out.println("OPPONENT card draw matches");
			}
		}
	}
}
