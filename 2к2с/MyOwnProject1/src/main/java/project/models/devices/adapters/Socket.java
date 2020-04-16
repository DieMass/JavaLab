package project.models.devices.adapters;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import project.models.devices.cpu.Cpu;
import project.models.MotherBoard;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table
@ToString(exclude = {"cpus", "motherBoards"})
public class Socket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "socket", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cpu> cpus;
    @JsonIgnore
    @OneToMany(mappedBy = "socket", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MotherBoard> motherBoards;

}
