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

    private boolean successfulLogin;

    public LoginPage() {

        addJobTitles();

        successfulLogin = false;



        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                        case 0:
                            sql = "select * from in2018g16.TravelAgent where EmailAddress = ? and Password = ?";
                            break;
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

                    stmt.setString(1, usernameTextField.getText());
                    stmt.setString(2, new String(passwordTextField.getPassword()));

                    ResultSet rs = stmt.executeQuery();
                    //check empty result
                    if (!rs.isBeforeFirst() ) {
                        JOptionPane.showMessageDialog(getMainPanel(), "This user does not exist", "Invalid Login", JOptionPane.ERROR_MESSAGE);
                        System.out.println("Invalid Login");
                    } else {
                        System.out.println("Successful login");
                        successfulLogin = true;
                    }
                    con.close();

                }catch(Exception ex){ System.out.println(ex);}
            }
        });
    }
    public JPanel getMainPanel() {
        return mainPanel;
    }

    public boolean isSuccessfulLogin() {
        return successfulLogin;
    }

    public void setSuccessfulLogin(boolean successfulLogin) {
        this.successfulLogin = successfulLogin;
    }

    public void addJobTitles(){
        jobTitleComboBox.addItem("Travel Agent");
        jobTitleComboBox.addItem("Administrator");
        jobTitleComboBox.addItem("Office Manager");
        jobTitleComboBox.addItem("Travel Advisor");
    }


}
