package gd.matrixdistance;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.la4j.Vector;
import org.la4j.Vectors;
import org.la4j.iterator.VectorIterator;
import org.la4j.matrix.sparse.CRSMatrix;
import org.la4j.vector.SparseVector;

public class MatrixDistanceComputation {

    // input
    private final CRSMatrix inputMatrix;

    // output
    private final float[][] outputMatrix;
    private float[] norms;

    public MatrixDistanceComputation(CRSMatrix inputMatrix) {
        this.inputMatrix = inputMatrix;
        this.outputMatrix = new float[inputMatrix.rows()][inputMatrix.rows()];
        initializeNorms();
    }

    private void initializeNorms() {
        // create norms holder        
        norms = new float[inputMatrix.rows()];

        // iterate all input rows calculating norm
        for (int rowIndex = 0; rowIndex < inputMatrix.rows(); rowIndex++) {
            Vector currentRow = inputMatrix.getRow(rowIndex);
            norms[rowIndex] = (float) currentRow.euclideanNorm();
        }
    }

    public float[][] compute() {
        // generate indexes
        List<Integer> rowIndexes = IntStream.range(0, inputMatrix.rows()).boxed().collect(Collectors.toList());

        // iterate indexes calculating
        AtomicInteger counter = new AtomicInteger(0);
        rowIndexes.parallelStream().forEach(rowIndex -> {
            calculateAllDistancesForRow(rowIndex);
            int c = counter.incrementAndGet();
            if (c % 500 == 0) {
                System.out.println(c);
            }
        });
        return outputMatrix;
    }

    private void calculateAllDistancesForRow(int rowIndex) {
        Vector baseRow = inputMatrix.getRow(rowIndex);
        for (int otherRowIndex = rowIndex + 1; otherRowIndex < inputMatrix.rows(); otherRowIndex++) {
            // calculate
            float distance = cosineDistance(rowIndex, baseRow, otherRowIndex, inputMatrix.getRow(otherRowIndex));
            outputMatrix[rowIndex][otherRowIndex] = distance;
            outputMatrix[otherRowIndex][rowIndex] = distance;
        }
    }

    private float cosineDistance(int p1Index, Vector p1, int p2Index, Vector p2) {
        double dotProduct = p1.innerProduct(p2);
        return (float) (dotProduct / (norms[p1Index] * norms[p2Index]));
    }

    private float euclideanDistance(int p1Index, Vector p1, int p2Index, Vector p2) {
        Vector diff = p1.subtract(p2);
        float sum = 0;
        VectorIterator it = ((SparseVector) diff).nonZeroIterator();
        while (it.hasNext()) {
            double element = it.next();
            sum += element * element;
        }
        return (float) Math.sqrt(sum);
    }
}
