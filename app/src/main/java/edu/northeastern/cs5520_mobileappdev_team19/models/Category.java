package edu.northeastern.cs5520_mobileappdev_team19.models;

import androidx.annotation.NonNull;

public enum Category {
    MMORPG("mmorpg"),
    SHOOTER("shooter"),
    STRATEGY("strategy"),
    MOBA("moba"),
    RACING("racing"),
    SPORTS("sports"),
    SOCIAL("social"),
    SANDBOX("sandbox"),
    SURVIVAL("survival"),
    PVP("pvp"),
    PVE("pve"),
    PIXEL("pixel"),
    VOXEL("voxel"),
    ZOMBIE("zombie"),
    TURN_BASED("turn-based"),
    FIRST_PERSON("first-person"),
    THIRD_PERSON("third-Person"),
    TOP_DOWN("top-down"),
    TANK("tank"),
    SPACE("space"),
    SAILING("sailing"),
    SIDE_SCROLLER("side-scroller"),
    SUPERHERO("superhero"),
    PERMADEATH("permadeath"),
    CARD("card"),
    BATTLE_ROYALE("battle-royale"),
    MMO("mmo"),
    MMOFPS("mmofps"),
    MMOTPS("mmotps"),
    THREE_D("3d"),
    TWO_D("2d"),
    ANIME("anime"),
    FANTASY("fantasy"),
    SCI_FI("sci-fi"),
    FIGHTING("fighting"),
    ACTION_RPG("action-rpg"),
    ACTION("action"),
    MILITARY("military"),
    MARTIAL_ARTS("martial-arts"),
    FLIGHT("flight"),
    LOW_SPEC("low-spec"),
    TOWER_DEFENSE("tower-defense"),
    HORROR("horror"),
    MMORTS("mmorts");


    private final String name;

    Category(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
    //, shooter, strategy, moba, racing, sports, social, sandbox, open-world,survival,pvp,pve,pixel,voxel,zombie,turn-based,first-person,third-Person,top-down,tank,space,sailing,side-scroller,superhero,permadeath,card,battle-royale,mmo,mmofps,mmotps,3d,2d,anime,fantasy,sci-fi,fighting,action-rpg,action,military,martial-arts,flight,low-spec,tower-defense,horror,mmorts
}
