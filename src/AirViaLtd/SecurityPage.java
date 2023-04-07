package AirViaLtd;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPOutputStream;

public class SecurityPage {
    private JButton restoreDataButton;
    private JButton backUpDataButton;
    private JPanel mainPanel;


    private AirViaLtd app;

    public SecurityPage(AirViaLtd a) {

        this.app = a;
        addBackupButtonListener();

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void addBackupButtonListener(){
        backUpDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    //BackupDB();

                /*try {
                    backup("server smcse-stuproj00.city.ac.uk", "3306", "in2018g16", "in2018g16_a", "FJ7BjC1x", "backupp");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }*/
                //backup1("in2018g16", "in2018g16_a", "FJ7BjC1x", "/Users/rd/IdeaProjects/AirViaLtd_Project");


            }
        });
    }

    public static void BackupDB() {
        try {

            /*NOTE: Getting path to the Jar file being executed*/
            /*NOTE: YourImplementingClass-> replace with the class executing the code*/
            CodeSource codeSource = SecurityPage.class.getProtectionDomain().getCodeSource();
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            String jarDir = jarFile.getParentFile().getPath();

            System.out.println(jarDir);


            /*NOTE: Creating Database Constraints*/
            String dbName = "in2018g16";
            String dbUser = "in2018g16_a";
            String dbPass = "FJ7BjC1x";

            /*NOTE: Creating Path Constraints for folder saving*/
            /*NOTE: Here the backup folder is created for saving inside it*/
            String folderPath = jarDir + "/backup";

            /*NOTE: Creating Folder if it does not exist*/
            File f1 = new File(folderPath);
            f1.mkdir();

            /*NOTE: Creating Path Constraints for backup saving*/
            /*NOTE: Here the backup is saved in a folder called backup with the name backup.sql*/
            String savePath = "/" + jarDir + "/backup" + "/backup.sql/";

            /*NOTE: Used to create a cmd command*/
            String executeCmd = "/usr/local/mysql-8.0.32-macos13-x86_64/bin/mysqldump -u" + dbUser + " -p" + dbPass + " --database " + dbName + " -r " + savePath;

            /*NOTE: Executing the command here*/
            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();

            /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
            if (processComplete == 0) {
                System.out.println("Backup Complete");
            } else {
                System.out.println("Backup Failure");
            }

        } catch (URISyntaxException | IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Error at Backup: " + ex.getMessage());
        }

    }

    public static void backup(String host, String port, String database, String user, String password, String filename) throws IOException {
        // Create date formatter for timestamp in backup file name
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String timestamp = formatter.format(new Date());

        // Build MySQL command to dump database to file
        String[] command = new String[]{"/usr/local/mysql-8.0.32-macos13-x86_64/bin/mysqldump", "--host=" + host, "--port=" + port, "--user=" + user, "--password=" + password, "--databases", database};

        // Execute command and redirect output to gzip file
        Process process = Runtime.getRuntime().exec(command);
        InputStream inputStream = process.getInputStream();
        OutputStream outputStream = new GZIPOutputStream(new FileOutputStream(filename + "-" + timestamp + ".sql.gz"));
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.close();
        inputStream.close();
    }

    public static void backup1(String dbName, String dbUserName, String dbPassword, String path) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        LocalDateTime currentDateTime = LocalDateTime.now();

        String fileName = dbName + "_" + currentDateTime.format(dateTimeFormatter) + ".sql";

        File file = new File(path + File.separator + fileName);

        List<String> command = new ArrayList<>();
        command.add("/usr/local/mysql-8.0.32-macos13-x86_64/bin/mysqldump");
        command.add("-u" + dbUserName);
        command.add("-p" + dbPassword);
        command.add(dbName);
        command.add("-r" + file.getAbsolutePath());

        ProcessBuilder pb = new ProcessBuilder(command);
        pb.environment().put("MYSQL_PWD", dbPassword);

        try {
            Process process = pb.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Backup completed successfully. Backup file: " + fileName);
            } else {
                System.out.println("Backup failed. Exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}