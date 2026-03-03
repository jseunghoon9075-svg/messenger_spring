package com.app.chat.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


//	이제 @Async는 이 executor를 쓴다.이거 안 하면 AI 비동기 처리 제대로 안 돌아감.
@Configuration
@EnableAsync
public class AsyncConfig {
	
	@Bean(name = "taskExecutor")
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(4);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(50);
		executor.setThreadNamePrefix("AI-Executor-");
		executor.initialize();
		return executor;
	}
}
