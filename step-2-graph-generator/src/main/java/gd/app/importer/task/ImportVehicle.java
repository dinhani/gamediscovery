package gd.app.importer.task;

import gd.app.importer.model.Node;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.plot.Vehicle;
import gd.infrastructure.steriotype.GDTask;
import java.util.Collection;

@GDTask
public class ImportVehicle extends ImportTask {

    // =========================================================================
    // NOT MOTORIZED
    // =========================================================================
    public static final Node BIKE = Node.withPredefinedName(Vehicle.class, "Bike", "bike", "bmx", "bicycle");
    public static final Node SKATE = Node.withPredefinedName(Vehicle.class, "Skate", "skate", "skateboard");

    // =========================================================================
    // MOTORIZED
    // =========================================================================
    public static final Node BOAT = Node.withPredefinedName(Vehicle.class, "Boat");
    public static final Node BUS = Node.withPredefinedName(Vehicle.class, "Bus");
    public static final Node CAR = Node.withPredefinedName(Vehicle.class, "Car").withMoreOcurrences();
    public static final Node HELICOPTER = Node.withPredefinedName(Vehicle.class, "Helicopter");
    public static final Node HOVERCRAFT = Node.withPredefinedName(Vehicle.class, "Hovercraft");
    public static final Node MOTORCYCLE = Node.withPredefinedName(Vehicle.class, "Motorcycle");
    public static final Node PLANE = Node.withPredefinedName(Vehicle.class, "Plane", "plane", "aircraft").withMoreOcurrences();
    public static final Node ROCKET = Node.withPredefinedName(Vehicle.class, "Rocket");
    public static final Node SHIP = Node.withPredefinedName(Vehicle.class, "Ship");
    public static final Node STARSHIP = Node.withPredefinedName(Vehicle.class, "Starship", "starship", "star-ship");
    public static final Node TRAIN = Node.withPredefinedName(Vehicle.class, "Train");
    public static final Node TRUCK = Node.withPredefinedName(Vehicle.class, "Truck").withMoreOcurrences();

    // =========================================================================
    // WAR
    // =========================================================================
    public static final Node SUBMARINE = Node.withPredefinedName(Vehicle.class, "Submarine");
    public static final Node TANK = Node.withPredefinedName(Vehicle.class, "Tank").withMoreOcurrences();

    // =========================================================================
    // MANUFACTURES
    // =========================================================================
    public static final Node ACURA = Node.withPredefinedName(Vehicle.class, "Acura");
    public static final Node ALFA_ROMEO = Node.withPredefinedName(Vehicle.class, "Alfa Romeo");
    public static final Node ASTON_MARTIN = Node.withPredefinedName(Vehicle.class, "Aston Martin");
    public static final Node AUDI = Node.withPredefinedName(Vehicle.class, "Audi");
    public static final Node BMW = Node.withPredefinedName(Vehicle.class, "BMW");
    public static final Node BUICK = Node.withPredefinedName(Vehicle.class, "Buick");
    public static final Node CHEVROLET = Node.withPredefinedName(Vehicle.class, "Chevrolet");
    public static final Node CITROEN = Node.withPredefinedName(Vehicle.class, "Citroën");
    public static final Node FERRARI = Node.withPredefinedName(Vehicle.class, "Ferrari");
    public static final Node FIAT = Node.withPredefinedName(Vehicle.class, "FIAT");
    public static final Node FORD = Node.withPredefinedName(Vehicle.class, "Ford");
    public static final Node HONDA = Node.withPredefinedName(Vehicle.class, "Honda");
    public static final Node HYUNDAI = Node.withPredefinedName(Vehicle.class, "Hyundai");
    public static final Node JAGUAR = Node.withPredefinedName(Vehicle.class, "Jaguar");
    public static final Node JEEP = Node.withPredefinedName(Vehicle.class, "Jeep");
    public static final Node LAMBORGHINI = Node.withPredefinedName(Vehicle.class, "Lamborghini");
    public static final Node LEXUS = Node.withPredefinedName(Vehicle.class, "Lexus");
    public static final Node NISSAN = Node.withPredefinedName(Vehicle.class, "Nissan");
    public static final Node MAZDA = Node.withPredefinedName(Vehicle.class, "Mazda");
    public static final Node MERCEDES_BENZ = Node.withPredefinedName(Vehicle.class, "Mercedes-Benz");
    public static final Node MITSUBISHI = Node.withPredefinedName(Vehicle.class, "Mitsubishi");
    public static final Node PEUGEOT = Node.withPredefinedName(Vehicle.class, "Peugeot");
    public static final Node PORSCHE = Node.withPredefinedName(Vehicle.class, "Porsche");
    public static final Node PLYMOUTH = Node.withPredefinedName(Vehicle.class, "Plymouth");
    public static final Node RENAULT = Node.withPredefinedName(Vehicle.class, "Renault");
    public static final Node SUBARU = Node.withPredefinedName(Vehicle.class, "Subaru");
    public static final Node SHELBY = Node.withPredefinedName(Vehicle.class, "Shelby");
    public static final Node SUZUKI = Node.withPredefinedName(Vehicle.class, "Suzuki");
    public static final Node TOYOTA = Node.withPredefinedName(Vehicle.class, "Toyota");
    public static final Node TVR = Node.withPredefinedName(Vehicle.class, "TVR");
    public static final Node VOLKSWAGEN = Node.withPredefinedName(Vehicle.class, "Volkswagen");
    public static final Node VOLVO = Node.withPredefinedName(Vehicle.class, "Volvo");

    // =========================================================================
    // JETS
    // =========================================================================
    public static final Node F15 = Node.withPredefinedName(Vehicle.class, "F-15");
    public static final Node F16 = Node.withPredefinedName(Vehicle.class, "F-16");
    public static final Node F22 = Node.withPredefinedName(Vehicle.class, "F-22");
    public static final Node F35 = Node.withPredefinedName(Vehicle.class, "F-35");
    public static final Node GRIPEN = Node.withPredefinedName(Vehicle.class, "Gripen");
    public static final Node MIG31 = Node.withPredefinedName(Vehicle.class, "MIG-31");

    @Override
    public Class<? extends ConceptEntry> getTargetClass() {
        return Vehicle.class;
    }

    @Override
    public Collection<Node> getNodesToCreate() {
        return getPredefinedNodes();
    }

}
