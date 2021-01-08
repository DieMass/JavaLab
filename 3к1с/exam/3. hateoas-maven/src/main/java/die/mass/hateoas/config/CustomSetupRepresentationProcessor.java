//package die.mass.hateoas.config;
//
//import die.mass.hateoas.controllers.CustomSetupController;
//import die.mass.hateoas.entities.CustomSetup;
//import die.mass.hateoas.entities.Status;
//import lombok.AllArgsConstructor;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.hateoas.server.RepresentationModelProcessor;
//import org.springframework.stereotype.Component;
//
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
//
//@Component
//@AllArgsConstructor
//public class CustomSetupRepresentationProcessor implements RepresentationModelProcessor<EntityModel<CustomSetup>> {
//
//	@Override
//	public EntityModel<CustomSetup> process(EntityModel<CustomSetup> model) {
//		CustomSetup customSetup = model.getContent();
//		if (customSetup != null && customSetup.getStatus().equals(Status.DRAFT)) {
//			model.add(linkTo(
//					methodOn(CustomSetupController.class)
//					.publish(customSetup.getId())).withRel("publish"));
//		}
//		return model;
//	}
//}
//
//
