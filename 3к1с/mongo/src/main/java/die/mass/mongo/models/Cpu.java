package die.mass.mongo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "cpu")
public class Cpu {

	@Id
	private ObjectId _id;
	private String series;
	private Integer cores;
	@DBRef
	private ObjectId socket;
}
