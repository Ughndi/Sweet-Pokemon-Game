import java.util.ArrayList;
import java.util.List;

public class EL3Creature extends Creature{
    /**
     * EL3Creature constructor 
     * 
     * @param strName - name of the creature
     * @param strType - type of the creature
     * @param strFamily - family of the creature
     */
    public EL3Creature(String strName, String strType, String strFamily) {
        super(strName, strType, strFamily, 3);
    }

    /**
     * A method that initializes evolution level 3 creatures
     * 
     * @return el1Creatures - evolution level 3 creatures
     */
    public static List<EL3Creature> initializeEL3Creatures() {
        List<EL3Creature> el3Creatures = new ArrayList<>();
        
        el3Creatures.add(new EL3Creature("STRAWIZARD", "FIRE", "A"));
        el3Creatures.add(new EL3Creature("CANDAROS", "FIRE", "B"));
        el3Creatures.add(new EL3Creature("PARFELURE", "FIRE", "C"));
        el3Creatures.add(new EL3Creature("FUDGASAUR", "PLANT", "D"));
        el3Creatures.add(new EL3Creature("CROBERRY", "PLANT", "E"));
        el3Creatures.add(new EL3Creature("VELVEVOIR", "PLANT", "F"));
        el3Creatures.add(new EL3Creature("PIESTOISE", "WATER", "G"));
        el3Creatures.add(new EL3Creature("ICESUNDAE", "WATER", "H"));
        el3Creatures.add(new EL3Creature("SAMURCONE", "WATER", "I"));

        return el3Creatures;
    }
}
