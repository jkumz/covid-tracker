package com.covidtracker.covidtracker.services;

import com.covidtracker.covidtracker.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;

@Service // tell spring it is a service
public class CoronaVirusDataService {

    // link to csv with relevant info, updated daily
    private static final String VIRUS_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    // list of all stats
    private List<LocationStats> allStats = new ArrayList<LocationStats>();
    public List<LocationStats> getAllStats() { return allStats; }

    // method to get all data
    @PostConstruct // when you construct this instance of this service, execute this method
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException
    {
        List<LocationStats> newStats = new ArrayList<LocationStats>();
        // create new client
        HttpClient client = HttpClient.newHttpClient();

        // create request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_URL))
                .build();

        // create response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // read the response into string format
        StringReader csvBodyReader = new StringReader(response.body());

        // make records iterable
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

        // iterate thru records, updating stats
        for (CSVRecord record : records)
        {
            LocationStats locationStat = new LocationStats();
            locationStat.setState(record.get("Province/State"));
            locationStat.setCountry(record.get("Country/Region"));
            int latestCases = Integer.parseInt(record.get(record.size()-1));
            int prevDayCases = Integer.parseInt(record.get(record.size()-2));
            locationStat.setLatestTotalCases(latestCases);
            locationStat.setDifferenceFromPrevDay(Math.abs(latestCases-prevDayCases));
            newStats.add(locationStat);
        }
        this.allStats = newStats;
    }
}
