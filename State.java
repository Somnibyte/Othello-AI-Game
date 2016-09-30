import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

// TODO
// South West has problems when activated at row 0 and column 3 or 4 ish (forgot whic one)

public class State {

 public static int[][] board = new int[8][8];
 public int SEF;
 public boolean isroot;
 public static boolean skipPlayer1 = false;
 public static boolean skipPlayer2 = false;
 final String NORTH = "NORTH";
 final String NORTHEAST = "NORTHEAST";
 final String NORTHWEST = "NORTHWEST";
 final String EAST = "EAST";
 final String WEST = "WEST";
 final String SOUTH = "SOUTH";
 final String SOUTHEAST = "SOUTHEAST";
 final String SOUTHWEST = "SOUTHWEST";

 public void printBoard() {

  System.out.println("    0   1   2   3   4   5   6   7 ");
  for (int i = 0; i < 8; i++) {
   System.out.print("  ----------------------------------\n");
   char columnChar = 'A';
   switch (i) {
    case 0:
     columnChar = 'A';
     break;
    case 1:
     columnChar = 'B';
     break;
    case 2:
     columnChar = 'C';
     break;
    case 3:
     columnChar = 'D';
     break;
    case 4:
     columnChar = 'E';
     break;
    case 5:
     columnChar = 'F';
     break;
    case 6:
     columnChar = 'G';
     break;
    case 7:
     columnChar = 'H';
     break;
   }

   System.out.print(columnChar + " | ");

   for (int j = 0; j < 8; j++) {
    if (board[i][j] != 0)
     System.out.print(" " + board[i][j] + " |");
    else
     System.out.print(" " + board[i][j] + " |");
   }

   System.out.println("");
  }

  System.out.print("  ----------------------------------\n");
 }

 public State() {
  this.SEF = 1;
  this.isroot = true;

  if (isroot) {
   this.board[3][3] = 2;
   this.board[3][4] = 1;
   this.board[4][3] = 1;
   this.board[4][4] = 2;

  }

 }


 public int[] readPlayerInput(int piece, Scanner reader) {

  char number;
  int[] usersPos = new int[2];
  System.out.println("Enter your move by entering a letter followed by a number. Example: B1");
  String input = reader.nextLine();

  char letter = input.charAt(0);

  int row = 0;
  int column = Character.getNumericValue(input.charAt(1));

  switch (letter) {
   case 'A':
    row = 0;
    break;
   case 'B':
    row = 1;
    break;
   case 'C':
    row = 2;
    break;
   case 'D':
    row = 3;
    break;
   case 'E':
    row = 4;
    break;
   case 'F':
    row = 5;
    break;
   case 'G':
    row = 6;
    break;
   case 'H':
    row = 7;
    break;
  }


  // When we firgure out the color we will adjust this
  board[row][column] = piece;

  // Save position
  usersPos[0] = row;
  usersPos[1] = column;

  return usersPos;
 }

 public List < Object > checkAvailableMoves(int player, int piece) {
  char columnChar = ' ';
  List < String > availableMoves = new ArrayList < String > (); // Ex: ['A1','B2'...]
  List < Object > isValidMoveAndAvailableMoves = new ArrayList < Object > (); // Ex: [true, ['A1','B2'...]]

  for (int i = 0; i < 8; i++) {
   for (int j = 0; j < 8; j++) {
     System.out.println("CURRENTLY CHECKING i: " + i + " j: " + j);
     if ( board[i][j] == 0 && evaluateMove(i, j, piece, player, true)) {
     //convert row to letter
     switch (i) {
      case 0:
       columnChar = 'A';
       break;
      case 1:
       columnChar = 'B';
       break;
      case 2:
       columnChar = 'C';
       break;
      case 3:
       columnChar = 'D';
       break;
      case 4:
       columnChar = 'E';
       break;
      case 5:
       columnChar = 'F';
       break;
      case 6:
       columnChar = 'G';
       break;
      case 7:
       columnChar = 'H';
       break;
     }

     String column = Integer.toString(j);

     String position = columnChar + column;
     availableMoves.add(position);
    }
    System.out.println("END OF CHECKING i: " + i + " j: " + j);
   }
  }

  if (availableMoves.size() >= 1) { //have valid move(s) to make
   if (player == 1) {
    skipPlayer1 = false;
   }
   if (player == 2) {
    skipPlayer2 = false;
   }

   isValidMoveAndAvailableMoves.add(true);
   isValidMoveAndAvailableMoves.add(availableMoves);
   return isValidMoveAndAvailableMoves;
  }


  isValidMoveAndAvailableMoves.add(false);
  if (player == 1) {
   skipPlayer1 = true;
  }
  if (player == 2) {
   skipPlayer2 = true;
  }
  return isValidMoveAndAvailableMoves;



 }

