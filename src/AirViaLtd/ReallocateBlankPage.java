package AirViaLtd;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;

public class ReallocateBlankPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel functionsPanel;
    private JLabel reallocateBlank;
    private JLabel selectedBlankLabel;
    private JTextField selectedBlankTextField;
    private JComboBox travelAdvisorComboBox;
    private JButton reallocateButton;
    private JPanel menuPanel;
    private JButton homeButton;
    private JButton backButton;

    private JScrollPane assignedBlanksScrollPane;
    private JTable table;
    private DefaultTableModel model;

    private AirViaLtd app;

    public ReallocateBlankPage(AirViaLtd a) {
        this.app = a;
        addMenuButtonsListener();
        addTableData();
        addSelectedBlankText();
        addTravelAdvisors();
        addReallocateButtonListener();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JScrollPane getAssignedBlanksScrollPane() {
        return assignedBlanksScrollPane;
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

            }
        });
    }

    public void addTableData(){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select ID, Type, Number, NewlyReceived, ReceivedDate, AssignedDate, UsedDate, AdvisorCode FROM in2018g16.Blank where AdvisorCode != 1 and UsedDate is null");

            model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Type");
            model.addColumn("Number");
            model.addColumn("Newly Received");
            model.addColumn("Received Date");
            model.addColumn("Assigned Date");
            model.addColumn("Used Date");
            model.addColumn("Advisor Code");

            while(rs.next()){
                Object[] row = new Object[8];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getInt(3);
                row[3] = rs.getBoolean(4);
                row[4] = rs.getDate(5);
                row[5] = rs.getDate(6);
                row[6] = rs.getDate(7);
                row[7] = rs.getInt(8);
                model.addRow(row);
            }

            table = new JTable();
            table.setModel(model);
            table.setDefaultEditor(Object.class, null);

            assignedBlanksScrollPane = new JScrollPane();
            assignedBlanksScrollPane.setViewportView(table);

            con.close();

        }catch (Exception e) { System.out.println(e);}

    }

    public void addReallocateButtonListener(){
        reallocateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validSelectedItems()){
                    int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to reassign this blank?", "Confirm", JOptionPane.YES_NO_OPTION);

                    if (reply == JOptionPane.YES_OPTION){

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
                                JOptionPane.showMessageDialog(null, rs + " rows updated", "Successful Update", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "No rows were updated, please retry", "Unsuccessful Update", JOptionPane.ERROR_MESSAGE);
                            }

                            con.close();

                        }catch(Exception ex){ System.out.println(ex);}
                    }

                    updateTable();

                } else {
                    JOptionPane.showMessageDialog(null, "Please select a blank and a travel advisor", "Invalid Entry", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public boolean validSelectedItems(){
        if (travelAdvisorComboBox.getSelectedIndex() == 0 || table.getSelectedRow() == -1){
            return false;
        }
        return true;
    }

    public void updateTable(){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select ID, Type, Number, NewlyReceived, ReceivedDate, AssignedDate, UsedDate, AdvisorCode FROM in2018g16.Blank where AdvisorCode != 1 and UsedDate is null");

            model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Type");
            model.addColumn("Number");
            model.addColumn("Newly Received");
            model.addColumn("Received Date");
            model.addColumn("Assigned Date");
            model.addColumn("Used Date");
            model.addColumn("Advisor Code");

            while(rs.next()){
                Object[] row = new Object[8];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getInt(3);
                row[3] = rs.getBoolean(4);
                row[4] = rs.getDate(5);
                row[5] = rs.getDate(6);
                row[6] = rs.getDate(7);
                row[7] = rs.getInt(8);
                model.addRow(row);
            }

            table.setModel(model);
            table.setDefaultEditor(Object.class, null);

            assignedBlanksScrollPane.repaint();

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

    public void addSelectedBlankText(){

        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int blankID = (int) table.getValueAt(table.getSelectedRow(), 0);
                int blankType = (int) table.getValueAt(table.getSelectedRow(), 1);
                int blankNumber = (int) table.getValueAt(table.getSelectedRow(), 2);
                int taCode = (int) table.getValueAt(table.getSelectedRow(), 7);

                selectedBlankTextField.setText("ID: " + blankID + " Type: " + blankType + " Number: " + blankNumber + " Advisor Code: " + taCode);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                int blankID = (int) table.getValueAt(table.getSelectedRow(), 0);
                int blankType = (int) table.getValueAt(table.getSelectedRow(), 1);
                int blankNumber = (int) table.getValueAt(table.getSelectedRow(), 2);
                int taCode = (int) table.getValueAt(table.getSelectedRow(), 7);

                selectedBlankTextField.setText("ID: " + blankID + " Type: " + blankType + " Number: " + blankNumber + " Advisor Code: " + taCode);
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }
}
