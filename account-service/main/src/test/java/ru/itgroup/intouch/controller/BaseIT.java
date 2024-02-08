package ru.itgroup.intouch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.itgroup.intouch.client.StorageServiceClient;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({"classpath:data/truncate.sql", "classpath:data/insert.sql"})
@ActiveProfiles("test")
@Testcontainers
public abstract class BaseIT {
    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected StorageServiceClient storageServiceClient;

    @MockBean
    protected JavaMailSender javaMailSender;

    protected ObjectMapper objectMapper = new ObjectMapper();

    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:14"));

    @DynamicPropertySource
    public static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("SN_DB_PORT", () -> postgreSQLContainer.getMappedPort(5432).toString());
        registry.add("SN_DB_HOST", postgreSQLContainer::getHost);
        registry.add("SN_DB_NAME", postgreSQLContainer::getDatabaseName);
        registry.add("SN_DB_USER", postgreSQLContainer::getUsername);
        registry.add("SN_DB_PASSWORD", postgreSQLContainer::getPassword);
    }

    static {
        postgreSQLContainer
                .withReuse(true)
                .start();
    }

}

