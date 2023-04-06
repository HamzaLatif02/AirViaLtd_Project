package AirViaLtd;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageUsersPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel buttonsPanel;
    private JLabel manageUsers;
    private JButton travelAdvisorButton;
    private JButton customerButton;
    private JButton officeManagerButton;

    private AirViaLtd app;

    public ManageUsersPage(AirViaLtd a) {
        this.app = a;
        addButtonsListener();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void addButtonsListener(){
        officeManagerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToManageOfficeManagerPage();
            }
        });

        travelAdvisorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToManageTravelAdvisorPage();
            }
        });


        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToManageCustomerPage();
            }
        });
    }
}
