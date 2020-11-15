package die.mass.hateoas.config;

import die.mass.entities.cpu.Cpu;
import die.mass.hateoas.controllers.CpuController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class CpuRepresentationProcessor implements RepresentationModelProcessor<EntityModel<Cpu>> {

	@Override
	public EntityModel<Cpu> process(EntityModel<Cpu> model) {
		var cpu = model.getContent();
		if (cpu != null && cpu.getSeries().equals("worst")) {
			model.add(linkTo(methodOn(CpuController.class)
					.changeSeries(cpu.getSeries(), "best")).withRel("series"));
		}
		return model;
	}
}

