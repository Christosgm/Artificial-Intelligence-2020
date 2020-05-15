import java.util.ArrayList;
import java.nio.file.*;
import java.io.IOException;

class NewHeuristic
{
  public int heuristic(String permutation, int whiteBalls){
      int extraValue = 0;
      int emptyIndex = permutation.indexOf('-');

      int lastValue = 0;
      for (int i = 0 ; i < 2*whiteBalls+1 ; i++)

          if ( i < whiteBalls && permutation.charAt(i) == 'A')
          extraValue += (whiteBalls - i)*2;

          else if (i >= whiteBalls && permutation.charAt(i) == 'M'){
          lastValue = i - whiteBalls;
          extraValue += (lastValue)*2;

      }
      if (emptyIndex <whiteBalls) extraValue += whiteBalls- emptyIndex;
      else extraValue += emptyIndex - whiteBalls;
      return extraValue - lastValue;
  }

  public static void main(String[] args)
  {
      String s = args[0];
      int whiteBalls = s.length()/2;
      NewHeuristic h = new NewHeuristic();
      try {
          Files.write(Paths.get("heur.txt"), (String.valueOf(h.heuristic(s,whiteBalls))+"\n").getBytes(), StandardOpenOption.APPEND);
      }catch (IOException e) {
          //exception handling left as an exercise for the reader
      }
  }
}
