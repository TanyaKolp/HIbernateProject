package ru.apache_maven;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringCfg.class);
        InputController ip = context.getBean(InputController.class);
        ip.init();
    }
}
