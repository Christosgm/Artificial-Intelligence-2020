import java.util.Random;

public class Grid
{
    private String[][] grid;

    public Grid()
    {
        //Initiate the grid
        grid = new String[3][3];
        for(int row = 0; row < 3; row++)
        {
            for(int col = 0; col < 3; col++)
            {
                grid[row][col] = " ";
            }
        }

        //Select random starting position
        Random random = new Random();
        int startingPosition = random.nextInt()%2;
        if(startingPosition == 0)
        {
            grid[1][0] = "O";
        }
        else
        {
            grid[1][2] = "O";
        }

    }

    public boolean put(String player, int row, int col, String move)
    {
        //If HumanPlayer is playing check for coordinates validity
        if(player.equals("user"))
        {
            if(row < 0 || col < 0 || row > 2 || col > 2)
            {
                return false;
            }
        }

        //If cell is full, you can't put symbol inside 
        if(!grid[row][col].equals(" "))
        {
            return false;
        }

        //Else put symbol
        grid[row][col] = move;
        return true;
    }

    public boolean isTerminalState()
    {
        //If grid is full, then the game is over.
        int countNonBlank = 0;
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                if(!grid[i][j].equals(" "))
                {
                    countNonBlank++;
                }
            }
        }

        return countNonBlank == 9;
    }

    //Return the grid
    public String[][] getGrid()
    {
        return grid;
    }


    //Print the grid
    public void print()
    {
        System.out.println("   -1-2-3-");
        for(int row = 0; row < 3; row++)
        {
            System.out.print((row+1)+" | ");
            for(int col = 0; col < 3; col++)
            {
                System.out.print(grid[row][col] + " ");
            }
            System.out.println("|");
        }
        System.out.println("   -------\n");
    }
}
