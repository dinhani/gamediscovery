package gd.app.graphgenerator;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Maps;
import gd.domain.entities.Node;
import gd.domain.services.CSV;
import gd.domain.services.Cache;
import gd.domain.services.Spring;
import gd.app.graphgenerator.task.ImportTask;
import gd.domain.entities.entity.ConceptEntry;
import gd.infrastructure.di.DIService;
import gd.infrastructure.uid.UID;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {

    // =========================================================================
    // DATA
    // =========================================================================
    private final Map<ImportTask, Collection<Node>> nodes = Maps.newHashMap();

    // =========================================================================
    // SERVICES
    // =========================================================================
    private final CSV csv;
    private final Cache cache;
    private final UID uid;

    // =========================================================================
    // TASKS
    // =========================================================================
    private final Collection<ImportTask> importTasks;

    // =========================================================================
    // INITIALIZATION
    // =========================================================================
    public static void main(String[] args) throws Exception {
        Main importer = new Main();
        importer.run();
    }

    public Main() {
        Spring.initSpringContext();
        csv = DIService.getBean(CSV.class);
        cache = DIService.getBean(Cache.class);
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

    // NODES
    private void generateNodesCSV() {
        // generate data
        System.out.println("==================================================");
        System.out.println("GETTING NODES TO CREATE");
        System.out.println("==================================================");

        importTasks.parallelStream().forEach(task -> {
            // log start
            Stopwatch watch = Stopwatch.createStarted();

            // get nodes
            Collection<Node> nodesToCreate = task.getNodesToCreate();

            // generate uid and add to cache
            for (Node node : nodesToCreate) {
                node.setGeneratedId(uid.generateUid(task.getTargetClass(), node.getId()));
                node.setType(task.getTargetClass());
                cache.add(node);
            }
            // save csv
            try {
                csv.createNodesFile(task.getTargetClass(), nodesToCreate);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // store for next iteration
            nodes.put(task, nodesToCreate);

            // log end
            System.out.println("Nodes: " + task.getClass() + " = " + watch.elapsed(TimeUnit.SECONDS));
        });
    }

    // RELATIONSHIPS
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
                csv.createRelationshipsFile(importTask.getTargetClass(), relationships);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // log end
            System.out.println("Relationships: " + importTask.getClass() + " = " + watch.elapsed(TimeUnit.SECONDS));
        }
    }

}
