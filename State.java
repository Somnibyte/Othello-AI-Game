import java.util.Scanner;

public class State {

 		public int[][] board = new int[8][8];
  	public int SEF;
  	public boolean isroot;

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
          System.out.println("");
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


        // Debugging
        this.board[2][2] = 2;
        this.board[1][2] = 1;
        this.board[0][2] = 2;
      }

    }


		public int[] readPlayerInput()
	{

		char number;
    int[] usersPos = new int[2];
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter your move by entering a letter followed by a number. Example: B1");
		String input = reader.nextLine();
		reader.close();

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

      // Dont put automatically, will fix later!
      board[row][column] = 1;

      // Save position
      usersPos[0] = row;
      usersPos[1] = column;

    	return usersPos;
	}


  // Should take board later
  public void northLogic(int row, int column, int piece){

    System.out.println("CALLED");
    boolean continueSearching = false;
    int target = (piece == 1) ? 2:1;

    if(row != 0){

      System.out.println("ROW NOT EQUAL TO BOARDER");
      if((board[row-1][column] != 0) && (board[row-1][column] != piece)){ //if adjacent space not  empty or space not occupied by friendly piece
      	continueSearching = true;
        System.out.println("CONDITION ACTIVATED");
      }

      if(continueSearching){
        System.out.println("CONDITION  2 ACTIVATED");
        recursiveFunction((row-1), column, target);
      	printBoard();
      }
    }
  }

  boolean recursiveFunction(int row, int column, int target)
  {
  	// Base case #1
    int piece = (target == 1) ? 2:1;
    if(board[row][column] == piece){
      return true;
    }else if(row == 0 && board[0][column] == target){
      return false;
    }

    if( recursiveFunction(row-1, column, target) == true){
     	board[row][column] = piece;
      return true;
    }

    return false;
  }

		public static void main(String[] args){
			State state = new State();
      int[] pos = new int[2];

      state.printBoard();
			pos = state.readPlayerInput();
      state.northLogic(pos[0], pos[1], 1);

		}


}
