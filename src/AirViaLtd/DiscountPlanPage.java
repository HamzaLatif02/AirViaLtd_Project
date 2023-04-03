package AirViaLtd;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DiscountPlanPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel featurePanel;
    private JLabel selectCustomerLabel;
    private JComboBox customerComboBox;
    private JLabel selectMonthLabel;
    private JTextField salesTextField;
    private JLabel salesDuringThisPeriodLabel;
    private JComboBox discountPlanComboBox;
    private JLabel selectDiscountPlanLabel;
    private JTextField discountPercentageTextField;
    private JLabel discountPercentageLabel;
    private JButton setDiscountButton;
    private JComboBox monthComboBox;
    private JComboBox yearComboBox;
    private JLabel selectYearLabel;
    private JButton getSalesButton;


    private AirViaLtd app;

    public DiscountPlanPage(AirViaLtd a) {
        this.app = a;
        addCustomerComboBoxData();
        addMonthComboBox();
        addYearComboBox();
        addGetSalesListener();
        addDiscountPlanComboBoxData();
        addSetDiscountButtonListener();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }


    public void addCustomerComboBoxData(){
        customerComboBox.addItem("-- Select --");

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");

            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select EmailAddress, FirstName, LastName FROM in2018g16.Customer");

            while (rs.next()){
                customerComboBox.addItem(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3));
            }
            con.close();
        } catch (Exception e) { System.out.println(e);}
    }

    public void addMonthComboBox(){
        monthComboBox.addItem("-- Select --");

        monthComboBox.addItem("January");
        monthComboBox.addItem("February");
        monthComboBox.addItem("March");
        monthComboBox.addItem("April");
        monthComboBox.addItem("May");
        monthComboBox.addItem("June");
        monthComboBox.addItem("July");
        monthComboBox.addItem("August");
        monthComboBox.addItem("September");
        monthComboBox.addItem("October");
        monthComboBox.addItem("November");
        monthComboBox.addItem("December");


    }

    public void addYearComboBox(){
        yearComboBox.addItem("-- Select --");

        for (int i = 2023; i > 1999; i--){
            yearComboBox.addItem(i);
        }
    }

    public void addGetSalesListener(){
        getSalesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSalesDuringPeriodData();
            }
        });

    }
    public void addSalesDuringPeriodData(){

        String cu = customerComboBox.getSelectedItem().toString();
        String[] splitCuSelected = cu.split("\\s+");
        String email = (splitCuSelected[0]);

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");

            String sql = "select count(TicketNumber) from in2018g16.Sale where CustomerEmailAddress = ? and SaleDate > ?-? and SaleDate < ?-?;";
            PreparedStatement stmt=con.prepareStatement(sql);

            stmt.setString(1, email);
            stmt.setInt(2, Integer.valueOf(yearComboBox.getSelectedItem().toString()));
            stmt.setInt(3, monthComboBox.getSelectedIndex());
            stmt.setInt(4, Integer.valueOf(yearComboBox.getSelectedItem().toString()));
            stmt.setInt(5, monthComboBox.getSelectedIndex() + 1);

            ResultSet rs=stmt.executeQuery();

            while (rs.next()){
                salesTextField.setText(rs.getString(1));
            }
            con.close();
        } catch (Exception e) { System.out.println(e);}
    }

    public void addDiscountPlanComboBoxData(){
        discountPlanComboBox.addItem("-- Select --");
        discountPlanComboBox.addItem("Fixed");
        discountPlanComboBox.addItem("Flexible");
    }

    public void addSetDiscountButtonListener(){
        setDiscountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String cu = customerComboBox.getSelectedItem().toString();
                String[] splitCuSelected = cu.split("\\s+");
                String email = (splitCuSelected[0]);



                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con= DriverManager.getConnection(
                            "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                            "in2018g16_d",
                            "35cnYJLB");

                    String sql = "";

                    if (discountPlanComboBox.getSelectedIndex() == 1){
                        sql = "update in2018g16.Customer Set FixedDiscount = ? Where EmailAddress = ?";
                    } else {
                        sql = "update in2018g16.Customer Set FlexibleDiscount = ? Where EmailAddress = ?";
                    }

                    PreparedStatement stmt=con.prepareStatement(sql);

                    stmt.setInt(1, Integer.valueOf(discountPercentageTextField.getText()));
                    stmt.setString(2, email);


                    int rs=stmt.executeUpdate();

                    if (rs != 0){
                        JOptionPane.showMessageDialog(null, "Discount added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        customerComboBox.setSelectedIndex(0);
                        monthComboBox.setSelectedIndex(0);
                        yearComboBox.setSelectedIndex(0);
                        salesTextField.setText("");
                        discountPlanComboBox.setSelectedIndex(0);
                        discountPercentageLabel.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Could not add discount at the moment, please retry", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    con.close();
                } catch (Exception x) { System.out.println(x);}
            }
        });
    }
}
