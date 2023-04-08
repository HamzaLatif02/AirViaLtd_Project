package AirViaLtd;

import javax.swing.*;
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

    public SellTicketPage(AirViaLtd a) {
        this.app = a;

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


            String sql = "select AdvisorCode from in2018g16.TravelAdvisor where EmailAddress = ? ";

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
}
