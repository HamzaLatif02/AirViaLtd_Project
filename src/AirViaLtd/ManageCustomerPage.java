package AirViaLtd;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
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
    private JPanel menuPanel;
    private JButton homeButton;
    private JButton backButton;

    private ImageIcon homeIcon;
    private ImageIcon backIcon;
    private AirViaLtd app;

    public ManageCustomerPage(AirViaLtd a) {
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

        createCustomerAccountButton.setPreferredSize(new Dimension(250, 50));
        createCustomerAccountButton.setBorder(new LineBorder(Color.WHITE, 1));
        editCustomerAccountButton.setPreferredSize(new Dimension(250, 50));
        editCustomerAccountButton.setBorder(new LineBorder(Color.WHITE, 1));
        removeCustomerAccountButton.setPreferredSize(new Dimension(250, 50));
        removeCustomerAccountButton.setBorder(new LineBorder(Color.WHITE, 1));

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
                if (app.getCurrentHomepage().equals(app.getAdministratorHomePage().getMainPanel())){
                    app.transitionToManageUsersPage();
                } else if (app.getCurrentHomepage().equals(app.getOfficeManagerHomePage().getMainPanel())){
                    app.transitionToHomepage();
                }
            }
        });
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
