package AirViaLtd;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlankManagerPage {
    private JPanel mainPanel;
    private JPanel menuPanel;
    private JButton logoutButton;
    private JLabel manageBlankLabel;
    private JButton addBlankButton;
    private JButton removeBlankButton;
    private JButton editBlankButton;
    private JButton allocateBlankButton;
    private JButton reallocateBlankButton;
    private JPanel titlePanel;
    private JPanel buttonsPanel;

    private AirViaLtd app;

    public BlankManagerPage(AirViaLtd a) {
        this.app = a;

        addButtonsListener();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void addButtonsListener(){

        addBlankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToAddBlankPage();
            }
        });

        removeBlankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToRemoveBlankPage();
            }
        });

        /*editBlankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToEditBlankPage();
            }
        });*/

        allocateBlankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToAllocateBlankPage();
            }
        });


        /*realloctaeBlankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToReallocateBlankPage();
            }
        });*/

    }
}
