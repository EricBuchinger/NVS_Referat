package at.htl.schichtbetrieb.entities;

/**
 * Created by phili on 19.10.2017.
 */

public class Worker{
    private int id;
    private String name;
    private boolean working;
    private boolean havingBreak;

    //region Getter & Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isWorking() {
        return working;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }

    public boolean isHavingBreak() {
        return havingBreak;
    }

    public void setHavingBreak(boolean havingBreak) {
        this.havingBreak = havingBreak;
    }
    //endregion

    public Worker(){
        setWorking(false);
        setHavingBreak(false);
    }
    public Worker(String name){
        setWorking(false);
        setHavingBreak(false);
        setName(name);
    }
}
