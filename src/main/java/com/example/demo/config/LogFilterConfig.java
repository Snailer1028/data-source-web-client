package com.example.demo.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

/**
 * Log日志过滤，在logback-spring.xml中使用
 *
 * @author yan
 * @since 2024-06-28
 */
public class LogFilterConfig extends Filter<ILoggingEvent> {
	@Override
	public FilterReply decide(ILoggingEvent event) {
		if (event.getMessage().contains("disconnected")
				|| event.getMessage().contains("Consumer clientId=consumer")
				|| event.getMessage().contains("ConsumerConfig values")
				|| event.getMessage().contains("hivemetaops: partitions assigned")) {
			return FilterReply.DENY;
		} else {
			return FilterReply.NEUTRAL;
		}
	}
}

