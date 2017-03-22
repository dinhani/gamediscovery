package gd.app.importer.task;

import gd.app.importer.model.Node;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.plot.Period;
import gd.infrastructure.steriotype.GDTask;
import java.util.Calendar;
import java.util.Collection;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.apache.commons.lang3.math.NumberUtils;

@GDTask
public class ImportPeriod extends ImportTask {

    private static final String PERIODS = "video_games_set_in_(the_)?\\d+.*";

    // =========================================================================
    // DATA
    // =========================================================================
    public static final Node FUTURE = Node.withPredefinedName(Period.class, "Future").noKeywords();
    public static final Node PAST = Node.withPredefinedName(Period.class, "Past").noKeywords();
    public static final Node PREHISTORY = Node.withPredefinedName(Period.class, "Prehistory", "prehistory", "pre-history", "prehistoric");

    // =========================================================================
    // DATA (SEASON)
    // =========================================================================
    public static final Node AUTUMN = Node.withPredefinedName(Period.class, "Autumn");
    public static final Node SPRING = Node.withPredefinedName(Period.class, "Spring");
    public static final Node SUMMER = Node.withPredefinedName(Period.class, "Summer").checkName();
    public static final Node WINTER = Node.withPredefinedName(Period.class, "Winter").checkName();

    // =========================================================================
    // DATA (YEAR)
    // =========================================================================
    public static final Node CHRISTMAS = Node.withPredefinedName(Period.class, "Christmas").checkName();

    // SERVICES
    private final ScriptEngine js;
    private final int currentYear = Calendar.getInstance().get(Calendar.YEAR);

    public ImportPeriod() {
        js = new ScriptEngineManager().getEngineByName("nashorn");
    }

    // =========================================================================
    // INTERFACE
    // =========================================================================
    @Override
    public Class<? extends ConceptEntry> getTargetClass() {
        return Period.class;
    }

    @Override
    public Collection<Node> getNodesToCreate() {
        // dbpedia
        Collection<Node> periods = jdbc.query()
                .regex(PERIODS)
                .findDBPediaCategories();

        // predefined
        periods.addAll(getPredefinedNodes());
        for (int year = 0; year < 5000; year++) {
            periods.add(Node.withPredefinedIdAndName(Period.class, year + "", year + ""));
        }

        // fix
        fixIds(periods, "video_games_set_in_");

        return periods;
    }

    @Override
    public ConceptEntry getRelationshipsToCreate(Node node) {
        Period period = new Period();
        period.setUid(node.getGeneratedId());

        setPastOrFuture(node, period);
        setCentury(node, period);

        return period;
    }

    private void setCentury(Node node, Period period) {
        // if not year, do not set
        if (!NumberUtils.isNumber(node.getName())) {
            return;
        }

        // calculate century
        int year = NumberUtils.toInt(node.getName());
        int century = (year / 100) + 1;
        String ordinal = "th";
        if ((century + "").endsWith("1")) {
            ordinal = "st";
        } else if ((century + "").endsWith("2")) {
            ordinal = "st";
        } else if ((century + "").endsWith("3")) {
            ordinal = "st";
        }

        // generate century id and add it
        String centuryUid = String.format("%s%s_century", century, ordinal);
        period.getParentPeriods().addAll(cache.findEntriesById(Period.class, centuryUid));
    }

    // =========================================================================
    // JS EXECUTION TO DETERMINE PAST OF FUTURE
    // =========================================================================
    private void setPastOrFuture(Node node, Period period) {
        try {
            js.put("name", node.getName());
            js.eval(""
                    + "var matcher = /(\\d+)/;"
                    + "var matched = matcher.exec(name)[0];");

            String matchedPeriod = (String) js.get("matched");
            if (matchedPeriod.length() == 2) {
                setPastOrFutureForCentury(matchedPeriod, period);
            } else if (matchedPeriod.length() == 4) {
                setPastOrFutureForYear(matchedPeriod, period);
            }
        } catch (ScriptException e) {
        }
    }

    private void setPastOrFutureForCentury(String matchedPeriod, Period period) {
        int century = NumberUtils.toInt(matchedPeriod);
        if (century <= 20) {
            period.getParentPeriods().add((Period) PAST.getConceptEntry());
        } else if (century > 21) {
            period.getParentPeriods().add((Period) FUTURE.getConceptEntry());
        }
    }

    private void setPastOrFutureForYear(String matchedPeriod, Period period) {
        int year = NumberUtils.toInt(matchedPeriod);
        if (year < currentYear) {
            period.getParentPeriods().add((Period) PAST.getConceptEntry());
        } else if (year >= currentYear) {
            period.getParentPeriods().add((Period) FUTURE.getConceptEntry());
        }
    }

}
