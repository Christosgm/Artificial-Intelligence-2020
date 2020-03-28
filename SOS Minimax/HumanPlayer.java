import java.util.InputMismatchException;
import java.util.Scanner;

public class HumanPlayer
{

    private String name;//Player name 
    private int points;//Player points

    public HumanPlayer(String name)
    {
        //Initiate player
        this.name = name;
        points = 0;
    }

    public boolean play(Grid grid)
    {
        boolean moveResult = false;

        //Ends if coordinates are valid
        do
        {
            System.out.print("Enter the coordinates of your position and your move (ex. 2 2 S): ");
            try
            {
                //Enter coordinates and symbol
                Scanner input = new Scanner(System.in);
                int row = input.nextInt();
                int col = input.nextInt();
                String move;
                move = input.next();

                //If symbol is not valid raise InputMismatchException
                if(!move.equals("S") && !move.equals("O"))
                {
                    throw new InputMismatchException();
                }

                //Make move
                moveResult = grid.put("user", row - 1, col - 1, move);

                //If move is not valid raise InputMismatchException
                if(!moveResult)
                {
                    throw  new InputMismatchException();
                }

                //Check for points
                int pointsAquired = checkForPoins(row - 1, col - 1, grid);
                points += pointsAquired;

                //If points are earned
                if(pointsAquired != 0)
                {
                    return true;//Player replays
                }
            }
            catch (InputMismatchException e)//If coordinates and/or symbol are invalid
            {
                System.out.println("You should enter a valid position, S or O!");
            }
        }
        while(!moveResult);

        //If reached here player has played, not earned points and does not replay
        return false;
    }

    public String getName()
    {
        return name;
    }

    public int getPoints()
    {
        return points;
    }

    //Check if points are earned by placing in [row, col]
    private int checkForPoins(int row, int col, Grid grid)
    {
        String[][] gridStringArray = grid.getGrid();
        int score = 0;

        String rowWord = "";
        String colWord = "";
        String diag1Word = "";
        String diag2Word = "";

        //Check the row and column of the coordinate
        for(int i = 0; i < gridStringArray.length; i++)
        {
            rowWord += gridStringArray[i][col];
            colWord += gridStringArray[row][i];
        }

        //If coordinate is in main diagonal check it
        if((row == col))
        {
            for(int i = 0; i < gridStringArray.length; i++)
            {
                diag1Word += gridStringArray[i][i];
            }
        }

        //If coordinate is in secondary diagonal check it
        if(row + col == 2)
        {
            for(int i = 0; i < gridStringArray.length; i++)
            {
                diag2Word += gridStringArray[i][2 - i];
            }
        }

        //Add up SOS points
        if(rowWord.equals("SOS"))
        {
            score++;
        }
        if(colWord.equals("SOS"))
        {
            score++;
        }
        if(diag1Word.equals("SOS"))
        {
            score++;
        }
        if(diag2Word.equals("SOS"))
        {
            score++;
        }

        //Return points earned
        return score;
    }
}
