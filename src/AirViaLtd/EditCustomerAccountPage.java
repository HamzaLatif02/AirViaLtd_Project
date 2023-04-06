package AirViaLtd;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;

public class EditCustomerAccountPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel functionPanel;
    private JLabel editCustomerLabel;
    private JLabel selectCustomerLabel;
    private JLabel selectedCustomerDetailsLabel;
    private JTextField emailAddressTextField;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField fixedDiscountTextField;
    private JTextField flexibleDiscountTextField;
    private JComboBox customerComboBox;
    private JButton editButton;
    private JComboBox typeComboBox;

    private String selectedEmail;

    private AirViaLtd app;

    public EditCustomerAccountPage(AirViaLtd a) {
        this.app = a;
        addCustomers();
        addCustomerComboBoxListener();
        addEditButtonListener();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }


    public void addCustomers(){

        customerComboBox.addItem("-- Select --");
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");

            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select EmailAddress, FirstName, LastName FROM in2018g16.Customer");

            while (rs.next()){
                customerComboBox.addItem(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3));
            }
            con.close();
        } catch (Exception e) { System.out.println(e);}

        typeComboBox.addItem("Regular");
        typeComboBox.addItem("Valued");
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
    public void addCustomerData(){

        String c = (String) customerComboBox.getSelectedItem();
        String[] splitCSelected = c.split("\\s+");
        selectedEmail = splitCSelected[0];

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");

            String sql = "select * FROM in2018g16.Customer where EmailAddress = ?";
            PreparedStatement stmt=con.prepareStatement(sql);

            stmt.setString(1, selectedEmail);

            ResultSet rs=stmt.executeQuery();

            while (rs.next()){
                emailAddressTextField.setText(rs.getString(1));
                firstNameTextField.setText(rs.getString(2));
                lastNameTextField.setText(rs.getString(3));

                if (rs.getString(4).equals("Regular")){
                    typeComboBox.setSelectedIndex(0);
                } else {
                    typeComboBox.setSelectedIndex(1);
                }

                fixedDiscountTextField.setText("" + rs.getInt(5));
                flexibleDiscountTextField.setText("" + rs.getInt(6));
            }

            con.close();
        } catch (Exception e) { System.out.println(e);}
    }

    public void addEditButtonListener(){
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (customerComboBox.getSelectedIndex() != 0){
                    int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to edit this customer?", "Confirm", JOptionPane.YES_NO_OPTION);

                    if (reply == JOptionPane.YES_OPTION) {



                        try{
                            Class.forName("com.mysql.jdbc.Driver");
                            Connection con= DriverManager.getConnection(
                                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                    "in2018g16_d",
                                    "35cnYJLB");

                            String sql = "update in2018g16.Customer Set EmailAddress = ?, FirstName = ?, LastName = ?, Type = ?, FixedDiscount = ?, FlexibleDiscount = ? WHERE EmailAddress = ?";

                            PreparedStatement stmt = con.prepareStatement(sql);

                            stmt.setString(1, emailAddressTextField.getText());
                            stmt.setString(2, firstNameTextField.getText());
                            stmt.setString(3, lastNameTextField.getText());
                            stmt.setString(4, typeComboBox.getSelectedItem().toString());
                            stmt.setInt(5, Integer.valueOf(fixedDiscountTextField.getText()));
                            stmt.setInt(6, Integer.valueOf(flexibleDiscountTextField.getText()));
                            stmt.setString(7, selectedEmail);

                            int rs = stmt.executeUpdate();

                            if (rs != 0){
                                JOptionPane.showMessageDialog(null, rs + " customer updated", "Successful Update", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "No customer was updated, please retry", "Unsuccessful Update", JOptionPane.ERROR_MESSAGE);
                            }

                            con.close();

                        }catch(Exception ex){ System.out.println(ex);}

                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Please select a customer", "Invalid Entry", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

}
