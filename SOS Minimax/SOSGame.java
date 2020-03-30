import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SOSGame
{
    private static void clrScr() throws IOException, InterruptedException
    {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
    }

	//Enter the command to do stuff
    private static String[] enterCommand() throws IOException, InterruptedException
    {
        String[] commands = null;
        //Ends when the command given is valid
        do
        {
            try
            {
            	//Enter the command
                System.out.print("Input command(type help for info): ");
                Scanner input = new Scanner(System.in);
                commands = input.nextLine().split(" ");

                //Check for validity and if not valid raise InputMismatchException
                if(!checkParams(commands))
                {
                    throw new InputMismatchException();
                }

                //If command is help print guide
                if(commands[0].equals("help"))
                {
                    clrScr();
                    printHelp();
                }
            }
            catch (InputMismatchException e)//If command is not valid
            {
                System.out.println("Invalid command!");
            }
        }
        while(!checkParams(commands) || commands[0].equals("help"));//When help is chosen we need to enter a new command 
        return commands;
    }

    private static boolean checkParams(String[] commands)
    {
    	//Command length can be 1 or 3
        if(commands.length != 3 && commands.length != 1)
        {
            return false;
        }

        //First arg can only be start, exit or help
        if(!commands[0].equals("start")  && !commands[0].equals("exit")  && !commands[0].equals("help"))
        {
            return false;
        }

        //The other arguments cannot be keywords
        for(int i = 1; i < commands.length; i++)
        {
            if(commands[i].equals("start") || commands[i].equals("exit") || commands[i].equals("help"))
            {
                return false;
            }
        }

        //If command is exit or help the length can only be 1
        if((commands[0].equals("exit")  || commands[0].equals("help")) && commands.length != 1)
        {
            return false;
        }

        return true;
    }

    private static void printHelp()
    {
        System.out.println(
                "\nGAME RULES\n" +
                "Players take turns to add either an \"S\" or an \"O\" to any square,\n" + 
                "with no requirement to use the same letter each turn.\n\n" + 
                "The object of the game is for each player to attempt to create the\n" + 
                "straight sequence S-O-S among connected squares either diagonally,\n" + 
                "horizontally, or vertically.\n\n" + 
                "If a player succeeds in creating a SOS, he is declared the winner.\n\n" +
                "If the board fills, without any SOS then the game is considered a draw.\n\n" +
                
                "VALID COMMANDS\n" +
                "start <name_of_p1> <name_of_p2> : Starts a game with <name_of_p1> being the first player.\n"+
                "                                  If minimax is used as a name then the computer plays\n" + 
                "                                  using the minimax algorithm.\n" +
                "exit : Exits the program.\n" +
                "help : Shows info.\n"
        );
    }

    public static void main(String[] args) throws IOException, InterruptedException
    {
        String[] commands;
        clrScr();

        //Ends if command is exit
        do
        {
        	//Enter command
            commands = enterCommand();

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

            while(!grid.isDraw() && winner.equals(""))//While game is not a draw or there is not a winner
            {
                clrScr();
                System.out.println("  -TURN #" + turn + "-");
                grid.print();//Print grid
                if(turn%2 == 1)//If it is player 1 turn
                {
                    System.out.println(player1.getName() + " play!\n");
                    if(player1.play(grid))//Play and if player 1 wins
                    {
                        winner = player1.getName();
                    }
                }
                else //Else it is player 2 turn
                {
                    System.out.println(player2.getName() + " play!\n");
                    if(player2.play(grid))//Play and if player 2 wins
                    {
                        winner = player2.getName();
                    }
                }
                turn++;
            }

            clrScr();
            System.out.println("  -TURN #" + turn + "-");
            grid.print();
            if(winner.equals(""))
            {
                System.out.println("Draw!\n");
            }
            else
            {
                System.out.println(winner + " wins!\n");
            }

        }
        while(true);
    }
}
