package edu.hust.robothub.core.storage;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import edu.hust.robothub.core.until.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;


//tencent对象存储
public class CosStorage extends AbstractStorage<PutObjectRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CosStorage.class);
     static final String COS_SECRETID = "COS_SECRETID";
     static final String COS_SECREDKEY = "COS_SECRETKEY";
     static final String REGION_NAME = "COS_REGION";
    COSClient cosClient;

    public CosStorage(Config config) {
        super(config);
    }

    @Override
    public void start() {
        // 1 初始化用户身份信息（secretId, secretKey）
        String secretId = config.getConfigValue(COS_SECRETID);
        String secretKey = config.getConfigValue(COS_SECREDKEY);
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(config.getConfigValue(REGION_NAME));
        ClientConfig clientConfig = new ClientConfig(region);
       // 3 生成 cos 客户端。
        cosClient = new COSClient(cred, clientConfig);
    }

    @Override
    public void storage(PutObjectRequest putObjectRequest) {
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
       LOGGER.info( putObjectResult.getETag());
    }

    public PutObjectRequest buildPutObjectRequest(String bucketName,String key,File localFile){
     return new PutObjectRequest(bucketName,key,localFile);
    }

    public PutObjectRequest buildPutObjectRequest(String bucketName, String key, InputStream inputStream, ObjectMetadata objectMetadata){
    return new PutObjectRequest(bucketName,key,inputStream,objectMetadata);
    }


    @Override
    public void stop() {
        // 关闭客户端(关闭后台线程)
        cosClient.shutdown();
    }

}
