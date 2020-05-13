import java.util.Scanner;
import java.util.regex.*;
import java.util.ArrayList;
import java.util.Random;
import java.nio.file.*;
import java.io.IOException;

class Search2{

    private String startingSequence; // The state we start the search from
    private int whiteBalls; // The number of white balls (=black balls)
    private int expandedNodes; // How many nodes the current search has expanded

    // Node of the search tree
    class Node{
        private String currentOutput; // Current state
        private int nodeG; // Value of g(n) (cost from root until here)
        private int nodeH; // Value of h(n) (cost heuristic gives)
        private Node parent; // The parent node

        // Constructor - Accessors
        public Node(String currentOutput,int currentCost, Node parent){
            this.currentOutput = currentOutput;
            this.nodeG = currentCost;
            this.parent = parent;
            this.nodeH = 0;
        }
        public String getCurrentOutput(){
            return currentOutput;
        }
        // If we use ucs, nodeH is 0
        public int getCurrentCost(){
            return nodeG + nodeH;
        }

        public Node getParent(){
            return parent;
        }
        // Mutator for h(n)
        public void setNodeH(int increment){
            nodeH = increment;
        }

        public int getNodeG(){
            return nodeG;
        }
    }

    //Always initialise a correct input sequence
    public Search2(String start){
        //correctInputHandler();
        whiteBalls = start.length()/2;
        startingSequence = start;

    }

    // Makes sure that the input the user gave is correct and prepares any
    // variables accordingly
    private void correctInputHandler(){
        Scanner input = new Scanner(System.in);
        System.out.println("Give me the starting sequence or a number k to"+
        "generate random sequence of length 2*k+1:");
        System.out.println("(need equal number of \"A\"s and \"M\"s and a dash)");
        do {
            // Either get n and make a random startingSequence(length 2n+1)
            if(input.hasNextInt()) {
                whiteBalls = input.nextInt();
                randomStart();
            }else{
                // or get the startingSequence and check if it is valid
                startingSequence = input.nextLine();
                countLetters();
                whiteBalls = startingSequence.length()/2;
            }
        }while (!startingSequence.matches("[AM]*-[AM]*") || !countLetters());
    }

    // Checks if the input has equal As and Ms and a dash
    private boolean countLetters(){
        Pattern letterM = Pattern.compile("M");
        Pattern letterA = Pattern.compile("A");
        Matcher matcherForM = letterM.matcher(startingSequence);
        Matcher matcherForA = letterA.matcher(startingSequence);
        int countA = 0; int countM = 0;
        // Firstly count numbers of A, and the numbers of M
        while (matcherForA.find())
            countA++;
        while (matcherForM.find())
            countM++;
        return countM == countA;
    }

    // Creates a random starting sequence of given size (2n + 1)
    // Note: this seems complicated but works... given time it might change
    // to something more readable
    private void randomStart(){
        // Init stuff
        Random rand = new Random();
        startingSequence = "";
        int AorM;
        int blackCounter = whiteBalls;
        int whiteCounter = whiteBalls;
        // Random place the dash will go
        int dashIndex = rand.nextInt(2*whiteBalls+1);
        // while we havent placed all balls and the space
        while (blackCounter > 0 || whiteCounter > 0 || dashIndex >= 0 ){
            // Time to place the dash
            if (dashIndex == 0){
                startingSequence += "-";
                dashIndex--;
            }
            // else reduce the loops needed until placing the dash
            else dashIndex --;
            // Randomly decide to place A or M next
            AorM = rand.nextInt(2);
            // If we placed all white or black balls make sure other colour
            // will be placed
            if (blackCounter == 0) AorM = 0;
            if (whiteCounter == 0) AorM = 1;
            // Place the ball
            if (AorM == 0 && whiteCounter > 0 ){
                startingSequence += "A";
                whiteCounter--;
            }else if (blackCounter >0){
                startingSequence += "M";
                blackCounter--;
            }
        }
    }

    // Find the minimum costing node in the list
    private Node extractMin(ArrayList<Node> listOfNodes){
        Node min = listOfNodes.get(0);
        for (Node node: listOfNodes){
            if (node.getCurrentCost()< min.getCurrentCost())
                min = node;
        }
        listOfNodes.remove(min);
        return min;
    }

    // Creates all children of a node
    private ArrayList<Node> findAllChildren(Node parent, int mode){
        expandedNodes++;
        ArrayList<Node> children = new ArrayList<>();
        int emptyIndex = parent.getCurrentOutput().indexOf('-');
        int leftIndex = emptyIndex;
        int rightIndex = emptyIndex;
        // Only possible to move positions <= whiteBall number
        for (int i = 1; i <= whiteBalls; i++){
            // From the dash position going towards the start and the end of the
            // string, if we are not out of bounds make a child by swapping the
            // dash with a ball and calculating its cost (parentCost + moveCost)
            if (--leftIndex >= 0 ) children.add(new Node(swapLetters(
            parent.getCurrentOutput(),leftIndex,emptyIndex),emptyIndex-leftIndex+
            parent.getNodeG(),parent));
            if (++rightIndex < parent.getCurrentOutput().length())
                children.add(new Node(swapLetters(parent.getCurrentOutput(),
                emptyIndex,rightIndex),rightIndex-emptyIndex+parent.getNodeG(),parent));
        }
        // If we use A* for every child calculate the Heuristic cost
        if (mode == 1)
            for (Node child:children)
                addHeuristic(child);
        return children;
    }

