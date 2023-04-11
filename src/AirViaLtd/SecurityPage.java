package AirViaLtd;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SecurityPage {
    private JButton restoreDataButton;
    private JButton backUpDataButton;
    private JPanel mainPanel;
    private JLabel securityLabel;
    private JPanel menuPanel;
    private JPanel buttonsPanel;
    private JPanel titlePanel;
    private JButton homeButton;
    private JButton backButton;


    private AirViaLtd app;

    public SecurityPage(AirViaLtd a) {

        this.app = a;
        addMenuButtonsListener();
        addBackupButtonListener();
        //addRestoreButtonListener();

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

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToHomepage();
            }
        });
    }

    public void addBackupButtonListener(){
        backUpDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //openWebPage("https://smcse.city.ac.uk/phpmyadmin/index.php?route=/database/export&db=in2018g16");

                try {
                    backup("smcse-stuproj00.city.ac.uk", "3306", "in2018g16","in2018g16_a", "FJ7BjC1x", "db_backup");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }

    /*public void openWebPage(String url){
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        }
        catch (java.io.IOException e) {
            System.out.println(e.getMessage());
        }
    }*/

    public void addRestoreButtonListener(){
        restoreDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restore("in2018g16", "in2018g16_a", "FJ7BjC1x", "/Users/rd/IdeaProjects/AirViaLtd_Project/in2018g16_2023-04-08_00-52-56.sql");
            }
        });
    }

    public static void backup(String host, String port, String database, String user, String password, String filename) throws IOException {
        // Create date formatter for timestamp in backup file name
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String timestamp = formatter.format(new Date());

        // Build MySQL command to dump all databases and tables to file
        String[] command = new String[]{"/usr/local/mysql-8.0.32-macos13-x86_64/bin/mysqldump", "--host=" + host, "--port=" + port, "--user=" + user, "--password=" + password, "--databases", database};

        // Execute command and redirect output to SQL file
        Process process = Runtime.getRuntime().exec(command);
        InputStream inputStream = process.getInputStream();
        OutputStream outputStream = new FileOutputStream(filename + "-" + timestamp + ".sql");
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.close();
        inputStream.close();
    }

    public static void restore(String dbName, String dbUser, String dbPass, String filePath) {
        try {
            /*Establishing MySQL Connection*/
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/" + dbName, dbUser, dbPass);
            Statement stmt = con.createStatement();

            /*Creating file reader stream*/
            FileReader fr = new FileReader(new File(filePath));

            /*Creating buffered reader*/
            BufferedReader br = new BufferedReader(fr);

            /*Building SQL Script*/
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

            /*Splitting SQL Script into individual statements*/
            String[] commands = sb.toString().split(";");

            /*Executing each individual statement*/
            for (String command : commands) {
                if (!command.trim().equals("")) {
                    stmt.execute(command);
                }
            }

            /*Closing Streams*/
            br.close();
            fr.close();
            stmt.close();
            con.close();
            System.out.println("Database restored successfully from " + filePath);

        } catch (ClassNotFoundException | SQLException | IOException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Error at restoreDB: " + e.getMessage());
        }
    }



}