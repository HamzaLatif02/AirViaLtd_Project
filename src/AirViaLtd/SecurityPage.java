package AirViaLtd;

import javax.swing.*;

public class SecurityPage {
    private JButton restoreDataButton;
    private JButton backUpDataButton;
    private JPanel mainPanel;


    private AirViaLtd app;

    public SecurityPage(AirViaLtd a) {

        this.app = a;

        /*restoreDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToRestoreData();
            }
        });

        backUpDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.transitionToBackUpData();
            }
        });*/

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

}