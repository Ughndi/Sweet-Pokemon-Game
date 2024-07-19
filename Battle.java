import java.util.Random;

public class Battle {
    private Inventory CInventory;
    private Random CRandom;
    private int nActionsLeft = 3;
    private int nEnemyHealth = 50;

    private Creature activeCreature;
    private Creature creature;
    private Creature enemy;

    /**
     * Battle constructor
     * 
     * @param activeCreature - activeCreature in the inventory
     * @param enemy - enemy creature
     * @param CInventory - inventory containing all creatures
     */
    public Battle(Creature activeCreature, Creature enemy, Inventory CInventory) {
        this.activeCreature = activeCreature;
        this.enemy = enemy;
        this.CInventory = CInventory;
        this.CRandom = new Random();
    }

    /**
     * A method that starts the battle
     */
    public void start() {
        PokemonRPG.clearScreen(); 
        
        while (nActionsLeft > 0 && nEnemyHealth > 0) {
            System.out.println("\nBATTLE PHASE:");
            System.out.println("Your active creature: " + activeCreature.getName());
            System.out.println("Enemy creature: " + enemy.getName() + " with " + nEnemyHealth + " HP");
            System.out.println("Actions left: " + nActionsLeft);
            System.out.println("[1] ATTACK");
            System.out.println("[2] SWAP");
            System.out.println("[3] CATCH");
            System.out.println("[4] RUN AWAY");
            System.out.print("Choose an action: ");

            int action = PokemonRPG.getScanner().nextInt();
            PokemonRPG.getScanner().nextLine();

            switch (action) {
                case 1:
                    PokemonRPG.clearScreen();
                    attack();
                    break;
                case 2:
                    PokemonRPG.clearScreen();
                    swap(creature);
                    break;
                case 3:
                    if (catchCreature(CInventory)) {
                        PokemonRPG.getScanner().nextLine();
                        return;
                    }
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid action!");
                    PokemonRPG.getScanner().nextLine();
                    PokemonRPG.clearScreen();
                    continue;
            }
            nActionsLeft--;
            if (nActionsLeft == 0) {
                System.out.println("\nThe enemy ran away!");
                PokemonRPG.getScanner().nextLine();
            }
        }
    }

    /**
     * A method that attacks the enemy creature including how
     * much damage is dealt and if the enemy is defeated
     * 
     * @return nDamage - damage dealt by active creature
     */
    protected int attack() {
        int nDamage = activeCreature.attack(enemy);
        nEnemyHealth -= nDamage;
        nActionsLeft--;
        System.out.println("\nYour creature dealt " + nDamage + " damage!");

        if (nEnemyHealth <= 0) {
            System.out.println("Enemy creature is defeated!");
            nEnemyHealth = 0;
        }

        return nDamage;
    }

    /**
     * A method that checks if the creature to be swapped
     * is the same as the current active creature. If
     * it's the same it won't decrement the actionsLeft 
     * 
     * @param creature - selected creature to swap with
     */
    protected void swap(Creature creature) {
        if (creature == this.activeCreature){
            CInventory.setActiveCreature(creature);
        }
        else{
            nActionsLeft--;
            CInventory.setActiveCreature(creature);
        }
    }    

    /**
     * A method that retrieves the 
     * enemyHealth
     * 
     * @return nEnemyHealth - enemy's health
     */
    protected int getEnemyHealth(){
        return nEnemyHealth;
    }

    /**
     * A method that retrieves the 
     * number of actions left
     * 
     * @return nActionsLeft - the user's number of actions
     */
    protected int getActionsLeft(){
        return nActionsLeft;
    }

    /**
     * A method that catches the enemy creature depending
     * on the calculated catch rate
     * 
     * @return true if player caught enemy
     */
    protected boolean catchCreature(Inventory CInventory) {
        int catchRate = 40 + 50 - nEnemyHealth;
        nActionsLeft--;
        if (CRandom.nextInt(100) < catchRate) {
            CInventory.addCreature(enemy);
            System.out.println("\nYou caught the " + enemy.getName() + "!");
            return true;
        } else {
            System.out.println("\nThe creature escaped your attempt to catch!");
            return false;
        }
    }

}
