package AirViaLtd;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OfficeManagerHomePage {
    private JPanel mainPanel;
    private JPanel buttonsPanel;
    private JButton blankStockButton;
    private JButton createReportButton;
    private JButton discountPlanButton;
    private JPanel titlePanel;
    private JLabel homepageLabel;
    private JPanel menuPanel;
    private JButton logOutButton;
    private JButton manageBlanksButton;
    private JButton searchBlankButton;
    private JButton manageCustomerButton;

    private ImageIcon logoutIcon;
    private AirViaLtd app;

    public OfficeManagerHomePage(AirViaLtd a) {

        this.app = a;

        setGraphics();
        addButtonsListener();
    }

    public void setGraphics(){

        logoutIcon = new ImageIcon("data/logout.png");
        logOutButton.setPreferredSize(new Dimension(100, 30));
        logOutButton.setBorderPainted(false);
        logOutButton.setIcon(logoutIcon);

        homepageLabel.setPreferredSize(new Dimension(500, 100));
        homepageLabel.setBorder(new LineBorder(Color.WHITE, 1));

        manageBlanksButton.setPreferredSize(new Dimension(250, 50));
        manageBlanksButton.setBorder(new LineBorder(Color.WHITE, 1));
        searchBlankButton.setPreferredSize(new Dimension(250, 50));
        searchBlankButton.setBorder(new LineBorder(Color.WHITE, 1));
        blankStockButton.setPreferredSize(new Dimension(250, 50));
        blankStockButton.setBorder(new LineBorder(Color.WHITE, 1));
        createReportButton.setPreferredSize(new Dimension(250, 50));
        createReportButton.setBorder(new LineBorder(Color.WHITE, 1));
        discountPlanButton.setPreferredSize(new Dimension(250, 50));
        discountPlanButton.setBorder(new LineBorder(Color.WHITE, 1));
        manageCustomerButton.setPreferredSize(new Dimension(250, 50));
        manageCustomerButton.setBorder(new LineBorder(Color.WHITE, 1));

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void addButtonsListener(){

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.doLogout();
            }
        });

        manageBlanksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToBlankManagerPage();
            }
        });

        searchBlankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToSearchBlankPage();
            }
        });


        blankStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToBlankStockPage();
            }
        });

        createReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToCreateReportPage();
            }
        });

        discountPlanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToDiscountPlanPage();
            }
        });

        manageCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToManageCustomerPage();
            }
        });

    }
}
