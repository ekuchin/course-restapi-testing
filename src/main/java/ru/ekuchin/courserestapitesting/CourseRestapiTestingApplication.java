package ru.ekuchin.courserestapitesting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ekuchin.courserestapitesting.entity.Cat;
import ru.ekuchin.courserestapitesting.entity.CatRepository;

@SpringBootApplication
public class CourseRestapiTestingApplication implements CommandLineRunner {

	@Autowired
	CatRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(CourseRestapiTestingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		repository.save(new Cat(1L, "Мурзик", "Манул", 12));
		repository.save(new Cat(2L, "Рамзес", "Сфинкс", 2));
		repository.save(new Cat(3L, "Эдуард", "Британец", 5));
		repository.findAll().forEach(System.out::println);

	}
}
