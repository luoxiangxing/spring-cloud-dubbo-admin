# spring-cloud-Dubbo Admin 支持spring-cloud-dubbo

这个是根据开源的dubbo-admin修改而来的，因为开源的dubbo-admin不支持spring-cloud-dubbo的组件
，所以修改了源码。支持spring-cloud-dubbo
1、目前引用的spring-cloud-dubbo为2.2.7.RELEASE，nacos的注册中心为：spring-cloud-starter-alibaba-nacos-discovery：2.2.7RELEASE

2、一、修改的具体类1、org.apache.dubbo.admin.service.RegistryServerSync引入spring-cloud-dubbo的类
@Resource
private DubboServiceMetadataRepository dubboServiceMetadataRepository;
@Resource
private DubboMetadataServiceProxy dubboMetadataConfigServiceProxy;

3、具体的修改点
//订阅spring-cloud-dubbo框架服务变更的事件SubscribedServicesChangedEvent
@EventListener(SubscribedServicesChangedEvent.class)
    public void start(SubscribedServicesChangedEvent event) throws Exception {
        Set<String> allServiceKeys = dubboServiceMetadataRepository.getSubscribedServices();
        log.info("订阅的所有服务：{}",allServiceKeys);
        for (String serviceName:allServiceKeys){
            try {
                boolean initialized = dubboMetadataConfigServiceProxy.isInitialized(serviceName);
                if(initialized){
                    DubboMetadataService proxy = dubboMetadataConfigServiceProxy.getProxy(serviceName);
                    Map<String, String> allExportedURLs = proxy.getAllExportedURLs();
                    if(allExportedURLs !=null && !allExportedURLs.isEmpty()){
                        allExportedURLs.forEach((key,value)->{
                            List<URL> urlList = jsonUtils.toURLs(value);
                            cacheURL(urlList);
                        });
                        //log.info("所有导出的接口：{}",allExportedURLs);
                    }
                }
            }catch (Exception e){
                log.error("获取服务暴露的URL出错,服务名：{}",serviceName);
            }

        }
    }


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
