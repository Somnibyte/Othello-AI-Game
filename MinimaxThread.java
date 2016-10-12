class MinimaxThread extends Thread {

 int currentLevel = 0;
 int currentDepth = 0;
 public static GameEvaluator evaluator = new GameEvaluator();
 Node currentNode;



 public MinimaxThread(int inputLevel, int inputDepth, Node inputNode) {
  this.currentLevel = inputLevel;
  this.currentDepth = inputDepth;
  this.currentNode = inputNode;
 }


 public static int minimax(Node node, int level, int depth) {
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
    RVal = minimax(n, level + 1, depth);
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
    RVal = minimax(n, level + 1, depth);
    //System.out.println("RVAL IS CURRENTLY: " + RVal + " CURRENT VAL IS:" + value);

    if (RVal < value) {
     value = RVal;
     node.SEF = value;
    }
   }
  }

  //System.out.println("Level: " + node.level + " Sibling: " + node.sibling + " SEF: " + node.SEF);
  return value;
 }

 public void run() {
  minimax(this.currentNode, this.currentLevel, this.currentDepth);

 }


}
