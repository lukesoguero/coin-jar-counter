package com.example.android.coinjarcounter;

/**
 * Created by lukes_000 on 8/3/2016.
 */
public class Jar {

    private String mJarName;
    private long mJarId;
    private double mJarTotal;

    public Jar(String jarName, double jarTotal) {
        mJarName = jarName;
        mJarId = 0;
        mJarTotal = jarTotal;
    }

    public Jar(String jarName, double jarTotal, long jarId) {
        mJarName = jarName;
        mJarTotal = jarTotal;
        mJarId = jarId;
    }

    public String getJarName(){
        return mJarName;
    }

    public long getJarId() {
        return mJarId;
    }

    public double getJarTotal() {
        return mJarTotal;
    }

}
