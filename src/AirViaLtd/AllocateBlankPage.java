package AirViaLtd;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AllocateBlankPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel buttonsPanel;
    private JLabel allocateBlankLabel;
    private JComboBox travelAdvisorComboBox;
    private JButton assignButton;
    private JLabel blanksNotAssignedYetLabel;
    private JButton refreshTableButton;
    private JTable table;
    private JScrollPane notAssignedBlanksScrollPane;
    private DefaultTableModel model;

    private AirViaLtd app;

    private boolean changesMade;

    public AllocateBlankPage(AirViaLtd a) {
        this.app = a;
        this.changesMade = false;

        addTableData();
        addTravelAdvisors();
        addAssignButtonListener();
        addRefreshTableButtonListener();

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JScrollPane getNotAssignedBlanksScrollPane() {
        return notAssignedBlanksScrollPane;
    }

    public void addTableData(){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select ID, Type, Number, NewlyReceived, ReceivedDate, AssignedDate, UsedDate FROM in2018g16.Blank where AdvisorCode = 1");

            model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Type");
            model.addColumn("Number");
            model.addColumn("Newly Received");
            model.addColumn("Received Date");
            model.addColumn("Assigned Date");
            model.addColumn("Used Date");

            while(rs.next()){
                Object[] row = new Object[7];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getInt(3);
                row[3] = rs.getBoolean(4);
                row[4] = rs.getDate(5);
                row[5] = rs.getDate(6);
                row[6] = rs.getDate(7);
                model.addRow(row);
            }

            table = new JTable();
            table.setModel(model);
            table.setDefaultEditor(Object.class, null);

            notAssignedBlanksScrollPane = new JScrollPane();
            notAssignedBlanksScrollPane.setViewportView(table);

            con.close();

        }catch (Exception e) { System.out.println(e);}

    }
    public void addTravelAdvisors(){
        travelAdvisorComboBox.addItem("-- Select a Travel Advisor --");

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

    public void addAssignButtonListener(){

        assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validSelectedItems()){
                    int blankID = (int) table.getValueAt(table.getSelectedRow(), 0);
                    String ta = (String) travelAdvisorComboBox.getSelectedItem();
                    String[] splitTaSelected = ta.split("\\s+");
                    int taID = Integer.valueOf(splitTaSelected[0]);

                    String date = java.time.LocalDate.now().toString();

                    try{
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con= DriverManager.getConnection(
                                "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                "in2018g16_d",
                                "35cnYJLB");

                        String sql = "update in2018g16.Blank set AdvisorCode = ?, AssignedDate = ? where ID = ? ";


                        PreparedStatement stmt = con.prepareStatement(sql);

                        stmt.setInt(1, taID);
                        stmt.setString(2, date);
                        stmt.setInt(3, blankID);

                        int rs = stmt.executeUpdate();

                        if (rs != 0){
                            changesMade = true;
                            JOptionPane.showMessageDialog(null, rs + " rows updated", "Successfull update", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "No rows were updated, please retry", "Unsuccessfull update", JOptionPane.ERROR_MESSAGE);
                        }

                        con.close();

                    }catch(Exception ex){ System.out.println(ex);}

                updateTable();

                } else {
                    JOptionPane.showMessageDialog(null, "Please select a blank", "Invalid Entry", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

    }

    public boolean validSelectedItems(){
        if (travelAdvisorComboBox.getSelectedIndex() == 0 && table.getSelectedRow() == -1){
            return false;
        }
        return true;
    }

    public void addRefreshTableButtonListener(){
        refreshTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (changesMade){

                    updateTable();

                } else {
                    JOptionPane.showMessageDialog(null, "Make some changes first", "No changes", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void updateTable(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select ID, Type, Number, NewlyReceived, ReceivedDate, AssignedDate, UsedDate FROM in2018g16.Blank where AdvisorCode = 1");

            model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Type");
            model.addColumn("Number");
            model.addColumn("Newly Received");
            model.addColumn("Received Date");
            model.addColumn("Assigned Date");
            model.addColumn("Used Date");

            while(rs.next()){
                Object[] row = new Object[7];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getInt(3);
                row[3] = rs.getBoolean(4);
                row[4] = rs.getDate(5);
                row[5] = rs.getDate(6);
                row[6] = rs.getDate(7);
                model.addRow(row);
            }

            table.setModel(model);
            table.setDefaultEditor(Object.class, null);

            notAssignedBlanksScrollPane.repaint();

            con.close();

        }catch (Exception e) { System.out.println(e);}
    }

}
