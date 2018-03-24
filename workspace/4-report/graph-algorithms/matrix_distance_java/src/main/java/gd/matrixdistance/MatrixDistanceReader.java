package gd.matrixdistance;

import com.opencsv.CSVReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.la4j.matrix.sparse.CRSMatrix;

public class MatrixDistanceReader {

    public CRSMatrix read(String path) throws UnsupportedEncodingException, FileNotFoundException, IOException {
        // open file
        Reader reader = new InputStreamReader(new FileInputStream(path), "utf-8");
        CSVReader csvReader = new CSVReader(reader, ',', '"', 0);

        // read first line to determine matrix size
        String[] line = csvReader.readNext();
        int numberOfRows = (int) Files.lines(Paths.get(path)).count();
        int numberOfColumns = line.length;
        
        CRSMatrix inputMatrix = CRSMatrix.zero(numberOfRows, numberOfColumns);

        // read rows in file to matrix
        int rowIndex = 0;
        do {
            // transform line
            if (rowIndex % 1000 == 0) {
                System.out.println(rowIndex);
            }
            for (int columnIndex = 0; columnIndex < line.length; columnIndex++) {
                inputMatrix.set(rowIndex, columnIndex, Double.parseDouble(line[columnIndex]));                
            }

            // read next line
            line = csvReader.readNext();
            rowIndex++;
        } while (line != null);

        return inputMatrix;
    }
}
