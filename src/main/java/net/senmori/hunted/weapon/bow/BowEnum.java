package net.senmori.hunted.weapon.bow;

public class BowEnum
{

	/*
	 * Sword names without adverbs
	 */
   public enum Bow {
       WHISPERWIND("Whisperwind"),
       HATRED_STING("Hatred's Sting"),
       WITHDRAW("WithDraw"),
       HEARTSTRING("Heartstring"),
       VIPER("Viper"),
       MAPLE_REFLEX_BOW("Maple Reflex Bow"),
       SPECIAL_DELIVERY("Special Delivery"),
       BRISTLEBLITZ("Bristleblitz"),
       FIRESTARTER("Firestarter"),
       DEATH_KISS("Death's Kiss"),
       PIQUE("Pique"),
       BON_VOYAGE("Bon Voyage"),
       NIGHTSTALKER("Nightstalker"),
       PHOENIX("Phoenix"),
       PRECISE("Precise"),
       WARSONG("Warsong"),
       MOLTEN_FURY("Molten Fury"),
       FINAL_WHISPER("Final Whisper"),
       PHANTOM("Phantom"),
       YEW_WARBOW("Yew Warbow"),
       EBONY_WARBOW("Ebony Warbow"),
       SOULSTRING("Soulstring"),
       DEATH_SIGHT("Death's Sigh"),
       HUSH("Hush"),
       SPITFIRE("Spitfire"),
       WINDBREAKER("Windbreaker"),
       VENOMSTRIKE("Venomstrike"),
       RIGORMORTIS("Rigormortis"),
       SCORPION_STING("Scorpion Sting"),
       RAIN_MAKER("Rain Maker"),
       KISS_DEATH("Kiss of Death"),
       SHADOWSTRIKE("Shadow Strike"),
       SHATTER_STORM("Shatter Storm");

       private String name;
       
        Bow(String name) {
            this.name = name;
        }
        
        public String getName() {
        	return name;
        }

   }
   
   /*
    * Adverbs that can be appended onto the end of a weapon name for more customization
    */
    public enum BowTitle {
        STRING_UNHOLY_MIGHT("String of Unholy Might"),
        SPINE_ETERNAL_STRUGGLES("Spine of Eternal Struggles"),
        CRESCENT_REGRET("Crescent of Regret"),
        SPEARGUN_TERROR("Speargun of Terror"),
        CHORD_FADED_MEMORIES("Chord of Faded Memories"),
        HUNTING_BOW_DEGREDATION("Hunting Bow of Degredation"),
        COMPOSITE_BOW_POWER("Composite Bow of Power"),
        SECRET_GRIEVING_WIDOWS("Secret of Grieving Widows"),
        RECURVE_OBLIVION("Recurve of Oblivion"),
        LAUNCHER_STORMS("Launcher of Storms"),
        WIT_DREADLORD("Wit of the Dreadlord"),
        CRESCENT_SHADOWS("Crescent of Shadows"),
        SHORTBOW_BURNING_SUN("Shortbow of the Burning Sun"),
        SPINE_BROKEN_FAMILIES("Spine of Broken Families"),
        CHAMPION_BASILISK("Champion of the Basilisk"),
        ELLIPSE_PRIDE_FALL("Ellipse of Pride's Fall"),
        PIERCER_BEAST("Piercer of the Beast"),
        GUARDIAN_EMPTY_VOID("Guardian of the Empty Void"),
        LONGBOW_MOUNTAINS("Longbow of the Mountains"),
        BRINGER_FALLEN("Bringer of the Fallen"),
        TERROR_COVENANT("Terror of the Covenant"),
        WIT_BLESSED_FORTUNE("Wit of Blessed Fortune"),
        CRESCENT_LOST("Crescent of the Lost"),
        HOPE_EARTH("Hope of the Earth"),
        CRY_BREAKING_STORM("Cry of the Breaking Storm"),
        PACT_MISERY("Pact of Misery"),
        SKEWER_LIGHT_HOPE("Skewer of Light's Hope");
        
        private String name;
        
        BowTitle(String name) {
        	this.name = name;
        }
        
        public String getName() {
        	return name;
        }

    }
}
