package chess.driver;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

/**
 * Due to not preparing as well as I should have, this class has basically ended
 * up being half of my driver method.  Contains methods for creating the board
 * and pieces, displaying possible moves, making those moves, making substitutions,
 * and checking for win conditions.
 *
 * @author Randyll Bearer
 */
public class ChessBoard {
    private int turn = 0; // 0=Uppercase's Turn[Player1], 1=Lowercase's Turn[Player2]
    private ArrayList<ArrayList<ChessPiece>> columns = new ArrayList<ArrayList<ChessPiece>>(8);     // Multi-dimensional ArrayList which contains all ChessPiece objects                     
    
    /**
     * While the multi-dimensional array has already been initialized, this method
     * "fills it out" with ChessPiece objects in the proper order.
     */
    public void create(){       
        ArrayList<ChessPiece> columnA = new ArrayList<ChessPiece>(8);
        columns.add(columnA);                                               
        ArrayList<ChessPiece> columnB = new ArrayList<ChessPiece>(8);
        columns.add(columnB);
        ArrayList<ChessPiece> columnC = new ArrayList<ChessPiece>(8);
        columns.add(columnC);
        ArrayList<ChessPiece> columnD = new ArrayList<ChessPiece>(8);
        columns.add(columnD);
        ArrayList<ChessPiece> columnE = new ArrayList<ChessPiece>(8);
        columns.add(columnE);
        ArrayList<ChessPiece> columnF = new ArrayList<ChessPiece>(8);
        columns.add(columnF);
        ArrayList<ChessPiece> columnG = new ArrayList<ChessPiece>(8);
        columns.add(columnG);
        ArrayList<ChessPiece> columnH = new ArrayList<ChessPiece>(8);
        columns.add(columnH);
        //Column0
        ChessPiece ROOK1 = new ChessPiece(2, "R1  ");
        columns.get(0).add(ROOK1);
        ChessPiece PAWN1 = new ChessPiece(1, "P1  ");
        columns.get(0).add(PAWN1);
        ChessPiece blank1 = new ChessPiece();
        columns.get(0).add(blank1);
        ChessPiece blank2 = new ChessPiece();
        columns.get(0).add(blank2);
        ChessPiece blank3 = new ChessPiece();
        columns.get(0).add(blank3);
        ChessPiece blank4 = new ChessPiece();
        columns.get(0).add(blank4);
        ChessPiece pawn1 = new ChessPiece(1, "p1  ");
        columns.get(0).add(pawn1);
        ChessPiece rook1 = new ChessPiece(2, "r1  ");
        columns.get(0).add(rook1);
        //Column1
        ChessPiece KNIGHT1 = new ChessPiece(3, "K1  ");
        columns.get(1).add(KNIGHT1);
        ChessPiece PAWN2 = new ChessPiece(1, "P2  ");
        columns.get(1).add(PAWN2);
        ChessPiece blank5 = new ChessPiece();
        columns.get(1).add(blank5);
        ChessPiece blank6 = new ChessPiece();
        columns.get(1).add(blank6);
        ChessPiece blank7 = new ChessPiece();
        columns.get(1).add(blank7);
        ChessPiece blank8 = new ChessPiece();
        columns.get(1).add(blank8);
        ChessPiece pawn2 = new ChessPiece(1, "p2  ");
        columns.get(1).add(pawn2);
        ChessPiece knight1 = new ChessPiece(3, "k1  ");
        columns.get(1).add(knight1);
        //Column2
        ChessPiece BISHOP1 = new ChessPiece(4, "B1  ");
        columns.get(2).add(BISHOP1);
        ChessPiece PAWN3 = new ChessPiece(1, "P3  ");
        columns.get(2).add(PAWN3);
        ChessPiece blank9 = new ChessPiece();
        columns.get(2).add(blank9);
        ChessPiece blank10 = new ChessPiece();
        columns.get(2).add(blank10);
        ChessPiece blank11 = new ChessPiece();
        columns.get(2).add(blank11);
        ChessPiece blank12 = new ChessPiece();
        columns.get(2).add(blank12);
        ChessPiece pawn3 = new ChessPiece(1, "p3  ");
        columns.get(2).add(pawn3);
        ChessPiece bishop1 = new ChessPiece (4, "b1  ");
        columns.get(2).add(bishop1);
        //Column3
        ChessPiece QUEEN = new ChessPiece(5, "QN  "); 
        columns.get(3).add(QUEEN);
        ChessPiece PAWN4 = new ChessPiece(1, "P4  ");
        columns.get(3).add(PAWN4);
        ChessPiece blank13 = new ChessPiece();
        columns.get(3).add(blank13);
        ChessPiece blank14 = new ChessPiece();
        columns.get(3).add(blank14);
        ChessPiece blank15 = new ChessPiece();
        columns.get(3).add(blank15);
        ChessPiece blank16 = new ChessPiece();
        columns.get(3).add(blank16);
        ChessPiece pawn4 = new ChessPiece(1, "p4  ");
        columns.get(3).add(pawn4);
        ChessPiece queen = new ChessPiece (5, "qn  ");
        columns.get(3).add(queen);
        //Column4
        ChessPiece KING = new ChessPiece(6, "KN  ");
        columns.get(4).add(KING);
        ChessPiece PAWN5 = new ChessPiece(1, "P5  ");
        columns.get(4).add(PAWN5);
        ChessPiece blank17 = new ChessPiece();
        columns.get(4).add(blank17);
        ChessPiece blank18 = new ChessPiece();
        columns.get(4).add(blank18);
        ChessPiece blank19 = new ChessPiece();
        columns.get(4).add(blank19);
        ChessPiece blank20 = new ChessPiece();
        columns.get(4).add(blank20);
        ChessPiece pawn5 = new ChessPiece(1, "p5  ");
        columns.get(4).add(pawn5);
        ChessPiece king = new ChessPiece (6, "kn  ");
        columns.get(4).add(king);
        //Column5
        ChessPiece BISHOP2 = new ChessPiece(4, "B2  ");
        columns.get(5).add(BISHOP2);
        ChessPiece PAWN6 = new ChessPiece(1, "P6  ");
        columns.get(5).add(PAWN6);
        ChessPiece blank21 = new ChessPiece();
        columns.get(5).add(blank21);
        ChessPiece blank22 = new ChessPiece();
        columns.get(5).add(blank22);
        ChessPiece blank23 = new ChessPiece();
        columns.get(5).add(blank23);
        ChessPiece blank24 = new ChessPiece();
        columns.get(5).add(blank24);
        ChessPiece pawn6 = new ChessPiece(1, "p6  ");
        columns.get(5).add(pawn6);
        ChessPiece bishop2 = new ChessPiece (4, "b2  ");
        columns.get(5).add(bishop2);
        //Column6
        ChessPiece KNIGHT2 = new ChessPiece(3, "K2  ");
        columns.get(6).add(KNIGHT2);
        ChessPiece PAWN7 = new ChessPiece(1, "P7  ");
        columns.get(6).add(PAWN7);
        ChessPiece blank25 = new ChessPiece();
        columns.get(6).add(blank25);
        ChessPiece blank26 = new ChessPiece();
        columns.get(6).add(blank26);
        ChessPiece blank27 = new ChessPiece();
        columns.get(6).add(blank27);
        ChessPiece blank28 = new ChessPiece();
        columns.get(6).add(blank28);
        ChessPiece pawn7 = new ChessPiece(1, "p7  ");
        columns.get(6).add(pawn7);
        ChessPiece knight2 = new ChessPiece (3, "k2  ");
        columns.get(6).add(knight2);
        //Column7
        ChessPiece ROOK2 = new ChessPiece(2, "R2  ");
        columns.get(7).add(ROOK2);
        ChessPiece PAWN8 = new ChessPiece(1, "P8  ");
        columns.get(7).add(PAWN8);
        ChessPiece blank29 = new ChessPiece();
        columns.get(7).add(blank29);
        ChessPiece blank30 = new ChessPiece();
        columns.get(7).add(blank30);
        ChessPiece blank31 = new ChessPiece();
        columns.get(7).add(blank31);
        ChessPiece blank32 = new ChessPiece();
        columns.get(7).add(blank32);
        ChessPiece pawn8 = new ChessPiece(1, "p8  ");
        columns.get(7).add(pawn8);
        ChessPiece rook2 = new ChessPiece (2, "r2  ");
        columns.get(7).add(rook2);  
        }
    
