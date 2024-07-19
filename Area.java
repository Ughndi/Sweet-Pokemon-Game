import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Area {
    private int nWidth;
    private int nHeight;
    private int playerX = 0;
    private int playerY = 0;
    private double fEncounterRate;
    private Inventory CInventory;
    private List<Creature> aCreaturesInArea1 = new ArrayList<>();
    private List<Creature> aCreaturesInArea2 = new ArrayList<>();
    private List<Creature> aCreaturesInArea3 = new ArrayList<>();


    /**
     * Area constructor
     * 
     * @param nWidth - width of the area
     * @param nHeight - height of the area
     * @param fEncounterRate - encounter rate in the area
     * @param CInventory - inventory containing all creatures of the player
     */
    public Area(int nWidth, int nHeight, double fEncounterRate, Inventory CInventory) {
        this.nWidth = nWidth;
        this.nHeight = nHeight;
        this.fEncounterRate = 0.4;
        this.CInventory = CInventory;
        aCreaturesInArea1.addAll(PokemonRPG.getEl1Creatures());
        aCreaturesInArea2.addAll(PokemonRPG.getEl1Creatures());
        aCreaturesInArea2.addAll(PokemonRPG.getEl2Creatures());
        aCreaturesInArea3.addAll(PokemonRPG.getEl1Creatures());
        aCreaturesInArea3.addAll(PokemonRPG.getEl2Creatures());
        aCreaturesInArea3.addAll(PokemonRPG.getEl3Creatures());
    }

    /**
     * A method that moves the player based on the input
     * 
     * @param strDirection - direction of player's chosen movement
     * @return true - if move is valid
     */
    public boolean move(String strDirection) {
        switch (strDirection.toUpperCase()) {
            case "UP":
                if (playerY - 1 >= 0) {
                    playerY--;
                    return true;
                }
                break;
            case "DOWN":
                if (playerY + 1 < nHeight) {
                    playerY++;
                    return true;
                }
                break;
            case "LEFT":
                if (playerX - 1 >= 0) {
                    playerX--;
                    return true;
                }
                break;
            case "RIGHT":
                if (playerX + 1 < nWidth) {
                    playerX++;
                    return true;
                }
                break;
        }
        return false;
    }

    /**
     * A method determines if the player will meet a creature
     * based on encounter rate
     * 
     * @return true if player encounters a creature
     */
    public boolean encounterCreature() {
        Random CRandom = new Random();
        return CRandom.nextDouble() < getEncounterRate();
    }

    /**
     * A method that displays the area using O and the player's
     * position using X
     */
    public void displayArea() {
        for (int i = 0; i < nHeight; i++) {
            for (int j = 0; j < nWidth; j++) {
                if (j == playerX && i == playerY) {
                    System.out.print("X ");
                } else {
                    System.out.print("O ");
                }
            }
            System.out.println();
        }
    }

    /**
     * A method that obtains the calculated encounter rate in the area
     * 
     * @return encounterRate 
     */
    public double getEncounterRate() {
        return fEncounterRate;
    }

    /**
     * A method that sets the creatures in the specified area
     * 
     * @param creatures - creatures in the area
     * @param nArea - the number of the area (1, 2, or 3)
     */
    public void setCreaturesInArea(List<Creature> creatures, int nArea) {
        switch (nArea) {
            case 1:
                aCreaturesInArea1 = creatures;
                break;
            case 2:
                aCreaturesInArea2 = creatures;
                break;
            case 3:
                aCreaturesInArea3 = creatures;
                break;
            default:
                break;
        }
    }

    /**
     * A method that obtains the creatures in the specified area
     * 
     * @param nArea - the number of the area (1, 2, or 3)
     * @return List<Creature> - creatures in the specified area
     */
    public List<Creature> getCreaturesInArea(int nArea) {
        switch (nArea) {
            case 1:
                return aCreaturesInArea1;
            case 2:
                return aCreaturesInArea2;
            case 3:
                return aCreaturesInArea3;
            default:
                return new ArrayList<>(); 
        }
    }


    /**
     * A method that allows player to explore an area
     * 
     * @param CArea - area to be explored
     * @param aCreatures - list of creatures in the area
     * @param CInventory - inventory containing all creatures of the player
     */
    private void exploreArea(Area CArea, List<Creature> aCreatures,  Inventory CInventory, int nArea) {
        boolean exploring = true;
        Random CRandom = new Random();
        Creature activeCreature = CInventory.getActiveCreature();

        while (exploring) {
            PokemonRPG.clearScreen();
            CArea.displayArea();

            System.out.print("Enter move (UP, DOWN, LEFT, RIGHT, or EXIT): ");
            String move = PokemonRPG.getScanner().nextLine();

            if (move.equalsIgnoreCase("EXIT")) {
                exploring = false;
                continue;
            }

            if (CArea.move(move)) {
                if (CArea.encounterCreature()) {
                    // Determine EL of encountered creature based on area's encounter rate
                    List<Creature> availableCreatures = getCreaturesForArea(CArea, aCreatures);
                    Creature encounteredCreature = availableCreatures.get(CRandom.nextInt(availableCreatures.size()));
                    Battle CBattle = new Battle(activeCreature, encounteredCreature, CInventory);
                    CBattle.start();
                }
            } else {
                System.out.println("Invalid move!");
                PokemonRPG.getScanner().nextLine();
            }
        }
    }

    /**
     * A method that allows player to explore Area 1 
     */
    public void exploreArea1() {
        Area CArea = new Area(5, 1, 0.4, CInventory);
        exploreArea(CArea, aCreaturesInArea1, CInventory, 1);
    }

    /**
     * A method that allows player to explore Area 2 
     */
    public void exploreArea2() {
        Area CArea = new Area(3, 3, 0.4, CInventory);
        exploreArea(CArea, aCreaturesInArea2, CInventory, 2);
    }
    
    /**
     * A method that allows player to explore Area 3 
     */
    public void exploreArea3() {
        Area CArea = new Area(4, 4, 0.4, CInventory);
        exploreArea(CArea, aCreaturesInArea3, CInventory, 3);
    }

    /**
     * A method that obtains the creatures in the area
     * 
     * @param CArea - area of the creatures
     * @param aCreatures - list of creatures
     * 
     * @return creatures - creatures in the area
     */
    private static List<Creature> getCreaturesForArea(Area CArea, List<Creature> aCreatures) {
        double encounterRate = CArea.getEncounterRate();
        if (encounterRate == 0.4) {
            return aCreatures;
        } else if (encounterRate == 0.6) {
            return aCreatures;
        } else if (encounterRate == 0.8) {
            return aCreatures;
        }
        return new ArrayList<>(); 
    }
}
