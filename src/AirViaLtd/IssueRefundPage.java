package AirViaLtd;

import javax.swing.*;

public class IssueRefundPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel functionsPanel;
    private JLabel issueRefundLabel;
    private JTextField ticketNumberTextField;
    private JLabel ticketNumberLabel;
    private JTextField paymentTypeTextField;
    private JTextField refundAmountTextField;
    private JLabel paymentTypeLabel;
    private JLabel refundAmountLabel;
    private JButton issueRefundButton;

    private AirViaLtd app;

    public IssueRefundPage(AirViaLtd a) {
        this.app = a;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
