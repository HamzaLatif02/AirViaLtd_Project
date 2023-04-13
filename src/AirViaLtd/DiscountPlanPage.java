package AirViaLtd;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
    private JComboBox flexibleDiscountComboBox;
    private JLabel selectFlexibleDiscountPlanLabel;
    private JPanel menuPanel;
    private JButton homeButton;
    private JButton backButton;
    private JLabel discountPlanLabel;

    private int salesAmount;
    private int fdID;

    private ImageIcon homeIcon;
    private ImageIcon backIcon;

    private AirViaLtd app;

    public DiscountPlanPage(AirViaLtd a) {
        this.app = a;
        setGraphics();
        addMenuButtonsListener();
        addCustomerComboBoxData();
        addMonthComboBox();
        addYearComboBox();
        addGetSalesListener();
        addDiscountPlanComboBoxData();
        addSetDiscountButtonListener();
        addDiscountPlanComboBoxListener();
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

        salesTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        discountPercentageTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));

        getSalesButton.setPreferredSize(new Dimension(250, 50));
        getSalesButton.setBorder(new LineBorder(Color.WHITE, 1));
        setDiscountButton.setPreferredSize(new Dimension(250, 50));
        setDiscountButton.setBorder(new LineBorder(Color.WHITE, 1));

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
                app.transitionToHomepage();
            }
        });
    }

    public void addCustomerComboBoxData(){
        customerComboBox.addItem("-- Select --");

        Connection con = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");
            con.setAutoCommit(false);

            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select EmailAddress, FirstName, LastName FROM in2018g16.Customer");
            con.commit();

            while (rs.next()){
                customerComboBox.addItem(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3));
            }
        } catch (Exception e) {
            System.out.println(e);
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

        Connection con = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");
            con.setAutoCommit(false);

            String sql = "select count(TicketNumber) from in2018g16.Sale where CustomerEmailAddress = ? and SaleDate > ?-? and SaleDate < ?-?;";
            PreparedStatement stmt=con.prepareStatement(sql);

            stmt.setString(1, email);
            stmt.setInt(2, Integer.valueOf(yearComboBox.getSelectedItem().toString()));
            stmt.setInt(3, monthComboBox.getSelectedIndex());
            stmt.setInt(4, Integer.valueOf(yearComboBox.getSelectedItem().toString()));
            stmt.setInt(5, monthComboBox.getSelectedIndex() + 1);

            ResultSet rs=stmt.executeQuery();
            con.commit();

            while (rs.next()){
                salesTextField.setText(rs.getString(1));
            }


        } catch (Exception e) {
            System.out.println(e);
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

                Connection con = null;

                if (validInput()){
                    try{
                        Class.forName("com.mysql.jdbc.Driver");
                        con= DriverManager.getConnection(
                                "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                "in2018g16_d",
                                "35cnYJLB");
                        con.setAutoCommit(false);

                        String sql = "";


                        if (discountPlanComboBox.getSelectedIndex() == 1){
                            sql = "update in2018g16.Customer Set FixedDiscount = ? Where EmailAddress = ?";
                        } else if (discountPlanComboBox.getSelectedIndex() == 2){
                            sql = "update in2018g16.Customer Set FlexibleDiscountID = ? Where EmailAddress = ?";
                        }

                        PreparedStatement stmt=con.prepareStatement(sql);


                        if (discountPlanComboBox.getSelectedIndex() == 1){
                            stmt.setInt(1, Integer.valueOf(discountPercentageTextField.getText()));
                            stmt.setString(2, email);
                        } else if (discountPlanComboBox.getSelectedIndex() == 2){
                            stmt.setInt(1, fdID);
                            stmt.setString(2, email);
                        }

                        int rs=stmt.executeUpdate();
                        con.commit();

                        if (rs != 0){
                            JOptionPane.showMessageDialog(null, "Discount added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                            customerComboBox.setSelectedIndex(0);
                            monthComboBox.setSelectedIndex(0);
                            yearComboBox.setSelectedIndex(0);
                            salesTextField.setText("");
                            discountPlanComboBox.setSelectedIndex(0);
                            discountPercentageTextField.setText("");
                        } else {
                            JOptionPane.showMessageDialog(null, "Could not add discount at the moment, please retry", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (Exception x) {
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

    public boolean validInput(){
            if (Integer.valueOf(discountPercentageTextField.getText()) > -1 && Integer.valueOf(discountPercentageTextField.getText()) < 101){
                return true;
            }
            JOptionPane.showMessageDialog(null, "Value must be between 0 and 100", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
    }

    public void addDiscountPlanComboBoxListener(){
        discountPlanComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (discountPlanComboBox.getSelectedIndex() == 2){
                    flexibleDiscountComboBox.removeAllItems();
                    addFlexiblePlans();
                    addDiscountPercentageText();
                } else {
                    flexibleDiscountComboBox.removeAllItems();
                    discountPercentageTextField.setEditable(true);
                }
            }
        });
    }

    public void addFlexiblePlans(){

        flexibleDiscountComboBox.addItem("-- View --");

        Connection con = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");
            con.setAutoCommit(false);

            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select ID, LowerBound, UpperBound, Rate FROM in2018g16.FlexibleDiscount");
            con.commit();

            while (rs.next()){
                flexibleDiscountComboBox.addItem(rs.getInt(1) + " LowerBound: " + rs.getInt(2) + " UpperBound: " + rs.getInt(3) + " Rate: " + rs.getInt(4));
            }

        } catch (Exception e) {
            System.out.println(e);
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

    public void addDiscountPercentageText(){

        salesAmount = Integer.valueOf(salesTextField.getText());

        Connection con = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");
            con.setAutoCommit(false);

            String sql = "select * from in2018g16.FlexibleDiscount where LowerBound <= ? And UpperBound >= ?";

            PreparedStatement stmt=con.prepareStatement(sql);

            stmt.setInt(1, salesAmount);
            stmt.setInt(2, salesAmount);

            ResultSet rs=stmt.executeQuery();
            con.commit();

            while (rs.next()){
                fdID = rs.getInt(1);
                discountPercentageTextField.setText(rs.getInt(1) + " LowerBound: " + rs.getInt(2) + " UpperBound: " + rs.getInt(3) + " Rate: " + rs.getInt(4));
            }

        } catch (Exception e) {
            System.out.println(e);
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

        discountPercentageTextField.setEditable(false);

    }
}
