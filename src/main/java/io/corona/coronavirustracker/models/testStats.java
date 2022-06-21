package io.corona.coronavirustracker.models;

public class testStats {
    private String date;
    private String state;
    private int newCases;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getNewCases() {
        return newCases;
    }

    public void setNewCases(int newCases) {
        this.newCases = newCases;
    }

    @Override
    public String toString() {
        return "testStats{" +
                "date='" + date + '\'' +
                ", state='" + state + '\'' +
                ", newCases=" + newCases +
                '}';
    }
}
