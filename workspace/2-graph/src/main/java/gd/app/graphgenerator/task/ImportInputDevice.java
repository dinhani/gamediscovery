package gd.app.graphgenerator.task;

import gd.domain.entities.Node;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.gameplay.InputDevice;
import gd.infrastructure.steriotype.GDTask;
import java.util.Collection;
import java.util.Set;

@GDTask
public class ImportInputDevice extends ImportTask {

    public static final Node EYETOY = Node.withPredefinedName(InputDevice.class, "EyeToy");
    public static final Node KINECT = Node.withPredefinedName(InputDevice.class, "Kinect");
    public static final Node KEYBOARD = Node.withPredefinedName(InputDevice.class, "Keyboard");
    public static final Node MICROPHONE = Node.withPredefinedName(InputDevice.class, "Microphone");
    public static final Node MOUSE = Node.withPredefinedName(InputDevice.class, "Mouse");

    private static final String GAME_CONTROLLER = "Q865422";
    private static final String INPUT_DEVICE = "Q864114";
    private static final String COMPUTER_HARDWARE = "Q3966";
    private static final String POINTING_DEVICE = "Q184824";

    @Override
    public Class<? extends ConceptEntry> getTargetClass() {
        return InputDevice.class;
    }

    @Override
    public Collection<Node> getNodesToCreate() {
        // wikidata
        Set<Node> gameControllers = jdbc.query()
                .attributesKeys(INSTANCE_OR_SUBCLASS)
                .attributesValues(GAME_CONTROLLER, INPUT_DEVICE, COMPUTER_HARDWARE, POINTING_DEVICE)
                .findWikidataEntities();

        // predefined
        gameControllers.addAll(getPredefinedNodes());

        return gameControllers;
    }

}
