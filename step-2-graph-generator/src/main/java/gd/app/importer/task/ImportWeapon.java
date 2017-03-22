package gd.app.importer.task;

import gd.app.importer.model.Node;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.plot.Weapon;
import gd.infrastructure.steriotype.GDTask;
import java.util.Collection;

@GDTask
public class ImportWeapon extends ImportTask {

    // ========================================================================
    // CATEGORIES
    // ========================================================================
    public static final Node CATEGORY_FIREARMS = Node.withPredefinedName(Weapon.class, "Firearm").addAlias("Fire weapon");
    public static final Node CATEGORY_WHITEARMS = Node.withPredefinedName(Weapon.class, "Whitearm").addAlias("White weapon").addAlias("Cold weapon");
    public static final Node CATEGORY_EXPLOSIVE = Node.withPredefinedName(Weapon.class, "Explosive");
    public static final Node CATEGORY_DEFENSE = Node.withPredefinedName(Weapon.class, "Defense");

    // ========================================================================
    // WHITE ARMS
    // ========================================================================
    public static final Node AXE = Node.withPredefinedName(Weapon.class, "Axe");
    public static final Node BOW = Node.withPredefinedName(Weapon.class, "Bow");
    public static final Node BLADE = Node.withPredefinedName(Weapon.class, "Blade");
    public static final Node CROSSBOW = Node.withPredefinedName(Weapon.class, "Crossbow");
    public static final Node HALBERD = Node.withPredefinedName(Weapon.class, "Halberd");
    public static final Node HAMMER = Node.withPredefinedName(Weapon.class, "Hammer");
    public static final Node KATANA = Node.withPredefinedName(Weapon.class, "Katana");
    public static final Node MACE = Node.withPredefinedName(Weapon.class, "Mace");
    public static final Node MAUL = Node.withPredefinedName(Weapon.class, "Maul");
    public static final Node NUNCHAKU = Node.withPredefinedName(Weapon.class, "Nunchaku");
    public static final Node SPEAR = Node.withPredefinedName(Weapon.class, "Spear");
    public static final Node SWORD = Node.withPredefinedName(Weapon.class, "Sword");
    public static final Node STAFF = Node.withPredefinedName(Weapon.class, "Staff", "magic staff");
    public static final Node TRIDENT = Node.withPredefinedName(Weapon.class, "Trident");

    // ========================================================================
    // FIREARMS
    // ========================================================================
    public static final Node ASSAULT_RIFLE = Node.withPredefinedName(Weapon.class, "Assault Rifle");
    public static final Node FLAMETHROWER = Node.withPredefinedName(Weapon.class, "Flamethrower");
    public static final Node GRENADE_LAUNCHER = Node.withPredefinedName(Weapon.class, "Grenade Launcher");
    public static final Node LASER = Node.withPredefinedName(Weapon.class, "Laser");
    public static final Node PISTOL = Node.withPredefinedName(Weapon.class, "Pistol");
    public static final Node REVOLVER = Node.withPredefinedName(Weapon.class, "Revolver");
    public static final Node RIFLE = Node.withPredefinedName(Weapon.class, "Rifle");
    public static final Node SUBMACHINE_GUN = Node.withPredefinedName(Weapon.class, "Submachine Gun / SMG", "submachine gun", "smg");
    public static final Node SHOTGUN = Node.withPredefinedName(Weapon.class, "Shotgun");
    public static final Node ROCKET_LAUNCHER = Node.withPredefinedName(Weapon.class, "Rocket Launcher");
    public static final Node SNIPER = Node.withPredefinedName(Weapon.class, "Sniper Rifle", "sniper");
    public static final Node TURRET = Node.withPredefinedName(Weapon.class, "Turret");

    // ========================================================================
    // EXPLOSIVES
    // ========================================================================
    public static final Node BAZOOKA = Node.withPredefinedName(Weapon.class, "Bazooka");
    public static final Node C4 = Node.withPredefinedName(Weapon.class, "C4");
    public static final Node FLASHBANG = Node.withPredefinedName(Weapon.class, "Flashbang", "flashbang", "stun grenade");
    public static final Node GRENADE = Node.withPredefinedName(Weapon.class, "Grenade");
    public static final Node MISSILE = Node.withPredefinedName(Weapon.class, "Missile");
    public static final Node TNT = Node.withPredefinedName(Weapon.class, "TNT");

    // ========================================================================
    // DEFENSE
    // ========================================================================
    public static final Node ARMOR = Node.withPredefinedName(Weapon.class, "Armor");
    public static final Node HELM = Node.withPredefinedName(Weapon.class, "Helm");
    public static final Node HELMET = Node.withPredefinedName(Weapon.class, "Helmet");
    public static final Node HOOD = Node.withPredefinedName(Weapon.class, "Hood");
    public static final Node SHIELD = Node.withPredefinedName(Weapon.class, "Shield");

    // ========================================================================
    // SPECIFIC WEAPONS
    // ========================================================================
    public static final Node AK_47 = Node.withPredefinedName(Weapon.class, "AK-47");
    public static final Node BERETTA = Node.withPredefinedName(Weapon.class, "Beretta");
    public static final Node FAMAS = Node.withPredefinedName(Weapon.class, "FAMAS");
    public static final Node FLAK_88 = Node.withPredefinedName(Weapon.class, "FlaK 88");
    public static final Node M1_CARBINE = Node.withPredefinedName(Weapon.class, "M1 Carbine");
    public static final Node M1_GARAND = Node.withPredefinedName(Weapon.class, "M1 Garand");
    public static final Node M14 = Node.withPredefinedName(Weapon.class, "M14");
    public static final Node M16 = Node.withPredefinedName(Weapon.class, "M16");
    public static final Node M21 = Node.withPredefinedName(Weapon.class, "M21");
    public static final Node M249 = Node.withPredefinedName(Weapon.class, "M249");
    public static final Node M_79 = Node.withPredefinedName(Weapon.class, "M-79");
    public static final Node MAGNUM = Node.withPredefinedName(Weapon.class, "Magnum");
    public static final Node MOSIN_NAGANT = Node.withPredefinedName(Weapon.class, "Mosin-Nagant");
    public static final Node MP5 = Node.withPredefinedName(Weapon.class, "MP5");
    public static final Node MP40 = Node.withPredefinedName(Weapon.class, "MP40");
    public static final Node RPG_7 = Node.withPredefinedName(Weapon.class, "RPG-7");
    public static final Node SKORPION = Node.withPredefinedName(Weapon.class, "Skorpion");
    public static final Node SVD = Node.withPredefinedName(Weapon.class, "SVD", "svd", "dragunov");
    public static final Node SPAS_12 = Node.withPredefinedName(Weapon.class, "SPAS-12");
    public static final Node THOMPSON = Node.withPredefinedName(Weapon.class, "Thompson");
    public static final Node UZI = Node.withPredefinedName(Weapon.class, "Uzi");

    @Override
    public Class<? extends ConceptEntry> getTargetClass() {
        return Weapon.class;
    }

    @Override
    public Collection<Node> getNodesToCreate() {
        return getPredefinedNodes();
    }
}
