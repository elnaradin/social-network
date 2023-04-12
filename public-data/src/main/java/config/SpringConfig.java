package config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;



@Configuration
@ComponentScan("datasource")
public class SpringConfig {

        @Value("${database.url}")
        private String url;
        @Value("${database.username}")
        private String userName;
        @Value("${database.password}")
        private String password;

        @Bean
        public DataSource dataSource() {

                DriverManagerDataSource driver = new DriverManagerDataSource();
                driver.setDriverClassName("org.postgresql.Driver");
                driver.setUrl(url);
                driver.setUsername(userName);
                driver.setPassword(password);

                return driver;
        }

}
