package br.com.abbarros.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.MessageChannel;

@Configuration
public class ChannelConfiguration {
	
	@Bean
	public MessageChannel searchRequest() {
		return new DirectChannel();
	}
	
	@Bean
	public MessageChannel searchAggregatedReply() {
		return new DirectChannel();
	}
	
	@Bean
	public MessageChannel searchRequests() {
		return new DirectChannel();
	}
	
	@Bean
	public QueueChannel searchRequestA() {
		return new QueueChannel(10);
	}
	
	@Bean
	public QueueChannel searchRequestB() {
		return new QueueChannel(10);
	}
	
	@Bean
	public PublishSubscribeChannel searchReply() {
		return new PublishSubscribeChannel();
	}

}
