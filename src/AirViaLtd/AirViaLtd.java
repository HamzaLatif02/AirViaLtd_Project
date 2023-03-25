package AirViaLtd;

import javax.swing.*;

public class AirViaLtd {

    public static void main(String[] args) {
        LoginPage loginPage = new LoginPage();

        JFrame frame = new JFrame("AirViaLtd");

        frame.add(loginPage.getMainPanel());
        frame.setSize(850,850);
        frame.setResizable(false);
        frame.setVisible(true);

    }
}
