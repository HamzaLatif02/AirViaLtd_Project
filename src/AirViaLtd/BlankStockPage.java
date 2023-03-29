package AirViaLtd;

import javax.swing.*;

public class BlankStockPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel featurePanel;
    private JLabel blankStockLabel;
    private JButton IDButton;
    private JButton typeButton;
    private JButton travelAdvisorButton;
    private JLabel sortByLabel;
    private JButton notAssignedButton;
    private JButton dateButton;
    private JButton usedButton;
    private JPanel tablePanel;
    private JTable blankTable;

    private AirViaLtd app;

    public BlankStockPage(AirViaLtd a) {
        this.app = a;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
