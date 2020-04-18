import java.util.Random;

public class ComputerPlayer extends Player
{
    public ComputerPlayer(String name)
    {
        super(name);
    }

    @Override
    public boolean play(Grid grid)
    {
        if(getName().equals("cpu"))//If name is "cpu"
        {
            //Play at random
            Random rand = new Random();
            char[] symbols = {'S', 'O'};
            int i, j, k;
            boolean put;
            do
            {
                i = rand.nextInt(3);
                j = rand.nextInt(3);
                k = rand.nextInt(2);
                put = grid.put("cpu", i, j, symbols[k]);
            }
            while(!put);
        }
        else//Play using the minimax algorithm
        {
            MiniMax  mm = new MiniMax();//Create the "brain"
            char[] nextMove = mm.nextMove(grid);//Find next move using minimax
            grid.put("minimax", nextMove[0] - '0', nextMove[1] - '0', nextMove[2]);//Put move into grid
            System.out.println("Cases tested: " + MiniMax.numberOfChildren);
        }

        return grid.someoneHasWon();//If someone won that is you
    }
}
