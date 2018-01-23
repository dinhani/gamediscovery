package gd.app.graphgenerator.task;

import com.google.common.collect.Sets;
import gd.domain.entities.Node;
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
        numberOfPlayers.add(Node.withPredefinedIdAndName(getTargetClass(), "1", "1 player"));
        numberOfPlayers.add(Node.withPredefinedIdAndName(getTargetClass(), "2", "2 players"));
        numberOfPlayers.add(Node.withPredefinedIdAndName(getTargetClass(), "3", "3 players"));
        numberOfPlayers.add(Node.withPredefinedIdAndName(getTargetClass(), "4", "4 players"));
        numberOfPlayers.add(Node.withPredefinedIdAndName(getTargetClass(), "5", "5 players"));
        numberOfPlayers.add(Node.withPredefinedIdAndName(getTargetClass(), "6", "6 players"));
        numberOfPlayers.add(Node.withPredefinedIdAndName(getTargetClass(), "7", "7 players"));
        numberOfPlayers.add(Node.withPredefinedIdAndName(getTargetClass(), "8", "8 players"));
        numberOfPlayers.add(Node.withPredefinedIdAndName(getTargetClass(), "9", "9 players"));
        numberOfPlayers.add(Node.withPredefinedIdAndName(getTargetClass(), "10", "10 players"));
        numberOfPlayers.add(Node.withPredefinedIdAndName(getTargetClass(), "11", "11 players"));
        numberOfPlayers.add(Node.withPredefinedIdAndName(getTargetClass(), "12", "12 players"));
        numberOfPlayers.add(Node.withPredefinedIdAndName(getTargetClass(), "16", "16 players"));
        numberOfPlayers.add(Node.withPredefinedIdAndName(getTargetClass(), "24", "24 players"));
        numberOfPlayers.add(Node.withPredefinedIdAndName(getTargetClass(), "30", "30 players"));
        numberOfPlayers.add(Node.withPredefinedIdAndName(getTargetClass(), "32", "32 players"));
        numberOfPlayers.add(Node.withPredefinedIdAndName(getTargetClass(), "64", "64 players"));
        numberOfPlayers.add(Node.withPredefinedIdAndName(getTargetClass(), "128", "128 players"));

        return numberOfPlayers;
    }

}
