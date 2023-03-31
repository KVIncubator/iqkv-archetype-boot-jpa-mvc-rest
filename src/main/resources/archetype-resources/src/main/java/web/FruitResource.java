#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.bind.annotation.RestController;
import org.ujar.boot.restful.web.ErrorResponse;
import org.ujar.boot.restful.web.PaginationRequest;
import ${package}.entity.Fruit;
import ${package}.repository.FruitRepository;

@RestController
@Tag(name = "Fruit Resource", description = "API endpoints for managing fruit entity.")
@RequestMapping("/api/v1/fruits")
@Validated
@RequiredArgsConstructor
class FruitResource {

  private final FruitRepository fruitRepository;

  @PostMapping
  @Operation(
      description = "Create a new fruit.",
      responses = {
          @ApiResponse(responseCode = "201",
                       description = "Success"),
          @ApiResponse(responseCode = "500",
                       description = "Internal error",
                       content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "400",
                       description = "Bad request",
                       content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  ResponseEntity<Fruit> create(@RequestBody final FruitDto request) {
    final var fruit = new Fruit(null, request.name());
    return new ResponseEntity<>(fruitRepository.save(fruit), HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  @Operation(
      description = "Retrieve fruit by id.",
      responses = {
          @ApiResponse(responseCode = "200",
                       description = "Success"),
          @ApiResponse(responseCode = "500",
                       description = "Internal error",
                       content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "400",
                       description = "Bad request",
                       content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "404",
                       description = "Not found",
                       content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
      })
  ResponseEntity<Fruit> findById(@PathVariable final Long id) {
    return ResponseEntity.of(fruitRepository.findById(id));
  }

  @GetMapping
  @Operation(
      description = "Get all the fruits.",
      responses = {
          @ApiResponse(responseCode = "200",
                       description = "Success"),
          @ApiResponse(responseCode = "500",
                       description = "Internal error",
                       content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "400",
                       description = "Bad request",
                       content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  ResponseEntity<Page<Fruit>> findAll(@ParameterObject @Valid final PaginationRequest request) {
    final var pageRequest = PageRequest.of(request.getPage(), request.getSize());
    return new ResponseEntity<>(fruitRepository.findAll(pageRequest), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  @Operation(
      description = "Updates an existing fruit.",
      responses = {
          @ApiResponse(responseCode = "200",
                       description = "Success"),
          @ApiResponse(responseCode = "500",
                       description = "Internal error",
                       content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "400",
                       description = "Bad request",
                       content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  ResponseEntity<Fruit> update(@PathVariable final Long id, @RequestBody final FruitDto request) {
    final var fruit = new Fruit(id, request.name());
    return new ResponseEntity<>(fruitRepository.save(fruit), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  @Operation(
      description = "Delete fruit.",
      responses = {
          @ApiResponse(responseCode = "200",
                       description = "Success"),
          @ApiResponse(responseCode = "500",
                       description = "Internal error",
                       content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "400",
                       description = "Bad request",
                       content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  HttpStatus delete(@PathVariable final Long id) {
    fruitRepository.deleteById(id);
    return HttpStatus.OK;
  }

  record FruitDto(String name) {
  }
}
