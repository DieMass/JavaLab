package die.mass.repositories;

import die.mass.models.Message;

import java.util.ArrayList;

public interface MessageRepository extends CrudRepository<Message, Long> {

    ArrayList<Message> pagination(Integer size, Integer page);

}
