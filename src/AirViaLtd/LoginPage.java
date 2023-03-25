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
    private JTextField jobTitleTextField;
    private JLabel jobTitleLabel;

    public LoginPage() {

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con= DriverManager.getConnection(
                            "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                            "in2018g16_a",
                            "FJ7BjC1x");

                    String sql = null;

                    if (jobTitleTextField.getText().equals("Travel Agent")){
                        sql = "select * from in2018g16.TravelAgent where EmailAddress = ? and Password = ?";
                    }

                    PreparedStatement stmt = con.prepareStatement(sql);

                    stmt.setString(1, usernameTextField.getText());
                    stmt.setString(2, new String(passwordTextField.getPassword()));

                    ResultSet rs = stmt.executeQuery();
                    //check empty result
                    if (!rs.isBeforeFirst() ) {
                        System.out.println("Invalid Login");
                    } else {
                        System.out.println("Successful login");
                    }
                    con.close();

                }catch(Exception ex){ System.out.println(ex);}
            }
        });
    }
    public JPanel getMainPanel() {
        return mainPanel;
    }


}
