package AirViaLtd;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.*;

public class LoginPage {
    private JPanel mainPanel;
    private JTextField emailTextField;
    private JPasswordField passwordTextField;
    private JPanel detailsPanel;
    private JPanel buttonsPanel;
    private JButton loginButton;
    private JLabel jobTitleLabel;
    private JComboBox jobTitleComboBox;
    private JPanel titlePanel;
    private JLabel emailIcon;
    private JLabel passwordIcon;

    private AirViaLtd app;
    private String user;

    private String loginEmail;

    private ImageIcon userIcon;
    private ImageIcon lockIcon;


    public LoginPage(AirViaLtd a) {

        this.app = a;

        this.user = "";

        userIcon = new ImageIcon("data/user.png");
        lockIcon = new ImageIcon("data/lock.png");

        addJobTitles();
        addEmailTextListener();
        addPasswordTextListener();

        emailIcon.setIcon(userIcon);
        passwordIcon.setIcon(lockIcon);

        loginListener();

    }
    public JPanel getMainPanel() {
        return mainPanel;
    }

    public String getUser() { return user; }


    public void addJobTitles(){
        jobTitleComboBox.addItem("-- Select --");
        jobTitleComboBox.addItem("Administrator");
        jobTitleComboBox.addItem("Office Manager");
        jobTitleComboBox.addItem("Travel Advisor");
    }

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


    public void loginListener(){
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                login();
            }
        });
    }
    public void login(){

        if (validInput()){



            try{

                Class.forName("com.mysql.jdbc.Driver");
                Connection con;

                switch (jobTitleComboBox.getSelectedIndex()){
                    case 1:
                        con= DriverManager.getConnection(
                                "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                "in2018g16_a",
                                "FJ7BjC1x");
                    default:
                        con= DriverManager.getConnection(
                                "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                "in2018g16_d",
                                "35cnYJLB");
                }

                String sql = null;

                switch (jobTitleComboBox.getSelectedIndex()){
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
                //check empty result
                if (!rs.isBeforeFirst() ) {
                    JOptionPane.showMessageDialog(getMainPanel(), "Invalid login, please try again", "Invalid Login", JOptionPane.ERROR_MESSAGE);
                } else {
                    app.getSellTicketPage().addAdvisorCode(emailTextField.getText());
                    app.getSellTicketPage().addBlanks();
                    app.getIssueRefundPage().addAdvisorCode(emailTextField.getText());
                    app.getIssueRefundPage().addBlanks();
                    this.user = jobTitleComboBox.getSelectedItem().toString();
                    switch (jobTitleComboBox.getSelectedIndex()){
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
                con.close();

            }catch(Exception ex){ System.out.println(ex);}
        }
    }

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
