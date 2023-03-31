package AirViaLtd;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginPage {
    private JPanel mainPanel;
    private JTextField usernameTextField;
    private JPasswordField passwordTextField;
    private JPanel detailsPanel;
    private JPanel buttonsPanel;
    private JButton loginButton;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JButton registerButton;
    private JLabel jobTitleLabel;
    private JComboBox jobTitleComboBox;

    private AirViaLtd app;
    private String user;

    public LoginPage(AirViaLtd a) {

        this.app = a;

        this.user = "";

        addJobTitles();

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
    }
    public JPanel getMainPanel() {
        return mainPanel;
    }

    public String getUser() { return user; }

    public void addJobTitles(){
        jobTitleComboBox.addItem("Administrator");
        jobTitleComboBox.addItem("Office Manager");
        jobTitleComboBox.addItem("Travel Advisor");
    }

    public void login(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con;

            switch (jobTitleComboBox.getSelectedIndex()){
                case 0:
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
                case 0:
                    sql = "select * from in2018g16.Administrator where EmailAddress = ? and Password = ?";
                    break;
                case 1:
                    sql = "select * from in2018g16.OfficeManager where EmailAddress = ? and Password = ?";
                    break;
                case 2:
                    sql = "select * from in2018g16.TravelAdvisor where EmailAddress = ? and Password = ?";
                    break;
            }

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, usernameTextField.getText());
            stmt.setString(2, new String(passwordTextField.getPassword()));

            ResultSet rs = stmt.executeQuery();
            //check empty result
            if (!rs.isBeforeFirst() ) {
                JOptionPane.showMessageDialog(getMainPanel(), "This user does not exist", "Invalid Login", JOptionPane.ERROR_MESSAGE);
                System.out.println("Invalid Login");
            } else {
                System.out.println("Successful login");
                this.user = jobTitleComboBox.getSelectedItem().toString();
                switch (jobTitleComboBox.getSelectedIndex()){
                    case 0:
                        app.transitionToAdministratoHomePage();
                        break;
                    case 1:
                        app.transitionToOfficeManagerHomePage();
                        break;
                    case 2:
                        app.transitionToTravelAdvisorHomePage();
                        break;
                }
            }
            con.close();

        }catch(Exception ex){ System.out.println(ex);}
    }


}
