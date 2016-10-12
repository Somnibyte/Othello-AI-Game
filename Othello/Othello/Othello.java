import java.util.*;

public class Othello {
 public static int maxDepth = 3; // Zero-Based
 public static boolean skipPlayer1 = false;
 public static boolean skipPlayer2 = false;
 public static GameEvaluator evaluator = new GameEvaluator();

 public static int initialMinimax(Node node, int level, int depth) {
  int RVal = 0;
  int value = 0;

  if (level == depth) {
   node.SEF = evaluator.SEF(node);
   //System.out.println("Level: " + node.level + " Sibling: " + node.sibling + " SEF: " + node.SEF);

   return evaluator.SEF(node);
  } else if (level % 2 == 0) //maximizing
  {

   if (node.level == 0 && node.sibling == 1) {
    //System.out.println("MAX AND THIS IS INIT NODE");
   }
   value = Integer.MIN_VALUE;
   for (Node n: node.state.children) {
    RVal = initialMinimax(n, level + 1, depth);
    //System.out.println("RVAL IS CURRENTLY: " + RVal + " CURRENT VAL IS:" + value);
    if (RVal > value) {
     //System.out.println("CHANGED");
     value = RVal;
     node.SEF = value;
    }
   }
  } else {
   value = Integer.MAX_VALUE;
   for (Node n: node.state.children) {
    RVal = initialMinimax(n, level + 1, depth);
    if (RVal < value) {
     value = RVal;
     node.SEF = value;
    }
   }
  }

  //System.out.println("Level: " + node.level + " Sibling: " + node.sibling + " SEF: " + node.SEF);
  return value;
 }


 public static void printSEFVals(List < Node > nodeArray, int Depth) {
  if (Depth == 0) {
   for (Node n: nodeArray) {

    //System.out.println("Level: " + n.level + " Sibling: " + n.sibling + " SEF: " + n.SEF);
   }
   //System.out.println("Done building tree");
   return;
  }

  for (Node n: nodeArray) {

   //System.out.println("Level: " + n.level + " Sibling: " + n.sibling + " SEF: " + n.SEF);
   printSEFVals(n.state.children, Depth - 1);
  }

 }



 public static void buildGameTree(List < Node > nodeArray, int Depth) {
  int piece = 0;
  int player = 0;

  if (Depth % 2 == 0) {
   piece = 1;
   player = 1;
  } else {
   piece = 2;
   player = 2;
  }

  //System.out.println("Building tree, Depth = " + Depth + " Node Array Size: " + nodeArray.size());

  if (Depth == 0) {
   //System.out.println("Done building tree");
   return;
  }



  for (Node n: nodeArray) {

   // Reset state to actual player and piece before checking moves
   n.state.player = player;
   n.state.piece = piece;


   if ((boolean) n.state.checkAvailableMoves().get(0) == false) {
    continue;
   }
   /*
   System.out.println("Original Node:");
   n.state.printBoard();
   System.out.println("End of Original Node.");
   */
   List < String > moves = (ArrayList < String > ) n.state.checkAvailableMoves().get(1);


   int counter = 0; // For printing the sibling number

   for (String move: moves) {

    counter += 1; // Indicates sibling number

    State newState = new State(n.state);
    newState.player = player;
    newState.piece = piece;
    newState.makeMove(move);


    //System.out.println(" ------- Piece: " + piece + " Player " + player + " # flanks: " + newState.numberOfFlanks + " # of directions: " + newState.numberOfDirections);
    //newState.printBoard();
    //System.out.println(" ------- ");

    Node newNode = new Node(newState);
    newNode.level = n.level + 1; // For printing the prev level of prev node + 1
    newNode.sibling = counter;
    n.state.children.add(newNode);
    n.state.moveAndChildNode.put(move, newNode);
   }

   buildGameTree(n.state.children, Depth - 1);
  }
 }

