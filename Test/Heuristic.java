import java.util.ArrayList;
import java.nio.file.*;
import java.io.IOException;

public class Heuristic
{
    public int heuristic(String permutation, int whiteBalls)
    {
        int extraValue = 0;
        int emptyIndex = permutation.indexOf('-');
        int[] left = new  int[whiteBalls];
        int leftCount = 0;
        int[] right = new int[whiteBalls];
        int rightCount = 0;
        // Go through the whole string
        for (int i = 0 ; i < 2*whiteBalls+1 ; i++){
            if ( i < whiteBalls && permutation.charAt(i) == 'A')
                left[leftCount++] = i;
            else if (i >= whiteBalls && permutation.charAt(i) == 'M')
                right[rightCount++] = i;
        }
        int full_counter = leftCount + rightCount; //Number of letters in wrong positions
        int new_right = 0;
        while(full_counter>0){
            if (emptyIndex >= whiteBalls){
                extraValue +=  (emptyIndex - left[--leftCount] );
                emptyIndex = left[leftCount];
            }else{
                extraValue += (right[new_right] - emptyIndex);
                emptyIndex = right[new_right++];
            }
            full_counter--;
        }
        return extraValue;
    }


    //Driver main
    public static void main(String[] args)
    {
        String s = args[0];
        int whiteBalls = s.length()/2;
        Heuristic h = new Heuristic();
        try {
            Files.write(Paths.get("heur.txt"), (String.valueOf(h.heuristic(s,whiteBalls))+"\n").getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }
}
