package at.htl.schichtbetrieb.entities;

import android.content.ContentValues;

import at.htl.schichtbetrieb.contracts.CVAble;

/**
 * Created by phili on 22.10.2017.
 */

public class WorkerImage implements CVAble {
    private int id;
    private String name;
    private byte[] wimage;

    public WorkerImage(int id, String name, byte[] wimage) {
        this.setId(id);
        this.setName(name);
        this.setWimage(wimage);
    }

    @Override
    public ContentValues toCV() {
        ContentValues cv = new ContentValues();
        cv.put("NAME", getName());
        cv.put("WIMAGE", getWimage());
        return cv;
    }

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

    public byte[] getWimage() {
        return wimage;
    }

    public void setWimage(byte[] wimage) {
        this.wimage = wimage;
    }
    //endregion
}


