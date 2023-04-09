package AirViaLtd;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.security.CodeSource;

public class AirViaLtd {

    private final JFrame frame;

    private LoginPage loginPage;

    private OfficeManagerHomePage officeManagerHomePage;

    private BlankManagerPage blankManagerPage;
    private AllocateBlankPage allocateBlankPage;
    private AddBlankPage addBlankPage;
    private RemoveBlankPage removeBlankPage;
    private EditBlankPage editBlankPage;
    private ReallocateBlankPage reallocateBlankPage;
    private SearchBlankPage searchBlankPage;
    private BlankStockPage blankStockPage;
    private CreateReportPage createReportPage;
    private DiscountPlanPage discountPlanPage;
    private ManageCustomerPage manageCustomerPage;
    private CreateCustomerAccountPage createCustomerAccountPage;
    private EditCustomerAccountPage editCustomerAccountPage;
    private RemoveCustomerAccountPage removeCustomerAccountPage;

    private TravelAdvisorHomePage travelAdvisorHomePage;
    private IssueRefundPage issueRefundPage;
    private SellTicketPage sellTicketPage;

    private AdministratorHomePage administratorHomePage;
    private ManageUsersPage manageUsersPage;
    private SecurityPage securityPage;
    private CommissionPage commissionPage;

    private ManageOfficeManagerPage manageOfficeManagerPage;
    private CreateOfficeManagerAccountPage createOfficeManagerAccountPage;
    private EditOfficeManagerAccountPage editOfficeManagerAccountPage;
    private RemoveOfficeManagerAccountPage removeOfficeManagerAccountPage;

    private ManageTravelAdvisorPage manageTravelAdvisorPage;
    private CreateTravelAdvisorAccountPage createTravelAdvisorAccountPage;
    private EditTravelAdvisorAccountPage editTravelAdvisorAccountPage;
    private RemoveTravelAdvisorAccountPage removeTravelAdvisorAccountPage;




