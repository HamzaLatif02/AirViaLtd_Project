package AirViaLtd;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
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
    private JPanel menuPanel;
    private JButton homeButton;
    private JButton backButton;

    private ImageIcon homeIcon;
    private ImageIcon backIcon;

    private AirViaLtd app;

    public ManageUsersPage(AirViaLtd a) {
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

        officeManagerButton.setPreferredSize(new Dimension(250, 50));
        officeManagerButton.setBorder(new LineBorder(Color.WHITE, 1));
        travelAdvisorButton.setPreferredSize(new Dimension(250, 50));
        travelAdvisorButton.setBorder(new LineBorder(Color.WHITE, 1));
        customerButton.setPreferredSize(new Dimension(250, 50));
        customerButton.setBorder(new LineBorder(Color.WHITE, 1));

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
                app.transitionToHomepage();
            }
        });

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
