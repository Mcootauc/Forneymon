package main.forneymon.arena;

import java.util.Objects;
import main.forneymon.species.*;


/**
 * Collections of Forneymon ready to fight in the arena!
 */
public class ForneymonagArray implements Forneymonagerie {

    // Constants
    // ----------------------------------------------------------
    // [!] DO NOT change this START_SIZE for your collection, and
    // your collection *must* be initialized to this size
    private static final int START_SIZE = 4;

    // Fields
    // ----------------------------------------------------------
    private Forneymon[] collection;
    private int size;
    
    
    // Constructor
    // ----------------------------------------------------------
    public ForneymonagArray () {
        this.collection = new Forneymon[START_SIZE];
    }
    
    
    // Methods
    // ----------------------------------------------------------
    
    // >> [AF] Oh no! Lacking JavaDocs on all of your methods (-1)
    public boolean empty () {
        // >> [AF] Better: return this.size == 0;
        // Note how your return value is the same as your if-condition!
    	if(this.size == 0) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    // >> [AF] Still mingling tabs and spaces for indents below! Need to use only
    // spaces to standardize how indents appear across editors (-1)
	public int size () {
    	return this.size;
    }
    
    public boolean collect (Forneymon toAdd) {
    	//checks each object of the array to see if it's the same as toAdd's type
    	for(int i = 0; i < this.size; i++) {
    		//checks if same type
    		if(toAdd.getSpecies() == this.collection[i].getSpecies()) {
    			//checks if they're not the same level
    			if(toAdd.getLevel() != this.collection[i].getLevel()) {
    				//if toAdd is higher lvl, the forneymon in collection is replaced
    				if(toAdd.getLevel() > this.collection[i].getLevel()) {
    					this.collection[i] = toAdd;
    					return true;
    				}
    			}
    			return false;
    		}
    	}
    	checkAndGrow();
    	insertAt(toAdd, size);
    	return true;
    }
    
    public boolean releaseSpecies (String fmSpecies) {
        // >> [AF] releaseSpecies can make use of the getSpeciesIndex method to
        // reduce repeated code (-0.5)
        for(int i = 0; i < this.size; i++) {
        	if(this.collection[i].getSpecies() != null && this.collection[i].getSpecies().equals(fmSpecies)) {
        		remove(i);
        		return true;
        	}
        }
        return false;
    }
    
    public Forneymon get (int index) {
    	if(index < 0 || index > this.size - 1) {
    		throw new IllegalArgumentException("Index can't be less than 0 or greater than the size");
    	}
        return this.collection[index];
    }
    
    public Forneymon getMVP () {
    	int highIndex = 0;
        if(this.size == 0) {
        	return null;
        }
        for(int i = 1; i < this.size; i++) {
            // >> [AF] Looks like some repeated logic below -- see how these if-statements are almost
            // exactly the same? Time to take a second trip to the drawing board when you notice that (-0.5)
        	if(this.collection[highIndex].getLevel() != this.collection[i].getLevel()) {
        		if(this.collection[highIndex].getLevel() < this.collection[i].getLevel()) {
        			highIndex = i;
        		}
        	}else if(this.collection[highIndex].getHealth() != this.collection[i].getHealth()) {
        		if(this.collection[highIndex].getHealth() < this.collection[i].getHealth()) {
        			highIndex = i;
        		}
        	}
        }
        return this.collection[highIndex];
    }
    
    public Forneymon remove (int index) {
    	if(index < 0 || index > this.size - 1) {
    		throw new IllegalArgumentException("Index can't be less than 0 or greater than the size");
    	}
        Forneymon theForney = this.collection[index]; 
        this.collection[index] = null;
        shiftLeft(index);
        this.size = this.size - 1;
        return theForney;
    }
    
    public int getSpeciesIndex (String fmSpecies) {
        for(int i = 0; i < this.size; i++) {
        	if(this.collection[i].getSpecies() != null && collection[i].getSpecies().equals(fmSpecies)) {
        		return i;
        	}
        }
        return -1;
    }
    
    // >> [AF] Notice how getSpeciesIndex and containsSpecies are basically the same
    // method in terms of the implementing syntax; here's where you should spot the
    // similarity and exploit opportunities to reduce repetition, e.g., you could have
    // implemented containsSpecies as simply: return getSpeciesIndex(toCheck) != -1;
    // or (mainly for future assignments) create a helper that both can call (-1)
    public boolean containsSpecies (String fmSpecies) {
    	 for(int i = 0; i < this.size; i++) {
         	if(this.collection[i].getSpecies() != null && this.collection[i].getSpecies().equals(fmSpecies)) {
         		return true;
         	} 
         }
    	 return false;
    }
    
    public void trade (Forneymonagerie other) {
        Forneymon[] tempArr = this.collection; 
        // >> [AF] Same here: you're downcasting other multiple times; just save the downcast
        // reference once and then use that onwards
        this.collection = ((ForneymonagArray)other).collection;
        ((ForneymonagArray)other).collection = tempArr;
        int tempSize = this.size; 
        this.size = ((ForneymonagArray)other).size;
        ((ForneymonagArray)other).size = tempSize;
    }
    
    public void rearrange (String fmSpecies, int index) {
    	if(index < 0 || index > this.size - 1) {
    		throw new IllegalArgumentException("Index can't be less than 0 or greater than the size");
    	}
    	for(int i = 0; i < this.size; i++) {
    		if(this.collection[i].getSpecies() != null && this.collection[i].getSpecies().equals(fmSpecies)) {
    			Forneymon selectedForney = this.collection[i];
    			remove(i);
    			insertAt(selectedForney, index);
    			return;
    		}
    	}
    }
    
    // Overridden Methods
    // ----------------------------------------------------------
    
    @Override
    public ForneymonagArray clone () {
    	ForneymonagArray ogForneyArr = new ForneymonagArray();
    	for(int i = 0; i < this.size; i++) {
    		ogForneyArr.collection[i] = this.collection[i].clone();
    	}
    	ogForneyArr.size = this.size;
    	return ogForneyArr;
    }
    
    @Override
    public boolean equals (Object other) {
        // >> [AF] Follow the format of equals we patterned in our Forneymon class:
        // 1. Check for aliasing
        // 2. Check for same class
        // 3. Check for field equivalence
        ForneymonagArray otherForneyArr = (ForneymonagArray) other;
        if(this.size != otherForneyArr.size) {
        	return false;
        }
        int count = 0;
        for(int i = 0; i < this.size; i++) {
        	if(this.collection[i].equals(otherForneyArr.collection[i])) {
        		count = count + 1;
        	}
        }
        if(count != this.size) {
        	return false;
        }
        return true;
    }
    
    @Override
    public String toString () {
        String[] result = new String[size];
        for (int i = 0; i < size; i++) {
            result[i] = collection[i].toString();
        }
        return "[ " + String.join(", ", result) + " ]";
    }
    
    @Override
    public int hashCode () {
        return Objects.hash(this.collection, this.size);
    }
    
    
    // Private helper methods
    // ----------------------------------------------------------

    // >> [AF] Helper methods should be private (-0.5)
    public void insertAt(Forneymon toAdd, int index) {
        if (index < 0 || index > this.size) {
            throw new IllegalArgumentException("Index must be in range of ArrayList size");
        }
        Forneymon replacer = toAdd;
        Forneymon moveAhead;
        this.size++;
        for (int i = index; i < this.size; i++) {
            checkAndGrow();
            moveAhead = this.collection[i];
            this.collection[i] = replacer;
            replacer = moveAhead;
        }

    }
    
    private void checkAndGrow () {
        // Case: big enough to fit another item, so no
        // need to grow
        if (this.size < this.collection.length) {
            return;
        }
        
        // Case: we're at capacity and need to grow
        // Step 1: create new, bigger array; we'll
        // double the size of the old one
        Forneymon[] newItems = new Forneymon[this.collection.length * 2];
        
        // Step 2: copy the items from the old array
        for (int i = 0; i < this.collection.length; i++) {
            newItems[i] = this.collection[i];
        }
        
        // Step 3: update items reference
        this.collection = newItems;
    }
    
    private void shiftLeft (int index) {
        for (int i = index; i < this.size - 1; i++) {
            this.collection[i] = this.collection[i+1];
        }
    }
}

// ===================================================
// >>> [AF] Summary
// Functionally, an excellent submission that was
// plainly well-tested and caught all but a couple of
// edge cases. Stylistically, however, room for a lot of
// improvement, as documentation was absent, and code
// repetition was located in a lot of different places
// throughout your code. Look for opportunities to not
// repeat similar logic by calling other methods or
// making more helper methods, and you'll be golden
// in the future!
// ---------------------------------------------------
// >>> [AF] Style Checklist
// [X] Variables and helper methods named and used well
// [ ] Proper and consistent indentation and spacing
// [ ] Proper JavaDocs provided for ALL methods
// [ ] Logic is adequately simplified
// [ ] Code repetition is kept to a minimum
// ---------------------------------------------------
// Correctness:         96 / 100 (-2 / missed unit test)
// Style Penalty:       -4
// Late (-20%):         -18
// Total:               74 / 100
// ===================================================