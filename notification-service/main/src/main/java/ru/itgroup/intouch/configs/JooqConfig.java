package ru.itgroup.intouch.configs;

import org.jooq.SQLDialect;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.Settings;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
public class JooqConfig {
    @Bean
    public DefaultConfiguration configuration(DataSource dataSource) {
        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
        jooqConfiguration.set(SQLDialect.POSTGRES)
                         .set(dataSource);

        Settings settings = new Settings();
        settings.setRenderSchema(false);
        settings.setRenderNameCase(RenderNameCase.LOWER);
        settings.setRenderFormatted(true);
        jooqConfiguration.set(settings);

        return jooqConfiguration;
    }
}

