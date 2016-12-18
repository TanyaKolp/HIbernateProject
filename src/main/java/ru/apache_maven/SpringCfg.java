package ru.apache_maven;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.apache_maven.commands.ShowCompaniesCommand;

/**
 * Created by tania on 12/16/16.
 */
@Configuration
@ComponentScan(basePackages = {"ru.apache_maven.commands","ru.apache_maven"})
public class SpringCfg {
}
