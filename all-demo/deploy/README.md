## 说明
将程序根据环境进行打包，压缩成为tar.gz文件

build阶段替换application.yml文件中@@包裹的内容，
并且根据resource配置复制指定的resource下的文件到target/classes路径

jar阶段过滤掉指定的文件，只打包当前module的内容到jar包，并制定mainclass

打包阶段(assembly)，将依赖包复制到lib中


## 打包命令
```
mvn clean package -Pdev
# 或者
mvn clean package -Pprod
```

## 启动
使用bin中run.sh脚本启动程序