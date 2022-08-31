package test.forneymon.arena;

import main.forneymon.arena.*;
import main.forneymon.species.*;
import static main.forneymon.arena.ForneymonArena.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.rules.Timeout;
import org.junit.runner.Description;

import static org.junit.Assert.*;

import org.junit.AfterClass;

public class ForneymonBattlegroundTests {
    
    // =================================================
    // Test Configuration
    // =================================================
    
    // Global timeout to prevent infinite loops from
    // crashing the test suite
    // [!] Comment out the next 2 lines if you're using
    // the debugger!
    @Rule
    public Timeout globalTimeout = Timeout.seconds(1);
    
    // Grade record-keeping
    static int possible = 0, passed = 0;

    // Each time you pass a test, you get a point! Yay!
    // [!] Requires JUnit 4+ to run
    @Rule
    public TestWatcher watchman = new TestWatcher() {
        @Override
        protected void succeeded(Description description) {
            passed++;
        }
    };
    
    // Used as the basic empty ForneymonagArray to test; the @Before
    // method is run before every @Test
    ForneymonagArray fm1;
    @Before
    public void init () {
        possible++;
        fm1 = new ForneymonagArray();
    }
    
    // Used for grading, reports the total number of tests
    // passed over the total possible
    @AfterClass
    public static void gradeReport () {
        System.out.println("============================");
        System.out.println("Tests Complete");
        System.out.println(passed + " / " + possible + " passed!");
        if ((1.0 * passed / possible) >= 0.9) {
            System.out.println("[!] Nice job!"); // Automated acclaim!
        }
        System.out.println("============================");
    }
    
    
    // =================================================
    // Unit Tests
    // =================================================
    
    @Test
    public void size_t0() {
        assertEquals(0, fm1.size());
        fm1.collect(new Dampymon(1));
        assertEquals(1, fm1.size());
    }


