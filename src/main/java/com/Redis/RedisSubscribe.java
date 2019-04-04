package com.Redis;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.DynamoDBLieferant.DynamoDB;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;


public class RedisSubscribe extends Thread{
	
	private String channel;
	private List<String> received_msg;
	private DynamoDB db;
	
	public RedisSubscribe(String channel) {
		
		this.channel = channel;
		this.received_msg = new ArrayList<String>();
		
		try {
			this.db = new DynamoDB();
		} catch (Exception e) {e.printStackTrace();}
	}

    public void run() {
        Jedis jedis = new Jedis("192.168.0.101");
        System.out.println("Starting Subscription on Channel: " + channel);

        while (true) {
            jedis.subscribe(new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {
                    super.onMessage(channel, message);
                    //System.out.println(""+ message);
                    
                    received_msg.add(message);	//Nachricht allgemein speichern
                    
                    interpretMessage(message);	//Nachricht interpretieren
                }

                @Override
                public void onSubscribe(String channel, int subscribedChannels) {
                }

                @Override
                public void onUnsubscribe(String channel, int subscribedChannels) {
                }

                @Override
                public void onPMessage(String pattern, String channel, String message) {
                }

                @Override
                public void onPUnsubscribe(String pattern, int subscribedChannels) {
                }
                
                @Override
                public void onPSubscribe(String pattern, int subscribedChannels) {
                }

            }, channel);
        }
    }
    
    public static void interpretMessage(String msg) {
    	
    	//Update LieferantNachrichtEmpfangen, wenn es 2 Strings getrennt mit ";" sind:
    	try {
			String[] split = msg.split(";");
			
			if(split.length == 2) {
				String bestellungId=split[0];
				String status =split[1];
				DynamoDB.saveLieferantNachrichtEmpfangen(bestellungId, status);
			}
		} catch (Exception e) {
			System.out.println("Split hat nicht geklappt");
		}
    	
    	
    }
    
    public List<String> getMessagesList(){
    	return this.received_msg;
    }
}