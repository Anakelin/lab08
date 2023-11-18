package it.unibo.deathnote;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.api.MyDeathNote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class TestDeathNote {
    private MyDeathNote notebook;
    static private String VICTIM_NAME = "Goro Akechi";
    static private String CAUSE_OF_DEATH = "Shot by his father's cognition";
    static private String DEATH_DETAILS = "Inside of his father's palace";
    static private String DEFAUL_CAUSE_OF_DEATH = "Heart Attack";
    static private int SLEEP_TIME = 100;
    
    @BeforeEach
    public void setUp(){
        notebook = new MyDeathNote();
    }

    @Test
    public void testRuleNumber(){
        //Test for 0 and negative
        int negativeTest = -10;
        for (int i = 0; i > negativeTest ; i--) {
            try {
                notebook.getRule(i);
                
            } catch (Exception e) {
                assertEquals("The rule you're looking for does not exist.", e.getMessage());
            }
        }
    }    
    
    @Test
    public void testRuleNotEmpty(){
        //Test for not null, empty or blank
        int rulesNumber = DeathNote.RULES.size();
        for (int i = 1; i < rulesNumber; i++) {
            assertNotNull(notebook.getRule(i));
            assertNotEquals("", notebook.getRule(i));
            assertNotEquals("", notebook.getRule(i).replace("\\s", ""));
        }
    }

    @Test
    public void testDeathIsWritten(){
        //Test if isn't written
        assertEquals(false,notebook.isNameWritten(VICTIM_NAME));
        
        //Test if name was written
        notebook.writeName(VICTIM_NAME);
        assertEquals(true, notebook.isNameWritten(VICTIM_NAME));

        //Test it's the only name
        int expectedSize = 1;
        assertEquals(expectedSize, notebook.getNameList().size());
        
        //Test if "" can be written
        try {
            notebook.writeName("");
        } catch (Exception e) {
            assertEquals("A blank space that needs a name.",e.getMessage());
        }
    }
    
    @Test
    public void testDeathHasCause() throws InterruptedException{
        //Test cause before name
        try {
            notebook.writeDeathCause(CAUSE_OF_DEATH);
        } catch (Exception e) {
            assertEquals("We don't have a victim, yet.", e.getMessage());
        }

        //Test if default cause of death is heart attack
        notebook.writeName(VICTIM_NAME);
        notebook.writeDeathCause("");
        assertEquals(DEFAUL_CAUSE_OF_DEATH, notebook.getCause(notebook.getNamePosition(VICTIM_NAME)));

        //Test a cause of death
        String secondVictim = "Shotaro Kaneda";
        String secondCause = "karting accident";
        notebook.writeName(secondVictim);

        //Test change cause after allowed time
        assertEquals(true, notebook.writeDeathCause(secondCause));
        assertEquals(secondCause,notebook.getDeathCause(secondVictim));
        Thread.sleep(SLEEP_TIME);
        assertEquals(false,notebook.writeDeathCause(""));
    }

    @Test
    public void testDeathDetail(){
        //Test details before name
        try {
            notebook.writeDetails(DEATH_DETAILS);
        } catch (Exception e) {
            assertEquals("We don't have a victim, yet.", e.getMessage());    
        }

        //Test default details
        notebook.writeName(VICTIM_NAME);
        notebook.writeDetails("");
        assertEquals("",notebook.getDeathDetails(VICTIM_NAME));
        notebook.writeDetails(DEATH_DETAILS);
    }

    public static void main(String[] args) {
        MyDeathNote test = new MyDeathNote();
        test.writeName(VICTIM_NAME);
        test.writeDetails(DEATH_DETAILS);
    }
}