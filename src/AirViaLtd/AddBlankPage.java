package AirViaLtd;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

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

    private AirViaLtd app;

    public AddBlankPage(AirViaLtd a) {
        this.app = a;
        addBlankTypeComboBoxData();
        addSingleButtonListener();
        addMultipleButtonListener();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void addSingleButtonListener(){
        addSingleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String date = java.time.LocalDate.now().toString();

                if (checkSingleValues()){
                    try{
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con= DriverManager.getConnection(
                                "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                "in2018g16_a",
                                "FJ7BjC1x");


                        Statement stmt1 = con.createStatement();
                        int rs1 = stmt1.executeUpdate("update in2018g16.Blank set NewlyReceived = 0 where NewlyReceived = 1");

                        String sql = "insert into in2018g16.Blank (Type, Number, NewlyReceived, ReceivedDate, AssignedDate, UsedDate, AdvisorCode, AuditCouponID) values (?, ?, 1, ?, null, null, 1, 1)";

                        PreparedStatement stmt= con.prepareStatement(sql);

                        stmt.setInt(1, Integer.valueOf(singleBlankTypeComboBox.getSelectedItem().toString()));
                        stmt.setInt(2, Integer.valueOf(singleBlankNumberTextField.getText()));
                        stmt.setString(3, date);

                        int rs=stmt.executeUpdate();

                        if (rs != 0){
                            JOptionPane.showMessageDialog(null, rs + " blank added", "Successful Update", JOptionPane.INFORMATION_MESSAGE);
                            singleBlankTypeComboBox.setSelectedIndex(0);
                            singleBlankNumberTextField.setText("");
                        } else {
                            JOptionPane.showMessageDialog(null, "Could not add blank, please retry", "Unsuccessful Update", JOptionPane.ERROR_MESSAGE);
                        }


                        con.close();

                    }catch(Exception x){ System.out.println(x);}
                }
            }
        });
    }

    public void addMultipleButtonListener(){
        addMultipleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String date = java.time.LocalDate.now().toString();

                if (checkMultipleValues()){
                    try{
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con= DriverManager.getConnection(
                                "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                "in2018g16_a",
                                "FJ7BjC1x");


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

                            if (rs == 0){
                                successfulQuery = false;
                            }
                        }


                        if (successfulQuery){
                            JOptionPane.showMessageDialog(null, difference + " blanks added", "Successful Update", JOptionPane.INFORMATION_MESSAGE);
                            multipleStartBlankTypeComboBox.setSelectedIndex(0);
                            multipleEndBlankTypeComboBox.setSelectedIndex(0);
                            multipleStartBlankNumberTextField.setText("");
                            multipleEndBlankNumberTextField.setText("");
                        } else {
                            JOptionPane.showMessageDialog(null, "Could not add blanks, please retry", "Unsuccessful Update", JOptionPane.ERROR_MESSAGE);
                        }


                        con.close();

                    }catch(Exception x){ System.out.println(x);}
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

        multipleEndBlankTypeComboBox.addItem("-- Select --");
        multipleEndBlankTypeComboBox.addItem(444);
        multipleEndBlankTypeComboBox.addItem(440);
        multipleEndBlankTypeComboBox.addItem(420);
        multipleEndBlankTypeComboBox.addItem(201);
        multipleEndBlankTypeComboBox.addItem(101);

        multipleStartBlankTypeComboBox.addItem("-- Select --");
        multipleStartBlankTypeComboBox.addItem(444);
        multipleStartBlankTypeComboBox.addItem(440);
        multipleStartBlankTypeComboBox.addItem(420);
        multipleStartBlankTypeComboBox.addItem(201);
        multipleStartBlankTypeComboBox.addItem(101);

    }

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
