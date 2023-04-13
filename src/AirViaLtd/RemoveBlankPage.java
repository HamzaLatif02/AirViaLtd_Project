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

public class RemoveBlankPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel buttonsPanel;
    private JLabel removeBlankLabel;
    private JTextField blankSelectedTextField;
    private JLabel blankSelectedLabel;
    private JButton removeButton;
    private JPanel menuPanel;
    private JButton homeButton;
    private JButton backButton;

    private JScrollPane blankStockScrollPane;
    private DefaultTableModel model;
    private JTable table;

    private ImageIcon homeIcon;
    private ImageIcon backIcon;
    private AirViaLtd app;

    public RemoveBlankPage(AirViaLtd a) {
        this.app = a;
        setGraphics();
        addMenuButtonsListener();
        addTableData();
        addRemoveButtonListener();
        addBlankSelectedText();

    }

    public void setGraphics(){

        homeIcon = new ImageIcon("data/home.png");
        homeButton.setPreferredSize(new Dimension(100, 30));
        homeButton.setBorderPainted(false);
        homeButton.setIcon(homeIcon);

        backIcon = new ImageIcon("data/back.png");
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.setBorderPainted(false);
        backButton.setIcon(backIcon);

        blankSelectedTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));

        removeButton.setPreferredSize(new Dimension(250, 50));
        removeButton.setBorder(new LineBorder(Color.WHITE, 1));


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

        Connection con = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");

            con.setAutoCommit(false);

            String sql = "select * FROM in2018g16.Blank where UsedDate is null and AdvisorCode = 1";

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

    public void addRemoveButtonListener(){
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (validSelectedItems()){
                    int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this blank?", "Confirm", JOptionPane.YES_NO_OPTION);

                    if (reply == JOptionPane.YES_OPTION) {

                        int blankID = (int) table.getValueAt(table.getSelectedRow(), 0);

                        Connection con = null;

                        try{
                            Class.forName("com.mysql.jdbc.Driver");
                            con= DriverManager.getConnection(
                                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                    "in2018g16_d",
                                    "35cnYJLB");
                            con.setAutoCommit(false);

                            String sql = "delete from in2018g16.Blank where ID = ? ";

                            PreparedStatement stmt = con.prepareStatement(sql);

                            stmt.setInt(1, blankID);

                            int rs = stmt.executeUpdate();

                            con.commit();

                            if (rs != 0){
                                JOptionPane.showMessageDialog(null, rs + " blank deleted", "Successful Update", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "No rows were deleted, please retry", "Unsuccessful Update", JOptionPane.ERROR_MESSAGE);
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

    public void addBlankSelectedText(){
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int blankID = (int) table.getValueAt(table.getSelectedRow(), 0);
                int blankType = (int) table.getValueAt(table.getSelectedRow(), 1);
                int blankNumber = (int) table.getValueAt(table.getSelectedRow(), 2);

                blankSelectedTextField.setText("ID: " + blankID + " Type: " + blankType + " Number: " + blankNumber);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                int blankID = (int) table.getValueAt(table.getSelectedRow(), 0);
                int blankType = (int) table.getValueAt(table.getSelectedRow(), 1);
                int blankNumber = (int) table.getValueAt(table.getSelectedRow(), 2);

                blankSelectedTextField.setText("ID: " + blankID + " Type: " + blankType + " Number: " + blankNumber);
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
