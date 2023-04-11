package AirViaLtd;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;

public class EditOfficeManagerAccountPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel functionPanel;
    private JLabel editOfficeManagerAccount;
    private JComboBox officeManagerComboBox;
    private JTextField IDTextField;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField emailAddressTextField;
    private JTextField passwordTextField;
    private JButton editButton;
    private JLabel selectOfficeManagerLabel;
    private JLabel selectedOfficeManagerDetailsLabel;
    private JPanel menuPanel;
    private JButton homeButton;
    private JButton backButton;

    private int selectedID;

    private AirViaLtd app;

    public EditOfficeManagerAccountPage(AirViaLtd a) {
        this.app = a;
        addMenuButtonsListener();
        addOfficeManagers();
        addOfficeManagerComboBoxListener();
        addEditButtonListener();
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
                app.transitionToManageOfficeManagerPage();
            }
        });
    }

    public void addOfficeManagers(){

        officeManagerComboBox.addItem("-- Select --");
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");

            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select ID, FirstName, LastName FROM in2018g16.OfficeManager");

            while (rs.next()){
                officeManagerComboBox.addItem(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3));
            }
            con.close();
        } catch (Exception e) { System.out.println(e);}

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

    public void addOfficeManagerData(){

        String om = (String) officeManagerComboBox.getSelectedItem();
        String[] splitOMSelected = om.split("\\s+");
        selectedID = Integer.valueOf(splitOMSelected[0]);

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");

            String sql = "select * FROM in2018g16.OfficeManager where ID = ?";
            PreparedStatement stmt=con.prepareStatement(sql);

            stmt.setInt(1, selectedID);

            ResultSet rs=stmt.executeQuery();

            while (rs.next()){

                IDTextField.setText("" + rs.getInt(1));
                firstNameTextField.setText(rs.getString(2));
                lastNameTextField.setText(rs.getString(3));
                emailAddressTextField.setText(rs.getString(4));
                passwordTextField.setText(rs.getString(5));
            }

            con.close();
        } catch (Exception e) { System.out.println(e);}
    }

    public void addEditButtonListener(){
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (officeManagerComboBox.getSelectedIndex() != 0){
                    int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to edit this office manager?", "Confirm", JOptionPane.YES_NO_OPTION);

                    if (reply == JOptionPane.YES_OPTION) {



                        try{
                            Class.forName("com.mysql.jdbc.Driver");
                            Connection con= DriverManager.getConnection(
                                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                    "in2018g16_d",
                                    "35cnYJLB");

                            String sql = "update in2018g16.OfficeManager Set ID = ?, FirstName = ?, LastName = ?, EmailAddress = ?, Password = ? WHERE ID = ?";

                            PreparedStatement stmt = con.prepareStatement(sql);

                            stmt.setInt(1, Integer.valueOf(IDTextField.getText()));
                            stmt.setString(2, firstNameTextField.getText());
                            stmt.setString(3, lastNameTextField.getText());
                            stmt.setString(4, emailAddressTextField.getText());
                            stmt.setString(5, passwordTextField.getText());
                            stmt.setInt(6, selectedID);

                            int rs = stmt.executeUpdate();

                            if (rs != 0){
                                JOptionPane.showMessageDialog(null, rs + " office manager updated", "Successful Update", JOptionPane.INFORMATION_MESSAGE);
                                officeManagerComboBox.setSelectedIndex(0);
                            } else {
                                JOptionPane.showMessageDialog(null, "No office manager was updated, please retry", "Unsuccessful Update", JOptionPane.ERROR_MESSAGE);
                            }

                            con.close();

                        }catch(Exception ex){ System.out.println(ex);}

                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Please select an office manager", "Invalid Entry", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
