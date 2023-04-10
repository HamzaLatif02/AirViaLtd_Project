package AirViaLtd;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;

public class SearchBlankPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel functionPanel;
    private JLabel searchBlankLabel;
    private JButton searchButton;
    private JLabel selectedBlankInfoLabel;
    private JTextField IDTextField;
    private JTextField typeTextField;
    private JTextField numberTextField;
    private JTextField newlyReceivedTextField;
    private JTextField receivedDateTextField;
    private JTextField assignedDateTextField;
    private JTextField usedDateTextField;
    private JTextField advisorCodeTextField;
    private JTextField auditCouponIDTextField;
    private JLabel flightCouponsLabel;
    private JComboBox typeComboBox;
    private JComboBox numberComboBox;
    private JTextField fcIDTextField;
    private JTextField departureCityTextField;
    private JTextField arrivalCityTextField;
    private JTextField departureDateTextField;
    private JTextField arrivalDateTextField;
    private JTextField departureTimeTextField;
    private JTextField arrivalTimeTextField;
    private JButton previousButton;
    private JButton nextButton;
    private JPanel menuPanel;
    private JButton homeButton;
    private JButton backButton;

    private DefaultTableModel fcModel;
    private JTable fcTable;

    private int tLength;
    private int tCurrent;

    private int searchedBlankID;

    private AirViaLtd app;

    public SearchBlankPage(AirViaLtd a) {
        this.app = a;

        addMenuButtonsListener();
        addSearchTypeData();
        addSearchNumberData();
        addSearchButtonListener();
        addNextButtonListener();
        addPreviousButtonListener();

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

            }
        });
    }

    public void addSearchTypeData(){
        typeComboBox.addItem("-- Select Type --");
        typeComboBox.addItem(444);
        typeComboBox.addItem(440);
        typeComboBox.addItem(420);
        typeComboBox.addItem(201);
        typeComboBox.addItem(101);
    }

    public void addSearchNumberData(){

        typeComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (typeComboBox.getSelectedIndex() != 0){
                    numberComboBox.removeAllItems();
                    numberComboBox.addItem("-- Select Number --");

                    try{
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con= DriverManager.getConnection(
                                "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                "in2018g16_d",
                                "35cnYJLB");

                        String sql = "select Number from in2018g16.Blank where Type = ? order by Number ASC";
                        PreparedStatement stmt=con.prepareStatement(sql);

                        stmt.setInt(1, Integer.valueOf(typeComboBox.getSelectedItem().toString()));

                        ResultSet rs=stmt.executeQuery();

                        while (rs.next()){
                            numberComboBox.addItem(rs.getInt(1));
                        }
                        con.close();
                    } catch (Exception x) { System.out.println(x);}
                } else {
                    numberComboBox.removeAllItems();
                    numberComboBox.addItem("-- Select Type Number First --");
                }
            }
        });
    }

    public void addSearchButtonListener(){
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               addSearchedBlankData();
               addSearchedFlightCouponData();
            }
        });
    }

    public void addSearchedBlankData(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");

            String sql = "select * from in2018g16.Blank where Type = ? and Number = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, Integer.valueOf(typeComboBox.getSelectedItem().toString()));
            stmt.setInt(2, Integer.valueOf(numberComboBox.getSelectedItem().toString()));

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                searchedBlankID = rs.getInt(1);
                IDTextField.setText("ID: " + rs.getInt(1));
                typeTextField.setText("Type: " + rs.getInt(2));
                numberTextField.setText("Number: " + rs.getInt(3));
                newlyReceivedTextField.setText("Newly Received: " + rs.getBoolean(4));
                receivedDateTextField.setText("Received Date:" + rs.getDate(5));
                assignedDateTextField.setText("Assigned Date: " + rs.getDate(6));
                usedDateTextField.setText("Used Date: " + rs.getDate(7));
                advisorCodeTextField.setText("Advisor Code: " + rs.getInt(8));
                auditCouponIDTextField.setText("Audit Coupon ID: " + rs.getInt(9));
            }

            con.close();

        }catch (Exception x) { System.out.println(x);}
    }

    public void addSearchedFlightCouponData(){

        fcIDTextField.setText("ID: ");
        departureCityTextField.setText("Departure City: ");
        arrivalCityTextField.setText("Arrival City: ");
        departureDateTextField.setText("Departure Date: ");
        arrivalDateTextField.setText("Arrival Date: ");
        departureTimeTextField.setText("Departure Time: ");
        arrivalTimeTextField.setText("Arrival Time: ");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");

            String sql = "Select count(*) from in2018g16.FlightCoupon where BlankID = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, searchedBlankID);

            ResultSet rs = stmt.executeQuery();

            tLength = 0;

            while (rs.next()){
                tLength = rs.getInt(1);
            }

            if (tLength > 0){
                flightCouponsLabel.setText("Flight Coupons: " + tLength);

                String sql1 = "select * from in2018g16.FlightCoupon where BlankID = ?";

                PreparedStatement stmt1 = con.prepareStatement(sql1);

                stmt1.setInt(1, searchedBlankID);

                ResultSet rs1 = stmt1.executeQuery();

                fcModel = new DefaultTableModel();
                fcModel.addColumn("ID");
                fcModel.addColumn("Departure City");
                fcModel.addColumn("Arrival City");
                fcModel.addColumn("Departure Date");
                fcModel.addColumn("Arrival Date");
                fcModel.addColumn("Departure Time");
                fcModel.addColumn("Arrival Time");

                while(rs1.next()){
                    Object[] row = new Object[7];
                    row[0] = rs1.getInt(1);
                    row[1] = rs1.getString(2);
                    row[2] = rs1.getString(3);
                    row[3] = rs1.getDate(4);
                    row[4] = rs1.getDate(5);
                    row[5] = rs1.getInt(6);
                    row[6] = rs1.getInt(7);
                    fcModel.addRow(row);
                }

                fcTable = new JTable();
                fcTable.setModel(fcModel);
                fcTable.setDefaultEditor(Object.class, null);

                tCurrent = 0;

                addFCData(tCurrent);

            } else {
                flightCouponsLabel.setText("Flight Coupons: " + tLength);
                JOptionPane.showMessageDialog(null, "This blank has no flight coupon associated with it", "Information", JOptionPane.INFORMATION_MESSAGE);
            }



            con.close();

        }catch (Exception x) { System.out.println(x);}

    }

    public void addNextButtonListener(){
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tLength > 0){
                    if (tCurrent + 1 == tLength){
                        JOptionPane.showMessageDialog(null, "no more flight coupons", "error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        tCurrent++;
                        addFCData(tCurrent);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "no flight coupons", "error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void addPreviousButtonListener(){
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tLength > 0){
                    if (tCurrent - 1 < 0){
                        JOptionPane.showMessageDialog(null, "no previous flight coupons", "error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        tCurrent--;
                        addFCData(tCurrent);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "no flight coupons", "error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }

    public void addFCData(int i){
        fcIDTextField.setText("ID: " + fcTable.getValueAt(i, 0).toString());
        departureCityTextField.setText("Departure City: " + fcTable.getValueAt(i,1).toString());
        arrivalCityTextField.setText("Arrival City: " + fcTable.getValueAt(i,2).toString());
        departureDateTextField.setText("Departure Date: " + fcTable.getValueAt(i,3).toString());
        arrivalDateTextField.setText("Arrival Date: " + fcTable.getValueAt(i, 4).toString());
        departureTimeTextField.setText("Departure Time: " + fcTable.getValueAt(i,5).toString());
        arrivalTimeTextField.setText("Arrival Time: " + fcTable.getValueAt(i, 6).toString());
    }

}
