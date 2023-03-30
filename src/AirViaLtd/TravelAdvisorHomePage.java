package AirViaLtd;

import javax.swing.*;

public class TravelAdvisorHomePage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel buttonsPanel;
    private JLabel homepageLabel;
    private JButton createCustomerAccountButton;
    private JButton issueRefundButton;
    private JButton createReportButton;
    private JButton sellTicketButton;

    private AirViaLtd app;

    public TravelAdvisorHomePage(AirViaLtd a) {
        this.app = a;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
