public class Othello
{


  public static void main(String[] args)
  {

    State gameState = new State();
    GameEvaluator evaluator = new GameEvaluator();
   	 // Test 1
    int player = 1;
    int piece = 1;
    gameState.piece = piece;
    gameState.evaluateMove(3,2, piece, player, false);
		int sef = evaluator.SEF(gameState);
    System.out.println("SEF: " + sef);
  }

}
