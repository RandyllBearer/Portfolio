/*
 * Dungeons Under Djinison by Randyll Bearer 2019
 * 
 * This Panel acts as the "container" of all other game panels and acts
 * as layout manager.
 * 
 * Additional information can be found at:
 * https://drive.google.com/open?id=1Krv9NYRR_9qC-e4cz-ulyMIfMEFIvQ8Y
 */

package view;

//Imports
import view.View;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.*;

public class MainFrame{

	//Fields
	static JFrame mainFrame = null;
	
	//Default Constructor
	public MainFrame() {
		//Do Nothing
		
	}
	
	//Public Functions
	//----------------
	
	public static JFrame getMainFrame(int x, int y) {
		mainFrame = new JFrame();
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);	//Don't know why, only way to initialize not minimized
		mainFrame.pack();	//Let the layout manager to the resizing work (more platform independent)
		mainFrame.setTitle("Dungeons Under Djinison - Randyll Bearer");
		mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.PAGE_AXIS));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);	//Make sure we can see the frame
		return mainFrame;
	}
	
	//Private Helper Functions
	//------------------------
	
	
}
//End of File