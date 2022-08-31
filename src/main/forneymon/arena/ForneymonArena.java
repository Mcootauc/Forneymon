package main.forneymon.arena;

import main.forneymon.species.Forneymon;

/**
 * Contains methods for facing off Forneymonagerie against one another!
 */
public class ForneymonArena {
    
    public static final int BASE_DAMAGE = 5;
    
    /**
     * Conducts a fight between two Forneymonageries, consisting of the following
     * steps:
     * <ol>
     *   <li>Forneymon from each Forneymonagerie are paired to fight, in sequence
     *     starting from index 0.</li>
     *  <li>Forneymon that faint (have 0 or less health) are removed from their
     *     respective Forneymonagerie.</li>
     *  <li>Repeat until one or both Forneymonagerie have no remaining Forneymon.</li>     
     * </ol>
     * @param fm1 One of the fighting Forneymonagerie
     * @param fm2 One of the fighting Forneymonagerie
     * @param verbose Whether or not the fight's steps are printed to the console
     */
    public static void fight (Forneymonagerie fm1, Forneymonagerie fm2, boolean verbose) {
        int ind1 = -1, ind2 = -1;
        StringBuilder logString = new StringBuilder();
        
        logString.append("[!] Combat Starting!\n");
        while (!(fm1.empty() || fm2.empty())) {
            ind1 = (ind1 + 1) % fm1.size();
            ind2 = (ind2 + 1) % fm2.size();
            
            Forneymon f1 = fm1.get(ind1);
            Forneymon f2 = fm2.get(ind2);
            logString.append("  [VS] New Round: " + f1 + " vs " + f2 + "\n");
            
            // Attack phase
            if (f1.takeDamage(BASE_DAMAGE + f2.getLevel(), f2.getDamageType()) <= 0) {
                fm1.remove(ind1);
                ind1--;
            }
            if (f2.takeDamage(BASE_DAMAGE + f1.getLevel(), f1.getDamageType()) <= 0) {
                fm2.remove(ind2);
                ind2--;
            }
            logString.append("    [>] Combat Results: " + f1 + " vs " + f2 + "\n");
        }
        
        logString.append("[!] Combat Finished! Victor: " + ((fm1.empty() && fm2.empty()) ? "TIE MATCH!" : "Forneymonagerie " + (fm1.empty() ? 2 : 1) + "\n"));
        if (verbose) { System.out.println(logString.toString()); }
    }
    
}
