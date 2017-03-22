package gd.app.importer.task;

import gd.app.importer.model.Node;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.plot.Organization;
import gd.infrastructure.steriotype.GDTask;
import java.util.Collection;

@GDTask
public class ImportOrganization extends ImportTask {

    // =========================================================================
    // POLICE
    // =========================================================================
    public static final Node CIA = Node.withPredefinedName(Organization.class, "CIA");
    public static final Node DELTA_FORCE = Node.withPredefinedName(Organization.class, "Delta Force");
    public static final Node FBI = Node.withPredefinedName(Organization.class, "FBI");
    public static final Node LAPD = Node.withPredefinedName(Organization.class, "LAPD");
    public static final Node KGB = Node.withPredefinedName(Organization.class, "KGB");
    public static final Node MIAMI_VICE = Node.withPredefinedName(Organization.class, "Miami Vice");
    public static final Node NSA = Node.withPredefinedName(Organization.class, "NSA");
    public static final Node MI6 = Node.withPredefinedName(Organization.class, "MI6");
    public static final Node NOPD = Node.withPredefinedName(Organization.class, "NOPD");
    public static final Node NYPD = Node.withPredefinedName(Organization.class, "NYPD");
    public static final Node SFPD = Node.withPredefinedName(Organization.class, "SFPD");
    public static final Node SWAT = Node.withPredefinedName(Organization.class, "SWAT");
    public static final Node SCOTLAND_YARD = Node.withPredefinedName(Organization.class, "Scotland Yard");
    public static final Node TEXAS_RANGERS = Node.withPredefinedName(Organization.class, "Texas Rangers");
    public static final Node US_ARMY = Node.withPredefinedName(Organization.class, "US Army", "american army", "us army");

    // =========================================================================
    // INTERNATIONAL
    // =========================================================================
    public static final Node NATO = Node.withPredefinedName(Organization.class, "NATO");
    public static final Node UN = Node.withPredefinedName(Organization.class, "United Nations");

    @Override
    public Class<? extends ConceptEntry> getTargetClass() {
        return Organization.class;
    }

    @Override
    public Collection<Node> getNodesToCreate() {
        return getPredefinedNodes();
    }

}
