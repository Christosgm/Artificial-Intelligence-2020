import java.util.Scanner;
import java.util.regex.*;
import java.util.ArrayList;
import java.util.Random;


class Search{
    //The first sequence  and the number of white balls(black is the same)
    //Also keep the numbers of nodes that were expanded
    private String startingSequence;
    private int whiteBalls;
    private int expandedNodes;

    // Node of the search tree that we are expanding
    class Node{
        //A string showing ball positions, int for the cost and the parent node
        private String currentOutput;
        private int nodeG;
        private int nodeH;
        private Node parent;

        public Node(String currentOutput,int currentCost, Node parent){
            this.currentOutput = currentOutput;
            this.nodeG = currentCost;
            this.parent = parent;
            this.nodeH = 0;
        }
        public String getCurrentOutput(){
            return currentOutput;
        }

        public int getCurrentCost(){
            return nodeG + nodeH;
        }

        public Node getParent(){
            return parent;
        }

        public void increaseCost(int increment){
            nodeH += increment;
        }

        public int getNodeG(){
            return nodeG;
        }
    }

    //Always initialise a correct input sequence
    public Search(){
        correctInputHandler();
    }

    // Makes sure that the input the user gave is correct and prepares any
    // variables accordingly
    private void correctInputHandler(){
        Scanner input = new Scanner(System.in);
        System.out.println("Give me the starting sequence or a number k to"+
        "generate random sequence of length 2*k+1:");
        System.out.println("(need equal number of \"A\"s and \"M\"s and a dash)");
        do {
            if(input.hasNextInt()) {
                whiteBalls = input.nextInt();
                randomStart();
            }else{
                startingSequence = input.nextLine();
                countLetters();
                whiteBalls = startingSequence.length()/2;
            }
        }while (!startingSequence.matches("[AM]*-[AM]*") || !countLetters());
    }

    private boolean countLetters(){
        Pattern letterM = Pattern.compile("M");
        Pattern letterA = Pattern.compile("A");
        Matcher matcherForM = letterM.matcher(startingSequence);
        Matcher matcherForA = letterA.matcher(startingSequence);
        int countA = 0; int countM = 0;
        while (matcherForA.find())
            countA++;
        while (matcherForM.find())
            countM++;
        return countM == countA;
    }

    // Creates a random starting sequence of given size
    private void randomStart(){
        Random rand = new Random();
        startingSequence = "";
        int AorM;
        int blackCounter = whiteBalls;
        int whiteCounter = whiteBalls;
        int dashIndex = rand.nextInt(2*whiteBalls+1);
        while (blackCounter > 0 || whiteCounter > 0 || dashIndex >= 0 ){
            if (dashIndex == 0){
                startingSequence += "-";
                dashIndex--;
            }
            else dashIndex --;
            AorM = rand.nextInt(2);
            if (blackCounter == 0) AorM = 0;
            if (whiteCounter == 0) AorM = 1;
            if (AorM == 0 && whiteCounter > 0 ){
                startingSequence += "A";
                whiteCounter--;
            }else if (blackCounter >0){
                startingSequence += "M";
                blackCounter--;
            }
        }
        System.out.println("Starting from : "+ startingSequence);
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
        for (int i = 1; i <= whiteBalls; i++){
            if (--leftIndex >= 0 ) children.add(new Node(swapLetters(
            parent.getCurrentOutput(),leftIndex,emptyIndex),emptyIndex-leftIndex+
            parent.getNodeG(),parent));
            if (++rightIndex < parent.getCurrentOutput().length())
                children.add(new Node(swapLetters(parent.getCurrentOutput(),
                emptyIndex,rightIndex),rightIndex-emptyIndex+parent.getNodeG(),parent));
        }
        if (mode == 1)
            for (Node child:children)
                addHeuristic(child);
        return children;
    }

    // Adds extra cost to the children nodes according to the heuristic
    private void addHeuristic(Node childNode){
        int extraValue = 0;
        for (int i = 0 ; i < 2*whiteBalls+1 ; i++)
            if ( i < whiteBalls && childNode.getCurrentOutput().charAt(i) != 'M')
                extraValue += whiteBalls - i;
            else if (i > whiteBalls && childNode.getCurrentOutput().charAt(i) == 'M')
                extraValue += i - whiteBalls;
        childNode.increaseCost(extraValue);
    }


    // Swaps the positions of first and second index
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
    private void AStar(){
        System.out.println("=================================================");
        System.out.println("A* Algorithm");
        System.out.println("=================================================");
        search(1);
    }

    // Basic skeleton of a search
    private Node searchAlgorithm(int mode){
        Node searchingNode;
        ArrayList<Node> searchSpace = new ArrayList<>();
        ArrayList<String> closedSet = new ArrayList<>();
        searchSpace.add(new Node(startingSequence,0,null));
        while (true){
            if (searchSpace.size() == 0) return null;
            searchingNode = extractMin(searchSpace);
            if (closedSet.contains(searchingNode.getCurrentOutput())) continue;
            if (checkSolution(searchingNode.getCurrentOutput()))
                return searchingNode;
            searchSpace.addAll(findAllChildren(searchingNode,mode));
            closedSet.add(searchingNode.getCurrentOutput());
        }
    }

    // Starts the search and prints the results after it ends
    private void search(int mode){
        expandedNodes = 0;
        Node searchResult = searchAlgorithm(mode);
        if (searchResult == null){
            System.out.println("No possible solution found");
            return;
        }
        System.out.println("One final positioning of the balls with cost "+
        searchResult.getNodeG() + " is : "+ searchResult.getCurrentOutput());
        System.out.println("Total nodes expanded: " + expandedNodes);
        System.out.println("The path that was found is: ");
        printPath(searchResult);
    }

    // Prints the search path using recursion
    private void printPath(Node givenNode){
        if (givenNode.parent != null) printPath(givenNode.getParent());
        System.out.println(givenNode.getCurrentOutput());
    }


    public static void main(String[] args) {
        Search search = new Search();
        long ucsStartTime = System.nanoTime();
        search.uniformCostSearch();
        long aStarStartTime = System.nanoTime();
        search.AStar();
        long finalTime = System.nanoTime();
        System.out.println("UCS total runtime was: "+ (aStarStartTime - ucsStartTime));
        System.out.println("A* total runtime was: "+ (finalTime - aStarStartTime));
        System.out.println("Difference: "+ (2*aStarStartTime - ucsStartTime - finalTime));
    }
}
