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

    private JPanel currentPanel;

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

        currentPanel = new JPanel();

        frame = new JFrame("AirViaLtd");

        frame.add(loginPage.getMainPanel());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setResizable(false);
        frame.setVisible(true);

        currentPanel = loginPage.getMainPanel();

    }

    public void transitionToOfficeManagerHomePage(){
        frame.remove(loginPage.getMainPanel());
        officeManagerHomePage = new OfficeManagerHomePage(this);
        frame.add(officeManagerHomePage.getMainPanel());
        frame.setVisible(true);
        currentPanel = officeManagerHomePage.getMainPanel();
    }

    public void transitionToBlankManagerPage(){
        frame.remove(currentPanel);
        blankManagerPage = new BlankManagerPage(this);
        frame.add(blankManagerPage.getMainPanel());
        frame.setVisible(true);
        currentPanel = blankManagerPage.getMainPanel();
    }

    public void transitionToAllocateBlankPage(){
        frame.remove(currentPanel);
        allocateBlankPage = new AllocateBlankPage(this);
        frame.add(allocateBlankPage.getMainPanel());
        frame.add(allocateBlankPage.getNotAssignedBlanksScrollPane(), BorderLayout.EAST);
        frame.setVisible(true);
        currentPanel = allocateBlankPage.getMainPanel();
    }

    public void transitionToAddBlankPage(){
        frame.remove(currentPanel);
        addBlankPage = new AddBlankPage(this);
        frame.add(addBlankPage.getMainPanel());
        frame.setVisible(true);
        currentPanel = addBlankPage.getMainPanel();
    }

    public void transitionToRemoveBlankPage(){
        frame.remove(currentPanel);
        removeBlankPage = new RemoveBlankPage(this);
        frame.add(removeBlankPage.getMainPanel());
        frame.add(removeBlankPage.getBlankStockScrollPane(), BorderLayout.EAST);
        frame.setVisible(true);
        currentPanel = removeBlankPage.getMainPanel();
    }

    public void transitionToEditBlankPage(){
        frame.remove(currentPanel);
        editBlankPage = new EditBlankPage(this);
        frame.add(editBlankPage.getMainPanel());
        frame.add(editBlankPage.getBlankStockScrollPane(), BorderLayout.EAST);
        frame.setVisible(true);
        currentPanel = editBlankPage.getMainPanel();
    }

    public void transitionToReallocateBlankPage(){
        frame.remove(currentPanel);
        reallocateBlankPage = new ReallocateBlankPage(this);
        frame.add(reallocateBlankPage.getMainPanel());
        frame.add(reallocateBlankPage.getAssignedBlanksScrollPane(), BorderLayout.EAST);
        frame.setVisible(true);
        currentPanel = reallocateBlankPage.getMainPanel();
    }

    public void transitionToSearchBlankPage(){
        frame.remove(currentPanel);
        searchBlankPage = new SearchBlankPage(this);
        frame.add(searchBlankPage.getMainPanel());
        frame.setVisible(true);
        currentPanel = searchBlankPage.getMainPanel();
    }

    public void transitionToBlankStockPage(){
        frame.remove(currentPanel);
        blankStockPage = new BlankStockPage(this);
        frame.add(blankStockPage.getMainPanel());
        frame.add(blankStockPage.getBlankStockScrollPane(), BorderLayout.SOUTH);
        frame.setVisible(true);
        currentPanel = blankStockPage.getMainPanel();
    }

    public void transitionToCreateReportPage(){
        frame.remove(currentPanel);
        createReportPage = new CreateReportPage(this);
        frame.add(createReportPage.getMainPanel());
        frame.setVisible(true);
        currentPanel = createReportPage.getMainPanel();
    }

    public void transitionToDiscountPlanPage(){
        frame.remove(currentPanel);
        discountPlanPage = new DiscountPlanPage(this);
        frame.add(discountPlanPage.getMainPanel());
        frame.setVisible(true);
        currentPanel = discountPlanPage.getMainPanel();
    }

    public void transitionToManageCustomerPage(){
        frame.remove(currentPanel);
        manageCustomerPage = new ManageCustomerPage(this);
        frame.add(manageCustomerPage.getMainPanel());
        frame.setVisible(true);
        currentPanel = manageCustomerPage.getMainPanel();
    }

    public void transitionToCreateCustomerAccountPage(){
        frame.remove(currentPanel);
        createCustomerAccountPage = new CreateCustomerAccountPage(this);
        frame.add(createCustomerAccountPage.getMainPanel());
        frame.setVisible(true);
        currentPanel = createCustomerAccountPage.getMainPanel();
    }

    public void transitionToEditCustomerAccountPage(){
        frame.remove(currentPanel);
        editCustomerAccountPage = new EditCustomerAccountPage(this);
        frame.add(editCustomerAccountPage.getMainPanel());
        frame.setVisible(true);
        currentPanel = editCustomerAccountPage.getMainPanel();
    }

    public void transitionToRemoveCustomerAccountPage(){
        frame.remove(currentPanel);
        removeCustomerAccountPage = new RemoveCustomerAccountPage(this);
        frame.add(removeCustomerAccountPage.getMainPanel());
        frame.setVisible(true);
        currentPanel = removeCustomerAccountPage.getMainPanel();
    }

    public void transitionToTravelAdvisorHomePage(){
        frame.remove(currentPanel);
        frame.add(travelAdvisorHomePage.getMainPanel());
        frame.setVisible(true);
        currentPanel = travelAdvisorHomePage.getMainPanel();
    }

    public void transitionToIssueRefundPage(){
        frame.remove(currentPanel);
        issueRefundPage = new IssueRefundPage(this);
        frame.add(issueRefundPage.getMainPanel());
        frame.setVisible(true);
        currentPanel = issueRefundPage.getMainPanel();
    }

    public void transitionToSellTicketPage(){
        frame.remove(currentPanel);
        sellTicketPage = new SellTicketPage(this);
        frame.add(sellTicketPage.getMainPanel());
        frame.setVisible(true);
        currentPanel = sellTicketPage.getMainPanel();
    }

    public void transitionToAdministratorHomePage(){
        frame.remove(currentPanel);
        frame.add(administratorHomePage.getMainPanel());
        frame.setVisible(true);
        currentPanel = administratorHomePage.getMainPanel();
    }

    public void transitionToManageUsersPage(){
        frame.remove(currentPanel);
        manageUsersPage = new ManageUsersPage(this);
        frame.add(manageUsersPage.getMainPanel());
        frame.setVisible(true);
        currentPanel = manageUsersPage.getMainPanel();

    }

    public void transitionToSecurityPage(){
        frame.remove(currentPanel);
        securityPage = new SecurityPage(this);
        frame.add(securityPage.getMainPanel());
        frame.setVisible(true);
        currentPanel = securityPage.getMainPanel();
    }

    public void transitionToCommissionPage(){
        frame.remove(currentPanel);
        commissionPage = new CommissionPage(this);
        frame.add(commissionPage.getMainPanel());
        frame.setVisible(true);
        currentPanel = commissionPage.getMainPanel();
    }

    public void transitionToManageOfficeManagerPage(){
        frame.remove(currentPanel);
        manageOfficeManagerPage = new ManageOfficeManagerPage(this);
        frame.add(manageOfficeManagerPage.getMainPanel());
        frame.setVisible(true);
        currentPanel = manageOfficeManagerPage.getMainPanel();
    }

    public void transitionToCreateOfficeManagerAccountPage(){
        frame.remove(currentPanel);
        createOfficeManagerAccountPage = new CreateOfficeManagerAccountPage(this);
        frame.add(createOfficeManagerAccountPage.getMainPanel());
        frame.setVisible(true);
        currentPanel = createOfficeManagerAccountPage.getMainPanel();
    }

    public void transitionToEditOfficeManagerAccountPage(){
        frame.remove(currentPanel);
        editOfficeManagerAccountPage = new EditOfficeManagerAccountPage(this);
        frame.add(editOfficeManagerAccountPage.getMainPanel());
        frame.setVisible(true);
        currentPanel = editOfficeManagerAccountPage.getMainPanel();
    }

    public void transitionToRemoveOfficeManagerAccountPage(){
        frame.remove(currentPanel);
        removeOfficeManagerAccountPage = new RemoveOfficeManagerAccountPage(this);
        frame.add(removeOfficeManagerAccountPage.getMainPanel());
        frame.setVisible(true);
        currentPanel = removeOfficeManagerAccountPage.getMainPanel();
    }


    public void transitionToManageTravelAdvisorPage(){
        frame.remove(currentPanel);
        manageTravelAdvisorPage = new ManageTravelAdvisorPage(this);
        frame.add(manageTravelAdvisorPage.getMainPanel());
        frame.setVisible(true);
        currentPanel = manageTravelAdvisorPage.getMainPanel();
    }

    public void transitionToCreateTravelAdvisorAccountPage(){
        frame.remove(currentPanel);
        createTravelAdvisorAccountPage = new CreateTravelAdvisorAccountPage(this);
        frame.add(createTravelAdvisorAccountPage.getMainPanel());
        frame.setVisible(true);
        currentPanel = createTravelAdvisorAccountPage.getMainPanel();
    }

    public void transitionToEditTravelAdvisorAccountPage(){
        frame.remove(currentPanel);
        editTravelAdvisorAccountPage = new EditTravelAdvisorAccountPage(this);
        frame.add(editTravelAdvisorAccountPage.getMainPanel());
        frame.setVisible(true);
        currentPanel = editTravelAdvisorAccountPage.getMainPanel();
    }

    public void transitionToRemoveTravelAdvisorAccountPage(){
        frame.remove(currentPanel);
        removeTravelAdvisorAccountPage = new RemoveTravelAdvisorAccountPage(this);
        frame.add(removeTravelAdvisorAccountPage.getMainPanel());
        frame.setVisible(true);
        currentPanel = removeTravelAdvisorAccountPage.getMainPanel();
    }

    public void transitionToHomepage(){
        frame.remove(currentPanel);
        removeTables();
        frame.add(newHomepage());
        frame.setVisible(true);
        currentPanel = getCurrentHomepage();
    }

    public JPanel getCurrentHomepage(){
        if (loginPage.getUser().equals("Administrator")){
            return administratorHomePage.getMainPanel();
        } else if (loginPage.getUser().equals("Office Manager")){
            return officeManagerHomePage.getMainPanel();
        } else return travelAdvisorHomePage.getMainPanel();
    }

    public JPanel newHomepage(){
        if (loginPage.getUser().equals("Administrator")){
            administratorHomePage = new AdministratorHomePage(this);
            return administratorHomePage.getMainPanel();
        } else if (loginPage.getUser().equals("Office Manager")){
            officeManagerHomePage = new OfficeManagerHomePage(this);
            return officeManagerHomePage.getMainPanel();
        } else {
            travelAdvisorHomePage = new TravelAdvisorHomePage(this);
            return travelAdvisorHomePage.getMainPanel();}
    }

    public void removeTables(){

        if (allocateBlankPage.getNotAssignedBlanksScrollPane() != null){
            frame.remove(allocateBlankPage.getNotAssignedBlanksScrollPane());
        }

        if (removeBlankPage.getBlankStockScrollPane() != null){
            frame.remove(removeBlankPage.getBlankStockScrollPane());
        }

        if (editBlankPage.getBlankStockScrollPane() != null){
            frame.remove(editBlankPage.getBlankStockScrollPane());
        }

        if (reallocateBlankPage.getAssignedBlanksScrollPane() != null){
            frame.remove(reallocateBlankPage.getAssignedBlanksScrollPane());
        }

        if (blankStockPage.getBlankStockScrollPane() != null){
            frame.remove(blankStockPage.getBlankStockScrollPane());
        }

        if (createReportPage.getReportScrollPane() != null){
            frame.remove(createReportPage.getReportScrollPane());
        }


    }

    public void doLogout(){
        if (JOptionPane.showConfirmDialog(getCurrentHomepage(), "Are you sure you want to log out?", "Logout", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            JOptionPane.showConfirmDialog(getCurrentHomepage(), "You have successfully logged out", "Logout", JOptionPane.PLAIN_MESSAGE);

            frame.remove(getCurrentHomepage());

            loginPage = new LoginPage(this);

            frame.add(loginPage.getMainPanel());
            frame.setVisible(true);
            currentPanel = loginPage.getMainPanel();
        };
    }

    public void addReportToPage(){
        frame.remove(createReportPage.getReportScrollPane());
        frame.add(createReportPage.getReportScrollPane(), BorderLayout.EAST);
        frame.setVisible(true);
    }

    public SellTicketPage getSellTicketPage() {
        return sellTicketPage;
    }

    public IssueRefundPage getIssueRefundPage() {
        return issueRefundPage;
    }

    public OfficeManagerHomePage getOfficeManagerHomePage() {
        return officeManagerHomePage;
    }

    public TravelAdvisorHomePage getTravelAdvisorHomePage() {
        return travelAdvisorHomePage;
    }

    public AdministratorHomePage getAdministratorHomePage() {
        return administratorHomePage;
    }

    public static void main(String[] args){
        new AirViaLtd();
    }

}
