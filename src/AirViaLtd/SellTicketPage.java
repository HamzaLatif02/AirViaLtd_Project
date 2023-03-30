package AirViaLtd;

import javax.swing.*;

public class SellTicketPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel infoPanel;
    private JPanel buttonsPanel;
    private JLabel sellTicketLabel;
    private JTextField ticketNumberTextField;
    private JLabel ticketNumberLabel;
    private JTextField interlineDomesticTextField;
    private JTextField totalSaleTextField;
    private JLabel customerEmailAddressLabel;
    private JTextField customerEmailAddressTextField;
    private JComboBox commissionRateComboBox;
    private JLabel selectCommissionRateLabel;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField paymentTypeTextField;
    private JTextField payLaterTextField;
    private JTextField discountTextField;
    private JButton applyDiscountButton;
    private JButton sellTicketButton;

    private AirViaLtd app;

    public SellTicketPage(AirViaLtd a) {
        this.app = a;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
