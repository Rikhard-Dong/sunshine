package com.hfmes.sunshine.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 20:34
 */
@Configuration
@MapperScan("com.hfmes.sunshine.dao")
public class MyBatisConfig {
}
