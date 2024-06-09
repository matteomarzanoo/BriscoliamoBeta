package View;

import Controller.ServerController;

import javax.swing.*;
import java.awt.*;

public class ServerPanel extends JPanel {

    private JTextField portTextField;
    private JTextField addressIpTextField;
    private JButton buttonStartListen;
    private JList<String> listLogs;
    private DefaultListModel<String> listModel;
    private ServerController serverController;
    private LogReader logReader;

    public ServerPanel() {
        setLayout(new BorderLayout());
        serverController = new ServerController(this);

        portTextField = new JTextField("8888", 10);
        listModel = new DefaultListModel<>();
        listLogs = new JList<>(listModel);
        buttonStartListen = new JButton("CONNECT!");

        JPanel connectPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        connectPanel.add(new JLabel("Port:"));
        connectPanel.add(portTextField);
        connectPanel.add(buttonStartListen);

        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBorder(BorderFactory.createTitledBorder("Logs"));
        logPanel.add(new JScrollPane(listLogs), BorderLayout.CENTER);

        add(connectPanel, BorderLayout.NORTH);
        add(logPanel, BorderLayout.CENTER);

        buttonStartListen.addActionListener(serverController);

        logReader = new LogReader(listLogs, listModel);
    }

    public LogReader getLogReader() {
        return logReader;
    }

    public JButton getButtonStartListen() {
        return buttonStartListen;
    }

    public int getPort() throws NumberFormatException {
        try {
            return Integer.parseInt(portTextField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid port number. Please enter a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
            throw e;
        }
    }

    public void onServerStart() {
        buttonStartListen.setEnabled(false);
        portTextField.setEditable(false);
        portTextField.setText("");
    }
}
