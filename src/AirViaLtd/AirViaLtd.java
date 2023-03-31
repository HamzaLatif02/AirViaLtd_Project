package AirViaLtd;

import javax.swing.*;

public class AirViaLtd {

    private final JFrame frame;

    private LoginPage loginPage;

    private OfficeManagerHomePage officeManagerHomePage;
    private AllocateBlankPage allocateBlankPage;
    private AddBlankPage addBlankPage;
    private BlankStockPage blankStockPage;
    private CreateReportPage createReportPage;
    private DiscountPlanPage discountPlanPage;
    private CreateCustomerAccountPage createCustomerAccountPage;

    private TravelAdvisorHomePage travelAdvisorHomePage;
    private IssueRefundPage issueRefundPage;
    private SellTicketPage sellTicketPage;

    private AdministratorHomePage administratorHomePage;
    private CreateTravelAgentAccountPage createTravelAgentAccountPage;
    private SecurityPage securityPage;
    private CommissionPage commissionPage;
    private TicketStockPage ticketStockPage;
    private UpdateDetailsPage updateDetailsPage;




    public AirViaLtd() {

        loginPage = new LoginPage(this);

        officeManagerHomePage = new OfficeManagerHomePage(this);
        allocateBlankPage = new AllocateBlankPage(this);
        addBlankPage = new AddBlankPage(this);
        blankStockPage = new BlankStockPage(this);
        createReportPage = new CreateReportPage(this);
        discountPlanPage = new DiscountPlanPage(this);
        createCustomerAccountPage = new CreateCustomerAccountPage(this);

        travelAdvisorHomePage = new TravelAdvisorHomePage(this);
        issueRefundPage = new IssueRefundPage(this);
        sellTicketPage = new SellTicketPage(this);

        administratorHomePage = new AdministratorHomePage(this);
        createTravelAgentAccountPage = new CreateTravelAgentAccountPage(this);
        securityPage = new SecurityPage(this);
        commissionPage = new CommissionPage(this);
        ticketStockPage = new TicketStockPage(this);
        updateDetailsPage = new UpdateDetailsPage(this);


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
        if (loginPage.getUser().equals("Office Manager")){
            frame.remove(officeManagerHomePage.getMainPanel());
        } else if (loginPage.getUser().equals("Administrator")){
            frame.remove(administratorHomePage.getMainPanel());
        }
        frame.add(blankStockPage.getMainPanel());
        frame.pack();
    }

    public void transitionToCreateReportPage(){
        if (loginPage.getUser().equals("Office Manager")){
            frame.remove(officeManagerHomePage.getMainPanel());
        } else if (loginPage.getUser().equals("Travel Advisor")){
            frame.remove(travelAdvisorHomePage.getMainPanel());
        }
        frame.add(createReportPage.getMainPanel());
        frame.pack();
    }

    public void transitionToDiscountPlanPage(){
        frame.remove(officeManagerHomePage.getMainPanel());
        frame.add(discountPlanPage.getMainPanel());
        frame.pack();
    }

    public void transitionToCreateCustomerAccountPage(){
        if (loginPage.getUser().equals("Office Manager")){
            frame.remove(officeManagerHomePage.getMainPanel());
        } else if (loginPage.getUser().equals("Travel Advisor")){
            frame.remove(travelAdvisorHomePage.getMainPanel());
        }
        frame.add(createCustomerAccountPage.getMainPanel());
        frame.pack();
    }

    public void transitionToTravelAdvisorHomePage(){
        frame.remove(loginPage.getMainPanel());
        frame.add(travelAdvisorHomePage.getMainPanel());
        frame.pack();
    }

    public void transitionToIssueRefundPage(){
        frame.remove(travelAdvisorHomePage.getMainPanel());
        frame.add(issueRefundPage.getMainPanel());
        frame.pack();
    }

    public void transitionToSellTicketPage(){
        frame.remove(travelAdvisorHomePage.getMainPanel());
        frame.add(sellTicketPage.getMainPanel());
        frame.pack();
    }

    public void transitionToAdministratoHomePage(){
        frame.remove(loginPage.getMainPanel());
        frame.add(administratorHomePage.getMainPanel());
        frame.pack();
    }

    public void transitionToCreateTravelAgentAccountPage(){
        frame.remove(administratorHomePage.getMainPanel());
        frame.add(createTravelAgentAccountPage.getMainPanel());
        frame.pack();
    }

    public void transitionToSecurityPage(){
        frame.remove(administratorHomePage.getMainPanel());
        frame.add(securityPage.getMainPanel());
        frame.pack();
    }

    public void transitionToCommissionPage(){
        frame.remove(administratorHomePage.getMainPanel());
        frame.add(commissionPage.getMainPanel());
        frame.pack();
    }

    public void transitionToTicketStockPage(){
        frame.remove(administratorHomePage.getMainPanel());
        frame.add(ticketStockPage.getMainPanel());
        frame.pack();
    }

    public void transitionToUpdateDetailsPage(){
        frame.remove(administratorHomePage.getMainPanel());
        frame.add(updateDetailsPage.getMainPanel());
        frame.pack();
    }









    public static void main(String[] args) {
        new AirViaLtd();
    }

}
