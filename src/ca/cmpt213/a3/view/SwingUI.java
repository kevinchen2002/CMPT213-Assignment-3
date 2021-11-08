package ca.cmpt213.a3.view;

import ca.cmpt213.a3.control.ConsumableFactory;
import ca.cmpt213.a3.control.ConsumableManager;
import ca.cmpt213.a3.model.Consumable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;

//TODO: make the buttons do something and make them tell what

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

        showAllButton.addActionListener(this::actionPerformed);
        showExpiredButton.addActionListener(this::actionPerformed);
        showNotExpiredButton.addActionListener(this::actionPerformed);
        showExpiringSevenButton.addActionListener(this::actionPerformed);

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
        }
    }
}
