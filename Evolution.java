import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Evolution {

    private Scanner CScanner;

    /**
     * Evolution Constructor
     * 
     * @param CScanner - scanner
     */
    public Evolution(Scanner CScanner) {
        this.CScanner = CScanner;
    }

    /**
     * A method that performs the evolution process
     * 
     * @param CInventory - inventory containing creatures
     */
    public void evolveCreature(Inventory CInventory) {
        System.out.println("EVOLUTION");
        
        if (CInventory.getInventory().size() < 2) {
            System.out.println("Cannot evolve. Need at least two creatures in the inventory.");
            return;
        }
        
        System.out.println("Creatures in Inventory:");
        CInventory.displayCreatures();
    
        System.out.println("Enter index of first creature for evolution: ");
        int index1 = CScanner.nextInt();
        
        System.out.println("Enter index of second creature for evolution: ");
        int index2 = CScanner.nextInt();
    
        if (!isValidIndex(index1, CInventory) || !isValidIndex(index2, CInventory)) {
            System.out.println("Invalid index. Please enter a valid index.");
            return;
        }
    
        Creature creature1 = CInventory.getCreature(index1);
        Creature creature2 = CInventory.getCreature(index2);
    
        if (isValidForEvolution(creature1, creature2)) {
            System.out.println("Evolution SUCCESS!");
    
            CInventory.removeCreature(creature1);
            CInventory.removeCreature(creature2);
    
            Creature evolvedCreature = createEvolvedCreature(creature1);
            CInventory.addCreature(evolvedCreature);
    
            System.out.println("Selected Creatures:");
            displayCreatureInfo(creature1);
            displayCreatureInfo(creature2);
    
            System.out.println("Evolved Creature:");
            displayCreatureInfo(evolvedCreature);
            CScanner.nextLine();
        } else {
            System.out.println("Evolution FAILED! Selected creatures are not eligible.");
            CScanner.nextLine();
        }
    }
    
/**
     * Checks if the index is valid
     * 
     * @return true if valid
     */    private boolean isValidIndex(int index, Inventory CInventory) {
        return index >= 0 && index < CInventory.getInventory().size();
    }
    
    /**
     * Check if the selected creatures are eligible for evolution
     * 
     * @param creature1 - first selected creature
     * @param creature2 - second selected creature
     * 
     * @return true if eligible
     */
    private boolean isValidForEvolution(Creature creature1, Creature creature2) {
        return creature1 != null && creature2 != null &&
               creature1.getEL() == creature2.getEL() &&
               creature1.getFamily().equals(creature2.getFamily()) &&
               creature1.getEL() < 3;
    }

    /**
     * Create an evolved creature based on the provided creature
     * 
     * @param creature - the creature to evolve
     * @return the evolved creature
     */
    private Creature createEvolvedCreature(Creature creature) {
        return new Creature(creature.getName() + " (Evolved)", creature.getType(), creature.getFamily(), creature.getEL() + 1);
    }

    public Creature evolveCreature(Creature creature1, Creature creature2, Inventory CInventory) {
        if (creature1.getEL() != 3 && creature1.getEL() == creature2.getEL() && creature1.getFamily().equals(creature2.getFamily())) {
            Creature evolvedCreature = getEvolvedCreature(creature1.getFamily(), creature1.getEL());
        
            if (evolvedCreature != null) {
                System.out.println("Evolution SUCCESS!");
        
                return evolvedCreature;
            } else {
                System.out.println("Evolution FAILED! No eligible creature found for evolution.");
                return null;
            }
        } else {
            System.out.println("Evolution FAILED! Selected creatures are not eligible for evolution.");
            return null;
        }
    }
    
    /**
     * Display information about a creature (name and EL)
     * 
     * @param creature - the evolved creature
     */
    private Creature getEvolvedCreature(String strFamily, int nEl) {
        List<Creature> aCreatures;
    
        switch (nEl + 1) {
            case 2:
                aCreatures = new ArrayList<>(PokemonRPG.getEl2Creatures());
                break;
            case 3:
                aCreatures = new ArrayList<>(PokemonRPG.getEl3Creatures());
                break;
            default:
                System.out.println("Invalid evolution level: " + (nEl + 1));
                return null; 
        }
    
        System.out.println("Contents of aCreatures: " + aCreatures);
    
        List<Creature> eligibleCreatures = new ArrayList<>();
        for (Creature creature : aCreatures) {
            if (creature.getFamily().equals(strFamily)) {
                eligibleCreatures.add(creature);
            }
        }
    
        System.out.println("Contents of eligible creatures: " + eligibleCreatures);
        if (!eligibleCreatures.isEmpty()) {
            // Choose a random creature from the filtered list
            Random random = new Random();
            int randomIndex = random.nextInt(eligibleCreatures.size());
            Creature randomCreature = eligibleCreatures.get(randomIndex);
    
            System.out.println("Chosen Creature: " + randomCreature.getName());
    
            return randomCreature;
        } else {
            return null;
        }
    }    
    
    /**
     * Display information about a creature (name and EL)
     * 
     * @param creature - the evolved creature
     */
    private void displayCreatureInfo(Creature creature) {
        System.out.println("[" + creature.getName() + ", EL: " + creature.getEL() + "]");
    }
}
