package AirViaLtd;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.*;

public class LoginPage extends JPanel {
    private JPanel mainPanel;
    private JTextField emailTextField;
    private JPasswordField passwordTextField;
    private JPanel detailsPanel;
    private JPanel buttonsPanel;
    private JButton loginButton;
    private JLabel jobTitleIcon;
    private JComboBox jobTitleComboBox;
    private JPanel titlePanel;
    private JLabel emailIcon;
    private JLabel passwordIcon;
    private JLabel airViaLtdIcon;
    private JLabel airViaLtdLabel;

    private AirViaLtd app;
    private String user;

    private String loginEmail;

    private ImageIcon staffIcon;
    private ImageIcon userIcon;
    private ImageIcon lockIcon;

    private ImageIcon bgIcon;

    private ImageIcon airviaIcon;

    private JLabel imageLabel;

    //constructor
    public LoginPage(AirViaLtd a) {

        this.app = a;
        this.user = "";

        setGraphics();
        addJobTitles();
        addEmailTextListener();
        addPasswordTextListener();
        loginListener();

    }

    //set page graphics
    public void setGraphics(){
        staffIcon = new ImageIcon("data/staff.png");
        userIcon = new ImageIcon("data/user.png");
        lockIcon = new ImageIcon("data/lock.png");
        bgIcon = new ImageIcon("data/background2.png");
        airviaIcon = new ImageIcon("data/airvialogo.png");

        jobTitleIcon.setIcon(staffIcon);
        emailIcon.setIcon(userIcon);
        passwordIcon.setIcon(lockIcon);
        airViaLtdIcon.setIcon(airviaIcon);

        emailTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        passwordTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));

        loginButton.setPreferredSize(new Dimension(250, 50));
        loginButton.setBorder(new LineBorder(Color.WHITE, 1));
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public String getUser() { return user; }

    public JTextField getEmailTextField() {
        return emailTextField;
    }

    public void addJobTitles(){
        jobTitleComboBox.addItem("-- Select --");
        jobTitleComboBox.addItem("Administrator");
        jobTitleComboBox.addItem("Office Manager");
        jobTitleComboBox.addItem("Travel Advisor");
    }

    //for visual effects
    public void addEmailTextListener(){

        emailTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                if (emailTextField.getText().equals("Email")){
                    emailTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                if (emailTextField.getText().equals("")){
                    emailTextField.setText("Email");
                }
            }
        });
    }

    //for visual effects
    public void addPasswordTextListener(){

        passwordTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                if (new String(passwordTextField.getPassword()).equals("Password")){
                    passwordTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                if (new String(passwordTextField.getPassword()).equals("")){
                    passwordTextField.setText("Password");
                }
            }
        });

    }

    //add functionality to lgin button
    public void loginListener(){
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                login();
            }
        });
    }

    //execute login with values inserted by the user
    public void login(){


        if (validInput()) {

            Connection con = null;

            try {

                Class.forName("com.mysql.jdbc.Driver");

                switch (jobTitleComboBox.getSelectedIndex()) {
                    case 1:
                        con = DriverManager.getConnection(
                                "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                "in2018g16_a",
                                "FJ7BjC1x");
                    default:
                        con = DriverManager.getConnection(
                                "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                "in2018g16_d",
                                "35cnYJLB");
                }

                con.setAutoCommit(false);

                String sql = null;

                switch (jobTitleComboBox.getSelectedIndex()) {
                    case 1:
                        sql = "select * from in2018g16.Administrator where EmailAddress = ? and Password = ?";
                        break;
                    case 2:
                        sql = "select * from in2018g16.OfficeManager where EmailAddress = ? and Password = ?";
                        break;
                    case 3:
                        sql = "select * from in2018g16.TravelAdvisor where EmailAddress = ? and Password = ?";
                        break;
                }

                PreparedStatement stmt = con.prepareStatement(sql);

                stmt.setString(1, emailTextField.getText());
                stmt.setString(2, new String(passwordTextField.getPassword()));

                ResultSet rs = stmt.executeQuery();

                con.commit();

                //check empty result
                if (!rs.isBeforeFirst()) {
                    JOptionPane.showMessageDialog(getMainPanel(), "Invalid login, please try again", "Invalid Login", JOptionPane.ERROR_MESSAGE);
                } else {
                    this.user = jobTitleComboBox.getSelectedItem().toString();
                    switch (jobTitleComboBox.getSelectedIndex()) {
                        case 1:
                            app.transitionToAdministratorHomePage();
                            break;
                        case 2:
                            app.transitionToOfficeManagerHomePage();
                            break;
                        case 3:
                            app.transitionToTravelAdvisorHomePage();
                            break;
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
                if (con != null){
                    try {
                        con.rollback();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            } finally {
                try {
                    con.setAutoCommit(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    //check if user entered a value
    public boolean validInput(){

        if (jobTitleComboBox.getSelectedIndex() == 0){
            JOptionPane.showMessageDialog(getMainPanel(), "Please select a job title", "Invalid Login", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (emailTextField.getText().equals("") || emailTextField.getText().equals("Email")){
            JOptionPane.showMessageDialog(getMainPanel(), "Please enter an email", "Invalid Login", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (new String(passwordTextField.getPassword()).equals("") || new String(passwordTextField.getPassword()).equals("Password")){
            JOptionPane.showMessageDialog(getMainPanel(), "Please enter a password", "Invalid Login", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

}
