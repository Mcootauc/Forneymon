package main.forneymon.arena;

import main.forneymon.species.*;

public interface Forneymonagerie {

    public boolean empty ();
    public int size ();
    public boolean collect (Forneymon toAdd);
    public Forneymon get (int index);
    public Forneymon getMVP ();
    public Forneymon remove (int index);
    public boolean releaseSpecies (String fmSpecies);
    public int getSpeciesIndex (String fmSpecies);
    public boolean containsSpecies (String fmSpecies);
    public void rearrange (String fmSpecies, int index);
    public Forneymonagerie clone ();
    public boolean equals (Object other);
    public String toString ();
    
}
