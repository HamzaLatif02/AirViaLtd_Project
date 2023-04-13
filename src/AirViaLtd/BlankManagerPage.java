package AirViaLtd;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlankManagerPage {
    private JPanel mainPanel;
    private JPanel menuPanel;
    private JLabel manageBlankLabel;
    private JButton addBlankButton;
    private JButton removeBlankButton;
    private JButton editBlankButton;
    private JButton allocateBlankButton;
    private JButton reallocateBlankButton;
    private JPanel titlePanel;
    private JPanel buttonsPanel;
    private JButton homeButton;
    private JButton backButton;

    private ImageIcon homeIcon;
    private ImageIcon backIcon;
    private AirViaLtd app;

    //constructor
    public BlankManagerPage(AirViaLtd a) {
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


        addBlankButton.setPreferredSize(new Dimension(250, 50));
        addBlankButton.setBorder(new LineBorder(Color.WHITE, 1));
        editBlankButton.setPreferredSize(new Dimension(250, 50));
        editBlankButton.setBorder(new LineBorder(Color.WHITE, 1));
        removeBlankButton.setPreferredSize(new Dimension(250, 50));
        removeBlankButton.setBorder(new LineBorder(Color.WHITE, 1));
        allocateBlankButton.setPreferredSize(new Dimension(250, 50));
        allocateBlankButton.setBorder(new LineBorder(Color.WHITE, 1));
        reallocateBlankButton.setPreferredSize(new Dimension(250, 50));
        reallocateBlankButton.setBorder(new LineBorder(Color.WHITE, 1));

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
                app.transitionToHomepage();
            }
        });

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

        editBlankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToEditBlankPage();
            }
        });

        allocateBlankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToAllocateBlankPage();
            }
        });


        reallocateBlankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToReallocateBlankPage();
            }
        });

    }
}