 public boolean evaluateMove(int row, int column, int piece, int player, boolean checkingForValidMove) {


  List boolList = new ArrayList < Boolean > ();
  boolean continueSearching = false;
  int target = (piece == 1) ? 2 : 1;
  boolean isThisAValidMove = true;

  // Evaluate North
  if (row != 0) { // Check boundary of direction
   if ((board[row - 1][column] != 0) && (board[row - 1][column] != piece)) { //if adjacent space not  empty or space not  occupied by friendly piece
    continueSearching = true;
   }

   if (continueSearching) {
    if (checkCardinalDirection((row - 1), column, target, NORTH, checkingForValidMove)) {
     boolList.add(true);
    }
   }

  }

  continueSearching = false;
  // Evaluate North East ( @ Current Position)
  if (row != 0) { // Check boundary of direction
   if (column != 7) {
    if ((board[row - 1][column + 1] != 0) && (board[row - 1][column + 1] != piece)) { //if adjacent space not  empty or space not  occupied by friendly piece
     continueSearching = true;
    }

    if (continueSearching) {
     if (checkDiagonalDirection(row - 1, column + 1, target, NORTHEAST, checkingForValidMove)) {
      boolList.add(true);
     }
    }
   }
  }


  continueSearching = false;
  // Evaluate North West
  if (row != 0) { // Check boundary of direction
   if (column != 0) {
    if ((board[row - 1][column - 1] != 0) && (board[row - 1][column - 1] != piece)) { //if adjacent space not  empty or space not  occupied by friendly piece
     continueSearching = true;
    }

    if (continueSearching) {
     if (checkDiagonalDirection(row - 1, column - 1, target, NORTHWEST, checkingForValidMove)) {
      boolList.add(true);
     }
    }
   }
  }

  continueSearching = false;
  // Evaluate East
  if (column != 7) { // Check boundary of direction

   if ((board[row][column + 1] != 0) && (board[row][column + 1] != piece)) { //if adjacent space not  empty or space not  occupied by friendly piece
    continueSearching = true;
   }

   if (continueSearching) {
    if (checkCardinalDirection(row, (column + 1), target, EAST, checkingForValidMove)) {
     boolList.add(true);
    }
   }

  }

  continueSearching = false;
  // Evaluate West
  if (column != 0) { // Check boundary of direction

   if ((board[row][column - 1] != 0) && (board[row][column - 1] != piece)) { //if adjacent space not  empty or space not  occupied by friendly piece
    continueSearching = true;
   }

   if (continueSearching) {
    if (checkCardinalDirection(row, (column - 1), target, WEST, checkingForValidMove)) {
     boolList.add(true);
    }
   }

  }

  continueSearching = false;
  // Evaluate South
  if (row != 7) { // Check boundary of direction

   if ((board[row + 1][column] != 0) && (board[row + 1][column] != piece)) { //if adjacent space not  empty or space not  occupied by friendly piece
    continueSearching = true;
   }

   if (continueSearching) {
    if (checkCardinalDirection(row + 1, column, target, SOUTH, checkingForValidMove)) {
     boolList.add(true);
    }
   }

  }

  continueSearching = false;
  // Evaluate South East
  if (row != 7) { // Check boundary of direction
   if (column != 7) {
    if ((board[row + 1][column + 1] != 0) && (board[row + 1][column + 1] != piece)) { //if adjacent space not  empty or space not  occupied by friendly piece
     continueSearching = true;
    }

    if (continueSearching) {
     if (checkDiagonalDirection(row + 1, column + 1, target, SOUTHEAST, checkingForValidMove)) {
      boolList.add(true);
     }
    }
   }
  }

  continueSearching = false;
  // Evaluate South West
  if (row != 7) { // Check boundary of direction

   if (column != 0) {
     System.out.println("");
    if ((board[row + 1][column - 1] != 0) && (board[row + 1][column - 1] != piece)) { //if adjacent space not  empty or space not  occupied by friendly piece
     continueSearching = true;
     System.out.println("WORKING WITH SW");
    }

    if (continueSearching) {
     if (checkDiagonalDirection(row + 1, column - 1, target, SOUTHWEST, checkingForValidMove)) {
      boolList.add(true);
     }
    }
   }
  }

  // Check if a move is valid
  if (boolList.size() == 0) {
   isThisAValidMove = false;
  }

  if (!checkingForValidMove) {
   printBoard();
  }

  return isThisAValidMove;
 }


