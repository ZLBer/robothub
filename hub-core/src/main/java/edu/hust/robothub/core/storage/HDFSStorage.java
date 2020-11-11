package edu.hust.robothub.core.storage;

import edu.hust.robothub.core.until.Config;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;

public class HDFSStorage extends AbstractStorage<HDFSStorage.HDFSRequst> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HDFSStorage.class);
    private final String HDFS_URI = "hdfs://hadoop102:9820";
    private final String HDFS_USER = "atguigu";
    FileSystem fs;
    Configuration conf;

    public HDFSStorage(Config config) {
        super(config);
    }

    @Override
    public void start() {
        //配置的优先级：代码中的>resource中的配置> 集群中的配置 >集群中得default配置
        conf = new Configuration();
        //conf.set("dfs.replication","1"); //设置副本数
        // 获取filesystem对象
        try {
            fs = FileSystem.get(URI.create(HDFS_URI), conf, HDFS_USER);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void storage(HDFSRequst hdfsRequst) {
        copyFileToHDFS(hdfsRequst.isDelSrc(), hdfsRequst.isOverwrite(), hdfsRequst.getSrcFile(), hdfsRequst.getDestPath());
    }


    public void copyFileToHDFS(boolean delSrc, boolean overwrite, String srcFile, String destPath) {
        // 源文件路径是 Linux 下的路径
        Path srcPath = new Path(srcFile);
        // 目的路径
        destPath = HDFS_URI + destPath;
        Path dstPath = new Path(destPath);
        // 实现文件上传
        try {
            // 获取 FileSystem 对象
            fs.copyFromLocalFile(srcPath, dstPath);
            fs.copyFromLocalFile(delSrc, overwrite, srcPath, dstPath);
            // 释放资源
            fs.close();
        } catch (IOException e) {
            LOGGER.error("", e);
        }
    }

    @Override
    public void stop() {
        try {
            fs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class HDFSRequst {
        boolean delSrc;
        boolean overwrite;
        String srcFile;
        String destPath;

        public HDFSRequst(boolean delSrc, boolean overwrite, String srcFile, String destPath) {
            this.delSrc = delSrc;
            this.overwrite = overwrite;
            this.srcFile = srcFile;
            this.destPath = destPath;
        }

        public boolean isDelSrc() {
            return delSrc;
        }

        public void setDelSrc(boolean delSrc) {
            this.delSrc = delSrc;
        }

        public boolean isOverwrite() {
            return overwrite;
        }

        public void setOverwrite(boolean overwrite) {
            this.overwrite = overwrite;
        }

        public String getSrcFile() {
            return srcFile;
        }

        public void setSrcFile(String srcFile) {
            this.srcFile = srcFile;
        }

        public String getDestPath() {
            return destPath;
        }

        public void setDestPath(String destPath) {
            this.destPath = destPath;
        }
    }
}