    public AirViaLtd() {

        loginPage = new LoginPage(this);

        officeManagerHomePage = new OfficeManagerHomePage(this);
        blankManagerPage = new BlankManagerPage(this);
        allocateBlankPage = new AllocateBlankPage(this);
        addBlankPage = new AddBlankPage(this);
        reallocateBlankPage = new ReallocateBlankPage(this);
        removeBlankPage = new RemoveBlankPage(this);
        editBlankPage = new EditBlankPage(this);
        searchBlankPage = new SearchBlankPage(this);
        blankStockPage = new BlankStockPage(this);
        createReportPage = new CreateReportPage(this);
        discountPlanPage = new DiscountPlanPage(this);
        manageCustomerPage = new ManageCustomerPage(this);
        createCustomerAccountPage = new CreateCustomerAccountPage(this);
        editCustomerAccountPage = new EditCustomerAccountPage(this);
        removeCustomerAccountPage = new RemoveCustomerAccountPage(this);

        travelAdvisorHomePage = new TravelAdvisorHomePage(this);
        issueRefundPage = new IssueRefundPage(this);
        sellTicketPage = new SellTicketPage(this);

        administratorHomePage = new AdministratorHomePage(this);
        manageUsersPage = new ManageUsersPage(this);
        securityPage = new SecurityPage(this);
        commissionPage = new CommissionPage(this);

        manageOfficeManagerPage = new ManageOfficeManagerPage(this);
        createOfficeManagerAccountPage = new CreateOfficeManagerAccountPage(this);
        editOfficeManagerAccountPage = new EditOfficeManagerAccountPage(this);
        removeOfficeManagerAccountPage = new RemoveOfficeManagerAccountPage(this);

        manageTravelAdvisorPage = new ManageTravelAdvisorPage(this);
        createTravelAdvisorAccountPage = new CreateTravelAdvisorAccountPage(this);
        editTravelAdvisorAccountPage = new EditTravelAdvisorAccountPage(this);
        removeTravelAdvisorAccountPage = new RemoveTravelAdvisorAccountPage(this);


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

    public void transitionToReallocateBlankPage(){
        frame.remove(blankManagerPage.getMainPanel());
        frame.add(reallocateBlankPage.getMainPanel());
        frame.add(reallocateBlankPage.getAssignedBlanksScrollPane(), BorderLayout.EAST);
        frame.setVisible(true);
    }

    public void transitionToSearchBlankPage(){
        frame.remove(officeManagerHomePage.getMainPanel());
        frame.add(searchBlankPage.getMainPanel());
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

    public void transitionToManageCustomerPage(){
        if (loginPage.getUser().equals("Office Manager")){
            frame.remove(officeManagerHomePage.getMainPanel());
        } else if (loginPage.getUser().equals("Administrator")){
            frame.remove(manageUsersPage.getMainPanel());
        } else {
            frame.remove(travelAdvisorHomePage.getMainPanel());
        }
        frame.add(manageCustomerPage.getMainPanel());
        frame.setVisible(true);
    }

    public void transitionToCreateCustomerAccountPage(){
        if (loginPage.getUser().equals("Travel Advisor")){
            frame.remove(travelAdvisorHomePage.getMainPanel());
        } else {
            frame.remove(manageCustomerPage.getMainPanel());
        }
        frame.add(createCustomerAccountPage.getMainPanel());
        frame.setVisible(true);
    }

    public void transitionToEditCustomerAccountPage(){
        frame.remove(manageCustomerPage.getMainPanel());
        frame.add(editCustomerAccountPage.getMainPanel());
        frame.setVisible(true);
    }

    public void transitionToRemoveCustomerAccountPage(){
        frame.remove(manageCustomerPage.getMainPanel());
        frame.add(removeCustomerAccountPage.getMainPanel());
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

    public void transitionToManageUsersPage(){
        frame.remove(administratorHomePage.getMainPanel());
        frame.add(manageUsersPage.getMainPanel());
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

    public void transitionToManageOfficeManagerPage(){
        frame.remove(manageUsersPage.getMainPanel());
        frame.add(manageOfficeManagerPage.getMainPanel());
        frame.setVisible(true);
    }

    public void transitionToCreateOfficeManagerAccountPage(){
        frame.remove(manageOfficeManagerPage.getMainPanel());
        frame.add(createOfficeManagerAccountPage.getMainPanel());
        frame.setVisible(true);
    }

    public void transitionToEditOfficeManagerAccountPage(){
        frame.remove(manageOfficeManagerPage.getMainPanel());
        frame.add(editOfficeManagerAccountPage.getMainPanel());
        frame.setVisible(true);
    }

    public void transitionToRemoveOfficeManagerAccountPage(){
        frame.remove(manageOfficeManagerPage.getMainPanel());
        frame.add(removeOfficeManagerAccountPage.getMainPanel());
        frame.setVisible(true);
    }


    public void transitionToManageTravelAdvisorPage(){
        frame.remove(manageUsersPage.getMainPanel());
        frame.add(manageTravelAdvisorPage.getMainPanel());
        frame.setVisible(true);
    }

    public void transitionToCreateTravelAdvisorAccountPage(){
        frame.remove(manageTravelAdvisorPage.getMainPanel());
        frame.add(createTravelAdvisorAccountPage.getMainPanel());
        frame.setVisible(true);
    }
    public void transitionToEditTravelAdvisorAccountPage(){
        frame.remove(manageTravelAdvisorPage.getMainPanel());
        frame.add(editTravelAdvisorAccountPage.getMainPanel());
        frame.setVisible(true);
    }
    public void transitionToRemoveTravelAdvisorAccountPage(){
        frame.remove(manageTravelAdvisorPage.getMainPanel());
        frame.add(removeTravelAdvisorAccountPage.getMainPanel());
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

    public SellTicketPage getSellTicketPage() {
        return sellTicketPage;
    }

    public IssueRefundPage getIssueRefundPage() {
        return issueRefundPage;
    }

    public static void main(String[] args){
        new AirViaLtd();
    }

}
