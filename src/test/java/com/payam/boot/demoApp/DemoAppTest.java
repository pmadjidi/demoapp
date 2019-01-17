package com.payam.boot.demoApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoAppTest{

    private static final Logger log = LoggerFactory.getLogger(DemoAppTest.class);
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    MovieArchive archive;




    @Test
    public void shouldHaveMoreThenFiveMovies() throws Exception {
        archive.save(new Movie("Dirty Harry Test", "Clint Eastwood","English","1971"));
        archive.save(new Movie("Unforgiven Test", "Clint Eastwood","English","1992"));
        archive.save(new Movie("The Good, The Bad and the Ugly Test", "Clint Eastwood","English","1966"));
        archive.save(new Movie("The Good, The Bad and the Ugly Test", "Lee Van Cleff","English","1966"));
        archive.save(new Movie("Kellys Heros Test", "Clint Eastwood","English","1970"));
        archive.save(new Movie("Thunderbolt and Lightfoot Test", "Clint Eastwood","English","1974"));
        archive.save(new Movie("High Plains Drifter Test", "Clint Eastwood","English","1973"));

        String URL = "http://localhost:" + port + "/movie/all";
        System.out.print(URL);
        String result =  this.restTemplate.getForObject(URL, String.class).toString();
        System.out.print(result);
        HashMap<String,Object> map =
                new ObjectMapper().readValue(result, HashMap.class);
        assert(map.size() >=  7);
        assert(map.containsKey("Dirty Harry Test"));
        assert(map.containsKey("Unforgiven Test"));
        assert(map.containsKey("The Good, The Bad and the Ugly Test"));
        assert(map.containsKey("Kellys Heros Test"));
        assert(map.containsKey("Thunderbolt and Lightfoot Test"));
        assert(map.containsKey("High Plains Drifter Test"));
    }





    @Test
    public void AddAMovieSucess() throws Exception {
        String URL = "http://localhost:" + port + "/movie/add/One more RestAPI demo/payam/English/2019";
        System.out.print(URL);
        ResponseEntity<String> response = restTemplate.getForEntity(URL, String.class);
        assert(response.getBody().toString().equals("Created..."));
        log.info(response.getStatusCode().toString());
    }

    @Test
    public void AddAMovieFail() throws Exception {
        String URL = "http://localhost:" + port + "/movie/add//payam/English/2019";
        System.out.print(URL);
        ResponseEntity<String> response = restTemplate.getForEntity(URL, String.class);
        assert(response.getBody().toString().indexOf("404") > 0);
        log.info(response.getStatusCode().toString());
    }

    @Test
    public void AddAMovieFailsAgain() throws Exception {
        String URL = "http://localhost:" + port + "/movie/add///payam/English/2019";
        System.out.print(URL);
        ResponseEntity<String> response = restTemplate.getForEntity(URL, String.class);
        assert(response.getBody().toString().indexOf("404") > 0);
        log.info(response.getStatusCode().toString());
    }

    @Test
    public void DeleteAMovieSuccess() throws Exception {
        archive.save(new Movie("Testing again", "Payam Madjidi","Swedish","2019"));
        String URL = "http://localhost:" + port + "/movie/delete/Testing again/Payam Madjidi/Swedish/2019";
        System.out.print(URL);
        ResponseEntity<String> response = restTemplate.getForEntity(URL, String.class);
        assert(response.getBody().toString().equals("Deleted..."));
        log.info(response.getStatusCode().toString());
    }

    @Test
    public void DeleteAMovieFails() throws Exception {

        String URL = "http://localhost:" + port + "/movie/delete/Testing no existant movie/Payam Madjidi/Swedish/2019";
        System.out.print(URL);
        ResponseEntity<String> response = restTemplate.getForEntity(URL, String.class);
        assert(response.getBody().toString().equals("Not Found..."));
        log.info(response.getStatusCode().toString());
    }



    @Test
    public void getMovieByName() throws Exception {
        archive.save(new Movie("Dirty Harry Test2", "Clint Eastwood","English","1971"));
        String movieName = "Dirty Harry Test2";
        String URL = "http://localhost:" + port + "/movie/name/" + movieName;
        System.out.print(URL);
        String result =  this.restTemplate.getForObject(URL, String.class).toString();
        System.out.print(result);
        HashMap<String,Object> map =
                new ObjectMapper().readValue(result, HashMap.class);
        assert(map.containsKey(movieName));
    }

    @Test
    public void getMovieByStarring() throws Exception {
        archive.save(new Movie("dodo", "doda doda","English","1971"));
        String starring = "doda doda";
        String URL = "http://localhost:" + port + "/movie/starring/" + starring;
        System.out.print(URL);
        String result =  this.restTemplate.getForObject(URL, String.class).toString();
        System.out.print(result);
        HashMap<String,Object> map =
                new ObjectMapper().readValue(result, HashMap.class);
        assert(map.containsKey("dodo"));
    }

    @Test
    public void getMovieByYear() throws Exception {
        archive.save(new Movie("in the year 2525", "doda doda","English","2525"));
        String year = "2525";
        String URL = "http://localhost:" + port + "/movie/year/" + year;
        System.out.print(URL);
        String result =  this.restTemplate.getForObject(URL, String.class).toString();
        System.out.print(result);
        HashMap<String,Object> map =
                new ObjectMapper().readValue(result, HashMap.class);
        assert(map.containsKey("in the year 2525"));
    }


    @Test
    public void yearDoesNotExist() throws Exception {
        String year = "3535";
        String URL = "http://localhost:" + port + "/movie/year/" + year;
        System.out.print(URL);
        ResponseEntity<String> response = restTemplate.getForEntity(URL, String.class);
        assert (response.getBody().toString().equals("Not Found..."));
    }

}




