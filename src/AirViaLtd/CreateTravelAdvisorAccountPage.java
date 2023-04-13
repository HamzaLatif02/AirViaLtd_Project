package AirViaLtd;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateTravelAdvisorAccountPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel functionPanel;
    private JLabel createTravelAdvisorAccountLabel;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField emailAddressTextField;
    private JTextField passwordTextField;
    private JTextField advisorCodeTextField;
    private JTextField officeManagerIDTextField;
    private JButton createButton;
    private JPanel menuPanel;
    private JButton homeButton;
    private JButton backButton;

    private ImageIcon homeIcon;
    private ImageIcon backIcon;
    private AirViaLtd app;

    public CreateTravelAdvisorAccountPage(AirViaLtd a) {
        this.app = a;
        setGraphics();
        addMenuButtonsListener();
        addCreateButtonListener();
        addTextListener();
    }

    public void setGraphics(){

        homeIcon = new ImageIcon("data/home.png");
        homeButton.setPreferredSize(new Dimension(100, 30));
        homeButton.setBorderPainted(false);
        homeButton.setIcon(homeIcon);

        backIcon = new ImageIcon("data/back.png");
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.setBorderPainted(false);
        backButton.setIcon(backIcon);

        firstNameTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        lastNameTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        emailAddressTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        passwordTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        advisorCodeTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        officeManagerIDTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));

        createButton.setPreferredSize(new Dimension(250, 50));
        createButton.setBorder(new LineBorder(Color.WHITE, 1));

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

    public void addCreateButtonListener(){
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkInputData()){
                    Connection con = null;
                    try{
                        Class.forName("com.mysql.jdbc.Driver");
                        con= DriverManager.getConnection(
                                "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                "in2018g16_d",
                                "35cnYJLB");
                        con.setAutoCommit(false);

                        String sql = "insert into in2018g16.TravelAdvisor (AdvisorCode, FirstName, LastName, EmailAddress, Password, OfficeManagerID) values (?, ?, ?, ?, ?, ?)";

                        PreparedStatement stmt= con.prepareStatement(sql);

                        stmt.setInt(1, Integer.valueOf(advisorCodeTextField.getText()));
                        stmt.setString(2, firstNameTextField.getText().toString());
                        stmt.setString(3, lastNameTextField.getText().toString());
                        stmt.setString(4, emailAddressTextField.getText().toString());
                        stmt.setString(5, passwordTextField.getText().toString());
                        stmt.setInt(6, Integer.valueOf(officeManagerIDTextField.getText()));

                        int rs=stmt.executeUpdate();
                        con.commit();

                        if (rs != 0){
                            JOptionPane.showMessageDialog(null, "Travel Advisor account created", "Successful Update", JOptionPane.INFORMATION_MESSAGE);
                            firstNameTextField.setText("First Name");
                            lastNameTextField.setText("Last Name");
                            emailAddressTextField.setText("Email Address");
                            passwordTextField.setText("Password");
                            advisorCodeTextField.setText("Advisor Code");
                            officeManagerIDTextField.setText("Office Manager ID");
                        } else {
                            JOptionPane.showMessageDialog(null, "Could not create travel advisor account, please retry", "Unsuccessful Update", JOptionPane.ERROR_MESSAGE);
                        }

                    }catch(Exception x){
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

    public boolean checkInputData(){

        if (firstNameTextField.getText().toString().equals("First Name") || firstNameTextField.getText().toString().equals("")){
            JOptionPane.showMessageDialog(null, "Please enter a first name", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (lastNameTextField.getText().toString().equals("Last Name") || lastNameTextField.getText().toString().equals("")){
            JOptionPane.showMessageDialog(null, "Please enter a last name", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (emailAddressTextField.getText().toString().equals("Email Address") || emailAddressTextField.getText().toString().equals("")){
            JOptionPane.showMessageDialog(null, "Please enter an email address", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (passwordTextField.getText().toString().equals("Password") || passwordTextField.getText().toString().equals("")){
            JOptionPane.showMessageDialog(null, "Please enter a password", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (advisorCodeTextField.getText().toString().equals("Advisor Code") || advisorCodeTextField.getText().toString().equals("")){
            JOptionPane.showMessageDialog(null, "Please enter an advisor code", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (officeManagerIDTextField.getText().toString().equals("Office Manager ID") || officeManagerIDTextField.getText().toString().equals("")){
            JOptionPane.showMessageDialog(null, "Please enter an office manager ID", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public void addTextListener(){
        firstNameTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                if (firstNameTextField.getText().equals("First Name")){
                    firstNameTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                if (firstNameTextField.getText().equals("")){
                    firstNameTextField.setText("First Name");
                }
            }
        });

        lastNameTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                if (lastNameTextField.getText().equals("Last Name")){
                    lastNameTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                if (lastNameTextField.getText().equals("")){
                    lastNameTextField.setText("Last Name");
                }
            }
        });

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

        passwordTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                if (passwordTextField.getText().equals("Password")){
                    passwordTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                if (passwordTextField.getText().equals("")){
                    passwordTextField.setText("Password");
                }
            }
        });

        advisorCodeTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                if (advisorCodeTextField.getText().equals("Advisor Code")){
                    advisorCodeTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                if (advisorCodeTextField.getText().equals("")){
                    advisorCodeTextField.setText("Advisor Code");
                }
            }
        });

        officeManagerIDTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                if (officeManagerIDTextField.getText().equals("Office Manager ID")){
                    officeManagerIDTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                if (officeManagerIDTextField.getText().equals("")){
                    officeManagerIDTextField.setText("Office Manager ID");
                }
            }
        });
    }
}
