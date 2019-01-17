package com.payam.boot.demoApp;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.jaxrs.json.annotation.JSONP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ArchiveController {
    private final AtomicLong counter = new AtomicLong();
    private static final Logger log = LoggerFactory.getLogger(ArchiveController.class);
    @Autowired
    MovieArchive archive;
    static final int MAXPARAMETERLENGTH = 100;
    static final String INVALID = "INVALID";

    private String validate(String param)  {
        String result;
        if (param.length() > MAXPARAMETERLENGTH) {
            result =   INVALID;
        }
        else if (!param.matches("^[A-Za-z0-9,\" ]+$")) {
           result =  INVALID;
        }

        param = param.replaceAll("\\s+"," ");
        param = param.replaceAll("\"","");
        return param;
    }


    @GetMapping(path = "/movie/name/{name}")
    public ResponseEntity<?> name(@PathVariable("name") String name) {
        name = validate(name);

        if (name.equals("INVALID")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> hits = new HashMap();

        try {
            archive.findByName(name).forEach(movie -> {
                hits.put(movie.getName(),movie);
            });
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (hits.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(hits, HttpStatus.OK);
    }


    @GetMapping(path = "/movie/add/{name}/{starring}/{lang}/{year}")
    public ResponseEntity<?> add(@PathVariable("name") String name,
                                 @PathVariable("starring") String starring,
                                 @PathVariable("lang") String lang,
                                 @PathVariable("year") String year) {

        name = validate(name);
        starring = validate(starring);
        lang = validate(lang);
        year = validate(year);

        if (name.equals("INVALID") || starring.equals("INVALID") || lang.equals("INVALID") || year.equals("INVALID")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> found = new HashMap();

        Movie inputMovie = new Movie(name,starring,lang,year);


        archive.findByName(name).forEach(aMovie -> {
            if (aMovie.same(inputMovie)) {
                found.put(aMovie.getName(),aMovie);
            }
        });

        if (!found.isEmpty()) {
            return new ResponseEntity<>("Conflict...",HttpStatus.CONFLICT);
        }

        try {
            archive.save(inputMovie);
            return new ResponseEntity<>("Created...",HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error...",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping(path = "/movie/delete/{name}/{starring}/{lang}/{year}")
    public ResponseEntity<?> delete(@PathVariable("name") String name,
                                 @PathVariable("starring") String starring,
                                 @PathVariable("lang") String lang,
                                 @PathVariable("year") String year) {

        name = validate(name);
        starring = validate(starring);
        lang = validate(lang);
        year = validate(year);

        if (name.equals("INVALID") || starring.equals("INVALID") || lang.equals("INVALID") || year.equals("INVALID")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> found = new HashMap();

        Movie inputMovie = new Movie(name,starring,lang,year);

        archive.findByName(inputMovie.getName()).forEach(movie -> {
            if (movie.same(inputMovie)) {
                found.put(movie.getName(),movie);
            }
        });

        if (found.isEmpty()) {
            return new ResponseEntity<>("Not Found...",HttpStatus.NOT_FOUND);
        }

        try {
            archive.delete(inputMovie);
            return new ResponseEntity<>("Deleted...",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error...",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @RequestMapping(value = "/movie/year/{year}", method = RequestMethod.GET)
    public ResponseEntity<?> year(@PathVariable("year") String year) {
        year = validate(year);

        if (year.equals("INVALID")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> found = new HashMap();

        try {
            archive.findByProductionYear(year).forEach(movie -> {
                found.put(movie.getName(),movie);
            });
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (found.isEmpty()) {
            return new ResponseEntity<>("Not Found...",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(found, HttpStatus.OK);
    }


    @RequestMapping(value = "/movie/starring/{starring}", method = RequestMethod.GET)
    public ResponseEntity<?> starring(@PathVariable("starring") String starring) {
        starring = validate(starring);

        if (starring.equals("INVALID")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> found = new HashMap();
        try {
            archive.findByStarring(starring).forEach(movie -> {
                found.put(movie.getName(),movie);
            });
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (found.isEmpty()) {
            return new ResponseEntity<>("Not Found...",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(found, HttpStatus.OK);
    }


    @RequestMapping(value = "/movie/all", method = RequestMethod.GET)
    public ResponseEntity<?> movie() {
        Map<String, Object> result = new HashMap();
        try {
            archive.findAll().forEach(movie -> {
                result.put(movie.getName(),movie);
            });
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (result.isEmpty()) {
            return new ResponseEntity<>("Not Found...",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
