package chess.driver;

/**
 * Having done this before we went over inheritance, this is by far not the most
 * effective method. All objects in the game (blank spaces, pawns, rooks, etc.) 
 * are all considered ChessPieces, albeit with a different 'type' field as shown 
 * below.  The ChessPiece class is used to store the type, name, and number of moves
 * a piece has made (useful for pawns). All objects should be created and moved
 * through the use of ChessBoard methods.
 * 
 * @author Randyll Bearer
 */
public class ChessPiece {
    private int type;       //6=king, 5=queen, 4=bishop, 3=knight, 2=rook, 1=pawn, 0=Blank
    private String name;    // Must be less than 4 Characters, preserve format
    private int moves;      // Can a pawn move one block or two this turn?
    
    /**
     * Initializer constructor, should be used if wanting to create
     * a non-copy or non-blankspace.
     * 
     * @param typeOfPiece:  Int value denoting what value the 'type' field should be given, values and descriptions are given above.
     * @param nameOfPiece:  The name which should be given to the piece, must be no more than four characters.
     */
    public ChessPiece(int typeOfPiece, String nameOfPiece){         
        setType(typeOfPiece);
        setName(nameOfPiece);
        setMoves(0);
    }
    
    /**
     * No-Args constructor, should be used to create blank-spaces.
     */
    public ChessPiece(){                                            
        setType(0);
        setName(" x  ");
        setMoves(0);
    }
    
    /**
     * Copy-Contructor, creates a new ChessPiece with the field values of another ChessPiece.
     * 
     * @param toCopy: ChessPiece object you wish to copy. 
     */
    public ChessPiece(ChessPiece toCopy){                          
        setType(toCopy.getType());
        setName(toCopy.getName());
        setMoves(toCopy.getMoves());
    }
    
    /**
     * Mutator method for the 'type' field. 
     * 
     * @param typeToSet: The new int value to set 'type' field equal to (must be between 0-6)
     */
    public void setType(int typeToSet){
        if(typeToSet > -1 && typeToSet < 8){
            type = typeToSet;
        }
        else{
            throw new IllegalArgumentException("ChessPiece objects can only have 'type' 0-6!");
        }
    }
    
    /**
     * Accessor method for the 'type' field.
     * 
     * @return: The int value of the ChessPiece object's type. 
     */
    public int getType(){
        return type;
    }
    
    /**
     * Mutator method for the 'name' field.
     * 
     * @param nameToSet: A string of four characters (preserves formatting)
     */
    public void setName(String nameToSet){
        if(nameToSet.length() == 4){    
            name = nameToSet;
        }
        else{
            throw new IllegalArgumentException("ChessPiece object cannot have name > 4 characters!");
        }
    }
    
    /**
     * Accessor method for the 'name' field.
     * 
     * @return: The name of the ChessPiece object 
     */
    public String getName(){
        return name;
    }
    
    /**
     * Mutator method for the 'moves' field.
     * 
     * @param numMoves: new int value for 'moves' field. 
     */
    public void setMoves(int numMoves){
        moves = numMoves;
    }
    
    /**
     * Accessor method for the 'moves' field.
     * 
     * @return : The number of moves a ChessPiece object has made.
     */
    public int getMoves(){
        return moves;
    }
    
    /**
     * toString method for ChessPiece
     * 
     * @return: The value of 'name' field. 
     */
    public String toString(){
        return name;
    }
}
