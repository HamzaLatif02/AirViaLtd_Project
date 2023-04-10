package AirViaLtd;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;

public class RemoveTravelAdvisorAccountPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel functionPanel;
    private JLabel removeTravelAdvisorAccountPage;
    private JLabel selectTravelAdvisorLabel;
    private JLabel selectedTravelAdvisorDetailsLabel;
    private JTextField advisorCodeTextField;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField emailAddressTextField;
    private JTextField passwordTextField;
    private JTextField officeManagerTextField;
    private JButton removeButton;
    private JComboBox travelAdvisorComboBox;
    private JButton homeButton;
    private JButton backButton;
    private JPanel menuPanel;

    private int selectedAdvisorCode;

    private AirViaLtd app;

    public RemoveTravelAdvisorAccountPage(AirViaLtd a) {
        this.app = a;
        addMenuButtonsListener();
        addTravelAdvisors();
        addTravelAdvisorComboBoxListener();
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
                    addOfficeManagerData();
                } else {
                    advisorCodeTextField.setText("");
                    firstNameTextField.setText("");
                    lastNameTextField.setText("");
                    emailAddressTextField.setText("");
                    passwordTextField.setText("");
                    officeManagerTextField.setText("");
                }
            }
        });
    }

    public void addOfficeManagerData(){

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
                officeManagerTextField.setText("" + rs.getInt(6));
            }

            con.close();
        } catch (Exception e) { System.out.println(e);}
    }


    public void addRemoveButtonListener(){
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (travelAdvisorComboBox.getSelectedIndex() != 0){
                    int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this travel advisor?", "Confirm", JOptionPane.YES_NO_OPTION);

                    if (reply == JOptionPane.YES_OPTION) {

                        try{
                            Class.forName("com.mysql.jdbc.Driver");
                            Connection con= DriverManager.getConnection(
                                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                    "in2018g16_d",
                                    "35cnYJLB");

                            String sql = "delete from in2018g16.TravelAdvisor WHERE AdvisorCode = ?";

                            PreparedStatement stmt = con.prepareStatement(sql);

                            stmt.setInt(1, selectedAdvisorCode);

                            int rs = stmt.executeUpdate();

                            if (rs != 0){
                                JOptionPane.showMessageDialog(null, rs + " travel advisor deleted", "Successful Update", JOptionPane.INFORMATION_MESSAGE);
                                travelAdvisorComboBox.setSelectedIndex(0);
                            } else {
                                JOptionPane.showMessageDialog(null, "No travel advisor was deleted, please retry", "Unsuccessful Update", JOptionPane.ERROR_MESSAGE);
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
