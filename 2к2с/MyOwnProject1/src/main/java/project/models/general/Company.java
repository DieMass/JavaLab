package project.models.general;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table
//@ToString(exclude = {"cpus", "gpus", "motherBoards"})
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
//    @JsonIgnore
//    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Cpu> cpus;
//    @JsonIgnore
//    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Gpu> gpus;
//    @JsonIgnore
//    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<MotherBoard> motherBoards;

}
