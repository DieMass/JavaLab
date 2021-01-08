package die.mass.hateoas;

import die.mass.entities.cpu.Cpu;
import die.mass.hateoas.services.CpuService;
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

import java.time.LocalDate;

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
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class CpuTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CpuService cpuService;

	@BeforeEach
	public void setUp() {
		when(cpuService.changeSeries("worst", "best")).thenReturn(currentCpu());
	}

	@Test
	public void changeSeriesTest() throws Exception {
		mockMvc.perform(put("/cpus/worst/changeSeries/best")).andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.series").value(currentCpu().getSeries()))
				.andExpect(jsonPath("$.launchDate").value(currentCpu().getDate()))
				.andDo(document("changeSeries_cpu", responseFields(
						fieldWithPath("series").description("Серия процессора"),
						fieldWithPath("launchDate").description("Дата назначения серии"),
						fieldWithPath("cores").description(""),
						fieldWithPath("threads").description(""),
						fieldWithPath("l1").description(""),
						fieldWithPath("l2").description(""),
						fieldWithPath("l3").description(""),
						fieldWithPath("tdp").description(""),
						fieldWithPath("eccSupported").description(""),
						fieldWithPath("unlock").description(""),
						fieldWithPath("maxTempCristal").description(""),
						fieldWithPath("maxTempCase").description(""),
						fieldWithPath("baseClock").description(""),
						fieldWithPath("maxClockSingleCore").description(""),
						fieldWithPath("maxMemorySize").description(""),
						fieldWithPath("maxMemoryBandwidth").description(""),
						fieldWithPath("codeName").description(""),
						fieldWithPath("line").description(""),
						fieldWithPath("platform").description(""),
						fieldWithPath("date").description(""),
						fieldWithPath("memoryChannelCount").description(""),
						fieldWithPath("opnTray").description(""),
						fieldWithPath("link").description(""),
						fieldWithPath("ramConfigs").description("")
				)));
	}

	private Cpu currentCpu() {
		return Cpu.builder().series("worst").date(LocalDate.now()).build();
	}


}
