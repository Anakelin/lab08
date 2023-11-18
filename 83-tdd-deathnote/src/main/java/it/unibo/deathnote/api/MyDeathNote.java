package it.unibo.deathnote.api;

import java.util.ArrayList;
import java.util.List;

public class MyDeathNote implements DeathNote{
    private List<String> names; 
    private List<String> causes;
    private List<String> details;
    private long nameInterval;
    
    public MyDeathNote() {
        this.names = new ArrayList<>();
        this.causes = new ArrayList<>();
        this.details = new ArrayList<>();
    }
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
        long writingInterval = java.lang.System.currentTimeMillis() - getNameInterval();
             
        if(getNameList().isEmpty()){
            throw new IllegalStateException("We don't have a victim, yet.");
        }

        if(writingInterval <= timeLimit){
            if(cause.equals("")){
                setCause();
            }
            else{
                setCause(cause);
            }
            return true;
        }
        else{
            return false;
        }
        
    }

    @Override
    public boolean writeDetails(String details) {
        long timeLimit = 6040;
        long writingInterval = java.lang.System.currentTimeMillis() - getNameInterval();
                    
        if(getNameList().isEmpty()){
            throw new IllegalStateException("We don't have a victim, yet.");
        }
        else{
            if(writingInterval <= timeLimit){
                setDetails(details);
                return true;
            }
            else{
                return false;
            }
        }

        
    }
    
    @Override
    public String getDeathCause(String name) {
        if(!isNameWritten(name)){
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
        
        if(!isNameWritten(name)){
            throw new IllegalArgumentException("There's no victim with this name, yet.");    
        }
        else{
            int position = getNamePosition(name);
            return getDetails(position);
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
        return List.copyOf(this.names);
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
        return List.copyOf(this.causes);
    }

    public String getCause(int index){
        return this.causes.get(index);
    }

    public void setCause(String cause) {
        this.causes.add(cause);
    }

    public void setCause() {
        setCause("Heart Attack");
    }

    public long getNameInterval() {
        return this.nameInterval;
    }

    public void setNameInterval(long nameInterval) {
        this.nameInterval = nameInterval;
    }

    public List<String> getDetailList() {
        return List.copyOf(this.details);
    }

    public String getDetails(int index){
        return this.details.get(index);
    }

    public void setDetails(String details){
        this.details.add(details);
    }
}
