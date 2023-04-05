package AirViaLtd;

import javax.swing.*;
import java.awt.*;

public class AirViaLtd {

    private final JFrame frame;

    private LoginPage loginPage;

    private OfficeManagerHomePage officeManagerHomePage;

    private BlankManagerPage blankManagerPage;
    private AllocateBlankPage allocateBlankPage;
    private AddBlankPage addBlankPage;
    private RemoveBlankPage removeBlankPage;
    private EditBlankPage editBlankPage;
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
        blankManagerPage = new BlankManagerPage(this);
        allocateBlankPage = new AllocateBlankPage(this);
        addBlankPage = new AddBlankPage(this);
        removeBlankPage = new RemoveBlankPage(this);
        editBlankPage = new EditBlankPage(this);
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
        frame.setVisible(true);
    }

    public void transitionToBlankManagerPage(){
        frame.remove(officeManagerHomePage.getMainPanel());
        frame.add(blankManagerPage.getMainPanel());
        frame.setVisible(true);
    }

    public void transitionToAllocateBlankPage(){
        frame.remove(blankManagerPage.getMainPanel());
        frame.add(allocateBlankPage.getMainPanel());
        frame.add(allocateBlankPage.getNotAssignedBlanksScrollPane(), BorderLayout.EAST);
        frame.setVisible(true);
    }

    public void transitionToAddBlankPage(){
        frame.remove(blankManagerPage.getMainPanel());
        frame.add(addBlankPage.getMainPanel());
        frame.setVisible(true);
    }

    public void transitionToRemoveBlankPage(){
        frame.remove(blankManagerPage.getMainPanel());
        frame.add(removeBlankPage.getMainPanel());
        frame.add(removeBlankPage.getBlankStockScrollPane(), BorderLayout.EAST);
        frame.setVisible(true);
    }

    public void transitionToEditBlankPage(){
        frame.remove(blankManagerPage.getMainPanel());
        frame.add(editBlankPage.getMainPanel());
        frame.add(editBlankPage.getBlankStockScrollPane(), BorderLayout.EAST);
        frame.setVisible(true);
    }

    public void transitionToBlankStockPage(){
        frame.remove(getCurrentHomepage());
        frame.add(blankStockPage.getMainPanel());
        frame.add(blankStockPage.getBlankStockScrollPane(), BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public void transitionToCreateReportPage(){
        frame.remove(getCurrentHomepage());
        frame.add(createReportPage.getMainPanel());
        frame.setVisible(true);
    }

    public void transitionToDiscountPlanPage(){
        frame.remove(officeManagerHomePage.getMainPanel());
        frame.add(discountPlanPage.getMainPanel());
        frame.setVisible(true);
    }

    public void transitionToCreateCustomerAccountPage(){
        frame.remove(getCurrentHomepage());
        frame.add(createCustomerAccountPage.getMainPanel());
        frame.setVisible(true);
    }

    public void transitionToTravelAdvisorHomePage(){
        frame.remove(loginPage.getMainPanel());
        frame.add(travelAdvisorHomePage.getMainPanel());
        frame.setVisible(true);
    }

    public void transitionToIssueRefundPage(){
        frame.remove(travelAdvisorHomePage.getMainPanel());
        frame.add(issueRefundPage.getMainPanel());
        frame.setVisible(true);
    }

    public void transitionToSellTicketPage(){
        frame.remove(travelAdvisorHomePage.getMainPanel());
        frame.add(sellTicketPage.getMainPanel());
        frame.setVisible(true);
    }

    public void transitionToAdministratoHomePage(){
        frame.remove(loginPage.getMainPanel());
        frame.add(administratorHomePage.getMainPanel());
        frame.setVisible(true);
    }

    public void transitionToCreateTravelAgentAccountPage(){
        frame.remove(administratorHomePage.getMainPanel());
        frame.add(createTravelAgentAccountPage.getMainPanel());
        frame.setVisible(true);
    }

    public void transitionToSecurityPage(){
        frame.remove(administratorHomePage.getMainPanel());
        frame.add(securityPage.getMainPanel());
        frame.setVisible(true);
    }

    public void transitionToCommissionPage(){
        frame.remove(administratorHomePage.getMainPanel());
        frame.add(commissionPage.getMainPanel());
        frame.setVisible(true);
    }

    public void transitionToTicketStockPage(){
        frame.remove(administratorHomePage.getMainPanel());
        frame.add(ticketStockPage.getMainPanel());
        frame.setVisible(true);
    }

    public void transitionToUpdateDetailsPage(){
        frame.remove(administratorHomePage.getMainPanel());
        frame.add(updateDetailsPage.getMainPanel());
        frame.setVisible(true);
    }

    public JPanel getCurrentHomepage(){
        if (loginPage.getUser().equals("Administrator")){
            return administratorHomePage.getMainPanel();
        } else if (loginPage.getUser().equals("Office Manager")){
            return officeManagerHomePage.getMainPanel();
        } else return travelAdvisorHomePage.getMainPanel();
    }

    public void doLogout(){
        if (JOptionPane.showConfirmDialog(getCurrentHomepage(), "Are you sure you want to log out?", "Logout", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            JOptionPane.showConfirmDialog(getCurrentHomepage(), "You have successfully logged out", "Logout", JOptionPane.PLAIN_MESSAGE);

            frame.remove(getCurrentHomepage());


            loginPage = new LoginPage(this);

            frame.add(loginPage.getMainPanel());
            frame.setVisible(true);
        };
    }

    public void addReportToPage(){
        frame.add(createReportPage.getReportScrollPane(), BorderLayout.EAST);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new AirViaLtd();
    }

}
