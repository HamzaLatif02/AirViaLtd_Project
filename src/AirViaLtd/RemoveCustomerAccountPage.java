package AirViaLtd;

import javax.swing.*;
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

    private AirViaLtd app;

    public RemoveCustomerAccountPage(AirViaLtd a) {
        this.app = a;
        addMenuButtonsListener();
        addCustomers();
        addCustomerComboBoxListener();
        addRemoveButtonListener();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

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

            }
        });
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
                typeTextField.setText(rs.getString(4));
                fixedDiscountTextField.setText("" + rs.getInt(5));
                flexibleDiscountTextField.setText("" + rs.getInt(6));
            }

            con.close();
        } catch (Exception e) { System.out.println(e);}
    }

    public void addRemoveButtonListener(){
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (customerComboBox.getSelectedIndex() != 0){
                    int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this customer?", "Confirm", JOptionPane.YES_NO_OPTION);

                    if (reply == JOptionPane.YES_OPTION) {


                        try{
                            Class.forName("com.mysql.jdbc.Driver");
                            Connection con= DriverManager.getConnection(
                                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                    "in2018g16_d",
                                    "35cnYJLB");

                            String sql = "delete from in2018g16.Customer where EmailAddress = ? ";

                            PreparedStatement stmt = con.prepareStatement(sql);

                            stmt.setString(1, selectedEmail);

                            int rs = stmt.executeUpdate();

                            if (rs != 0){
                                JOptionPane.showMessageDialog(null, rs + " customer deleted", "Successful Update", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "No customer was deleted, please retry", "Unsuccessful Update", JOptionPane.ERROR_MESSAGE);
                            }

                            con.close();

                        }catch(Exception ex){ System.out.println(ex);}

                        updateData();
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Please select a customer", "Invalid Entry", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }

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
