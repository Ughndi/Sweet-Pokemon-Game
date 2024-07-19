import java.util.List;
import java.util.Scanner;

import javax.swing.SwingUtilities;

public class PokemonRPG {

    private static Scanner CScanner;
    private static List<EL1Creature> aEl1Creatures;
    private static List<EL2Creature> aEl2Creatures;
    private static List<EL3Creature> aEl3Creatures;

    private static Inventory CInventory;
    private static Evolution CEvolution;
    private static Area CArea;
    
    /**
     * PokemonRPG constructor
     */
    public PokemonRPG(){
        CScanner = new Scanner(System.in);
        aEl1Creatures = EL1Creature.initializeEL1Creatures();
        aEl2Creatures = EL2Creature.initializeEL2Creatures();
        aEl3Creatures = EL3Creature.initializeEL3Creatures();
        CInventory = new Inventory(null); 
        CEvolution = new Evolution(CScanner); 
        CArea = new Area(0, 0, 0, CInventory);
    }

    /**
     * A method that runs the PokemonRPG game
     */
    public void run() {
        SwingUtilities.invokeLater(() -> {
            PokemonRPG pokemonRPG = new PokemonRPG();
            PokemonGUI pokemonGUI = new PokemonGUI(pokemonRPG);
        });

        CScanner.nextLine();
        
        while (true) {
            clearScreen();
            System.out.println("MENU:");
            System.out.println("[1] View Inventory");
            System.out.println("[2] Explore Areas");
            System.out.println("[3] Evolve");
            System.out.println("[4] Exit");
            System.out.print("Select an option: ");

            int nChoice = CScanner.nextInt();
            CScanner.nextLine();  

            switch (nChoice) {
                case 1:
                    clearScreen();
                    CInventory.view();
                    break;
                case 2:
                    clearScreen();
                    System.out.println("Choose an Area to explore:");
                    System.out.println("[1] Area 1");
                    System.out.println("[2] Area 2");
                    System.out.println("[3] Area 3");
                    System.out.println("[4] EXIT");
                    System.out.print("Select an option: ");

                    int nAChoice = CScanner.nextInt();
                    CScanner.nextLine();

                    switch (nAChoice) {
                        case 1: 
                            CArea.exploreArea1();
                            break;
                        case 2:
                            CArea.exploreArea2();
                            break;
                        case 3: 
                            CArea.exploreArea3();
                            break;
                        case 4:
                            break;
                        default:
                        System.out.println("Invalid choice.");
                        CScanner.nextLine();
                    }
                    
                    break;
                case 3:
                    clearScreen();
                    CEvolution.evolveCreature(CInventory); 
                    CScanner.nextLine();
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice.");
                    CScanner.nextLine();
            }
        }
    }

    /**
     * A getter for the list of evolution level 1 creatures
     * 
     * @return the list of evolution level 1 creatures
     */
    public static List<EL1Creature> getEl1Creatures() {
        return aEl1Creatures;
    }

    /**
     * A getter for the list of evolution level 2 creatures
     * 
     * @return the list of evolution level 2 creatures
     */
    public static List<EL2Creature> getEl2Creatures() {
        return aEl2Creatures;
    }
    
    /**
     * A getter for the list of evolution level 3 creatures
     * 
     * @return the list of evolution level 3 creatures
     */
    public static List<EL3Creature> getEl3Creatures() {
        return aEl3Creatures;
    }

    /**
     * A method that obtains the scanner
     * 
     * @return scanner 
     */
    public static Scanner getScanner() {
        return CScanner;
    }

    /**
     * A method that obtains the inventory
     * 
     * @return inventory 
     */
    public static Inventory getInventory(){
        return CInventory;
    }

    /**
     * A method that obtains the evolution
     * 
     * @return evolution 
     */
    public static Evolution getEvolution(){
        return CEvolution;
    }

    /**
     * A method that obtains the area
     * 
     * @return area 
     */
    public static Area getArea(){
        return CArea;
    }

    /**
     * A method that allows the user to select starter creature
     * 
     * @param CInventory - contains creatures
     * @param selectedCreature - selected evolution level 1 creature
     */
    public static void selectStarter(EL1Creature selectedCreature, Inventory CInventory) {
        clearScreen();
    
        Creature starter = selectedCreature;
        CInventory.addCreature(starter);

        CInventory.setActiveCreature(starter);
        System.out.println("You selected " + starter.getName() + " as your starter creature!");
    }
    
    
    /**
     * A method that clears the terminal screen
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}

 