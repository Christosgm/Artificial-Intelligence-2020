import java.io.IOException;
import java.util.Scanner;

public class Main
{
    //Create flow to watch computer play
    private static void stopFlow(String name)
    {
        if(name.equals("minimax") || name.equals("cpu"))
        {
            System.out.print(":> . . .");
            Scanner input = new Scanner(System.in);
            input.nextLine();
        }
    }

    //Returns the name of the winner if the move played is a winning move
    private static String play(Player player, Grid grid)
    {
        grid.print(player.getName());//Print grid
        if(player.play(grid))//Play and if player wins
        {
            stopFlow(player.getName());
            return player.getName();
        }
        stopFlow(player.getName());
        return "";
    }

    public static void main(String[] args) throws IOException, InterruptedException
    {
        Menu menu = new Menu();
        String[] commands;
        menu.clrScr();

        //Ends if command is exit
        do
        {
        	//Enter command
            commands = menu.enterCommand();

            //If command is exit terminate
            if(commands[0].equals("exit"))
            {
                System.out.println("Goodbye!");
                System.exit(0);
            }

            //Create players and grid
            PlayerFactory playerFactory = new PlayerFactory();
            Player player1 = playerFactory.createPlayer(commands[1]);
            Player player2 = playerFactory.createPlayer(commands[2]);

            Grid grid = new Grid();

            int turn = 1;
            String winner = "";

            while(!grid.isDraw() && !grid.someoneHasWon())//While game is not a draw or there is not a winner
            {
                menu.clrScr();
                System.out.println("-Turn #" + turn + "-");
                if(turn%2 == 1)//If it is player 1 turn
                {
                    winner = play(player1, grid);
                }
                else //Else it is player 2 turn
                {
                    winner = play(player2, grid);
                }
                turn++;
            }

            menu.clrScr();
            System.out.println("-Turn #" + (turn - 1) + "-");
            grid.print("");
            if(winner.equals(""))
            {
                System.out.println("Draw!");
            }
            else
            {
                System.out.println(winner + " wins!");
            }

        }
        while(true);
    }
}
