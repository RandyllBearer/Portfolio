/*
 * Dungeons Under Djinison by Randyll Bearer 2019
 * 
 * This Panel acts as the first display panel to be shown upon loading the game
 * Displays a static .jpg/.png title art piece
 * 
 * Additional information can be found at:
 * https://drive.google.com/open?id=1Krv9NYRR_9qC-e4cz-ulyMIfMEFIvQ8Y
 */

package view;

//Imports
import view.View;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.*;

public class TitleArtPanel{

	//Fields
	static JPanel titleArtPanel = null;	//To Be Returned and then added to mainFrame
	
	//Default Constructor
	public TitleArtPanel(int x, int y) {
		//Do Nothing
		
	}
	
	//Public Functions
	public static JPanel getTitleArtPanel(int x, int y) {
		titleArtPanel = new JPanel();
		titleArtPanel.setPreferredSize(new Dimension(x, y));
		
		JLabel helloMessage = new JLabel("Hello, Welcome to Dungeons under Djinison!");
		titleArtPanel.add(helloMessage);
		titleArtPanel.revalidate();
		titleArtPanel.repaint();
		titleArtPanel.setVisible(true);
		
		return titleArtPanel;
	}
	
}
//End of File