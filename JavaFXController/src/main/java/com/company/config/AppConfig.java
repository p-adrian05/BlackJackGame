package com.company.config;

import com.company.blackJack.game.Player;
import com.company.blackJack.game.PlayerImpl;
import com.company.javafx.controller.GameController;
import com.company.javafx.controller.LoginController;
import com.company.javafx.controller.StatController;
import org.springframework.context.annotation.*;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;

@Configuration
@ComponentScan(basePackages = {"com.company"})
@EnableTransactionManagement
public class AppConfig {
    @Bean
    public LocalEntityManagerFactoryBean entityManagerFactory() {
        LocalEntityManagerFactoryBean localEmfBean = new LocalEntityManagerFactoryBean();
        localEmfBean.setPersistenceUnitName("blackJackGame");
        return localEmfBean;
    }
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
    @Bean
    @Scope("prototype")
    public LoginController loginController() {
        return new LoginController();
    }
    @Bean
    @Scope("prototype")
    public GameController gameController() {
        return new GameController();
    }

    @Bean
    @Scope("prototype")
    public StatController statController() {
        return new StatController();
    }

}
