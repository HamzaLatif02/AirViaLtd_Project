package AirViaLtd;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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

    private ImageIcon homeIcon;
    private ImageIcon backIcon;

    private AirViaLtd app;

    //constructor
    public EditBlankPage(AirViaLtd a) {
        this.app = a;
        setGraphics();
        addMenuButtonsListener();
        addTableData();
        addBlankSelectedFieldsText();
        addApplyChangesButtonListener();
    }

    //set page graphics
    public void setGraphics(){

        homeIcon = new ImageIcon("data/home.png");
        homeButton.setPreferredSize(new Dimension(100, 30));
        homeButton.setBorderPainted(false);
        homeButton.setIcon(homeIcon);

        backIcon = new ImageIcon("data/back.png");
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.setBorderPainted(false);
        backButton.setIcon(backIcon);

        IDTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        typeTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        numberTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        newlyReceivedTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        receivedDateTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        assignedDateTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        usedDateTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        advisorCodeTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        auditCouponIDTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));

        applyChangesButton.setPreferredSize(new Dimension(250, 50));
        applyChangesButton.setBorder(new LineBorder(Color.WHITE, 1));


    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JScrollPane getBlankStockScrollPane() {
        return blankStockScrollPane;
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
                app.removeTables();
                app.transitionToBlankManagerPage();
            }
        });
    }

    //add all unused blanks into table
    public void addTableData(){

        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");
            con.setAutoCommit(false);


            String sql = "select * FROM in2018g16.Blank where UsedDate is null";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            con.commit();

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

        }catch (Exception e) {
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

    //change the blank selected
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

                        Connection con = null;

                        try{
                            Class.forName("com.mysql.jdbc.Driver");
                            con= DriverManager.getConnection(
                                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                    "in2018g16_d",
                                    "35cnYJLB");
                            con.setAutoCommit(false);

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

                            con.commit();

                            if (rs != 0){
                                JOptionPane.showMessageDialog(null, rs + " blank updated", "Successful Update", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "No rows were updated, please retry", "Unsuccessful Update", JOptionPane.ERROR_MESSAGE);
                            }

                        }catch(Exception ex){
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

    //update table after edit
    public void updateTable(){
        Connection con = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");
            con.setAutoCommit(false);

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * FROM in2018g16.Blank where UsedDate is null");
            con.commit();

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

        }catch (Exception e) {
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

    //set text based on user selection
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
