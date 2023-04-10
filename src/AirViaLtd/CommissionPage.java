package AirViaLtd;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CommissionPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel functionPanel;
    private JLabel commissionLabel;
    private JTextField commissionRateTextField;
    private JButton addButton;
    private JButton deleteButton;
    private JComboBox commissionComboBox;
    private JPanel menuPanel;
    private JButton homeButton;
    private JButton backButton;

    private AirViaLtd app;

    public CommissionPage(AirViaLtd a) {
        this.app = a;
        addMenuButtonsListener();
        addCommissionRates();
        addAddButtonListener();
        addDeleteButtonListener();

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void addMenuButtonsListener(){
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToHomepage();
            }
        });
    }

    public void addCommissionRates(){
        commissionComboBox.addItem("-- Select a Commission --");

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                    "in2018g16_d",
                    "35cnYJLB");

            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select CommissionRate FROM in2018g16.Commission where TravelAgentID = 1");

            while (rs.next()){
                commissionComboBox.addItem(rs.getInt(1));
            }

            con.close();
        } catch (Exception e) { System.out.println(e);}
    }

    public void addAddButtonListener(){
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con= DriverManager.getConnection(
                            "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                            "in2018g16_d",
                            "35cnYJLB");

                    String sql = "insert into in2018g16.Commission values (?, 1)";
                    PreparedStatement stmt=con.prepareStatement(sql);

                    stmt.setInt(1, Integer.valueOf(commissionRateTextField.getText()));

                    int rs=stmt.executeUpdate();

                    if (rs != 0 ){
                        JOptionPane.showMessageDialog(null, "Commission Rate Added", "Success", JOptionPane.INFORMATION_MESSAGE);
                        commissionRateTextField.setText("");
                        commissionComboBox.removeAllItems();
                        addCommissionRates();
                    } else {
                        JOptionPane.showMessageDialog(null, "Could not add commission rate, please retry", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    con.close();
                } catch (Exception x) { System.out.println(x);}
            }
        });
    }

    public void addDeleteButtonListener(){
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (commissionComboBox.getSelectedIndex() != 0){
                    int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this commission?", "Confirm", JOptionPane.YES_NO_OPTION);

                    if (reply == JOptionPane.YES_OPTION) {


                        try{
                            Class.forName("com.mysql.jdbc.Driver");
                            Connection con= DriverManager.getConnection(
                                    "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306",
                                    "in2018g16_d",
                                    "35cnYJLB");

                            String sql = "delete from in2018g16.Commission where CommissionRate = ? ";

                            PreparedStatement stmt = con.prepareStatement(sql);

                            stmt.setInt(1, Integer.valueOf(commissionComboBox.getSelectedItem().toString()));

                            int rs = stmt.executeUpdate();

                            if (rs != 0){
                                JOptionPane.showMessageDialog(null, rs + " commission deleted", "Successful Update", JOptionPane.INFORMATION_MESSAGE);
                                commissionComboBox.removeItem(commissionComboBox.getSelectedItem());
                            } else {
                                JOptionPane.showMessageDialog(null, "No commission was deleted, please retry", "Unsuccessful Update", JOptionPane.ERROR_MESSAGE);
                            }

                            con.close();

                        }catch(Exception ex){ System.out.println(ex);}

                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Please select a customer", "Invalid Entry", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }


}
