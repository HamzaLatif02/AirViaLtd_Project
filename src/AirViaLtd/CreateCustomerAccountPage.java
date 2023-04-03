package AirViaLtd;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class CreateCustomerAccountPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel featurePanel;
    private JLabel createCustomerAccountLabel;
    private JTextField nameTextField;
    private JTextField surnameTextField;
    private JTextField emailAddressTextField;
    private JButton createButton;
    private JComboBox regularValuedComboBox;

    private AirViaLtd app;

    public CreateCustomerAccountPage(AirViaLtd a) {
        this.app = a;

        addNameTextListener();
        addSurnameTextListener();
        addEmailTextListener();
        addRegularValuedComboBoxData();
        addCreateButtonListener();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void addCreateButtonListener(){
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (checkInputData()){
                    try{
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con= DriverManager.getConnection(
                                "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                "in2018g16_d",
                                "35cnYJLB");



                        String sql = "insert into in2018g16.Customer (EmailAddress, FirstName, LastName, Type, FixedDiscount, FlexibleDiscount) values (?, ?, ?, ?, null, null)";

                        PreparedStatement stmt= con.prepareStatement(sql);

                        stmt.setString(1, emailAddressTextField.getText().toString());
                        stmt.setString(2, nameTextField.getText().toString());
                        stmt.setString(3, surnameTextField.getText().toString());
                        stmt.setString(4, regularValuedComboBox.getSelectedItem().toString());

                        int rs=stmt.executeUpdate();

                        if (rs != 0){
                            JOptionPane.showMessageDialog(null, "Customer account created", "Successful Update", JOptionPane.INFORMATION_MESSAGE);
                            regularValuedComboBox.setSelectedIndex(0);
                            emailAddressTextField.setText("Email Address");
                            nameTextField.setText("Name");
                            surnameTextField.setText("Surname");
                        } else {
                            JOptionPane.showMessageDialog(null, "Could not create customer account, please retry", "Unsuccessful Update", JOptionPane.ERROR_MESSAGE);
                        }


                        con.close();

                    }catch(Exception x){ System.out.println(x);}
                }
            }
        });
    }

    public void addNameTextListener(){
        nameTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                if (nameTextField.getText().equals("Name")){
                    nameTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                if (nameTextField.getText().equals("")){
                    nameTextField.setText("Name");
                }
            }
        });
    }

    public void addSurnameTextListener(){
        surnameTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                if (surnameTextField.getText().equals("Surname")){
                    surnameTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                if (surnameTextField.getText().equals("")){
                    surnameTextField.setText("Surname");
                }
            }
        });
    }

    public void addEmailTextListener(){
        emailAddressTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                if (emailAddressTextField.getText().equals("Email Address")){
                    emailAddressTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                if (emailAddressTextField.getText().equals("")){
                    emailAddressTextField.setText("Email Address");
                }
            }
        });
    }

    public void addRegularValuedComboBoxData(){
        regularValuedComboBox.addItem("-- Regular Or Valued --");
        regularValuedComboBox.addItem("Regular");
        regularValuedComboBox.addItem("Valued");
    }

    public boolean checkInputData(){

        if (nameTextField.getText().toString().equals("Name") || nameTextField.getText().toString().equals("")){
            JOptionPane.showMessageDialog(null, "Please enter a name", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (surnameTextField.getText().toString().equals("Surname") || surnameTextField.getText().toString().equals("")){
            JOptionPane.showMessageDialog(null, "Please enter a surname", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (emailAddressTextField.getText().toString().equals("Email Address") || emailAddressTextField.getText().toString().equals("")){
            JOptionPane.showMessageDialog(null, "Please enter an email address", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (regularValuedComboBox.getSelectedIndex() == 0){
            JOptionPane.showMessageDialog(null, "Please select a type", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}
