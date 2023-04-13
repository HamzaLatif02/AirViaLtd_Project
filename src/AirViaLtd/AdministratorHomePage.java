package AirViaLtd;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
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

    private ImageIcon logoutIcon;

    private AirViaLtd app;

    //constructor
    public AdministratorHomePage(AirViaLtd a) {

        this.app = a;


        setGraphics();
        addButtonsListener();

    }

    //set page graphics
    public void setGraphics(){

        logoutIcon = new ImageIcon("data/logout.png");
        logOutButton.setPreferredSize(new Dimension(100, 30));
        logOutButton.setBorderPainted(false);
        logOutButton.setIcon(logoutIcon);

        homepageLabel.setPreferredSize(new Dimension(500, 100));
        homepageLabel.setBorder(new LineBorder(Color.WHITE, 1));

        manageUsersButton.setPreferredSize(new Dimension(250, 50));
        manageUsersButton.setBorder(new LineBorder(Color.WHITE, 1));
        blankStockButton.setPreferredSize(new Dimension(250, 50));
        blankStockButton.setBorder(new LineBorder(Color.WHITE, 1));
        commissionButton.setPreferredSize(new Dimension(250, 50));
        commissionButton.setBorder(new LineBorder(Color.WHITE, 1));
        securityButton.setPreferredSize(new Dimension(250, 50));
        securityButton.setBorder(new LineBorder(Color.WHITE, 1));

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    //add functionality to all buttons
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


