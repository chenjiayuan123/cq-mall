package club.banyuan.demo.oss.service.impl;

import club.banyuan.demo.oss.service.OssFileService;
import io.minio.MinioClient;
import io.minio.policy.PolicyType;
import java.io.IOException;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MinioOssFileServiceImpl implements OssFileService {

  private static final Logger LOGGER = LoggerFactory.getLogger(MinioOssFileServiceImpl.class);

  @Value("${minio.endpoint}")
  private String ENDPOINT;
  @Value("${minio.bucketName}")
  private String BUCKET_NAME;
  @Value("${minio.accessKey}")
  private String ACCESS_KEY;
  @Value("${minio.secretKey}")
  private String SECRET_KEY;

  @Override
  public String save(String objectName, InputStream stream, String contentType)
      throws IOException {
    try {
      //创建一个MinIO的Java客户端
      MinioClient minioClient = new MinioClient(ENDPOINT, ACCESS_KEY, SECRET_KEY);
      boolean isExist = minioClient.bucketExists(BUCKET_NAME);
      if (isExist) {
        LOGGER.info("存储桶已经存在！");
      } else {
        //创建存储桶并设置只读权限
        minioClient.makeBucket(BUCKET_NAME);
        minioClient.setBucketPolicy(BUCKET_NAME, "*.*", PolicyType.READ_ONLY);
      }
      // 使用putObject上传一个文件到存储桶中
      minioClient.putObject(BUCKET_NAME, objectName, stream, contentType);
      LOGGER.info("文件上传成功!");
      return ENDPOINT + "/" + BUCKET_NAME + "/" + objectName;
    } catch (Exception e) {
      throw new IOException(e);
    }
  }

  @Override
  public void delete(String objectName) throws IOException {
    try {
      MinioClient minioClient = new MinioClient(ENDPOINT, ACCESS_KEY, SECRET_KEY);
      minioClient.removeObject(BUCKET_NAME, objectName);
    } catch (Exception e) {
      throw new IOException(e);
    }
  }
}
