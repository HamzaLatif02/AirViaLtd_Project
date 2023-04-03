package AirViaLtd;

import javax.swing.*;
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
    private JFormattedTextField startDateFormattedTextField;
    private JLabel startDateLabel;
    private JLabel endDateLabel;
    private JFormattedTextField endDateFormattedTextField;
    private JButton createButton;
    private JButton downloadButton;

    private AirViaLtd app;

    public CreateReportPage(AirViaLtd a) {
        this.app = a;
        addSelectReportData();
        addIndividualOrGlobal();
        addTravelAdvisorData();
    }

    public JPanel getMainPanel() {
        return mainPanel;
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
}
