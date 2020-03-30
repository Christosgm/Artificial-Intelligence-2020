public class PlayerFactory
{
    //Create player based on name
    public Player createPlayer(String name)
    {
        if(!name.equals("minimax"))
        {
            return new HumanPlayer(name);
        }
        return new ComputerPlayer("minimax");//If minimax is the name then computer player is created
    }
}
