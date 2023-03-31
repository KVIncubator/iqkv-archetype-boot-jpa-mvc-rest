#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ${package}.entity.Fruit;

@SpringBootTest
@AutoConfigureMockMvc
record FruitResourceTest(@Autowired MockMvc mvc) {

  @Test
  @SneakyThrows
  void shouldCreateFruit() {
    final var fruit = new Fruit();
    fruit.setName("Mango");

    var responseBody = createNewEntity(fruit);
    assertEntityEquals(fruit, responseBody);
    deleteEntity(responseBody.getId());

    responseBody = createNewEntity(fruit);
    assertEntityEquals(fruit, responseBody);
    deleteEntity(responseBody.getId());
  }

  @Test
  @SneakyThrows
  void shouldFindFruitById() {
    final var gson = new Gson();
    final var fruit = new Fruit();
    fruit.setName("Kiwi");

    var responseBody = createNewEntity(fruit);

    MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/api/v1/fruits/" + responseBody.getId())
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();
    responseBody = gson.fromJson(result.getResponse().getContentAsString(), Fruit.class);

    assertEntityEquals(fruit, responseBody);
    deleteEntity(responseBody.getId());
  }

  @Test
  @SneakyThrows
  void shouldFindAllFruits() {
    final var fruit = new Fruit();

    final var numberOfRecords = 3;
    final var fruits = new String[] {"Apple", "Banana", "Orange"};
    final var created = new ArrayList<Fruit>();
    for (int i = 0; i < numberOfRecords; i++) {
      fruit.setName(fruits[i]);
      created.add(createNewEntity(fruit));
    }

    mvc.perform(MockMvcRequestBuilders.get("/api/v1/fruits")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(
            """
                {
                   "content":[
                      {
                         "id":1001,
                         "name":"Apple"
                      },
                      {
                         "id":1002,
                         "name":"Banana"
                      },
                      {
                         "id":1003,
                         "name":"Orange"
                      }
                   ],
                   "pageable":{
                      "sort":{
                         "empty":true,
                         "sorted":false,
                         "unsorted":true
                      },
                      "offset":0,
                      "pageNumber":0,
                      "pageSize":10,
                      "paged":true,
                      "unpaged":false
                   },
                   "last":true,
                   "totalPages":1,
                   "totalElements":3,
                   "size":10,
                   "number":0,
                   "sort":{
                      "empty":true,
                      "sorted":false,
                      "unsorted":true
                   },
                   "first":true,
                   "numberOfElements":3,
                   "empty":false
                }
                """
        ));
    for (Fruit singleEntity : created) {
      deleteEntity(singleEntity.getId());
    }
  }

  @Test
  @SneakyThrows
  void shouldUpdateFruit() {
    final var fruit = new Fruit();
    fruit.setName("Lemon");

    var responseBody = createNewEntity(fruit);

    responseBody = getEntityById(responseBody.getId(), status().isOk());
    responseBody.setName("Avocado");
    responseBody = updateEntity(responseBody);
    fruit.setName("Avocado");

    assertEntityEquals(fruit, responseBody);
    deleteEntity(responseBody.getId());
  }

  @Test
  void shouldDeleteFruit() {
    final var fruit = new Fruit();
    fruit.setName("Banana");

    var responseBody = createNewEntity(fruit);

    deleteEntity(responseBody.getId());
    getEntityById(responseBody.getId(), status().isNotFound());
  }

  @SneakyThrows
  private Fruit createNewEntity(final Fruit fruit) {
    final var gson = new Gson();

    MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/api/v1/fruits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(fruit))
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andReturn();

    return gson.fromJson(result.getResponse().getContentAsString(), Fruit.class);
  }

  @SneakyThrows
  private Fruit updateEntity(final Fruit fruit) {
    final var gson = new Gson();
    final var id = fruit.getId();
    MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/api/v1/fruits/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(fruit))
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();

    return gson.fromJson(result.getResponse().getContentAsString(), Fruit.class);
  }

  @SneakyThrows
  private Fruit getEntityById(final Long id, final ResultMatcher matcher) {
    final var gson = new Gson();

    final var result = mvc.perform(MockMvcRequestBuilders.get("/api/v1/fruits/" + id)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(matcher)
        .andReturn();

    return gson.fromJson(result.getResponse().getContentAsString(), Fruit.class);
  }

  @SneakyThrows
  private void deleteEntity(final Long id) {
    mvc.perform(MockMvcRequestBuilders.delete("/api/v1/fruits/" + id)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();
  }

  private void assertEntityEquals(final Fruit fruit, final Fruit responseBody) {
    assertNotNull(responseBody);
    assertNotNull(responseBody.getId());
    assertEquals(responseBody.getName(), fruit.getName());
  }

}
