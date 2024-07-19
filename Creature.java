import java.util.Random;

public class Creature {
    private String strName;
    private String strType;
    private String strFamily;
    private int nEL;
    private static Random CRandom = new Random();

    /**
     * Creature constructor 
     * 
     * @param strName - name of the creature
     * @param strType - type of the creature
     * @param strFamily - family of the creature
     * @param nEL - evolution level of the creature
     */
    public Creature(String strName, String strType, String strFamily, int nEL) {
        this.strName = strName;
        this.strType = strType;
        this.strFamily = strFamily;
        this.nEL = nEL;
    }

    /**
     * A method that calculates the damage to be dealt to
     * the enemy creature
     * 
     * @param enemy - enemy creature
     * @return nDamage - amount of damage
     */
    public int attack(Creature enemy) {
        int nDamage = CRandom.nextInt(10) + 1;
        nDamage *= nEL;

        if ((this.strType.equals("FIRE") && enemy.strType.equals("PLANT")) ||
                (this.strType.equals("PLANT") && enemy.strType.equals("WATER")) ||
                (this.strType.equals("WATER") && enemy.strType.equals("FIRE"))) {
            nDamage *= 1.5;
        }

        return nDamage;
    }

    /**
     * A setter for the creature's name
     * 
     * @param strName - name of the creature
     */
    public void setName(String strName) {
        this.strName = strName; 
    }

    /**
     * A setter for the creature's type
     * 
     * @param strType - type of the creature
     */
    public void setType(String strType) {
        this.strType = strType;
    }

    /**
     * A setter for the creature's family
     * 
     * @param strFamily - family of the creature
     */
    public void setFamily(String strFamily) {
        this.strFamily = strFamily;
    }

    /**
     * A setter for the creature's evolution level
     * 
     * @param nEL - Evolution Level of the creature
     */
    public void setEL(int nEL) {
        this.nEL = nEL;
    }
    
    /**
     * A getter for the creature's name
     * 
     * @return strName - name of the creature
     */
    public String getName() { 
        return strName; 
    }

    /** 
     * A getter for the creature's type
     * 
     * @return strType - type of the creature
     */
    public String getType() { 
        return strType; 
    }

    /**
     * A getter for the creature's family
     * 
     * @return strFamily - family of the creature
     */
    public String getFamily() { 
        return strFamily;
    }

    /**
     * A getter for the creature's evolution level
     * 
     * @return nEL - Evolution Level of the creature
     */
    public int getEL() { 
        return nEL; 
    }
}

