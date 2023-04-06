package AirViaLtd;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdministratorHomePage {
    private JPanel mainPanel;
    private JPanel menuPanel;
    private JPanel titlePanel;
    private JPanel buttonsPanel;
    private JButton securityButton;
    private JButton commissionButton;
    private JButton blankStockButton;
    private JButton logOutButton;
    private JLabel homepageLabel;
    private JButton manageUsersButton;

    private AirViaLtd app;

    public AdministratorHomePage(AirViaLtd a) {

        this.app = a;

        addButtonsListener();

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void addButtonsListener(){
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.doLogout();
            }
        });

        manageUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToManageUsersPage();
            }
        });

        blankStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToBlankStockPage();
            }
        });

        commissionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToCommissionPage();
            }
        });

        securityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToSecurityPage();
            }
        });

    }
}


