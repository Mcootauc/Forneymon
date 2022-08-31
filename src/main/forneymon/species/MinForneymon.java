package main.forneymon.species;

public interface MinForneymon {
    
    public int takeDamage (int dmg, DamageType type);
    public String getSpecies ();
    public int getHealth ();
    public int getLevel ();
    public DamageType getDamageType ();
    
}
