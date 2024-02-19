#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = Fruit.TABLE_NAME)
public class Fruit {

  protected static final String TABLE_NAME = "fruits";

  @Id
  @SequenceGenerator(
      name = "fruit_id_seq",
      sequenceName = "fruit_id_seq",
      initialValue = 1,
      allocationSize = 1
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "fruit_id_seq"
  )
  private Long id;

  @Size(min = 2, max = 120)
  @Column(length = 120, unique = true)
  private String name;

  private boolean available;

  public Fruit(String name, boolean available) {
    this.name = name;
    this.available = available;
  }

  @Override
  public String toString() {
    return "Fruit{" +
           "id=" + id +
           ", name='" + name + '\'' +
           ", available=" + available +
           '}';
  }
}
