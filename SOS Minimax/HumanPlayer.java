import java.util.InputMismatchException;
import java.util.Scanner;

public class HumanPlayer extends Player
{
    public HumanPlayer(String name)
    {
        super(name);
    }

    @Override
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
                moveResult = grid.put("human", row - 1, col - 1, move);

                //If move is not valid raise InputMismatchException
                if(!moveResult)
                {
                    throw  new InputMismatchException();
                }

                //Check for SOS sequences
                int numberOfSequences = isWinningMove(row - 1, col - 1, grid);

                //If sequences are found
                if(numberOfSequences != 0)
                {
                    return true;//Player wins
                }
            }
            catch (InputMismatchException e)//If coordinates and/or symbol are invalid
            {
                System.out.println("You should enter a valid position, S or O!");
            }
        }
        while(!moveResult);

        //If reached here player has played, not created sequences and does not win
        return false;
    }
}
