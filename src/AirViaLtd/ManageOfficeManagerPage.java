package AirViaLtd;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
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
    private JPanel menuPanel;
    private JButton homeButton;
    private JButton backButton;


    private ImageIcon homeIcon;
    private ImageIcon backIcon;

    private AirViaLtd app;

    //constructor
    public ManageOfficeManagerPage(AirViaLtd a) {
        this.app = a;
        setGraphics();
        addButtonsListener();
    }

    //set page graphics
    public void setGraphics(){

        homeIcon = new ImageIcon("data/home.png");
        homeButton.setPreferredSize(new Dimension(100, 30));
        homeButton.setBorderPainted(false);
        homeButton.setIcon(homeIcon);

        backIcon = new ImageIcon("data/back.png");
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.setBorderPainted(false);
        backButton.setIcon(backIcon);

        createOfficeManagerAccountButton.setPreferredSize(new Dimension(250, 50));
        createOfficeManagerAccountButton.setBorder(new LineBorder(Color.WHITE, 1));
        editOfficeManagerAccountButton.setPreferredSize(new Dimension(250, 50));
        editOfficeManagerAccountButton.setBorder(new LineBorder(Color.WHITE, 1));
        removeOfficeManagerAccountButton.setPreferredSize(new Dimension(250, 50));
        removeOfficeManagerAccountButton.setBorder(new LineBorder(Color.WHITE, 1));

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    //add functionality to all buttons
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
