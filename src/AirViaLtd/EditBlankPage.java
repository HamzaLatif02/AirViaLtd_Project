package AirViaLtd;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;

public class EditBlankPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel functionsPanel;
    private JLabel editBlankPanel;
    private JTextField IDTextField;
    private JTextField typeTextField;
    private JTextField numberTextField;
    private JTextField newlyReceivedTextField;
    private JTextField receivedDateTextField;
    private JTextField assignedDateTextField;
    private JTextField usedDateTextField;
    private JTextField advisorCodeTextField;
    private JTextField auditCouponIDTextField;
    private JButton applyChangesButton;
    private JLabel selectedBlankLabel;
    private JPanel menuPanel;
    private JButton homeButton;
    private JButton backButton;
    private JScrollPane blankStockScrollPane;
    private DefaultTableModel model;
    private JTable table;
    private AirViaLtd app;
    public EditBlankPage(AirViaLtd a) {
        this.app = a;

        addMenuButtonsListener();
        addTableData();
        addBlankSelectedFieldsText();
        addApplyChangesButtonListener();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JScrollPane getBlankStockScrollPane() {
        return blankStockScrollPane;
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
                app.removeTables();
                app.transitionToBlankManagerPage();
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


            String sql = "select * FROM in2018g16.Blank where UsedDate is null";

            Statement stmt = con.createStatement();
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


            table = new JTable();
            table.setModel(model);
            table.setDefaultEditor(Object.class, null);

            blankStockScrollPane = new JScrollPane();
            blankStockScrollPane.setViewportView(table);

            con.close();

        }catch (Exception e) { System.out.println(e);}

    }

    public void addApplyChangesButtonListener(){
        applyChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validSelectedItems()){
                    int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to edit this blank?", "Confirm", JOptionPane.YES_NO_OPTION);

                    if (reply == JOptionPane.YES_OPTION) {

                        int selectedID = (int) table.getValueAt(table.getSelectedRow(), 0);

                        int id = Integer.valueOf(IDTextField.getText());
                        int type = Integer.valueOf(typeTextField.getText());
                        int number = Integer.valueOf(numberTextField.getText());

                        boolean newlyReceived = true;
                        if (newlyReceivedTextField.getText().equals("true")){
                            newlyReceived = true;
                        } else if (newlyReceivedTextField.getText().equals("false")){
                            newlyReceived = false;
                        }

                        String receivedDate = null;
                        String assignedDate = null;
                        String usedDate = null;

                        if (receivedDateTextField.getText().equals("null") == false){
                            receivedDate = receivedDateTextField.getText();
                        }

                        if (assignedDateTextField.getText().equals("null") == false){
                            receivedDate = assignedDateTextField.getText();
                        }

                        if (usedDateTextField.getText().equals("null") == false){
                            receivedDate = usedDateTextField.getText();
                        }

                        int advisorCode = Integer.valueOf(advisorCodeTextField.getText());
                        int auditCouponID = Integer.valueOf(auditCouponIDTextField.getText());

                        try{
                            Class.forName("com.mysql.jdbc.Driver");
                            Connection con= DriverManager.getConnection(
                                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                    "in2018g16_d",
                                    "35cnYJLB");

                            String sql = "update in2018g16.Blank Set ID = ?, Type = ?, Number = ?, NewlyReceived = ?, ReceivedDate = ?, AssignedDate = ?, UsedDate = ?, AdvisorCode = ?, AuditCouponID = ? WHERE ID = ?";

                            PreparedStatement stmt = con.prepareStatement(sql);

                            stmt.setInt(1, id);
                            stmt.setInt(2, type);
                            stmt.setInt(3, number);
                            stmt.setBoolean(4, newlyReceived);
                            stmt.setString(5, receivedDate);
                            stmt.setString(6, assignedDate);
                            stmt.setString(7, usedDate);
                            stmt.setInt(8, advisorCode);
                            stmt.setInt(9, auditCouponID);
                            stmt.setInt(10, selectedID);

                            int rs = stmt.executeUpdate();

                            if (rs != 0){
                                JOptionPane.showMessageDialog(null, rs + " blank updated", "Successful Update", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "No rows were updated, please retry", "Unsuccessful Update", JOptionPane.ERROR_MESSAGE);
                            }

                            con.close();

                        }catch(Exception ex){ System.out.println(ex);}

                        updateTable();
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Please select a blank", "Invalid Entry", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public boolean validSelectedItems(){
        if (table.getSelectedRow() == -1){
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
            ResultSet rs = stmt.executeQuery("select * FROM in2018g16.Blank where UsedDate is null");

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

        }catch (Exception e) { System.out.println(e);}
    }


    public void addBlankSelectedFieldsText(){
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                int ID = (int) table.getValueAt(table.getSelectedRow(), 0);
                int Type = (int) table.getValueAt(table.getSelectedRow(), 1);
                int Number = (int) table.getValueAt(table.getSelectedRow(), 2);
                boolean NewlyReceived = (boolean) table.getValueAt(table.getSelectedRow(), 3);
                Date ReceivedDate = (Date) table.getValueAt(table.getSelectedRow(), 4);
                Date AssignedDate = (Date) table.getValueAt(table.getSelectedRow(), 5);
                Date UsedDate = (Date) table.getValueAt(table.getSelectedRow(), 6);
                int AdvisorCode = (int) table.getValueAt(table.getSelectedRow(), 7);
                int AuditCouponID = (int) table.getValueAt(table.getSelectedRow(), 8);

                IDTextField.setEditable(true);
                typeTextField.setEditable(true);
                numberTextField.setEditable(true);
                newlyReceivedTextField.setEditable(true);
                receivedDateTextField.setEditable(true);
                assignedDateTextField.setEditable(true);
                usedDateTextField.setEditable(true);
                advisorCodeTextField.setEditable(true);
                auditCouponIDTextField.setEditable(true);

                IDTextField.setText("" + ID);
                typeTextField.setText("" + Type);
                numberTextField.setText("" + Number);
                newlyReceivedTextField.setText("" + NewlyReceived);
                receivedDateTextField.setText("" + ReceivedDate);
                assignedDateTextField.setText("" + AssignedDate);
                usedDateTextField.setText("" + UsedDate);
                advisorCodeTextField.setText("" + AdvisorCode);
                auditCouponIDTextField.setText("" + AuditCouponID);

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
