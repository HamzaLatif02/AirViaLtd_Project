package AirViaLtd;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        createCustomerAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToCreateCustomerAccountPage();
            }
        });

        issueRefundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToIssueRefundPage();
            }
        });

        createReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToCreateReportPage();
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
