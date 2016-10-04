import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Othello
{
  public static int maxDepth = 2; // Zero-Based

  public static void buildGameTree(List<Node> nodeArray, int Depth)
  {

    int piece = 0;
    int player = 0;

    if(Depth % 2 == 0){
      piece = 1;
      player = 1;
    }else{
      piece = 2;
      player = 2;
    }

    System.out.println("Building tree, Depth = " + Depth + " Node Array Size: " + nodeArray.size());

   	if(Depth == 0)
    {
      System.out.println("Done building tree");
     	 return;
    }

    for(Node n: nodeArray){

      // Reset state to actual player and piece before checking moves
      n.state.player = player;
      n.state.piece = piece;


      if ((boolean)  n.state.checkAvailableMoves().get(0) == false) {
          continue;
      }
        System.out.println("Original Node:");
        n.state.printBoard();
        System.out.println("End of Original Node.");

        List<String> moves =  (ArrayList<String>) n.state.checkAvailableMoves().get(1);

        for(String move: moves)
        {

          State newState = new State(n.state);
          newState.player = player;
          newState.piece = piece;
         	newState.makeMove(move);

          System.out.println(" ------- Piece: " + piece +  " Player " + player);
          newState.printBoard();
          System.out.println(" ------- ");
          Node newNode = new Node(newState);
        	n.state.children.add(newNode);
        }

        buildGameTree(n.state.children, Depth-1);
    }
  }

  public static void main(String[] args)
  {
    State initialState = new State(1,1);
    Node initialNode = new Node(initialState);
    List<Node> initialMove = new ArrayList<Node>();
    List<String> initialStatesMoves = new ArrayList<String>();
    initialMove.add(initialNode);
    GameEvaluator evaluator = new GameEvaluator();

    buildGameTree(initialMove, maxDepth);

    /*
    System.out.println("Initial State Children");
    for(Node n: initialState.children){
      n.state.printBoard();
    }
    */
  }

}
