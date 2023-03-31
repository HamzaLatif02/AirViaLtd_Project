package AirViaLtd;

import javax.swing.*;

public class CreateTravelAgentAccountPage {
    private JPanel mainPanel;
    private JTextField emailTextField;
    private JTextField addressTextField;
    private JTextField usernameTextField;
    private JPasswordField passwordPasswordField;
    private JTextField nameTextField;
    private JTextField cityTextField;
    private JTextField countryTextField;
    private JTextField surnameTextField;
    private JButton createButton;

    private AirViaLtd app;

    public CreateTravelAgentAccountPage(AirViaLtd a) {

        this.app = a;

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
