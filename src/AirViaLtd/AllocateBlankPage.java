package AirViaLtd;

import javax.swing.*;

public class AllocateBlankPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel buttonsPanel;
    private JLabel allocateBlankLabel;
    private JRadioButton domesticBlankRadioButton;
    private JRadioButton MCOBlankRadioButton;
    private JRadioButton interlineBlankRadioButton;
    private JComboBox travelAdvisorComboBox;
    private JButton assignButton;

    private AirViaLtd app;

    public AllocateBlankPage(AirViaLtd a) {
        this.app = a;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
