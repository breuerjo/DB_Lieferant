package com.Redis;

import redis.clients.jedis.Jedis;

public class RedisPublish{
	
	private Jedis jedis;
	private String channel;
	
	public RedisPublish(String channel) {
		this.jedis = new Jedis("192.168.0.101");
		this.channel = channel;
	}
	
	public void publish(String message) {
		//System.out.println("Message: "+message);
		jedis.publish(this.channel,message);
	}
	
	   
}