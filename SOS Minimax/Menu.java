import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu
{
    //Enter the command to do stuff
    public String[] enterCommand() throws IOException, InterruptedException
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

    //Clear screen
    public void clrScr() throws IOException, InterruptedException
    {
        //Operating System name
        String os = System.getProperty("os.name");

        if (os.contains("Windows"))
        {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();//Clear screen on Windows
        }
        else
        {
            System.out.print("\033[H\033[2J");//Clear screen on other Systems
            System.out.flush();
        }
    }

    private boolean checkParams(String[] commands)
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
            if (commands[i].equals("start") || commands[i].equals("exit") || commands[i].equals("help"))
            {
                return false;
            }
        }

        //If command is exit or help the length can only be 1
        return (!commands[0].equals("exit") && !commands[0].equals("help")) || commands.length == 1;
    }

    private void printHelp()
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
                        "                                  minimax :  computer plays using the minimax algorithm.\n"+
                        "                                  cpu     :  computer plays at random.\n"+
                        "exit : Exits the program.\n" +
                        "help : Shows info.\n"
        );
    }

}
