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

    private ImageIcon homeIcon;
    private ImageIcon backIcon;

    private AirViaLtd app;

    //constructor
    public ReallocateBlankPage(AirViaLtd a) {
        this.app = a;
        setGraphics();
        addMenuButtonsListener();
        addTableData();
        addSelectedBlankText();
        addTravelAdvisors();
        addReallocateButtonListener();
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

        selectedBlankTextField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));

        reallocateButton.setPreferredSize(new Dimension(250, 50));
        reallocateButton.setBorder(new LineBorder(Color.WHITE, 1));

    }


    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JScrollPane getAssignedBlanksScrollPane() {
        return assignedBlanksScrollPane;
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

    //add assigned blanks
    public void addTableData(){

        Connection con = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");
            con.setAutoCommit(false);

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select ID, Type, Number, NewlyReceived, ReceivedDate, AssignedDate, UsedDate, AdvisorCode FROM in2018g16.Blank where AdvisorCode != 1 and UsedDate is null");
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

    //reallocate selected blanks to selected travel advisor
    public void addReallocateButtonListener(){
        reallocateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validSelectedItems()){
                    int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to reassign this blank?", "Confirm", JOptionPane.YES_NO_OPTION);

                    if (reply == JOptionPane.YES_OPTION){


                        String ta = (String) travelAdvisorComboBox.getSelectedItem();
                        String[] splitTaSelected = ta.split("\\s+");
                        int taID = Integer.valueOf(splitTaSelected[0]);

                        String date = java.time.LocalDate.now().toString();

                        int[] selection = table.getSelectedRows();

                        Connection con = null;

                        try{
                            Class.forName("com.mysql.jdbc.Driver");
                            con= DriverManager.getConnection(
                                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                    "in2018g16_d",
                                    "35cnYJLB");
                            con.setAutoCommit(false);

                            for (int i=0; i< selection.length; i++){

                                int blankID = (int) table.getValueAt(selection[i], 0);

                                String sql = "update in2018g16.Blank set AdvisorCode = ?, AssignedDate = ? where ID = ? ";

                                PreparedStatement stmt = con.prepareStatement(sql);

                                stmt.setInt(1, taID);
                                stmt.setString(2, date);
                                stmt.setInt(3, blankID);

                                int rs = stmt.executeUpdate();

                                con.commit();

                                if (rs == 0){
                                    JOptionPane.showMessageDialog(null, "No rows were updated, please retry", "Unsuccessful Update", JOptionPane.ERROR_MESSAGE);
                                }
                            }

                            JOptionPane.showMessageDialog(null, selection.length + " rows updated", "Successful Update", JOptionPane.INFORMATION_MESSAGE);


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
                    }

                    updateTable();

                } else {
                    JOptionPane.showMessageDialog(null, "Please select a blank and a travel advisor", "Invalid Entry", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }


    public boolean validSelectedItems(){
        int[] selection = table.getSelectedRows();

        for (int i = 0; i < selection.length; i++){
            if (travelAdvisorComboBox.getSelectedIndex() == 0 || table.getSelectedRow() == -1){
                return false;
            }
        }

        return true;
    }

    //update table after reallocation
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
            ResultSet rs = stmt.executeQuery("select ID, Type, Number, NewlyReceived, ReceivedDate, AssignedDate, UsedDate, AdvisorCode FROM in2018g16.Blank where AdvisorCode != 1 and UsedDate is null");

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

    public void addTravelAdvisors(){
        travelAdvisorComboBox.addItem("-- Select a Travel Advisor --");

        Connection con = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");
            con.setAutoCommit(false);

            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select AdvisorCode, FirstName, LastName FROM in2018g16.TravelAdvisor WHERE AdvisorCode != 1");


            con.commit();

            while (rs.next()){
                travelAdvisorComboBox.addItem(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3));
            }

        } catch (Exception e) {
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

    //add details about selected blanks
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
