package com.covidtracker.covidtracker.models;

public class LocationStats {
    private String state, country;
    private int latestTotalCases, differenceFromPrevDay;

    // setters
    public void setState(String state)
    {
        this.state = state;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public void setLatestTotalCases(int latestCases) {
        this.latestTotalCases = latestCases;
    }

    public void setDifferenceFromPrevDay(int differenceFromPrevDay) {
        this.differenceFromPrevDay = differenceFromPrevDay;
    }

    // getters
    public String getCountry() {
        return country;
    }

    public int getLatestTotalCases() {
        return latestTotalCases;
    }

    public String getState() {
        return state;
    }

    public int getDifferenceFromPrevDay() {
        return differenceFromPrevDay;
    }

    // toString
    @Override
    public String toString() {
        return "LocationStats{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", latestTotalCases=" + latestTotalCases +
                ", differenceFromPrevDay=" + differenceFromPrevDay +
                '}';
    }
}
