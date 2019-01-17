package com.payam.boot.demoApp;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface MovieArchive extends CrudRepository<Movie, Long> {
    List<Movie> findByName(String movieName);
    List<Movie> findByProductionYear(String year);
    List<Movie> findByStarring(String starring);
    List<Movie> findByLanguage(String lang);
}
