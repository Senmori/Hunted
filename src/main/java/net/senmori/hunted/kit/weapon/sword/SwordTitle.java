package net.senmori.hunted.kit.weapon.sword;

public enum SwordTitle {
    DEFILER("Defiler of the Dead"), VENGEANCE_FORGOTTEN("Vengeance of the Forgotten"), PLEDGE_INFINITE("Pledge of Inifinite Trials"), CRUSADER_CORRUPTION("Crusader of Corruption"), SUFFERING_WIT("Wit of Suffering"), PROMISE_BROKEN_BONES("Promise of Broken Bones"), PROMISE_CATACLYSM("Promise of the Cataclysm"), MEMOORY_OF_VOID("Memory of the Void"), DEFLECTOR_OF_SECRETS("Deflector of Secrets"), LONGSWORD_OF_EAST("Longsword of the East"), RAVAGER_OF_WHISPERS("Ravager of the Whispers"), WARGLAIVE_OF_PROTECTOR("Warglaive of the Protector"), SPINE_ETERNAL_STRUGGLES("Spine of Eternal Struggles"), SPELLBLADE_OF_WIZARDRY("Spellblade of Wizardry"), RAZOR_OF_CUNNING("Razor of Cunning"), RAZOR_OF_LIGHT("Razor of the Light"), GUARDIAN_HORRID_DREAMS("Guardian of Horrid Dreams"), BLESSED_BLADE_OF_BROKEN_BONES("Blessed Blade of Broken Bones"), DAWN_OF_REGRET("Dawn of Regret"), REAVER_OF_LONE_VICTOR("Reaver of the Lone Victor"), BLOOD_BLADE_OF_INSANE("Blood Blade of the Insane"), MIGHT_OF_FADED_MEMORIES("Might of Faded Memories"), BREAKER_OF_WEST("Breaker of the West"), BREAKER_OF_EAST("Breaker of the East"), BREAKER_OF_NORTH("Breaker of the North"), BREAKER_OF_SOUTH("Breaker of the South"), RAPIER_HOLY_MIGHT("Rapier of Holy Might"), DARKBLADE_OF_EMPEROR("Darkblade of the Emperor");

    private String name;

    SwordTitle(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
