public class GameEvaluator
{

  // Will have skipping, winning/losing, game over logic

	public static int SEF(State S)
  {

    int value = 0;

   	if(S.piece == 2) // Human
    {
     	value += S.numberOfFlanks;
      value += S.numberOfDirections;
      if(S.hasCorner)
      {
       	value += 3;
      }
    }
    if(S.piece == 1)
    {
    	value -= S.numberOfFlanks;
    	value -= S.numberOfDirections;
    	if(S.hasCorner)
      {
       	value -= 3;
      }
    }

    return value;
  }

}
