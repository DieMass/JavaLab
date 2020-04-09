package project.repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID>  {

    boolean save(T model);
    int update(T model);
    void delete(ID id);
    Optional<T> find(ID id);

    List<T> findAll();
}
