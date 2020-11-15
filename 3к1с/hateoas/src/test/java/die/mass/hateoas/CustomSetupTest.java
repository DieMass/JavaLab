package die.mass.hateoas;

import die.mass.hateoas.entities.CustomSetup;
import die.mass.hateoas.entities.Status;
import die.mass.hateoas.services.CustomSetupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
public class CustomSetupTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomSetupService customSetupService;

	@BeforeEach
	public void setUp() {
		when(customSetupService.update(1L)).thenReturn(publishedCourse());
	}

	@Test
	public void coursePublishTest() throws Exception {
		mockMvc.perform(put("/customSetups/1/publish")).andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(publishedCourse().getName()))
				.andExpect(jsonPath("$.status").value(publishedCourse().getStatus().name()))
				.andDo(document("publish_customSetup", responseFields(
						fieldWithPath("name").description("Имя сборки"),
						fieldWithPath("status").description("Статус публикации сборки")
				)));
	}

	private CustomSetup publishedCourse() {
		return CustomSetup.builder().id(1L).status(Status.PUBLISH).build();
	}
}
