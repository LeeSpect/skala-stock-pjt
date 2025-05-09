package com.sk.skala;

import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
// import org.springframework.data.jpa.repository.config.EnableJpaAuditing; // JPA Auditing 비활성화
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
// @EnableJpaAuditing // JPA Auditing 기능 사용 안 함으로 주석 처리
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
}) // 데이터소스 및 JPA 자동 설정 제외
public class SkalaStockConsole2Application {
    public static void main(String[] args) {
        SpringApplication.run(SkalaStockConsole2Application.class, args);
    }
}
