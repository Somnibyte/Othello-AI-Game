import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

// TODO
// South West has problems when activated at row 0 and column 3 or 4 ish (forgot whic one)

public class State {

 public static int[][] board = new int[8][8];
 public int piece = 0;
 public int SEF;
 public int numberOfFlanks = 0;
 public boolean hasCorner=false;
 public int numberOfDirections = 0;
 public boolean isroot;
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
     System.out.print(" " + " " + " |");
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
     //System.out.println("CURRENTLY CHECKING i: " + i + " j: " + j);
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
    //System.out.println("END OF CHECKING i: " + i + " j: " + j);
   }
  }

  if (availableMoves.size() >= 1) { //have valid move(s) to make

   isValidMoveAndAvailableMoves.add(true);
   isValidMoveAndAvailableMoves.add(availableMoves);
   return isValidMoveAndAvailableMoves;
  }


  isValidMoveAndAvailableMoves.add(false);
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
     	this.numberOfDirections++;
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
       this.numberOfDirections++;
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
       this.numberOfDirections++;
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
       this.numberOfDirections++;
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
       this.numberOfDirections++;
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
       this.numberOfDirections++;
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
       this.numberOfDirections++;
     }
    }
   }
  }

  continueSearching = false;
  // Evaluate South West
  if (row != 7) { // Check boundary of direction

   if (column != 0) {
    if ((board[row + 1][column - 1] != 0) && (board[row + 1][column - 1] != piece)) { //if adjacent space not  empty or space not  occupied by friendly piece
     continueSearching = true;
     //System.out.println("WORKING WITH SW");
    }

    if (continueSearching) {
     if (checkDiagonalDirection(row + 1, column - 1, target, SOUTHWEST, checkingForValidMove)) {
      boolList.add(true);
       this.numberOfDirections++;
     }
    }
   }
  }

  // Check if a move is valid
  if (boolList.size() == 0) {
   isThisAValidMove = false;
  }else{

    // Everything went fine. Now place the piece onto the board
    board[row][column] = piece;

    // Check if the piece is on a corner
    if( (row == 0 && column == 0) || (row == 0 && column == 7) || (row == 7 && column == 0) || (row == 7 && column == 7) ) {
      hasCorner = true;
    }

  }

	//if active gameplay and not checking
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
     //System.out.println("N FLIPPED");
     board[row][column] = piece;
      this.numberOfFlanks++;
    }
    return true;
   }
  } else if (direction == EAST) {
   if (checkCardinalDirection(row, column + 1, target, EAST, checkingForValidMove) == true) {
    if (!checkingForValidMove) {
     //System.out.println("E FLIPPED");
     board[row][column] = piece;
    	this.numberOfFlanks++;

    }
    return true;
   }
  } else if (direction == WEST) {
   if (checkCardinalDirection(row, column - 1, target, WEST, checkingForValidMove) == true) {
    if (!checkingForValidMove) {
     //System.out.println("W FLIPPED");
     board[row][column] = piece;
      this.numberOfFlanks++;

    }
    return true;
   }
  } else if (direction == SOUTH) {
   if (checkCardinalDirection(row + 1, column, target, SOUTH, checkingForValidMove) == true) {
    if (!checkingForValidMove) {
     //System.out.println("S FLIPPED");
     board[row][column] = piece;
      this.numberOfFlanks++;
    }
    return true;
   }
  }

  return false;
 }

 boolean checkDiagonalDirection(int row, int column, int target, String direction, boolean checkingForValidMove) {

  int piece = (target == 1) ? 2 : 1;

  if (board[row][column] == piece) {
    //System.out.println("EXIT 1");
   return true;
  } else if (direction == NORTHEAST && (row == 0 || column == 7)) {
    //System.out.println("EXIT 2");
   return false;
  } else if (direction == NORTHWEST && (row == 0 || column == 0)) {
    //System.out.println("EXIT 3");
   return false;
  } else if (direction == SOUTHEAST && (row == 7 || column == 7)) {
    //System.out.println("EXIT 4");
   return false;
  } else if (direction == SOUTHWEST && (row == 7 || column == 0)) {
    //System.out.println("EXIT 2");
   return false;
  }


  if (direction == NORTHEAST) {
    //System.out.println("GOT INTO NE");
   if (checkDiagonalDirection(row - 1, column + 1, target, NORTHEAST, checkingForValidMove) == true) {
    // System.out.println("NE OK");
    if (!checkingForValidMove) {
    // System.out.println("NE FLIPPED");
     board[row][column] = piece;
      this.numberOfFlanks++;
    }
    return true;
   }
  } else if (direction == NORTHWEST) {
    //System.out.println("GOT INTO NW");
   if (checkDiagonalDirection(row - 1, column - 1, target, NORTHWEST, checkingForValidMove) == true) {
     // System.out.println("NW OK");
    if (!checkingForValidMove) {
     // System.out.println("NW FLIPPED");
     board[row][column] = piece;
      this.numberOfFlanks++;
    }
    return true;
   }
  } else if (direction == SOUTHEAST) {
  //  System.out.println("GOT INTO SE");
   if (checkDiagonalDirection(row + 1, column + 1, target, SOUTHEAST, checkingForValidMove) == true) {
    // System.out.println("SE OK");
    if (!checkingForValidMove) {
     //System.out.println("SE FLIPPED");
     board[row][column] = piece;
      this.numberOfFlanks++;
    }
    return true;
   }
  } else if (direction == SOUTHWEST) {
    //System.out.println("GOT INTO SW");
   if (checkDiagonalDirection(row + 1, column - 1, target, SOUTHWEST, checkingForValidMove) == true) {
    // System.out.println("SW NOT OK");
    if (!checkingForValidMove) {
     // System.out.println("SW FLIPPED");
     board[row][column] = piece;
      this.numberOfFlanks++;
    }

  //  System.out.println("SW FINE");
    return true;
   }

   //System.out.println("SW SKIPPED");
  }

  //System.out.println("Couldn't flip anything");
  return false;
 }




}
