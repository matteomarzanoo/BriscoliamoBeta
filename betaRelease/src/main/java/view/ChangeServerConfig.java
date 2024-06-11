package view;

import model.online.Client;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChangeServerConfig extends JPanel {
    JTextField field1 = new JTextField("127.0.0.1");
    JTextField field2 = new JTextField("8888");
    Client client = Client.getInstance();

    public ChangeServerConfig() {
        setLayout(new GridLayout(2, 2));
        add(new JLabel("IP Address"));
        add(field1);
        add(new JLabel("Server Port"));
        add(field2);

        int result = JOptionPane.showConfirmDialog(null, this, "Enter Values", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            client.setHost(field1.getText());
            client.setPort(Integer.parseInt(field2.getText()));
        } else {
            JOptionPane.showMessageDialog(null, "Action Aborted", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}
