package code.thiago.app.interfaces;


import java.util.List;

public interface PersonService<T> {

    T save(T p);

    T findById(String id);

    List<T> findAll();
}
