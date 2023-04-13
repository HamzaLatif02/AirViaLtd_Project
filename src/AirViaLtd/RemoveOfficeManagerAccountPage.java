package AirViaLtd;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;

public class RemoveOfficeManagerAccountPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel functionPanel;
    private JLabel removeOfficeManagerAccountLabel;
    private JComboBox officeManagerComboBox;
    private JLabel selectOfficeManagerLabel;
    private JLabel selectedOfficeManagerDetailsLabel;
    private JTextField IDTextField;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField emailAddressTextField;
    private JTextField passwordTextField;
    private JButton removeButton;
    private JPanel menuPanel;
    private JButton homeButton;
    private JButton backButton;

    private int selectedID;

    private ImageIcon homeIcon;
    private ImageIcon backIcon;

    private AirViaLtd app;

    //constructor
    public RemoveOfficeManagerAccountPage(AirViaLtd a) {
        this.app = a;
        setGraphics();
        addMenuButtonsListener();
        addOfficeManagers();
        addOfficeManagerComboBoxListener();
        addRemoveButtonListener();
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

        IDTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        firstNameTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        lastNameTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        emailAddressTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        passwordTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));

        removeButton.setPreferredSize(new Dimension(250, 50));
        removeButton.setBorder(new LineBorder(Color.WHITE, 1));

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    //add functionality to menu buttons
    public void addMenuButtonsListener(){
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToHomepage();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToManageOfficeManagerPage();
            }
        });
    }

    public void addOfficeManagers(){

        officeManagerComboBox.addItem("-- Select --");

        Connection con = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");
            con.setAutoCommit(false);

            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select ID, FirstName, LastName FROM in2018g16.OfficeManager");
            con.commit();

            while (rs.next()){
                officeManagerComboBox.addItem(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3));
            }

        } catch (Exception e) {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException x) {
                throw new RuntimeException(x);
            }
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    public void addOfficeManagerComboBoxListener(){
        officeManagerComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (officeManagerComboBox.getSelectedIndex() != 0){
                    addOfficeManagerData();
                } else {
                    IDTextField.setText("");
                    firstNameTextField.setText("");
                    lastNameTextField.setText("");
                    emailAddressTextField.setText("");
                    passwordTextField.setText("");
                }
            }
        });
    }

    //add details of selected office manager
    public void addOfficeManagerData(){

        String om = (String) officeManagerComboBox.getSelectedItem();
        String[] splitOMSelected = om.split("\\s+");
        selectedID = Integer.valueOf(splitOMSelected[0]);

        Connection con = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");
            con.setAutoCommit(false);

            String sql = "select * FROM in2018g16.OfficeManager where ID = ?";
            PreparedStatement stmt=con.prepareStatement(sql);

            stmt.setInt(1, selectedID);

            ResultSet rs=stmt.executeQuery();

            con.commit();

            while (rs.next()){

                IDTextField.setText("" + rs.getInt(1));
                firstNameTextField.setText(rs.getString(2));
                lastNameTextField.setText(rs.getString(3));
                emailAddressTextField.setText(rs.getString(4));
                passwordTextField.setText(rs.getString(5));
            }

        } catch (Exception e) {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException x) {
                throw new RuntimeException(x);
            }
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    //remove selected office manager from database
    public void addRemoveButtonListener(){
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (officeManagerComboBox.getSelectedIndex() != 0){
                    int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this office manager?", "Confirm", JOptionPane.YES_NO_OPTION);

                    if (reply == JOptionPane.YES_OPTION) {

                        Connection con = null;

                        try{
                            Class.forName("com.mysql.jdbc.Driver");
                            con= DriverManager.getConnection(
                                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                    "in2018g16_d",
                                    "35cnYJLB");
                            con.setAutoCommit(false);

                            String sql = "delete from in2018g16.OfficeManager WHERE ID = ?";

                            PreparedStatement stmt = con.prepareStatement(sql);

                            stmt.setInt(1, selectedID);

                            int rs = stmt.executeUpdate();

                            con.commit();

                            if (rs != 0){
                                JOptionPane.showMessageDialog(null, rs + " office manager deleted", "Successful Update", JOptionPane.INFORMATION_MESSAGE);
                                officeManagerComboBox.setSelectedIndex(0);
                            } else {
                                JOptionPane.showMessageDialog(null, "No office manager was deleted, please retry", "Unsuccessful Update", JOptionPane.ERROR_MESSAGE);
                            }

                        }catch(Exception ex){
                            System.out.println(ex);
                            try {
                                con.rollback();
                            } catch (SQLException x) {
                                throw new RuntimeException(x);
                            }
                        } finally {
                            try {
                                con.setAutoCommit(true);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        }

                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Please select an office manager", "Invalid Entry", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

}
