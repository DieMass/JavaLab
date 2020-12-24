package die.mass.mongo.jpa.controllers;

import com.querydsl.core.types.Predicate;
import die.mass.mongo.jpa.repositories.CpuRepository;
import die.mass.mongo.jpa.repositories.SocketRepository;
import die.mass.mongo.models.Cpu;
import die.mass.mongo.models.CpuDto;
import die.mass.mongo.models.Socket;
import lombok.RequiredArgsConstructor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequiredArgsConstructor
public class SearchController {

	private final CpuRepository cpuRepository;
	private final SocketRepository socketRepository;

	@GetMapping("/cpu/search")
	public ResponseEntity<List<CpuDto>> searchByPredicate(@QuerydslPredicate(root = Cpu.class) Predicate predicate) {
		return ResponseEntity.ok(StreamSupport.stream(cpuRepository.findAll(predicate).spliterator(), true)
				.map(cpu ->
						CpuDto.builder()
								.cores(cpu.getCores())
								.series(cpu.getSeries())
								.socketName(socketRepository.findById(cpu.getSocket()).map(Socket::getName).orElse(null))
								.build()).collect(Collectors.toList()));
	}
}
