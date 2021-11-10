package ca.cmpt213.a3.view;

import ca.cmpt213.a3.control.ConsumableFactory;
import ca.cmpt213.a3.control.ConsumableManager;
import ca.cmpt213.a3.model.Consumable;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.optionalusertools.DateTimeChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateTimeChangeEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.Objects;

public class AddConsumableDialog extends JDialog implements ActionListener, DateTimeChangeListener {
    private boolean isFood = true;
    private LocalDateTime expDate;
    private DateTimePicker dateTimePicker;
    private final ConsumableManager consumableManager = ConsumableManager.getInstance();

    private final JComboBox<String> consumableTypeSelect;
    private JLabel weightOrVolumeLabel;
    private final String[] typeOptions = {"Food", "Drink"};

    JTextField nameField;
    JTextField notesField;
    JTextField priceField;
    JTextField weightOrVolumeField;

    public AddConsumableDialog(Frame parent) {
        super(parent, "Add", true);

        Point location = parent.getLocation();
        setLocation(location.x+100, location.y+100);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        consumableTypeSelect = new JComboBox<>(typeOptions);
        consumableTypeSelect.setPreferredSize(new Dimension(300, 25));
        consumableTypeSelect.addActionListener(this);

        JPanel namePanel = getNamePanel();
        JPanel notesPanel = getNotesPanel();
        JPanel pricePanel = getPricePanel();
        JPanel weightOrVolumePanel = getWeightOrVolumePanel();
        JPanel datePanel = getDatePanel();
        JPanel btnPanel = getBtnPanel();

        panel.add(consumableTypeSelect);
        panel.add(namePanel);
        panel.add(notesPanel);
        panel.add(pricePanel);
        panel.add(weightOrVolumePanel);
        panel.add(datePanel);
        panel.add(btnPanel);

        getContentPane().setSize(500,500);
        getContentPane().add(panel);
        pack();
        this.setVisible(true);
    }

    private JPanel getNamePanel() {
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        JLabel nameLabel = new JLabel();
        nameLabel.setText("Name: ");
        nameLabel.setPreferredSize(new Dimension(50, 25));
        nameField = new JTextField();
        nameField.addActionListener(this);
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        namePanel.setPreferredSize(new Dimension(300,25));
        return namePanel;
    }

    private JPanel getNotesPanel() {
        JPanel notesPanel = new JPanel();
        notesPanel.setLayout(new BoxLayout(notesPanel, BoxLayout.X_AXIS));
        JLabel notesLabel = new JLabel();
        notesLabel.setText("Notes: ");
        notesLabel.setPreferredSize(new Dimension(50, 25));
        notesField = new JTextField();
        notesPanel.add(notesLabel);
        notesPanel.add(notesField);
        notesPanel.setPreferredSize(new Dimension(300,25));
        return notesPanel;
    }

    private JPanel getPricePanel() {
        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.X_AXIS));
        JLabel priceLabel = new JLabel();
        priceLabel.setText("Price: ");
        priceLabel.setPreferredSize(new Dimension(50, 25));
        priceField = new JTextField();
        pricePanel.add(priceLabel);
        pricePanel.add(priceField);
        pricePanel.setPreferredSize(new Dimension(300,25));
        return pricePanel;
    }

    private JPanel getWeightOrVolumePanel() {
        JPanel weightOrVolumePanel = new JPanel();
        weightOrVolumePanel.setLayout(new BoxLayout(weightOrVolumePanel, BoxLayout.X_AXIS));
        weightOrVolumeLabel = new JLabel();
        weightOrVolumeLabel.setText("Weight: ");
        weightOrVolumeLabel.setPreferredSize(new Dimension(50, 25));
        weightOrVolumeField = new JTextField();
        weightOrVolumePanel.add(weightOrVolumeLabel);
        weightOrVolumePanel.add(weightOrVolumeField);
        weightOrVolumePanel.setPreferredSize(new Dimension(300,25));
        return weightOrVolumePanel;
    }

    private JPanel getDatePanel() {
        JPanel datePanel = new JPanel();
        datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.X_AXIS));
        JLabel dateLabel = new JLabel();
        dateLabel.setText("Date: ");
        dateLabel.setPreferredSize(new Dimension(50, 25));
        dateTimePicker = new DateTimePicker();
        dateTimePicker.addDateTimeChangeListener(this);
        datePanel.add(dateLabel);
        datePanel.add(dateTimePicker);
        datePanel.setPreferredSize(new Dimension (300, 25));
        return datePanel;
    }

    private JPanel getBtnPanel() {
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
        JButton okBtn = new JButton("OK");
        JButton cancelBtn = new JButton("Cancel");
        okBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        btnPanel.add(okBtn);
        btnPanel.add(cancelBtn);
        return btnPanel;
    }

    private void addConsumable() {
        try {
            String name = parseName();
            String notes = parseNotes();
            double price = parsePrice();
            double weightOrVolume = parseWeightOrVolume();

            if (name.equals("")) {
                JOptionPane.showMessageDialog(this, "Error: name cannot be empty");
                return;
            }
            if (price < 0) {
                JOptionPane.showMessageDialog(this, "Error: price cannot be less than 0");
                return;
            }
            if (weightOrVolume < 0) {
                if (isFood) {
                    JOptionPane.showMessageDialog(this, "Error: weight cannot be less than 0");
                } else {
                    JOptionPane.showMessageDialog(this, "Error: volume cannot be less than 0");
                }
                return;
            }
            Consumable newConsumable = ConsumableFactory.getInstance(isFood, name, notes, price, weightOrVolume, expDate);
            consumableManager.addConsumable(newConsumable);
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: invalid input");
        }
    }

    private String parseName() {
        if (nameField.getText() == null) {
            return "";
        }
        return nameField.getText();
    }

    private String parseNotes() {
        if (notesField.getText() == null) {
            return "";
        }
        return notesField.getText();
    }

    private double parsePrice() {
        if (priceField.getText() == null) {
            return -1;
        }
        try {
            return Double.parseDouble(priceField.getText());
        } catch (NumberFormatException nfe) {
            //do nothing
        }
        return -1;
    }

    private double parseWeightOrVolume() {
        if (weightOrVolumeField.getText() == null) {
            return -1;
        }
        try {
            return Double.parseDouble(weightOrVolumeField.getText());
        } catch (NumberFormatException nfe) {
            //do nothing
        }
        return -1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Objects.equals(consumableTypeSelect.getSelectedItem(), "Food")) {
            weightOrVolumeLabel.setText("Weight: ");
            isFood = true;
        } else if (Objects.equals(consumableTypeSelect.getSelectedItem(), "Drink")) {
            weightOrVolumeLabel.setText("Volume:");
            isFood = false;
        }

        if (Objects.equals(e.getActionCommand(), "OK")) {
            addConsumable();
        } else if (Objects.equals(e.getActionCommand(), "Cancel")) {
            this.dispose();
        }
    }

    @Override
    public void dateOrTimeChanged(DateTimeChangeEvent event) {
        expDate = dateTimePicker.getDateTimePermissive();
    }
}
