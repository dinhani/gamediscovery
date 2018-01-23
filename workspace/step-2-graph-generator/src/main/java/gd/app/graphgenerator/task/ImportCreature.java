package gd.app.graphgenerator.task;

import gd.domain.entities.Node;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.plot.Creature;
import gd.infrastructure.steriotype.GDTask;
import java.util.Collection;

@GDTask
public class ImportCreature extends ImportTask {

    // =========================================================================
    // DATA (ANIMALS)
    // =========================================================================
    public static final Node BAT = Node.withPredefinedName(Creature.class, "Bear");
    public static final Node BEAR = Node.withPredefinedName(Creature.class, "Bear");
    public static final Node BEE = Node.withPredefinedName(Creature.class, "Bee");
    public static final Node CAT = Node.withPredefinedName(Creature.class, "Cat");
    public static final Node CHICKEN = Node.withPredefinedName(Creature.class, "Chicken");
    public static final Node CRAB = Node.withPredefinedName(Creature.class, "Crab");
    public static final Node COW = Node.withPredefinedName(Creature.class, "Cow");
    public static final Node CROW = Node.withPredefinedName(Creature.class, "Crow");
    public static final Node DEER = Node.withPredefinedName(Creature.class, "Deer");
    public static final Node DOG = Node.withPredefinedName(Creature.class, "Dog");
    public static final Node DOLPHIN = Node.withPredefinedName(Creature.class, "Dolphin");
    public static final Node DINOSAUR = Node.withPredefinedName(Creature.class, "Dinosaur").checkName().boostRelevance();
    public static final Node ELK = Node.withPredefinedName(Creature.class, "Elk");
    public static final Node GOAT = Node.withPredefinedName(Creature.class, "Goat");
    public static final Node HORSE = Node.withPredefinedName(Creature.class, "Horse");
    public static final Node INSECT = Node.withPredefinedName(Creature.class, "Insect");
    public static final Node LION = Node.withPredefinedName(Creature.class, "Lion");
    public static final Node MULE = Node.withPredefinedName(Creature.class, "Mule");
    public static final Node MONKEY = Node.withPredefinedName(Creature.class, "Monkey");
    public static final Node PANDA = Node.withPredefinedName(Creature.class, "Panda");
    public static final Node PIG = Node.withPredefinedName(Creature.class, "Pig");
    public static final Node RABBIT = Node.withPredefinedName(Creature.class, "Rabbit");
    public static final Node SABRE_CAT = Node.withPredefinedName(Creature.class, "Sabre Cat");
    public static final Node SHARK = Node.withPredefinedName(Creature.class, "Shark");
    public static final Node SHEEP = Node.withPredefinedName(Creature.class, "Sheep");
    public static final Node SPIDER = Node.withPredefinedName(Creature.class, "Spider");
    public static final Node UNICORN = Node.withPredefinedName(Creature.class, "Unicorn");
    public static final Node WHALE = Node.withPredefinedName(Creature.class, "Whale");
    public static final Node WOLF = Node.withPredefinedName(Creature.class, "Wolf");
    public static final Node WORM = Node.withPredefinedName(Creature.class, "Worm");

    // =========================================================================
    // DATA (CLASSIC RPG)
    // =========================================================================
    public static final Node CYCLOPS = Node.withPredefinedName(Creature.class, "Cyclops", "cyclops", "cyclop");
    public static final Node DRAGON = Node.withPredefinedName(Creature.class, "Dragon").boostRelevance();
    public static final Node DRAUGR = Node.withPredefinedName(Creature.class, "Draugr");
    public static final Node DWARF = Node.withPredefinedName(Creature.class, "Dwarf");
    public static final Node ELF = Node.withPredefinedName(Creature.class, "Elf");
    public static final Node GIANT = Node.withPredefinedName(Creature.class, "Giant");
    public static final Node GOBLIN = Node.withPredefinedName(Creature.class, "Goblin").boostRelevance();
    public static final Node GNOLL = Node.withPredefinedName(Creature.class, "Gnoll");
    public static final Node GHOST = Node.withPredefinedName(Creature.class, "Ghost");
    public static final Node GHOUL = Node.withPredefinedName(Creature.class, "Ghoul");
    public static final Node GOLEM = Node.withPredefinedName(Creature.class, "Golem");
    public static final Node HUMAN = Node.withPredefinedName(Creature.class, "Human");
    public static final Node KOBOLD = Node.withPredefinedName(Creature.class, "Kobold");
    public static final Node OGRE = Node.withPredefinedName(Creature.class, "Ogre");
    public static final Node ORC = Node.withPredefinedName(Creature.class, "Orc").boostRelevance();
    public static final Node SHADE = Node.withPredefinedName(Creature.class, "Shade");
    public static final Node SLIME = Node.withPredefinedName(Creature.class, "Slime");
    public static final Node SKELETON = Node.withPredefinedName(Creature.class, "Skeleton");
    public static final Node TROLL = Node.withPredefinedName(Creature.class, "Troll");
    public static final Node UNDEAD = Node.withPredefinedName(Creature.class, "Undead").boostRelevance();
    public static final Node WEREWOLF = Node.withPredefinedName(Creature.class, "Werewolf", "werewolf", "lycan");
    public static final Node WITCH = Node.withPredefinedName(Creature.class, "Witch").boostRelevance();
    public static final Node WRAITH = Node.withPredefinedName(Creature.class, "Wraith");
    public static final Node WYVERN = Node.withPredefinedName(Creature.class, "Wyvern");

