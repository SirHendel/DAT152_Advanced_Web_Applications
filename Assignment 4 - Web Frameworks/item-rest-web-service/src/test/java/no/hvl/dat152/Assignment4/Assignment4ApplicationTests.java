package no.hvl.dat152.Assignment4;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest()
@AutoConfigureMockMvc
class Assignment4ApplicationTests {
	@Autowired
	private MockMvc mvc;

	@Test
	void databaseInitTest() throws Exception {
		mvc.perform(get("/items")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(3)));
	}

	@Test
	void ItemNotFoundTest() throws Exception {
		mvc.perform(get("/items/10100")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
}