 public static void main(String[] args) {
  State initialState = new State(2, 2);
  Node initialNode = new Node(initialState);

  List < Node > initialMove = new ArrayList < Node > ();
  List < String > initialStatesMoves = new ArrayList < String > ();
  initialMove.add(initialNode);

  // Build the initial tree
  buildGameTree(initialMove, maxDepth);

  // Setup the SEF values for the current build
  initialMinimax(initialNode, 0, maxDepth);



  // Keep track of the current game node
  Node currentPly = initialNode;
  int nextCuttOffLevel = 0;
  Scanner reader = new Scanner(System.in);

  // Player 1 (piece 1) will go first
  // Start the game

  System.out.println("READY.SET.GO! \n");
  currentPly.state.printBoard();

  while (!(evaluator.gameOver(skipPlayer1, skipPlayer2))) {


   // Print the current ply
   System.out.println("Current Depth: " + maxDepth + " current level: " + nextCuttOffLevel);

   List < Object > availableMoves = new ArrayList < Object > ();
   availableMoves = currentPly.state.checkAvailableMoves();

   if (maxDepth == 0) {
    // If we reached the leaves, then reinitialize maxDepth to be 3
    maxDepth = 2;
    List < Node > newMove = new ArrayList < Node > ();
    newMove.add(currentPly);

    buildGameTree(newMove, maxDepth);

    MinimaxThread t1 = new MinimaxThread(0, maxDepth, currentPly);
    t1.start();
    try {
     t1.join();
     System.out.println("After: " + currentPly.state.children.size());
    } catch (InterruptedException e) {

    }


   }

   if (maxDepth % 2 != 0) {
    // Human Player Logic
    // Check if the player has any available moves
    if ((boolean) availableMoves.get(0) == false) {
     // No available moves? Skip Player 1
     skipPlayer1 = true;
    } else {
     // There are moves available, so let the human player keep playing
     skipPlayer1 = false;

     // Print the available moves to the player
     for (String move: (ArrayList < String > ) availableMoves.get(1)) {
      System.out.print(move + ", ");
     }

     // Allow player to make a move
     String playerInput = evaluator.readPlayerInput(reader);

     // Find the players move in the current nodes children
     Node move = currentPly.state.moveAndChildNode.get(playerInput);
     // Set the new node as the currentNode for the next player to start from
     currentPly = move;

     // Player 1 has finished making a move.
     // Print the players move
     System.out.println("PRINTED SECOND BOARD");
     currentPly.state.printBoard();
    }

   } else {
    // AI Player Logic
    System.out.println("AI gives you stern look. ಠ╭╮ಠ");

    if ((boolean) availableMoves.get(0) == false) {
     // No available moves? Skip Player 1
     skipPlayer2 = true;
    } else {
     skipPlayer2 = false;

     String moveWithLowestSEF = "";
     int lowestSEF = Integer.MAX_VALUE;

     // Print the available moves to the AI
     System.out.println("AI is thinking...");
     System.out.println("Number of moves available to AI: " + currentPly.state.moveAndChildNode.size());
     for (Map.Entry < String, Node > entry: currentPly.state.moveAndChildNode.entrySet()) {
      if (entry.getValue().SEF < lowestSEF) {
       lowestSEF = entry.getValue().SEF;
       moveWithLowestSEF = entry.getKey();
      }
     }

     try{
        Thread.sleep(2000);
     }catch(InterruptedException e){

     }

     System.out.println("AI placed a piece on " + moveWithLowestSEF);
     // Find the players move in the current nodes children
     Node move = currentPly.state.moveAndChildNode.get(moveWithLowestSEF);
     // Set the new node as the currentNode for the next player to start from
     currentPly = move;

     // Show the AI's move
     System.out.println(currentPly);
     currentPly.state.printBoard();
    }
   }

   // Go down the game tree
   maxDepth -= 1;
   nextCuttOffLevel++;

  }


  int winningValue = evaluator.winner(currentPly);

  if(winningValue == 1){
    System.out.println("AI WINS!!!!");
  }else if(winningValue == 2){
    System.out.println("YOU WIN!!!!");
  }else if(winningValue == 9001){ // IT"S OVER 9000!
    System.out.println("DRAW!!!!");
  }

 }

}
