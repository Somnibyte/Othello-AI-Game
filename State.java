import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

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

		public void printBoard(){

				System.out.println("    0   1   2   3   4   5   6   7 ");
		    for(int i = 0; i < 8; i++){
		       	System.out.print("  ----------------------------------\n");
		        char columnChar = 'A';
		      	switch(i){
		        	case 0: columnChar = 'A';
		          break;
		          case 1: columnChar = 'B';
		          break;
		          case 2: columnChar = 'C';
		          break;
		          case 3: columnChar = 'D';
		          break;
		          case 4: columnChar = 'E';
		          break;
		          case 5: columnChar = 'F';
		          break;
		          case 6: columnChar = 'G';
		          break;
		          case 7: columnChar = 'H';
		          break;
		      	}

		      	System.out.print(columnChar + " | ");

		        for (int j = 0; j < 8; j++){
		          if(board[i][j] != 0)
		          	System.out.print(" " + board[i][j] + " |");
		          else
		            System.out.print(" " + " " + " |");
		        }

						System.out.println("");
		      }

					System.out.print("  ----------------------------------\n");
		  	}

  	public State()
    {
     	this.SEF = 1;
      this.isroot = true;

      if(isroot)
      {
       	this.board[3][3] = 2;
        this.board[3][4] = 1;
        this.board[4][3] = 1;
        this.board[4][4] = 2;

      }

    }


		public int[] readPlayerInput(int piece, Scanner reader)
	{

		char number;
    int[] usersPos = new int[2];
		System.out.println("Enter your move by entering a letter followed by a number. Example: B1");
		String input = reader.nextLine();

		char letter = input.charAt(0);

  		int row = 0;
      int column = Character.getNumericValue(input.charAt(1));

      switch(letter){
        case 'A': row = 0;
        break;
        case 'B': row = 1;
        break;
        case 'C': row = 2;
        break;
        case 'D': row = 3;
        break;
        case 'E': row = 4;
        break;
        case 'F': row = 5;
        break;
        case 'G': row = 6;
        break;
        case 'H': row = 7;
        break;
      }


      // When we firgure out the color we will adjust this
      board[row][column] = piece;

      // Save position
      usersPos[0] = row;
      usersPos[1] = column;

    	return usersPos;
	}

  public void evaluateMove(int row, int column, int piece, int player)
  {


    // NE row == o || column == 7 AND OTHER ONE IS -> row == 0 && column 7

    List boolList = new ArrayList<Boolean>();
    boolean continueSearching = false;
    int target = (piece == 1) ? 2:1;

    // Evaluate North

    if(row != 0){ // Check boundary of direction

      if((board[row-1][column] != 0) && (board[row-1][column] != piece)){ //if adjacent space not  empty or space not  occupied by friendly piece
      	continueSearching = true;
      }

      if(continueSearching){
        if(checkCardinalDirection((row-1), column, target, NORTH)){
         boolList.add(true);
        }
      }

    }

    // Evaluate North East
    System.out.println("ABOUT TO CHECK NE");
    if((row != 0) && (column != 7)){ // Check boundary of direction
      System.out.println("NE IS OK");
      if((board[row-1][column+1] != 0) && (board[row-1][column+1] != piece)){ //if adjacent space not  empty or space not  occupied by friendly piece
        System.out.println("YOU TOTALLY GOOD");
        continueSearching = true;
      }

      if(continueSearching){
        System.out.println("NOW SEARCHING");
        if(checkDiagonalDirection(row-1, column+1, target, NORTHEAST)){
          boolList.add(true);
        }
      }

    }

    System.out.println("DONE WITH NE");
    // Evaluate North West
    if((row != 0) && (column != 0)){ // Check boundary of direction

      if((board[row-1][column-1] != 0) && (board[row-1][column-1] != piece)){ //if adjacent space not  empty or space not  occupied by friendly piece
      	continueSearching = true;
      }

      if(continueSearching){
        if(checkDiagonalDirection(row-1, column-1, target, NORTHWEST)){
          boolList.add(true);
        }
      }

    }

    // Evaluate East
    if(column != 7){ // Check boundary of direction

      if((board[row][column+1] != 0) && (board[row][column + 1] != piece)){ //if adjacent space not  empty or space not  occupied by friendly piece
      	continueSearching = true;
      }

      if(continueSearching){
        if(checkCardinalDirection(row, (column + 1), target, EAST)){
         	boolList.add(true);
        }
      }

    }

    // Evaluate West
    if(column != 7){ // Check boundary of direction

      if((board[row][column-1] != 0) && (board[row][column-1] != piece)){ //if adjacent space not  empty or space not  occupied by friendly piece
      	continueSearching = true;
      }

      if(continueSearching){
        if(checkCardinalDirection(row, (column-1), target, WEST)){
        	boolList.add(true);
        }
      }

    }


    // Evaluate South
    if(row != 7){ // Check boundary of direction

      if((board[row+1][column] != 0) && (board[row+1][column] != piece)){ //if adjacent space not  empty or space not  occupied by friendly piece
      	continueSearching = true;
      }

      if(continueSearching){
        if(checkCardinalDirection(row+1, column, target, SOUTH)){
         	boolList.add(true);
        }
      }

    }

    // Evaluate South East
    if((row != 7) && (column != 7)){ // Check boundary of direction

      if((board[row+1][column+1] != 0) && (board[row+1][column+1] != piece)){ //if adjacent space not  empty or space not  occupied by friendly piece
      	continueSearching = true;
      }

      if(continueSearching){
        if(checkDiagonalDirection(row+1, column+1, target, SOUTHEAST)){
         boolList.add(true);
        }
      }

    }

    // Evaluate South West
    if((row != 7) && (column != 0)){ // Check boundary of direction

      if((board[row+1][column-1] != 0) && (board[row+1][column-1] != piece)){ //if adjacent space not  empty or space not  occupied by friendly piece
      	continueSearching = true;
      }

      if(continueSearching){
        if(checkDiagonalDirection(row+1, column-1, target, SOUTHWEST)){
         boolList.add(true);
        }
      }

    }


    if(player == 1  && boolList.size() == 0){
     	 skipPlayer1 = true;
    }

  	if(player == 1  && boolList.size() == 0){
     	 skipPlayer1 = true;
    }

    printBoard();

  }


  boolean checkCardinalDirection(int row, int column, int target, String direction)
  {
    int piece = (target == 1) ? 2:1;

    if(board[row][column] == piece){
      return true;
    }else if( direction == NORTH && row == 0){
      return false;
    }else if( direction == EAST && column == 7){
      return false;
    }else if( direction == WEST && column == 0){
      return false;
    }else if( direction == SOUTH && row == 7){
      return false;
    }


    if(direction == NORTH){
    		if( checkCardinalDirection(row-1, column, target, NORTH) == true){
          	board[row][column] = piece;
            return true;
    		}
    }else if(direction == EAST){
    		if( checkCardinalDirection(row, column+1, target, EAST) == true){
          	board[row][column] = piece;
            return true;
    		}
    }else if(direction == WEST){
    		if( checkCardinalDirection(row, column-1, target, WEST) == true){
          	board[row][column] = piece;
            return true;
    		}
    }else if(direction == SOUTH){
    		if( checkCardinalDirection(row+1, column, target, SOUTH) == true){
          	board[row][column] = piece;
            return true;
    		}
    }

    return false;
  }

  boolean checkDiagonalDirection(int row, int column, int target, String direction)
  {
 		int piece = (target == 1) ? 2:1;

    if(board[row][column] == piece){
      if(direction == NORTHEAST){
        System.out.println("NE PLACE WAS YOUR PIECE");
      }
      return true;
    }else if( direction == NORTHEAST && row == 0 && column == 7){
      System.out.println("NE WAS AT CORNER");
      return false;
    }else if( direction == NORTHWEST && row == 0 && column == 0){
      return false;
    }else if( direction == SOUTHEAST && row == 7 && column == 7){
      return false;
    }else if( direction == SOUTHWEST && row == 7 && column == 0){
      return false;
    }


    if(direction == NORTHEAST){
    		if( checkCardinalDirection(row-1, column+1, target, NORTHEAST) == true){
          System.out.println("NE WAS ALLOWED TO CONTINUE.");
          	board[row][column] = piece;
            return true;
    		}
    }else if(direction == NORTHWEST){
    		if( checkCardinalDirection(row-1, column-1, target, NORTHWEST) == true){
          	board[row][column] = piece;
            return true;
    		}
    }else if(direction == SOUTHEAST){
    		if( checkCardinalDirection(row+1, column+1, target, SOUTHEAST) == true){
          	board[row][column] = piece;
            return true;
    		}
    }else if(direction == SOUTHWEST){
    		if( checkCardinalDirection(row+1, column-1, target, SOUTHWEST) == true){
          	board[row][column] = piece;
            return true;
    		}
    }

    return false;
  }


    public static boolean gameOver()
    {
      if(skipPlayer1 && skipPlayer2){
       	return true;
      }else{
       	return false;
      }
    }

    public static int winner(){
      int blackPebbles = 0;
      int whitePebbles = 0;

      for(int i = 0; i < 8; i++){

        for(int j = 0; j < 8; j++){
          if(board[i][j] == 1){
          	 blackPebbles++;
          }else {
           	whitePebbles++;
          }
        }
      }

      if(blackPebbles > whitePebbles){
       	return 1;
      }else if(blackPebbles == whitePebbles){
       	return 9001;
      }else{
       	return 2;
      }

    }


		public static void main(String[] args){
			State state = new State();
      int counter = 0;
      Scanner reader = new Scanner(System.in);

      state.printBoard();

      while(!gameOver()){

        // Even = PLAYER 1
        if(counter % 2 == 0){
         	int piece = 1;
          int player = 1;
          int[] pos = new int[2];
          pos = state.readPlayerInput(piece,reader);
          state.evaluateMove(pos[0], pos[1],piece,player);

        }else {
          int piece = 2;
          int player = 2;
          int[] pos = new int[2];
          pos = state.readPlayerInput(piece,reader);
          state.evaluateMove(pos[0], pos[1], piece,player);
        }

        counter++;
      }

      if(winner() == 1){
       	System.out.println("PLAYER 1 WINS");
      }else if(winner() == 2){
        System.out.println("PLAYER 2 WINS");
      }else{
       	System.out.println("DRAAAAAAAAW!");
      }

		}


}
