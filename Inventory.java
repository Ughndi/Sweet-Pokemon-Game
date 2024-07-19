import java.util.List;
import java.util.ArrayList;

public class Inventory {
    private List<Creature> aCreatures;
    private Creature activeCreature;

    /**
     * Inventory constructor 
     * 
     * @param activeCreature - active creature in the inventory
     */
    public Inventory(Creature activeCreature) {
        this.aCreatures = new ArrayList<>();
        this.activeCreature = activeCreature;
    }

    /**
     * A method that displays the inventory and its menu
     */
    public void view() {
        while (true){
            PokemonRPG.clearScreen();
            System.out.println("INVENTORY");
            displayCreatures();
            System.out.println();
            displayActiveCreature();
            System.out.println();
            System.out.println("Inventory Menu");
            System.out.println("[1] Change active creature");
            System.out.println("[2] Go back to MAIN MENU");
            System.out.println("Select an option: ");
        
            int choice = PokemonRPG.getScanner().nextInt();
            PokemonRPG.getScanner().nextLine(); // Consume newline
        
            switch (choice) {
                case 1:
                    if (aCreatures.size() == 1) {
                        System.out.println("You cannot change because you only have 1 creature in your inventory.");
                        PokemonRPG.getScanner().nextLine();
                    } else {
                        PokemonRPG.clearScreen();
                    }
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid index.");
                    PokemonRPG.getScanner().nextLine();
                    break;
                }
        }   
    }
    
    /**
     * A method that adds a creature to the inventory
     */
    public void addCreature(Creature creature) {
        aCreatures.add(creature);
    }

    /**
     * A method that removes a creature from the inventory
     * 
     * @param creature - the creature to be removed
     * @return true if the removal is successful, false otherwise
     */
    public boolean removeCreature(Creature creature) {
        return aCreatures.remove(creature);
    }

    /**
     * A setter for the active creature
     */
    public void setActiveCreature(Creature creature) {
        activeCreature = creature;
        System.out.println(creature.getName() + "is your new active creature");
    }

    /**
     * A method that gets a creature from the inventory based on its index
     * 
     * @param index - the index of the creature in the inventory
     * @return the creature at the specified index, or null if index is out of bounds
     */
    public Creature getCreature(int index) {
        if (index >= 0 && index < aCreatures.size()) {
            return aCreatures.get(index);
        }
        return null;
    }
    
    /**
     * A getter for the active creature
     * 
     * @return activeCreature
     */
    public Creature getActiveCreature() {
        return activeCreature; 
    }

    /**
     * A getter for all creatures in the inventory
     * 
     * @return all creatures in the inventory
     */
    public List<Creature> getInventory() {
        return aCreatures;
    }   

    /**
     * A method that displays all creatures in the inventory including
     * its name, type, family, and evolution level
     */
    public void displayCreatures() {
    System.out.println("All Creatures:");
        for (int i = 0; i < aCreatures.size(); i++) {
            Creature creature = aCreatures.get(i);
            System.out.println("Index: " + (i + 1) + ", Name: " + creature.getName() + ", Type: " + creature.getType() +
                    ", Family: " + creature.getFamily() + ", EL: " + creature.getEL());
        }
    }

    /**
     * A method that displays the active creature in the inventory
     * including its name, type, family, and evolution level
     */
    public void displayActiveCreature() {
        if (activeCreature != null) {
            System.out.println("Active Creature:");
            System.out.println("Name: " + activeCreature.getName() + ", Type: " + activeCreature.getType() +
                    ", Family: " + activeCreature.getFamily() + ", EL: " + activeCreature.getEL());
        } else {
            System.out.println("No active creature.");
        }
    }
}
