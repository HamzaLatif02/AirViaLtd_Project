package AirViaLtd;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageOfficeManagerPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel buttonsPanel;
    private JLabel manageOfficeManagerPage;
    private JButton createOfficeManagerAccountButton;
    private JButton editOfficeManagerAccountButton;
    private JButton removeOfficeManagerAccountButton;

    private AirViaLtd app;

    public ManageOfficeManagerPage(AirViaLtd a) {
        this.app = a;
        addButtonsListener();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void addButtonsListener(){
        createOfficeManagerAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToCreateOfficeManagerAccountPage();
            }
        });

        editOfficeManagerAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToEditOfficeManagerAccountPage();
            }
        });

        removeOfficeManagerAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToRemoveOfficeManagerAccountPage();
            }
        });
    }
}