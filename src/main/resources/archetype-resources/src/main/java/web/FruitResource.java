#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.iqkv.boot.web.rest.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ${package}.entity.Fruit;
import ${package}.repository.FruitRepository;

@RestController
@Tag(name = "Fruits", description = "API endpoints for managing fruit entity.")
@Validated
@RequestMapping("/api/v1/fruits")
@RequiredArgsConstructor
class FruitResource {

  private final FruitRepository fruitRepository;

  @PostMapping
  @Operation(
      description = "Create a new fruit.",
      responses = {
          @ApiResponse(responseCode = "201",
                       description = "Created"),
          @ApiResponse(responseCode = "500",
                       description = "Internal error",
                       content = @Content(schema = @Schema(implementation = ApiError.class))),
          @ApiResponse(responseCode = "400",
                       description = "Bad request",
                       content = @Content(schema = @Schema(implementation = ApiError.class))),
      })
  ResponseEntity<Fruit> create(@RequestBody final FruitRequest fruitRequest) {
    final Fruit fruit = fruitRepository.save(new Fruit(fruitRequest.name(), fruitRequest.available()));
    return new ResponseEntity<>(fruit, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  @Operation(
      description = "Retrieve fruit by id.",
      responses = {
          @ApiResponse(responseCode = "200",
                       description = "Success"),
          @ApiResponse(responseCode = "500",
                       description = "Internal error",
                       content = @Content(schema = @Schema(implementation = ApiError.class))),
          @ApiResponse(responseCode = "400",
                       description = "Bad request",
                       content = @Content(schema = @Schema(implementation = ApiError.class))),
          @ApiResponse(responseCode = "404",
                       description = "Not found",
                       content = @Content(schema = @Schema(implementation = ApiError.class)))
      })
  ResponseEntity<Fruit> findById(@PathVariable("id") final long id) {
    Optional<Fruit> fruitData = fruitRepository.findById(id);
    return fruitData.map(fruit -> new ResponseEntity<>(fruit, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @GetMapping
  @Operation(
      description = "Get all the fruits.",
      responses = {
          @ApiResponse(responseCode = "200",
                       description = "Success"),
          @ApiResponse(responseCode = "500",
                       description = "Internal error",
                       content = @Content(schema = @Schema(implementation = ApiError.class))),
          @ApiResponse(responseCode = "400",
                       description = "Bad request",
                       content = @Content(schema = @Schema(implementation = ApiError.class))),
      })
  ResponseEntity<List<Fruit>> findAll(@RequestParam(required = false) final String name) {
    final List<Fruit> fruits = new ArrayList<>();

    if (name == null) {
      fruits.addAll(fruitRepository.findAll());
    } else {
      fruits.addAll(fruitRepository.findByNameContaining(name));
    }

    if (fruits.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(fruits, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  @Operation(
      description = "Updates an existing fruit.",
      responses = {
          @ApiResponse(responseCode = "200",
                       description = "Success"),
          @ApiResponse(responseCode = "500",
                       description = "Internal error",
                       content = @Content(schema = @Schema(implementation = ApiError.class))),
          @ApiResponse(responseCode = "400",
                       description = "Bad request",
                       content = @Content(schema = @Schema(implementation = ApiError.class))),
      })
  ResponseEntity<Fruit> update(@PathVariable("id") final long id, @RequestBody final FruitRequest fruitRequest) {
    Optional<Fruit> fruitData = fruitRepository.findById(id);

    if (fruitData.isPresent()) {
      final Fruit fruit = fruitData.get();
      fruit.setName(fruitRequest.name());
      fruit.setAvailable(fruitRequest.available());
      return new ResponseEntity<>(fruitRepository.save(fruit), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  ResponseEntity<HttpStatus> delete(@PathVariable("id") final long id) {
    fruitRepository.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);

  }

  @DeleteMapping
  @Operation(
      description = "Delete all fruits.",
      responses = {
          @ApiResponse(responseCode = "204",
                       description = "No Content"),
          @ApiResponse(responseCode = "500",
                       description = "Internal error",
                       content = @Content(schema = @Schema(implementation = ApiError.class))),
          @ApiResponse(responseCode = "400",
                       description = "Bad request",
                       content = @Content(schema = @Schema(implementation = ApiError.class))),
      })
  ResponseEntity<HttpStatus> deleteAll() {
    fruitRepository.deleteAll();
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/available")
  @Operation(
      description = "Get all available profiles.",
      responses = {
          @ApiResponse(responseCode = "200",
                       description = "Success"),
          @ApiResponse(responseCode = "500",
                       description = "Internal error",
                       content = @Content(schema = @Schema(implementation = ApiError.class))),
          @ApiResponse(responseCode = "400",
                       description = "Bad request",
                       content = @Content(schema = @Schema(implementation = ApiError.class))),
      })
  ResponseEntity<List<Fruit>> findByAvailable() {
    final List<Fruit> fruits = fruitRepository.findByAvailable(true);

    if (fruits.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(fruits, HttpStatus.OK);
  }

  record FruitRequest(String name, boolean available) {
  }
}
