package AirViaLtd;

import javax.swing.*;

public class TicketStockPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel buttonsPanel;
    private JPanel tablePanel;
    private JLabel ticketStockLabel;
    private JLabel sortByLabel;
    private JButton ticketNumberButton;
    private JButton priceButton;
    private JButton blankIDButton;
    private JTable ticketTable;

    private AirViaLtd app;

    public TicketStockPage(AirViaLtd a) {
        this.app = a;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
