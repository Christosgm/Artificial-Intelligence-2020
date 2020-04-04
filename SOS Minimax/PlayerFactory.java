public class PlayerFactory
{
    //Create player based on name
    public Player createPlayer(String name)
    {
        if(!name.equals("minimax") && ! name.equals("cpu"))
        {
            return new HumanPlayer(name);
        }
        return new ComputerPlayer(name);//If minimax or cpu is the name then computer player is created
    }
}
