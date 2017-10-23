package at.htl.androidlivecoding.entities;

import android.content.ContentValues;

import at.htl.androidlivecoding.contracts.CVAble;


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

    /*
        fill imageviews with Bitmaps

        iv_w1.setImageDrawable(new BitmapDrawable(DbBitmapUtility.getImage(allWorkerImages.get(0).getWimage())));
        iv_w2.setImageDrawable(new BitmapDrawable(DbBitmapUtility.getImage(allWorkerImages.get(1).getWimage())));

        get bitmaps from assets

        Bitmap pic_w1 = DbBitmapUtility.getBitmapFromAsset(getContext(), "worker1.png");
        Bitmap pic_w2 = DbBitmapUtility.getBitmapFromAsset(getContext(), "worker2.jpg");

        WorkerImage wimage1 = new WorkerImage(0, worker1.getName(), DbBitmapUtility.getBytes(pic_w1));
        WorkerImage wimage2 = new WorkerImage(0, worker2.getName(), DbBitmapUtility.getBytes(pic_w2));
     */

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