    // =========================================================================
    // DATA (MYTHIC CREATURES)
    // =========================================================================
    public static final Node BASILISK = Node.withPredefinedName(Creature.class, "Basilisk");
    public static final Node CERBERUS = Node.withPredefinedName(Creature.class, "Cerberus");
    public static final Node CHIMERA = Node.withPredefinedName(Creature.class, "Chimera");
    public static final Node COCKATRICE = Node.withPredefinedName(Creature.class, "Cockatrice");
    public static final Node IFRIT = Node.withPredefinedName(Creature.class, "Ifrit");
    public static final Node HYDRA = Node.withPredefinedName(Creature.class, "Hydra");
    public static final Node KRAKEN = Node.withPredefinedName(Creature.class, "Kraken");
    public static final Node MANTICORE = Node.withPredefinedName(Creature.class, "Manticore");
    public static final Node MINOTAUR = Node.withPredefinedName(Creature.class, "Minotaur");
    public static final Node YETI = Node.withPredefinedName(Creature.class, "Yeti");

    // =========================================================================
    // DATA (HUMANS)
    // =========================================================================
    // COMBAT
    public static final Node ARCHER = Node.withPredefinedName(Creature.class, "Archer");
    public static final Node BARBARIAN = Node.withPredefinedName(Creature.class, "Barbarian");
    public static final Node BARD = Node.withPredefinedName(Creature.class, "Bard");
    public static final Node MAGE = Node.withPredefinedName(Creature.class, "Mage");
    public static final Node MONK = Node.withPredefinedName(Creature.class, "Monk");
    public static final Node NINJA = Node.withPredefinedName(Creature.class, "Ninja").checkName().boostRelevance();
    public static final Node PIRATE = Node.withPredefinedName(Creature.class, "Pirate").checkName().boostRelevance();
    public static final Node SAMURAI = Node.withPredefinedName(Creature.class, "Samurai").checkName().boostRelevance();
    public static final Node THIEF = Node.withPredefinedName(Creature.class, "Thief");
    public static final Node WARRIOR = Node.withPredefinedName(Creature.class, "Warrior");
    public static final Node WIZARD = Node.withPredefinedName(Creature.class, "Wizard").boostRelevance();

    // PROFESSIONS
    public static final Node COP = Node.withPredefinedName(Creature.class, "Police Officer", "cop", "police officer");
    public static final Node DANCER = Node.withPredefinedName(Creature.class, "Dancer");
    public static final Node MANAGER = Node.withPredefinedName(Creature.class, "Manager");
    public static final Node MEDIC = Node.withPredefinedName(Creature.class, "Medic");
    public static final Node PILOT = Node.withPredefinedName(Creature.class, "Pilot");
    public static final Node PRIEST = Node.withPredefinedName(Creature.class, "Priest");
    public static final Node SINGER = Node.withPredefinedName(Creature.class, "Singer");
    public static final Node SOLDIER = Node.withPredefinedName(Creature.class, "Soldier");
    public static final Node SCIENTIST = Node.withPredefinedName(Creature.class, "Scientist");

    // NOBILITY
    public static final Node EMPEROR = Node.withPredefinedName(Creature.class, "Emperor");
    public static final Node KING = Node.withPredefinedName(Creature.class, "King");
    public static final Node PHARAOH = Node.withPredefinedName(Creature.class, "Pharaoh");
    public static final Node PRINCE = Node.withPredefinedName(Creature.class, "Prince");
    public static final Node PRINCESS = Node.withPredefinedName(Creature.class, "Princess");
    public static final Node SULTAN = Node.withPredefinedName(Creature.class, "Sultan");
    public static final Node QUEEN = Node.withPredefinedName(Creature.class, "Queen");

    // =========================================================================
    // DATA (OTHERS)
    // =========================================================================
    public static final Node ALIEN = Node.withPredefinedName(Creature.class, "Alien").checkName();
    public static final Node ANGEL = Node.withPredefinedName(Creature.class, "Angel");
    public static final Node AUTOMATON = Node.withPredefinedName(Creature.class, "Automaton");
    public static final Node CENTAUR = Node.withPredefinedName(Creature.class, "Centaur");
    public static final Node COLOSSUS = Node.withPredefinedName(Creature.class, "Colossus");
    public static final Node DEMON = Node.withPredefinedName(Creature.class, "Demon").boostRelevance();
    public static final Node HARPPIA = Node.withPredefinedName(Creature.class, "Harppia");
    public static final Node MECHA = Node.withPredefinedName(Creature.class, "Mecha");
    public static final Node MERMAID = Node.withPredefinedName(Creature.class, "Mermaid");
    public static final Node MEDUSA = Node.withPredefinedName(Creature.class, "Medusa");
    public static final Node MONSTER = Node.withPredefinedName(Creature.class, "Monster").checkName().boostRelevance();
    public static final Node MUMMY = Node.withPredefinedName(Creature.class, "Mummy");
    public static final Node MUTANT = Node.withPredefinedName(Creature.class, "Mutant").boostRelevance();
    public static final Node ROBOT = Node.withPredefinedName(Creature.class, "Robot").boostRelevance();
    public static final Node VAMPIRE = Node.withPredefinedName(Creature.class, "Vampire").checkName().boostRelevance();
    public static final Node ZOMBIE = Node.withPredefinedName(Creature.class, "Zombie").checkName().boostRelevance();

    // =========================================================================
    // GAME SPECIFIC
    // =========================================================================
    public static final Node DARKSPAWN = Node.withPredefinedName(Creature.class, "Darkspawn");
    public static final Node FALMER = Node.withPredefinedName(Creature.class, "Falmer");
    public static final Node KIRBY = Node.withPredefinedName(Creature.class, "Kirby");

    @Override
    public Class<? extends ConceptEntry> getTargetClass() {
        return Creature.class;
    }

    @Override
    public Collection<Node> getNodesToCreate() {
        return getPredefinedNodes();
    }

}
