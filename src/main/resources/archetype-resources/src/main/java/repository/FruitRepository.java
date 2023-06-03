#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ${package}.entity.Fruit;

@Repository
public interface FruitRepository
    extends PagingAndSortingRepository<Fruit, Long>, JpaRepository<Fruit, Long> {

  List<Fruit> findByName(String name);

  List<Fruit> findByNameContaining(String name);

  List<Fruit> findByAvailable(boolean availabilityState);

}
