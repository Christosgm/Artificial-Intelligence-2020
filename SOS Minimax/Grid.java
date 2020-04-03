import java.util.Random;

public class Grid
{
    private char[][] grid;

    public Grid()
    {
        //Initiate the grid
        grid = new char[3][3];
        for(int row = 0; row < 3; row++)
        {
            for(int col = 0; col < 3; col++)
            {
                grid[row][col] = ' ';
            }
        }

        //Select random starting position
        Random random = new Random();
        int startingPosition = random.nextInt()%2;
        if(startingPosition == 0)
        {
            grid[1][0] = 'O';
        }
        else
        {
            grid[1][2] = 'O';
        }

    }

    public Grid(char[][] grid)
    {
        this.grid = new char[3][3];
        for(int i = 0; i < grid.length; i++)
        {
            for(int j = 0; j < grid.length; j++)
            {
                this.grid[i][j] = grid[i][j];
            }
        }
    }

    public boolean put(String player, int row, int col, char move)
    {
        //If HumanPlayer is playing check for coordinates validity
        if(player.equals("human"))
        {
            if(row < 0 || col < 0 || row > 2 || col > 2)
            {
                return false;
            }
        }

        //If cell is full, you can't put symbol inside 
        if(grid[row][col] != ' ')
        {
            return false;
        }

        //Else put symbol
        grid[row][col] = move;
        return true;
    }

    public boolean isDraw()
    {
        int countNonBlank = 0;
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                if(grid[i][j] != ' ')
                {
                    countNonBlank++;
                }
            }
        }

        return !someoneHasWon() && countNonBlank == 9; //If grid is full, then the game is a draw.
    }

    //Return the grid
    public char[][] getGrid()
    {
        return grid;
    }

    //Check if someone has won
    public boolean someoneHasWon()
    {

        //Check rows and columns of the grid
        for(int i = 0; i < grid.length; i++)
        {
            StringBuilder rowSequence = new StringBuilder();
            StringBuilder colSequence = new StringBuilder();
           for(int j = 0; j < grid.length; j++)
           {
               rowSequence.append(grid[i][j]);
               colSequence.append(grid[j][i]);
           }
           if(rowSequence.toString().equals("SOS") || colSequence.toString().equals("SOS"))
           {
               return true;
           }
        }

        StringBuilder diag1Sequence = new StringBuilder();
        StringBuilder diag2Sequence = new StringBuilder();
        //Check diagonals
        for(int i = 0; i < grid.length; i++)
        {
            diag1Sequence.append(grid[i][i]);
            diag2Sequence.append(grid[i][2 - i]);
        }

        return diag1Sequence.toString().equals("SOS") || diag2Sequence.toString().equals("SOS");
    }


    //Print the grid
    public void print()
    {
        System.out.println("\n   -1-2-3-");
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
