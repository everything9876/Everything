package com.everything;

import android.graphics.drawable.shapes.Shape;
import android.support.annotation.Nullable;

import com.everything.fragments.hydrantFragments.OpenCloseExampleFragment;
import com.everything.service.model.User;

/**
 * Created by Mirek on 2016-04-26.
 */
public class FragmentsDataCollector {

    private static FragmentsDataCollector instance;
    private User user;

    private OpenCloseExampleFragment.Rectangle rectangle;
    private OpenCloseExampleFragment.Triangle triangle;
    private OpenCloseExampleFragment.Circle circle;

    private double lastResult;

    private FragmentsDataCollector(){
    }

    public static FragmentsDataCollector getInstance(){
        if(instance == null){
            instance = new FragmentsDataCollector();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(@Nullable User user) {
        this.user = user;
    }

    public double getLastResult() {
        return lastResult;
    }

    public void setLastResult(@Nullable double lastResult) {
        this.lastResult = lastResult;
    }

    public void setRectangle(OpenCloseExampleFragment.Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public void setCircle(OpenCloseExampleFragment.Circle circle) {
        this.circle = circle;
    }

    public void setTriangle(OpenCloseExampleFragment.Triangle triangle) {
        this.triangle = triangle;
    }
}