    /**
     * Displays the multi-dimensional array created in .create()
     * as text and prints it out to the screen.
     */
    public void display(){
        ArrayList<ChessPiece> row0 = new ArrayList<ChessPiece>(8);  // Contains all pieces in uppermost row
        ArrayList<ChessPiece> row1 = new ArrayList<ChessPiece>(8);
        ArrayList<ChessPiece> row2 = new ArrayList<ChessPiece>(8);
        ArrayList<ChessPiece> row3 = new ArrayList<ChessPiece>(8);
        ArrayList<ChessPiece> row4 = new ArrayList<ChessPiece>(8);
        ArrayList<ChessPiece> row5 = new ArrayList<ChessPiece>(8);
        ArrayList<ChessPiece> row6 = new ArrayList<ChessPiece>(8);
        ArrayList<ChessPiece> row7 = new ArrayList<ChessPiece>(8);  // Contains all pieces in bottom row
        
        ChessPiece toSet = new ChessPiece();
        for(int i=0; i<8; i++){
           toSet = columns.get(i).get(0);
           row0.add(toSet);
           toSet = columns.get(i).get(1);
           row1.add(toSet);
           toSet = columns.get(i).get(2);
           row2.add(toSet);
           toSet = columns.get(i).get(3);
           row3.add(toSet);
           toSet = columns.get(i).get(4);
           row4.add(toSet);
           toSet = columns.get(i).get(5);
           row5.add(toSet);
           toSet = columns.get(i).get(6);
           row6.add(toSet);
           toSet = columns.get(i).get(7);
           row7.add(toSet);
        }
        System.out.print("\r\n\r\n\r\n\r\n\r\n    A   B   C   D   E   F   G   H");   // Resets output window
        System.out.print("\r\n0- ");
        for(int i=0; i<8; i++){
            ChessPiece toCopy = new ChessPiece(row0.get(i));
            String toDisplay = toCopy.getName();
            System.out.print(toDisplay);
        }
        System.out.print("\r\n1- ");
        for(int i=0; i<8; i++){
            ChessPiece toCopy = new ChessPiece(row1.get(i));
            String toDisplay = toCopy.getName();
            System.out.print(toDisplay);
        }
        System.out.print("\r\n2- ");
        for(int i=0; i<8; i++){
            ChessPiece toCopy = new ChessPiece(row2.get(i));
            String toDisplay = toCopy.getName();
            System.out.print(toDisplay);
        }
        System.out.print("\r\n3- ");
        for(int i=0; i<8; i++){
            ChessPiece toCopy = new ChessPiece(row3.get(i));
            String toDisplay = toCopy.getName();
            System.out.print(toDisplay);
        }
        System.out.print("\r\n4- ");
        for(int i=0; i<8; i++){
            ChessPiece toCopy = new ChessPiece(row4.get(i));
            String toDisplay = toCopy.getName();
            System.out.print(toDisplay);
        }
        System.out.print("\r\n5- ");
        for(int i=0; i<8; i++){
            ChessPiece toCopy = new ChessPiece(row5.get(i));
            String toDisplay = toCopy.getName();
            System.out.print(toDisplay);
        }
        System.out.print("\r\n6- ");
        for(int i=0; i<8; i++){
            ChessPiece toCopy = new ChessPiece(row6.get(i));
            String toDisplay = toCopy.getName();
            System.out.print(toDisplay);
        }
        System.out.print("\r\n7- ");
        for(int i=0; i<8; i++){
            ChessPiece toCopy = new ChessPiece(row7.get(i));
            String toDisplay = toCopy.getName();
            System.out.print(toDisplay);
        }
    }
    
    /**
     * Prompts the user which piece it would like to move and then passes
     * that piece into showMoves();
     */
    int oldCoordinateColumn;    // X - coordinate for piece to move
    int oldCoordinateRow;       // Y - coordinate for piece to move
    public void selectPiece(){
        int pieceExists = 0;    //Flag for whether the selected piece is able to be moved
        Scanner kb = new Scanner(System.in);
        String pieceToCheck = "";
        if(turn == 0){
            System.out.print("\r\n\r\nPlayer One, Select Piece: ");
        }else if(turn == 1){
            System.out.print("\r\n\r\nPlayer Two, Select Piece: ");
        }
        pieceToCheck = kb.nextLine();
        if(pieceToCheck.length() == 2){
            pieceToCheck = pieceToCheck + "  ";
            for(int i=0; i<8; i++){
                for(int j=0; j<8; j++){
                    if(columns.get(i).get(j).getName().equals(pieceToCheck)){       // Learning I could call and object like this was a breakthrough
                        oldCoordinateColumn = i;
                        oldCoordinateRow = j;
                        pieceExists = 1;
                    }                    
                }
            }
        }
        if(pieceExists == 1){
            showMoves(oldCoordinateColumn, oldCoordinateRow);
        }
        if(pieceExists == 0){
            System.out.println("Piece does not exist.");
        }
    }
    
