package AirViaLtd;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelExporter {
    // Constructor for the ExcelExporter class
    ExcelExporter() {
    }

    // Method to export JTable data to an Excel file
    public void exportTable(ArrayList<JTable> jTable, File file) throws IOException {

        // Create a FileWriter and BufferedWriter object to write to the output file
        FileWriter out = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(out);

        // Loop through all the JTable objects in the ArrayList
        for (JTable table : jTable){
            // Get the TableModel for the current JTable
            TableModel model = table.getModel();

            // Write the column headers to the file
            for (int i = 0; i < model.getColumnCount(); i++) {
                bw.write(model.getColumnName(i) + "\t");
            }

            // Write a newline character after the column headers
            bw.write("\n");

            // Loop through each row in the table
            for (int i = 0; i < model.getRowCount(); i++) {
                // Loop through each column in the row
                for (int j = 0; j < model.getColumnCount(); j++) {
                    // Write the value at the current cell to the file
                    bw.write(model.getValueAt(i,j)+"\t");
                }
                // Write a newline character after the row is complete
                bw.write("\n");
            }

            // Write an additional newline character after the table is complete
            bw.write("\n");
        }

        // Close the BufferedWriter object
        bw.close();
    }
}
