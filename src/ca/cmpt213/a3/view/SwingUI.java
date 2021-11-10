package ca.cmpt213.a3.view;

import ca.cmpt213.a3.control.ConsumableManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class SwingUI implements ActionListener {
    JFrame applicationFrame;
    JTextPane displayPane;
    JScrollPane consumableListView;
    JLabel categoryLabel;

    private final ConsumableManager consumableManager = ConsumableManager.getInstance();
    private int DISPLAY_OPTION = 0;

    public void displayMenu() {
        consumableManager.loadFile();

        applicationFrame = new JFrame("Consumable Tracker");
        applicationFrame.setSize(800, 800);
        applicationFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        applicationFrame.setLayout(new BoxLayout(applicationFrame.getContentPane(), BoxLayout.Y_AXIS));
        applicationFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                consumableManager.writeFile();
                super.windowClosing(e);
                applicationFrame.dispose();
            }
        });

        setupTopButtons();
        setupCategoryLabel();
        setupListView();
        setupAddRemoveButton();

        updateView();

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

        listTabsPanel.setPreferredSize(new Dimension(800, 90));
        addPanel(listTabsPanel, applicationFrame);
    }

    private void setupCategoryLabel() {
        categoryLabel = new JLabel("All Consumables");
        JPanel categoryPanel = new JPanel();
        categoryPanel.add(categoryLabel);
        categoryPanel.setPreferredSize(new Dimension(800, 20));
        addPanel(categoryPanel, applicationFrame);
    }

    private void setupListView() {
        displayPane = new JTextPane();
        displayPane.setEditable(false);

        consumableListView = new JScrollPane(displayPane);
        consumableListView.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
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
        addRemovePanel.setPreferredSize(new Dimension(800, 90));
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

    private void updateView() {
        if (DISPLAY_OPTION == 0) {
            viewAllConsumables();
            categoryLabel.setText("All Consumables");
        } else if (DISPLAY_OPTION == 1) {
            viewExpired();
            categoryLabel.setText("Expired Consumables");
        } else if (DISPLAY_OPTION == 2) {
            viewNotExpired();
            categoryLabel.setText("Consumables which are not Expired");
        } else if (DISPLAY_OPTION == 3) {
            viewExpiringSevenDays();
            categoryLabel.setText("Consumables Expiring within Seven Days");
        }
    }

    private int getDeletionIndex() {
        try {
            String input = JOptionPane.showInputDialog("Which consumable would you like to delete?");
            if (input == null) {
                return -1;
            }
            return Integer.parseInt(input);
        } catch (NumberFormatException nfe) {
            //do nothing
        }
        return -1;
    }

    private void removeConsumable() {
        int toDelete = getDeletionIndex();
        if (toDelete < 1 || toDelete > consumableManager.getSize()) {
            JOptionPane.showMessageDialog(null, "Please give a number from 1 to "
                    + consumableManager.getSize() + ".");
            return;
        }
        consumableManager.removeConsumable(toDelete-1);
        updateView();
        JOptionPane.showMessageDialog(null, "Item #" + toDelete + " has been removed!");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Objects.equals(e.getActionCommand(), "All")) {
            DISPLAY_OPTION = 0;
            updateView();
        } else if (Objects.equals(e.getActionCommand(), "Expired")) {
            DISPLAY_OPTION = 1;
            updateView();
        } else if (Objects.equals(e.getActionCommand(), "Not Expired")) {
            DISPLAY_OPTION = 2;
            updateView();
        } else if (Objects.equals(e.getActionCommand(), "Expiring in 7 Days")) {
            DISPLAY_OPTION = 3;
            updateView();
        } else if (Objects.equals(e.getActionCommand(), "Add")) {
            new AddConsumableDialog(applicationFrame);
            updateView();
        } else if (Objects.equals(e.getActionCommand(), "Remove")) {
            removeConsumable();
        }
    }
}
