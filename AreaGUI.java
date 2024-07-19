import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AreaGUI {
    private JFrame areaFrame;
    private JPanel areaPanel;

    private Inventory CInventory;
    private Random CRandom;
    private Area CArea;

    private int playerX; 
    private int playerY;
    
    /**
     * AreaGUI constructor
     * 
     * @param CInventory - user's inventory containing creatures
     * @param CArea - area
     */
    public AreaGUI(Inventory CInventory, Area CArea) {
        this.CInventory = CInventory;
        this.CArea = CArea;
        this.CRandom = new Random();
        initialize();
    }
    
    /**
     * A method that initializes the Area Menu
     */
    private void initialize() {
        areaFrame = new JFrame("Explore Areas");
        areaFrame.setBounds(100, 100, 850, 600);
        areaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        areaPanel = new JPanel(new GridLayout(6, 1));

        ImageIcon areaTitleIcon = createResizedIcon("icons/ExploreTitle.png", 300, 80);
        JLabel areaTitleLabel = new JLabel(areaTitleIcon);
        areaPanel.add(areaTitleLabel);

        JLabel mainMenuLabel = new JLabel("Area Menu");
        mainMenuLabel.setHorizontalAlignment(SwingConstants.CENTER);
        areaPanel.add(mainMenuLabel);

        JButton exploreArea1Button = createMenuButton("Explore Area 1", "icons/area1.png");
        exploreArea1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               exploreArea(CArea.getCreaturesInArea(1), 5, 1, 1);
            }
        });
        areaPanel.add(exploreArea1Button);

        JButton exploreArea2Button = createMenuButton("Explore Area 2", "icons/area2.png");
        exploreArea2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               exploreArea(CArea.getCreaturesInArea(2), 3, 3, 2);
            }
        });
        areaPanel.add(exploreArea2Button);

        JButton exploreArea3Button = createMenuButton("Explore Area 3", "icons/area3.png");
        exploreArea3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               exploreArea(CArea.getCreaturesInArea(2), 3, 3, 2);
            }
        });
        areaPanel.add(exploreArea3Button);

        JButton exitButton = createMenuButton("Exit Explore Areas", "icons/back.png");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               areaFrame.dispose();
            }
        });
        areaPanel.add(exitButton);

        areaFrame.getContentPane().add(areaPanel);
        areaFrame.setVisible(true);
    }

    /**
     * A method that initializes the selected area
     * 
     * @param aCreaturesInArea - creatures found in an area
     * @param gridWith - width of the area grid
     * @param gridHeight - height of the area grid
     * @param areaNumber - number of the selected area
     */
    private void exploreArea(List<Creature> aCreaturesInArea, int gridWidth, int gridHeight, int areaNumber) {
        JFrame explorationFrame = new JFrame("Explore Area " + areaNumber);
        explorationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
        playerX = 0;
        playerY = 0;
    
        JPanel tilePanel = new JPanel(new GridLayout(gridHeight, gridWidth));
        JButton[][] tileButtons = new JButton[gridHeight][gridWidth];

        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                tileButtons[y][x] = new JButton();
                tilePanel.add(tileButtons[y][x]);
            }
        }
    
        ImageIcon trainerIcon = createResizedIcon("icons/trainer.png", 80, 80);
        tileButtons[0][0].setIcon(trainerIcon);
    
        JPanel movementButtonPanel = new JPanel(new GridLayout(2, 5));
        movementButtonPanel.add(new JLabel()); 
        movementButtonPanel.add(new JLabel()); 
        movementButtonPanel.add(new JLabel()); 
        movementButtonPanel.add(createDirectionButton("Up", tileButtons, areaNumber));
        movementButtonPanel.add(new JLabel()); 
        movementButtonPanel.add(new JLabel()); 
        movementButtonPanel.add(new JLabel()); 
        movementButtonPanel.add(new JLabel()); 
        movementButtonPanel.add(new JLabel()); 
        movementButtonPanel.add(createDirectionButton("Left", tileButtons, areaNumber));
        movementButtonPanel.add(createDirectionButton("Down", tileButtons, areaNumber));
        movementButtonPanel.add(createDirectionButton("Right", tileButtons, areaNumber));
        movementButtonPanel.add(new JLabel()); 
        movementButtonPanel.add(new JLabel()); 
    
        JPanel exitButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton exitButton = createMenuButton("Exit Explore Area", "icons/back.png");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               explorationFrame.dispose();
            }
        });
        exitButtonPanel.add(exitButton);
    
        explorationFrame.add(tilePanel, BorderLayout.CENTER);
        explorationFrame.add(movementButtonPanel, BorderLayout.SOUTH);
        explorationFrame.add(exitButtonPanel, BorderLayout.EAST);
    
        if (areaNumber == 1){
            explorationFrame.setSize(800, 250);
        }
        else if (areaNumber == 2){
            explorationFrame.setSize(600, 500);
        }
        else{
            explorationFrame.setSize(700, 600);
        }
        
        explorationFrame.setLocationRelativeTo(null);
        explorationFrame.setVisible(true);
    }
    
    /**
     * A method that creates direction buttons for
     * exploring an area
     * 
     * @param strDirection - name of the direction
     * @param tileButtons - grid of the area
     * @param nAreaNumber - number of the selected area
     */
    private JButton createDirectionButton(String strDirection, JButton[][] tileButtons, int nAreaNumber) {
        JButton button = new JButton();
    
        ImageIcon icon = createResizedIcon("icons/" + strDirection + ".png", 50, 50);

        button.setIcon(icon);
        button.setBackground(Color.WHITE);
        button.setPreferredSize(new Dimension(50, 50));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePlayer(strDirection, tileButtons, nAreaNumber);
            }
        });
        
        return button;
    }

    /**
     * A method that checks whether or not
     * the player will encounter an enemy creature
     * 
     * @param nAreaNumber = number of the selected area
     */
    private void checkEncounter(int nAreaNumber) {
        if (CArea.encounterCreature() == true) {
            System.out.println(CArea.encounterCreature());
            List<Creature> aCreaturesinArea = CArea.getCreaturesInArea(nAreaNumber);

            Creature encounteredCreature = aCreaturesinArea.get(CRandom.nextInt(aCreaturesinArea.size()));

            Battle battle = new Battle(CInventory.getActiveCreature(), encounteredCreature, CInventory);
            BattleGUI battleGUI = new BattleGUI(CInventory.getActiveCreature(), encounteredCreature, CInventory, battle);
            battleGUI.setVisible(true); 
        }
    }

    /**
     * A method that implements the movement of the 
     * player and reflects it in the interface. 
     * 
     * @param strDirection - name of the direction
     * @param tileButtons - grid of the area
     * @param nAreaNumber - number of the selected area
     */
    private void movePlayer(String strDirection, JButton[][] tileButtons, int nAreaNumber) {
        switch (strDirection.toUpperCase()) {
            case "UP":
                if (playerY - 1 >= 0) {
                    playerY--;
                    updateTileButtons(tileButtons);
                    checkEncounter(nAreaNumber);
                }
                break;
            case "DOWN":
                if (playerY + 1 < tileButtons.length) {
                    playerY++;
                    updateTileButtons(tileButtons);
                    checkEncounter(nAreaNumber);
                }
                break;
            case "LEFT":
                if (playerX - 1 >= 0) {
                    playerX--;
                    updateTileButtons(tileButtons);
                    checkEncounter(nAreaNumber);
                }
                break;
            case "RIGHT":
                if (playerX + 1 < tileButtons[0].length) {
                    playerX++;
                    updateTileButtons(tileButtons);
                    checkEncounter(nAreaNumber);
                }
                break;
        }
    }

    /**
     * A method that updates the player icon
     * to mark their position
     * 
     * @param tileButtons - grid of the area
     */
    private void updateTileButtons(JButton[][] tileButtons) {
        for (JButton[] row : tileButtons) {
            for (JButton button : row) {
                button.setText("");
                button.setIcon(null);
            }
        }

        tileButtons[playerY][playerX].setIcon(createResizedIcon("icons/trainer.png", 80, 80));
    }

    /**
     * A method that creates the buttons for the
     * area menu
     * 
     * @param strButtonText - name of the button
     * @param strImagePath - image path
     */
    private JButton createMenuButton(String strButtonText, String strImagePath) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());

        if (strButtonText == "Exit Explore Area"){
            ImageIcon icon = createResizedIcon(strImagePath, 50, 50);
            JLabel imageLabel = new JLabel(icon);
            button.add(imageLabel, BorderLayout.WEST);
        }
        else{
            ImageIcon icon = createResizedIcon(strImagePath, 100, 100);
            JLabel imageLabel = new JLabel(icon);
            button.add(imageLabel, BorderLayout.WEST);
        }
        
        JLabel textLabel = new JLabel(strButtonText);
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        button.add(textLabel, BorderLayout.CENTER);
        button.setBackground(Color.WHITE);
        return button;
    }

    /**
     * A method creates resized icons
     * 
     * @param strPath - image path
     * @param nWidth - width of image
     * @param nHeight - height of image
    */
    private ImageIcon createResizedIcon(String strPath, int nWidth, int nHeight) {
        ImageIcon originalIcon = new ImageIcon(strPath);
        Image image = originalIcon.getImage().getScaledInstance(nWidth, nHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }
}
