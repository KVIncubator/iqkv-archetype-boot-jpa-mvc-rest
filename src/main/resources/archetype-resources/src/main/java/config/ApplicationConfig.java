import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories({"${package}.repository"})
@EnableJpaAuditing
@EnableTransactionManagement
@OpenAPIDefinition(info = @Info(title = "${projectName}", version = "${version}"))
class ApplicationConfig {

}
