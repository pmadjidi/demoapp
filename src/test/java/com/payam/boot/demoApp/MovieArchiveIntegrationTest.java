package com.payam.boot.demoApp;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MovieArchiveIntegrationTest {

    private static final Logger log = LoggerFactory.getLogger(MovieArchiveIntegrationTest.class);
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MovieArchive archive;


    @Test
    public void compareMovieToMovie() {
        Movie aMovie = new Movie("Dirty Harry Test One", "Clint Eastwood","English","1971");
        Movie anOtherMovie =  new Movie("Dirty Harry Test One", "Clint Eastwood","English","1971");
        assert(aMovie.same(anOtherMovie));
    }

    @Test
    public void findNameThenReturnMovie() {
        // first save
        Movie aMovie = new Movie("Dirty Harry Test", "Clint Eastwood","English","1971");
        entityManager.persist(aMovie);
        entityManager.flush();

        // when retrive
        Movie found = archive.findByName(aMovie.getName()).get(0);

        // and assert compare
        assert(found.equals(aMovie));
        entityManager.remove(aMovie);
    }

}
