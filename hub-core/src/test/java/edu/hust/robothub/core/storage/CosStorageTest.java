package edu.hust.robothub.core.storage;

import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import edu.hust.robothub.core.until.Config;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class CosStorageTest {
    Config config = Config.INSTANCE;
    CosStorage cosStorage;

    @Before
    public void setUp() throws Exception {
      config.setConfigKV(CosStorage.COS_SECREDKEY,"KOrAFCuQCH6dux60gDVq11mHWpulY5MN");
      config.setConfigKV(CosStorage.COS_SECRETID,"AKID1h7rOizS0ASVKxpZUBdMr9GDvIVSBcDb");
      config.setConfigKV(CosStorage.REGION_NAME,"ap-nanjing");

      cosStorage = new CosStorage(config);

      cosStorage.start();
    }


    @Test
    public void storage() {
        ObjectMetadata metadata = new ObjectMetadata();
        PutObjectRequest putObjectRequest= null;
        try {
            putObjectRequest = new PutObjectRequest("test-1253402865","test.zip",new FileInputStream(new File("D:\\全景图素材.zip")),metadata);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        cosStorage.storage(putObjectRequest);
    }

    @Test
    public void stop() {
    }


}