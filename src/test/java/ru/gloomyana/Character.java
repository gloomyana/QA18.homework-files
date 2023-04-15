package ru.gloomyana;

public class Character {
    public String name;
    public String race;

    public classAndSpec classAndSpec;
    public static class classAndSpec {
        public String className;
        public String specName;
    }

    public String[] abilities;
    public boolean isActive;
    public Integer killsCount;
    public Integer deathsCount;
    public Integer completedQuestsCount;
}
