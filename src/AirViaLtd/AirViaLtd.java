package AirViaLtd;

import javax.swing.*;
import java.awt.*;

public class AirViaLtd {

    private final JFrame frame;
    private LoginPage loginPage;
    private OfficeManagerHomePage officeManagerHomePage;
    private AllocateBlankPage allocateBlankPage;
    private AddBlankPage addBlankPage;

    public AirViaLtd() {

        loginPage = new LoginPage(this);
        officeManagerHomePage = new OfficeManagerHomePage(this);
        allocateBlankPage = new AllocateBlankPage(this);
        addBlankPage = new AddBlankPage(this);

        frame = new JFrame("AirViaLtd");

        frame.add(loginPage.getMainPanel());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
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

    public void transitionToAddBlankPage(){
        frame.remove(officeManagerHomePage.getMainPanel());
        frame.add(addBlankPage.getMainPanel());
        frame.pack();
    }



    public static void main(String[] args) {
        new AirViaLtd();
    }

}