    @Test
    public void collect_t0() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        assertTrue(fm1.containsSpecies("Dampymon"));
        assertTrue(fm1.containsSpecies("Burnymon"));
        assertTrue(!fm1.containsSpecies("Zappymon"));
        assertEquals(2, fm1.size());
        assertEquals("Burnymon", fm1.get(1).getSpecies());
    }
    @Test
    public void collect_t1() {
        Dampymon d1 = new Dampymon(1);
        Dampymon d2 = new Dampymon(2);
        fm1.collect(d1);
        fm1.collect(d1);
        assertTrue(fm1.containsSpecies("Dampymon"));
        assertEquals(1, fm1.get(0).getLevel());
        fm1.collect(d2);
        assertEquals(2, fm1.get(0).getLevel());
        assertEquals(1, fm1.size());
    }

    
    @Test
    public void releaseSpecies_t0() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        assertEquals(2, fm1.size());
        fm1.releaseSpecies("Dampymon");
        assertEquals(1, fm1.size());
        assertTrue(fm1.containsSpecies("Burnymon"));
        assertTrue(!fm1.containsSpecies("Dampymon"));
    }
    
    
    @Test
    public void get_t0() {
        Dampymon d1 = new Dampymon(1);
        Burnymon b1 = new Burnymon(1);
        fm1.collect(d1);
        fm1.collect(b1);
        assertEquals(d1, fm1.get(0));
        assertEquals(b1, fm1.get(1));
    }
    
    
    @Test
    public void getMVP_t0() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(3));
        fm1.collect(new Leafymon(3));
        Forneymon result = fm1.getMVP();
        assertEquals("Leafymon", result.getSpecies());
        assertEquals(3, result.getLevel());
    }
    @Test
    public void getMVP_t1() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(3));
        fm1.collect(new Leafymon(3));
        fm1.get(2).takeDamage(10, DamageType.BASIC);
        Forneymon result = fm1.getMVP();
        assertEquals("Burnymon", result.getSpecies());
        assertEquals(3, result.getLevel());
    }

    
    @Test
    public void testRemove_t0() {
        Dampymon d1 = new Dampymon(1);
        Burnymon b1 = new Burnymon(1);
        fm1.collect(d1);
        fm1.collect(b1);
        assertEquals(2, fm1.size());
        fm1.remove(0);
        assertEquals(1, fm1.size());
        assertEquals(b1, fm1.get(0));
    }
    
    
    @Test
    public void getSpeciesIndexContainsSpecies_t0() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        assertEquals(0, fm1.getSpeciesIndex("Dampymon"));
        assertEquals(1, fm1.getSpeciesIndex("Burnymon"));
        assertEquals(-1, fm1.getSpeciesIndex("Leafymon"));
        assertTrue(fm1.containsSpecies("Dampymon"));
        assertFalse(fm1.containsSpecies("Zappymon"));
    }
    
    
    @Test
    public void rearrange_t0() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        fm1.rearrange("Leafymon", 0);
        assertEquals(1, fm1.getSpeciesIndex("Dampymon"));
        assertEquals(2, fm1.getSpeciesIndex("Burnymon"));
        assertEquals(0, fm1.getSpeciesIndex("Leafymon"));
    }
    @Test
    public void rearrange_t1() {
        Dampymon d1 = new Dampymon(1);
        Burnymon b1 = new Burnymon(1);
        Leafymon e1 = new Leafymon(1);
        fm1.collect(d1);
        fm1.collect(b1);
        fm1.collect(e1);
        fm1.rearrange("Leafymon", 0);
        fm1.collect(new Burnymon(1));
        fm1.collect(new Dampymon(1));
        fm1.collect(d1);
        fm1.collect(b1);
        fm1.collect(e1);
        assertEquals(1, fm1.getSpeciesIndex("Dampymon"));
        assertEquals(2, fm1.getSpeciesIndex("Burnymon"));
        assertEquals(0, fm1.getSpeciesIndex("Leafymon"));
        assertEquals(e1, fm1.get(0));
        assertEquals(d1, fm1.get(1));
        assertEquals(b1, fm1.get(2));
    }
    
    
    @Test
    public void clone_t0() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        ForneymonagArray dolly = fm1.clone();
        assertEquals(3, dolly.size());
        
        fm1.get(0).takeDamage(5, DamageType.BASIC);
        assertEquals(Dampymon.START_HEALTH - 5, fm1.get(0).getHealth());
        assertEquals(Dampymon.START_HEALTH, dolly.get(0).getHealth());
        
        fm1.rearrange("Leafymon", 0);
        assertEquals(0, fm1.getSpeciesIndex("Leafymon"));
        assertEquals(2, dolly.getSpeciesIndex("Leafymon"));
    }
    
    
    @Test
    public void trade_t0() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        ForneymonagArray fm2 = new ForneymonagArray();
        fm2.collect(new Leafymon(1));
        fm1.trade(fm2);
        
        assertEquals(2, fm2.size());
        assertEquals(1, fm1.size());
        assertTrue(fm1.containsSpecies("Leafymon"));
        assertTrue(!fm1.containsSpecies("Dampymon"));
        assertTrue(fm2.containsSpecies("Dampymon"));
        assertTrue(!fm2.containsSpecies("Leafymon"));
    }
    
    
    @Test
    public void equals_t0() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        ForneymonagArray fm2 = new ForneymonagArray();
        fm2.collect(new Dampymon(1));
        fm2.collect(new Burnymon(1));
        
        assertEquals(fm1, fm2);
        fm2.rearrange("Burnymon", 0);
        assertNotEquals(fm1, fm2);
    }
    
    
    // =================================================
    // Integration Tests
    // =================================================
    
    @Test
    public void fight_t0() {
        fm1.collect(new Dampymon(1));
        ForneymonagArray fm2 = new ForneymonagArray();
        fm2.collect(new Dampymon(1));
        
        fight(fm1, fm2, false);
        assertEquals(0, fm1.size());
        assertEquals(0, fm2.size());
    }
    @Test
    public void fight_t1() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        ForneymonagArray fm2 = new ForneymonagArray();
        fm2.collect(new Burnymon(1));
        fm2.collect(new Dampymon(1));
        
        fight(fm1, fm2, false);
        assertEquals(0, fm1.size());
        assertEquals(0, fm2.size());
    }
    @Test
    public void fight_t2() {
        fm1.collect(new Dampymon(3));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        ForneymonagArray fm2 = new ForneymonagArray();
        fm2.collect(new Burnymon(3));
        fm2.collect(new Dampymon(1));
        fm2.collect(new Zappymon(1));
        
        fight(fm1, fm2, false);
        assertEquals(0, fm1.size());
        assertEquals(1, fm2.size());
    }
    @Test
    public void fight_t3() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Zappymon(1));
        ForneymonagArray fm2 = new ForneymonagArray();
        fm2.collect(new Burnymon(5));
        fm2.collect(new Dampymon(5));
        
        fight(fm1, fm2, false);
        assertEquals(0, fm1.size());
        assertEquals(1, fm2.size());
    }
    @Test
    public void fight_t4() {
        fm1.collect(new Burnymon(5));
        ForneymonagArray fm2 = new ForneymonagArray();
        fm2.collect(new Dampymon(1));
        
        fight(fm1, fm2, false);
        assertEquals(1, fm1.size());
        assertEquals(0, fm2.size());
        assertEquals(3, fm1.get(0).getHealth());
        assertFalse(fm2.containsSpecies("Dampymon"));
    }
    @Test
    public void fight_t5() {
        fm1.collect(new Dampymon(3));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        fm1.collect(new Zappymon(1));
        ForneymonagArray fm2 = new ForneymonagArray();
        fm2.collect(new Burnymon(3));
        fm2.collect(new Dampymon(3));
        fm2.collect(new Zappymon(3));
        fm2.collect(new Leafymon(3));
        
        fight(fm1, fm2, false);
        assertEquals(0, fm1.size());
        assertEquals(2, fm2.size());
    }
    
}
