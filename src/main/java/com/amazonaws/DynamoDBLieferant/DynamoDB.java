package com.amazonaws.DynamoDBLieferant;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.util.TableUtils;


public class DynamoDB {


    static AmazonDynamoDB dynamoDB;
    
    public DynamoDB() throws Exception {
    	init();
    	
    }

    private static void init() throws Exception {
    	//Benutzer erstellen
        ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
        try {
            credentialsProvider.getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (~/.aws/credentials), and is in valid format.",
                    e);
        }
        //DynamoDB-Client erstellen
        dynamoDB = AmazonDynamoDBClientBuilder.standard()
            .withCredentials(credentialsProvider)
            .withRegion("eu-central-1")
            .build();
    }

    public static void main(String[] args) throws Exception {
        init();

        try {
            String tableName = "Produkt";

            // Scan items for movies with a year attribute greater than 1985
            HashMap<String, Condition> scanFilter = new HashMap<String, Condition>();
            /*Condition condition = new Condition()
                .withComparisonOperator(ComparisonOperator.GT.toString())
                .withAttributeValueList(new AttributeValue().withN("1985"));*/
            //scanFilter.put("year" ,condition);
            ScanRequest scanRequest = new ScanRequest(tableName).withScanFilter(scanFilter);
            ScanResult scanResult = dynamoDB.scan(scanRequest);
            System.out.println("Result: " + scanResult);
            System.out.println(scanResult.getItems().get(0).get("Preis").getN());
            

        } catch (AmazonServiceException ase) {
            System.out.println("Error AWS-Connection");
        } catch (AmazonClientException ace) {
        	System.out.println("Error AWS-Credentials");
        }
    }
     
    
    public static void saveLieferantNachrichtEmpfangen(String bestellungId, String status) {
    	//erst passenden Eintrag zu Bestellung-ID holen
    	//-------xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx--------------------------
    	//Dann status updaten
    	System.out.println("Status LieferantNachrichtEmpfangen wurde upgedated: "+ bestellungId + "  "+status);
    }
    
   /* public static void saveElementToBestellung(String id, String bestelldatum, String[] mitarbeiterID, String[] produktId, String[] menge) {
    	//lieferantGeschickt standardm‰ﬂig auf "false" setzen
    	
    	String tableName = "Bestellung";
    	
    	Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
    	/*item.put("Id", new AttributeValue().withN(Integer.toString(id)));
        item.put("Bestelldatum", new AttributeValue(bestelldatum));
        item.put("ProduktId", new AttributeValue(rating));
        item.put("Menge", new AttributeValue().withNS(Integer.toString(menge)));
        item.put("MitarbeiterId", new AttributeValue(rating));
        item.put("LieferantGeschickt", new AttributeValue("false"));
        
        //Element in Tabelle Bestellung speichern
        PutItemRequest putItemRequest = new PutItemRequest(tableName, item);
        PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);
        System.out.println("Result: " + putItemResult);
    }*/


}
