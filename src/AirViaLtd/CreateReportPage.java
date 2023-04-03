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

    private JScrollPane reportScrollPane;
    private DefaultTableModel model;
    private JTable table;

    private AirViaLtd app;

    public CreateReportPage(AirViaLtd a) {
        this.app = a;

        addDateComboBoxData();
        addSelectReportData();
        addIndividualOrGlobal();
        addTravelAdvisorData();
        addCreateButtonListener();
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
            }
        });
    }

    public void createReport(){
        if (selectReportComboBox.getSelectedIndex() == 1){
            createTicketStockReport();
        }

    }

    public void createTicketStockReport(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");


            String sql = "SELECT Blank.Type AS BlankType ,CONCAT ( Blank.Type ,Blank.Number ) AS BlankNumber ,SUM(CASE WHEN Blank.NewlyReceived = 1 THEN 1 ELSE 0 END) AS ReceivedBlanks ,SUM(CASE WHEN Blank.UsedDate IS NOT NULL THEN 1 ELSE 0 END) AS AssignedUsedBlanks ,SUM(CASE WHEN Blank.NewlyReceived = 1 THEN 1 ELSE 0 END) - SUM(CASE WHEN Blank.UsedDate IS NOT NULL THEN 1 ELSE 0 END) AS FinalAmount ,GROUP_CONCAT(DISTINCT TravelAdvisor.AdvisorCode ORDER BY Blank.ID SEPARATOR ', ') AS UsedBy FROM Blank LEFT JOIN TravelAdvisor ON Blank.AdvisorCode = TravelAdvisor.AdvisorCode WHERE Blank.AssignedDate BETWEEN '2023-04-01' AND '2023-04-02' GROUP BY Blank.Type ORDER BY Blank.Type;";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            model = new DefaultTableModel();
            model.addColumn("Blank Type");
            model.addColumn("Blank Number");
            model.addColumn("Received Blanks");
            model.addColumn("Assigned Used Blanks");
            model.addColumn("Final Amount");
            model.addColumn("Used By");


            while(rs.next()){
                Object[] row = new Object[6];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getInt(3);
                row[3] = rs.getInt(4);
                row[4] = rs.getInt(5);
                row[5] = rs.getInt(6);
                model.addRow(row);
            }


            table = new JTable();
            table.setModel(model);
            table.setDefaultEditor(Object.class, null);

            reportScrollPane = new JScrollPane();
            reportScrollPane.setViewportView(table);

            con.close();

        }catch (Exception e) { System.out.println(e);}
    }
}
