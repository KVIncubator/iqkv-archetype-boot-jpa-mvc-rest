#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ${package}.entity.Fruit;

public interface FruitRepository
    extends PagingAndSortingRepository<Fruit, Long>, JpaRepository<Fruit, Long> {

}
