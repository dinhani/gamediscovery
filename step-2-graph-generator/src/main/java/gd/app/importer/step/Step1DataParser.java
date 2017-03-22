package gd.app.importer.step;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Maps;
import gd.app.importer.model.Node;
import gd.app.importer.repository.DataImporterCSV;
import gd.app.importer.repository.DataImporterCache;
import gd.app.importer.task.ImportTask;
import gd.domain.entities.entity.ConceptEntry;
import gd.infrastructure.di.DIService;
import gd.infrastructure.uid.UID;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.context.ApplicationContext;

public class Step1DataParser extends AbstractStep {

    private final ApplicationContext ctx;

    // SERVICES
    private final DataImporterCSV csv;
    private final DataImporterCache cache;
    private final UID uid;

    // TASKS
    private final Collection<ImportTask> importTasks;

    // DATA
    private final Map<ImportTask, Collection<Node>> nodes = Maps.newHashMap();

    // =========================================================================
    // INITIALIZATION
    // =========================================================================
    public static void main(String[] args) throws Exception {
        Step1DataParser importer = new Step1DataParser();
        importer.run();
    }

    public Step1DataParser() {
        ctx = initSpringContext();
        csv = DIService.getBean(DataImporterCSV.class);
        cache = DIService.getBean(DataImporterCache.class);
        uid = DIService.getBean(UID.class);

        importTasks = DIService.getBeansOfType(ImportTask.class);
    }

    // =========================================================================
    // EXECUTION
    // =========================================================================
    public void run() throws SQLException, IOException {
        // prepare
        nodes.clear();

        // generate CSVs
        generateNodesCSV();
        generateRelationshipsCSV();
    }

    private void generateNodesCSV() {
        // generate data
        System.out.println("==================================================");
        System.out.println("GETTING NODES TO CREATE");
        System.out.println("==================================================");

        importTasks.parallelStream().forEach(importTask -> {
            // log start
            Stopwatch watch = Stopwatch.createStarted();

            // get nodes
            Collection<Node> nodesToCreate = importTask.getNodesToCreate();

            // generate uid and add to cache
            for (Node node : nodesToCreate) {
                node.setGeneratedId(uid.generateUid(importTask.getTargetClass(), node.getId()));
                cache.addEntry(importTask.getTargetClass(), node);
            }
            // save csv
            try {
                csv.createNodes(importTask.getTargetClass(), nodesToCreate);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // store for next iteration
            nodes.put(importTask, nodesToCreate);

            // log end            
            System.out.println("Nodes: " + importTask.getClass() + " = " + watch.elapsed(TimeUnit.SECONDS));
        });
    }

    private void generateRelationshipsCSV() {
        // generate relatinships
        System.out.println("=======================================================");
        System.out.println("CREATING RELATIONSHIPS");
        System.out.println("=======================================================");
        for (ImportTask importTask : importTasks) {

            // log start
            Stopwatch watch = Stopwatch.createStarted();

            // parse relationships
            Collection<Node> nodesWithRelationships = nodes.get(importTask);
            // it can be null when testing a single ImportTask
            if (nodesWithRelationships == null) {
                continue;
            }

            // execute
            Collection<ConceptEntry> relationships = nodesWithRelationships.parallelStream()
                    .map(node -> {
                        return importTask.getRelationshipsToCreate(node);
                    })
                    .filter(entry -> entry != null)
                    .collect(Collectors.toList());

            // save csv
            try {
                csv.createRelationships(importTask.getTargetClass(), relationships);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // log end            
            System.out.println("Relationships: " + importTask.getClass() + " = " + watch.elapsed(TimeUnit.SECONDS));
        }
    }

}
