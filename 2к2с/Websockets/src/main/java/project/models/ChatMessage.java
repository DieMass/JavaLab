package project.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class ChatMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
//	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private String text;
	private String sender;
//	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private String chat;
	private LocalDateTime dateTime;
}
