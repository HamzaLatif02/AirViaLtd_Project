package AirViaLtd;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CreateReportPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel featurePanel;
    private JComboBox selectReportComboBox;
    private JLabel createReportLabel;
    private JLabel selectReportLabel;
    private JLabel individualOrGlobalLabel;
    private JComboBox individualGlobalComboBox;
    private JComboBox travelAdvisorComboBox;
    private JLabel selectTravelAdvisorLabel;
    private JLabel startDateLabel;
    private JButton createButton;
    private JButton downloadButton;
    private JLabel endDateLabel;
    private JComboBox startYearComboBox;
    private JComboBox startMonthComboBox;
    private JComboBox endYearComboBox;
    private JComboBox endMonthComboBox;
    private JButton nextButton;
    private JButton previousButton;

    private JScrollPane reportScrollPane;
    private DefaultTableModel model;
    private JTable table;
    private DefaultTableModel model1;
    private JTable table1;

    private DefaultTableModel model2;
    private JTable table2;

    private DefaultTableModel model3;
    private JTable table3;
    private DefaultTableModel model4;
    private JTable table4;
    private DefaultTableModel model5;
    private JTable table5;

    private boolean reportCreated;

    private int index;

    private AirViaLtd app;

    public CreateReportPage(AirViaLtd a) {
        this.app = a;
        this.reportCreated = false;

        addDateComboBoxData();
        addSelectReportData();
        addIndividualOrGlobal();
        addTravelAdvisorData();
        addCreateButtonListener();
        addNextButtonListener();
        addPreviousButtonListener();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JScrollPane getReportScrollPane() {
        return reportScrollPane;
    }


    public void addDateComboBoxData(){

        startYearComboBox.addItem("-- Select Year --");

        for (int i = 2023; i > 1999; i--){
            startYearComboBox.addItem(i);
        }

        startMonthComboBox.addItem("-- Select Month --");

        startMonthComboBox.addItem("January");
        startMonthComboBox.addItem("February");
        startMonthComboBox.addItem("March");
        startMonthComboBox.addItem("April");
        startMonthComboBox.addItem("May");
        startMonthComboBox.addItem("June");
        startMonthComboBox.addItem("July");
        startMonthComboBox.addItem("August");
        startMonthComboBox.addItem("September");
        startMonthComboBox.addItem("October");
        startMonthComboBox.addItem("November");
        startMonthComboBox.addItem("December");

        endYearComboBox.addItem("-- Select Year --");

        for (int i = 2023; i > 1999; i--){
            endYearComboBox.addItem(i);
        }

        endMonthComboBox.addItem("-- Select Month --");

        endMonthComboBox.addItem("January");
        endMonthComboBox.addItem("February");
        endMonthComboBox.addItem("March");
        endMonthComboBox.addItem("April");
        endMonthComboBox.addItem("May");
        endMonthComboBox.addItem("June");
        endMonthComboBox.addItem("July");
        endMonthComboBox.addItem("August");
        endMonthComboBox.addItem("September");
        endMonthComboBox.addItem("October");
        endMonthComboBox.addItem("November");
        endMonthComboBox.addItem("December");

    }

    public void addSelectReportData(){
        selectReportComboBox.addItem("-- Select --");
        selectReportComboBox.addItem("Ticket Stock Turnover");
        selectReportComboBox.addItem("Interline Sales");
        selectReportComboBox.addItem("Global Sales");
    }

    public void addIndividualOrGlobal(){

        individualGlobalComboBox.addItem("-- Select --");
        individualGlobalComboBox.addItem("Individual");
        individualGlobalComboBox.addItem("Global");

    }

    public void addTravelAdvisorData(){

        travelAdvisorComboBox.addItem("-- Select -- ");

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");

            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select AdvisorCode, FirstName, LastName FROM in2018g16.TravelAdvisor WHERE AdvisorCode != 1");

            while (rs.next()){
                travelAdvisorComboBox.addItem(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3));
            }
            con.close();
        } catch (Exception e) { System.out.println(e);}
    }

    public void addCreateButtonListener(){
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createReport();
                app.addReportToPage();
                reportCreated = true;
            }
        });
    }

    public void createReport(){
        if (selectReportComboBox.getSelectedIndex() == 1){
            createTicketStockReport();
            index = 1;
        }

    }

    public void createTicketStockReport(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");


            String sql = "select Type,Number From in2018g16.Blank Where NewlyReceived = 1 And ReceivedDate BETWEEN '2017-01-01' and '2024-01-01' order by Type";
            String sql1 = "select Type,Number From in2018g16.Blank Where NewlyReceived = 1 And ReceivedDate BETWEEN '2017-01-01' and '2024-01-01' And AdvisorCode != 1 order by Type";
            String sql2 = "select AdvisorCode,Type,Number From in2018g16.Blank Where NewlyReceived = 0 And AssignedDate BETWEEN '2017-01-01' and '2024-01-01' And AdvisorCode != 1";
            String sql3 = "select Type,Number From in2018g16.Blank Where UsedDate BETWEEN '2017-01-01' and '2024-01-01' And AdvisorCode != 1";
            String sql4 = "select Type,Number From in2018g16.Blank Where ReceivedDate <= '2024-01-01' And UsedDate IS NULL";
            String sql5 = "select AdvisorCode,Type,Number From in2018g16.Blank Where ReceivedDate <= '2024-01-01' And UsedDate IS NULL";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            Statement stmt1 = con.createStatement();
            ResultSet rs1 = stmt1.executeQuery(sql1);

            Statement stmt2 = con.createStatement();
            ResultSet rs2 = stmt2.executeQuery(sql2);

            Statement stmt3 = con.createStatement();
            ResultSet rs3 = stmt3.executeQuery(sql3);

            Statement stmt4 = con.createStatement();
            ResultSet rs4 = stmt4.executeQuery(sql4);

            Statement stmt5 = con.createStatement();
            ResultSet rs5 = stmt5.executeQuery(sql5);

            model = new DefaultTableModel();
            model.addColumn("Received Blank Type");
            model.addColumn("Received Blank Number");

            model1 = new DefaultTableModel();
            model1.addColumn("Assigned Blank Type");
            model1.addColumn("Assigned Blank Number");

            model2 = new DefaultTableModel();
            model2.addColumn("Advisor Code");
            model2.addColumn("Existing Assigned Blank Type");
            model2.addColumn("Existing Assigned Blank Number");

            model3 = new DefaultTableModel();
            model3.addColumn("Used Blank Type");
            model3.addColumn("Used Blank Number");

            model4 = new DefaultTableModel();
            model4.addColumn("Available Blank Type");
            model4.addColumn("Available Blank Number");

            model5 = new DefaultTableModel();
            model5.addColumn("Advisor Code");
            model5.addColumn("Used Blank Type");
            model5.addColumn("Used Blank Number");

            while(rs.next()){
                Object[] row = new Object[2];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                model.addRow(row);
            }

            while (rs1.next()){
                Object[] row = new Object[2];
                row[0] = rs1.getInt(1);
                row[1] = rs1.getInt(2);
                model1.addRow(row);
            }

            while (rs2.next()){
                Object[] row = new Object[3];
                row[0] = rs2.getInt(1);
                row[1] = rs2.getInt(2);
                row[2] = rs2.getInt(3);
                model2.addRow(row);
            }

            while (rs3.next()){
                Object[] row = new Object[2];
                row[0] = rs3.getInt(1);
                row[1] = rs3.getInt(2);
                model3.addRow(row);
            }

            while (rs4.next()){
                Object[] row = new Object[2];
                row[0] = rs4.getInt(1);
                row[1] = rs4.getInt(2);
                model4.addRow(row);
            }

            while (rs5.next()){
                Object[] row = new Object[3];
                row[0] = rs5.getInt(1);
                row[1] = rs5.getInt(2);
                row[2] = rs5.getInt(3);
                model5.addRow(row);
            }

            table = new JTable();
            table.setModel(model);
            table.setDefaultEditor(Object.class, null);

            table1 = new JTable();
            table1.setModel(model1);
            table1.setDefaultEditor(Object.class, null);

            table2 = new JTable();
            table2.setModel(model2);
            table2.setDefaultEditor(Object.class, null);

            table3 = new JTable();
            table3.setModel(model3);
            table3.setDefaultEditor(Object.class, null);

            table4 = new JTable();
            table4.setModel(model4);
            table4.setDefaultEditor(Object.class, null);

            table5 = new JTable();
            table5.setModel(model5);
            table5.setDefaultEditor(Object.class, null);

            reportScrollPane = new JScrollPane();
            reportScrollPane.setViewportView(table1);

            con.close();

        }catch (Exception e) { System.out.println(e);}
    }

    public void addNextButtonListener(){
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectReportComboBox.getSelectedIndex() == 1){
                    if (reportCreated = true && index < 5){
                        index++;
                        switch (index){
                            case 2:
                                reportScrollPane.setViewportView(table2);
                                break;
                            case 3:
                                reportScrollPane.setViewportView(table3);
                                break;
                            case 4:
                                reportScrollPane.setViewportView(table4);
                                break;
                            case 5:
                                reportScrollPane.setViewportView(table5);
                                break;
                        }
                    }
                }
            }
        });
    }

    public void addPreviousButtonListener(){
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectReportComboBox.getSelectedIndex() == 1){
                    if (reportCreated = true && index > 1){
                        index--;
                        switch (index){
                            case 1:
                                reportScrollPane.setViewportView(table1);
                                break;
                            case 2:
                                reportScrollPane.setViewportView(table2);
                                break;
                            case 3:
                                reportScrollPane.setViewportView(table3);
                                break;
                            case 4:
                                reportScrollPane.setViewportView(table4);
                                break;
                        }
                    }
                }
            }
        });
    }
}
