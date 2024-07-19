import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class EvolutionGUI {
    private JFrame evolutionFrame;
    private Inventory CInventory;
    private Evolution CEvolution;
    private List<Creature> aSelectedCreatures;

    /**
     * EvolutionGUI constructor
     * 
     * @param CInventory - inventory
     * @param CEvolution - evolution
     */
    public EvolutionGUI(Inventory CInventory, Evolution CEvolution) {
        this.CInventory = CInventory;
        this.CEvolution = CEvolution;
        this.aSelectedCreatures = new ArrayList<>();
        initialize();
    }

    /**
     * A method that initializes the evolution frame
     */
    private void initialize() {
        evolutionFrame = new JFrame("Evolution");
        JScrollPane contentPanel = selectCreaturePanel();
        evolutionFrame.getContentPane().add(contentPanel, BorderLayout.CENTER);
        evolutionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        evolutionFrame.setSize(400, 300);
        evolutionFrame.setLocationRelativeTo(null);
        evolutionFrame.setVisible(true);
    }

    /**
     * A method that creates select creature panel
     * 
     * @return scrollPane
     */
    private JScrollPane selectCreaturePanel() {
        JPanel creaturePanel = new JPanel();
        creaturePanel.setLayout(new GridLayout(0, 1));

        JLabel selectLabel = new JLabel("Select 2 Creatures");
        selectLabel.setHorizontalAlignment(SwingConstants.CENTER);
        creaturePanel.add(selectLabel);

        for (Creature creature : CInventory.getInventory()) {
            JButton creatureButton = createCreatureButton(creature, evolutionFrame);
            creaturePanel.add(creatureButton);
        }

        JScrollPane scrollPane = new JScrollPane(creaturePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        scrollPane.setPreferredSize(new Dimension(400, 800));

        return scrollPane;
    }

    /**
     * A method that creates creature buttons
     * 
     * @return button
     */
    private JButton createCreatureButton(Creature creature, JFrame frame) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());

        ImageIcon creatureIcon = createResizedIcon("creatures/" + creature.getName() + ".png", 90, 100);
        JLabel imageLabel = new JLabel(creatureIcon);
        button.add(imageLabel, BorderLayout.NORTH);

        JLabel nameLabel = new JLabel(creature.getName());
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        button.add(nameLabel, BorderLayout.CENTER);

        button.setBackground(Color.WHITE);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (creature.getEL() != 3) {
                    aSelectedCreatures.add(creature);
                    button.setEnabled(false);

                    if (aSelectedCreatures.size() == 2) {
                        evolutionPanel(aSelectedCreatures.get(0), aSelectedCreatures.get(1));
                        frame.dispose();
                    }
                }
                button.setEnabled(false);
            }
        });

        return button;
    }

    /**
     * A method that creates evolution panel
     * 
     * @param creature1 - 1st creature to be evolved
     * @param creature2 - 2nd creature to be evolved
     */
    private void evolutionPanel(Creature creature1, Creature creature2) {
        JFrame evolutionFrame = new JFrame("Evolution Panel");
        evolutionFrame.setSize(800, 200);
        evolutionFrame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(0, 3));

        panel.add(createCreaturePanel(creature1));

        JButton evolveButton = new JButton("Evolve");
        evolveButton.addActionListener(e -> {
            evolveButton.setEnabled(false);
            evolveCreatures(creature1, creature2, CInventory);
            evolutionFrame.dispose(); 
        });
        evolveButton.setBackground(Color.WHITE);
        panel.add(evolveButton);

        panel.add(createCreaturePanel(creature2));

        evolutionFrame.getContentPane().add(panel, BorderLayout.CENTER);
        evolutionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        evolutionFrame.setLocationRelativeTo(null);
        evolutionFrame.setVisible(true);
    }

    /**
     * A method that evolves the selected creature and
     * removes them from the inventory
     * 
     * @param creature1 - 1st creature to be evolved
     * @param creature2 - 2nd creature to be evolved
     * @param CInventory - inventory
     */
    private void evolveCreatures(Creature creature1, Creature creature2, Inventory CInventory) {
        Creature evolvedCreature = CEvolution.evolveCreature(creature1, creature2, CInventory);

        if (evolvedCreature == null) {
            showEvolutionResult(false, creature1, creature2);
        } else {
            CInventory.removeCreature(creature1);
            CInventory.removeCreature(creature2);

            CInventory.addCreature(evolvedCreature);

            showEvolutionResult(true, evolvedCreature);
        }
    }

    /**
     * A method that displays whether or not the
     * evolution is successful
     * 
     * @param success - boolean that represents if the creatures
     * meet the criteria for evolution
     * @param creatures - creatures to be evolved
     */
    private void showEvolutionResult(boolean success, Creature... creatures) {
        JFrame resultFrame = new JFrame("Evolution Result");
        resultFrame.setSize(600, 200);
        resultFrame.setLayout(new BorderLayout());

        JLabel resultLabel = new JLabel(success ? "EVOLUTION SUCCESSFUL" : "EVOLUTION FAILED");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultFrame.add(resultLabel, BorderLayout.NORTH);

        JPanel creaturePanel = new JPanel(new GridLayout(1, creatures.length));
        for (Creature creature : creatures) {
            creaturePanel.add(createCreaturePanel(creature));
        }
        resultFrame.add(creaturePanel, BorderLayout.CENTER);

        JButton exitButton = new JButton("Exit Evolution", createResizedIcon("icons/back.png", 50, 50));
        exitButton.addActionListener(e -> resultFrame.dispose());
        exitButton.setBackground(Color.WHITE);
        resultFrame.add(exitButton, BorderLayout.SOUTH);

        resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        resultFrame.setLocationRelativeTo(null);
        resultFrame.setVisible(true);
    }

    /**
     * A method that creates the creature panel
     * 
     * @param creature - selected creature
     * 
     * @return panel
     */
    private JPanel createCreaturePanel(Creature creature) {
        JPanel panel = new JPanel(new BorderLayout());

        String imagePath = "creatures/" + creature.getName() + ".png";
        ImageIcon creatureIcon = createResizedIcon(imagePath, 100, 100);
        JLabel imageLabel = new JLabel(creatureIcon);
        panel.add(imageLabel, BorderLayout.WEST);

        JPanel textPanel = new JPanel(new GridLayout(0, 1));

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
