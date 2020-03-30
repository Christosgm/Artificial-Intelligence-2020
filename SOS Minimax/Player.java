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

    //Check if sequences are created by placing in [row, col]
    protected int isWinningMove(int row, int col, Grid grid)
    {
        String[][] gridStringArray = grid.getGrid();
        int score = 0;

        String rowWord = "";
        String colWord = "";
        String diag1Word = "";
        String diag2Word = "";

        //Check the row and column of the coordinate
        for(int i = 0; i < gridStringArray.length; i++)
        {
            rowWord += gridStringArray[i][col];
            colWord += gridStringArray[row][i];
        }

        //If coordinate is in main diagonal check it
        if((row == col))
        {
            for(int i = 0; i < gridStringArray.length; i++)
            {
                diag1Word += gridStringArray[i][i];
            }
        }

        //If coordinate is in secondary diagonal check it
        if(row + col == 2)
        {
            for(int i = 0; i < gridStringArray.length; i++)
            {
                diag2Word += gridStringArray[i][2 - i];
            }
        }

        //Add up SOS sequences
        if(rowWord.equals("SOS"))
        {
            score++;
        }
        if(colWord.equals("SOS"))
        {
            score++;
        }
        if(diag1Word.equals("SOS"))
        {
            score++;
        }
        if(diag2Word.equals("SOS"))
        {
            score++;
        }

        //Return sequences created
        return score;
    }
}
