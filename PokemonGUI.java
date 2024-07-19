import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class PokemonGUI {

    private JFrame pokemonFrame;
    private PokemonRPG CPokemonRPG;
    private InventoryGUI inventoryGUI;
    private Area CArea;
    private Inventory CInventory;
    private Evolution CEvolution;

    /**
     * PokemonGUI constructor
     * 
     * @param CPokemonRPG - inventory
     */
    public PokemonGUI(PokemonRPG CPokemonRPG) {
        this.CPokemonRPG = CPokemonRPG;
        this.CEvolution = CPokemonRPG.getEvolution();
        this.CInventory = CPokemonRPG.getInventory();
        this.CArea = CPokemonRPG.getArea();
        initialize();
    }

    /**
     * A method that initializes the pokemon frame
     */
    private void initialize() {
        pokemonFrame = new JFrame("Pokemon RPG");
        pokemonFrame.setBounds(100, 100, 850, 600);
        pokemonFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pokemonFrame.getContentPane().setLayout(new BorderLayout());

        JPanel startPanel = createStartPanel();
        pokemonFrame.getContentPane().add(startPanel, BorderLayout.CENTER);

        pokemonFrame.setVisible(true);
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
    
    /**
     * A method that creates the start panel
     * 
     * @return panel
     */
    private JPanel createStartPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        ImageIcon logoIcon = createResizedIcon("icons/PokemonLogo.png", 400, 120);
        JLabel logoLabel = new JLabel(logoIcon);
        panel.add(logoLabel, BorderLayout.NORTH);
    
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
    
        JPanel contentPanel = new JPanel(new BorderLayout());
    
        JLabel titleLabel = new JLabel("Select Your Starter Pokemon");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(titleLabel, BorderLayout.NORTH);
    
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
    
        List<EL1Creature> aEl1Creatures = PokemonRPG.getEl1Creatures();
        JPanel buttonsPanel = new JPanel(new GridLayout(0, 3)); 
    
        for (EL1Creature creature : aEl1Creatures) {
            JButton button = createCreatureButton(creature);
            buttonsPanel.add(button);
        }
    
        contentPanel.add(buttonsPanel, BorderLayout.SOUTH);
        panel.add(contentPanel, BorderLayout.CENTER);
    
        return panel;
    }    
    
    /**
     * A method that creates the start panel
     * 
     * @param creature - evolution level 1 creature
     */
    private JButton createCreatureButton(EL1Creature creature) {
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
                CPokemonRPG.selectStarter(creature, CPokemonRPG.getInventory());
                pokemonFrame.dispose();
                createMainMenu();
            }
        });

        return button;
    }

    /**
     * A method that creates the main menu
     */
    private void createMainMenu() {
        JFrame mainMenuFrame = new JFrame("Main Menu");
        mainMenuFrame.setBounds(100, 100, 850, 600);
        mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        JPanel mainMenuPanel = new JPanel(new GridLayout(0, 1));
    
        ImageIcon logoIcon = createResizedIcon("icons/PokemonLogo.png", 400, 120);
        JLabel logoLabel = new JLabel(logoIcon);
        mainMenuPanel.add(logoLabel);
    
        JLabel titleLabel = new JLabel("MAIN MENU");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainMenuPanel.add(titleLabel);
    
        JButton viewInventoryButton = createMenuButton("View Inventory", "icons/inventory.png");
        mainMenuPanel.add(viewInventoryButton);
    
        JButton exploreAreasButton = createMenuButton("Explore Areas", "icons/explore.png");
        mainMenuPanel.add(exploreAreasButton);
    
        JButton evolveButton = createMenuButton("Evolve a Pokemon", "icons/evolve.png");
        mainMenuPanel.add(evolveButton);
    
        JButton exitButton = createMenuButton("Exit", "icons/exit.png");
        mainMenuPanel.add(exitButton);
    
        mainMenuFrame.getContentPane().add(mainMenuPanel);
        mainMenuFrame.setVisible(true);
    }
    
    /**
     * A method that creates the main menu buttons
     * 
     * @return button
     */
    private JButton createMenuButton(String buttonText, String imagePath) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());
    
        ImageIcon icon = createResizedIcon(imagePath, 100, 100);
        JLabel imageLabel = new JLabel(icon);
        button.add(imageLabel, BorderLayout.WEST);
    
        JLabel textLabel = new JLabel(buttonText);
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        button.add(textLabel, BorderLayout.CENTER);
        button.setBackground(Color.WHITE);
    
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonClick(buttonText);
            }
        });
    
        return button;
    }
    
    /**
     * A method that opens the inventory GUI
     */
    private void openInventoryGUI() {
        pokemonFrame.dispose();
        InventoryGUI inventoryGUI = new InventoryGUI(CInventory);
    }
    
    /**
     * A method that opens the area GUI
     */
    private void openAreaGUI() {
        System.out.println("Opening AreaGUI...");
        pokemonFrame.dispose();
        AreaGUI areaGUI = new AreaGUI(CInventory, CArea);
        System.out.println("AreaGUI opened successfully.");
    }

    /**
     * A method that opens the evolution GUI
     */
    private void openEvolutionGUI() {
        System.out.println("Opening EvolutionGUI...");
        pokemonFrame.dispose();  
        EvolutionGUI evolutionGUI = new EvolutionGUI(CInventory, CEvolution);
        System.out.println("EvolutionGUI opened successfully.");
    }

    /**
     * A method that handles the actions for each main menu
     */
    private void handleButtonClick(String buttonText) {
        switch (buttonText) {
            case "View Inventory":
                openInventoryGUI();
                break;
            case "Explore Areas":
                openAreaGUI();
                break;
            case "Evolve a Pokemon":
                if (CInventory.getInventory().size() > 1){
                    openEvolutionGUI();
                }
                break;
            case "Exit":
                System.exit(0);
                break;
            default:
                break;
        }
    }    
}
