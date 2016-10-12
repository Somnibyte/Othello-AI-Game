import java.util.*;

public class GameEvaluator {


 public static String readPlayerInput(Scanner reader) {

  System.out.println("Enter your move by entering a letter followed by a number. Example: B1");
  String input = reader.nextLine();

  return input.toUpperCase();
 }


 public static int winner(Node gameNode) {
  int blackPebbles = 0;
  int whitePebbles = 0;

  for (int i = 0; i < 8; i++) {

   for (int j = 0; j < 8; j++) {
    if (gameNode.state.board.board[i][j] == 1) {
     blackPebbles++;
    } else {
     whitePebbles++;
    }
   }
  }

  if (blackPebbles > whitePebbles) {
   return 1;
  } else if (blackPebbles == whitePebbles) {
   return 9001;
  } else {
   return 2;
  }

 }


 public static int SEF(Node n) {
  int value = 0;

  if (n.state.piece == 2) // Human
  {
   value += n.state.numberOfFlanks;
   value += n.state.numberOfDirections;
   if (n.state.hasCorner) {
    value += 3;
   }
  }
  if (n.state.piece == 1) {
   value -= n.state.numberOfFlanks;
   value -= n.state.numberOfDirections;
   if (n.state.hasCorner) {
    value -= 3;
   }
  }

  return value;
 }

 public static boolean gameOver(boolean skipPlayer1, boolean skipPlayer2) {
  if (skipPlayer1 && skipPlayer2) {
   return true;
  } else {
   return false;
  }
 }



}
