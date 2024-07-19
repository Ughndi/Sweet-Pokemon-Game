import java.util.ArrayList;
import java.util.List;

public class EL2Creature extends Creature{
    /**
     * EL2Creature constructor 
     * 
     * @param strName - name of the creature
     * @param strType - type of the creature
     * @param strFamily - family of the creature
     */
    public EL2Creature(String strName, String strType, String strFamily) {
        super(strName, strType, strFamily, 2);
    }

    /**
     * A method that initializes evolution level 2 creatures
     * 
     * @return el1Creatures - evolution level 2 creatures
     */
    public static List<EL2Creature> initializeEL2Creatures() {
        List<EL2Creature> el2Creatures = new ArrayList<>();
        
        el2Creatures.add(new EL2Creature("STRAWLEON", "FIRE", "A"));
        el2Creatures.add(new EL2Creature("CHOCOFLUFF", "FIRE", "B"));
        el2Creatures.add(new EL2Creature("PARFURE", "FIRE", "C"));
        el2Creatures.add(new EL2Creature("CHOCOSAUR", "PLANT", "D"));
        el2Creatures.add(new EL2Creature("GOLBERRY", "PLANT", "E"));
        el2Creatures.add(new EL2Creature("KIRLICAKE", "PLANT", "F"));
        el2Creatures.add(new EL2Creature("TARTORTLE", "WATER", "G"));
        el2Creatures.add(new EL2Creature("CHOCOLISH", "WATER", "H"));
        el2Creatures.add(new EL2Creature("DEWICE", "WATER", "I"));

        return el2Creatures;
    }
}
