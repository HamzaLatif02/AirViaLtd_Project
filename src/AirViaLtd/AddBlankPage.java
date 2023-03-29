package AirViaLtd;

import javax.swing.*;

public class AddBlankPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel featurePanel;
    private JLabel addBlankLabel;
    private JTextField singleBlankNumberTextField;
    private JLabel addOneLabel;
    private JLabel blankNumberLabel;
    private JTextField multipleBlankStartNumberTextField;
    private JTextField multipleBlankEndNumberTextField;
    private JButton addButton1;
    private JButton addButton;
    private JLabel addMultipleLabel;
    private JLabel startBlankNumberLabel;
    private JLabel endBlankNumberLabel;

    private AirViaLtd app;

    public AddBlankPage(AirViaLtd a) {
        this.app = a;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
