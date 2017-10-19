package at.htl.schichtbetrieb.entities;

/**
 * Created by phili on 19.10.2017.
 */

public class Worker{
    private int id;
    private String name;

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
    //endregion

    public Worker(){
    }
    public Worker(String name){
        setName(name);
    }
}
