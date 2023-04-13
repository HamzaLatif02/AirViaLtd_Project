package AirViaLtd;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;

public class IssueRefundPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel functionsPanel;
    private JLabel issueRefundLabel;
    private JLabel ticketNumberLabel;
    private JTextField paymentTypeTextField;
    private JTextField refundAmountTextField;
    private JLabel paymentTypeLabel;
    private JLabel refundAmountLabel;
    private JButton issueRefundButton;
    private JComboBox blankComboBox;
    private JLabel advisorCodeLabel;
    private JTextField advisorCodeTextField;
    private JButton homeButton;
    private JButton backButton;
    private JPanel menuPanel;

    private String taEmail;
    private int taCode;

    private int selectedBlankType;
    private int selectedBlankNumber;

    private int tNumber;

    private ImageIcon homeIcon;
    private ImageIcon backIcon;
    private AirViaLtd app;

    //constructor
    public IssueRefundPage(AirViaLtd a) {
        this.app = a;
        setGraphics();
        addMenuButtonsListener();
        addSaleDetails();
        addIssueRefundButtonListener();
    }

    //set page graphics
    public void setGraphics() {

        homeIcon = new ImageIcon("data/home.png");
        homeButton.setPreferredSize(new Dimension(100, 30));
        homeButton.setBorderPainted(false);
        homeButton.setIcon(homeIcon);

        backIcon = new ImageIcon("data/back.png");
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.setBorderPainted(false);
        backButton.setIcon(backIcon);

        advisorCodeTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        paymentTypeTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        refundAmountTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));

        issueRefundButton.setPreferredSize(new Dimension(250, 50));
        issueRefundButton.setBorder(new LineBorder(Color.WHITE, 1));
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
                app.transitionToHomepage();
            }
        });
    }

    public void addAdvisorCode(String email){
        this.taEmail = email;

        Connection con = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");
            con.setAutoCommit(false);

            String sql = "select AdvisorCode from in2018g16.TravelAdvisor where EmailAddress = ?";

            PreparedStatement stmt= con.prepareStatement(sql);

            stmt.setString(1, taEmail);

            ResultSet rs = stmt.executeQuery();

            con.commit();

            while (rs.next()){
                taCode = rs.getInt(1);
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

        advisorCodeTextField.setText("" + taCode);
    }

    //add sold blanks
    public void addBlanks(){
        blankComboBox.addItem("-- Select Blank --");

        Connection con = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");
            con.setAutoCommit(false);

            String sql = "select Type, Number FROM in2018g16.Blank INNER JOIN in2018g16.Ticket on in2018g16.Blank.ID = in2018g16.Ticket.BlankID where in2018g16.Blank.AdvisorCode = ? and in2018g16.Blank.UsedDate is not null";

            PreparedStatement stmt=con.prepareStatement(sql);
            stmt.setInt(1, taCode);

            ResultSet rs=stmt.executeQuery();

            con.commit();

            while (rs.next()){
                blankComboBox.addItem(rs.getInt(1) + " " + rs.getInt(2));
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

    //add sale details of the selected blank
    public void addSaleDetails(){
        blankComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (blankComboBox.getSelectedIndex() != 0){

                    String b = (String) blankComboBox.getSelectedItem();
                    String[] splitBSelected = b.split("\\s+");
                    selectedBlankType = Integer.valueOf(splitBSelected[0]);
                    selectedBlankNumber = Integer.valueOf(splitBSelected[1]);

                    Connection con = null;

                    try{
                        Class.forName("com.mysql.jdbc.Driver");
                        con= DriverManager.getConnection(
                                "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                "in2018g16_d",
                                "35cnYJLB");
                        con.setAutoCommit(false);

                        String sql = "select PaymentType FROM in2018g16.Payment INNER JOIN in2018g16.Sale on in2018g16.Payment.TicketNumber = in2018g16.Sale.TicketNumber inner join in2018g16.Ticket on in2018g16.Sale.TicketNumber = in2018g16.Ticket.TicketNumber inner join in2018g16.Blank on in2018g16.Ticket.BlankID = in2018g16.Blank.ID where in2018g16.Blank.Type = ? and in2018g16.Blank.Number = ?";

                        PreparedStatement stmt=con.prepareStatement(sql);
                        stmt.setInt(1, selectedBlankType);
                        stmt.setInt(2, selectedBlankNumber);

                        ResultSet rs=stmt.executeQuery();

                        while (rs.next()){
                            paymentTypeTextField.setText(rs.getString(1));
                        }

                        String sql1 = "select TotalSale from in2018g16.Sale inner join in2018g16.Ticket on in2018g16.Sale.TicketNumber = in2018g16.Ticket.TicketNumber inner join in2018g16.Blank on in2018g16.Ticket.BlankID = in2018g16.Blank.ID where in2018g16.Blank.Type = ? and in2018g16.Blank.Number = ?";
                        PreparedStatement stmt1 = con.prepareStatement(sql1);
                        stmt1.setInt(1, selectedBlankType);
                        stmt1.setInt(2, selectedBlankNumber);

                        ResultSet rs1 = stmt1.executeQuery();

                        while (rs1.next()){
                            refundAmountTextField.setText("" + rs1.getInt(1));
                        }

                        String sql2 = "select in2018g16.Sale.TicketNumber from in2018g16.Sale inner join in2018g16.Ticket on in2018g16.Sale.TicketNumber = in2018g16.Ticket.TicketNumber inner join in2018g16.Blank on in2018g16.Ticket.BlankID = in2018g16.Blank.ID where in2018g16.Blank.Type = ? and in2018g16.Blank.Number = ?";
                        PreparedStatement stmt2 = con.prepareStatement(sql2);
                        stmt2.setInt(1, selectedBlankType);
                        stmt2.setInt(2, selectedBlankNumber);

                        ResultSet rs2 = stmt2.executeQuery();

                        con.commit();

                        while (rs2.next()){
                            tNumber = rs2.getInt(1);
                        }

                    } catch (Exception ex) {
                        System.out.println(ex);
                        try {
                            con.rollback();
                        } catch (SQLException x) {
                            throw new RuntimeException(x);
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

    //set blank as not sold and insert refund into the database
    public void addIssueRefundButtonListener(){
        issueRefundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (blankComboBox.getSelectedIndex() == 0){
                    JOptionPane.showMessageDialog(null, "Select a blank to refund", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String date = java.time.LocalDate.now().toString();

                    Connection con = null;

                    try{
                        Class.forName("com.mysql.jdbc.Driver");
                        con= DriverManager.getConnection(
                                "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                "in2018g16_d",
                                "35cnYJLB");
                        con.setAutoCommit(false);

                        String sql = "insert into in2018g16.Refund values (?,?,?,?,?)";
                        PreparedStatement stmt=con.prepareStatement(sql);
                        stmt.setInt(1, taCode);
                        stmt.setInt(2, tNumber);
                        stmt.setString(3, paymentTypeTextField.getText());
                        stmt.setInt(4, Integer.valueOf(refundAmountTextField.getText()));
                        stmt.setString(5, date);

                        int rs = stmt.executeUpdate();

                        if (rs != 0){
                            JOptionPane.showMessageDialog(null, "Refund Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Could not make this refund, please retry", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                        String sql1 = "update in2018g16.Blank set UsedDate = null where Type = ? and Number = ?";
                        PreparedStatement stmt1 = con.prepareStatement(sql1);
                        stmt1.setInt(1, selectedBlankType);
                        stmt1.setInt(2, selectedBlankNumber);

                        int rs1 = stmt1.executeUpdate();

                        con.commit();

                        if (rs1 != 0){
                            JOptionPane.showMessageDialog(null, "Blank Updated", "Blank Updated", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Could not update blank, please retry", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                        if (rs != 0 && rs1 !=0){
                            blankComboBox.removeAllItems();
                            addBlanks();
                            paymentTypeTextField.setText("");
                            refundAmountTextField.setText("");
                        }

                    } catch (Exception ex) {
                        System.out.println(ex);
                        try {
                            con.rollback();
                        } catch (SQLException x) {
                            throw new RuntimeException(x);
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
}
