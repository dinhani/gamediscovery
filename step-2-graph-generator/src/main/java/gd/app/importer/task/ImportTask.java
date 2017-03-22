package gd.app.importer.task;

import com.google.common.collect.Maps;
import gd.app.importer.model.Node;
import gd.app.importer.repository.DataImporterCache;
import gd.app.importer.repository.DataImporterJDBC;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.RelationshipRelevanceBoost;
import gd.domain.entities.entity.TreeSetWithAttributes;
import gd.infrastructure.di.DIService;
import gd.infrastructure.steriotype.GDTask;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javassist.Modifier;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@GDTask
public abstract class ImportTask {

    // =========================================================================
    // COMMON PROPERTIES
    // =========================================================================
    protected static final String FREEBASE = "P646";

    protected static final String INSTANCE_OF = "P31";
    protected static final String SUBCLASS_OF = "P279";
    protected static final String[] INSTANCE_OR_SUBCLASS = new String[]{INSTANCE_OF, SUBCLASS_OF};

    @Autowired
    private DIService di;

    // =========================================================================
    // DATA
    // =========================================================================
    // tasks instances to be accessed when a task class is passed as parameter
    private final Map<Class<? extends ImportTask>, ImportTask> tasks = Maps.newHashMap();

    // =========================================================================
    // SERVICES    
    // =========================================================================
    @Autowired
    protected DataImporterCache cache;

    @Autowired
    protected DataImporterJDBC jdbc;

    // =========================================================================
    // INITIALIZATION
    // =========================================================================
    @PostConstruct
    public void init() {
        Collection<ImportTask> allTasks = DIService.getBeansOfType(ImportTask.class);

        // get a reference to all tasks instances to access later
        for (ImportTask task : allTasks) {
            tasks.put(task.getClass(), task);
        }
    }

    // =========================================================================
    // PREDEFINED NODES
    // =========================================================================
    protected Collection<Node> getPredefinedNodes() {

        // get fields
        List<Field> staticFields = Arrays.stream(FieldUtils.getAllFields(this.getClass()))
                .filter(field -> Modifier.isStatic(field.getModifiers()))
                .filter(field -> Modifier.isPublic(field.getModifiers()))
                .filter(field -> field.getType() == Node.class
                )
                .collect(Collectors.toList());

        // read values
        return staticFields.stream()
                .map(field -> {
                    try {
                        Node node = (Node) FieldUtils.readField(field, this, true);
                        return node;
                    } catch (IllegalAccessException ex) {
                        return null;
                    }
                })
                .filter(value -> value != null)
                .map(value -> value)
                .collect(Collectors.toList());
    }

    protected <T extends ConceptEntry> void importNodes(
            Collection<T> collectionToAddNodes,
            Class<? extends ImportTask> tasksToGetNodes,
            Node nodeToCheckWords) {

        // get the task to get target nodes
        ImportTask task = tasks.get(tasksToGetNodes);

        // iterate all predefined nodes        
        for (Node targetNode : task.getPredefinedNodes()) {
            // check text for keywords
            double relevanceModifier = nodeToCheckWords.relevanceForWords(targetNode.getKeywords());
            boolean containsWords = relevanceModifier > 0;
            if (containsWords) {
                collectionToAddNodes.add((T) targetNode.getConceptEntry());
            }

            // check name
            boolean containsName = false;
            if (!containsWords && targetNode.shouldCheckName()) {
                containsName = nodeToCheckWords.getName().contains(targetNode.getName());
                if (containsName) {
                    collectionToAddNodes.add((T) targetNode.getConceptEntry());
                    relevanceModifier = RelationshipRelevanceBoost.HIGH;
                }
            }

            // add modifier if necessary
            if (targetNode.shouldBoostRelevance() && (containsWords || containsName)) {
                ((TreeSetWithAttributes) collectionToAddNodes).setRelevanceModifier(targetNode.getConceptEntry(), relevanceModifier);
            }
        }
    }

    protected <T extends ConceptEntry> void importNodes(
            Collection<T> collectionToAddNodes,
            Node... nodesToAdd) {

        for (Node node : nodesToAdd) {
            // add node
            T entry = (T) node.getConceptEntry();
            collectionToAddNodes.add(entry);

            // check if need boosting
            if (node.shouldBoostRelevance()) {
                ((TreeSetWithAttributes) collectionToAddNodes).setRelevanceModifier(entry, RelationshipRelevanceBoost.HIGH);
            }
        }
    }

    // =========================================================================
    // COLLECTION
    // =========================================================================
    protected boolean contains(Collection<? extends ConceptEntry> collectionToCheck, String... uidsToCheck) {
        for (String uidToCheck : uidsToCheck) {
            boolean contains = collectionToCheck.stream().anyMatch(entry -> entry.getUid().equals(uidToCheck));
            if (contains) {
                return true;
            }
        }
        return false;
    }

    protected boolean containsRegex(Collection<? extends ConceptEntry> collectionToCheck, String regexToCheck) {
        return containsRegexStr(collectionToCheck.stream().map(ConceptEntry::getUid).collect(Collectors.toList()), regexToCheck);
    }

    protected boolean containsRegexStr(Collection<String> collectionToCheck, String regexToCheck) {
        return collectionToCheck.stream().anyMatch(entry -> entry.matches(regexToCheck));
    }

    protected void remove(Collection<? extends ConceptEntry> collectionToRemove, String... uidsToRemove) {
        for (String uidToRemove : uidsToRemove) {
            collectionToRemove.removeIf(entry -> entry.getUid().equals(uidToRemove));
        }
    }

    // =========================================================================
    // NODES
    // =========================================================================
    protected void fixIds(Collection<Node> nodes, String... piecesToRemove) {
        for (Node node : nodes) {
            node.addAlternativeId(node.getId());

            String newId = node.getId();
            for (String pieceToRemove : piecesToRemove) {
                newId = StringUtils.replace(newId, pieceToRemove, "");
            }
            node.setId(newId);
        }
    }

    // =========================================================================
    // INTERFACE
    // =========================================================================
    public abstract Class<? extends ConceptEntry> getTargetClass();

    public abstract Collection<Node> getNodesToCreate();

    public ConceptEntry getRelationshipsToCreate(Node node) {
        return null;
    }
}
