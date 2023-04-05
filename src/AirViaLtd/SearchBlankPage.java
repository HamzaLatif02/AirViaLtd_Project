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
    private JComboBox fcIDComboBox;
    private JComboBox departureCityComboBox;
    private JComboBox arrivalCityComboBox;
    private JComboBox departureDateComboBox;
    private JComboBox arrivalDateComboBox;
    private JComboBox departureTimeComboBox;
    private JComboBox arrivalTimeComboBox;

    private boolean dataAdded;

    private AirViaLtd app;

    public SearchBlankPage(AirViaLtd a) {
        this.app = a;
        this.dataAdded = false;

        addSearchTypeData();
        addSearchNumberData();
        addSearchButtonListener();
    }

    public JPanel getMainPanel() {
        return mainPanel;
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
               addFcIDComboBoxListener();
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
                IDTextField.setText("" + rs.getInt(1));
                typeTextField.setText("" + rs.getInt(2));
                numberTextField.setText("" + rs.getInt(3));
                newlyReceivedTextField.setText("" + rs.getBoolean(4));
                receivedDateTextField.setText("" + rs.getDate(5));
                assignedDateTextField.setText("" + rs.getDate(6));
                usedDateTextField.setText("" + rs.getDate(7));
                advisorCodeTextField.setText("" + rs.getInt(8));
                auditCouponIDTextField.setText("" + rs.getInt(9));
            }

            con.close();

        }catch (Exception x) { System.out.println(x);}
    }

    public void addSearchedFlightCouponData(){
        dataAdded = false;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");

            String sql = "Select count(*) from in2018g16.FlightCoupon where BlankID = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, Integer.valueOf(Integer.valueOf(IDTextField.getText())));

            ResultSet rs = stmt.executeQuery();

            int length = 0;

            while (rs.next()){
                length = rs.getInt(1);
            }

            fcIDComboBox.removeAllItems();
            departureCityComboBox.removeAllItems();
            arrivalCityComboBox.removeAllItems();
            departureDateComboBox.removeAllItems();
            arrivalDateComboBox.removeAllItems();
            departureTimeComboBox.removeAllItems();
            arrivalTimeComboBox.removeAllItems();

            if (length > 0){
                flightCouponsLabel.setText("Flight Coupons: " + length);

                String sql1 = "select * from in2018g16.FlightCoupon where BlankID = ?";

                PreparedStatement stmt1 = con.prepareStatement(sql1);

                stmt1.setInt(1, Integer.valueOf(Integer.valueOf(IDTextField.getText())));

                ResultSet rs1 = stmt1.executeQuery();

                while(rs1.next()){
                    fcIDComboBox.addItem(rs1.getInt(1));
                    departureCityComboBox.addItem(rs1.getString(2));
                    arrivalCityComboBox.addItem(rs1.getString(3));
                    departureDateComboBox.addItem(rs1.getDate(4));
                    arrivalDateComboBox.addItem(rs1.getDate(5));
                    departureTimeComboBox.addItem(rs1.getInt(6));
                    arrivalTimeComboBox.addItem(rs1.getInt(7));
                }

                dataAdded = true;

            } else {
                flightCouponsLabel.setText("Flight Coupons: " + length);
                JOptionPane.showMessageDialog(null, "This blank has no flight coupon associated with it", "Information", JOptionPane.INFORMATION_MESSAGE);
            }



            con.close();

        }catch (Exception x) { System.out.println(x);}

    }

    public void addFcIDComboBoxListener(){

        fcIDComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dataAdded = true){
                    int selectedIndex = fcIDComboBox.getSelectedIndex();
                    departureCityComboBox.setSelectedIndex(selectedIndex);
                    arrivalCityComboBox.setSelectedIndex(selectedIndex);
                    departureDateComboBox.setSelectedIndex(selectedIndex);
                    arrivalDateComboBox.setSelectedIndex(selectedIndex);
                    departureTimeComboBox.setSelectedIndex(selectedIndex);
                    arrivalTimeComboBox.setSelectedIndex(selectedIndex);
                }
            }
        });
    }

}
