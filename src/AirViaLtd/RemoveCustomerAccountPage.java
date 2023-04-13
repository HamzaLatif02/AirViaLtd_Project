package AirViaLtd;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;

public class RemoveCustomerAccountPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel featurePanel;
    private JLabel removeCustomerLabel;
    private JLabel selectCustomerLabel;
    private JTextField emailAddressTextField;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField typeTextField;
    private JTextField fixedDiscountTextField;
    private JTextField flexibleDiscountTextField;
    private JButton removeButton;
    private JLabel selectedCustomerDetailsLabel;
    private JComboBox customerComboBox;
    private JButton homeButton;
    private JButton backButton;
    private JPanel menuPanel;
    private String selectedEmail;

    private ImageIcon homeIcon;
    private ImageIcon backIcon;

    private AirViaLtd app;

    //constructor
    public RemoveCustomerAccountPage(AirViaLtd a) {
        this.app = a;
        setGraphics();
        addMenuButtonsListener();
        addCustomers();
        addCustomerComboBoxListener();
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

        emailAddressTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        firstNameTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        lastNameTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        typeTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        flexibleDiscountTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        fixedDiscountTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));

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
                app.transitionToManageCustomerPage();
            }
        });
    }

    public void addCustomers(){

        customerComboBox.addItem("-- Select --");

        Connection con = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");
            con.setAutoCommit(false);

            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select EmailAddress, FirstName, LastName FROM in2018g16.Customer");

            con.commit();

            while (rs.next()){
                customerComboBox.addItem(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3));
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

    public void addCustomerComboBoxListener(){
        customerComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (customerComboBox.getSelectedIndex() != 0){
                    addCustomerData();
                }
            }
        });
    }

    //add details of selected blanks
    public void addCustomerData(){

        String c = (String) customerComboBox.getSelectedItem();
        String[] splitCSelected = c.split("\\s+");
        selectedEmail = splitCSelected[0];

        Connection con = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");
            con.setAutoCommit(false);

            String sql = "select * FROM in2018g16.Customer where EmailAddress = ?";
            PreparedStatement stmt=con.prepareStatement(sql);

            stmt.setString(1, selectedEmail);

            ResultSet rs=stmt.executeQuery();

            con.commit();

            while (rs.next()){
                emailAddressTextField.setText(rs.getString(1));
                firstNameTextField.setText(rs.getString(2));
                lastNameTextField.setText(rs.getString(3));
                typeTextField.setText(rs.getString(4));
                fixedDiscountTextField.setText("" + rs.getInt(5));
                flexibleDiscountTextField.setText("" + rs.getInt(6));
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

    //remove selected customer from database
    public void addRemoveButtonListener(){
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (customerComboBox.getSelectedIndex() != 0){
                    int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this customer?", "Confirm", JOptionPane.YES_NO_OPTION);

                    if (reply == JOptionPane.YES_OPTION) {

                        Connection con = null;

                        try{
                            Class.forName("com.mysql.jdbc.Driver");
                            con= DriverManager.getConnection(
                                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                    "in2018g16_d",
                                    "35cnYJLB");
                            con.setAutoCommit(false);

                            String sql = "delete from in2018g16.Customer where EmailAddress = ? ";

                            PreparedStatement stmt = con.prepareStatement(sql);

                            stmt.setString(1, selectedEmail);

                            int rs = stmt.executeUpdate();

                            con.commit();

                            if (rs != 0){
                                JOptionPane.showMessageDialog(null, rs + " customer deleted", "Successful Update", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "No customer was deleted, please retry", "Unsuccessful Update", JOptionPane.ERROR_MESSAGE);
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

                        updateData();
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Please select a customer", "Invalid Entry", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }

    //update text fields after removing
    public void updateData(){
        customerComboBox.removeItem(customerComboBox.getSelectedItem());
        emailAddressTextField.setText("");
        firstNameTextField.setText("");
        lastNameTextField.setText("");
        typeTextField.setText("");
        fixedDiscountTextField.setText("");
        flexibleDiscountTextField.setText("");
    }
}
