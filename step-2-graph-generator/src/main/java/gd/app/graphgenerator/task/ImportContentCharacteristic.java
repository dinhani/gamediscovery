package gd.app.graphgenerator.task;

import gd.domain.entities.Node;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.industry.ContentCharacteristic;
import gd.infrastructure.steriotype.GDTask;
import java.util.Collection;

@GDTask
public class ImportContentCharacteristic extends ImportTask {

    @Override
    public Class<? extends ConceptEntry> getTargetClass() {
        return ContentCharacteristic.class;
    }

    @Override
    public Collection<Node> getNodesToCreate() {
        Collection<Node> characteristics = jdbc.executeQuery("SELECT name as id, name FROM (SELECT DISTINCT unnest(categories) as name FROM esrb_games) esrb WHERE name <> 'No Descriptors' ORDER BY name");
        return characteristics;
    }
}
