package AirViaLtd;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelExporter {

    ExcelExporter() {
    }

    public void exportTable(ArrayList<JTable> jTable, File file) throws IOException {

        FileWriter out = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(out);

        for (JTable table : jTable){
            TableModel model = table.getModel();

            for (int i = 0; i < model.getColumnCount(); i++) {
                bw.write(model.getColumnName(i) + "\t");
            }
            bw.write("\n");
            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    bw.write(model.getValueAt(i,j)+"\t");
                }
                bw.write("\n");
            }

            bw.write("\n");
        }

        bw.close();
        //System.out.print("Write out to" + file);
    }
}
