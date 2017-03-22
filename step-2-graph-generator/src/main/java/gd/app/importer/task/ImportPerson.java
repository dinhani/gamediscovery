package gd.app.importer.task;

import gd.app.importer.model.Node;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.industry.Company;
import gd.domain.entities.entity.industry.Person;
import gd.infrastructure.steriotype.GDTask;
import java.util.Collection;

@GDTask
public class ImportPerson extends ImportTask {

    private static final String COMPOSER = "Q36834";
    private static final String ENGINEER = "Q81096";
    private static final String GAME_DESIGNER = "Q12811294";
    private static final String GAME_PRODUCER = "Q2702296";
    private static final String VIDEO_GAME = "Q7889";

    private static final String FIELD_OF_WORK = "P101";
    private static final String OCCUPATION = "P106";

    private static final String EMPLOYER = "P108";

    @Override
    public Class<? extends ConceptEntry> getTargetClass() {
        return Person.class;
    }

    @Override
    public Collection<Node> getNodesToCreate() {
        return jdbc.query()
                .attributesKeys(FIELD_OF_WORK, OCCUPATION)
                .attributesValues(COMPOSER, ENGINEER, GAME_DESIGNER, GAME_PRODUCER, VIDEO_GAME)
                .findWikidataEntities();
    }

    @Override
    public ConceptEntry getRelationshipsToCreate(Node node) {
        Person person = new Person();
        person.setUid(node.getGeneratedId());

        person.setCompaniesWorkedFor(cache.findEntriesByWikidataIds(Company.class, node.getWikidataAttributes(EMPLOYER)));

        return person;
    }

}
