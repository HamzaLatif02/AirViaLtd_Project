package AirViaLtd;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class CreateCustomerAccountPage {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel featurePanel;
    private JLabel createCustomerAccountLabel;
    private JTextField nameTextField;
    private JTextField surnameTextField;
    private JTextField emailAddressTextField;
    private JTextField regularOrValuedTextField;
    private JButton createButton;

    private AirViaLtd app;

    public CreateCustomerAccountPage(AirViaLtd a) {
        this.app = a;

        addNameTextListener();
        addSurnameTextListener();
        addEmailTextListener();
        addRegularValuedTextListener();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void addNameTextListener(){
        nameTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                if (nameTextField.getText().equals("name")){
                    nameTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                if (nameTextField.getText().equals("")){
                    nameTextField.setText("name");
                }
            }
        });
    }

    public void addSurnameTextListener(){
        surnameTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                if (surnameTextField.getText().equals("surname")){
                    surnameTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                if (surnameTextField.getText().equals("")){
                    surnameTextField.setText("surname");
                }
            }
        });
    }

    public void addEmailTextListener(){
        emailAddressTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                if (emailAddressTextField.getText().equals("email address")){
                    emailAddressTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                if (emailAddressTextField.getText().equals("")){
                    emailAddressTextField.setText("email address");
                }
            }
        });
    }

    public void addRegularValuedTextListener(){
        regularOrValuedTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                if (regularOrValuedTextField.getText().equals("regular or valued")){
                    regularOrValuedTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                if (regularOrValuedTextField.getText().equals("")){
                    regularOrValuedTextField.setText("regular or valued");
                }
            }
        });
    }
}
