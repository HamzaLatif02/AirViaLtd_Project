package AirViaLtd;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;

public class EditTravelAdvisorAccountPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel functionPanel;
    private JLabel editTravelAdvisorAccountLabel;
    private JComboBox travelAdvisorComboBox;
    private JTextField advisorCodeTextField;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField emailAddressTextField;
    private JTextField passwordTextField;
    private JTextField officeManagerIDTextField;
    private JButton editButton;
    private JLabel selectTravelAdvisorLabel;
    private JLabel selectedTravelAdvisorDetailsLabel;
    private JPanel menuPanel;
    private JButton homeButton;
    private JButton backButton;

    private int selectedAdvisorCode;

    private AirViaLtd app;

    public EditTravelAdvisorAccountPage(AirViaLtd a) {
        this.app = a;
        addMenuButtonsListener();
        addTravelAdvisors();
        addTravelAdvisorComboBoxListener();
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
                app.transitionToManageTravelAdvisorPage();
            }
        });
    }

    public void addTravelAdvisors(){

        travelAdvisorComboBox.addItem("-- Select --");
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");

            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select AdvisorCode, FirstName, LastName FROM in2018g16.TravelAdvisor");

            while (rs.next()){
                travelAdvisorComboBox.addItem(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3));
            }
            con.close();
        } catch (Exception e) { System.out.println(e);}

    }

    public void addTravelAdvisorComboBoxListener(){
        travelAdvisorComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (travelAdvisorComboBox.getSelectedIndex() != 0){
                    addTravelAdvisorData();
                } else {
                    advisorCodeTextField.setText("");
                    firstNameTextField.setText("");
                    lastNameTextField.setText("");
                    emailAddressTextField.setText("");
                    passwordTextField.setText("");
                    officeManagerIDTextField.setText("");
                }
            }
        });
    }

    public void addTravelAdvisorData(){

        String ta = (String) travelAdvisorComboBox.getSelectedItem();
        String[] splitTASelected = ta.split("\\s+");
        selectedAdvisorCode = Integer.valueOf(splitTASelected[0]);

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");

            String sql = "select * FROM in2018g16.TravelAdvisor where AdvisorCode = ?";
            PreparedStatement stmt=con.prepareStatement(sql);

            stmt.setInt(1, selectedAdvisorCode);

            ResultSet rs=stmt.executeQuery();

            while (rs.next()){

                advisorCodeTextField.setText("" + rs.getInt(1));
                firstNameTextField.setText(rs.getString(2));
                lastNameTextField.setText(rs.getString(3));
                emailAddressTextField.setText(rs.getString(4));
                passwordTextField.setText(rs.getString(5));
                officeManagerIDTextField.setText("" + rs.getInt(6));
            }

            con.close();
        } catch (Exception e) { System.out.println(e);}
    }

    public void addEditButtonListener(){
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (travelAdvisorComboBox.getSelectedIndex() != 0){
                    int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to edit this travel advisor?", "Confirm", JOptionPane.YES_NO_OPTION);

                    if (reply == JOptionPane.YES_OPTION) {

                        try{
                            Class.forName("com.mysql.jdbc.Driver");
                            Connection con= DriverManager.getConnection(
                                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                    "in2018g16_d",
                                    "35cnYJLB");

                            String sql = "update in2018g16.TravelAdvisor Set AdvisorCode = ?, FirstName = ?, LastName = ?, EmailAddress = ?, Password = ?, OfficeManagerID = ? WHERE AdvisorCode = ?";

                            PreparedStatement stmt = con.prepareStatement(sql);

                            stmt.setInt(1, Integer.valueOf(advisorCodeTextField.getText()));
                            stmt.setString(2, firstNameTextField.getText());
                            stmt.setString(3, lastNameTextField.getText());
                            stmt.setString(4, emailAddressTextField.getText());
                            stmt.setString(5, passwordTextField.getText());
                            stmt.setInt(6, Integer.valueOf(officeManagerIDTextField.getText()));
                            stmt.setInt(7, selectedAdvisorCode);

                            int rs = stmt.executeUpdate();

                            if (rs != 0){
                                JOptionPane.showMessageDialog(null, rs + " travel advisor updated", "Successful Update", JOptionPane.INFORMATION_MESSAGE);
                                travelAdvisorComboBox.setSelectedIndex(0);
                            } else {
                                JOptionPane.showMessageDialog(null, "No travel advisor was updated, please retry", "Unsuccessful Update", JOptionPane.ERROR_MESSAGE);
                            }

                            con.close();

                        }catch(Exception ex){ System.out.println(ex);}

                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Please select a travel advisor", "Invalid Entry", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
