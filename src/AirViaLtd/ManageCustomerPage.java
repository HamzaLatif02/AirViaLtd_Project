package AirViaLtd;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageCustomerPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel buttonsPanel;
    private JLabel manageCustomerLabel;
    private JButton createCustomerAccountButton;
    private JButton editCustomerAccountButton;
    private JButton removeCustomerAccountButton;
    private AirViaLtd app;

    public ManageCustomerPage(AirViaLtd a) {
        this.app = a;
        addButtonsListener();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void addButtonsListener(){
        createCustomerAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToCreateCustomerAccountPage();
            }
        });

        editCustomerAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToEditCustomerAccountPage();
            }
        });

        removeCustomerAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToRemoveCustomerAccountPage();
            }
        });
    }
}
