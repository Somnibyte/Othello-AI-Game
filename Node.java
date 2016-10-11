public class Node
{
	public int level;
	public int sibling;
	public State state;
  public int SEF = 0;

  Node(State s){
   this.state = s;

	 if(this.state.isroot){
		 this.level = 0;
		 this.sibling = 1;
	 }
  }

}
