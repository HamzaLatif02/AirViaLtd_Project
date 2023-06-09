package AirViaLtd;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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
    private JPanel menuPanel;
    private JButton homeButton;
    private JButton backButton;

    private JScrollPane blankStockScrollPane;
    private DefaultTableModel model;
    private JTable table;

    private int counter;

    private ImageIcon homeIcon;
    private ImageIcon backIcon;

    private AirViaLtd app;


    //constructor
    public BlankStockPage(AirViaLtd a) {
        this.app = a;
        setGraphics();
        addMenuButtonsListener();
        addTableData();
        addIDButtonListener();
        addTypeButtonListener();
        addDateButtonListener();
        addTravelAdvisorButtonListener();
        addNotAssignedButtonListener();
        addUsedButtonListener();

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

        IDButton.setPreferredSize(new Dimension(250, 50));
        IDButton.setBorder(new LineBorder(Color.WHITE, 1));
        typeButton.setPreferredSize(new Dimension(250, 50));
        typeButton.setBorder(new LineBorder(Color.WHITE, 1));
        dateButton.setPreferredSize(new Dimension(250, 50));
        dateButton.setBorder(new LineBorder(Color.WHITE, 1));
        travelAdvisorButton.setPreferredSize(new Dimension(250, 50));
        travelAdvisorButton.setBorder(new LineBorder(Color.WHITE, 1));
        notAssignedButton.setPreferredSize(new Dimension(250, 50));
        notAssignedButton.setBorder(new LineBorder(Color.WHITE, 1));
        usedButton.setPreferredSize(new Dimension(250, 50));
        usedButton.setBorder(new LineBorder(Color.WHITE, 1));

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
                app.transitionToHomepage();
            }
        });
    }

    //add blanks data to table
    public void addTableData(){

        Connection con= null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");

            con.setAutoCommit(false);


            String sql = "select * FROM in2018g16.Blank";

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

    //sort by ID
    public void addIDButtonListener(){

        counter = 0;
        IDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (counter % 2 == 0){

                    Connection con =null;
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        con = DriverManager.getConnection(
                                "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                "in2018g16_d",
                                "35cnYJLB");
                        con.setAutoCommit(false);

                        String sql = "select * FROM in2018g16.Blank ORDER BY ID ASC";

                        PreparedStatement stmt = con.prepareStatement(sql);
                        ResultSet rs = stmt.executeQuery();
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

                        counter++;

                    }catch (Exception x) {
                        System.out.println(x);
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
                } else {
                    Connection con = null;
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        con = DriverManager.getConnection(
                                "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                "in2018g16_d",
                                "35cnYJLB");
                        con.setAutoCommit(false);

                        String sql = "select * FROM in2018g16.Blank ORDER BY ID DESC";

                        PreparedStatement stmt = con.prepareStatement(sql);
                        ResultSet rs = stmt.executeQuery();
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

                        counter++;

                    }catch (Exception x) {
                        System.out.println(x);
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

            }
        });
    }

    //sort by type
    public void addTypeButtonListener(){
        counter = 0;
        typeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (counter % 2 == 0){

                    Connection con = null;
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        con = DriverManager.getConnection(
                                "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                "in2018g16_d",
                                "35cnYJLB");
                        con.setAutoCommit(false);

                        String sql = "select * FROM in2018g16.Blank ORDER BY Type ASC";

                        PreparedStatement stmt = con.prepareStatement(sql);
                        ResultSet rs = stmt.executeQuery();
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

                        counter++;

                    }catch (Exception x) {
                        System.out.println(x);
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
                } else {
                    Connection con = null;
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        con = DriverManager.getConnection(
                                "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                "in2018g16_d",
                                "35cnYJLB");
                        con.setAutoCommit(false);

                        String sql = "select * FROM in2018g16.Blank ORDER BY Type DESC";

                        PreparedStatement stmt = con.prepareStatement(sql);
                        ResultSet rs = stmt.executeQuery();
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

                        counter++;

                    }catch (Exception x) {
                        System.out.println(x);
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
            }
        });
    }

    //sort by received date
    public void addDateButtonListener(){
        counter = 0;
        dateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (counter % 2 == 0){
                    Connection con = null;
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        con = DriverManager.getConnection(
                                "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                "in2018g16_d",
                                "35cnYJLB");
                        con.setAutoCommit(false);

                        String sql = "select * FROM in2018g16.Blank ORDER BY ReceivedDate ASC";

                        PreparedStatement stmt = con.prepareStatement(sql);
                        ResultSet rs = stmt.executeQuery();
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

                        counter++;

                    }catch (Exception x) {
                        System.out.println(x);
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
                } else {
                    Connection con = null;
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        con = DriverManager.getConnection(
                                "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                "in2018g16_d",
                                "35cnYJLB");
                        con.setAutoCommit(false);

                        String sql = "select * FROM in2018g16.Blank ORDER BY ReceivedDate DESC";

                        PreparedStatement stmt = con.prepareStatement(sql);
                        ResultSet rs = stmt.executeQuery();
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

                        counter++;

                    }catch (Exception x) {
                        System.out.println(x);
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
            }
        });
    }

    //sort by travel advisor
    public void addTravelAdvisorButtonListener(){
        counter = 0;
        travelAdvisorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (counter % 2 == 0){
                    Connection con= null;
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        con = DriverManager.getConnection(
                                "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                "in2018g16_d",
                                "35cnYJLB");
                        con.setAutoCommit(false);

                        String sql = "select * FROM in2018g16.Blank ORDER BY AdvisorCode ASC";

                        PreparedStatement stmt = con.prepareStatement(sql);
                        ResultSet rs = stmt.executeQuery();
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

                        counter++;

                    }catch (Exception x) {
                        System.out.println(x);
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
                } else {
                    Connection con = null;
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        con = DriverManager.getConnection(
                                "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                "in2018g16_d",
                                "35cnYJLB");
                        con.setAutoCommit(false);

                        String sql = "select * FROM in2018g16.Blank ORDER BY AdvisorCode DESC";

                        PreparedStatement stmt = con.prepareStatement(sql);
                        ResultSet rs = stmt.executeQuery();
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

                        counter++;

                    }catch (Exception x) {
                        System.out.println(x);
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
            }
        });
    }

    //filter by not assigned
    public void addNotAssignedButtonListener(){
        notAssignedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection con = null;
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    con = DriverManager.getConnection(
                            "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                            "in2018g16_d",
                            "35cnYJLB");
                    con.setAutoCommit(false);

                    String sql = "select * FROM in2018g16.Blank WHERE AdvisorCode = 1";

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

                    table.setModel(model);
                    table.setDefaultEditor(Object.class, null);

                    blankStockScrollPane.repaint();

                }catch (Exception x) {
                    System.out.println(x);
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
        });
    }


    //filter by used blanks only
    public void addUsedButtonListener(){
        usedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection con = null;
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    con = DriverManager.getConnection(
                            "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                            "in2018g16_d",
                            "35cnYJLB");
                    con.setAutoCommit(false);

                    String sql = "select * FROM in2018g16.Blank WHERE UsedDate is not null";

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

                    table.setModel(model);
                    table.setDefaultEditor(Object.class, null);

                    blankStockScrollPane.repaint();

                }catch (Exception x) {
                    System.out.println(x);
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
        });
    }
}
