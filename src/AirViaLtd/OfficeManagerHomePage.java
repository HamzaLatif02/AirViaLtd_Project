package AirViaLtd;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OfficeManagerHomePage {
    private JPanel mainPanel;
    private JPanel buttonsPanel;
    private JButton allocateBlankButton;
    private JButton addBlankButton;
    private JButton blankStockButton;
    private JButton createReportButton;
    private JButton discountPlanButton;
    private JButton statusButton;
    private JPanel titlePanel;
    private JLabel homepageLabel;

    private AirViaLtd app;

    public OfficeManagerHomePage(AirViaLtd a) {

        this.app = a;

        allocateBlankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToAllocateBlankPage();
            }
        });

        addBlankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToAddBlankPage();
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
