package me.cirq.sencons.activity;

public class SensorItem {

    private String name;
    private int lightImageId;
    private int darkImageId;
    private boolean light;

    public SensorItem(String name, int lightImageId, int darkImageId) {
        this.name = name.toUpperCase();
        this.lightImageId = lightImageId;
        this.darkImageId = darkImageId;
        this.light = true;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return light ? lightImageId : darkImageId;
    }

    public void flipIcon(){
        this.light ^= true;
    }


}
