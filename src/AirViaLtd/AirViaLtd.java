package AirViaLtd;

import javax.swing.*;

public class AirViaLtd {

    private final JFrame frame;
    private LoginPage loginPage;
    private OfficeManagerHomePage officeManagerHomePage;
    private AllocateBlankPage allocateBlankPage;

    public AirViaLtd() {

        loginPage = new LoginPage(this);
        officeManagerHomePage = new OfficeManagerHomePage(this);
        allocateBlankPage = new AllocateBlankPage(this);

        frame = new JFrame("AirViaLtd");

        frame.add(loginPage.getMainPanel());
        frame.setSize(850,850);
        frame.setResizable(false);
        frame.setVisible(true);

    }

    public void transitionToOfficeManagerHomePage(){
        frame.remove(loginPage.getMainPanel());
        frame.add(officeManagerHomePage.getMainPanel());
        frame.pack();
    }

    public void transitionToAllocateBlankPage(){
        frame.remove(officeManagerHomePage.getMainPanel());
        frame.add(allocateBlankPage.getMainPanel());
        frame.pack();
    }



    public static void main(String[] args) {
        new AirViaLtd();
    }

}
