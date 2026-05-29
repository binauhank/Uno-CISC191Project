import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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

public class UnoGame extends JFrame // UnoGame IS-A JFrame
{
	private Hand playerHand; // UnoGame HAS-A player hand
	private Hand opponentHand; // UnoGame HAS-AN opponent hand
	private Card discardPile; // UnoGame HAS-A discard pile
	private File gameLog; // UnoGame HAS-A game log
	private PrintWriter logWriter; // UnoGame HAS-A log writer
	
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
		
		// Discard Pile
		// Generate random color and number for card on top of discard pile at the start of the game
		Random rand = new Random();
		
		Color randomColor = randomColor();
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
		
		gameLog = new File("GameLog.txt");

		try
		{
			logWriter = new PrintWriter(gameLog);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace(); // prints stack trace if attempt to open file fails
		}
		
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
		if (cardToCheck.getColor().equals(Color.WHITE)) // if Wild Card or Draw Four Card
		{
			return true;
		}
		
		if (cardToCheck.getColor().equals(discardPile.getColor()) || cardToCheck.getNumber() == discardPile.getNumber()) // color matches OR number matches
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
		
		// Check if card is a Draw Two card
		if (card instanceof DrawTwoCard)
		{
			discardPile.setText("+2");
			
			if (hand.checkPlayer()) // if the hand that this card belongs to is the player's, force opponent to draw two
			{
				opponentHand.drawCard(2);
				
				opponentHand.revalidate(); // update UI
				opponentHand.repaint();
				
				String str = "Player - " + convertColorToString(card.getColor()) + " +2";
				updateGameLog(str);
			}
			else // if the hand that this card belongs to is the opponent's, force player to draw two
			{
				playerHand.drawCard(2);
				
				playerHand.revalidate();
				playerHand.repaint();
				
				String str = "Opponent - " + convertColorToString(card.getColor()) + " +2";
				updateGameLog(str);
			}
		}
		
		// Check if card is a Draw Four card
		if (card instanceof DrawFourCard)
		{
			discardPile.setText("+4");
			
			if (hand.checkPlayer()) // if hand belongs to player, use selectColor() method to prompt a selection
			{
				try
				{
					Color colorChosen = selectColor("Draw Four");
					discardPile.setColor(colorChosen);
					
					String str = "Player - " + convertColorToString(colorChosen) + " +4";
					updateGameLog(str);
				}
				catch (InvalidOptionException e)
				{
					// If InvalidOptionException is thrown (player closes the Select Color window), pick a random color and print to console
					Color randomColor = randomColor();
					discardPile.setColor(randomColor);
					
					String str = "Player - " + convertColorToString(randomColor) + " +4";
					updateGameLog(str);
					
					System.out.println(e.getMessage());
				}
				
				opponentHand.drawCard(4); // force opponent to draw four
				
				opponentHand.revalidate();
				opponentHand.repaint();
			}
			else // if hand belongs to opponent, select a random color
			{
				Color randomColor = randomColor();
				discardPile.setColor(randomColor);
				
				String str = "Opponent - " + convertColorToString(randomColor) + " +4";
				updateGameLog(str);
				
				playerHand.drawCard(4); // force player to draw four
				
				playerHand.revalidate();
				playerHand.repaint();
			}
		}
		
		// Check if card is a Wild card
		if (card instanceof WildCard)
		{
			discardPile.setText("W");
			
			if (hand.checkPlayer()) // if hand belongs to player, use selectColor() method to prompt a selection
			{
				try
				{
					Color colorChosen = selectColor("Wild Card");
					discardPile.setColor(colorChosen);
					
					String str = "Player - " + convertColorToString(colorChosen) + " Wild";
					updateGameLog(str);
				}
				catch (InvalidOptionException e)
				{
					// If InvalidOptionException is thrown (player closes the Select Color window), use a random color and print to console
					Color randomColor = randomColor();
					discardPile.setColor(randomColor);
					
					String str = "Player - " + convertColorToString(randomColor) + " Wild";
					updateGameLog(str);
					
					System.out.println(e.getMessage());
				}
			}
			else // if hand belongs to opponent, select a random color
			{
				Color randomColor = randomColor();
				discardPile.setColor(randomColor);
				
				String str = "Opponent - " + convertColorToString(randomColor) + " Wild";
				updateGameLog(str);
			}
		}
		
