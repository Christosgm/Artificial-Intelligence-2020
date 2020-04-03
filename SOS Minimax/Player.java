public abstract class Player
{
    private String name;//Player name

    public Player(String name)
    {
        //Initiate player
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    abstract boolean play(Grid grid);
}
