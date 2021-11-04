package ca.cmpt213.a3.view;

import javax.swing.*;
import java.awt.*;

public class SwingUI {
    JFrame applicationFrame;
    JButton showAllButton;
    JButton addNewButton;
    JScrollPane consumableListView;

    public void displayMenu() {
        applicationFrame = new JFrame("Consumable Tracker");
        applicationFrame.setSize(800, 800);
        applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        applicationFrame.setLayout(new BoxLayout(applicationFrame.getContentPane(), BoxLayout.Y_AXIS));

        showAllButton = new JButton("View All");
        JPanel listTabsPanel = new JPanel();
        listTabsPanel.add(showAllButton);
        listTabsPanel.setSize(800, 150);
        addPanel(listTabsPanel, applicationFrame);

        consumableListView = new JScrollPane();
        JPanel listViewPanel = new JPanel();
        JLabel dummy = new JLabel("BLAH");
        consumableListView.add(dummy);
        listViewPanel.add(consumableListView);
        listViewPanel.setSize(800, 500);
        addPanel(listViewPanel, applicationFrame);

        addNewButton = new JButton("Add");
        JPanel addPanel = new JPanel();
        addPanel.add(addNewButton);
        addPanel.setSize(800, 150);
        addPanel(addPanel, applicationFrame);

        applicationFrame.setSize(800, 800);
        applicationFrame.pack();
        applicationFrame.setVisible(true);
    }

    private static void addPanel(JPanel jpanel, Container container) {
        jpanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(jpanel);
    }

    private void viewAllConsumables() {

    }
}
