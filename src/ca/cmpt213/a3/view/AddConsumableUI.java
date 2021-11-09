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
        panel.add(consumableTypeSelect);

        panel.setSize(200, 200);
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
