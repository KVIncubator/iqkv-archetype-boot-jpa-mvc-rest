import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;{package}.repository.FruitRepository;

@WebMvcTest(value = FruitResource.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class FruitResourceTest {
  @MockBean
  private FruitRepository fruitRepository;

  private final MockMvc mockMvc;

  private final ObjectMapper objectMapper;

  @Autowired
  public FruitResourceTest(final MockMvc mockMvc, final ObjectMapper objectMapper) {
    this.mockMvc = mockMvc;
    this.objectMapper = objectMapper;
  }

  @Test
  void shouldCreateFruit() throws Exception {
    final Fruit fruit = new Fruit(1L, "Banana", true);

    mockMvc.perform(post("/api/v1/fruits").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(fruit)))
        .andExpect(status().isCreated())
        .andDo(print());
  }

  @Test
  void shouldReturnFruit() throws Exception {
    long id = 1L;
    final Fruit fruit = new Fruit(id, "Orange", true);

    when(fruitRepository.findById(id)).thenReturn(Optional.of(fruit));
    mockMvc.perform(get("/api/v1/fruits/{id}", id)).andExpect(status().isOk())
        .andExpect(jsonPath("${symbol_dollar}.id").value(id))
        .andExpect(jsonPath("${symbol_dollar}.name").value(fruit.getName()))
        .andExpect(jsonPath("${symbol_dollar}.available").value(fruit.isAvailable()))
        .andDo(print());
  }

  @Test
  void shouldReturnNotFoundFruit() throws Exception {
    final long id = 1L;

    when(fruitRepository.findById(id)).thenReturn(Optional.empty());
    mockMvc.perform(get("/api/v1/fruits/{id}", id))
        .andExpect(status().isNotFound())
        .andDo(print());
  }

  @Test
  void shouldReturnListOfFruits() throws Exception {
    final List<Fruit> fruits = new ArrayList<>(
        Arrays.asList(new Fruit(1L, "Banana 1", true),
            new Fruit(2L, "Banana 2", true),
            new Fruit(3L, "Banana 3", true)));

    when(fruitRepository.findAll()).thenReturn(fruits);
    mockMvc.perform(get("/api/v1/fruits"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("${symbol_dollar}.size()").value(fruits.size()))
        .andDo(print());
  }

  @Test
  void shouldReturnListOfFruitsWithFilter() throws Exception {
    final List<Fruit> fruits = new ArrayList<>(
        Arrays.asList(new Fruit(1L, "Banana example", true),
            new Fruit(3L, "Kiwi example", true)));

    final String name = "example";
    final MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
    paramsMap.add("name", name);

    when(fruitRepository.findByNameContaining(name)).thenReturn(fruits);
    mockMvc.perform(get("/api/v1/fruits").params(paramsMap))
        .andExpect(status().isOk())
        .andExpect(jsonPath("${symbol_dollar}.size()").value(fruits.size()))
        .andDo(print());
  }

  @Test
  void shouldReturnNoContentWhenFilter() throws Exception {
    final String name = "Kiwi";
    final MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
    paramsMap.add("name", name);

    final List<Fruit> fruits = Collections.emptyList();

    when(fruitRepository.findByName(name)).thenReturn(fruits);
    mockMvc.perform(get("/api/v1/fruits").params(paramsMap))
        .andExpect(status().isNoContent())
        .andDo(print());
  }

  @Test
  void shouldUpdateFruit() throws Exception {
    final long id = 1L;

    final Fruit fruit = new Fruit(id, "Banana", false);
    final Fruit updatedFruit = new Fruit(id, "Orange", true);

    when(fruitRepository.findById(id)).thenReturn(Optional.of(fruit));
    when(fruitRepository.save(any(Fruit.class))).thenReturn(updatedFruit);

    mockMvc.perform(put("/api/v1/fruits/{id}", id).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedFruit)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("${symbol_dollar}.name").value(updatedFruit.getName()))
        .andExpect(jsonPath("${symbol_dollar}.available").value(updatedFruit.isAvailable()))
        .andDo(print());
  }

  @Test
  void shouldReturnNotFoundUpdateFruit() throws Exception {
    final long id = 1L;

    final Fruit updatedFruit = new Fruit(id, "Orange", true);

    when(fruitRepository.findById(id)).thenReturn(Optional.empty());
    when(fruitRepository.save(any(Fruit.class))).thenReturn(updatedFruit);

    mockMvc.perform(put("/api/v1/fruits/{id}", id).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedFruit)))
        .andExpect(status().isNotFound())
        .andDo(print());
  }

  @Test
  void shouldDeleteFruit() throws Exception {
    final long id = 1L;

    doNothing().when(fruitRepository).deleteById(id);
    mockMvc.perform(delete("/api/v1/fruits/{id}", id))
        .andExpect(status().isNoContent())
        .andDo(print());
  }

  @Test
  void shouldDeleteAllFruits() throws Exception {
    doNothing().when(fruitRepository).deleteAll();
    mockMvc.perform(delete("/api/v1/fruits"))
        .andExpect(status().isNoContent())
        .andDo(print());
  }
}
