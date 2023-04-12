package AirViaLtd;


import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SellTicketPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel infoPanel;
    private JLabel sellTicketLabel;
    private JLabel selectBlankLabel;
    private JTextField interlineDomesticTextField;
    private JTextField basePriceTextField;
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
    private JTextField cardTypeTextField;
    private JTextField cardNumberTextField;
    private JTextField conversionRateTextField;
    private JLabel conversionRateLabel;
    private JPanel menuPanel;
    private JButton homeButton;
    private JButton backButton;

    private AirViaLtd app;

    private String taEmail;
    private int taCode;

    private int selectedBlankType;
    private int selectedBlankNumber;

    private int commissionAmount;

    private int discountedTotal;

    private int selectedTicketNumber;

    private ImageIcon homeIcon;
    private ImageIcon backIcon;

    public SellTicketPage(AirViaLtd a) {
        this.app = a;
        setGraphics();
        addMenuButtonsListener();
        addBlankComboBoxListener();
        addCalculateButtonTotalListener();
        addCommissions();
        addCommissionAmount();
        addSearchButtonListener();
        addPaymentDetails();
        addDiscountedPrice();
        addSellButtonListener();
    }

    public void setGraphics() {

        homeIcon = new ImageIcon("data/home.png");
        homeButton.setPreferredSize(new Dimension(100, 30));
        homeButton.setBorderPainted(false);
        homeButton.setIcon(homeIcon);

        backIcon = new ImageIcon("data/back.png");
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.setBorderPainted(false);
        backButton.setIcon(backIcon);

        interlineDomesticTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        basePriceTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        customerEmailAddressTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        firstNameTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        lastNameTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        advisorCodeTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        localTaxesTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        otherTaxesTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        totalPriceTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        discountedPriceTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        commissionAmountTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        cardTypeTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        cardNumberTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        conversionRateTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));


        calculateTotalButton.setPreferredSize(new Dimension(250, 50));
        calculateTotalButton.setBorder(new LineBorder(Color.WHITE, 1));
        searchButton.setPreferredSize(new Dimension(250, 50));
        searchButton.setBorder(new LineBorder(Color.WHITE, 1));
        sellButton.setPreferredSize(new Dimension(250, 50));
        sellButton.setBorder(new LineBorder(Color.WHITE, 1));
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
                    } else if (discountedPriceTextField.getText().equals("No discount applied") || discountedPriceTextField.getText().equals("Calculate Total Price")){
                        commissionAmount = (Integer.valueOf(totalPriceTextField.getText()) * Integer.valueOf(commissionRateComboBox.getSelectedItem().toString())) / 100;
                        commissionAmountTextField.setText("" + commissionAmount);
                    } else {
                        commissionAmount = (Integer.valueOf(discountedPriceTextField.getText()) * Integer.valueOf(commissionRateComboBox.getSelectedItem().toString())) / 100;
                        commissionAmountTextField.setText("" + commissionAmount);
                    }
                }
            }
        });
    }

    public void addSearchButtonListener(){

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con= DriverManager.getConnection(
                            "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                            "in2018g16_d",
                            "35cnYJLB");

                    String sql = "select FirstName, LastName, Type, FixedDiscount, Rate FROM in2018g16.Customer inner join in2018g16.FlexibleDiscount on in2018g16.Customer.FlexibleDiscountID = in2018g16.FlexibleDiscount.ID where EmailAddress = ?";

                    PreparedStatement stmt = con.prepareStatement(sql);
                    stmt.setString(1, customerEmailAddressTextField.getText());
                    ResultSet rs=stmt.executeQuery();

                    if (!rs.isBeforeFirst() ) {
                        JOptionPane.showMessageDialog(null, "This is not an existing customer", "Info", JOptionPane.INFORMATION_MESSAGE);
                        payLaterComboBox.removeAllItems();
                        payLaterComboBox.addItem("-- Pay Later Not Available --");
                        discountAvailableComboBox.removeAllItems();
                        discountAvailableComboBox.addItem("-- Discount Not Available -- ");
                        firstNameTextField.setText("");
                        lastNameTextField.setText("");

                    } else {
                        while (rs.next()){
                            firstNameTextField.setText(rs.getString(1));
                            lastNameTextField.setText(rs.getString(2));
                            payLaterComboBox.removeAllItems();
                            payLaterComboBox.addItem("-- Pay Later --");
                            payLaterComboBox.addItem("Yes");
                            payLaterComboBox.addItem("No");
                            discountAvailableComboBox.removeAllItems();
                            discountAvailableComboBox.addItem("-- Select Discount Percentage --");
                            discountAvailableComboBox.addItem(rs.getInt(4));
                            discountAvailableComboBox.addItem(rs.getInt(5));
                        }
                    }

                    con.close();
                } catch (Exception ex) { System.out.println(ex);}
            }
        });

    }

    public void addPaymentDetails(){
            paymentTypeComboBox.addItem("-- Select Payment Type --");
            paymentTypeComboBox.addItem("Cash");
            paymentTypeComboBox.addItem("Card");

            cardTypeTextField.setText("Card Type:");
            cardNumberTextField.setText("Card Number:");
            cardTypeTextField.setEditable(false);
            cardNumberTextField.setEditable(false);

            paymentTypeComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (paymentTypeComboBox.getSelectedIndex() != 2){
                        cardTypeTextField.setEditable(false);
                        cardNumberTextField.setEditable(false);
                    } else {
                        addCardDetailsTextFieldListener();
                        cardTypeTextField.setEditable(true);
                        cardNumberTextField.setEditable(true);
                    }
                }
            });

        addCardDetailsTextFieldListener();
    }
    public void addCardDetailsTextFieldListener(){
        cardTypeTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                if (cardTypeTextField.getText().equals("Card Type:") && paymentTypeComboBox.getSelectedIndex() == 2){
                    cardTypeTextField.setText("");
                } else if (!cardTypeTextField.getText().equals("Card Type:") && paymentTypeComboBox.getSelectedIndex() != 2){
                    cardTypeTextField.setText("Card Type:");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                if (cardTypeTextField.getText().equals("") && paymentTypeComboBox.getSelectedIndex() == 2){
                    cardTypeTextField.setText("Card Type:");
                } else if (!cardTypeTextField.getText().equals("") && paymentTypeComboBox.getSelectedIndex() != 2){
                    cardTypeTextField.setText("Card Type:");
                }
            }
        });

        cardNumberTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                if (cardNumberTextField.getText().equals("Card Number:") && paymentTypeComboBox.getSelectedIndex() == 2){
                    cardNumberTextField.setText("");
                } else if (!cardNumberTextField.getText().equals("Card Number:") && paymentTypeComboBox.getSelectedIndex() != 2){
                    cardNumberTextField.setText("Card Number:");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                if (cardNumberTextField.getText().equals("") && paymentTypeComboBox.getSelectedIndex() == 2){
                    cardNumberTextField.setText("Card Number:");
                } else if (!cardNumberTextField.getText().equals("") && paymentTypeComboBox.getSelectedIndex() != 2){
                    cardNumberTextField.setText("Card Number:");
                }
            }
        });

    }


    public void addDiscountedPrice(){

        discountAvailableComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

                if (!totalPriceTextField.getText().equals("Total Price")){
                    if (discountAvailableComboBox.getSelectedIndex() == 0){
                        discountedPriceTextField.setText("No discount applied");
                    } else {
                        discountedTotal = Integer.valueOf(totalPriceTextField.getText()) - Integer.valueOf(totalPriceTextField.getText()) * Integer.valueOf(discountAvailableComboBox.getSelectedItem().toString()) / 100;
                        discountedPriceTextField.setText("" + discountedTotal);
                    }
                } else {
                    discountedPriceTextField.setText("Calculate Total Price");
                }
            }
        });

    }

    public void addSellButtonListener(){

        conversionRateTextField.setText("0");

        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String date = java.time.LocalDate.now().toString();


                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection(
                            "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                            "in2018g16_d",
                            "35cnYJLB");


                    String sql = "select TicketNumber from in2018g16.Ticket inner join in2018g16.Blank on in2018g16.Ticket.BlankID = in2018g16.Blank.ID where in2018g16.Blank.Type = ? and in2018g16.Blank.Number = ?";

                    PreparedStatement stmt = con.prepareStatement(sql);
                    stmt.setInt(1, selectedBlankType);
                    stmt.setInt(2, selectedBlankNumber);

                    ResultSet rs = stmt.executeQuery();

                    while(rs.next()){
                        selectedTicketNumber = rs.getInt(1);
                    }

                    String sql1 = "insert into in2018g16.Sale values (?,?,?,?,?,?,?,?,?,?,?,?)";

                    PreparedStatement stmt1 = con.prepareStatement(sql1);
                    stmt1.setInt(1, Integer.valueOf(advisorCodeTextField.getText()));
                    stmt1.setInt(2, selectedTicketNumber);
                    stmt1.setString(3, date);
                    stmt1.setString(4, interlineDomesticTextField.getText());
                    stmt1.setInt(5, Integer.valueOf(basePriceTextField.getText()));
                    stmt1.setDouble(6, Double.valueOf(conversionRateTextField.getText()));
                    stmt1.setInt(7, Integer.valueOf(localTaxesTextField.getText()) + Integer.valueOf(otherTaxesTextField.getText()));
                    stmt1.setInt(8, Integer.valueOf(totalPriceTextField.getText()));
                    stmt1.setInt(9, commissionAmount);
                    stmt1.setInt(10, Integer.valueOf(commissionRateComboBox.getSelectedItem().toString()));
                    stmt1.setInt(11, 1);
                    stmt1.setString(12, customerEmailAddressTextField.getText());

                    int rs1 = stmt1.executeUpdate();

                    String sql2 = "insert into in2018g16.Payment values (?,?,?,?,?)";

                    PreparedStatement stmt2 = con.prepareStatement(sql2);
                    stmt2.setInt(1, Integer.valueOf(advisorCodeTextField.getText()));
                    stmt2.setInt(2, selectedTicketNumber);
                    stmt2.setString(3, paymentTypeComboBox.getSelectedItem().toString());

                    if (payLaterComboBox.getSelectedIndex() == 1){
                        stmt2.setBoolean(4, true);
                    } else {
                        stmt2.setBoolean(4, false);
                    }

                    if (payLaterComboBox.getSelectedIndex() == 1){
                        stmt2.setBoolean(5, false);
                    } else {
                        stmt2.setBoolean(5, true);
                    }

                    int rs2 = stmt2.executeUpdate();

                    String sql3 = "update in2018g16.Blank set UsedDate = ? where Type = ? and Number = ?";

                    PreparedStatement stmt3 = con.prepareStatement(sql3);
                    stmt3.setString(1, date);
                    stmt3.setInt(2, selectedBlankType);
                    stmt3.setInt(3, selectedBlankNumber);

                    int rs3 = stmt3.executeUpdate();

                    if (rs1 != 0) {
                        JOptionPane.showMessageDialog(null, "Sale made", "Successful Update", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Could not make sale, please retry", "Unsuccessful Update", JOptionPane.ERROR_MESSAGE);
                    }

                    if (rs2 == 0){
                        JOptionPane.showMessageDialog(null, "Could not record payment", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    if (rs3 == 0){
                        JOptionPane.showMessageDialog(null, "Could not update blank", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    con.close();

                } catch (Exception x) {
                    System.out.println(x);
                }
            }

        });
    }
}
