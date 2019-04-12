package me.cirq.sencons.activity;

public class SensorItem {

    private String name;
    private int lightImageId;
    private int darkImageId;

    public SensorItem(String name, int lightImageId, int darkImageId) {
        this.name = name.toUpperCase();
        this.lightImageId = lightImageId;
        this.darkImageId = darkImageId;
    }

    public String getName() {
        return name;
    }

    public int getLightImageId() {
        return lightImageId;
    }

    public int getDarkImageId() {
        return darkImageId;
    }

    public int getImageId() {
        return darkImageId;
    }
}
