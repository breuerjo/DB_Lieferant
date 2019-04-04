package com.Redis;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RedisTest {
	
	
	
	public static void main (String[] args) {
		RedisPublish redis_publish = new RedisPublish("Testchannel");
		RedisSubscribe redis_subscribe = new RedisSubscribe("Testchannel");
		 
		
		//Subscriber starten
		redis_subscribe.start();
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {}
		
		System.out.println("Testausgabe");
		redis_publish.publish("Test");
		redis_publish.publish("Test_Neu");
		
		List<String> list = redis_subscribe.getMessagesList();
		printList(list);
	}
	
	public static void printList(List<String> list) {
		System.out.println("Output Liste");
		for (int i = 0;i<list.size();i++){
		    System.out.println(list.get(i));
		}
	}
	
	
	
}