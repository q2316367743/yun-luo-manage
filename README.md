# 云落Linux服务器管理（开发中）

> 本项目部分功能基于Linux命令实现，请在linux系统下运行

基于springboot+vue，数据库存储为sqlite存储，只需要有java运行环境即可运行

## 参考

- [盘古](https://gitee.com/javashop/pangu)
- [BaoTa](https://gitee.com/terrydash/BaoTa)
- [phpMyAdmin](https://gitee.com/mirrors/phpMyAdmin)（[码云地址](https://gitee.com/mirrors/phpMyAdmin)）

## 安装使用

初始用户名：Esion，初始密码：123456

### 自行打包

```bash
git pull https://gitee.com/qiaoshengda/yun-luo-manage.git
cd yun-luo-manage
cd web
npm install
npm run build
cd ../java
mvn package -Dmaven.test.skip=true
cd target
java -jar manage-1.0.jar
```

访问地址：<localhost:7743>

### 下载安装

> 如果只是想体验功能，可以使用此方法

软件运行系统：Linux

软件运行基础：Java

下载发行版

使用`java -jar manage-1.0.0`运行

访问地址：<localhost:7743>

## 说明

官网地址：<https://esion.xyz>



