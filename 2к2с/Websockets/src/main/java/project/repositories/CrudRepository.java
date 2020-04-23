package project.repositories;

public interface CrudRepository<T, ID> {

	boolean save(T model);
	T find(ID id);
	T update(ID id, T model);
	boolean remove(ID id);

}
