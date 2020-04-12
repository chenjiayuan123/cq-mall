package club.banyuan.mall.mgt.service;

import java.io.IOException;
import java.io.InputStream;

public interface OssFileService {

  /**
   * @return 文件的url路径
   * @throws IOException 上传出现问题
   */
  String save(String objectName, InputStream stream, String contentType)
      throws IOException;

  void delete(String objectName) throws IOException;
}