 boolean checkCardinalDirection(int row, int column, int target, String direction, boolean checkingForValidMove) {
  int piece = (target == 1) ? 2 : 1;

  if (board[row][column] == piece) {
   return true;
  } else if (direction == NORTH && row == 0) {
   return false;
  } else if (direction == EAST && column == 7) {
   return false;
  } else if (direction == WEST && column == 0) {
   return false;
  } else if (direction == SOUTH && row == 7) {
   return false;
  }


  if (direction == NORTH) {
   if (checkCardinalDirection(row - 1, column, target, NORTH, checkingForValidMove) == true) {
    if (!checkingForValidMove) {
     System.out.println("N FLIPPED");
     board[row][column] = piece;
    }
    return true;
   }
  } else if (direction == EAST) {
   if (checkCardinalDirection(row, column + 1, target, EAST, checkingForValidMove) == true) {
    if (!checkingForValidMove) {
     System.out.println("E FLIPPED");
     board[row][column] = piece;
    }
    return true;
   }
  } else if (direction == WEST) {
   if (checkCardinalDirection(row, column - 1, target, WEST, checkingForValidMove) == true) {
    if (!checkingForValidMove) {
     System.out.println("W FLIPPED");
     board[row][column] = piece;
    }
    return true;
   }
  } else if (direction == SOUTH) {
   if (checkCardinalDirection(row + 1, column, target, SOUTH, checkingForValidMove) == true) {
    if (!checkingForValidMove) {
     System.out.println("S FLIPPED");
     board[row][column] = piece;
    }
    return true;
   }
  }

  return false;
 }

 boolean checkDiagonalDirection(int row, int column, int target, String direction, boolean checkingForValidMove) {

   System.out.println("Checking FROM DIAG: " + board[row+1][column-1]);
  int piece = (target == 1) ? 2 : 1;

  if (board[row][column] == piece) {
    System.out.println("EXIT 1");
   return true;
  } else if (direction == NORTHEAST && (row == 0 || column == 7)) {
   return false;
  } else if (direction == NORTHWEST && (row == 0 || column == 0)) {
   return false;
  } else if (direction == SOUTHEAST && (row == 7 || column == 7)) {
   return false;
  } else if (direction == SOUTHWEST && (row == 7 || column == 0)) {
    System.out.println("EXIT 2");
   return false;
  }


  if (direction == NORTHEAST) {
   if (checkCardinalDirection(row - 1, column + 1, target, NORTHEAST, checkingForValidMove) == true) {
    if (!checkingForValidMove) {
     System.out.println("NE FLIPPED");
     board[row][column] = piece;
    }
    return true;
   }
  } else if (direction == NORTHWEST) {
   if (checkCardinalDirection(row - 1, column - 1, target, NORTHWEST, checkingForValidMove) == true) {
    if (!checkingForValidMove) {
     System.out.println("NW FLIPPED");
     board[row][column] = piece;
    }
    return true;
   }
  } else if (direction == SOUTHEAST) {
   if (checkCardinalDirection(row + 1, column + 1, target, SOUTHEAST, checkingForValidMove) == true) {
    if (!checkingForValidMove) {
     System.out.println("SE FLIPPED");
     board[row][column] = piece;
    }
    return true;
   }
  } else if (direction == SOUTHWEST) {

   if (checkCardinalDirection(row + 1, column - 1, target, SOUTHWEST, checkingForValidMove) == true) {
    if (!checkingForValidMove) {
     System.out.println("SW FLIPPED");
     board[row][column] = piece;
    }

    System.out.println("SW FINE");
    return true;
   }
  }

  System.out.println("Couldn't flip anything");
  return false;
 }


 public static boolean gameOver() {
  if (skipPlayer1 && skipPlayer2) {
   return true;
  } else {
   return false;
  }
 }

 public static int winner() {
  int blackPebbles = 0;
  int whitePebbles = 0;

  for (int i = 0; i < 8; i++) {

   for (int j = 0; j < 8; j++) {
    if (board[i][j] == 1) {
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


 public static void main(String[] args) {
  State state = new State();
  int counter = 0;
  boolean isValidMove = false;
  List < Object > availableMoves = new ArrayList < Object > ();
  Scanner reader = new Scanner(System.in);

  state.printBoard();

  while (!gameOver()) {

   // Even = PLAYER 1
   if (counter % 2 == 0) {
    int piece = 1;
    int player = 1;
    int[] pos = new int[2];
    System.out.println("4,4 has: " + board[4][4]);

    availableMoves = state.checkAvailableMoves(player, piece);

    if ((boolean) availableMoves.get(0)) {

     for (String move: (ArrayList < String > ) availableMoves.get(1)) {
      System.out.print(move + ", ");
     }

     pos = state.readPlayerInput(piece, reader);
     isValidMove = state.evaluateMove(pos[0], pos[1], piece, player, false);

    } else {
     System.out.println("There are no available moves.");
    }

   } else {
    int piece = 2;
    int player = 2;
    int[] pos = new int[2];
    availableMoves = state.checkAvailableMoves(player, piece);

    if ((boolean) availableMoves.get(0)) {

     for (String move: (ArrayList < String > ) availableMoves.get(1)) {
      System.out.print(move + ", ");
     }

     pos = state.readPlayerInput(piece, reader);
     isValidMove = state.evaluateMove(pos[0], pos[1], piece, player, false);

    } else {
     System.out.println("There are no available moves.");
    }

   }

   counter++;

  }

  if (winner() == 1) {
   System.out.println("PLAYER 1 WINS");
  } else if (winner() == 2) {
   System.out.println("PLAYER 2 WINS");
  } else {
   System.out.println("DRAAAAAAAAW!");
  }

 }


}
