package AirViaLtd;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
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

    private ImageIcon homeIcon;
    private ImageIcon backIcon;
    private AirViaLtd app;

    //constructor
    public EditTravelAdvisorAccountPage(AirViaLtd a) {
        this.app = a;
        setGraphics();
        addMenuButtonsListener();
        addTravelAdvisors();
        addTravelAdvisorComboBoxListener();
        addEditButtonListener();
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

        advisorCodeTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        firstNameTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        lastNameTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        emailAddressTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        passwordTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        officeManagerIDTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));

        editButton.setPreferredSize(new Dimension(250, 50));
        editButton.setBorder(new LineBorder(Color.WHITE, 1));

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
                app.transitionToManageTravelAdvisorPage();
            }
        });
    }

    public void addTravelAdvisors(){

        travelAdvisorComboBox.addItem("-- Select --");
        Connection con = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");
            con.setAutoCommit(false);

            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select AdvisorCode, FirstName, LastName FROM in2018g16.TravelAdvisor");

            con.commit();

            while (rs.next()){
                travelAdvisorComboBox.addItem(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3));
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

    //show data of selected travel advisor
    public void addTravelAdvisorData(){

        String ta = (String) travelAdvisorComboBox.getSelectedItem();
        String[] splitTASelected = ta.split("\\s+");
        selectedAdvisorCode = Integer.valueOf(splitTASelected[0]);

        Connection con =null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");

            con.setAutoCommit(false);

            String sql = "select * FROM in2018g16.TravelAdvisor where AdvisorCode = ?";
            PreparedStatement stmt=con.prepareStatement(sql);

            stmt.setInt(1, selectedAdvisorCode);

            ResultSet rs=stmt.executeQuery();

            con.commit();

            while (rs.next()){

                advisorCodeTextField.setText("" + rs.getInt(1));
                firstNameTextField.setText(rs.getString(2));
                lastNameTextField.setText(rs.getString(3));
                emailAddressTextField.setText(rs.getString(4));
                passwordTextField.setText(rs.getString(5));
                officeManagerIDTextField.setText("" + rs.getInt(6));
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

    //insert user changes into travel advisor table in database
    public void addEditButtonListener(){
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (travelAdvisorComboBox.getSelectedIndex() != 0){
                    int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to edit this travel advisor?", "Confirm", JOptionPane.YES_NO_OPTION);

                    if (reply == JOptionPane.YES_OPTION) {

                        Connection con =null;

                        try{
                            Class.forName("com.mysql.jdbc.Driver");
                            con= DriverManager.getConnection(
                                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                    "in2018g16_d",
                                    "35cnYJLB");
                            con.setAutoCommit(false);

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

                            con.commit();

                            if (rs != 0){
                                JOptionPane.showMessageDialog(null, rs + " travel advisor updated", "Successful Update", JOptionPane.INFORMATION_MESSAGE);
                                travelAdvisorComboBox.setSelectedIndex(0);
                            } else {
                                JOptionPane.showMessageDialog(null, "No travel advisor was updated, please retry", "Unsuccessful Update", JOptionPane.ERROR_MESSAGE);
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
                    JOptionPane.showMessageDialog(null, "Please select a travel advisor", "Invalid Entry", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
