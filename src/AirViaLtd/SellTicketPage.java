package AirViaLtd;

import javax.swing.*;

public class SellTicketPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel infoPanel;
    private JLabel sellTicketLabel;
    private JTextField ticketNumberTextField;
    private JLabel ticketNumberLabel;
    private JTextField interlineDomesticTextField;
    private JTextField basePriceTextField;
    private JLabel customerEmailAddressLabel;
    private JTextField customerEmailAddressTextField;
    private JComboBox commissionRateComboBox;
    private JLabel selectCommissionRateLabel;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JLabel advisorCodeLabel;
    private JTextField advisorCodeTextField;
    private JLabel addTaxesLabel;
    private JTextField localTaxesTextField;
    private JTextField otherTaxesTextField;
    private JButton calculateTotalButton;
    private JTextField totalPriceTextField;
    private JTextField commissionAmountTextField;
    private JLabel commissionAmountLabel;
    private JButton searchButton;
    private JComboBox paymentTypeComboBox;
    private JComboBox payLaterComboBox;
    private JComboBox discountAvailableComboBox;
    private JTextField discountedPriceTextField;
    private JButton sellButton;

    private AirViaLtd app;

    public SellTicketPage(AirViaLtd a) {
        this.app = a;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
