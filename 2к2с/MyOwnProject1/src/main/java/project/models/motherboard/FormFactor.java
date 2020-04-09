package project.models.motherboard;

import lombok.*;
import project.models.MotherBoard;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@ToString(exclude = {"motherBoards"})
public class FormFactor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "formFactor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MotherBoard> motherBoards;
    private Integer height;
    private Integer width;

}

