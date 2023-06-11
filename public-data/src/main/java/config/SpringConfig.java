package config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;



@Configuration
public class SpringConfig {

        @Value("${SN_DB_HOST}")
        private String host;
        @Value("${SN_DB_PORT}")
        private String port;
        @Value("${SN_DB_NAME}")
        private String name;
        @Value("${SN_DB_USER}")
        private String userName;
        @Value("${SN_DB_PASSWORD}")
        private String password;

        @Bean
        public DataSource dataSource() {

                DriverManagerDataSource driver = new DriverManagerDataSource();
                driver.setDriverClassName("org.postgresql.Driver");
                driver.setUrl("jdbc:postgresql://" +host +":" + port + "/" + name);
                driver.setUsername(userName);
                driver.setPassword(password);

                return driver;
        }

}
