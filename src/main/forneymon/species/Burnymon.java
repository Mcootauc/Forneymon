package main.forneymon.species;

/**
 * Fire-based Forneymon that has little health but can deal bonus
 * damage to other Forneymon of certain types
 */
public class Burnymon extends Forneymon {
    
    public static final int START_HEALTH = 15;
    public static final DamageType DT = DamageType.BURNY;
    
    /**
     * Creates a new Forneymon of this species with the given level
     * @param level Level in which to initialize this Forneymon
     */
    public Burnymon (int level) {
        super(START_HEALTH, DT, level);
    }
    
    /**
     * Copy constructor for this Forneymon that creates a deep-copy
     * of the given other toCopy
     * @param toCopy The other to copy from
     */
    public Burnymon (Burnymon toCopy) {
        super(toCopy.getHealth(), DT, toCopy.getLevel());
    }
    
    /**
     * Creates a copy of this Forneymon
     * @return A copy of the calling Forneymon
     */
    @Override
    public Burnymon clone() {
        return new Burnymon(this);
    }
    
}
