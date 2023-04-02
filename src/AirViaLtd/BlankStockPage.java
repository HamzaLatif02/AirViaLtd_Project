package AirViaLtd;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class BlankStockPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel featurePanel;
    private JLabel blankStockLabel;
    private JButton IDButton;
    private JButton typeButton;
    private JButton travelAdvisorButton;
    private JLabel sortByLabel;
    private JButton notAssignedButton;
    private JButton dateButton;
    private JButton usedButton;
    private JPanel tablePanel;

    private JScrollPane blankStockScrollPane;
    private DefaultTableModel model;
    private JTable table;

    private int counter;

    private AirViaLtd app;

    public BlankStockPage(AirViaLtd a) {
        this.app = a;
        this.counter = 0;

        addTableData(null, null);
        addIDButtonListener();
        addTypeButtonListener();
        addDateButtonListener();
        addTravelAdvisorButtonListener();
        addNotAssignedButtonListener();
        addUsedButtonListener();

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JScrollPane getBlankStockScrollPane() {
        return blankStockScrollPane;
    }

    public void addTableData(String filter, String ascOrDesc){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");


            String sql;

            if (filter == null){
                sql = "select ID, Type, Number, NewlyReceived, ReceivedDate, AssignedDate, UsedDate, AdvisorCode, AuditCouponID FROM in2018g16.Blank";
            } else {
                sql = "select ID, Type, Number, NewlyReceived, ReceivedDate, AssignedDate, UsedDate, AdvisorCode, AuditCouponID FROM in2018g16.Blank ORDER BY ? ?";
            }

            PreparedStatement stmt = con.prepareStatement(sql);

            if (filter != null){
                stmt.setString(1, filter);
                stmt.setString(2, ascOrDesc);
            }

            ResultSet rs = stmt.executeQuery();


            model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Type");
            model.addColumn("Number");
            model.addColumn("Newly Received");
            model.addColumn("Received Date");
            model.addColumn("Assigned Date");
            model.addColumn("Used Date");
            model.addColumn("Advisor Code");
            model.addColumn("Audit Coupon ID");

            while(rs.next()){
                Object[] row = new Object[9];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getInt(3);
                row[3] = rs.getBoolean(4);
                row[4] = rs.getDate(5);
                row[5] = rs.getDate(6);
                row[6] = rs.getDate(7);
                row[7] = rs.getInt(8);
                row[8] = rs.getInt(9);
                model.addRow(row);
            }


            table = new JTable();
            table.setModel(model);
            table.setDefaultEditor(Object.class, null);

            blankStockScrollPane = new JScrollPane();
            blankStockScrollPane.setViewportView(table);

            con.close();

        }catch (Exception e) { System.out.println(e);}

    }

    public void addIDButtonListener(){

        IDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection(
                            "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                            "in2018g16_d",
                            "35cnYJLB");

                    String sql;

                    sql = "select ID, Type, Number, NewlyReceived, ReceivedDate, AssignedDate, UsedDate, AdvisorCode, AuditCouponID FROM in2018g16.Blank ORDER BY ID DESC";

                    Statement stmt = con.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery(sql);


                    model = new DefaultTableModel();

                    model.addColumn("ID");
                    model.addColumn("Type");
                    model.addColumn("Number");
                    model.addColumn("Newly Received");
                    model.addColumn("Received Date");
                    model.addColumn("Assigned Date");
                    model.addColumn("Used Date");
                    model.addColumn("Advisor Code");
                    model.addColumn("Audit Coupon ID");

                    while(rs.next()){
                        Object[] row = new Object[9];
                        row[0] = rs.getInt(1);
                        row[1] = rs.getInt(2);
                        row[2] = rs.getInt(3);
                        row[3] = rs.getBoolean(4);
                        row[4] = rs.getDate(5);
                        row[5] = rs.getDate(6);
                        row[6] = rs.getDate(7);
                        row[7] = rs.getInt(8);
                        row[8] = rs.getInt(9);
                        model.addRow(row);
                    }


                    table.setModel(model);
                    table.setDefaultEditor(Object.class, null);


                    blankStockScrollPane.repaint();

                    con.close();

                }catch (Exception x) { System.out.println(x);}

            }
        });
    }

    public void addTypeButtonListener(){
        typeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection(
                            "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                            "in2018g16_d",
                            "35cnYJLB");

                    String sql;

                    sql = "select ID, Type, Number, NewlyReceived, ReceivedDate, AssignedDate, UsedDate, AdvisorCode, AuditCouponID FROM in2018g16.Blank ORDER BY Type ASC";

                    Statement stmt = con.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery(sql);


                    model = new DefaultTableModel();

                    model.addColumn("ID");
                    model.addColumn("Type");
                    model.addColumn("Number");
                    model.addColumn("Newly Received");
                    model.addColumn("Received Date");
                    model.addColumn("Assigned Date");
                    model.addColumn("Used Date");
                    model.addColumn("Advisor Code");
                    model.addColumn("Audit Coupon ID");

                    while(rs.next()){
                        Object[] row = new Object[9];
                        row[0] = rs.getInt(1);
                        row[1] = rs.getInt(2);
                        row[2] = rs.getInt(3);
                        row[3] = rs.getBoolean(4);
                        row[4] = rs.getDate(5);
                        row[5] = rs.getDate(6);
                        row[6] = rs.getDate(7);
                        row[7] = rs.getInt(8);
                        row[8] = rs.getInt(9);
                        model.addRow(row);
                    }


                    table.setModel(model);
                    table.setDefaultEditor(Object.class, null);

                    blankStockScrollPane.repaint();

                    con.close();

                }catch (Exception x) { System.out.println(x);}
            }
        });
    }

    public void addDateButtonListener(){
        dateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection(
                            "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                            "in2018g16_d",
                            "35cnYJLB");

                    String sql;

                    sql = "select ID, Type, Number, NewlyReceived, ReceivedDate, AssignedDate, UsedDate, AdvisorCode, AuditCouponID FROM in2018g16.Blank ORDER BY ReceivedDate DESC";

                    Statement stmt = con.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery(sql);


                    model = new DefaultTableModel();

                    model.addColumn("ID");
                    model.addColumn("Type");
                    model.addColumn("Number");
                    model.addColumn("Newly Received");
                    model.addColumn("Received Date");
                    model.addColumn("Assigned Date");
                    model.addColumn("Used Date");
                    model.addColumn("Advisor Code");
                    model.addColumn("Audit Coupon ID");

                    while(rs.next()){
                        Object[] row = new Object[9];
                        row[0] = rs.getInt(1);
                        row[1] = rs.getInt(2);
                        row[2] = rs.getInt(3);
                        row[3] = rs.getBoolean(4);
                        row[4] = rs.getDate(5);
                        row[5] = rs.getDate(6);
                        row[6] = rs.getDate(7);
                        row[7] = rs.getInt(8);
                        row[8] = rs.getInt(9);
                        model.addRow(row);
                    }


                    table.setModel(model);
                    table.setDefaultEditor(Object.class, null);

                    blankStockScrollPane.repaint();

                    con.close();

                }catch (Exception x) { System.out.println(x);}
            }
        });
    }

    public void addTravelAdvisorButtonListener(){
        travelAdvisorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection(
                            "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                            "in2018g16_d",
                            "35cnYJLB");

                    String sql;

                    sql = "select ID, Type, Number, NewlyReceived, ReceivedDate, AssignedDate, UsedDate, AdvisorCode, AuditCouponID FROM in2018g16.Blank ORDER BY AdvisorCode ASC";

                    Statement stmt = con.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery(sql);


                    model = new DefaultTableModel();

                    model.addColumn("ID");
                    model.addColumn("Type");
                    model.addColumn("Number");
                    model.addColumn("Newly Received");
                    model.addColumn("Received Date");
                    model.addColumn("Assigned Date");
                    model.addColumn("Used Date");
                    model.addColumn("Advisor Code");
                    model.addColumn("Audit Coupon ID");

                    while(rs.next()){
                        Object[] row = new Object[9];
                        row[0] = rs.getInt(1);
                        row[1] = rs.getInt(2);
                        row[2] = rs.getInt(3);
                        row[3] = rs.getBoolean(4);
                        row[4] = rs.getDate(5);
                        row[5] = rs.getDate(6);
                        row[6] = rs.getDate(7);
                        row[7] = rs.getInt(8);
                        row[8] = rs.getInt(9);
                        model.addRow(row);
                    }


                    table.setModel(model);
                    table.setDefaultEditor(Object.class, null);

                    blankStockScrollPane.repaint();

                    con.close();

                }catch (Exception x) { System.out.println(x);}
            }
        });
    }

    public void addNotAssignedButtonListener(){
        notAssignedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection(
                            "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                            "in2018g16_d",
                            "35cnYJLB");

                    String sql;

                    sql = "select ID, Type, Number, NewlyReceived, ReceivedDate, AssignedDate, UsedDate, AdvisorCode, AuditCouponID FROM in2018g16.Blank WHERE AdvisorCode = 1";

                    Statement stmt = con.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery(sql);


                    model = new DefaultTableModel();

                    model.addColumn("ID");
                    model.addColumn("Type");
                    model.addColumn("Number");
                    model.addColumn("Newly Received");
                    model.addColumn("Received Date");
                    model.addColumn("Assigned Date");
                    model.addColumn("Used Date");
                    model.addColumn("Advisor Code");
                    model.addColumn("Audit Coupon ID");

                    while(rs.next()){
                        Object[] row = new Object[9];
                        row[0] = rs.getInt(1);
                        row[1] = rs.getInt(2);
                        row[2] = rs.getInt(3);
                        row[3] = rs.getBoolean(4);
                        row[4] = rs.getDate(5);
                        row[5] = rs.getDate(6);
                        row[6] = rs.getDate(7);
                        row[7] = rs.getInt(8);
                        row[8] = rs.getInt(9);
                        model.addRow(row);
                    }


                    table.setModel(model);
                    table.setDefaultEditor(Object.class, null);

                    blankStockScrollPane.repaint();

                    con.close();

                }catch (Exception x) { System.out.println(x);}
            }
        });
    }

    public void addUsedButtonListener(){
        usedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection(
                            "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                            "in2018g16_d",
                            "35cnYJLB");

                    String sql;

                    sql = "select ID, Type, Number, NewlyReceived, ReceivedDate, AssignedDate, UsedDate, AdvisorCode, AuditCouponID FROM in2018g16.Blank WHERE UsedDate != null";

                    Statement stmt = con.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery(sql);


                    model = new DefaultTableModel();

                    model.addColumn("ID");
                    model.addColumn("Type");
                    model.addColumn("Number");
                    model.addColumn("Newly Received");
                    model.addColumn("Received Date");
                    model.addColumn("Assigned Date");
                    model.addColumn("Used Date");
                    model.addColumn("Advisor Code");
                    model.addColumn("Audit Coupon ID");

                    while(rs.next()){
                        Object[] row = new Object[9];
                        row[0] = rs.getInt(1);
                        row[1] = rs.getInt(2);
                        row[2] = rs.getInt(3);
                        row[3] = rs.getBoolean(4);
                        row[4] = rs.getDate(5);
                        row[5] = rs.getDate(6);
                        row[6] = rs.getDate(7);
                        row[7] = rs.getInt(8);
                        row[8] = rs.getInt(9);
                        model.addRow(row);
                    }


                    table.setModel(model);
                    table.setDefaultEditor(Object.class, null);

                    blankStockScrollPane.repaint();

                    con.close();

                }catch (Exception x) { System.out.println(x);}
            }
        });
    }
}
