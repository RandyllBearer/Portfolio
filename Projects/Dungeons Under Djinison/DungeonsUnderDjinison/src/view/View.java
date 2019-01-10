/*
 * Dungeons Under Djinison by Randyll Bearer 2019
 * 
 * This class will comprise the "View" portion of the
 * Model-View-Controller architecture. This View class will
 * contain functions for displaying game Model-State to
 * screen through the use of Swing panels.
 * 
 * Additional information can be found at:
 * https://drive.google.com/open?id=1Krv9NYRR_9qC-e4cz-ulyMIfMEFIvQ8Y
 */

package view;

//Imports
import view.MainFrame;
import view.TitleArtPanel;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.swing.*;

public class View {
	
	//Fields
	JFrame mainFrame = null;	//will act as a container to hold all individual panels
	int frameWidth;
	int frameHeight;
	int screenWidth;
	int screenHeight;
	
	//Default Constructor
	public View() {
		//Initialize mainFrame
		createMainFrame();
		//Retrieve screen resolution details
		setScreenResolution();
	}
	
	//Display Main Menu
	//Display Character Select
	//Display Game Loop
	
	//Public Functions
	//----------------
	
	//Display Title Art Panel
	public void displayTitleArt() {
		//Create titleArtPanel
		JPanel titleArtPanel =TitleArtPanel.getTitleArtPanel(screenWidth, screenHeight);
		//Add newPanel to mainFrame
		mainFrame.add(titleArtPanel);
		//Repaint the contents after adding titleArtPanel
		mainFrame.revalidate();
		mainFrame.repaint();
	
		
	}
	
	//Private Helper Functions
	//------------------------
	
	//set Screen Resolution
	private void setScreenResolution() {
		//Get size of resolution for the mainFrame
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frameWidth = (int) screenSize.getWidth();	
		frameHeight = (int) screenSize.getHeight();
		//Determine how much space we have to work with inside the frame
		Insets insets = mainFrame.getInsets();
		screenWidth = frameWidth-(insets.left+insets.right);
		screenHeight = frameHeight-(insets.top+insets.bottom);
	}
	
	//Create Main Frame (Container to hold all other panels)
	private void createMainFrame() {
		mainFrame = MainFrame.getMainFrame(frameWidth, frameHeight);
	}
	
	//Public Getter/Setter Functions
	//------------------------------
	
	//Get FrameWidth
	public int getFrameWidth() {
		return frameWidth;
	}
	
	//Get FrameHeight
	public int getFrameHeight() {
		return frameHeight;
	}
	
	//Get ScreenWidth
	public int getScreenWidth() {
		return screenWidth;
	}
	
	//Get ScreenHeight
	public int getScreenHeight() {
		return screenHeight;
	}
	
}
//End of File