package me.cirq.sencons.activity;

public class SensorItem {

    private String name;
    private int lightImageId;
    private int darkImageId;
    private boolean light;
    private float[] values;
    private String[] dim;
    private String unit;
    private String fmt;

    public SensorItem(String name, int lightImageId, int darkImageId, String[] dim, String unit, int round) {
        this.name = name.toUpperCase();
        this.lightImageId = lightImageId;
        this.darkImageId = darkImageId;
        this.light = true;
        this.values = new float[]{1, 1, 1};
        this.dim = dim;
        this.unit = unit;
        this.fmt = String.format("%%s: %%.%df %%s", round);
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

    public void setValues(float[] values){
        System.arraycopy(values, 0, this.values, 0, values.length);
    }

    public String getValue(int i){
        if(values[i] < 0)
            return "";
        return String.format(fmt, dim[i], values[i], unit);
    }

}
