public class Board {
  int[][] board = new int[8][8];


  public Board(Board b){

    this.board = b.board;
  }

  public Board(int[][] newBoard){
    this.board = newBoard;
  }

  public Board(){


  }

}
