package ca.cmpt213.a3.view;

import ca.cmpt213.a3.control.ConsumableFactory;
import ca.cmpt213.a3.model.Consumable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class AddConsumableUI extends JDialog implements ActionListener {
    private JComboBox<String> consumableTypeSelect;
    private final String[] typeOptions = {"Food", "Drink"};

    public AddConsumableUI(Frame parent) {
        super(parent, "Add", true);

        Point location = parent.getLocation();
        setLocation(location.x+100, location.y+100);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        consumableTypeSelect = new JComboBox<String>(typeOptions);
        consumableTypeSelect.setSelectedIndex(-1);
        consumableTypeSelect.setPreferredSize(new Dimension(500, 30));
        consumableTypeSelect.addActionListener(this);

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        JLabel nameLabel = new JLabel();
        nameLabel.setText("Name: ");
        JTextField nameField = new JTextField();
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        namePanel.setPreferredSize(new Dimension(500,100));

        JPanel notesPanel = new JPanel();
        notesPanel.setLayout(new BoxLayout(notesPanel, BoxLayout.X_AXIS));
        JLabel notesLabel = new JLabel();
        notesLabel.setText("Notes: ");
        JTextField notesField = new JTextField();
        notesPanel.add(notesLabel);
        notesPanel.add(notesField);
        notesPanel.setPreferredSize(new Dimension(500,100));

        panel.add(consumableTypeSelect);
        panel.add(namePanel);
        panel.add(notesPanel);

        getContentPane().setSize(500,500);
        getContentPane().add(panel);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public Consumable run() {
        this.setVisible(true);
        return ConsumableFactory.getInstance(true, " ", "", 1,1, LocalDateTime.now());
    }
}
