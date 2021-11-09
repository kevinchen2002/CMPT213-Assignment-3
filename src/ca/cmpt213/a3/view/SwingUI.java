package ca.cmpt213.a3.view;

import ca.cmpt213.a3.control.ConsumableFactory;
import ca.cmpt213.a3.control.ConsumableManager;
import ca.cmpt213.a3.model.Consumable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Objects;

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
            String input = JOptionPane.showInputDialog(message);
            if (input == null) {
                return -1;
            }
            return Integer.parseInt(input);
        } catch (NumberFormatException nfe) {
            //do nothing
        }
        return -1;
    }

    private double getDouble(String message) {
        try {
            String input = JOptionPane.showInputDialog(message);
            if (input == null) {
                return -1;
            }
            return Double.parseDouble(input);
        } catch (NumberFormatException nfe) {
            //do nothing
        }
        return -1;
    }

    private String getString(String message) {
        return JOptionPane.showInputDialog(message);
    }

    private LocalDateTime getDateTime() {
        int year;
        int month;
        int day;
        while (true) {
            try {
                final int MIN_YEAR = 2000;
                year = getInteger("Enter the year of the expiry date: ");
                while (year < MIN_YEAR) {
                    year = getInteger("The year cannot be before 2000.");
                }

                final int MIN_MONTH = 1;
                final int MAX_MONTH = 12;
                month = getInteger("Enter the month of the expiry date:");
                while (month < MIN_MONTH || month > MAX_MONTH) {
                    month = getInteger("The month must be between 1 and 12.");
                }

                final int MIN_DAY = 1;
                final int MAX_DAY = 31;
                day = getInteger("Enter the day of the expiry date: ");
                while (day < MIN_DAY || day > MAX_DAY) {
                    day = getInteger("The day must be between 1 and 31.");
                }

                return LocalDateTime.of(year, month, day, 23, 59);
            } catch (DateTimeException e) {
                JOptionPane.showMessageDialog(null, "This date does not exist! Please try again.");
            }
        }
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
        if (name == null) {
            return;
        }
        while (name.equals("")) {
            name = getString("The name cannot be empty.");
        }

        String notes = getString("Enter any notes about this " + consumableType + ".");
        if (notes == null) {
            return;
        }

        double price = getDouble("What is the price of this " + consumableType + "?");
        if (price == -1) {
            return;
        }
        while (price < 0) {
            price = getDouble("The price cannot be less than 0.");
        }

        double weightOrVolume = getDouble("Enter the " + dataType + " of this " + consumableType + " item: ");
        if (weightOrVolume == -1) {
            return;
        }
        if (weightOrVolume < 0) {
            weightOrVolume = getDouble("The " + dataType + " cannot be less than 0.");
        }

        LocalDateTime expiry = getDateTime();

        Consumable newConsumable = ConsumableFactory.getInstance(isFood, name, notes,
                price, weightOrVolume, expiry);

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
        if (Objects.equals(e.getActionCommand(), "All")) {
            viewAllConsumables();
        } else if (Objects.equals(e.getActionCommand(), "Expired")) {
            viewExpired();
        } else if (Objects.equals(e.getActionCommand(), "Not Expired")) {
            viewNotExpired();
        } else if (Objects.equals(e.getActionCommand(), "Expiring in 7 Days")) {
            viewExpiringSevenDays();
        } else if (Objects.equals(e.getActionCommand(), "Add")) {
            addConsumable();
        } else if (Objects.equals(e.getActionCommand(), "Remove")) {
            removeConsumable();
        }
    }
}
