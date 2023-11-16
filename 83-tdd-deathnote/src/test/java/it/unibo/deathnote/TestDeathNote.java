package it.unibo.deathnote;

import javax.print.DocFlavor.STRING;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.unibo.deathnote.api.DeathNote;
import java.util.List;

class TestDeathNote {
    private DeathNote notebook;
    
    @BeforeEach
    public void setUp(){
        this.notebook = new DeathNote() {
            private List<String> names; 
            private List<String> causes;
            private List<String> details;
            private long nameInterval;
            private long deathInterval;

            @Override
            public String getRule(int ruleNumber) {
                if(ruleNumber > RULES.size() || ruleNumber < 1)
                {
                    throw new IllegalArgumentException("The rule you're looking for does not exist.");
                }
                else{
                    return RULES.get(ruleNumber);
                }
                
            }

            @Override
            public void writeName(String name) {
                if(name.isEmpty())
                {
                    throw new NullPointerException("A blank space that needs a name.");
                }                
                else{
                    addName(name);
                    long currentTime = java.lang.System.currentTimeMillis();
                    setNameInterval(currentTime);
                }
            }

            @Override
            public boolean writeDeathCause(String cause) {
                long timeLimit = 40;
                long writingInterval = getNameInterval() - java.lang.System.currentTimeMillis();
                            
                if(getCauseList().isEmpty()){
                    throw new IllegalStateException("Death can't be achieved if there is no cause.");
                }
                else if(getNameList().isEmpty()){
                    throw new IllegalStateException("We don't have a victim, yet.");
                }

                if(writingInterval <= timeLimit){
                    setDeathInterval(java.lang.System.currentTimeMillis());
                    setCause(cause);
                    return true;
                }
                else{
                    return false;
                }
                
            }

            @Override
            public boolean writeDetails(String details) {
                long timeLimit = 6040;
                long writingInterval = getDeathInterval() - java.lang.System.currentTimeMillis();
                            
                if(getDetails().isEmpty()){
                    throw new IllegalStateException("How can we kill if we don't even know how it will go ?");
                }
                else if(getNameList().isEmpty()){
                    throw new IllegalStateException("We don't have a victim, yet.");
                }

                if(writingInterval <= timeLimit){
                    return true;
                }
                else{
                    return false;
                }
            }
            
            @Override
            public String getDeathCause(String name) {
                if(isNameWritten(name)){
                    throw new IllegalArgumentException("There's no victim with this name, yet.");    
                }
                else{
                    int position = getNamePosition(name);
                    if(getCause(position).isEmpty()){
                        return "Heart Attack";
                    }
                    else{
                        return getCause(position);
                    }
                }
            }

            @Override
            public String getDeathDetails(String name) {
                
                if(isNameWritten(name)){
                    throw new IllegalArgumentException("There's no victim with this name, yet.");    
                }
                else{
                    int position = getNamePosition(name);
                    if(getDetail(position).isEmpty()){
                        return "";
                    }
                    else{
                        return getDetail(position);
                    }
                }
            }

            @Override
            public boolean isNameWritten(String name) {
                int doesNotExist = -1;
                if(getNamePosition(name) != doesNotExist){
                    return true;
                }
                else{
                    return false;
                }
                
            }

            public List<String> getNameList() {
                return this.names;
            }

            public String getName(int index) {
                return this.names.get(index);
            }

            public void addName(String name){
                this.names.add(name);
            }

            public int getNamePosition(String name){
                return getNameList().indexOf(name);
            }

            public List<String> getCauseList() {
                return this.causes;
            }

            public String getCause(int index){
                return this.causes.get(index);
            }

            public void setCause(String cause) {
                this.causes.add(cause);
            }

            public long getNameInterval() {
                return this.nameInterval;
            }

            public void setNameInterval(long nameInterval) {
                this.nameInterval = nameInterval;
            }

            public long getDeathInterval() {
                return this.deathInterval;
            }

            public void setDeathInterval(long deathInterval) {
                this.deathInterval = deathInterval;
            }

            public List<String> getDetails() {
                return this.details;
            }

            public String getDetail(int index){
                return this.details.get(index);
            }

            public void setDetails(String details) {
                this.details.add(details);
            }
        };
    }

    @Test
    public void testGetRule(){
        
    }    
    
}