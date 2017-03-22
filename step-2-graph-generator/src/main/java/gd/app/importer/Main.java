package gd.app.importer;

import gd.app.importer.step.*;
import java.io.IOException;
import java.sql.SQLException;

public class DataImporter extends AbstractStep {

    // =========================================================================
    // INITIALIZATION
    // =========================================================================
    public static void main(String[] args) throws Exception {
        DataImporter importer = new DataImporter();
        importer.run();
    }

    public DataImporter() {
        initSpringContext();
    }

    // =========================================================================
    // EXECUTION
    // =========================================================================
    public void run() throws SQLException, IOException {
        new Step1DataParser().run();        
    }

}
