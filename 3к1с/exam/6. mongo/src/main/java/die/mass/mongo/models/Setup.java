package die.mass.mongo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "setup")
public class Setup {

	@Id
	private String _id;
	private String name;
	@DBRef
	private Customer owner;
	@DBRef
	private Cpu cpu;
	@DBRef
	private MotherBoard motherBoard;
}
