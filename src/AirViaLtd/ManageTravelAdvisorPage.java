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
    private JPanel menuPanel;
    private JButton homeButton;
    private JButton backButton;

    private AirViaLtd app;

    public ManageTravelAdvisorPage(AirViaLtd a) {
        this.app = a;
        addButtonsListener();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void addButtonsListener(){

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToHomepage();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

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
