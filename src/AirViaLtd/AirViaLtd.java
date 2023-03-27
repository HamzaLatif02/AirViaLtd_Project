package AirViaLtd;

import javax.swing.*;

public class AirViaLtd {

    public AirViaLtd() {
        LoginPage loginPage = new LoginPage();
        OfficeManagerHomePage officeManagerHomePage = new OfficeManagerHomePage();

        JFrame frame = new JFrame("AirViaLtd");

        frame.add(loginPage.getMainPanel());
        frame.setSize(850,850);
        frame.setResizable(false);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        new AirViaLtd();
    }

}
