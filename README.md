# spring-cloud-Dubbo Admin 支持spring-cloud-dubbo

这个是根据开源的dubbo-admin修改而来的，因为开源的dubbo-admin不支持spring-cloud-dubbo的组件
，所以修改了源码。支持spring-cloud-dubbo
1、目前引用的spring-cloud-dubbo为2.2.7.RELEASE，nacos的注册中心为：spring-cloud-starter-alibaba-nacos-discovery：2.2.7RELEASE

### 注意事项
1、如果使用redis作为元数据中心，redis不能设置密码，这是dubbo2.7.X的bug，本项目对源码做了修改，修改了dubbo-metadata-report-redis的2.7.14的源码，对应的jar包为2.7.14-fix-RELEASE，这个jar包放在了meta-jar下，个人需要把这个jar放到本地或则自己的私服下
2、亲测如果使用nacos注册元数据中心会不能指定namespace
3、这是用了nacos作为注册中心



[中文说明](README_ZH.md)
### Quick start


![index](https://raw.githubusercontent.com/apache/dubbo-admin/develop/doc/images/index.png)

### Service Governance  
service governance follows the version of Dubbo 2.7, and compatible for Dubbo 2.6, please refer to [here](https://github.com/apache/dubbo-admin/wiki/The-compatibility-of-service-governance)
### admin UI

- [Vue.js](https://vuejs.org) and [Vue Cli](https://cli.vuejs.org/)
- [dubbo-admin-ui/README.md](dubbo-admin-ui/README.md) for more detail
- Set npm **proxy mirror**: if you have network issue, you can set npm proxy mirror to speedup npm install: add `registry =https://registry.npm.taobao.org` to ~/.npmrc

### admin Server

* Standard spring boot project
* [configurations in application.properties](https://github.com/apache/dubbo-admin/wiki/Dubbo-Admin-configuration)


### Production Setup

1. Clone source code on develop branch `git clone https://github.com/apache/dubbo-admin.git`
2. Specify registry address in `dubbo-admin-server/src/main/resources/application.properties`
3. Build

    > - `mvn clean package -Dmaven.test.skip=true`  
4. Start 
    * `mvn --projects dubbo-admin-server spring-boot:run`  
    OR
    * `cd dubbo-admin-distribution/target`;   `java -jar dubbo-admin-0.1.jar`
5. Visit `http://localhost:8080`
6. Default username and password is `root`

### 开发启动
* Run admin server project
   backend is a standard spring boot project, you can run it in any java IDE
* Run admin ui project
  run with `npm run dev`.
* visit web page
  visit `http://localhost:8081`, frontend supports hot reload.
  
### 
