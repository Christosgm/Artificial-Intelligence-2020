import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SOSGame
{
	//Enter the command to do stuff
    private static String[] enterCommand()
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

        //if command is exit or help the length can only be 1
        if((commands[0].equals("exit")  || commands[0].equals("help")) && commands.length != 1)
        {
            return false;
        }

        return true;
    }

    private static void printHelp()
    {
        System.out.println(
                "\nPlayers take turns to add either an \"S\" or an \"O\" to any square, with no requirement to use the same letter each turn.\n" +
                "The object of the game is for each player to attempt to create the straight sequence S-O-S among connected squares \n" +
                "(either diagonally, horizontally, or vertically), and to create as many such sequences as they can. If a player succeeds\n" +
                "in creating an SOS, that player immediately takes another turn, and continues to do so until no SOS can be created on their turn.\n\n" +
                
                "VALID COMMANDS\n" +
                "start <name_of_p1> <name_of_p2> : Starts a game with <name_of_p1> being the first player.\n"+
                "                                  If minimax is used then CPU is selected as player.\n" +
                "exit : Exits the program.\n" +
                "help : Shows info.\n"
        );
    }

    public static void main(String[] args)
    {
        String[] commands;

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
            HumanPlayer player1 = new HumanPlayer(commands[1]);
            HumanPlayer player2 = new HumanPlayer(commands[2]);
            Grid grid = new Grid();

            int turn = 1;
            while(!grid.isTerminalState())//While game is not finished
            {
                System.out.printf("\n%s points: %d\n%s points: %d\n\n", player1.getName(), player1.getPoints(), player2.getName(), player2.getPoints());
                grid.print();//Print grid
                if(turn%2 == 1)//If it is player 1 turn
                {
                    System.out.println(player1.getName() + " play!\n");
                    if(player1.play(grid))//Play and if player 1 replays
                    {
                        turn++;
                    }
                }
                else //Else it is player 2 turn
                {
                    System.out.println(player2.getName() + " play!");
                    if(player2.play(grid))//Play and if player 2 replays
                    {
                        turn++;
                    }
                }
                turn++;
            }

            System.out.printf("\n%s points: %d\n%s points: %d\n\n", player1.getName(), player1.getPoints(), player2.getName(), player2.getPoints());
            grid.print();

            //Decide winner
            if(player1.getPoints() > player2.getPoints())
            {
                System.out.println(player1.getName() + " wins with " + player1.getPoints() + " points!");
            }
            else if(player2.getPoints() > player1.getPoints())
            {
                System.out.println(player2.getName() + " wins with " + player2.getPoints() + " points!");
            }
            else
            {
                System.out.println("Draw!");
            }

        }
        while(true);
    }
}
