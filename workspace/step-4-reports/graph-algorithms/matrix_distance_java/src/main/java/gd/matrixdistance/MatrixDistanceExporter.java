package gd.matrixdistance;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;

public class MatrixDistanceExporter {

    public void export(float[][] outputMatrix, String path) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            FileChannel channel = fos.getChannel();
            for (float[] row : outputMatrix) {
                // generate indexes
                short[] indices = argsort(row);
                
                // prepare buffer
                ByteBuffer buffer = ByteBuffer.allocate(indices.length * 2);
                buffer.asShortBuffer().put(indices);

                // write buffer
                channel.write(buffer);
            }
            fos.flush();
        }
    }

    // =========================================================================
    // ARGSORT
    // =========================================================================
    private short[] argsort(final float[] row) {
        // create array with indices
        Short[] indexes = new Short[row.length];
        for (short i = 0; i < indexes.length; i++) {
            indexes[i] = i;
        }

        // sort array
        Arrays.sort(indexes, (final Short i1, final Short i2) -> -1 * Float.compare(row[i1], row[i2]));
        return ArrayUtils.toPrimitive(ArrayUtils.subarray(indexes, 0, 64));
    }

}
