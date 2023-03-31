package AirViaLtd;

import javax.swing.*;

public class CommissionPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel functionPanel;
    private JLabel commissionLabel;
    private JList commissionList;
    private JTextField commissionRateTextField;
    private JButton addButton;
    private JButton deleteButton;

    private AirViaLtd app;

    public CommissionPage(AirViaLtd a) {
        this.app = a;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
