package ca.cmpt213.a3;

import ca.cmpt213.a3.view.SwingUI;

import javax.swing.*;

public class ConsumableTracker {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SwingUI app = new SwingUI();
                app.displayMenu();
            }
        });
    }
}
