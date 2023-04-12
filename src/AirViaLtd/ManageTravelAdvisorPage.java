package AirViaLtd;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
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

    private ImageIcon homeIcon;
    private ImageIcon backIcon;

    private AirViaLtd app;

    public ManageTravelAdvisorPage(AirViaLtd a) {
        this.app = a;
        setGraphics();
        addButtonsListener();
    }

    public void setGraphics(){

        homeIcon = new ImageIcon("data/home.png");
        homeButton.setPreferredSize(new Dimension(100, 30));
        homeButton.setBorderPainted(false);
        homeButton.setIcon(homeIcon);

        backIcon = new ImageIcon("data/back.png");
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.setBorderPainted(false);
        backButton.setIcon(backIcon);

        createTravelAdvisorAccountButton.setPreferredSize(new Dimension(250, 50));
        createTravelAdvisorAccountButton.setBorder(new LineBorder(Color.WHITE, 1));
        editTravelAdvisorAccountButton.setPreferredSize(new Dimension(250, 50));
        editTravelAdvisorAccountButton.setBorder(new LineBorder(Color.WHITE, 1));
        removeTravelAdvisorAccountButton.setPreferredSize(new Dimension(250, 50));
        removeTravelAdvisorAccountButton.setBorder(new LineBorder(Color.WHITE, 1));

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
                app.transitionToManageUsersPage();
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
