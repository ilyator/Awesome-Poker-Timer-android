package com.ily.awesomepokertimer.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ily on 20.10.2016.
 */

public class Tournament extends RealmObject {

    @PrimaryKey
    private int id;
    private String name;
    private RealmList<Level> levels;
    private boolean isActive;
    private boolean isRunning;
    private Level currentLevel;
    private long currentLevelTime;

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

    public RealmList<Level> getLevels() {
        return levels;
    }

    public void setLevels(RealmList<Level> levels) {
        this.levels = levels;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    public long getCurrentLevelTime() {
        return currentLevelTime;
    }

    public void setCurrentLevelTime(long currentLevelTime) {
        this.currentLevelTime = currentLevelTime;
    }
}