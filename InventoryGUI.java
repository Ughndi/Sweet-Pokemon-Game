import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class InventoryGUI {
    private JFrame inventoryFrame;
    private Inventory CInventory;
    
    private JLabel nameALabel;
    private JLabel typeALabel;
    private JLabel familyALabel;
    private JLabel elALabel;
    private JLabel imageActiveLabel; 

    private ImageIcon creatureActiveIcon;

    private String strImageActivePath;

    /**
     * InventoryGUI constructor
     * 
     * @param CInventory - inventory
     */
    public InventoryGUI(Inventory CInventory) {
        this.CInventory = CInventory;
        
        this.nameALabel = new JLabel("Name: " + CInventory.getActiveCreature().getName());
        this.typeALabel = new JLabel("Type: " + CInventory.getActiveCreature().getType());
        this.familyALabel = new JLabel("Family: " + CInventory.getActiveCreature().getFamily());
        this.elALabel = new JLabel("Evolution Level: " + CInventory.getActiveCreature().getEL());
        

        this.strImageActivePath = "creatures/" + CInventory.getActiveCreature().getName() + ".png";
        this.creatureActiveIcon = createResizedIcon(strImageActivePath, 100, 100);
        this.imageActiveLabel = new JLabel(creatureActiveIcon);

        initialize();
    }

    /**
     * A method that initializes the inventory frame
     */
    private void initialize() {
        inventoryFrame = new JFrame("Inventory");
        inventoryFrame.setBounds(100, 100, 850, 600);
        inventoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        inventoryFrame.getContentPane().setLayout(new BorderLayout());

        JScrollPane contentPanel = createInventoryPanel();
        inventoryFrame.getContentPane().add(contentPanel, BorderLayout.CENTER);

        inventoryFrame.setVisible(true);
    }

    /**
     * A method that initializes the inventory panel
     */
    private JScrollPane createInventoryPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
    
        ImageIcon inventoryTitleIcon = createResizedIcon("icons/InventoryTitle.png", 300, 100);
        JLabel inventoryTitleLabel = new JLabel(inventoryTitleIcon);
        panel.add(inventoryTitleLabel);
    
        JLabel activeLabel = new JLabel("ACTIVE CREATURE");
        activeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(activeLabel);
    
        JPanel activeCreaturePanel = createActivePanel(CInventory.getActiveCreature());
        panel.add(activeCreaturePanel);
    
        JLabel creatureLabel = new JLabel("ALL CREATURES");
        creatureLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(creatureLabel);
    
        for (Creature creature : CInventory.getInventory()) {
            JPanel creaturePanel = createCreaturePanel(creature);
            panel.add(creaturePanel);
        }

        JButton changeActiveButton = new JButton("Change Active Creature", createResizedIcon("icons/pokeball.png", 80, 80));
        changeActiveButton.setBackground(Color.WHITE);  
        if (CInventory.getInventory().size() > 1) {
            changeActiveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   changeActiveCreature();
                }
            });
        }    
        panel.add(changeActiveButton);
    
        JButton exitButton = new JButton("Exit Inventory", createResizedIcon("icons/back.png", 80, 80));
        exitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   inventoryFrame.dispose();
                }
            });
        exitButton.setBackground(Color.WHITE); 
        panel.add(exitButton);
        
        JScrollPane scrollPane = new JScrollPane(panel);
    
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        return scrollPane;
    }
    
    /**
     * A method that creates the active creature's panel
     * 
     * @param creature - active creature
     * @return panel
     */
    private JPanel createActivePanel(Creature creature) {
        JPanel panel = new JPanel(new BorderLayout());
    
        strImageActivePath = "creatures/" + creature.getName() + ".png";
        creatureActiveIcon = createResizedIcon(strImageActivePath, 100, 100);
        imageActiveLabel.setIcon(creatureActiveIcon);
        
        panel.add(imageActiveLabel, BorderLayout.WEST);
    
        JPanel textActivePanel = new JPanel(new GridLayout(4, 1));
        
        textActivePanel.add(nameALabel);
        textActivePanel.add(typeALabel);
        textActivePanel.add(familyALabel);
        textActivePanel.add(elALabel);
    
        panel.add(textActivePanel, BorderLayout.CENTER);
    
        return panel;
    }
    
    /**
     * A method that creates a creature's panel
     * 
     * @param creature - creature in the inventory
     * @return panel
     */
    private JPanel createCreaturePanel(Creature creature) {
        JPanel panel = new JPanel(new BorderLayout());
    
        String imagePath = "creatures/" + creature.getName() + ".png";
        ImageIcon creatureIcon = createResizedIcon(imagePath, 100, 100);
        JLabel imageLabel = new JLabel(creatureIcon);
        panel.add(imageLabel, BorderLayout.WEST);
    
        JPanel textPanel = new JPanel(new GridLayout(4, 1));
    
        JLabel nameLabel = new JLabel("Name: " + creature.getName());
        textPanel.add(nameLabel);
    
        JLabel typeLabel = new JLabel("Type: " + creature.getType());
        textPanel.add(typeLabel);
    
        JLabel familyLabel = new JLabel("Family: " + creature.getFamily());
        textPanel.add(familyLabel);
    
        JLabel elLabel = new JLabel("Evolution Level: " + creature.getEL());
        textPanel.add(elLabel);
    
        panel.add(textPanel, BorderLayout.CENTER);
    
        return panel;
    }
    
    /**
     * A method handles the changing of the active creature
     */
    private void changeActiveCreature() {
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
     * A method handles the changing of the active creature
     * 
     * @param selectedCreature - creature to be set as active creature
     */
    private void setActiveUpdateUI(Creature selectedCreature) {
        CInventory.setActiveCreature(selectedCreature);
    
        nameALabel.setText("Name: " + CInventory.getActiveCreature().getName());
        familyALabel.setText("Family: " + CInventory.getActiveCreature().getFamily());
        typeALabel.setText("Type: " + CInventory.getActiveCreature().getType());
        elALabel.setText("Evolution Level: " + CInventory.getActiveCreature().getEL());
    
        String newImagePath = "creatures/" + CInventory.getActiveCreature().getName() + ".png";
        ImageIcon newCreatureIcon = createResizedIcon(newImagePath, 100, 100);        
        imageActiveLabel.setIcon(newCreatureIcon);
    }
    
    /**
     * A method that creates the creature buttons
     * 
     * @param creature - creature in the inventory
     * @param inventoryFrame - inventory frame
     */
    private JButton createCreatureButton(Creature creature, JFrame inventoryFrame) {
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
                inventoryFrame.dispose();
            }
        });
        return button;
    }
    
    /**
     * A method that creates the creature buttons
     * 
     * @param strPath - image path
     * @param nWidth - width of the icon
     * @param nHeight - height of the icon
     */
    private ImageIcon createResizedIcon(String strPath, int nWidth, int nHeight) {
        ImageIcon originalIcon = new ImageIcon(strPath);
        Image image = originalIcon.getImage().getScaledInstance(nWidth, nHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }
}
