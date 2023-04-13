package AirViaLtd;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CommissionPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel functionPanel;
    private JLabel commissionLabel;
    private JTextField commissionRateTextField;
    private JButton addButton;
    private JButton deleteButton;
    private JComboBox commissionComboBox;
    private JPanel menuPanel;
    private JButton homeButton;
    private JButton backButton;
    private ImageIcon homeIcon;
    private ImageIcon backIcon;

    private AirViaLtd app;

    //constructor
    public CommissionPage(AirViaLtd a) {
        this.app = a;
        setGraphics();
        addMenuButtonsListener();
        addCommissionRates();
        addAddButtonListener();
        addDeleteButtonListener();

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

        commissionRateTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));

        addButton.setPreferredSize(new Dimension(250, 50));
        addButton.setBorder(new LineBorder(Color.WHITE, 1));
        deleteButton.setPreferredSize(new Dimension(250, 50));
        deleteButton.setBorder(new LineBorder(Color.WHITE, 1));

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
                app.transitionToHomepage();
            }
        });
    }

    public void addCommissionRates(){
        commissionComboBox.addItem("-- Select a Commission --");

        Connection con = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");
            con.setAutoCommit(false);

            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select CommissionRate FROM in2018g16.Commission where TravelAgentID = 1");
            con.commit();

            while (rs.next()){
                commissionComboBox.addItem(rs.getInt(1));
            }

        } catch (Exception e) {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    //add input commission to databse if valid
    public void addAddButtonListener(){
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (validInput()) {

                    Connection con = null;

                    try{
                        Class.forName("com.mysql.jdbc.Driver");
                        con= DriverManager.getConnection(
                                "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                "in2018g16_d",
                                "35cnYJLB");
                        con.setAutoCommit(false);

                        String sql = "insert into in2018g16.Commission values (?, 1)";
                        PreparedStatement stmt=con.prepareStatement(sql);

                        stmt.setInt(1, Integer.valueOf(commissionRateTextField.getText()));

                        int rs=stmt.executeUpdate();
                        con.commit();

                        if (rs != 0 ){
                            JOptionPane.showMessageDialog(null, "Commission Rate Added", "Success", JOptionPane.INFORMATION_MESSAGE);
                            commissionRateTextField.setText("");
                            commissionComboBox.removeAllItems();
                            addCommissionRates();
                        } else {
                            JOptionPane.showMessageDialog(null, "Could not add commission rate, please retry", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (Exception x) {
                        System.out.println(x);
                        try {
                            con.rollback();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    } finally {
                        try {
                            con.setAutoCommit(true);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }

            }
        });
    }

    //check that the values entered is between 0 and 100
    public boolean validInput(){
        if (Integer.valueOf(commissionRateTextField.getText()) > 0 && Integer.valueOf(commissionRateTextField.getText()) < 101){
               return true;
        }
        JOptionPane.showMessageDialog(null, "Value must be between 1 and 100", "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    //remove the selected commission from the blank
    public void addDeleteButtonListener(){
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (commissionComboBox.getSelectedIndex() != 0){
                    int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this commission?", "Confirm", JOptionPane.YES_NO_OPTION);

                    if (reply == JOptionPane.YES_OPTION) {

                        Connection con = null;

                        try{
                            Class.forName("com.mysql.jdbc.Driver");
                            con= DriverManager.getConnection(
                                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                    "in2018g16_d",
                                    "35cnYJLB");
                            con.setAutoCommit(false);

                            String sql = "delete from in2018g16.Commission where CommissionRate = ? ";

                            PreparedStatement stmt = con.prepareStatement(sql);

                            stmt.setInt(1, Integer.valueOf(commissionComboBox.getSelectedItem().toString()));

                            int rs = stmt.executeUpdate();
                            con.commit();

                            if (rs != 0){
                                JOptionPane.showMessageDialog(null, rs + " commission deleted", "Successful Update", JOptionPane.INFORMATION_MESSAGE);
                                commissionComboBox.removeItem(commissionComboBox.getSelectedItem());
                            } else {
                                JOptionPane.showMessageDialog(null, "No commission was deleted, please retry", "Unsuccessful Update", JOptionPane.ERROR_MESSAGE);
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
                    JOptionPane.showMessageDialog(null, "Please select a customer", "Invalid Entry", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }


}
