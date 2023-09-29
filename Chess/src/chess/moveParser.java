package chess;
import chess.Piece;
import chess.Square;
import chess.Piece.moveType;
import chess.pieces.*;

public class moveParser {
    
    private moveParser(){
    }

    public static void parseMove(String moveToParse){
        
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

        switch(checkMoveValidity(move)){
            case INVALID:
                //Create message for invalid move
                break;
            case VALID:
                executeMove(move); //Execute move normally
                break;
            case CASTLE: 
                executeMove(move);
                executeMove(move); //TODO Additionally execute rook move 
                break;
            case ENPASSANT:
                executeMove(move);
                Piece.Board.Board[move[1].getFile()][move[0].getRank()] = null; //Delete the Pawn that was En Passanted
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
        //pieceToMove.extraMove.getPosition(move[1])
    }

    public static void resign(){
        //TODO
    }

    public static void draw(){
        //TODO
    }

    public static void promotePawn(Square pawnPos, char pieceToBecome){
        Pawn pawn = Piece.Board.getPosition(pawnPos);  
        pawn.promotePawn(pieceToBecome);
        /*TODO: Need to fix the Dynamic/Static typing issue. Maybe move promotion function into this class?*/
    }

    public static moveType checkMoveValidity(Square[] move){
        return Piece.Board.getPosition(move[0]).isValidMove(move[1]);
    }
}
