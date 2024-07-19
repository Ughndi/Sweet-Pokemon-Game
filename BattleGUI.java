import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BattleGUI {
    private JFrame battleFrame;

    private JLabel label;
    private JLabel actionsLeftLabel;
    private JLabel enemyHealthLabel;
    private JLabel activeCreatureInfoLabel;
    private JLabel activeImageLabel;

    private JButton attackButton;
    private JButton swapButton;
    private JButton catchButton;

    private ImageIcon activeCreatureIcon;

    private Creature activeCreature;
    private Creature enemyCreature;

    private int nEnemyHealth;
    private int nActionsLeft;

    private Battle CBattle;
    private Inventory CInventory;

    /**
     * BattleGUI constructor
     * 
     * @param activeCreature - active creature
     * @param enemyCreature - enemy creature
     * @param CInventory - inventory
     * @param CBattle - battle
     */
    public BattleGUI(Creature activeCreature, Creature enemyCreature, Inventory CInventory, Battle CBattle) {
        this.activeCreature = activeCreature;
        this.enemyCreature = enemyCreature;
        this.CInventory = CInventory;
        this.CBattle = CBattle;

        this.nEnemyHealth = CBattle.getEnemyHealth(); 
        this.nActionsLeft = CBattle.getActionsLeft(); 

        this.actionsLeftLabel = new JLabel("Actions Left: " + nActionsLeft);
        this.enemyHealthLabel = new JLabel("Enemy Health: " + nEnemyHealth);

        this.label = new JLabel("<html>What will<br>" + activeCreature.getName() + "<br>do?</html>");
        this.activeCreatureInfoLabel = new JLabel("<html>Name: " + activeCreature.getName() + "<br>Evolution Level: " + activeCreature.getEL() + "<br>Type: " + activeCreature.getType() + "</html>");
        this.activeCreatureIcon = createResizedIcon("creatureback/" + activeCreature.getName() + "back.png", 150, 150);
        this.activeImageLabel = new JLabel(activeCreatureIcon);
        this.attackButton = new JButton(createResizedIcon("icons/attack.png", 50, 50));
        this.swapButton = new JButton(createResizedIcon("icons/swap.png", 50, 50));
        this.catchButton = new JButton(createResizedIcon("icons/pokeball.png", 50, 50));

        initialize();
    }

    /**
     * A method that initializes the Battle Frame
     */
    private void initialize() {
        battleFrame = new JFrame("Battle");
        Color backgroundColor = Color.decode("#c1ffb3"); 
        battleFrame.getContentPane().setBackground(backgroundColor);

        JPanel topPanel = new JPanel(new GridLayout(0,2));
        JPanel centerPanel = new JPanel(new GridLayout(2, 4));
            
        JLabel enemyInfoLabel = new JLabel("<html>Name: " + enemyCreature.getName() + "<br>Evolution Level: " + enemyCreature.getEL() + "<br>Type: " + enemyCreature.getType() + "</html>");
        JLabel blankLabelTop = new JLabel();
        JLabel blankLabelBottom = new JLabel();
    
        JLabel whiteLabelTop = new JLabel("");
        whiteLabelTop.setOpaque(true);
        whiteLabelTop.setBackground(Color.WHITE);
        
        JLabel whiteLabelBottom = new JLabel("");
        whiteLabelBottom.setOpaque(true);
        whiteLabelBottom.setBackground(Color.WHITE);

        JPanel imagePanel = new JPanel(new GridLayout(1, 1));
        ImageIcon enemyIcon = createResizedIcon("creatures/" + enemyCreature.getName() + ".png", 150, 150);
        JLabel enemyImageLabel = new JLabel(enemyIcon);
        imagePanel.add(enemyImageLabel);
        
        JPanel imagePanelBottom = new JPanel(new GridLayout(1, 1));
        
        imagePanelBottom.add(activeImageLabel);
    
        topPanel.add(actionsLeftLabel);
        topPanel.add(enemyHealthLabel);
    
        centerPanel.add(blankLabelTop);
        centerPanel.add(whiteLabelTop);
        centerPanel.add(imagePanel);
        centerPanel.add(enemyInfoLabel);
        centerPanel.add(activeCreatureInfoLabel);
        centerPanel.add(imagePanelBottom);
        centerPanel.add(whiteLabelBottom);
        centerPanel.add(blankLabelBottom);
    
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 5));
        createButtons(buttonsPanel);
    
        battleFrame.add(topPanel, BorderLayout.NORTH);
        battleFrame.add(centerPanel, BorderLayout.CENTER);
        battleFrame.add(buttonsPanel, BorderLayout.SOUTH);
        
        battleFrame.setSize(575, 380);
        battleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        battleFrame.setLocationRelativeTo(null); 
        battleFrame.setVisible(true);
    }

    /**
     * A method that creates buttons for the Battle frame
     */
    private void createButtons(JPanel panel) {
        attackButton.setBackground(Color.WHITE);
        attackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               performAttack();
            }
        });
        
        swapButton.setBackground(Color.WHITE);
        if (CInventory.getInventory().size() > 1){
            swapButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   performSwap();
                }
            });
        }
        
        catchButton.setBackground(Color.WHITE);
        catchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               performCatch(CInventory);
            }
        });

        JButton exitButton = new JButton(createResizedIcon("icons/exit.png", 50, 50));
        exitButton.setBackground(Color.WHITE);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              battleFrame.dispose();
            }
        });
    
        Dimension buttonSize = new Dimension(50, 50);
        attackButton.setPreferredSize(buttonSize);
        swapButton.setPreferredSize(buttonSize);
        catchButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);
    
        panel.add(label);
        panel.add(attackButton);
        panel.add(swapButton);
        panel.add(catchButton);
        panel.add(exitButton);
    }

    /**
     * A method that gets the damage to be dealt
     * to the enemy creature
     */
    private void performAttack() {
        int nDamage = CBattle.attack();
        String attackResult = "<html>Your creature dealt<br>" + nDamage + " damage!</html>";
        
        if (CBattle.getEnemyHealth() <= 0) {
            attackResult += "<html><br>Enemy creature<br>is defeated!</html>";
            disableButtons();
        }
    
        label.setText(attackResult);
        enemyHealthLabel.setText("Enemy Health: " + CBattle.getEnemyHealth());
        actionsLeftLabel.setText("Actions Left: " + CBattle.getActionsLeft());
    
        checkBattleStatus();
    }

    /**
     * A method that checks if the player's catch
     * rate is successful
     */
    private void performCatch(Inventory CInventory) {
        boolean catchSuccess = CBattle.catchCreature(CInventory);
        if (catchSuccess) {
            disableButtons();
            String catchResult = "<html>You caught the<br>" + enemyCreature.getName() + "!</html>";
            label.setText(catchResult);
            enemyHealthLabel.setText("Enemy Health: " + CBattle.getEnemyHealth());
            actionsLeftLabel.setText("Actions Left: " + CBattle.getActionsLeft());
    
            Timer timer = new Timer(2000, e -> battleFrame.dispose());
            timer.setRepeats(false);
            timer.start();

        } else {
            String strCatchResult = "<html>The creature<br>escaped your<br>attempt to catch!</html>";
            label.setText(strCatchResult);
            enemyHealthLabel.setText("Enemy Health: " + CBattle.getEnemyHealth());
            actionsLeftLabel.setText("Actions Left: " + CBattle.getActionsLeft());
            checkBattleStatus();
        }
    }
    
    /**
     * A method that makes the selected creature
     * the new active creature
     */
    private void performSwap() {
        JFrame changeActiveFrame = new JFrame("Change Active Creature");
        changeActiveFrame.setSize(400, 300);
        changeActiveFrame.setLayout(new GridLayout(0, 1));
        
        JLabel titleLabel = new JLabel("Select new Active Creature");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        changeActiveFrame.add(titleLabel, BorderLayout.NORTH);
        
        JPanel changeActivePanel = new JPanel();
        changeActivePanel.setLayout(new GridLayout(0, 1));
    
        for (Creature creature : CInventory.getInventory()) {
            JButton creatureButton = createCreatureButton(creature, changeActiveFrame);
            changeActivePanel.add(creatureButton);
        }
    
        JScrollPane scrollPane = new JScrollPane(changeActivePanel);
        changeActiveFrame.add(scrollPane, BorderLayout.CENTER);
    
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    
        changeActiveFrame.setVisible(true);
    }    

    /**
     * A method that sets the selected Creature as 
     * the active creature in the inventory and updates
     * the interface
     */
    private void setActiveUpdateUI(Creature selectedCreature) {
        CBattle.swap(selectedCreature);

        CInventory.setActiveCreature(selectedCreature);
        actionsLeftLabel.setText("Actions Left: " + CBattle.getActionsLeft());

        activeCreatureInfoLabel.setText("<html>Name: " + CInventory.getActiveCreature().getName() + "<br>Evolution Level: " + CInventory.getActiveCreature().getEL() + "<br>Type: " + CInventory.getActiveCreature().getType() + "</html>");
        ImageIcon newCreatureIcon = createResizedIcon("creatureback/" + CInventory.getActiveCreature().getName() + "back.png", 150, 150);
        activeImageLabel.setIcon(newCreatureIcon);
        checkBattleStatus();
    }   
    
    /**
     * A method that creates the creature buttons
     */
    private JButton createCreatureButton(Creature creature, JFrame frame) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());
    
        ImageIcon creatureIcon = createResizedIcon("creatures/" + creature.getName() + ".png", 100, 100);
        JLabel imageLabel = new JLabel(creatureIcon);
        button.add(imageLabel, BorderLayout.NORTH);
    
        JLabel nameLabel = new JLabel(creature.getName());
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        button.add(nameLabel, BorderLayout.CENTER);
        button.setBackground(Color.WHITE);
    
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setActiveUpdateUI(creature);
                frame.dispose();
            }
        });
    
        return button;
    }

    /**
     * A method that checks if the enemyHealth is 0 
     * and/or the actions left is 0 and ends the battle phase
     */
    private void checkBattleStatus() {
        if (CBattle.getEnemyHealth() <= 0) {
            String defeatMessage = "<html>Enemy creature<br>is defeated!</html>";
            label.setText(defeatMessage);
            enemyHealthLabel.setText("Enemy Health: 0");
    
            Timer timer = new Timer(2000, e -> battleFrame.dispose());
            timer.setRepeats(false);
            timer.start();
        } else if (CBattle.getActionsLeft() == 0) {
            String runAwayMessage = "<html>The enemy<br>ran away!</html>";
            label.setText(runAwayMessage);
            disableButtons();
    
            Timer timer = new Timer(2000, e -> battleFrame.dispose());
            timer.setRepeats(false);
            timer.start();
        }
    }
    
    /**
     * A method disables the buttons
     */
    private void disableButtons(){
        attackButton.setEnabled(false);
        swapButton.setEnabled(false);
        catchButton.setEnabled(false);
    }

    /**
     * A method that makes the battleFrame visible
     * 
     * @param visible - frame visibility
     */
    public void setVisible(boolean visible) {
        battleFrame.setVisible(visible);
    }

    /**
     * A method creates resized icons
     * 
     * @param strPath - image path
     * @param nWidth - width of image
     * @param nHeight - height of image
    */
    private ImageIcon createResizedIcon(String path, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(path);
        Image image = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }
}
