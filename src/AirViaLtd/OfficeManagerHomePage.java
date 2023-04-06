package AirViaLtd;

import javax.swing.*;
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

    private AirViaLtd app;

    public OfficeManagerHomePage(AirViaLtd a) {

        this.app = a;

        addButtonsListener();
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