		// Check win conditions - if player wins/loses, end the game
		if (playerWin())
		{
			try
			{
				endGame("You Win!");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		if (opponentWin())
		{
			try
			{
				endGame("You lost.");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	// Opponent's turn - checks for matching card in their hand, plays it if found
	public void opponentTurn()
	{
		Card opponentCard = opponentHand.findMatch(discardPile);
		
		if (opponentCard != null) // if there is a matching card in the opponent's hand
		{
			if (opponentCard.getNumber() < 10) // if standard card (not Draw 2/4 or Wild)
			{
				String str = "Opponent - " + convertColorToString(opponentCard.getColor()) + " " + opponentCard.getNumber();
				updateGameLog(str);
			}
			
			this.updateDiscardPile(opponentCard, opponentHand);
		}
		else // otherwise, draw one card
		{
			opponentHand.drawCard(1);
			
			String str = "Opponent - Draw 1";
			updateGameLog(str);
			
			// Check if newly drawn card matches discard pile
			boolean cardCheck = this.checkMatchingCard(opponentHand.getLastCard());
			
			if (cardCheck) // if there is a match, update discard pile
			{
				if (opponentHand.getLastCard().getNumber() < 10)
				{
					str = "Opponent - " + convertColorToString(opponentHand.getLastCard().getColor())
							+ " " + opponentHand.getLastCard().getNumber();
					
					updateGameLog(str);
				}
				
				this.updateDiscardPile(opponentHand.getLastCard(), opponentHand);
			}
		}
	}
	
	// Generate a random color
	public Color randomColor()
	{
		Color[] colors = {Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN};
		Random rand = new Random();
		
		int randomIndex = rand.nextInt(4);
		return colors[randomIndex];
	}
	
	// Display pop-up window for selecting a color (used when Wild Card or Draw Four Card is played)
	public Color selectColor(String cardType) throws InvalidOptionException
	{
		Object[] options = {"Red", "Yellow", "Blue", "Green"}; // array used for options in JOptionPane
		
		int colorSelected = JOptionPane.showOptionDialog(this, "Select a color!", cardType, 0, 3, null, options, options[0]);
		
		// Return color based on option selected
		if (colorSelected == 0)
		{
			return Color.RED;
		}
		if (colorSelected == 1)
		{
			return Color.YELLOW;
		}
		if (colorSelected == 2)
		{
			return Color.BLUE;
		}
		if (colorSelected == 3)
		{
			return Color.GREEN;
		}
		else
		{
			throw (new InvalidOptionException(cardType));
		}
	}
	
	// Converts color into a readable string
	public String convertColorToString(Color color)
	{
		if (color.equals(Color.RED))
		{
			return "Red";
		}
		if (color.equals(Color.YELLOW))
		{
			return "Yellow";
		}
		if (color.equals(Color.BLUE))
		{
			return "Blue";
		}
		if (color.equals(Color.GREEN))
		{
			return "Green";
		}
		else
		{
			return "Invalid";
		}
	}
	
	// Pass in string to game log file
	public void updateGameLog(String str)
	{
		logWriter.println(str);
		logWriter.flush();
	}
	
	// Return true if player has zero cards in their hand - player wins
	public boolean playerWin()
	{
		return playerHand.getNumCards() == 0;
	}
	
	// Return true if opponent has zero cards in their hand - opponent wins
	public boolean opponentWin()
	{
		return opponentHand.getNumCards() == 0;
	}
	
	// Close PrintWriter and opens pop-up window once game is over
	public void endGame(String message) throws IOException
	{
		logWriter.close();
		
		Object[] options = {"Open Game Log", "Close Game"};
		int optionSelected = JOptionPane.showOptionDialog(this, message, "Game Over!", 0, 3, null, options, options[0]);
		
		if (optionSelected == 0) // "Open Game Log" button
		{
			Desktop.getDesktop().open(gameLog); // if this button is clicked, opens game log that documents each turn
			endGame(message); // reopen window
		}
		if (optionSelected == 1) // "Close Game" button
		{
			System.exit(0); // close window
		}
	}
}
