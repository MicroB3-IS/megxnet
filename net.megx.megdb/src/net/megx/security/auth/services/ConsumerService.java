package net.megx.security.auth.services;

import java.util.List;

import net.megx.security.auth.model.Consumer;

public interface ConsumerService {
	public Consumer getConsumer(String name) throws Exception;
	public Consumer getConsumerForKey(String key) throws Exception;
	public Consumer getConsumerForKeyAndName(String key, String name) throws Exception;
	public Consumer addConsumer(Consumer consumer) throws Exception;
	public Consumer updateConsumer(Consumer consumer) throws Exception;
	public void removeConsumer(Consumer consumer) throws Exception;
	public List<Consumer> getConsumersForUser(String userId) throws Exception;
}
