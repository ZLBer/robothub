package edu.hust.robothub.core.storage;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import edu.hust.robothub.core.until.Config;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoStorage extends AbstractStorage<MongoStorage.MongoRequest> {

    private final String MONGO_HOST = "localhost";
    private final int MONGO_PORT = 27017;
    private final String MONGO_USER = "username";
    private final String MONGO_PASSWORD = "password";
    private final String MONGO_DATABASE = "databaseName";
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;

    public MongoStorage(Config config) {
        super(config);
    }

    @Override
    public void start() {

        List<ServerAddress> adds = new ArrayList<>();
//ServerAddress()两个参数分别为 服务器地址 和 端口
        ServerAddress serverAddress = new ServerAddress(MONGO_HOST, MONGO_PORT);
        adds.add(serverAddress);

        List<MongoCredential> credentials = new ArrayList<>();
//MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
        MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(MONGO_USER, MONGO_DATABASE, MONGO_PASSWORD.toCharArray());
        credentials.add(mongoCredential);

//通过连接认证获取MongoDB连接
        mongoClient = new MongoClient(serverAddress, credentials);

        mongoDatabase = mongoClient.getDatabase("");

    }

    @Override
    public void storage(MongoRequest obj) {

        MongoCollection<Document> collection = mongoDatabase.getCollection(obj.getCollectionName());
        collection.insertMany(obj.getDocuments());

    }


    @Override
    public void stop() {
        mongoClient.close();
    }

    class MongoRequest {
        String collectionName;
        List<Document> documents;

        public MongoRequest(String collectionName, List<Document> documents) {
            this.collectionName = collectionName;
            this.documents = documents;
        }

        public String getCollectionName() {
            return collectionName;
        }

        public void setCollectionName(String collectionName) {
            this.collectionName = collectionName;
        }

        public List<Document> getDocuments() {
            return documents;
        }

        public void setDocuments(List<Document> documents) {
            this.documents = documents;
        }
    }
}
