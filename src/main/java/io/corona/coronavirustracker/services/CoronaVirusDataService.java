package io.corona.coronavirustracker.services;

import io.corona.coronavirustracker.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;

@Service // Ak sa spustí program, spustí sa aj táto služba

public class CoronaVirusDataService {
    private static final String VIRUS_DATA_URL = "https://raw.githubusercontent.com/owid/covid-19-data/master/public/data/owid-covid-data.csv";

    private final List<LocationStats> allStats = new ArrayList<>();

    public static void writeToFile(LocationStats in, String date) {
        try (FileWriter writer = new FileWriter("covidData/" + date + ".txt", true)) {
            writer.write(String.valueOf(in));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    @PostConstruct // Po dokončení Service spustí toto
    @Scheduled(cron = "0 0 6 * * *")
    // Naplánovanie updatovania, hviezdicky Sec/Min/Hour/Day/Month/Year, teraz to updatuje kazdy den o 6:00
    public void fetchVirusData() throws IOException, InterruptedException {

        // Potrebujeme stiahnúť dáta-pomocou HTTP klientu
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requestData = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();
        HttpResponse<String> httpResponseData = client.send(requestData, BodyHandlers.ofString()); // Tu je response od webu-stiahnuté dokumentu
        StringReader csvBodyReader = new StringReader(httpResponseData.body());
        Iterable<CSVRecord> zaznamy = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader); // AUTO DETECTION HEADERS CSV čítačka, keďže prvý riadok CSV má hlavičky

        List<String> headers = getHeaders(new StringReader(httpResponseData.body())); //Return string of headers

/*        FileUtils.cleanDirectory(new File("covidData"));*/

        int sk = 0;

        for (CSVRecord zaznam : zaznamy) {
            while(!zaznam.get("date").equals("2020-09-27")){
                if(zaznam.get("location").equals("Slovakia")){
                    System.out.println("1");
                }
            }

            LocationStats locationStats = new LocationStats();
            locationStats.setDate(zaznam.get("date"));
            locationStats.setLocation(zaznam.get("location"));
            locationStats.setTotalCases(zaznam.get("total_cases"));
            locationStats.setNewCases(zaznam.get("new_cases"));
            locationStats.setTotalDeaths(zaznam.get("total_deaths"));
            locationStats.setNewDeaths(zaznam.get("new_deaths"));
            locationStats.setNewTests(zaznam.get("new_tests"));

/*            writeToFile(locationStats, locationStats.getDate());*/
        }

    }

    public List<String> getHeaders(StringReader in) throws IOException {
        CSVParser parser = CSVParser.parse(in, CSVFormat.DEFAULT.withFirstRecordAsHeader());
        return parser.getHeaderNames();
    }
}