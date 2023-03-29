package AirViaLtd;

import javax.swing.*;

public class DiscountPlanPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel featurePanel;
    private JLabel selectCustomerLabel;
    private JComboBox customerComboBox;
    private JLabel selectMonthLabel;
    private JComboBox monthComboBox;
    private JTextField salesTextField;
    private JLabel salesDuringThisPeriodLabel;
    private JComboBox discountPlanComboBox;
    private JLabel selectDiscountPlanLabel;
    private JTextField discountAmountTextField;
    private JLabel discountAmountLabel;
    private JButton setDiscountButton;

    private AirViaLtd app;

    public DiscountPlanPage(AirViaLtd a) {
        this.app = a;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
