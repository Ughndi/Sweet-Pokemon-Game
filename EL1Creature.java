import java.util.ArrayList;
import java.util.List;

public class EL1Creature extends Creature{
    
    /**
     * EL1Creature constructor 
     * 
     * @param strName - name of the creature
     * @param strType - type of the creature
     * @param strFamily - family of the creature
     */
    public EL1Creature(String strName, String strType, String strFamily) {
        super(strName, strType, strFamily, 1);
    }

    /**
     * A method that initializes evolution level 1 creatures
     * 
     * @return el1Creatures - evolution level 1 creatures
     */
    public static List<EL1Creature> initializeEL1Creatures() {
        List<EL1Creature> el1Creatures = new ArrayList<>();
        
        // Initialize EL1 creatures
        el1Creatures.add(new EL1Creature("STRAWANDER", "FIRE", "A"));
        el1Creatures.add(new EL1Creature("CHOCOWOOL", "FIRE", "B"));
        el1Creatures.add(new EL1Creature("PARFWIT", "FIRE", "C"));
        el1Creatures.add(new EL1Creature("BROWNISAUR", "PLANT", "D"));
        el1Creatures.add(new EL1Creature("FRUBAT", "PLANT", "E"));
        el1Creatures.add(new EL1Creature("MALTS", "PLANT", "F"));
        el1Creatures.add(new EL1Creature("SQUIRPIE", "WATER", "G"));
        el1Creatures.add(new EL1Creature("CHOCOLITE", "WATER", "H"));
        el1Creatures.add(new EL1Creature("OSHACONE", "WATER", "I"));

        return el1Creatures;
    }
}
