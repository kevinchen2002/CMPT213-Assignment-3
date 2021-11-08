package ca.cmpt213.a3.view;

import ca.cmpt213.a3.control.ConsumableFactory;
import ca.cmpt213.a3.control.ConsumableManager;
import ca.cmpt213.a3.model.Consumable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class SwingUI implements ActionListener {
    JFrame applicationFrame;
    JTextPane displayPane;
    JScrollPane consumableListView;

    ConsumableManager consumableManager = new ConsumableManager();

    public void displayMenu() {
        applicationFrame = new JFrame("Consumable Tracker");
        applicationFrame.setSize(800, 800);
        applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        applicationFrame.setLayout(new BoxLayout(applicationFrame.getContentPane(), BoxLayout.Y_AXIS));

        setupTopButtons();
        setupListView();
        setupAddRemoveButton();

        applicationFrame.setSize(700, 700);
        applicationFrame.pack();
        applicationFrame.setVisible(true);

        //DEBUG STUFF
        Consumable newItem = ConsumableFactory.getInstance(true, "food", "this is food", 1, 1, LocalDateTime.now());
        consumableManager.addConsumable(newItem);
        newItem = ConsumableFactory.getInstance(false, "drink", "this is drink", 2, 2, LocalDateTime.now());
        consumableManager.addConsumable(newItem);
        newItem = ConsumableFactory.getInstance(false, "drink", "this is drink", 2, 2, LocalDateTime.now());
        consumableManager.addConsumable(newItem);
        newItem = ConsumableFactory.getInstance(false, "drink", "this is drink", 2, 2, LocalDateTime.now());
        consumableManager.addConsumable(newItem);
        newItem = ConsumableFactory.getInstance(false, "drink", "this is drink", 2, 2, LocalDateTime.now());
        consumableManager.addConsumable(newItem);
        newItem = ConsumableFactory.getInstance(false, "drink", "this is drink", 2, 2, LocalDateTime.now());
        consumableManager.addConsumable(newItem);
    }

    private void setupTopButtons() {
        JButton showAllButton = new JButton("All");
        JButton showExpiredButton = new JButton("Expired");
        JButton showNotExpiredButton = new JButton("Not Expired");
        JButton showExpiringSevenButton = new JButton("Expiring in 7 Days");

        showAllButton.addActionListener(this);
        showExpiredButton.addActionListener(this);
        showNotExpiredButton.addActionListener(this);
        showExpiringSevenButton.addActionListener(this);

        JPanel listTabsPanel = new JPanel();
        listTabsPanel.setLayout(new BoxLayout(listTabsPanel, BoxLayout.X_AXIS));

        listTabsPanel.add(showAllButton);
        listTabsPanel.add(showExpiredButton);
        listTabsPanel.add(showNotExpiredButton);
        listTabsPanel.add(showExpiringSevenButton);

        listTabsPanel.setSize(800, 100);
        listTabsPanel.setPreferredSize(new Dimension(800, 100));
        addPanel(listTabsPanel, applicationFrame);
    }

    private void setupListView() {
        displayPane = new JTextPane();

        consumableListView = new JScrollPane(displayPane);
        consumableListView.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        consumableListView.setSize(800, 500);
        consumableListView.setPreferredSize(new Dimension(800, 500));
        consumableListView.setAlignmentX(Component.CENTER_ALIGNMENT);
        applicationFrame.add(consumableListView);
    }

    private void setupAddRemoveButton() {
        JButton addNewButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");

        addNewButton.addActionListener(this);
        removeButton.addActionListener(this);

        JPanel addRemovePanel = new JPanel();
        addRemovePanel.setLayout(new BoxLayout(addRemovePanel, BoxLayout.X_AXIS));

        addRemovePanel.add(addNewButton);
        addRemovePanel.add(removeButton);
        addRemovePanel.setSize(800, 100);
        addRemovePanel.setPreferredSize(new Dimension(800, 100));
        addPanel(addRemovePanel, applicationFrame);
    }

    private static void addPanel(JPanel jpanel, Container container) {
        jpanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(jpanel);
    }

    private void viewAllConsumables() {
        displayPane.setText(consumableManager.getAllConsumablesString());
    }

    private void viewExpired() {
        displayPane.setText(consumableManager.getExpiredString());
    }

    private void viewNotExpired() {
        displayPane.setText(consumableManager.getNotExpiredString());
    }

    private void viewExpiringSevenDays() {
        displayPane.setText(consumableManager.getExpiringSevenDaysString());
    }

    private int getInteger(String message) {
        try {
            return Integer.parseInt(JOptionPane.showInputDialog(message));
        } catch (NumberFormatException nfe) {
            //do nothing
        }
        return -1;
    }

    private double getDouble(String message) {
        try {
            return Double.parseDouble(JOptionPane.showInputDialog(message));
        } catch (NumberFormatException nfe) {
            //do nothing
        }
        return -1;
    }

    private String getString(String message) {
        return JOptionPane.showInputDialog(message);
    }

    /**
     * code derived from https://mkyong.com/swing/java-swing-joptionpane-showoptiondialog-example/
     * @return the option 0 for food or 1 for drink.
     */
    private int getFoodOrDrink() {
        String[] options = {"Food", "Drink"};
        return JOptionPane.showOptionDialog(null, "Is this a Food or Drink?",
                "Click a button",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);
    }

    private void addConsumable() {
        int itemType = getFoodOrDrink() + 1;
        if (itemType == 0) {
            return;
        }
        boolean isFood;
        String consumableType;
        String dataType;
        if (itemType == 1) {
            isFood = true;
            consumableType = "food";
            dataType = "weight";
        } else {
            isFood = false;
            consumableType = "drink";
            dataType = "volume";
        }
        String name = getString("What is the name of this " + consumableType + "?");
        while (name.equals("")) {
            name = getString("The name cannot be empty.");
        }
        String notes = getString("Enter any notes about this " + consumableType + ".");

        double price = getDouble("What is the price of this " + consumableType + "?");
        while (price < 0) {
            price = getDouble("The price cannot be less than 0.");
        }

        double weightOrVolume = getDouble("Enter the " + dataType + " of this " + consumableType + " item: ");
        if (weightOrVolume < 0) {
            weightOrVolume = getDouble("The " + dataType + " cannot be less than 0.");
        }

        Consumable newConsumable = ConsumableFactory.getInstance(isFood, name, notes,
                price, weightOrVolume, LocalDateTime.now());

        consumableManager.addConsumable(newConsumable);
        displayPane.setText("Item " + name + " has been added!");
    }

    private void removeConsumable() {
        int toDelete = getInteger("Which consumable would you like to delete?");
        if (toDelete < 1 || toDelete > consumableManager.getSize()) {
            displayPane.setText("INVALID - please give a number from 1 to " + consumableManager.getSize() + ".");
            return;
        }
        consumableManager.removeConsumable(toDelete-1);
        displayPane.setText("Item #" + toDelete + " has been removed!");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "All") {
            viewAllConsumables();
        } else if (e.getActionCommand() == "Expired") {
            viewExpired();
        } else if (e.getActionCommand() == "Not Expired") {
            viewNotExpired();
        } else if (e.getActionCommand() == "Expiring in 7 Days") {
            viewExpiringSevenDays();
        } else if (e.getActionCommand() == "Add") {
            addConsumable();
        } else if (e.getActionCommand() == "Remove") {
            removeConsumable();
        }
    }
}
