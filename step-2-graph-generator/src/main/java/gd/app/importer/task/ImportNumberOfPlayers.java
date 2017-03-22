package gd.app.importer.task;

import com.google.common.collect.Sets;
import gd.app.importer.model.Node;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.gameplay.NumberOfPlayers;
import gd.infrastructure.steriotype.GDTask;
import java.util.Collection;
import java.util.Set;

@GDTask
public class ImportNumberOfPlayers extends ImportTask {

    @Override
    public Class<? extends ConceptEntry> getTargetClass() {
        return NumberOfPlayers.class;
    }

    @Override
    public Collection<Node> getNodesToCreate() {
        Set<Node> numberOfPlayers = Sets.newHashSet();
        numberOfPlayers.add(new Node("1", "1 player"));
        numberOfPlayers.add(new Node("2", "2 players"));
        numberOfPlayers.add(new Node("3", "3 players"));
        numberOfPlayers.add(new Node("4", "4 players"));
        numberOfPlayers.add(new Node("5", "5 players"));
        numberOfPlayers.add(new Node("6", "6 players"));
        numberOfPlayers.add(new Node("7", "7 players"));
        numberOfPlayers.add(new Node("8", "8 players"));
        numberOfPlayers.add(new Node("9", "9 players"));
        numberOfPlayers.add(new Node("10", "10 players"));
        numberOfPlayers.add(new Node("11", "11 players"));
        numberOfPlayers.add(new Node("12", "12 players"));
        numberOfPlayers.add(new Node("16", "16 players"));
        numberOfPlayers.add(new Node("24", "24 players"));
        numberOfPlayers.add(new Node("30", "30 players"));
        numberOfPlayers.add(new Node("32", "32 players"));
        numberOfPlayers.add(new Node("64", "64 players"));
        numberOfPlayers.add(new Node("128", "128 players"));

        return numberOfPlayers;
    }

}
