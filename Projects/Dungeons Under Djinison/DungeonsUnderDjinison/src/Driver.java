/*
* Dungeons Under Djinison by Randyll Bearer 2019
*
* More Details found at:
* https://github.com/RandyllBearer/Portfolio/tree/master/Projects/Dungeons%20Under%20Djinison
*/

//Imports
import model.Model;
import view.View;
import controller.Controller;

class Driver{
	//Game Model (World, level maps, monsters, data, etc pretty mh everything)
	//View (Functions to display to screen)
	//Controller (Functions to recieve and interpret user input)
	
	
	public static void main(String[] args){
		
		//Model - Initialize Model to prepare for game
		Model gameModel = new Model();
		
		//View - Initialize view to create main frame
		View gameView = new View();
		
		//View - Display TitleArt
		gameView.displayTitleArt();
		
		//Controller - Accept command to progress from titleArt
		
		//View - Display Main Menu
		
		//Controller - Progress from Main Menu
		
		//Startup
			//Intro Text
			//Main Menu Screen (Load/New)
		//Initialize
			//Create game map/world
			//Fill in Meta-Data
		//Gameplay Loop
			//Display to Screen
			//Accept Input
			//Execute Input Command
		
	}
	
}
//End of File