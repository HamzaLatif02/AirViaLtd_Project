package AirViaLtd;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TravelAdvisorHomePage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel buttonsPanel;
    private JLabel homepageLabel;
    private JButton createCustomerAccountButton;
    private JButton issueRefundButton;
    private JButton createReportButton;
    private JButton sellTicketButton;
    private JPanel menuPanel;
    private JButton logOutButton;

    private ImageIcon logoutIcon;

    private AirViaLtd app;

    //constructor
    public TravelAdvisorHomePage(AirViaLtd a) {
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

        sellTicketButton.setPreferredSize(new Dimension(250, 50));
        sellTicketButton.setBorder(new LineBorder(Color.WHITE, 1));
        issueRefundButton.setPreferredSize(new Dimension(250, 50));
        issueRefundButton.setBorder(new LineBorder(Color.WHITE, 1));
        createReportButton.setPreferredSize(new Dimension(250, 50));
        createReportButton.setBorder(new LineBorder(Color.WHITE, 1));
        createCustomerAccountButton.setPreferredSize(new Dimension(250, 50));
        createCustomerAccountButton.setBorder(new LineBorder(Color.WHITE, 1));

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

        createCustomerAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToCreateCustomerAccountPage();
            }
        });

        issueRefundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToIssueRefundPage();
            }
        });

        createReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToCreateReportPage();
            }
        });

        sellTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToSellTicketPage();
            }
        });
    }
}

