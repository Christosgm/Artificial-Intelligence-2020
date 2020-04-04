import java.util.InputMismatchException;
import java.util.Scanner;

public class HumanPlayer extends Player
{
    public HumanPlayer(String name)
    {
        super(name);
    }

    //Returns true if the move played is a winning move
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
                char move;
                move = input.next().charAt(0);

                //If symbol is not valid raise InputMismatchException
                if(move!='S' && move!='O')
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

                //If grid finds a winner
                if(grid.someoneHasWon())
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
