package ca.cmpt213.a3.view;

import ca.cmpt213.a3.control.ConsumableFactory;
import ca.cmpt213.a3.model.Consumable;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.Objects;

//TODO: FIX FORMATTING
//TODO: MAKE THE OK BUTTON RETURN SOMETHING

public class AddConsumableUI extends JDialog implements ActionListener, DateChangeListener {
    private boolean isFood = true;
    private LocalDateTime expDate;
    private final DatePicker datePicker;

    private final JComboBox<String> consumableTypeSelect;
    private final JLabel weightOrVolumeLabel;
    private final String[] typeOptions = {"Food", "Drink"};

    public AddConsumableUI(Frame parent) {
        super(parent, "Add", true);

        Point location = parent.getLocation();
        setLocation(location.x+100, location.y+100);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        consumableTypeSelect = new JComboBox<>(typeOptions);
        //consumableTypeSelect.setSelectedIndex(-1);
        consumableTypeSelect.setPreferredSize(new Dimension(500, 30));
        consumableTypeSelect.addActionListener(this);

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        JLabel nameLabel = new JLabel();
        nameLabel.setText("Name: ");
        JTextField nameField = new JTextField();
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        namePanel.setPreferredSize(new Dimension(500,30));

        JPanel notesPanel = new JPanel();
        notesPanel.setLayout(new BoxLayout(notesPanel, BoxLayout.X_AXIS));
        JLabel notesLabel = new JLabel();
        notesLabel.setText("Notes: ");
        JTextField notesField = new JTextField();
        notesPanel.add(notesLabel);
        notesPanel.add(notesField);
        notesPanel.setPreferredSize(new Dimension(500,30));

        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.X_AXIS));
        JLabel priceLabel = new JLabel();
        priceLabel.setText("Price: ");
        JTextField priceField = new JTextField();
        pricePanel.add(priceLabel);
        pricePanel.add(priceField);
        pricePanel.setPreferredSize(new Dimension(500,30));

        JPanel weightOrVolumePanel = new JPanel();
        weightOrVolumePanel.setLayout(new BoxLayout(weightOrVolumePanel, BoxLayout.X_AXIS));
        weightOrVolumeLabel = new JLabel();
        weightOrVolumeLabel.setText("Weight: ");
        JTextField weightOrVolumeField = new JTextField();
        weightOrVolumePanel.add(weightOrVolumeLabel);
        weightOrVolumePanel.add(weightOrVolumeField);
        weightOrVolumePanel.setPreferredSize(new Dimension(500,30));

        JPanel datePanel = new JPanel();
        datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.X_AXIS));
        JLabel dateLabel = new JLabel();
        dateLabel.setText("Date: ");
        datePicker = new DatePicker();
        datePicker.addDateChangeListener(this::dateChanged);
        datePanel.add(dateLabel);
        datePanel.add(datePicker);
        datePanel.setPreferredSize(new Dimension (500, 30));

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
        JButton okBtn = new JButton("OK");
        JButton cancelBtn = new JButton("Cancel");
        okBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        btnPanel.add(okBtn);
        btnPanel.add(cancelBtn);

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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Objects.equals(consumableTypeSelect.getSelectedItem(), "Food")) {
            weightOrVolumeLabel.setText("Weight: ");
            isFood = true;
        } else if (consumableTypeSelect.getSelectedItem().equals("Drink")) {
            weightOrVolumeLabel.setText("Volume:");
            isFood = false;
        }

        if (Objects.equals(e.getActionCommand(), "OK")) {
            //do run or some other Consumable return
        } else if (Objects.equals(e.getActionCommand(), "Cancel")) {
            this.dispose();
        }
    }

    public Consumable run() {
        this.setVisible(true);
        return ConsumableFactory.getInstance(isFood, " ", "", 1,1, LocalDateTime.now());
    }

    @Override
    public void dateChanged(DateChangeEvent event) {
        expDate = datePicker.getDate().atTime(23, 59);
    }
}
