package AirViaLtd;

import javax.swing.*;
import java.awt.*;

public class AirViaLtd {

    private final JFrame frame;
    private LoginPage loginPage;
    private OfficeManagerHomePage officeManagerHomePage;
    private AllocateBlankPage allocateBlankPage;
    private AddBlankPage addBlankPage;
    private BlankStockPage blankStockPage;
    private CreateReportPage createReportPage;

    public AirViaLtd() {

        loginPage = new LoginPage(this);
        officeManagerHomePage = new OfficeManagerHomePage(this);
        allocateBlankPage = new AllocateBlankPage(this);
        addBlankPage = new AddBlankPage(this);
        blankStockPage = new BlankStockPage(this);
        createReportPage = new CreateReportPage(this);


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

    public void transitionToBlankStockPage(){
        frame.remove(officeManagerHomePage.getMainPanel());
        frame.add(blankStockPage.getMainPanel());
        frame.pack();
    }

    public void transitionToCreateReportPage(){
        frame.remove(officeManagerHomePage.getMainPanel());
        frame.add(createReportPage.getMainPanel());
        frame.pack();
    }








    public static void main(String[] args) {
        new AirViaLtd();
    }

}
