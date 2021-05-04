package com.github.attiand.assertj.jaxrs.json.asserts;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

import org.junit.jupiter.api.Test;

class JsonStructureAssertTest {

	private static final JsonObject JSON_OBJ = Json.createObjectBuilder().add("firstName", "John").build();

	private static final JsonArray JSON_ARRAY = Json.createArrayBuilder().add("one").add("two").add("tree").build();

	@Test
	void shouldAcceptJsonObject() {
		JsonStructureAssert.assertThat(JSON_OBJ).pathValue("/firstName").asString().isEqualTo("John");
	}

	@Test
	void shouldAcceptJsonArray() {
		JsonStructureAssert.assertThat(JSON_ARRAY).pathValue("/0").asString().isEqualTo("one");
		JsonStructureAssert.assertThat(JSON_ARRAY).pathValue("/2").asString().isEqualTo("tree");
	}

	@Test
	void shouldFailOnNonExistingPath() {
		assertThatThrownBy(() -> {
			JsonStructureAssert.assertThat(JSON_OBJ).pathValue("/nonexistent");
		}).isInstanceOf(AssertionError.class).hasMessageContaining("Expected json pointer expression to contain to a value");

	}

	@Test
	void shouldCheckExistingPath() {
		JsonStructureAssert.assertThat(JSON_OBJ).containsPath("/firstName");
	}

	@Test
	void shouldCheckForNonExistingPath() {
		JsonStructureAssert.assertThat(JSON_OBJ).doesNotContainPath("/nonexistent");
	}

}
