package com.main;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.Redis.RedisPublish;
import com.Redis.RedisSubscribe;


public class MainController {
	
	public static void main (String[] args) {

		
		RedisPublish redis_publish = new RedisPublish("Testchannel");
		RedisSubscribe redis_subscribe = new RedisSubscribe("Testchannel");
		
		//Subscriber starten
		redis_subscribe.start();
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {}
		
		redis_publish.publish("Test;Alt");
		redis_publish.publish("Test;Neu");
		
		List<String> list = redis_subscribe.getMessagesList();
		//RedisTest.printList(list);
	}
	
}