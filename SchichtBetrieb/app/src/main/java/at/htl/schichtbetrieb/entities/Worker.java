package at.htl.schichtbetrieb.entities;

/**
 * Created by phili on 19.10.2017.
 */

public class Worker{
    private int id;
    private String name;
    private boolean working;
    private Activity activity;

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

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    //endregion

    public Worker(){
        setWorking(false);
    }

    public Worker(int id, String name, boolean working, Activity activity) {
        this.id = id;
        this.name = name;
        this.working = working;
        this.activity = activity;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
