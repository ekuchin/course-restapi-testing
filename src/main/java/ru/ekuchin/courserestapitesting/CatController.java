package ru.ekuchin.courserestapitesting;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.ekuchin.courserestapitesting.entity.Cat;
import ru.ekuchin.courserestapitesting.entity.CatNotFoundException;
import ru.ekuchin.courserestapitesting.entity.CatRepository;

import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class CatController {

    private CatRepository repository;

    public CatController(CatRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/cats")
    public List<Cat> getAll(@RequestHeader(value="Authorization") String token){
        System.out.println(token);
        return repository.findAll();
    }

    @GetMapping("/cat/{id}")
    public Cat getOne(@PathVariable Long id){
        return repository.findById(id).orElseThrow(() -> new CatNotFoundException(id));
    }

    @PostMapping("/cat")
    public Cat add(@RequestBody Cat newCat){
       return repository.save(newCat);
    }

    @PostMapping("/cat/{id}")
    public Cat replace(@RequestBody Cat newCat, @PathVariable Long id){
        newCat.setId(id);
        return repository.save(newCat);
    }

    @DeleteMapping("/cat/{id}")
    public void delete(@PathVariable Long id){
        repository.deleteById(id);
    }

    public boolean checkAuth(String token){
        return true;
    }
}