    /**
     * 1 - Checks to see if the piece can be moved (If not, says so)
     * 2 - Calculates all possible moves 
     * 3 - Displays all possible moves
     * 4 - Asks for desired move
     * 5 - Checks to see if that move is valid (if not, restarts selectPiece())
     * 6 - Calls movePiece()
     * 7 - Displays newly changed board
     * 
     * @param column: The x-coordinate of piece whose moves we are showing.
     * @param row: The y-coordinate of the piece whose moves we are showing. 
     */
    private void showMoves(int column, int row){
        ArrayList<Integer> revertColumn = new ArrayList<Integer>();         // Keeps track of all blank spaces changed to "[ ] " so that they can be reverted
        ArrayList<Integer> revertRow = new ArrayList<Integer>();
        ArrayList<Integer> revertPieceColumn = new ArrayList<Integer>();    // Keeps track of all pieces changed to "xx* " so that they can be reverted
        ArrayList<Integer> revertPieceRow = new ArrayList<Integer>();
        int type;
        int moves;
        String pieceName = "";
        String displayMessage = ""; // What message is shown after displaying possible moves
        type = columns.get(column).get(row).getType();
        if(type == 1){                                          // PAWN (First Move Special Rules)
            moves = columns.get(column).get(row).getMoves();
            if(moves == 0){  // Since pawns can move 2 spaces on their first turn.
                if(columns.get(column).get(row).getName().substring(0,1).equals("P") && turn == 0){  // Player 1's pawns can only move downwards
                    int i = 1;
                    while(row+i <=7 && i<3 && columns.get(column).get(row+i).getType() == 0){ 
                        revertColumn.add(column);   
                        revertRow.add(row+i);
                        columns.get(column).get(row+i).setName("[ ] ");
                        i += 1;
                    }
                    if(column+1 <= 7 && row+1 <=7 && Character.isLowerCase(columns.get(column+1).get(row+1).getName().charAt(0))){   // Handles Pawn Attacks
                        revertPieceColumn.add(column+1);
                        revertPieceRow.add(row+1);
                        pieceName = columns.get(column+1).get(row+1).getName();
                        pieceName = pieceName.substring(0,2) + "* ";
                        columns.get(column+1).get(row+1).setName(pieceName);
                    }
                    if(column-1 >= 0 && row+1 <= 7 && Character.isLowerCase(columns.get(column-1).get(row+1).getName().charAt(0))){
                        revertPieceColumn.add(column-1);
                        revertPieceRow.add(row+1);
                        pieceName = columns.get(column-1).get(row+1).getName();
                        pieceName = pieceName.substring(0,2) + "* ";
                        columns.get(column-1).get(row+1).setName(pieceName);
                    }
                    displayMessage = "\r\n\r\nMove to: ";
                }else if(columns.get(column).get(row).getName().substring(0,1).equals("p") && turn == 1){  // Player2's pawns can only move upwards
                    int i = 1;
                    while(row-i >=0 && i<3 && columns.get(column).get(row-i).getType() == 0){
                        revertColumn.add(column);
                        revertRow.add(row-i);
                        columns.get(column).get(row-i).setName("[ ] ");
                        i += 1;
                    }
                    if(column+1 <= 7 && row-1 <=7 && Character.isUpperCase(columns.get(column+1).get(row-1).getName().charAt(0))){
                        revertPieceColumn.add(column+1);    
                        revertPieceRow.add(row-1);
                        pieceName = columns.get(column+1).get(row-1).getName();
                        pieceName = pieceName.substring(0,2) + "* ";
                        columns.get(column+1).get(row-1).setName(pieceName);
                    }
                    if(column-1 >= 0 && row-1 <= 7 && Character.isUpperCase(columns.get(column-1).get(row-1).getName().charAt(0))){
                        revertPieceColumn.add(column-1);
                        revertPieceRow.add(row-1);
                        pieceName = columns.get(column-1).get(row-1).getName();
                        pieceName = pieceName.substring(0,2) + "* ";
                        columns.get(column-1).get(row-1).setName(pieceName);
                    }
                    displayMessage = "\r\n\r\nMove to: ";
                }else{
                    displayMessage = ("\r\nSorry but that piece cannot be moved");
                }
            }else if(moves >0){ // Pawns (after their first move) can only move 1 space instead of 2
                if(columns.get(column).get(row).getName().substring(0,1).equals("P") && turn == 0){  // Player1's pawns can only move downwards.
                    int i = 1;
                    while(row+i <=7 && i<2 && columns.get(column).get(row+i).getType() == 0){
                        revertColumn.add(column);
                        revertRow.add(row+i);
                        columns.get(column).get(row+i).setName("[ ] ");
                        i += 1;
                    }
                    if(column+1 <= 7 && row+1 <=7 && Character.isLowerCase(columns.get(column+1).get(row+1).getName().charAt(0))){   
                        revertPieceColumn.add(column+1);    
                        revertPieceRow.add(row+1);
                        pieceName = columns.get(column+1).get(row+1).getName();
                        pieceName = pieceName.substring(0,2) + "* ";
                        columns.get(column+1).get(row+1).setName(pieceName);
                    }
                    if(column-1 >= 0 && row+1 <= 7 && Character.isLowerCase(columns.get(column-1).get(row+1).getName().charAt(0))){
                        revertPieceColumn.add(column-1);
                        revertPieceRow.add(row+1);
                        pieceName = columns.get(column-1).get(row+1).getName();
                        pieceName = pieceName.substring(0,2) + "* ";
                        columns.get(column-1).get(row+1).setName(pieceName);
                    }
                displayMessage = "\r\n\r\nMove to: ";
                }else if(columns.get(column).get(row).getName().substring(0,1).equals("p") && turn == 1){  // Player2's pawns can only move upwards.
                    int i = 1;
                    while(row-i >=0 && i<2 && columns.get(column).get(row-i).getType() == 0){
                        revertColumn.add(column);
                        revertRow.add(row-i);
                        columns.get(column).get(row-i).setName("[ ] ");
                        i += 1;
                    }
                    if(column+1 <= 7 && row-1 >= 0 && Character.isUpperCase(columns.get(column+1).get(row-1).getName().charAt(0))){
                        revertPieceColumn.add(column+1);    
                        revertPieceRow.add(row-1);
                        pieceName = columns.get(column+1).get(row-1).getName();
                        pieceName = pieceName.substring(0,2) + "* ";
                        columns.get(column+1).get(row-1).setName(pieceName);
                    }
                    if(column-1 >= 0 && row-1 >= 0 && Character.isUpperCase(columns.get(column-1).get(row-1).getName().charAt(0))){
                        revertPieceColumn.add(column-1);
                        revertPieceRow.add(row-1);
                        pieceName = columns.get(column-1).get(row-1).getName();
                        pieceName = pieceName.substring(0,2) + "* ";
                        columns.get(column-1).get(row-1).setName(pieceName);
                    }
                displayMessage = "\r\n\r\nMove to: ";
                }else{
                    displayMessage = ("\r\nSorry but that piece cannot be moved");
                }    
            }
        }else if(type == 2){    // ROOK (4 Loops For Cardinal Directions)
            if(columns.get(column).get(row).getName().substring(0,1).equals("R") && turn == 0){ // Is this Player1's piece?
                int i = 1;
                while(column+i <= 7 && columns.get(column+i).get(row).getType() == 0){  // Changes all possible blank space moves to "[ ] "
                    revertColumn.add(column+i);
                    revertRow.add(row);
                    columns.get(column+i).get(row).setName("[ ] ");
                    i += 1;
                }
                if(column+i <= 7 && Character.isLowerCase(columns.get(column+i).get(row).getName().charAt(0))){      // Changes all possible piece captures to "xx* "
                    revertPieceColumn.add(column+i);
                    revertPieceRow.add(row);
                    pieceName = columns.get(column+i).get(row).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column+i).get(row).setName(pieceName);
                }
                i = 1;
                while(column-i >= 0 && columns.get(column-i).get(row).getType() == 0){
                    revertColumn.add(column-i);
                    revertRow.add(row);
                    columns.get(column-i).get(row).setName("[ ] ");
                    i += 1;
                }
                if(column-i >= 0 && Character.isLowerCase(columns.get(column-i).get(row).getName().charAt(0))){      
                    revertPieceColumn.add(column-i);
                    revertPieceRow.add(row);
                    pieceName = columns.get(column-i).get(row).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column-i).get(row).setName(pieceName);
                }
                i = 1;
                while(row+i <= 7 && columns.get(column).get(row+i).getType() == 0){
                    revertColumn.add(column);
                    revertRow.add(row+i);
                    columns.get(column).get(row+i).setName("[ ] ");
                    i += 1;
                }
                if(row+i <= 7 && Character.isLowerCase(columns.get(column).get(row+i).getName().charAt(0))){      
                    revertPieceColumn.add(column);
                    revertPieceRow.add(row+i);
                    pieceName = columns.get(column).get(row+i).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column).get(row+i).setName(pieceName);
                }
                i = 1;
                while(row-i >= 0 && columns.get(column).get(row-i).getType() == 0){
                    revertColumn.add(column);
                    revertRow.add(row-i);
                    columns.get(column).get(row-i).setName("[ ] ");
                    i += 1;
                }
                if(row-i >= 0 && Character.isLowerCase(columns.get(column).get(row-i).getName().charAt(0))){      
                    revertPieceColumn.add(column);
                    revertPieceRow.add(row-i);
                    pieceName = columns.get(column).get(row-i).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column).get(row-i).setName(pieceName);
                }
                i = 1;
                displayMessage = "\r\n\r\nMove to: ";
            }else if(columns.get(column).get(row).getName().substring(0,1).equals("r") && turn == 1){ // Is this Player2's piece?
                int i = 1;
                while(column+i <= 7 && columns.get(column+i).get(row).getType() == 0){  // Searches East
                    revertColumn.add(column+i);
                    revertRow.add(row);
                    columns.get(column+i).get(row).setName("[ ] ");
                    i += 1;
                }
                if(column+i <= 7 && Character.isUpperCase(columns.get(column+i).get(row).getName().charAt(0))){      
                    revertPieceColumn.add(column+i);
                    revertPieceRow.add(row);
                    pieceName = columns.get(column+i).get(row).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column+i).get(row).setName(pieceName);
                }
                i = 1;
                while(column-i >= 0 && columns.get(column-i).get(row).getType() == 0){  // Searches West
                    revertColumn.add(column-i);
                    revertRow.add(row);
                    columns.get(column-i).get(row).setName("[ ] ");
                    i += 1;
                }
                if(column-i >= 0 && Character.isUpperCase(columns.get(column-i).get(row).getName().charAt(0))){      
                    revertPieceColumn.add(column-i);
                    revertPieceRow.add(row);
                    pieceName = columns.get(column-i).get(row).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column-i).get(row).setName(pieceName);
                }
                i = 1;
                while(row+i <= 7 && columns.get(column).get(row+i).getType() == 0){ // Searches South
                    revertColumn.add(column);
                    revertRow.add(row+i);
                    columns.get(column).get(row+i).setName("[ ] ");
                    i += 1;
                }
                if(row+i <= 7 && Character.isUpperCase(columns.get(column).get(row+i).getName().charAt(0))){     
                    revertPieceColumn.add(column);
                    revertPieceRow.add(row+i);
                    pieceName = columns.get(column).get(row+i).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column).get(row+i).setName(pieceName);
                }
                i = 1;
                while(row-i >= 0 && columns.get(column).get(row-i).getType() == 0){ // Searches North
                    revertColumn.add(column);
                    revertRow.add(row-i);
                    columns.get(column).get(row-i).setName("[ ] ");
                    i += 1;
                }
                if(row-i >= 0 && Character.isUpperCase(columns.get(column).get(row-i).getName().charAt(0))){      
                    revertPieceColumn.add(column);
                    revertPieceRow.add(row-i);
                    pieceName = columns.get(column).get(row-i).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column).get(row-i).setName(pieceName);
                }
                displayMessage = "\r\n\r\nMove to: ";                
            }else{
                displayMessage = "\r\nSorry but that piece cannot be moved";
            }
        }else if(type == 3){    // KNIGHT (No loops, complicated moves [8 possible] + ability to jump over piece)
            if(columns.get(column).get(row).getName().substring(0,1).equals("K") && turn == 0){
                if(column+1 <= 7 && row-2 >= 0 && columns.get(column+1).get(row-2).getType() == 0){ // If the move lands in a blank space, change to "[ ] "
                    revertColumn.add(column+1);
                    revertRow.add(row-2);
                    columns.get(column+1).get(row-2).setName("[ ] ");
                }
                if(column+1 <= 7 && row-2 >= 0 && Character.isLowerCase(columns.get(column+1).get(row-2).getName().charAt(0))){ // If the move lands in an enemy piece, change to "[ ] "
                    revertPieceColumn.add(column+1);
                    revertPieceRow.add(row-2);
                    pieceName = columns.get(column+1).get(row-2).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column+1).get(row-2).setName(pieceName);
                }
                if(column+2 <= 7 && row-1 >= 0 && columns.get(column+2).get(row-1).getType() == 0){
                    revertColumn.add(column+2);
                    revertRow.add(row-1);
                    columns.get(column+2).get(row-1).setName("[ ] ");
                }
                if(column+2 <= 7 && row-1 >= 0 && Character.isLowerCase(columns.get(column+2).get(row-1).getName().charAt(0))){
                    revertPieceColumn.add(column+2);
                    revertPieceRow.add(row-1);
                    pieceName = columns.get(column+2).get(row-1).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column+2).get(row-1).setName(pieceName);
                }
                if(column+2 <= 7 && row+1 <= 7 && columns.get(column+2).get(row+1).getType() == 0){
                    revertColumn.add(column+2);
                    revertRow.add(row+1);
                    columns.get(column+2).get(row+1).setName("[ ] ");
                }
                if(column+2 <= 7 && row+1 <= 7 && Character.isLowerCase(columns.get(column+2).get(row+1).getName().charAt(0))){
                    revertPieceColumn.add(column+2);
                    revertPieceRow.add(row+1);
                    pieceName = columns.get(column+2).get(row+1).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column+2).get(row+1).setName(pieceName);
                }
                if(column+1 <= 7 && row+2 <= 7 && columns.get(column+1).get(row+2).getType() == 0){
                    revertColumn.add(column+1);
                    revertRow.add(row+2);
                    columns.get(column+1).get(row+2).setName("[ ] ");
                }
                if(column+1 <= 7 && row+2 <= 7 && Character.isLowerCase(columns.get(column+1).get(row+2).getName().charAt(0))){
                    revertPieceColumn.add(column+1);
                    revertPieceRow.add(row+2);
                    pieceName = columns.get(column+1).get(row+2).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column+1).get(row+2).setName(pieceName);
                }
                if(column-1 >= 0 && row+2 <= 7 && columns.get(column-1).get(row+2).getType() == 0){
                    revertColumn.add(column-1);
                    revertRow.add(row+2);
                    columns.get(column-1).get(row+2).setName("[ ] ");
                }
                if(column-1 >= 0 && row+2 <= 7 && Character.isLowerCase(columns.get(column-1).get(row+2).getName().charAt(0))){
                    revertPieceColumn.add(column-1);
                    revertPieceRow.add(row+2);
                    pieceName = columns.get(column-1).get(row+2).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column-1).get(row+2).setName(pieceName);
                }
                if(column-2 >= 0 && row+1 <= 7 && columns.get(column-2).get(row+1).getType() == 0){
                    revertColumn.add(column-2);
                    revertRow.add(row+1);
                    columns.get(column-2).get(row+1).setName("[ ] ");
                }
                if(column-2 >= 0 && row+1 <= 7 && Character.isLowerCase(columns.get(column-2).get(row+1).getName().charAt(0))){
                    revertPieceColumn.add(column-2);
                    revertPieceRow.add(row+1);
                    pieceName = columns.get(column-2).get(row+1).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column-2).get(row+1).setName(pieceName);
                }
                if(column-2 >= 0 && row-1 >= 0 && columns.get(column-2).get(row-1).getType() == 0){
                    revertColumn.add(column-2);
                    revertRow.add(row-1);
                    columns.get(column-2).get(row-1).setName("[ ] ");
                }
                if(column-2 >= 0 && row-1 >= 0 && Character.isLowerCase(columns.get(column-2).get(row-1).getName().charAt(0))){
                    revertPieceColumn.add(column-2);
                    revertPieceRow.add(row-1);
                    pieceName = columns.get(column-2).get(row-1).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column-2).get(row-1).setName(pieceName);
                }
                if(column-1 >= 0 && row-2 >= 0 && columns.get(column-2).get(row-2).getType() == 0){
                    revertColumn.add(column-1);
                    revertRow.add(row-2);
                    columns.get(column-1).get(row-2).setName("[ ] ");
                }
                if(column-1 >= 0 && row-2 >= 0 && Character.isLowerCase(columns.get(column-1).get(row-2).getName().charAt(0))){
                    revertPieceColumn.add(column-1);
                    revertPieceRow.add(row-2);
                    pieceName = columns.get(column-1).get(row-2).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column-1).get(row-2).setName(pieceName);
                }
                displayMessage = "\r\n\r\nMove to: ";
            }else if(columns.get(column).get(row).getName().substring(0,1).equals("k") && turn == 1){   // Is this Player2's piece?
                if(column+1 <= 7 && row-2 >= 0 && columns.get(column+1).get(row-2).getType() == 0){ // If the move lands in a blank space, change to "[ ] "
                    revertColumn.add(column+1);
                    revertRow.add(row-2);
                    columns.get(column+1).get(row-2).setName("[ ] ");
                }
                if(column+1 <= 7 && row-2 >= 0 && Character.isUpperCase(columns.get(column+1).get(row-2).getName().charAt(0))){ // If the move lands on a capturable piece, change to "xx* "
                    revertPieceColumn.add(column+1);
                    revertPieceRow.add(row-2);
                    pieceName = columns.get(column+1).get(row-2).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column+1).get(row-2).setName(pieceName);
                }
                if(column+2 <= 7 && row-1 >= 0 && columns.get(column+2).get(row-1).getType() == 0){
                    revertColumn.add(column+2);
                    revertRow.add(row-1);
                    columns.get(column+2).get(row-1).setName("[ ] ");
                }
                if(column+2 <= 7 && row-1 >= 0 && Character.isUpperCase(columns.get(column+2).get(row-1).getName().charAt(0))){
                    revertPieceColumn.add(column+2);
                    revertPieceRow.add(row-1);
                    pieceName = columns.get(column+2).get(row-1).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column+2).get(row-1).setName(pieceName);
                }
                if(column +2 <= 7 && row+1 <= 7 && columns.get(column+2).get(row+1).getType() == 0){
                    revertColumn.add(column+2);
                    revertRow.add(row+1);
                    columns.get(column+2).get(row+1).setName("[ ] ");
                }
                if(column+2 <= 7 && row+1 <= 7 && Character.isUpperCase(columns.get(column+2).get(row+1).getName().charAt(0))){
                    revertPieceColumn.add(column+2);
                    revertPieceRow.add(row+1);
                    pieceName = columns.get(column+2).get(row+1).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column+2).get(row+1).setName(pieceName);
                }
                if(column+1 <= 7 && row+2 <= 7 && columns.get(column+1).get(row+2).getType() == 0){
                    revertColumn.add(column+1);
                    revertRow.add(row+2);
                    columns.get(column+1).get(row+2).setName("[ ] ");
                }
                if(column+1 <= 7 && row+2 <= 7 && Character.isUpperCase(columns.get(column+1).get(row+2).getName().charAt(0))){
                    revertPieceColumn.add(column+1);
                    revertPieceRow.add(row+2);
                    pieceName = columns.get(column+1).get(row+2).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column+1).get(row+2).setName(pieceName);
                }
                if(column-1 >= 0 && row+2 <= 7 && columns.get(column-1).get(row+2).getType() == 0){
                    revertColumn.add(column-1);
                    revertRow.add(row+2);
                    columns.get(column-1).get(row+2).setName("[ ] ");
                }
                if(column-1 >= 0 && row+2 <= 7 && Character.isUpperCase(columns.get(column-1).get(row+2).getName().charAt(0))){
                    revertPieceColumn.add(column-1);
                    revertPieceRow.add(row+2);
                    pieceName = columns.get(column-1).get(row+2).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column-1).get(row+2).setName(pieceName);
                }
                if(column-2 >= 0 && row+1 <= 7 && columns.get(column-2).get(row+1).getType() == 0){
                    revertColumn.add(column-2);
                    revertRow.add(row+1);
                    columns.get(column-2).get(row+1).setName("[ ] ");
                }
                if(column-2 >= 0 && row+1 <= 7 && Character.isUpperCase(columns.get(column-2).get(row+1).getName().charAt(0))){
                    revertPieceColumn.add(column-2);
                    revertPieceRow.add(row+1);
                    pieceName = columns.get(column-2).get(row+1).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column-2).get(row+1).setName(pieceName);
                }
                if(column-2 >= 0 && row-1 >= 0 && columns.get(column-2).get(row-1).getType() == 0){
                    revertColumn.add(column-2);
                    revertRow.add(row-1);
                    columns.get(column-2).get(row-1).setName("[ ] ");
                }
                if(column-2 >= 0 && row-1 >= 0 && Character.isUpperCase(columns.get(column-2).get(row-1).getName().charAt(0))){
                    revertPieceColumn.add(column-2);
                    revertPieceRow.add(row-1);
                    pieceName = columns.get(column-2).get(row-1).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column-2).get(row-1).setName(pieceName);
                }
                if(column-1 >= 0 && row-2 >= 0 && columns.get(column-1).get(row-2).getType() == 0){
                    revertColumn.add(column-1);
                    revertRow.add(row-2);
                    columns.get(column-1).get(row-2).setName("[ ] ");
                }
                if(column-1 >= 0 && row-2 >= 0 && Character.isUpperCase(columns.get(column-1).get(row-2).getName().charAt(0))){
                    revertPieceColumn.add(column-1);
                    revertPieceRow.add(row-2);
                    pieceName = columns.get(column-1).get(row-2).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column-1).get(row-2).setName(pieceName);
                }
                displayMessage = "\r\n\r\nMove to: ";
            }else{
                displayMessage = "\r\nSorry but that piece cannot be moved.";            
            }  
        }else if(type == 4){    // BISHOP (4 Diagonal Loops)
            if(columns.get(column).get(row).getName().substring(0,1).equals("B") && turn == 0){ // Is this Player1's piece?
                int i = 1;
                int j = 1;
                while(column+i <= 7 && row-j >= 0 && columns.get(column+i).get(row-j).getType() == 0){  // Changes all consecutive blank spaces in the diagonal to "[ ] "
                    revertColumn.add(column+i);
                    revertRow.add(row-j);
                    columns.get(column+i).get(row-j).setName("[ ] ");
                    i += 1;
                    j += 1;
                }
                if(column+i <= 7 && row-j >= 0 && Character.isLowerCase(columns.get(column+i).get(row-j).getName().charAt(0))){ // Changes first capturable piece in the diagonal to "xx* "
                    revertPieceColumn.add(column+i);
                    revertPieceRow.add(row-j);
                    pieceName = columns.get(column+i).get(row-j).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column+i).get(row-j).setName(pieceName);
                }
                i=1;
                j=1;
                while(column+i <= 7 && row+j <= 7 && columns.get(column+i).get(row+j).getType() == 0){
                    revertColumn.add(column+i);
                    revertRow.add(row+j);
                    columns.get(column+i).get(row+j).setName("[ ] ");
                    i += 1;
                    j += 1;
                }
                if(column+i <= 7 && row+j <=7 && Character.isLowerCase(columns.get(column+i).get(row+j).getName().charAt(0))){
                    revertPieceColumn.add(column+i);
                    revertPieceRow.add(row+j);
                    pieceName = columns.get(column+i).get(row+j).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column+i).get(row+j).setName(pieceName);
                }
                i=1;
                j=1;
                while(column-i >= 0 && row+j <= 7 && columns.get(column-i).get(row+j).getType() == 0){
                    revertColumn.add(column-i);
                    revertRow.add(row+j);
                    columns.get(column-i).get(row+j).setName("[ ] ");
                    i += 1;
                    j += 1;
                }
                if(column-i >= 0 && row+j <=7 && Character.isLowerCase(columns.get(column-i).get(row+j).getName().charAt(0))){
                    revertPieceColumn.add(column-i);
                    revertPieceRow.add(row+j);
                    pieceName = columns.get(column-i).get(row+j).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column-i).get(row+j).setName(pieceName);
                }
                i=1;
                j=1;
                while(column-i >= 0 && row-j >= 0 && columns.get(column-i).get(row-j).getType() == 0){
                    revertColumn.add(column-i);
                    revertRow.add(row-j);
                    columns.get(column-i).get(row-j).setName("[ ] ");
                    i += 1;
                    j += 1;
                }
                if(column-i >= 0 && row-j >= 0 && Character.isLowerCase(columns.get(column-i).get(row-j).getName().charAt(0))){
                    revertPieceColumn.add(column-i);
                    revertPieceRow.add(row-j);
                    pieceName = columns.get(column-i).get(row-j).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column-i).get(row-j).setName(pieceName);
                }
                displayMessage = "\r\n\r\nMove to: ";
            }else if(columns.get(column).get(row).getName().substring(0,1).equals("b")&& turn == 1){    // Is this Player2's piece?
                int i = 1;
                int j = 1;
                while(column+i <= 7 && row-j >= 0 && columns.get(column+i).get(row-j).getType() == 0){
                    revertColumn.add(column+i);
                    revertRow.add(row-j);
                    columns.get(column+i).get(row-j).setName("[ ] ");
                    i += 1;
                    j += 1;
                }
                if(column+i <= 7 && row-j >= 0 && Character.isUpperCase(columns.get(column+i).get(row-j).getName().charAt(0))){
                    revertPieceColumn.add(column+i);
                    revertPieceRow.add(row-j);
                    pieceName = columns.get(column+i).get(row-j).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column+i).get(row-j).setName(pieceName);
                }
                i=1;
                j=1;
                while(column+i <= 7 && row+j <= 7 && columns.get(column+i).get(row+j).getType() == 0){
                    revertColumn.add(column+i);
                    revertRow.add(row+j);
                    columns.get(column+i).get(row+j).setName("[ ] ");
                    i += 1;
                    j += 1;
                }
                if(column+i <= 7 && row+j <=7 && Character.isUpperCase(columns.get(column+i).get(row+j).getName().charAt(0))){
                    revertPieceColumn.add(column+i);
                    revertPieceRow.add(row+j);
                    pieceName = columns.get(column+i).get(row+j).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column+i).get(row+j).setName(pieceName);
                }
                i=1;
                j=1;
                while(column-i >= 0 && row+j <= 7 && columns.get(column-i).get(row+j).getType() == 0){
                    revertColumn.add(column-i);
                    revertRow.add(row+j);
                    columns.get(column+i).get(row+j).setName("[ ] ");
                    i += 1;
                    j += 1;
                }
                if(column-i >= 0 && row+j <=7 && Character.isUpperCase(columns.get(column-i).get(row+j).getName().charAt(0))){
                    revertPieceColumn.add(column-i);
                    revertPieceRow.add(row+j);
                    pieceName = columns.get(column-i).get(row+j).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column-i).get(row+j).setName(pieceName);
                }
                i=1;
                j=1;
                while(column-i >= 0 && row-j >= 0 && columns.get(column-1).get(row-1).getType() == 0){
                    revertColumn.add(column-i);
                    revertRow.add(row-j);
                    columns.get(column-i).get(row-j).setName("[ ] ");
                    i += 1;
                    j += 1;
                }
                if(column-i >= 0 && row-j >= 0 && Character.isUpperCase(columns.get(column-i).get(row-j).getName().charAt(0))){
                    revertPieceColumn.add(column-i);
                    revertPieceRow.add(row-j);
                    pieceName = columns.get(column-i).get(row-j).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column-i).get(row-j).setName(pieceName);
                }
                displayMessage = "\r\n\r\nMove to: ";
            }else{
                displayMessage = "\r\nSorry but that piece cannot be moved.";
            }
        }else if(type == 5){    // QUEEN (Bishop + Rook MoveSet)
            if(columns.get(column).get(row).getName().substring(0,1).equals("Q") && turn == 0){ // Is this Player1's piece?
                int i = 1;
                while(column+i <= 7 && columns.get(column+i).get(row).getType() == 0){
                    revertColumn.add(column+i);
                    revertRow.add(row);
                    columns.get(column+i).get(row).setName("[ ] ");
                    i += 1;
                }
                if(column+i <= 7 && Character.isLowerCase(columns.get(column+i).get(row).getName().charAt(0))){      
                    revertPieceColumn.add(column+i);
                    revertPieceRow.add(row);
                    pieceName = columns.get(column+i).get(row).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column+i).get(row).setName(pieceName);
                }
                i = 1;
                while(column-i >= 0 && columns.get(column-i).get(row).getType() == 0){
                    revertColumn.add(column-i);
                    revertRow.add(row);
                    columns.get(column-i).get(row).setName("[ ] ");
                    i += 1;
                }
                if(column-i >= 0 && Character.isLowerCase(columns.get(column-i).get(row).getName().charAt(0))){      
                    revertPieceColumn.add(column-i);
                    revertPieceRow.add(row);
                    pieceName = columns.get(column-i).get(row).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column-i).get(row).setName(pieceName);
                }
                i = 1;
                while(row+i <= 7 && columns.get(column).get(row+i).getType() == 0){
                    revertColumn.add(column);
                    revertRow.add(row+i);
                    columns.get(column).get(row+i).setName("[ ] ");
                    i += 1;
                }
                if(row+i <= 7 && Character.isLowerCase(columns.get(column).get(row+i).getName().charAt(0))){      
                    revertPieceColumn.add(column);
                    revertPieceRow.add(row+i);
                    pieceName = columns.get(column).get(row+i).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column).get(row+i).setName(pieceName);
                }
                i = 1;
                while(row-i >= 0 && columns.get(column).get(row-i).getType() == 0){
                    revertColumn.add(column);
                    revertRow.add(row-i);
                    columns.get(column).get(row-i).setName("[ ] ");
                    i += 1;
                }
                if(row-i >= 0 && Character.isLowerCase(columns.get(column).get(row-i).getName().charAt(0))){      
                    revertPieceColumn.add(column);
                    revertPieceRow.add(row-i);
                    pieceName = columns.get(column).get(row-i).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column).get(row-i).setName(pieceName);
                }
                i = 1;
                int j = 1;
                while(column+i <= 7 && row-j >= 0 && columns.get(column+i).get(row-j).getType() == 0){
                    revertColumn.add(column+i);
                    revertRow.add(row-j);
                    columns.get(column+i).get(row-j).setName("[ ] ");
                    i += 1;
                    j += 1;
                }
                if(column+i <= 7 && row-j >= 0 && Character.isLowerCase(columns.get(column+i).get(row-j).getName().charAt(0))){
                    revertPieceColumn.add(column+i);
                    revertPieceRow.add(row-j);
                    pieceName = columns.get(column+i).get(row-j).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column+i).get(row-j).setName(pieceName);
                }
                i=1;
                j=1;
                while(column+i <= 7 && row+j <= 7 && columns.get(column+i).get(row+j).getType() == 0){
                    revertColumn.add(column+i);
                    revertRow.add(row+j);
                    columns.get(column+i).get(row+j).setName("[ ] ");
                    i += 1;
                    j += 1;
                }
                if(column+i <= 7 && row+j <=7 && Character.isLowerCase(columns.get(column+i).get(row+j).getName().charAt(0))){
                    revertPieceColumn.add(column+i);
                    revertPieceRow.add(row+j);
                    pieceName = columns.get(column+i).get(row+j).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column+i).get(row+j).setName(pieceName);
                }
                i=1;
                j=1;
                while(column-i >= 0 && row+j <= 7 && columns.get(column-i).get(row+j).getType() == 0){
                    revertColumn.add(column-i);
                    revertRow.add(row+j);
                    columns.get(column-i).get(row+j).setName("[ ] ");
                    i += 1;
                    j += 1;
                }
                if(column-i >= 0 && row+j <=7 && Character.isLowerCase(columns.get(column-i).get(row+j).getName().charAt(0))){
                    revertPieceColumn.add(column-i);
                    revertPieceRow.add(row+j);
                    pieceName = columns.get(column-i).get(row+j).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column-i).get(row+j).setName(pieceName);
                }
                i=1;
                j=1;
                while(column-i >= 0 && row-j >= 0 && columns.get(column-1).get(row-1).getType() == 0){
                    revertColumn.add(column-i);
                    revertRow.add(row-j);
                    columns.get(column-i).get(row-j).setName("[ ] ");
                    i += 1;
                    j += 1;
                }
                if(column-i >= 0 && row-j >= 0 && Character.isLowerCase(columns.get(column-i).get(row-j).getName().charAt(0))){
                    revertPieceColumn.add(column-i);
                    revertPieceRow.add(row-j);
                    pieceName = columns.get(column-i).get(row-j).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column-i).get(row-j).setName(pieceName);
                }
                displayMessage = "\r\n\r\nMove to: ";
            }else if(columns.get(column).get(row).getName().substring(0,1).equals("q") && turn == 1){ // Is this Player2's piece?
                int i = 1;
                while(column+i <= 7 && columns.get(column+i).get(row).getType() == 0){
                    revertColumn.add(column+i);
                    revertRow.add(row);
                    columns.get(column+i).get(row).setName("[ ] ");
                    i += 1;
                }
                if(column+i <= 7 && Character.isUpperCase(columns.get(column+i).get(row).getName().charAt(0))){      
                    revertPieceColumn.add(column+i);
                    revertPieceRow.add(row);
                    pieceName = columns.get(column+i).get(row).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column+i).get(row).setName(pieceName);
                }
                i = 1;
                while(column-i >= 0 && columns.get(column-i).get(row).getType() == 0){
                    revertColumn.add(column-i);
                    revertRow.add(row);
                    columns.get(column-i).get(row).setName("[ ] ");
                    i += 1;
                }
                if(column-i >= 0 && Character.isUpperCase(columns.get(column-i).get(row).getName().charAt(0))){      
                    revertPieceColumn.add(column-i);
                    revertPieceRow.add(row);
                    pieceName = columns.get(column-i).get(row).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column-i).get(row).setName(pieceName);
                }
                i = 1;
                while(row+i <= 7 && columns.get(column).get(row+i).getType() == 0){
                    revertColumn.add(column);
                    revertRow.add(row+i);
                    columns.get(column).get(row+i).setName("[ ] ");
                    i += 1;
                }
                if(row+i <= 7 && Character.isUpperCase(columns.get(column).get(row+i).getName().charAt(0))){     
                    revertPieceColumn.add(column);
                    revertPieceRow.add(row+i);
                    pieceName = columns.get(column).get(row+i).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column).get(row+i).setName(pieceName);
                }
                i = 1;
                while(row-i >= 0 && columns.get(column).get(row-i).getType() == 0){
                    revertColumn.add(column);
                    revertRow.add(row-i);
                    columns.get(column).get(row-i).setName("[ ] ");
                    i += 1;
                }
                if(row-i >= 0 && Character.isUpperCase(columns.get(column).get(row-i).getName().charAt(0))){      
                    revertPieceColumn.add(column);
                    revertPieceRow.add(row-i);
                    pieceName = columns.get(column).get(row-i).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column).get(row-i).setName(pieceName);
                }
                i = 1;
                int j = 1;
                while(column+i <= 7 && row-j >= 0 && columns.get(column+i).get(row-j).getType() == 0){
                    revertColumn.add(column+i);
                    revertRow.add(row-j);
                    columns.get(column+i).get(row-j).setName("[ ] ");
                    i += 1;
                    j += 1;
                }
                if(column+i <= 7 && row-j >= 0 && Character.isUpperCase(columns.get(column+i).get(row-j).getName().charAt(0))){
                    revertPieceColumn.add(column+i);
                    revertPieceRow.add(row-j);
                    pieceName = columns.get(column+i).get(row-j).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column+i).get(row-j).setName(pieceName);
                }
                i=1;
                j=1;
                while(column+i <= 7 && row+j <= 7 && columns.get(column+i).get(row+j).getType() == 0){
                    revertColumn.add(column+i);
                    revertRow.add(row+j);
                    columns.get(column+i).get(row+j).setName("[ ] ");
                    i += 1;
                    j += 1;
                }
                if(column+i <= 7 && row+j <=7 && Character.isUpperCase(columns.get(column+i).get(row+j).getName().charAt(0))){
                    revertPieceColumn.add(column+i);
                    revertPieceRow.add(row+j);
                    pieceName = columns.get(column+i).get(row+j).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column+i).get(row+j).setName(pieceName);
                }
                i=1;
                j=1;
                while(column-i >= 0 && row+j <= 7 && columns.get(column-i).get(row+j).getType() == 0){
                    revertColumn.add(column-i);
                    revertRow.add(row+j);
                    columns.get(column+i).get(row+j).setName("[ ] ");
                    i += 1;
                    j += 1;
                }
                if(column-i >= 0 && row+j <=7 && Character.isUpperCase(columns.get(column-i).get(row+j).getName().charAt(0))){
                    revertPieceColumn.add(column-i);
                    revertPieceRow.add(row+j);
                    pieceName = columns.get(column-i).get(row+j).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column-i).get(row+j).setName(pieceName);
                }
                i=1;
                j=1;
                while(column-i >= 0 && row-j >= 0 && columns.get(column-1).get(row-1).getType() == 0){
                    revertColumn.add(column-i);
                    revertRow.add(row-j);
                    columns.get(column-i).get(row-j).setName("[ ] ");
                    i += 1;
                    j += 1;
                }
                if(column-i >= 0 && row-j >= 0 && Character.isUpperCase(columns.get(column-i).get(row-j).getName().charAt(0))){
                    revertPieceColumn.add(column-i);
                    revertPieceRow.add(row-j);
                    pieceName = columns.get(column-i).get(row-j).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column-i).get(row-j).setName(pieceName);
                }
                displayMessage = "\r\n\r\nMove to: ";
            }
        }else if(type == 6){    // KING (No loops, 1 space any direction)
            if(columns.get(column).get(row).getName().substring(0,2).equals("KN") && turn == 0){    // Is this Player1's piece?
                if(column+0 <= 7 && row-1 >= 0 && columns.get(column+0).get(row-1).getType() == 0){ // North
                    revertColumn.add(column+0);
                    revertRow.add(row-1);
                    columns.get(column+0).get(row-1).setName("[ ] ");
                }
                if(column+0 <= 7 && row-1 >= 0 && Character.isLowerCase(columns.get(column+0).get(row-1).getName().charAt(0))){
                    revertPieceColumn.add(column+0);
                    revertPieceRow.add(row-1);
                    pieceName = columns.get(column+0).get(row-1).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column+0).get(row-1).setName(pieceName);
                }
                if(column+1 <= 7 && row-1 >= 0 && columns.get(column+1).get(row-1).getType() == 0){ // North-East
                    revertColumn.add(column+1);
                    revertRow.add(row-1);
                    columns.get(column+1).get(row-1).setName("[ ] ");
                }
                if(column+1 <= 7 && row-1 >= 0 && Character.isLowerCase(columns.get(column+1).get(row-1).getName().charAt(0))){
                    revertPieceColumn.add(column+1);
                    revertPieceRow.add(row-1);
                    pieceName = columns.get(column+1).get(row-1).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column+1).get(row-1).setName(pieceName);
                }
                if(column+1 <= 7 && row+0 <= 7 && columns.get(column+1).get(row+0).getType() == 0){ // East
                    revertColumn.add(column+1);
                    revertRow.add(row+0);
                    columns.get(column+1).get(row+0).setName("[ ] ");
                }
                if(column+1 <= 7 && row+0 <= 7 && Character.isLowerCase(columns.get(column+1).get(row+0).getName().charAt(0))){
                    revertPieceColumn.add(column+1);
                    revertPieceRow.add(row+0);
                    pieceName = columns.get(column+1).get(row+0).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column+1).get(row+0).setName(pieceName);
                }
                if(column+1 <= 7 && row+1 <= 7 && columns.get(column+1).get(row+1).getType() == 0){ // South-East
                    revertColumn.add(column+1);
                    revertRow.add(row+1);
                    columns.get(column+1).get(row+1).setName("[ ] ");
                }
                if(column+1 <= 7 && row+1 <= 7 && Character.isLowerCase(columns.get(column+1).get(row+1).getName().charAt(0))){
                    revertPieceColumn.add(column+1);
                    revertPieceRow.add(row+1);
                    pieceName = columns.get(column+1).get(row+1).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column+1).get(row+1).setName(pieceName);
                }
                if(column+0 <= 7 && row+1 <= 7 && columns.get(column+0).get(row+1).getType() == 0){ // South
                    revertColumn.add(column+0);
                    revertRow.add(row+1);
                    columns.get(column+0).get(row+1).setName("[ ] ");
                }
                if(column+0 <= 7 && row+1 <= 7 && Character.isLowerCase(columns.get(column+0).get(row+1).getName().charAt(0))){
                    revertPieceColumn.add(column+0);
                    revertPieceRow.add(row+1);
                    pieceName = columns.get(column+0).get(row+1).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column+0).get(row+1).setName(pieceName);
                }
                if(column-1 >= 0 && row+1 <= 7 && columns.get(column-1).get(row+1).getType() == 0){ // South-West
                    revertColumn.add(column-1);
                    revertRow.add(row+1);
                    columns.get(column-1).get(row+1).setName("[ ] ");
                }
                if(column-1 >= 0 && row+1 <= 7 && Character.isLowerCase(columns.get(column-1).get(row+1).getName().charAt(0))){
                    revertPieceColumn.add(column-1);
                    revertPieceRow.add(row+1);
                    pieceName = columns.get(column-1).get(row+1).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column-1).get(row+1).setName(pieceName);
                }
                if(column-1 >= 0 && row+0 <= 7 && columns.get(column-1).get(row+0).getType() == 0){ // West
                    revertColumn.add(column-1);
                    revertRow.add(row+0);
                    columns.get(column-1).get(row+0).setName("[ ] ");
                }
                if(column-1 >= 0 && row+0 <= 7 && Character.isLowerCase(columns.get(column-1).get(row+0).getName().charAt(0))){
                    revertPieceColumn.add(column-1);
                    revertPieceRow.add(row+0);
                    pieceName = columns.get(column-1).get(row+0).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column-1).get(row+0).setName(pieceName);
                }
                if(column-1 >= 0 && row-1 >= 0 && columns.get(column-1).get(row-1).getType() == 0){ // North-West
                    revertColumn.add(column-1);
                    revertRow.add(row-1);
                    columns.get(column-1).get(row-1).setName("[ ] ");
                }
                if(column-1 >= 0 && row-1 >= 0 && Character.isLowerCase(columns.get(column-1).get(row-1).getName().charAt(0))){
                    revertPieceColumn.add(column-1);
                    revertPieceRow.add(row-1);
                    pieceName = columns.get(column-1).get(row-1).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column-1).get(row-1).setName(pieceName);
                }
                displayMessage = "\r\n\r\nMove to: ";
            }else if(columns.get(column).get(row).getName().substring(0,2).equals("kn") && turn == 1){ // Is this Player2's piece?
                if(column+0 <= 7 && row-1 >= 0 && columns.get(column+0).get(row-1).getType() == 0){ // North
                    revertColumn.add(column+0);
                    revertRow.add(row-1);
                    columns.get(column+0).get(row-1).setName("[ ] ");
                }
                if(column+0 <= 7 && row-1 >= 0 && Character.isLowerCase(columns.get(column+0).get(row-1).getName().charAt(0))){
                    revertPieceColumn.add(column+0);
                    revertPieceRow.add(row-1);
                    pieceName = columns.get(column+0).get(row-1).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column+0).get(row-1).setName(pieceName);
                }
                if(column+1 <= 7 && row-1 >= 0 && columns.get(column+1).get(row-1).getType() == 0){ // North-East
                    revertColumn.add(column+1);
                    revertRow.add(row-1);
                    columns.get(column+1).get(row-1).setName("[ ] ");
                }
                if(column+1 <= 7 && row-1 >= 0 && Character.isUpperCase(columns.get(column+1).get(row-1).getName().charAt(0))){
                    revertPieceColumn.add(column+1);
                    revertPieceRow.add(row-1);
                    pieceName = columns.get(column+1).get(row-1).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column+1).get(row-1).setName(pieceName);
                }
                if(column+1 <= 7 && row+0 <= 7 && columns.get(column+1).get(row+0).getType() == 0){ // East
                    revertColumn.add(column+1);
                    revertRow.add(row+0);
                    columns.get(column+1).get(row+0).setName("[ ] ");
                }
                if(column+1 <= 7 && row+0 <= 7 && Character.isUpperCase(columns.get(column+1).get(row+0).getName().charAt(0))){
                    revertPieceColumn.add(column+1);
                    revertPieceRow.add(row+0);
                    pieceName = columns.get(column+1).get(row+0).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column+1).get(row+0).setName(pieceName);
                }
                if(column+1 <= 7 && row+1 <= 7 && columns.get(column+1).get(row+1).getType() == 0){ // South-East
                    revertColumn.add(column+1);
                    revertRow.add(row+1);
                    columns.get(column+1).get(row+1).setName("[ ] ");
                }
                if(column+1 <= 7 && row+1 <= 7 && Character.isUpperCase(columns.get(column+1).get(row+1).getName().charAt(0))){
                    revertPieceColumn.add(column+1);
                    revertPieceRow.add(row+1);
                    pieceName = columns.get(column+1).get(row+1).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column+1).get(row+1).setName(pieceName);
                }
                if(column+0 <= 7 && row+1 <= 7 && columns.get(column+0).get(row+1).getType() == 0){ // South
                    revertColumn.add(column+0);
                    revertRow.add(row+1);
                    columns.get(column+0).get(row+1).setName("[ ] ");
                }
                if(column+0 <= 7 && row+1 <= 7 && Character.isUpperCase(columns.get(column+0).get(row+1).getName().charAt(0))){
                    revertPieceColumn.add(column+0);
                    revertPieceRow.add(row+1);
                    pieceName = columns.get(column+0).get(row+1).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column+0).get(row+1).setName(pieceName);
                }
                if(column-1 >= 0 && row+1 <= 7 && columns.get(column-1).get(row+1).getType() == 0){ // South-West
                    revertColumn.add(column-1);
                    revertRow.add(row+1);
                    columns.get(column-1).get(row+1).setName("[ ] ");
                }
                if(column-1 >= 0 && row+1 <= 7 && Character.isUpperCase(columns.get(column-1).get(row+1).getName().charAt(0))){
                    revertPieceColumn.add(column-1);
                    revertPieceRow.add(row+1);
                    pieceName = columns.get(column-1).get(row+1).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column-1).get(row+1).setName(pieceName);
                }
                if(column-1 >= 0 && row+0 <= 7 && columns.get(column-1).get(row+0).getType() == 0){ // West
                    revertColumn.add(column-1);
                    revertRow.add(row+0);
                    columns.get(column-1).get(row+0).setName("[ ] ");
                }
                if(column-1 >= 0 && row+0 <= 7 && Character.isUpperCase(columns.get(column-1).get(row+0).getName().charAt(0))){
                    revertPieceColumn.add(column-1);
                    revertPieceRow.add(row+0);
                    pieceName = columns.get(column-1).get(row+0).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column-1).get(row+0).setName(pieceName);
                }
                if(column-1 >= 0 && row-1 >= 0 && columns.get(column-1).get(row-1).getType() == 0){ // North-West
                    revertColumn.add(column-1);
                    revertRow.add(row-1);
                    columns.get(column-1).get(row-1).setName("[ ] ");
                }
                if(column-1 >= 0 && row-1 >= 0 && Character.isUpperCase(columns.get(column-1).get(row-1).getName().charAt(0))){
                    revertPieceColumn.add(column-1);
                    revertPieceRow.add(row-1);
                    pieceName = columns.get(column-1).get(row-1).getName();
                    pieceName = pieceName.substring(0,2) + "* ";
                    columns.get(column-1).get(row-1).setName(pieceName);
                }
                displayMessage = "\r\n\r\nMove to: ";
            }else{
                displayMessage = "\r\nSorry but that piece cannot be moved.";
            }
        }
        

        display(); // Updated with possible moves)
        Scanner kb = new Scanner(System.in);
        String moveTo = "";
        if(displayMessage.equals("\r\n\r\nMove to: ")){
            System.out.print(displayMessage);
        }
        int flag = 0; // Has there been a move conducted this turn?
        while(flag == 0){   // Asks the user to make a move
            if(displayMessage.equals("\r\n\r\nMove to: ")){
                moveTo = kb.nextLine();
                if(moveTo.equals("00")){
                    break;
                }
                if(moveTo.length() == 2 && (moveTo.substring(0,1).equalsIgnoreCase("a") || moveTo.substring(0,1).equalsIgnoreCase("b") || moveTo.substring(0,1).equalsIgnoreCase("c") || moveTo.substring(0,1).equalsIgnoreCase("d") || moveTo.substring(0,1).equalsIgnoreCase("e") || moveTo.substring(0,1).equalsIgnoreCase("f") || moveTo.substring(0,1).equalsIgnoreCase("g") || moveTo.substring(0,1).equalsIgnoreCase("h")) && (moveTo.substring(1,2).equals("0") || moveTo.substring(1,2).equals("1") || moveTo.substring(1,2).equals("2") || moveTo.substring(1,2).equals("3") || moveTo.substring(1,2).equals("4") || moveTo.substring(1,2).equals("5") || moveTo.substring(1,2).equals("6") || moveTo.substring(1,2).equals("7"))){
                   flag = movePiece(moveTo.substring(0,1), moveTo.substring(1,2), column, row);
                   break;
                }else{
                    System.out.println("That is not a valid location (Enter '00' to go back)");
                    System.out.print("Move to: ");
                }
            }else{
                flag = 1;
            }
        }
        for(int i = 0; i<revertColumn.size(); i++){      // Reverts all "[ ] " back to " x  "
            if(columns.get(revertColumn.get(i)).get(revertRow.get(i)).getName().equals("[ ] ")){    
                columns.get(revertColumn.get(i)).get(revertRow.get(i)).setName(" x  ");
            }
        }
        for(int i = 0; i<revertPieceColumn.size(); i++){ // Reverts all "xx* " back to "xx  "
            if(columns.get(revertPieceColumn.get(i)).get(revertPieceRow.get(i)).getName().substring(2,3).equals("*")){
                String nameCopy = columns.get(revertPieceColumn.get(i)).get(revertPieceRow.get(i)).getName();
                nameCopy = nameCopy.substring(0,2) + "  ";
                columns.get(revertPieceColumn.get(i)).get(revertPieceRow.get(i)).setName(nameCopy);
            }
        }
        revertColumn.clear();   // Resets for next turn
        revertRow.clear();
        revertPieceColumn.clear();
        revertPieceRow.clear();
        display();
        if(flag == 2){  // If Player2's king has been captured (returned from movePiece();)
            System.out.println("\r\n\r\nCONGRATULATIONS! PLAYER ONE HAS WON THE GAME!");
            System.exit(1);
        }else if(flag == 3){    // If Player1's king has been captured (returned from movePiece();)
            System.out.println("\r\n\r\nCONGRATULATIONS! PLAYER TWO HAS WON THE GAME!");
            System.exit(1);
        }
        if(!displayMessage.equals("\r\n\r\nMove to: ")){    // If any errors has occured, display them here after the new board
            System.out.println(displayMessage);
        }
    }
        

    
   /**
    * Since showMoves() has already validated if a move is possible, movePiece()
    * only has to take the selected piece and move it into the new position and
    * delete it from the old location.  Also checks to see if a pawn has crossed
    * the board, as well as win-conditions.
    * 
    * @param newColumn: x-coordinate to be moved to
    * @param newRow: y-coordinate to be moved to
    * @param oldColumn: x-coordinate piece is currently at
    * @param oldRow: y-coordinate piece is currently at
    * @return 
    */
    private int movePiece(String newColumn, String newRow, Integer oldColumn, Integer oldRow){
        int newRowCoordinate = Integer.parseInt(newRow);
        int newColumnCoordinate = -1;
        
        if(newColumn.equalsIgnoreCase("a")){    // A player moves using "(A-H),(0-7)", there is no coordinate(a)
            newColumnCoordinate = 0;}
        if(newColumn.equalsIgnoreCase("b")){
            newColumnCoordinate = 1;}
        if(newColumn.equalsIgnoreCase("c")){
            newColumnCoordinate = 2;}
        if(newColumn.equalsIgnoreCase("d")){
            newColumnCoordinate = 3;}
        if(newColumn.equalsIgnoreCase("e")){
            newColumnCoordinate = 4;}
        if(newColumn.equalsIgnoreCase("f")){
            newColumnCoordinate = 5;}
        if(newColumn.equalsIgnoreCase("g")){
            newColumnCoordinate = 6;}
        if(newColumn.equalsIgnoreCase("h")){
            newColumnCoordinate = 7;}
        
        if(columns.get(newColumnCoordinate).get(newRowCoordinate).getName().equals("[ ] ") || columns.get(newColumnCoordinate).get(newRowCoordinate).getName().substring(2,3).equals("*")){ // If the move is possible
            ChessPiece maybeKing = new ChessPiece(columns.get(newColumnCoordinate).get(newRowCoordinate));
            ChessPiece toCopy = new ChessPiece(columns.get(oldColumn).get(oldRow)); // Pulls it out
            columns.get(newColumnCoordinate).set(newRowCoordinate, toCopy);         // Puts it in
            columns.get(oldColumn).set(oldRow, new ChessPiece());                   // Erases old
            int oldMoves = toCopy.getMoves();
            oldMoves += 1;
            toCopy.setMoves(oldMoves);  
            
            if(turn == 0){
                turn = 1;}
            else{
                turn = 0;}
            
            if(maybeKing.getType() == 6){                                           // Are you capturing a king?
                if(turn == 0){                    
                    return 2;               // Player1 has won
                }else if (turn == 1){
                    return 3;               // Player2 has won
                }
            }
            
            int numNewPieces = 3;
            if(newRowCoordinate == 0 && turn == 0 && toCopy.getType() == 1){      // Player2 made it to other side with pawn
                Scanner kb = new Scanner(System.in);
                System.out.print("\r\nWhich piece would you like to substitute for? ");
                while(true){
                    String newPiece = kb.nextLine();
                    if(newPiece.equalsIgnoreCase("Rook")){
                        columns.get(newColumnCoordinate).set(newRowCoordinate, new ChessPiece(2, ("r" + numNewPieces + "  ")));
                        numNewPieces += 1;        
                        break;
                    }else if(newPiece.equalsIgnoreCase("Knight")){
                        columns.get(newColumnCoordinate).set(newRowCoordinate, new ChessPiece(3, ("k" + numNewPieces + "  ")));
                        numNewPieces += 1;
                        break;
                    }else if(newPiece.equalsIgnoreCase("Bishop")){
                        columns.get(newColumnCoordinate).set(newRowCoordinate, new ChessPiece(4, ("b" + numNewPieces + "  ")));
                        numNewPieces += 1;
                        break;
                    }else if(newPiece.equalsIgnoreCase("Queen")){
                        columns.get(newColumnCoordinate).set(newRowCoordinate, new ChessPiece(5, ("q" + numNewPieces + "  ")));
                        numNewPieces += 1;
                        break;
                    }else{
                        System.out.print("Sorry but that piece cannot be substituted. Please select from [Rook, Knight, Bishop, Queen]: ");
                    }  
                }                
            }else if(newRowCoordinate == 7 && turn == 1 && toCopy.getType() == 1){   // Player1 made it to other side with pawn
                Scanner kb = new Scanner(System.in);
                System.out.print("\r\nWhich piece would you like to substitute for? ");
                while(true){
                    String newPiece = kb.nextLine();
                    if(newPiece.equalsIgnoreCase("Rook")){
                        columns.get(newColumnCoordinate).set(newRowCoordinate, new ChessPiece(2, ("R" + numNewPieces + "  ")));
                        numNewPieces += 1;
                        break;
                    }else if(newPiece.equalsIgnoreCase("Knight")){
                        columns.get(newColumnCoordinate).set(newRowCoordinate, new ChessPiece(3, ("K" + numNewPieces + "  ")));
                        numNewPieces += 1;
                        break;
                    }else if(newPiece.equalsIgnoreCase("Bishop")){
                        columns.get(newColumnCoordinate).set(newRowCoordinate, new ChessPiece(4, ("B" + numNewPieces + "  ")));
                        numNewPieces += 1;
                        break;
                    }else if(newPiece.equalsIgnoreCase("Queen")){
                        columns.get(newColumnCoordinate).set(newRowCoordinate, new ChessPiece(5, ("Q" + numNewPieces + "  ")));
                        numNewPieces += 1;
                        break;
                    }else{
                        System.out.print("Sorry but that piece cannot be substituted. Please select from [Rook, Knight, Bishop, Queen]: ");
                    }  
                }       
            }
            
            return 1;   // Yes a move has been made
        }else{
            System.out.println("Cannot move there.");
            return 0;   // No a move has not been made
        }
    }
    
}

