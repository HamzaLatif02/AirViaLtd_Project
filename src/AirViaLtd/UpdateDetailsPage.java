package AirViaLtd;

import javax.swing.*;

public class UpdateDetailsPage {
    private JTextField nameTextField;
    private JTextField cityTextField;
    private JTextField countryTextField;
    private JTextField surnameTextField;
    private JTextField emailTextField;
    private JTextField addressTextField;
    private JTextField usernameTextField;
    private JPasswordField passwordPasswordField;
    private JButton createButton;
    private JButton editButton5;
    private JButton editButton;
    private JButton editButton1;
    private JButton editButton2;
    private JButton editButton3;
    private JButton editButton4;
    private JPanel mainPanel;

    private AirViaLtd app;

    public UpdateDetailsPage(AirViaLtd a) {
        this.app = a;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
