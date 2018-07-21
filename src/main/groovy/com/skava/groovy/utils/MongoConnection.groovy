package com.skava.groovy.utils

import com.framework.reporting.BaseClass
import com.gmongo.GMongo
import com.mongodb.MongoClient
import com.mongodb.MongoClientURI


class MongoConnection extends BaseClass {
	 static def mongo
	 static def db


	public static void openMongoConnection(){

		MongoClientURI uri = new MongoClientURI("mongodb://"+properties.getProperty("MongoUser")+":"+properties.getProperty("MongoPassword")+"@"+properties.getProperty("Host")+":27017/"+properties.getProperty("DataBaseName")+"?mechanism='SCRAM-SHA-1'");
		MongoClient mongoClient = new MongoClient(uri)
		mongo = new GMongo(mongoClient)
		db=mongo.getDB(properties.getProperty("DataBaseName"))
		System.out.println("Connected");

		}
		public static void validateProduct(def Product) {

			def json=db.productmaster.findOne([productid:Product])
			System.out.println(json);
			System.out.println(json.productid);
			if(json.toString().contains(Product)){
			System.out.println("PASS");
			}
			else{
			System.out.println("FAIL");
			}
		}


}
