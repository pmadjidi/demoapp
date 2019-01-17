package com.payam.boot.demoApp;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.zalando.logbook.Logbook;


@SpringBootApplication
public class DemoApp {

	private static final Logger log = LoggerFactory.getLogger(DemoApp.class);
	Logbook logbook = Logbook.create();
	public static void main(String[] args)
	{
		SpringApplication.run(DemoApp.class, args);
	}

	@Bean
	public CommandLineRunner demo(MovieArchive archive) {
		return (args) -> {
			// Some movies in to database
			archive.save(new Movie("Dirty Harry", "Clint Eastwood","English","1971"));
			archive.save(new Movie("Unforgiven", "Clint Eastwood","English","1992"));
			archive.save(new Movie("The Good, The Bad and the Ugly", "Clint Eastwood","English","1966"));
			archive.save(new Movie("The Good, The Bad and the Ugly", "Lee Van Cleff","English","1966"));
			archive.save(new Movie("Kellys Heros", "Clint Eastwood","English","1970"));
			archive.save(new Movie("Thunderbolt and Lightfoot", "Clint Eastwood","English","1974"));
			archive.save(new Movie("High Plains Drifter", "Clint Eastwood","English","1973"));



			for (Movie movie : archive.findAll()) {
				log.info(movie.toString());
			}

			log.info("****");

			// fetch an individual customer by ID
			archive.findById(1L).ifPresent(movie -> {
						log.info("Test to find a movie with id....");
						log.info("--------------------------------");
						log.info(movie.toString());
						log.info("");
					});

			// fetch customers by last name
			log.info("Test to find a movie with name");
			log.info("--------------------------------------------");
			archive.findByName("Kellys Heros").forEach(movie -> {
				log.info(movie.toString());
			});

			log.info("****");
		};
	}

}

