package AirViaLtd;

import javax.swing.*;

public class CreateReportPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel featurePanel;
    private JComboBox selectReportComboBox;
    private JLabel createReportLabel;
    private JLabel selectReportLabel;
    private JLabel individualOrGlobalLabel;
    private JComboBox individualGlobalComboBox;
    private JComboBox travelAdvisorComboBox;
    private JLabel selectTravelAdvisorLabel;
    private JFormattedTextField startDateFormattedTextField;
    private JLabel startDateLabel;
    private JLabel endDateLabel;
    private JFormattedTextField endDateFormattedTextField;
    private JButton createButton;
    private JButton downloadButton;

    private AirViaLtd app;

    public CreateReportPage(AirViaLtd a) {
        this.app = a;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
