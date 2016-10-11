import java.util.*;

public class GameEvaluator
{


	public static String readPlayerInput(Scanner reader) {

	 System.out.println("Enter your move by entering a letter followed by a number. Example: B1");
	 String input = reader.nextLine();

	 return input.toUpperCase();
	}


	public static int SEF(Node n)
  {
    int value = 0;

		if(n.level == 1 && n.sibling == 2){
			value = 4;
			return value;
		}

   	if(n.state.piece == 2) // Human
    {
     	value += n.state.numberOfFlanks;
      value += n.state.numberOfDirections;
      if(n.state.hasCorner)
      {
       	value += 3;
      }
    }
    if(n.state.piece == 1)
    {
    	value -= n.state.numberOfFlanks;
    	value -= n.state.numberOfDirections;
    	if(n.state.hasCorner)
      {
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
