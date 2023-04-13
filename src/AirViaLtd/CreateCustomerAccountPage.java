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

public class CreateCustomerAccountPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel featurePanel;
    private JLabel createCustomerAccountLabel;
    private JTextField nameTextField;
    private JTextField surnameTextField;
    private JTextField emailAddressTextField;
    private JButton createButton;
    private JComboBox regularValuedComboBox;
    private JPanel menuPanel;
    private JButton homeButton;
    private JButton backButton;

    private ImageIcon homeIcon;
    private ImageIcon backIcon;

    private AirViaLtd app;

    public CreateCustomerAccountPage(AirViaLtd a) {
        this.app = a;
        setGraphics();
        addMenuButtonsListener();
        addNameTextListener();
        addSurnameTextListener();
        addEmailTextListener();
        addRegularValuedComboBoxData();
        addCreateButtonListener();
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

        nameTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        surnameTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        emailAddressTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));

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
                if (app.getCurrentHomepage().equals(app.getAdministratorHomePage().getMainPanel()) || app.getCurrentHomepage().equals(app.getOfficeManagerHomePage().getMainPanel())){
                    app.transitionToManageCustomerPage();
                } else if (app.getCurrentHomepage().equals(app.getTravelAdvisorHomePage().getMainPanel())){
                    app.transitionToHomepage();
                }

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

                        String sql = "insert into in2018g16.Customer (EmailAddress, FirstName, LastName, Type, FixedDiscount, FlexibleDiscountID) values (?, ?, ?, ?, null, null)";

                        PreparedStatement stmt= con.prepareStatement(sql);

                        stmt.setString(1, emailAddressTextField.getText().toString());
                        stmt.setString(2, nameTextField.getText().toString());
                        stmt.setString(3, surnameTextField.getText().toString());
                        stmt.setString(4, regularValuedComboBox.getSelectedItem().toString());

                        int rs=stmt.executeUpdate();
                        con.commit();

                        if (rs != 0){
                            JOptionPane.showMessageDialog(null, "Customer account created", "Successful Update", JOptionPane.INFORMATION_MESSAGE);
                            regularValuedComboBox.setSelectedIndex(0);
                            emailAddressTextField.setText("Email Address");
                            nameTextField.setText("Name");
                            surnameTextField.setText("Surname");
                        } else {
                            JOptionPane.showMessageDialog(null, "Could not create customer account, please retry", "Unsuccessful Update", JOptionPane.ERROR_MESSAGE);
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

    public void addNameTextListener(){
        nameTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                if (nameTextField.getText().equals("Name")){
                    nameTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                if (nameTextField.getText().equals("")){
                    nameTextField.setText("Name");
                }
            }
        });
    }

    public void addSurnameTextListener(){
        surnameTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                if (surnameTextField.getText().equals("Surname")){
                    surnameTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                if (surnameTextField.getText().equals("")){
                    surnameTextField.setText("Surname");
                }
            }
        });
    }

    public void addEmailTextListener(){
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
    }

    public void addRegularValuedComboBoxData(){
        regularValuedComboBox.addItem("-- Regular Or Valued --");
        regularValuedComboBox.addItem("Regular");
        regularValuedComboBox.addItem("Valued");
    }

    public boolean checkInputData(){

        if (nameTextField.getText().toString().equals("Name") || nameTextField.getText().toString().equals("")){
            JOptionPane.showMessageDialog(null, "Please enter a name", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (surnameTextField.getText().toString().equals("Surname") || surnameTextField.getText().toString().equals("")){
            JOptionPane.showMessageDialog(null, "Please enter a surname", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (emailAddressTextField.getText().toString().equals("Email Address") || emailAddressTextField.getText().toString().equals("")){
            JOptionPane.showMessageDialog(null, "Please enter an email address", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (regularValuedComboBox.getSelectedIndex() == 0){
            JOptionPane.showMessageDialog(null, "Please select a type", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}