    // Adds extra cost to the children nodes according to the heuristic
    private void addHeuristic(Node childNode){
        int extraValue = 0;
        // Go through the whole string
        for (int i = 0 ; i < 2*whiteBalls+1 ; i++)
            // For every non-M character until middle-1 calculate its distance
            // from the middle
            if ( i < whiteBalls && childNode.getCurrentOutput().charAt(i) != 'M')
                extraValue += whiteBalls - i;
            // For every M character after middle-1 calculate its distance from
            // the middle
            else if (i > whiteBalls && childNode.getCurrentOutput().charAt(i) == 'M')
                extraValue += i - whiteBalls;
        // These two distances for every postition gives us cost to get all M
        // characters to the leftMost spaces if every move was allowed
        childNode.setNodeH(extraValue);
    }


    // Swaps the positions of first and second index in a string
    private String swapLetters(String input, int firstIndex, int secondIndex){
        return input.substring(0, firstIndex) + input.charAt(secondIndex) +
        input.substring(firstIndex+1, secondIndex) + input.charAt(firstIndex) +
        input.substring(secondIndex+1, input.length());
    }

    // The regex of a final state
    private boolean checkSolution(String currentSolution){
        return currentSolution.matches("[M]*[A\\-]*A");
    }

    // Starts a search using UCS
    private void uniformCostSearch(){
        System.out.println("=================================================");
        System.out.println("Uniform Cost Search");
        System.out.println("=================================================");
        search(0);
    }

    // Starts a search using the A* algorithm
    private int AStar(){
        //System.out.println("=================================================");
        //System.out.println("A* Algorithm");
        //System.out.println("=================================================");
        return search(1);
    }

    // Basic skeleton of a search based on 2_blind_search.pdf page 18
    private Node searchAlgorithm(int mode){
        Node searchingNode;
        ArrayList<Node> searchSpace = new ArrayList<>();
        ArrayList<String> closedSet = new ArrayList<>();
        // 1. Add stating state to seachSpace
        searchSpace.add(new Node(startingSequence,0,null));
        while (true){
            // 2. If searchSpace is empty stop
            if (searchSpace.size() == 0) return null;
            // 3. Get the node with the smallest cost
            searchingNode = extractMin(searchSpace);
            // 4. If it is in the closed set go back to 2
            if (closedSet.contains(searchingNode.getCurrentOutput())) continue;
            // 5. If this is accepted final state, return it
            if (checkSolution(searchingNode.getCurrentOutput()))
                return searchingNode;
            // 6. Create the childen with the accepted transitions
            // 7. Add the children to the seachSpace
            searchSpace.addAll(findAllChildren(searchingNode,mode));
            // 8. Add parent node to the closedSet
            closedSet.add(searchingNode.getCurrentOutput());
            // 9. Go back to 2
        }
    }

    // Starts the search and prints the results after it ends
    private int search(int mode){
        expandedNodes = 0;
        // Searching part
        Node searchResult = searchAlgorithm(mode);
        if (searchResult == null){
            //System.out.println("No possible solution found");
            return -1;
        }
        return searchResult.getNodeG();
        // Printing path
        //System.out.println("One final positioning of the balls with cost "+
        //searchResult.getNodeG() + " is : "+ searchResult.getCurrentOutput());
        //System.out.println("Total nodes expanded: " + expandedNodes);
        //System.out.println("The path that was found is: ");
        //printPath(searchResult);
    }

    private String getStart(){
        return startingSequence;
    }

    // Prints the search path using recursion
    private void printPath(Node givenNode){
        if (givenNode.parent != null) printPath(givenNode.getParent());
        System.out.println(givenNode.getCurrentOutput());
    }


    public static void main(String[] args) {
        Search2 search = new Search2(args[0]);
        // Start the ucs search
        //long ucsStartTime = System.nanoTime();
        //search.uniformCostSearch();
        // Start the A* search
        try {
            Files.write(Paths.get("start.txt"), (search.getStart()+"\n").getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("value.txt"), (String.valueOf(search.AStar())+"\n").getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
        //long aStarStartTime = System.nanoTime();
        // Print the times the algorithms took to run
        //long finalTime = System.nanoTime();
        //System.out.println("UCS total runtime was: "+ (aStarStartTime - ucsStartTime));
        //System.out.println("A* total runtime was: "+ (finalTime - aStarStartTime));
        //System.out.println("Difference: "+ (2*aStarStartTime - ucsStartTime - finalTime));
    }
}
