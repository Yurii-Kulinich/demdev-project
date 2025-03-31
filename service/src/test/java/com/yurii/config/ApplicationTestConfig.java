package com.yurii.config;

import com.yurii.util.HibernateTestUtil;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

//@Import({ApplicationConfiguration.class})
public class ApplicationTestConfig {

  @Primary
  @Bean
  public Configuration configuration() {
    return HibernateTestUtil.buildHibernateConfig();
  }

}
