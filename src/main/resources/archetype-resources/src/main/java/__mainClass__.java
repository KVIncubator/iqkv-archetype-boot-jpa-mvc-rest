#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

@SpringBootApplication
public class ${mainClass} {

  public static void main(String[] args) {
    SpringApplication springApplication = new SpringApplication(${mainClass}.class);
    springApplication.setApplicationStartup(new BufferingApplicationStartup(2048));
    springApplication.run(args);
  }
}
