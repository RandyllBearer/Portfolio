/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package midterm.practice.pkg4.players.pkgfor.a.team;

/**
 *
 * @author Randyll Bearer
 */
import java.util.Scanner;
import java.util.InputMismatchException;

public class MidtermPractice4PlayersForATeam {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        while(true){
            try{
                System.out.print("How many players will each team have? [9 - 15] ");
                int PLAYERS = keyboard.nextInt();
                if (PLAYERS > 15 || PLAYERS < 9){
                   System.out.println("Sorry but you must have between 9 - 15 players per team");
                }else if (PLAYERS >= 9 && PLAYERS <= 15){
                    System.out.println("How many players are available to be grouped into teams? ");
                    int availablePlayers = keyboard.nextInt();
                    if (availablePlayers < 0){
                        System.out.println("You cannot have negative players!");
                    }else if (availablePlayers > 0){
                        int numTeams = availablePlayers / PLAYERS;
                        int playersRemaining = availablePlayers % PLAYERS;
                        System.out.println("There will be " + numTeams + " teams, with " + playersRemaining + " players remaining.");
                        break;
                    }
                    
                }
            
            
            }catch(InputMismatchException ime){
                System.out.println("Sorry, but you must enter an integer value.");
                keyboard.next();
            }
        }
    }
}
