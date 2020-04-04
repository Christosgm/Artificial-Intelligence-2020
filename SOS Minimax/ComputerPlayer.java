import java.util.Random;

public class ComputerPlayer extends Player
{
    public ComputerPlayer(String name)
    {
        super(name);
    }

    //Returns true if the move played is a winning move
    @Override
    public boolean play(Grid grid)
    {
        if(getName().equals("cpu"))
        {
          playAtRandom(grid);
        }
        else
        {
          playWithMiniMax(grid);
        }

        return grid.someoneHasWon();//If someone won that is you
    }

    //Plays at random
    private void playAtRandom(Grid grid)
    {
        Random rand = new Random();
        char[] symbols = {'S', 'O'};
        char[] position;
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

    //Plays using minimax
    private void playWithMiniMax(Grid grid)
    {
        MiniMax  mm = new MiniMax();//Create the "brain"
        char[] nextMove = mm.nextMove(grid);//Find next move using minimax
        grid.put("minimax", nextMove[0] - '0', nextMove[1] - '0', nextMove[2]);//Put move into grid
    }
}
