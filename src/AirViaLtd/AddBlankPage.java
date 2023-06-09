package AirViaLtd;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddBlankPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel featurePanel;
    private JLabel addBlankLabel;
    private JLabel addOneLabel;
    private JLabel blankTypeLabel;
    private JTextField multipleEndBlankNumberTextField;
    private JButton addMultipleButton;
    private JButton addSingleButton;
    private JLabel addMultipleLabel;
    private JLabel startBlankNumberLabel;
    private JLabel endBlankNumberLabel;
    private JTextField singleBlankNumberTextField;
    private JLabel blankNumberLabel;
    private JLabel startLabel;
    private JLabel endLabel;
    private JLabel startBlankTypeLabel;
    private JLabel endBlankTypeLabel;
    private JComboBox singleBlankTypeComboBox;
    private JComboBox multipleStartBlankTypeComboBox;
    private JTextField multipleStartBlankNumberTextField;
    private JComboBox multipleEndBlankTypeComboBox;
    private JPanel menuPanel;
    private JButton homeButton;
    private JButton backButton;

    private int selectedID;

    private ImageIcon homeIcon;
    private ImageIcon backIcon;
    private AirViaLtd app;

    //constructor
    public AddBlankPage(AirViaLtd a) {
        this.app = a;
        setGraphics();
        addMenuButtonsListener();
        addBlankTypeComboBoxData();
        addSingleButtonListener();
        addMultipleButtonListener();
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

        singleBlankNumberTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        multipleStartBlankNumberTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        multipleEndBlankNumberTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));


        addSingleButton.setPreferredSize(new Dimension(250, 50));
        addSingleButton.setBorder(new LineBorder(Color.WHITE, 1));
        addMultipleButton.setPreferredSize(new Dimension(250, 50));
        addMultipleButton.setBorder(new LineBorder(Color.WHITE, 1));

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
                app.removeTables();
                app.transitionToBlankManagerPage();
            }
        });
    }

    //add functionality to single button
    //retrieve data from input
    //add data to database
    public void addSingleButtonListener(){
        addSingleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String date = java.time.LocalDate.now().toString();

                if (checkSingleValues()){
                    Connection con = null;
                    try{
                        Class.forName("com.mysql.jdbc.Driver");
                        con= DriverManager.getConnection(
                                "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                "in2018g16_d",
                                "35cnYJLB");

                        con.setAutoCommit(false);

                        Statement stmt1 = con.createStatement();
                        int rs1 = stmt1.executeUpdate("update in2018g16.Blank set NewlyReceived = 0 where NewlyReceived = 1");

                        String sql = "insert into in2018g16.Blank (Type, Number, NewlyReceived, ReceivedDate, AssignedDate, UsedDate, AdvisorCode, AuditCouponID) values (?, ?, 1, ?, null, null, 1, 1)";

                        PreparedStatement stmt= con.prepareStatement(sql);

                        stmt.setInt(1, Integer.valueOf(singleBlankTypeComboBox.getSelectedItem().toString()));
                        stmt.setInt(2, Integer.valueOf(singleBlankNumberTextField.getText()));
                        stmt.setString(3, date);


                        int rs=stmt.executeUpdate();
                        con.commit();

                        if (rs != 0){
                            JOptionPane.showMessageDialog(null, rs + " blank added", "Successful Update", JOptionPane.INFORMATION_MESSAGE);

                            addTicket(con);

                            singleBlankTypeComboBox.setSelectedIndex(0);
                            singleBlankNumberTextField.setText("");


                        } else {
                            JOptionPane.showMessageDialog(null, "Could not add blank, please retry", "Unsuccessful Update", JOptionPane.ERROR_MESSAGE);
                        }

                    }catch(Exception x){
                        System.out.println(x);
                        if (con != null){
                            try {
                                con.rollback();
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
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

    //create ticket based on blank added
    public void addTicket(Connection con) throws SQLException {

        int randomnumber = (int) (Math.floor(Math.random()*11)*10);

        String sql1 = "select ID from in2018g16.Blank where Type = ? and Number = ?";
        PreparedStatement stmt2 = con.prepareStatement(sql1);
        stmt2.setInt(1, Integer.valueOf(singleBlankTypeComboBox.getSelectedItem().toString()));
        stmt2.setInt(2, Integer.valueOf(singleBlankNumberTextField.getText()));

        ResultSet rs2 = stmt2.executeQuery();

        while (rs2.next()){
            selectedID = rs2.getInt(1);
        }

        String sql3 = "insert into in2018g16.Ticket (Price, BlankID) values (?,?)";
        PreparedStatement stmt3 = con.prepareStatement(sql3);
        stmt3.setInt(1, randomnumber);
        stmt3.setInt(2, selectedID);

        int rs3 = stmt3.executeUpdate();

        if (rs3 != 0){
            JOptionPane.showMessageDialog(null, "Ticket created", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "could not create ticket, please retry", "Unsuccessfull", JOptionPane.ERROR_MESSAGE);
        }
    }

    //creates tickets based on multiple blanks
    public void addTickets(Connection con, int type, int number) throws SQLException {

        int randomnumber = (int) (Math.floor(Math.random()*11)*10);

        String sql1 = "select ID from in2018g16.Blank where Type = ? and Number = ?";
        PreparedStatement stmt2 = con.prepareStatement(sql1);
        stmt2.setInt(1, type);
        stmt2.setInt(2, number);

        ResultSet rs2 = stmt2.executeQuery();

        while (rs2.next()){
            selectedID = rs2.getInt(1);
        }

        String sql3 = "insert into in2018g16.Ticket (Price, BlankID) values (?,?)";
        PreparedStatement stmt3 = con.prepareStatement(sql3);
        stmt3.setInt(1, randomnumber);
        stmt3.setInt(2, selectedID);

        int rs3 = stmt3.executeUpdate();

    }

    //add functionality to multiple button
    //retrieve data from user input
    //add data to database
    public void addMultipleButtonListener(){
        addMultipleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String date = java.time.LocalDate.now().toString();


                if (checkMultipleValues()){
                    Connection con = null;
                    try{
                        Class.forName("com.mysql.jdbc.Driver");
                        con= DriverManager.getConnection(
                                "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                "in2018g16_d",
                                "35cnYJLB");

                        con.setAutoCommit(false);

                        Statement stmt1 = con.createStatement();
                        int rs1 = stmt1.executeUpdate("update in2018g16.Blank set NewlyReceived = 0 where NewlyReceived = 1");


                        int difference = Integer.valueOf(multipleEndBlankNumberTextField.getText()) - Integer.valueOf(multipleStartBlankNumberTextField.getText());

                        boolean successfulQuery = true;

                        for (int i = 0; i < difference + 1; i++){

                            String sql = "insert into in2018g16.Blank (Type, Number, NewlyReceived, ReceivedDate, AssignedDate, UsedDate, AdvisorCode, AuditCouponID) values (?, ?, 1, ?, null, null, 1, 1)";

                            PreparedStatement stmt= con.prepareStatement(sql);

                            stmt.setInt(1, Integer.valueOf(multipleStartBlankTypeComboBox.getSelectedItem().toString()));
                            stmt.setInt(2, Integer.valueOf(multipleStartBlankNumberTextField.getText()) + i);
                            stmt.setString(3, date);

                            int rs = stmt.executeUpdate();
                            con.commit();

                            addTickets(con, Integer.valueOf(multipleStartBlankTypeComboBox.getSelectedItem().toString()), Integer.valueOf(multipleStartBlankNumberTextField.getText()) + i);

                            if (rs == 0){
                                successfulQuery = false;
                            }
                        }

                        if (successfulQuery){
                            JOptionPane.showMessageDialog(null, difference+1 + " blanks added", "Successful Update", JOptionPane.INFORMATION_MESSAGE);
                            JOptionPane.showMessageDialog(null, difference+1 + " tickets added", "Successful Update", JOptionPane.INFORMATION_MESSAGE);

                            multipleStartBlankTypeComboBox.setSelectedIndex(0);
                            multipleEndBlankTypeComboBox.setSelectedIndex(0);
                            multipleStartBlankNumberTextField.setText("");
                            multipleEndBlankNumberTextField.setText("");
                        } else {
                            JOptionPane.showMessageDialog(null, "Could not add blanks, please retry", "Unsuccessful Update", JOptionPane.ERROR_MESSAGE);
                        }

                    } catch(Exception x){
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

    public void addBlankTypeComboBoxData(){

        singleBlankTypeComboBox.addItem("-- Select --");
        singleBlankTypeComboBox.addItem(444);
        singleBlankTypeComboBox.addItem(440);
        singleBlankTypeComboBox.addItem(420);
        singleBlankTypeComboBox.addItem(201);
        singleBlankTypeComboBox.addItem(101);
        singleBlankTypeComboBox.addItem(451);
        singleBlankTypeComboBox.addItem(452);

        multipleEndBlankTypeComboBox.addItem("-- Select --");
        multipleEndBlankTypeComboBox.addItem(444);
        multipleEndBlankTypeComboBox.addItem(440);
        multipleEndBlankTypeComboBox.addItem(420);
        multipleEndBlankTypeComboBox.addItem(201);
        multipleEndBlankTypeComboBox.addItem(101);
        multipleEndBlankTypeComboBox.addItem(451);
        multipleEndBlankTypeComboBox.addItem(452);

        multipleStartBlankTypeComboBox.addItem("-- Select --");
        multipleStartBlankTypeComboBox.addItem(444);
        multipleStartBlankTypeComboBox.addItem(440);
        multipleStartBlankTypeComboBox.addItem(420);
        multipleStartBlankTypeComboBox.addItem(201);
        multipleStartBlankTypeComboBox.addItem(101);
        multipleStartBlankTypeComboBox.addItem(451);
        multipleStartBlankTypeComboBox.addItem(452);

    }

    //check if user input is valid
    public boolean checkSingleValues(){
        if (singleBlankTypeComboBox.getSelectedIndex() == 0){
            JOptionPane.showMessageDialog(null, "Please select a blank type", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (singleBlankNumberTextField.getText().toString().equals("") ){
            JOptionPane.showMessageDialog(null, "Please enter a blank number", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    //check if user input is valid
    public boolean checkMultipleValues() {

        if (multipleEndBlankTypeComboBox.getSelectedIndex() == 0 || multipleStartBlankTypeComboBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Please select a blank type", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (multipleStartBlankTypeComboBox.getSelectedIndex() != multipleEndBlankTypeComboBox.getSelectedIndex()){
            JOptionPane.showMessageDialog(null, "Please make sure the blank types are the same", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (multipleStartBlankNumberTextField.getText().toString().equals("") || multipleEndBlankNumberTextField.getText().toString().equals("")){
            JOptionPane.showMessageDialog(null, "Please enter a blank number", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (Integer.valueOf(multipleStartBlankNumberTextField.getText()) > Integer.valueOf(multipleEndBlankNumberTextField.getText())){
            JOptionPane.showMessageDialog(null, "Please make sure the end blank number is higher than the start blank number", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}
