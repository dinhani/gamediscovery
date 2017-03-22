package gd.app.importer.task;

import gd.app.importer.model.Node;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.gameplay.Mechanics;
import gd.infrastructure.steriotype.GDTask;
import java.util.Collection;

@GDTask
public class ImportMechanics extends ImportTask {

    public static final Node BACKTRACKING = Node.withPredefinedName(Mechanics.class, "Backtracking");
    public static final Node BOSS = Node.withPredefinedName(Mechanics.class, "Boss");
    public static final Node BULLET_TIME = Node.withPredefinedName(Mechanics.class, "Bullet Time").boostRelevance();
    public static final Node CHARACTER_CUSTOMIZATION = Node.withPredefinedName(Mechanics.class, "Character Customization");
    public static final Node CAR_CHASE = Node.withPredefinedName(Mechanics.class, "Car Chase", "car chase", "perseguição").boostRelevance();
    public static final Node CHAMPIONSHIP = Node.withPredefinedName(Mechanics.class, "Championship").checkName();
    public static final Node CHECKPOINT = Node.withPredefinedName(Mechanics.class, "Checkpoint");
    public static final Node CLIMBING = Node.withPredefinedName(Mechanics.class, "Climbing").boostRelevance();
    public static final Node COLLECTIBLES = Node.withPredefinedName(Mechanics.class, "Collectibles");
    public static final Node COMBO = Node.withPredefinedName(Mechanics.class, "Combo").boostRelevance();
    public static final Node COMPANION = Node.withPredefinedName(Mechanics.class, "Companion").boostRelevance();
    public static final Node COSTUME = Node.withPredefinedName(Mechanics.class, "Costume");
    public static final Node COVER = Node.withPredefinedName(Mechanics.class, "Cover System").boostRelevance();
    public static final Node CRAFTING = Node.withPredefinedName(Mechanics.class, "Crafting");
    public static final Node CRITICAL_HIT = Node.withPredefinedName(Mechanics.class, "Critical Hit");
    public static final Node CUSTOMIZATION = Node.withPredefinedName(Mechanics.class, "Customization");
    public static final Node DESTRUCTIBLE_ENVIRONMENT = Node.withPredefinedName(Mechanics.class, "Destructible Environment").boostRelevance();
    public static final Node DIALOG_TREE = Node.withPredefinedName(Mechanics.class, "Dialog Tree", "dialog tree", "dialogue tree", "conversation tree");
    public static final Node DODGING = Node.withPredefinedName(Mechanics.class, "Dodge / Evage", "dodging", "dodge", "evade");
    public static final Node DRIFT = Node.withPredefinedName(Mechanics.class, "Drift");
    public static final Node DRIVING = Node.withPredefinedName(Mechanics.class, "Driving", "driving", "drive").boostRelevance();
    public static final Node EXPLORATION = Node.withPredefinedName(Mechanics.class, "Exploration").boostRelevance();
    public static final Node EXPLOSION = Node.withPredefinedName(Mechanics.class, "Explosion");
    public static final Node GRINDING = Node.withPredefinedName(Mechanics.class, "Grinding", "grinding", "grind");
    public static final Node FAST_TRAVEL = Node.withPredefinedName(Mechanics.class, "Fast Travel");
    public static final Node FATALITY = Node.withPredefinedName(Mechanics.class, "Fatality");
    public static final Node FIRE = Node.withPredefinedName(Mechanics.class, "Fire");
    public static final Node FOG_OF_WAR = Node.withPredefinedName(Mechanics.class, "Fog of War", "fog war");
    public static final Node FREE_ROAM = Node.withPredefinedName(Mechanics.class, "Free Roam");
    public static final Node HP = Node.withPredefinedName(Mechanics.class, "HP");
    public static final Node JUMP = Node.withPredefinedName(Mechanics.class, "Jump");
    public static final Node LEVELLING = Node.withPredefinedName(Mechanics.class, "Levelling", "levelling", "level up", "grinding");
    public static final Node LOOTING = Node.withPredefinedName(Mechanics.class, "Looting", "looting", "loot").boostRelevance();
    public static final Node LUCK = Node.withPredefinedName(Mechanics.class, "Luck");
    public static final Node MICROMANAGEMENT = Node.withPredefinedName(Mechanics.class, "Micromanagement");
    public static final Node MAGIC = Node.withPredefinedName(Mechanics.class, "Magic");
    public static final Node MEELE = Node.withPredefinedName(Mechanics.class, "Meele");
    public static final Node MINI_MAP = Node.withPredefinedName(Mechanics.class, "Minimap", "mini-map", "minimap", "mini map");
    public static final Node MP = Node.withPredefinedName(Mechanics.class, "MP");
    public static final Node MULTIPLE_ENDING = Node.withPredefinedName(Mechanics.class, "Multiple Endings", "multiple ending");
    public static final Node NEW_GAME_PLUS = Node.withPredefinedName(Mechanics.class, "New Game Plus", "game plus");
    public static final Node NITRO = Node.withPredefinedName(Mechanics.class, "Nitro");
    public static final Node PHYSICS = Node.withPredefinedName(Mechanics.class, "Physics").boostRelevance();
    public static final Node PORTAL = Node.withPredefinedName(Mechanics.class, "Portal");
    public static final Node POTION = Node.withPredefinedName(Mechanics.class, "Potion");
    public static final Node QUEST = Node.withPredefinedName(Mechanics.class, "Quest");
    public static final Node QUICK_SAVE = Node.withPredefinedName(Mechanics.class, "Quick Save", "quick save", "quick-save");
    public static final Node QUICK_TRAVEL = Node.withPredefinedName(Mechanics.class, "Quick Travel");
    public static final Node QTE = Node.withPredefinedName(Mechanics.class, "Quick Time Event", "qte", "quick-time event").boostRelevance();
    public static final Node RADAR = Node.withPredefinedName(Mechanics.class, "Radar");
    public static final Node RESPAWN = Node.withPredefinedName(Mechanics.class, "Respawn");
    public static final Node ROUND = Node.withPredefinedName(Mechanics.class, "Round");
    public static final Node SANDBOX = Node.withPredefinedName(Mechanics.class, "Sandbox").boostRelevance();
    public static final Node SIDE_MISSIONS = Node.withPredefinedName(Mechanics.class, "Side Mission /  Side Quest", "side mission", "side quest");
    public static final Node SKILL_TREE = Node.withPredefinedName(Mechanics.class, "Skill Tree");
    public static final Node SQUAD = Node.withPredefinedName(Mechanics.class, "Squad").boostRelevance();
    public static final Node STAGE = Node.withPredefinedName(Mechanics.class, "Stage");
    public static final Node TELEKINESIS = Node.withPredefinedName(Mechanics.class, "Telekinesis");
    public static final Node THEFT = Node.withPredefinedName(Mechanics.class, "Theft", "steal", "theft");
    public static final Node TIME_TRAVEL = Node.withPredefinedName(Mechanics.class, "Time Travel", "time travel", "time control", "rewind time").boostRelevance();
    public static final Node TIMER = Node.withPredefinedName(Mechanics.class, "Timer");
    public static final Node TOURNAMENT = Node.withPredefinedName(Mechanics.class, "Tournament");
    public static final Node TRAP = Node.withPredefinedName(Mechanics.class, "Trap");
    public static final Node TUNING = Node.withPredefinedName(Mechanics.class, "Tuning").boostRelevance();
    public static final Node TURN_BASED = Node.withPredefinedName(Mechanics.class, "Turn-Based").boostRelevance();
    public static final Node VEHICLE_CUSTOMIZATION = Node.withPredefinedName(Mechanics.class, "Vehicle Customization");
    public static final Node WEAPON_CUSTOMIZATION = Node.withPredefinedName(Mechanics.class, "Weapon Customization");
    public static final Node WORLD_MAP = Node.withPredefinedName(Mechanics.class, "World Map", "world map");

    @Override
    public Class<? extends ConceptEntry> getTargetClass() {
        return Mechanics.class;
    }

    @Override
    public Collection<Node> getNodesToCreate() {
        return getPredefinedNodes();
    }

}
