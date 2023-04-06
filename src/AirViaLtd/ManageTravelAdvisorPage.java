package AirViaLtd;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageTravelAdvisorPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel buttonsPanel;
    private JLabel manageTravelAdvisor;
    private JButton createTravelAdvisorAccountButton;
    private JButton editTravelAdvisorAccountButton;
    private JButton removeTravelAdvisorAccountButton;

    private AirViaLtd app;

    public ManageTravelAdvisorPage(AirViaLtd a) {
        this.app = a;
        addButtonsListener();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void addButtonsListener(){
        createTravelAdvisorAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToCreateTravelAdvisorAccountPage();
            }
        });

        editTravelAdvisorAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToEditTravelAdvisorAccountPage();
            }
        });

        removeTravelAdvisorAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToRemoveTravelAdvisorAccountPage();
            }
        });


    }
}
