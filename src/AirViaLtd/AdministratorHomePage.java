package AirViaLtd;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdministratorHomePage {
    private JPanel mainPanel;
    private JButton createTravelAgentAccountButton;
    private JButton securityButton;
    private JButton commissionButton;
    private JButton blankStockButton;
    private JButton ticketStockButton;
    private JButton updateDetailsButton;

    private AirViaLtd app;

    public AdministratorHomePage(AirViaLtd a) {

        this.app = a;

        createTravelAgentAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToCreateTravelAgentAccountPage();
            }
        });

        securityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToSecurityPage();
            }
        });

        commissionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToCommissionPage();
            }
        });

        blankStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToBlankStockPage();
            }
        });

        ticketStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToTicketStockPage();
            }
        });

        updateDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToUpdateDetailsPage();
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}

