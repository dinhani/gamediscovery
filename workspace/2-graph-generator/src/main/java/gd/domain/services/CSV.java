package gd.domain.services;

import com.google.common.collect.Maps;
import com.opencsv.CSVWriter;
import gd.domain.entities.Node;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.RelationshipInfo;
import gd.infrastructure.enviroment.Environment;
import gd.infrastructure.log.LogProducer;
import gd.infrastructure.steriotype.GDService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

@GDService
public class CSV {

    // ENV VARS    
    private final String PARSED_DATA_DIRECTORY = "GD_DATA_PARSED";

    // DATA
    private String nodesFolder;
    private String relationshipsFolder;

    // SERVICES
    @Autowired
    private Environment environment;

    private static final Logger LOGGER = LogProducer.getLogger(CSV.class);

    // =========================================================================
    // INITIALIZATION
    // =========================================================================
    @PostConstruct
    public void init() {
        this.nodesFolder = environment.readValue(PARSED_DATA_DIRECTORY) + "/neo4j/nodes/";
        new File(nodesFolder).mkdirs();

        this.relationshipsFolder = environment.readValue(PARSED_DATA_DIRECTORY) + "/neo4j/relationships/";
        new File(relationshipsFolder).mkdirs();
    }

    // =========================================================================
    // CREATE
    // =========================================================================    
    public void createNodesFile(Class<? extends ConceptEntry> type, Collection<Node> nodes) throws IOException {
        LOGGER.debug("Creating CSV {} nodes | size={}", type.getSimpleName(), nodes.size());

        try (CSVWriter csv = new CSVWriter(new OutputStreamWriter(new FileOutputStream(nodesFolder + type.getSimpleName()), "UTF-8"))) {
            // header
            csv.writeNext(new String[]{"id", "name", "alias", "image", "video", "summary"});

            // write values
            for (Node node : nodes) {
                csv.writeNext(new String[]{
                    node.getGeneratedId(),
                    node.getName(),
                    node.getAlias(),
                    node.getImage(),
                    node.getVideo(),
                    node.getSummary()
                });
            }
        }
    }

    public void createRelationshipsFile(Class<? extends ConceptEntry> type, Collection<ConceptEntry> sourceEntries) throws IOException {
        LOGGER.debug("Creating CSV {} relationships | size={}", type.getSimpleName(), sourceEntries.size());

        // one writer for each file is opened
        Map<String, CSVWriter> writers = Maps.newConcurrentMap();

        sourceEntries.stream().forEach(entry -> {
            entry.getRelatedEntriesAndRelationships().stream().forEach(relatedEntry -> {
                // get relationship to write
                ConceptEntry sourceToSave = entry;
                RelationshipInfo relationship = relatedEntry.getRelationship();
                ConceptEntry targetToSave = relatedEntry.getTargetEntry();

                // create writer if necessary
                String writerKey = String.format("%s-%s-%s-%s", entry.getClass().getSimpleName(), targetToSave.getClass().getSimpleName(), relationship.getDirection(), relationship.getType());
                if (!writers.containsKey(writerKey)) {
                    try {
                        CSVWriter writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(relationshipsFolder + writerKey), "UTF-8"));
                        writer.writeNext(new String[]{"source", "target", "weight", "type"});
                        writers.put(writerKey, writer);
                    } catch (IOException ex) {
                    }
                }
                CSVWriter writer = writers.get(writerKey);

                // relationships should be saved following the pattern (origin)-[outgoing]->(target)
                // swap nodes in relationship if is incoming                
                if (relationship.getDirection().equals("INCOMING")) {
                    sourceToSave = targetToSave;
                    targetToSave = entry;
                }

                // write line
                writer.writeNext(new String[]{
                    sourceToSave.getUid(),
                    targetToSave.getUid(),
                    relatedEntry.getRelevance() + "",
                    relationship.getType()
                });
            });
        });

        // close all writers
        for (CSVWriter writer : writers.values()) {
            writer.close();
        }
    }

    // =========================================================================
    // RETRIEVE
    // =========================================================================
    public Collection<File> getAllNodesFiles() {
        return FileUtils.listFiles(new File(nodesFolder), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
    }

    public Collection<File> getAllRelationshipsFiles() {
        return FileUtils.listFiles(new File(relationshipsFolder), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
    }

}
