package view;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChangeServerConfig extends JPanel
{
    // Crea i campi di input
    JTextField field1 = new JTextField();
    JTextField field2 = new JTextField();

    public ChangeServerConfig()
    {
        // Crea un pannello e aggiungi i campi di input
        setLayout(new GridLayout(2, 2));
        add(new JLabel("IP Address"));
        add(field1);
        add(new JLabel("Server Port"));
        add(field2);

        int result = JOptionPane.showConfirmDialog(null, this, "Enter Values", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String value1 = field1.getText();
            String value2 = field2.getText();
            /* Manderà questi valori al server */
            System.out.println("Value 1: " + value1);
            System.out.println("Value 2: " + value2);
        } else {
            System.err.println("Action Aborted");
        }
    }
}
