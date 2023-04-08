package AirViaLtd;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;

public class SellTicketPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel infoPanel;
    private JLabel sellTicketLabel;
    private JLabel selectBlankLabel;
    private JTextField interlineDomesticTextField;
    private JTextField basePriceTextField;
    private JLabel customerEmailAddressLabel;
    private JTextField customerEmailAddressTextField;
    private JComboBox commissionRateComboBox;
    private JLabel selectCommissionRateLabel;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JLabel advisorCodeLabel;
    private JTextField advisorCodeTextField;
    private JLabel addTaxesLabel;
    private JTextField localTaxesTextField;
    private JTextField otherTaxesTextField;
    private JButton calculateTotalButton;
    private JTextField totalPriceTextField;
    private JTextField commissionAmountTextField;
    private JLabel commissionAmountLabel;
    private JButton searchButton;
    private JComboBox paymentTypeComboBox;
    private JComboBox payLaterComboBox;
    private JComboBox discountAvailableComboBox;
    private JTextField discountedPriceTextField;
    private JButton sellButton;
    private JComboBox blankComboBox;

    private AirViaLtd app;

    private String taEmail;
    private int taCode;

    private int selectedBlankType;
    private int selectedBlankNumber;

    private int commissionAmount;

    public SellTicketPage(AirViaLtd a) {
        this.app = a;
        addBlankComboBoxListener();
        addCalculateButtonTotalListener();
        addCommissions();
        addCommissionAmount();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }




    public void addAdvisorCode(String email){

        this.taEmail = email;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");


            String sql = "select AdvisorCode from in2018g16.TravelAdvisor where EmailAddress = ?";

            PreparedStatement stmt= con.prepareStatement(sql);

            stmt.setString(1, taEmail);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                taCode = rs.getInt(1);
            }

            con.close();

        }catch(Exception x){ System.out.println(x);}

        advisorCodeTextField.setText("" + taCode);
    }

    public void addBlanks(){
        blankComboBox.addItem("-- Select Blank --");

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");

            String sql = "select Type, Number FROM in2018g16.Blank INNER JOIN in2018g16.Ticket on in2018g16.Blank.ID = in2018g16.Ticket.BlankID where in2018g16.Blank.AdvisorCode = ? and in2018g16.Blank.UsedDate is null";

            PreparedStatement stmt=con.prepareStatement(sql);
            stmt.setInt(1, taCode);

            ResultSet rs=stmt.executeQuery();

            while (rs.next()){
                blankComboBox.addItem(rs.getInt(1) + " " + rs.getInt(2));
            }
            con.close();
        } catch (Exception e) { System.out.println(e);}
    }

    public void addBlankComboBoxListener(){
        blankComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (blankComboBox.getSelectedIndex() == 0){
                    interlineDomesticTextField.setText("Interline/Domestic");
                    basePriceTextField.setText("Base Price");
                } else {
                    addBlankDetails();
                }
            }
        });
    }

    public void addBlankDetails(){
        String b = (String) blankComboBox.getSelectedItem();
        String[] splitBSelected = b.split("\\s+");
        selectedBlankType = Integer.valueOf(splitBSelected[0]);
        selectedBlankNumber = Integer.valueOf(splitBSelected[1]);

        if (selectedBlankType == 444 || selectedBlankType == 440 || selectedBlankType == 420){
            interlineDomesticTextField.setText("Interline");
        } else if (selectedBlankType == 201 || selectedBlankType == 101){
            interlineDomesticTextField.setText("Domestic");
        } else if (selectedBlankType == 451 || selectedBlankType == 452){
            interlineDomesticTextField.setText("MCO");
        }

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");

            String sql = "select Price FROM in2018g16.Ticket INNER JOIN in2018g16.Blank on in2018g16.Ticket.BlankID = in2018g16.Blank.ID where in2018g16.Blank.Type = ? and in2018g16.Blank.Number = ?";

            PreparedStatement stmt=con.prepareStatement(sql);
            stmt.setInt(1, selectedBlankType);
            stmt.setInt(2, selectedBlankNumber);

            ResultSet rs=stmt.executeQuery();

            while (rs.next()){
                basePriceTextField.setText("" + rs.getInt(1));
            }
            con.close();
        } catch (Exception e) { System.out.println(e);}

    }

    public void addCalculateButtonTotalListener(){
        calculateTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (localTaxesTextField.getText().equals("") && otherTaxesTextField.getText().equals("")){
                    totalPriceTextField.setText(basePriceTextField.getText());
                } else if (localTaxesTextField.getText().equals("") && !otherTaxesTextField.getText().equals("")){
                    int totalPrice = Integer.valueOf(basePriceTextField.getText()) + Integer.valueOf(localTaxesTextField.getText());
                    totalPriceTextField.setText(""+ totalPrice);
                } else if (!localTaxesTextField.getText().equals("") && otherTaxesTextField.getText().equals("")) {
                    int totalPrice = Integer.valueOf(basePriceTextField.getText()) + Integer.valueOf(otherTaxesTextField.getText());
                    totalPriceTextField.setText("" + totalPrice);
                } else if (!localTaxesTextField.getText().equals("") && !otherTaxesTextField.getText().equals("")) {
                    int totalPrice = Integer.valueOf(basePriceTextField.getText()) + Integer.valueOf(localTaxesTextField.getText()) + Integer.valueOf(otherTaxesTextField.getText());
                    totalPriceTextField.setText("" + totalPrice);
                }
            }
        });

    }

    public void addCommissions(){

        commissionRateComboBox.addItem("-- Select Commission --");

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");

            String sql = "select CommissionRate FROM in2018g16.Commission where TravelAgentID = 1 ";

            Statement stmt = con.createStatement();

            ResultSet rs=stmt.executeQuery(sql);

            while (rs.next()){
                commissionRateComboBox.addItem(rs.getInt(1));
            }
            con.close();
        } catch (Exception e) { System.out.println(e);}
    }


    public void addCommissionAmount(){
        commissionRateComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (commissionRateComboBox.getSelectedIndex() == 0){
                    commissionAmountTextField.setText("Select a Commission Rate");
                } else {
                    if (totalPriceTextField.getText().equals("Total Price")){
                        commissionAmountTextField.setText("Calculate Total Price");
                    } else {
                        commissionAmount = (Integer.valueOf(totalPriceTextField.getText()) * Integer.valueOf(commissionRateComboBox.getSelectedItem().toString())) / 100;
                        commissionAmountTextField.setText("" + commissionAmount);
                    }
                }
            }
        });
    }
}
