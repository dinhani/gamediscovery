package gd.matrixdistance;

import java.io.IOException;
import org.la4j.matrix.sparse.CRSMatrix;

public class MatrixDistance {

    public static void main(String[] args) throws IOException {
        new MatrixDistance().execute();
    }

    public void execute() throws IOException {
        // read
        System.out.println("\nREADING");
        CRSMatrix inputMatrix = new MatrixDistanceReader().read("../data/matrix_distance_input.csv");

        // calculate        
        System.out.println("\nCALCULATING");
        long start = System.currentTimeMillis();
        float[][] outputMatrix = new MatrixDistanceComputation(inputMatrix).compute();
        inputMatrix = null; // free memory        
        System.out.println((System.currentTimeMillis() - start) + "ms");

        // export results        
        System.out.println("\nRESULTS");
        new MatrixDistanceExporter().export(outputMatrix, "../data/matrix_distance_results_64shorts.bin");
    }
}
