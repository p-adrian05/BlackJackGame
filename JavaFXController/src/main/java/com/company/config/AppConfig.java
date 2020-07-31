package com.company.config;

import com.company.blackJack.game.Dealer;
import com.company.blackJack.game.Person;
import com.company.blackJack.game.Player;
import com.company.blackJack.game.PlayerImpl;
import com.company.javafx.controller.GameController;
import com.company.javafx.controller.LoginController;
import com.company.javafx.controller.StatController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.security.SecureRandom;

@Configuration
@ComponentScan(basePackages = {"com.company"})
@EnableTransactionManagement
@PropertySource("classpath:config/game.properties")
public class AppConfig {

    @Value("${game.timerDuration:10}")
    private int timerDuration;

    @Bean
    @TimerDuration
    public int timerDuration(){
        return timerDuration;
    }

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

    @Bean
    public PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder(12,new SecureRandom());
    }

    @Bean
    @Scope("prototype")
    public Player player(){
        return new PlayerImpl();
    }
    @Bean(name = "dealer")
    @Scope("prototype")
    public Person dealer(){
        return new Dealer();
    }

}
