import java.util.ArrayList;

public class MiniMax
{
    public static int numberOfChildren; //Counts the tree nodes of a given state

    private static class Node
    {
        protected Grid grid;
        protected ArrayList<Node> children;

        public Node(Grid grid)
        {
            this.grid = grid;
            children = new ArrayList<>();
        }

        //Find all possible next moves
        public void findChildren()
        {
            char[][] board = grid.getGrid();
            Grid grid = new Grid(board);
            //If someone has won then finding its children is not needed
            if(grid.someoneHasWon())
            {
                return;
            }
            for(int i = 0; i < board.length; i++)
            {
                for(int j = 0; j < board.length; j++)
                {
                    if(board[i][j] == ' ')
                    {
                        board[i][j] = 'S';
                        children.add(new Node(new Grid(board)));
                        board[i][j] = 'O';
                        children.add( new Node(new Grid(board)));
                        board[i][j] = ' ';
                        MiniMax.numberOfChildren+=2;
                    }
                }
            }
        }
    }

    //Evaluates a board state. Depth is taken into consideration
    private int evaluateState(int depth, boolean someoneWon, boolean isDraw, boolean isMaximizer)
    {
        //If terminal state is draw
        if(isDraw && !someoneWon)
        {
            return 0;
        }
        //If you won in the terminal state
        if(someoneWon && isMaximizer)
        {
            return 10 - depth;
        }
        //If the other player won in the terminal state
        if(someoneWon)
        {
            return -10 + depth;
        }

        //If it is not a terminal state yet
        return 0;
    }

    public char[] nextMove(Grid grid)
    {
        numberOfChildren = 0;
        //Make starting decision node
        Node state = new Node(grid);
        Node bestState = null;
        int bestScore = Integer.MIN_VALUE;
        char[] position = {' ', ' ', ' '};

       //Find possible next moves
        state.findChildren();

        //For every possible next move
        for(int i = 0; i < state.children.size(); i++)
        {
            Node child = state.children.get(i);

            //Compute the score using minimax
            int score = minimax(child, 1, false);

            //If the current score is the best so far
            if(score > bestScore)
            {
                //Make it the best score
                bestScore = score;

                //Child is the best so far
                bestState = child;
            }
        }

        //Find the difference between current state and best next state
        char[][] board = bestState.grid.getGrid();
        for(int i = 0; i < board.length; i++)
        {
            for(int j = 0; j < board.length; j++)
            {
                if(board[i][j] != state.grid.getGrid()[i][j])
                {
                    position[0] = (char)(i + '0');
                    position[1] = (char)(j + '0');
                    position[2] = board[i][j];
                }
            }
        }

        //Return the best move that you found
        return position;
    }

    private int minimax(Node state, int depth, boolean isMaximizer)
    {
        boolean someoneWon = state.grid.someoneHasWon();
        boolean isDraw =  state.grid.isDraw();

        //If the game is in a terminal state
        if(someoneWon || isDraw)
        {
            return evaluateState(depth, someoneWon, isDraw, !isMaximizer);
        }

        //MAX player's turn
        if(isMaximizer)
        {
            //Find maximum score of all possible state's scores
            int bestScore = Integer.MIN_VALUE;
            state.findChildren();

            //While the current state has children to explore
            while(state.children.size() != 0)
            {
                Node child = state.children.remove(0);

                //Compute the score for the child
                int score = minimax(child, depth + 1, false);
                bestScore = Math.max(bestScore, score);
            }

            //Return the best score found
            return bestScore;
        }
        else//MIN player's turn
        {
            //Find minimum score of all possible move's scores
            int bestScore = Integer.MAX_VALUE;
            state.findChildren();

            //While the current state has children to explore
            while(state.children.size() != 0)
            {
                Node child = state.children.remove(0);

                //Compute the score for the child
                int score = minimax(child, depth + 1, true);
                bestScore = Math.min(bestScore, score);
            }

            //Return the worst score found
            return bestScore;
        }
    }
}
