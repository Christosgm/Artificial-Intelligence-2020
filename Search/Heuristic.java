import java.util.ArrayList;

public class Heuristic
{
    public int heuristic(String permutation)
    {
        ArrayList<Integer> left = new ArrayList<>();

        ArrayList<Integer> right = new ArrayList<>();

        //Find center position
        int center = permutation.length()/2;

        //Find the index of '-'
        int blankPosition = permutation.indexOf('-');


        for(int i = 0; i < center; i++)
        {
            //Find positions < n+1 that contain defective 'A'
            if(permutation.charAt(center - i - 1) == 'A')
            {
                left.add(center - i - 1);
            }

            //Find positions > n+1 that contain defective 'M'
            if(permutation.charAt(center + i + 1) == 'M')
            {
                right.add(center + i + 1);
            }
        }

        //Merge the arrays by alternating left and right ArrayList elements
        ArrayList<Integer> merged = new ArrayList<>();

        if(blankPosition < center)
        {
            merged.add(blankPosition);
            merged.addAll(merge(right, left));
        }
        else
        {
            merged.add(blankPosition);
            merged.addAll(merge(left, right));
        }

        int score = 0;

        //Find score by calculating the sum of the absolute value of the difference
        // per two elements of the merged ArrayList
        for(int i = 0; i < merged.size() - 1; i++)
        {
            score += Math.abs(merged.get(i) - merged.get(i + 1));
        }

        return score;
    }

    //Merging of two arrays routine
    private ArrayList<Integer> merge(ArrayList<Integer> a, ArrayList<Integer> b)
    {
        ArrayList<Integer> merged = new ArrayList<>();
        while(!a.isEmpty() && !b.isEmpty())
        {
            merged.add(a.remove(0));
            merged.add(b.remove(0));
        }
        if(!a.isEmpty())
        {
            merged.addAll(a);
        }
        else if(!b.isEmpty())
        {
            merged.addAll(b);
        }

        return merged;
    }

    //Driver main
    public static void main(String[] args)
    {
        String s = "MAAMAMMMAAAMAM-";
        Heuristic h = new Heuristic();
        System.out.println("Passed: " + (h.heuristic(s) == 49));
    }
}
