package chess.driver;

/**
 * Driver class for Chess program.
 * Displays Greetings and Brief Instructions, then creates the board, displays it,
 * then asks Player1 for a move.  The game follows all the rules of normal chess
 * (Minus castleing), and uses a rough text display.
 * 
 * @author Randyll Bearer
 */
public class ChessDriver {


    
    /**
     * Creates the board object, piece objects, displays them in a text interface,
     * and then asks the user for a move.
     * 
     * @param args 
     */
    public static void main(String[] args) {
        System.out.println("Hello, Welcome to Chess by Randyll Bearer!\r\n\r\nThis game of Chess follows all the rules of conventional chess (minus castleing) but with an additional layer of text display goodness!");
        System.out.println("1 - Player One will control the UpperCase characters, and Player Two will control the LowerCase characters");
        System.out.println("2 - Blank spaces capable of being moved to will be displayed as '[ ] ' while spaces occupied by the other player's pieces will be donated with an '*'");
        System.out.println("3 - To select a piece, type only the characters displayed on the screen [Case-Sensitive]");
        System.out.println("4 - To move a piece, enter the correct Coordinates (x-axis, y-axis) [Non Case-Sensitive]");
        System.out.println("5 - Entering '00' during 'Move to: ' phase will allow you to reselect another piece");
        
        ChessBoard board = new ChessBoard();
        board.create();
        board.display();
        
        while(true){    // Keep moving pieces until the game is over.
            board.selectPiece();             
        }
           
    }
}
