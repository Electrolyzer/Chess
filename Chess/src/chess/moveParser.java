package chess;
import chess.Piece.moveType;
import chess.pieces.*;

public class moveParser {
    
    private moveParser(){
    }

    public static void parseMove(String moveToParse, boolean curPlayer){
        
        int nextSpace;
        char pieceToBecome = '\0';
        Square[] move = new Square[2];

        int counter = 0;

        while(moveToParse != ""){

            //Find where the next space is in the string
            nextSpace = moveToParse.indexOf(" ");
            //If the next char is a space, slice it off then continue
            if(nextSpace == 0){ moveToParse = moveToParse.substring(1); continue; }

            //Otherwise parse the characters. First check for resign, draw or promotion, then try to parse a position
            else{
                if(moveToParse.indexOf("resign") != -1){
                    resign();
                }else if(moveToParse.indexOf("draw?") == 0){
                    executeMove(move);
                    draw();
                }else if( "BNRQ".indexOf(moveToParse.charAt(0)) != -1){ 
                    pieceToBecome = moveToParse.charAt(0);
                }else{
                    move[counter] = new Square((int)moveToParse.charAt(0), moveToParse.charAt(1));
                }
                
                counter++;
            }
        }



        switch(checkMoveValidity(move, curPlayer)){
            case INVALID:
                //Create message for invalid move
                break;
            case VALID:
                executeMove(move); //Execute move normally
                break;
            case CASTLE: 
                executeMove(move);
                if(move[1].getFile() > move[0].getFile()){
                    Square[] rookMove = {new Square(8, move[1].getRank()), new Square(6, move[1].getRank())};
                    executeMove(rookMove);
                }else{
                    Square[] rookMove = {new Square(1, move[1].getRank()), new Square(4, move[1].getRank())};
                    executeMove(rookMove);
                }
                break;
            case ENPASSANT:
                executeMove(move);
                Piece.Board.setPosition(move[1].getFile(), move[0].getRank(), null); //Delete the Pawn that was En Passanted
                break;
            case PROMOTE:
                executeMove(move);
                promotePawn(move[1], pieceToBecome); //Change pawn after movement
                break;
        }        

    }


    public static void executeMove(Square[] move){
        Piece pieceToMove = Piece.Board.getPosition(move[0]);
        Piece.Board.setPosition(move[1], pieceToMove);
        Piece.Board.setPosition(move[0], null);
    }

    public static void resign(){
        //TODO . Resign method
    }

    public static void draw(){
        //TODO . Draw method
    }

    public static void promotePawn(Square pawnPos, char pieceToBecome){
        Pawn pawn = (Pawn)Piece.Board.getPosition(pawnPos);  
        pawn.promotePawn(pieceToBecome);
    }

    public static moveType checkMoveValidity(Square[] move, boolean curPlayer){
        if(Piece.Board.getPosition(move[0]) == null) return moveType.INVALID;
        if(Piece.Board.getPosition(move[0]).isWhite() == curPlayer) return moveType.INVALID;
        return Piece.Board.getPosition(move[0]).isValidMove(move[1]);
    }
}
