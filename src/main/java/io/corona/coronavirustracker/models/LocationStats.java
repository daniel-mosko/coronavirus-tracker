package io.corona.coronavirustracker.models;

public class LocationStats {

    private String date;
    private String location;
    private int totalCases;
    private int newCases;
    private int totalDeaths;
    private int newDeaths;
    private int newTests;

    @Override
    public String toString() {
        return location + ',' + totalCases + ',' + newCases + ',' + totalDeaths
                + ',' + newDeaths + ',' + newTests + '\n';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(String totalCases) {
        if (!totalCases.isEmpty()) {
            this.totalCases = (int) Float.parseFloat(totalCases);
        }
    }

    public int getNewCases() {
        return newCases;
    }

    public void setNewCases(String newCases) {
        if (!newCases.isEmpty()) {
            this.newCases = (int) Float.parseFloat(newCases);
        }
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(String totalDeaths) {
        if (!totalDeaths.isEmpty()) {
            this.totalDeaths = (int) Float.parseFloat(totalDeaths);
        }
    }

    public int getNewDeaths() {
        return newDeaths;
    }

    public void setNewDeaths(String newDeaths) {
        if (!newDeaths.isEmpty()) {
            this.newDeaths = (int) Float.parseFloat(newDeaths);
        }
    }

    public int getNewTests() {
        return newTests;
    }

    public void setNewTests(String newTests) {
        if (!newTests.isEmpty()) {
            this.newTests = (int) Float.parseFloat(newTests);
        }
    }
}