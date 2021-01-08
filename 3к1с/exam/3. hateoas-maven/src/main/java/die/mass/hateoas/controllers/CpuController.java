package die.mass.hateoas.controllers;

import die.mass.hateoas.services.CpuService;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RepositoryRestController
@AllArgsConstructor
public class CpuController {
	private final CpuService cpuService;
	@RequestMapping(value = "/cpus/{cpu-series-before}/changeSeries/{cpu-series-after}", method = RequestMethod.PUT)
	public @ResponseBody
	ResponseEntity<?> changeSeries(@PathVariable("cpu-series-before") String cpuSeriesBefore,
								   @PathVariable("cpu-series-after") String cpuSeriesAfter) {
		return ResponseEntity.ok(EntityModel.of(cpuService.changeSeries(cpuSeriesBefore, cpuSeriesAfter)));
	}
}
