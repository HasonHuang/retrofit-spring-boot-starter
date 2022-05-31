
## retrofit-spring-boot-starter

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Build Status](https://api.travis-ci.com/LianjiaTech/retrofit-spring-boot-starter.svg?branch=master)](https://travis-ci.com/github/LianjiaTech/retrofit-spring-boot-starter)
[![Maven central](https://maven-badges.herokuapp.com/maven-central/com.github.lianjiatech/retrofit-spring-boot-starter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.lianjiatech/retrofit-spring-boot-starter)
[![GitHub release](https://img.shields.io/github/v/release/lianjiatech/retrofit-spring-boot-starter.svg)](https://github.com/LianjiaTech/retrofit-spring-boot-starter/releases)
[![License](https://img.shields.io/badge/JDK-1.8+-4EB1BA.svg)](https://docs.oracle.com/javase/8/docs/index.html)
[![License](https://img.shields.io/badge/SpringBoot-1.5+-green.svg)](https://docs.spring.io/spring-boot/docs/2.1.5.RELEASE/reference/htmlsingle/)
[![Author](https://img.shields.io/badge/Author-chentianming-orange.svg?style=flat-square)](https://juejin.im/user/3562073404738584/posts)
[![QQ-Group](https://img.shields.io/badge/QQ%E7%BE%A4-806714302-orange.svg?style=flat-square) ](https://img.ljcdn.com/hc-picture/6302d742-ebc8-4649-95cf-62ccf57a1add)


**`retrofit-spring-boot-starter`实现了`Retrofit`与`spring-boot`框架快速整合，并且支持了诸多功能增强，极大简化开发**。

> 🚀项目持续优化迭代，欢迎大家提ISSUE和PR！麻烦大家能给一颗star✨，您的star是我们持续更新的动力！

github项目地址：[https://github.com/LianjiaTech/retrofit-spring-boot-starter](https://github.com/LianjiaTech/retrofit-spring-boot-starter)

gitee项目地址：[https://gitee.com/lianjiatech/retrofit-spring-boot-starter](https://gitee.com/lianjiatech/retrofit-spring-boot-starter)

示例demo：[https://github.com/ismart-yuxi/retrofit-spring-boot-demo](https://github.com/ismart-yuxi/retrofit-spring-boot-demo)

> 感谢`@ismart-yuxi`为本项目写的示例demo

<!--more-->

## 功能特性

- [x] [自定义OkHttpClient属性](#自定义OkHttpClient属性)
- [x] [注解式拦截器](#注解式拦截器)
- [x] [日志打印](#日志打印)
- [x] [请求重试](#请求重试)
- [x] [熔断降级](#熔断降级)
- [x] [错误解码器](#错误解码器)
- [x] [微服务之间的HTTP调用](#微服务之间的HTTP调用)
- [x] [全局拦截器](#全局拦截器)
- [x] [调用适配器](#调用适配器)
- [x] [数据转换器](#数据转码器)
- [x] [元注解](#元注解)
- [x] [其他功能示例](#其他功能示例)

## 快速使用

### 引入依赖

```xml
<dependency>
    <groupId>com.github.lianjiatech</groupId>
   <artifactId>retrofit-spring-boot-starter</artifactId>
   <version>2.3.4</version>
</dependency>
```

**本项目依赖Retrofit-2.9.0，okhttp-3.14.9，okio-1.17.5版本，如果冲突，烦请手动引入相关jar包**。完整依赖如下：


```xml
<dependency>
    <groupId>com.github.lianjiatech</groupId>
   <artifactId>retrofit-spring-boot-starter</artifactId>
   <version>2.3.4</version>
</dependency>
 <dependency>
    <groupId>com.squareup.okhttp3</groupId>
    <artifactId>logging-interceptor</artifactId>
    <version>3.14.9</version>
</dependency>
<dependency>
    <groupId>com.squareup.okhttp3</groupId>
    <artifactId>okhttp</artifactId>
    <version>3.14.9</version>
</dependency>
<dependency>
    <groupId>com.squareup.okio</groupId>
    <artifactId>okio</artifactId>
    <version>1.17.5</version>
</dependency>
<dependency>
    <groupId>com.squareup.retrofit2</groupId>
    <artifactId>retrofit</artifactId>
    <version>2.9.0</version>
</dependency>
<dependency>
    <groupId>com.squareup.retrofit2</groupId>
    <artifactId>converter-jackson</artifactId>
    <version>2.9.0</version>
</dependency>
```

### 定义http接口

**接口必须使用`@RetrofitClient`注解标记**！http相关注解可参考官方文档：[retrofit官方文档](https://square.github.io/retrofit/)。

```java
@RetrofitClient(baseUrl = "${test.baseUrl}")
public interface HttpApi {

    @GET("person")
    Result<Person> getPerson(@Query("id") Long id);
}
```

> 友情提示：**方法请求路径慎用`/`开头**。对于`Retrofit`而言，如果`baseUrl=http://localhost:8080/api/test/`，方法请求路径如果是`person`，则该方法完整的请求路径是：`http://localhost:8080/api/test/person`。而方法请求路径如果是`/person`，则该方法完整的请求路径是：`http://localhost:8080/person`。

### 注入使用

**将接口注入到其它Service中即可使用！**

```java
@Service
public class TestService {

    @Autowired
    private HttpApi httpApi;

    public void test() {
        // 通过httpApi发起http请求
    }
}
```

**默认情况下，自动使用`SpringBoot`扫描路径进行`retrofitClient`注册**。你也可以在配置类加上`@RetrofitScan`手工指定扫描路径。

## HTTP请求相关注解

`HTTP`请求相关注解，全部使用了`retrofit`原生注解。**详细信息可参考官方文档：[retrofit官方文档](https://square.github.io/retrofit/)**，以下是一个简单说明。

| 注解分类|支持的注解 |
|------------|-----------|
|请求方式|`@GET` `@HEAD` `@POST` `@PUT` `@DELETE` `@OPTIONS` `@HTTP`|
|请求头|`@Header` `@HeaderMap` `@Headers`|
|Query参数|`@Query` `@QueryMap` `@QueryName`|
|path参数|`@Path`|
|form-encoded参数|`@Field` `@FieldMap` `@FormUrlEncoded`|
| 请求体 |`@Body`|
|文件上传|`@Multipart` `@Part` `@PartMap`|
|url参数|`@Url`|

## 配置项说明

组件支持了多个可配置的属性，用来应对不同的业务场景，具体可支持的配置项及默认值如下：

**注意：应用只需要配置要更改的配置项！**

```yaml
retrofit:
   # 全局转换器工厂
   global-converter-factories:
      - com.github.lianjiatech.retrofit.spring.boot.core.BasicTypeConverterFactory
      - retrofit2.converter.jackson.JacksonConverterFactory
   # 全局调用适配器工厂
   global-call-adapter-factories:
      - com.github.lianjiatech.retrofit.spring.boot.core.BodyCallAdapterFactory
      - com.github.lianjiatech.retrofit.spring.boot.core.ResponseCallAdapterFactory

   # 全局日志打印配置
   global-log:
      # 启用日志打印
      enable: true
      # 全局日志打印级别
      log-level: info
      # 全局日志打印策略
      log-strategy: basic

   # 全局重试配置
   global-retry:
      # 是否启用全局重试
      enable: false
      # 全局重试间隔时间
      interval-ms: 100
      # 全局最大重试次数
      max-retries: 2
      # 全局重试规则
      retry-rules:
         - response_status_not_2xx
         - occur_io_exception

   # 熔断降级配置
   degrade:
      # 熔断降级类型。默认none，表示不启用熔断降级
      degrade-type: none
      # 全局sentinel降级配置
      global-sentinel-degrade:
         # 是否开启
         enable: false
         # 各降级策略对应的阈值。平均响应时间(ms)，异常比例(0-1)，异常数量(1-N)
         count: 1000
         # 熔断时长，单位为 s
         time-window: 5
         # 降级策略（0：平均响应时间；1：异常比例；2：异常数量）
         grade: 0

      # 全局resilience4j降级配置
      global-resilience4j-degrade:
         # 是否开启
         enable: false
         # 根据该名称从#{@link CircuitBreakerConfigRegistry}获取CircuitBreakerConfig，作为全局熔断配置
         circuit-breaker-config-name: defaultCircuitBreakerConfig
```

## 高级功能

### 自定义OkHttpClient属性

通过实现`SourceOkHttpClientRegistrar`接口，可以实现自定义注册。然后使用`@RetrofitClient`的`sourceOkHttpClient`属性指定`源OkHttpClient`。
组件会基于该`OkHttpClient`创建当前接口使用的`OkHttpClient`。可以配置超时时间、代理、连接池、分发等等属性。

```java

@Slf4j
@Component
public class CustomSourceOkHttpClientRegistrar implements SourceOkHttpClientRegistrar {

   @Override
   public void register(SourceOkHttpClientRegistry registry) {

      // 替换默认的SourceOkHttpClient
      registry.register(Constants.DEFAULT_SOURCE_OK_HTTP_CLIENT, new OkHttpClient.Builder()
              .addInterceptor(chain -> {
                 log.info("============替换默认的SourceOkHttpClient=============");
                 return chain.proceed(chain.request());
              })
              .build());

      // 添加新的SourceOkHttpClient
      registry.register("testSourceOkHttpClient", new OkHttpClient.Builder()
              .addInterceptor(chain -> {
                 log.info("============使用testSourceOkHttpClient=============");
                 return chain.proceed(chain.request());
              })
              .build());
   }
}
```

### 注解式拦截器

很多时候，我们希望某个接口下的某些http请求执行统一的拦截处理逻辑。为了支持这个功能，组件提供了**注解式拦截器**，做到了**基于url路径的匹配拦截**。使用的步骤主要分为2步：

1. 继承`BasePathMatchInterceptor`编写拦截处理器；
2. 接口上使用`@Intercept`进行标注。如需配置多个拦截器，在接口上标注多个`@Intercept`注解即可！

下面以*给指定请求的url后面拼接timestamp时间戳*为例，介绍下如何使用注解式拦截器。

#### 继承`BasePathMatchInterceptor`编写拦截处理器

```java
@Component
public class TimeStampInterceptor extends BasePathMatchInterceptor {

    @Override
    public Response doIntercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url();
        long timestamp = System.currentTimeMillis();
        HttpUrl newUrl = url.newBuilder()
                .addQueryParameter("timestamp", String.valueOf(timestamp))
                .build();
        Request newRequest = request.newBuilder()
                .url(newUrl)
                .build();
        return chain.proceed(newRequest);
    }
}

```

#### 接口上使用`@Intercept`进行标注

```java
@RetrofitClient(baseUrl = "${test.baseUrl}")
@Intercept(handler = TimeStampInterceptor.class, include = {"/api/**"}, exclude = "/api/test/savePerson")
@Intercept(handler = TimeStamp2Interceptor.class) // 需要多个，直接添加即可
public interface HttpApi {

    @GET("person")
    Result<Person> getPerson(@Query("id") Long id);

    @POST("savePerson")
    Result<Person> savePerson(@Body Person person);
}
```

上面的`@Intercept`配置表示：拦截`HttpApi`接口下`/api/**`路径下（排除`/api/test/savePerson`）的请求，拦截处理器使用`TimeStampInterceptor`。

### 扩展注解式拦截器

有的时候，我们需要在**拦截注解**动态传入一些参数，然后再执行拦截的时候需要使用这个参数。这种时候，我们可以扩展实现**自定义拦截注解**。`自定义拦截注解`必须使用`@InterceptMark`标记，并且**注解中必须包括`include()、exclude()、handler()`属性信息**。使用的步骤主要分为3步：

1. 自定义拦截注解
2. 继承`BasePathMatchInterceptor`编写拦截处理器
3. 接口上使用自定义拦截注解；

例如我们需要**在请求头里面动态加入`accessKeyId`、`accessKeySecret`签名信息才能正常发起http请求**，这个时候**可以自定义一个加签拦截器注解`@Sign`来实现**。下面以自定义`@Sign`拦截注解为例进行说明。


#### 自定义`@Sign`注解

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@InterceptMark
public @interface Sign {
    /**
     * 密钥key
     * 支持占位符形式配置。
     *
     * @return
     */
    String accessKeyId();

    /**
     * 密钥
     * 支持占位符形式配置。
     *
     * @return
     */
    String accessKeySecret();

    /**
     * 拦截器匹配路径
     *
     * @return
     */
    String[] include() default {"/**"};

    /**
     * 拦截器排除匹配，排除指定路径拦截
     *
     * @return
     */
    String[] exclude() default {};

    /**
     * 处理该注解的拦截器类
     * 优先从spring容器获取对应的Bean，如果获取不到，则使用反射创建一个！
     *
     * @return
     */
    Class<? extends BasePathMatchInterceptor> handler() default SignInterceptor.class;
}
```

扩展`自定义拦截注解`有以下2点需要注意：

1. `自定义拦截注解`必须使用`@InterceptMark`标记。
2. 注解中必须包括`include()、exclude()、handler()`属性信息。

#### 实现`SignInterceptor`

```java
@Component
public class SignInterceptor extends BasePathMatchInterceptor {

    private String accessKeyId;

    private String accessKeySecret;

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    @Override
    public Response doIntercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request newReq = request.newBuilder()
                .addHeader("accessKeyId", accessKeyId)
                .addHeader("accessKeySecret", accessKeySecret)
                .build();
        return chain.proceed(newReq);
    }
}
```

**上述`accessKeyId`和`accessKeySecret`字段值会依据`@Sign`注解的`accessKeyId()`和`accessKeySecret()`值自动注入，如果`@Sign`指定的是占位符形式的字符串，则会取配置属性值进行注入**。另外，**`accessKeyId`和`accessKeySecret`字段必须提供`setter`方法**。

#### 接口上使用`@Sign`

```java
@RetrofitClient(baseUrl = "${test.baseUrl}")
@Sign(accessKeyId = "${test.accessKeyId}", accessKeySecret = "${test.accessKeySecret}", exclude = {"/api/test/person"})
public interface HttpApi {

    @GET("person")
    Result<Person> getPerson(@Query("id") Long id);

    @POST("savePerson")
    Result<Person> savePerson(@Body Person person);
}
```

这样就能在指定url的请求上，自动加上签名信息了。

### 日志打印

组件支持支持全局日志打印和声明式日志打印。

#### 全局日志打印

全局日志打印默认开启，开启后所有`HTTP`请求都会打印日志，默认配置如下：

```yaml
retrofit:
   # 全局日志打印配置
   global-log:
      # 启用日志打印
      enable: true
      # 全局日志打印级别
      log-level: info
      # 全局日志打印策略
      log-strategy: basic
```

**4种日志打印策略含义如下**：

1. `NONE`：No logs.
2. `BASIC`：Logs request and response lines.
3. `HEADERS`：Logs request and response lines and their respective headers.
4. `BODY`：Logs request and response lines and their respective headers and bodies (if present).

#### 声明式日志打印

如果只需要部分请求才打印日志，可以在相关接口或者方法上使用`@Logging`注解。

#### 日志打印自定义扩展

如果需要修改日志打印行为，继承`LoggingInterceptor`，并将其配置成Spring bean即可！

#### 聚合日志打印

如果需要将同一个请求的日志聚合在一起打印，可配置`AggregateLoggingInterceptor`。

```java
 @Bean
public LoggingInterceptor loggingInterceptor(RetrofitProperties retrofitProperties){
        return new AggregateLoggingInterceptor(retrofitProperties.getGlobalLog());
        }
```

### 请求重试

组件支持支持全局重试和声明式重试。

#### 全局重试

全局重试默认关闭。开启之后，所有`HTTP`请求都会按照配置参数自动重试，默认配置项如下：

```yaml
retrofit:
  # 全局重试配置
  global-retry:
     # 是否启用全局重试
     enable: false
     # 全局重试间隔时间
     interval-ms: 100
     # 全局最大重试次数
     max-retries: 2
     # 全局重试规则
     retry-rules:
        - response_status_not_2xx
        - occur_io_exception
 ```

**重试规则支持三种配置**：

1. `RESPONSE_STATUS_NOT_2XX`：响应状态码不是`2xx`时执行重试；
2. `OCCUR_IO_EXCEPTION`：发生IO异常时执行重试；
3. `OCCUR_EXCEPTION`：发生任意异常时执行重试；

#### 声明式重试

如果只需要在指定某些请求才执行重试，可以使用声明式重试！具体就是在接口或者方法上声明`@Retry`注解。

#### 请求重试自定义扩展

如果需要修改请求重试行为，继承`RetryInterceptor`，并将其配置成Spring bean即可！

### 熔断降级

熔断降级功能默认关闭。当前支持`sentinel`和`resilience4j`两种实现。

```yaml
retrofit:
   # 熔断降级配置
   degrade:
      # 熔断降级类型。默认none，表示不启用熔断降级
      degrade-type: sentinel
```

#### sentinel熔断降级

配置`degrade-type=sentinel`开启。然后在相关接口或者方法上声明`@SentinelDegrade`即可。另外项目需要自行引入`sentinel`依赖。

```xml

<dependency>
   <groupId>com.alibaba.csp</groupId>
   <artifactId>sentinel-core</artifactId>
   <version>1.6.3</version>
</dependency>
```

通过以下配置可开启全局sentinel熔断降级：

```yaml
retrofit:
  # 熔断降级配置
  degrade:
    # 熔断降级类型。默认none，表示不启用熔断降级
    degrade-type: sentinel
    # 全局sentinel降级配置
    global-sentinel-degrade:
      # 是否开启
      enable: true
      # ...其他sentinel全局配置
```

#### resilience4j熔断降级

配置`degrade-type=resilience4j`开启。然后在相关接口或者方法上声明`@Resilience4jDegrade`即可。另外项目需要自行引入`resilience4j`依赖。

```xml

<dependency>
   <groupId>io.github.resilience4j</groupId>
   <artifactId>resilience4j-circuitbreaker</artifactId>
   <version>1.7.1</version>
</dependency>
```

通过以下配置可开启全局resilience4j熔断降级：

```yaml
retrofit:
   # 熔断降级配置
   degrade:
      # 熔断降级类型。默认none，表示不启用熔断降级
      degrade-type: resilience4j
      # 全局resilience4j降级配置
      global-resilience4j-degrade:
         # 是否开启
         enable: true
         # 根据该名称从#{@link CircuitBreakerConfigRegistry}获取CircuitBreakerConfig，作为全局熔断配置
         circuit-breaker-config-name: defaultCircuitBreakerConfig
```

通过实现`CircuitBreakerConfigRegistrar`接口，可以注入自定义CircuitBreakerConfig。 然后使用`@Resilience4jDegrade`的`circuitBreakerConfigName`
指定采用熔断配置。

```java

@Component
public class CustomCircuitBreakerConfigRegistrar implements CircuitBreakerConfigRegistrar {

   @Override
   public void register(CircuitBreakerConfigRegistry registry) {

      // 替换默认的CircuitBreakerConfig
      registry.register(Constants.DEFAULT_CIRCUIT_BREAKER_CONFIG, CircuitBreakerConfig.ofDefaults());

      // 注册其它的CircuitBreakerConfig
      registry.register("testCircuitBreakerConfig", CircuitBreakerConfig.custom()
              .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.TIME_BASED)
              .failureRateThreshold(20)
              .minimumNumberOfCalls(5)
              .permittedNumberOfCallsInHalfOpenState(5)
              .build());
   }
}
```

#### 扩展熔断降级

如果用户需要使用其他的熔断降级实现，继承`BaseRetrofitDegrade`，并将其配置`bean`即可，具体可参考`SentinelRetrofitDegrade`。

#### @RetrofitClient设置fallback或者fallbackFactory (可选)

如果`@RetrofitClient`不设置`fallback`或者`fallbackFactory`，当触发熔断时，会直接抛出`RetrofitBlockException`异常。**用户可以通过设置`fallback`
或者`fallbackFactory`来定制熔断时的方法返回值**。`fallback`类必须是当前接口的实现类，`fallbackFactory`必须是`FallbackFactory<T>`
实现类，泛型参数类型为当前接口类型。另外，`fallback`和`fallbackFactory`实例必须配置成`Spring`容器的`Bean`。

**`fallbackFactory`相对于`fallback`，主要差别在于能够感知每次熔断的异常原因(cause)**。参考示例如下：

```java

@Slf4j
@Service
public class HttpDegradeFallback implements HttpDegradeApi {

   @Override
   public Result<Integer> test() {
      Result<Integer> fallback = new Result<>();
      fallback.setCode(100)
              .setMsg("fallback")
              .setBody(1000000);
      return fallback;
   }
}
```

```java

@Slf4j
@Service
public class HttpDegradeFallbackFactory implements FallbackFactory<HttpDegradeApi> {

   /**
    * Returns an instance of the fallback appropriate for the given cause
    *
    * @param cause fallback cause
    * @return 实现了retrofit接口的实例。an instance that implements the retrofit interface.
    */
   @Override
   public HttpDegradeApi create(Throwable cause) {
      log.error("触发熔断了! ", cause.getMessage(), cause);
      return new HttpDegradeApi() {
         @Override
         public Result<Integer> test() {
            Result<Integer> fallback = new Result<>();
            fallback.setCode(100)
                    .setMsg("fallback")
                    .setBody(1000000);
            return fallback;
         }
      }
   }
```

### 错误解码器

在`HTTP`发生请求错误(包括发生异常或者响应数据不符合预期)的时候，错误解码器可将`HTTP`相关信息解码到自定义异常中。你可以在`@RetrofitClient`注解的`errorDecoder()`
指定当前接口的错误解码器，自定义错误解码器需要实现`ErrorDecoder`接口：

```java
/**
 * 错误解码器。ErrorDecoder.
 * 当请求发生异常或者收到无效响应结果的时候，将HTTP相关信息解码到异常中，无效响应由业务自己判断
 *
 * When an exception occurs in the request or an invalid response result is received, the HTTP related information is decoded into the exception,
 * and the invalid response is determined by the business itself.
 *
 * @author 陈添明
 */
public interface ErrorDecoder {

    /**
     * 当无效响应的时候，将HTTP信息解码到异常中，无效响应由业务自行判断。
     * When the response is invalid, decode the HTTP information into the exception, invalid response is determined by business.
     *
     * @param request  request
     * @param response response
     * @return If it returns null, the processing is ignored and the processing continues with the original response.
     */
    default RuntimeException invalidRespDecode(Request request, Response response) {
        if (!response.isSuccessful()) {
            throw RetrofitException.errorStatus(request, response);
        }
        return null;
    }


    /**
     * 当请求发生IO异常时，将HTTP信息解码到异常中。
     * When an IO exception occurs in the request, the HTTP information is decoded into the exception.
     *
     * @param request request
     * @param cause   IOException
     * @return RuntimeException
     */
    default RuntimeException ioExceptionDecode(Request request, IOException cause) {
        return RetrofitException.errorExecuting(request, cause);
    }

    /**
     * 当请求发生除IO异常之外的其它异常时，将HTTP信息解码到异常中。
     * When the request has an exception other than the IO exception, the HTTP information is decoded into the exception.
     *
     * @param request request
     * @param cause   Exception
     * @return RuntimeException
     */
    default RuntimeException exceptionDecode(Request request, Exception cause) {
        return RetrofitException.errorUnknown(request, cause);
    }

}

```

### 微服务之间的HTTP调用

为了能够使用微服务调用，需要进行如下配置：

#### 配置`ServiceInstanceChooser`为`Spring`容器`Bean`

用户可以自行实现`ServiceInstanceChooser`接口，完成服务实例的选取逻辑，并将其配置成`Spring`容器的`Bean`。对于`Spring Cloud`
应用，组件提供了`SpringCloudServiceInstanceChooser`实现，用户只需将其配置成`Spring`的`Bean`即可。

```java
@Bean
@Autowired
public ServiceInstanceChooser serviceInstanceChooser(LoadBalancerClient loadBalancerClient) {
    return new SpringCloudServiceInstanceChooser(loadBalancerClient);
}
```

#### 使用`@Retrofit`的`serviceId`和`path`属性，可以实现微服务之间的HTTP调用

```java

@RetrofitClient(serviceId = "${jy-helicarrier-api.serviceId}", path = "/m/count", errorDecoder = HelicarrierErrorDecoder.class)
@Retry
public interface ApiCountService {

}
```

## 全局拦截器

### 全局应用拦截器

如果我们需要对整个系统的的http请求执行统一的拦截处理，可以自定义实现全局拦截器`GlobalInterceptor`, 并配置成`spring`容器中的`bean`！例如我们需要在整个系统发起的http请求，都带上来源信息。

```java

@Component
@Order(2)
public class SourceGlobalInterceptor implements GlobalInterceptor {

   @Autowired
   private TestService testService;

   @Override
   public Response intercept(Chain chain) throws IOException {
      Request request = chain.request();
      Request newReq = request.newBuilder()
              .addHeader("source", "test")
              .build();
      System.out.println("===========执行全局重试===========");
      testService.test();
      return chain.proceed(newReq);
   }
}
```

### 全局网络拦截器

只需要实现`NetworkInterceptor`接口 并配置成`spring`容器中的`bean`就支持自动织入全局网络拦截器。

## 调用适配器和数据转码器

### 调用适配器

`Retrofit`可以通过调用适配器`CallAdapterFactory`将`Call<T>`对象适配成接口方法的返回值类型。组件扩展2种`CallAdapterFactory`
实现：

1. `BodyCallAdapterFactory`
   - 默认启用，可通过配置`retrofit.enable-body-call-adapter=false`关闭
   - 同步执行http请求，将响应体内容适配成接口方法的返回值类型实例。
    - 除了`Retrofit.Call<T>`、`Retrofit.Response<T>`、`java.util.concurrent.CompletableFuture<T>`之外，其它返回类型都可以使用该适配器。
2. `ResponseCallAdapterFactory`
    - 默认启用，可通过配置`retrofit.enable-response-call-adapter=false`关闭
    - 同步执行http请求，将响应体内容适配成`Retrofit.Response<T>`返回。
    - 如果方法的返回值类型为`Retrofit.Response<T>`，则可以使用该适配器。

**Retrofit自动根据方法返回值类型选用对应的`CallAdapterFactory`执行适配处理！加上Retrofit默认的`CallAdapterFactory`，可支持多种形式的方法返回值类型：**

- 基础类型(`String`/`Long`/`Integer`/`Boolean`/`Float`/`Double`)：直接将响应内容转换为上述基础类型。
- 其它任意POJO类型： 将响应体内容适配成一个对应的POJO类型对象返回，如果http状态码不是2xx，直接抛错！（推荐）
- `CompletableFuture<T>`: 将响应体内容适配成`CompletableFuture<T>`对象返回！（异步调用推荐）
- `Void`: 不关注返回类型可以使用`Void`。如果http状态码不是2xx，直接抛错！（不关注返回值）
- `Response<T>`: 将响应内容适配成`Response<T>`对象返回！（不推荐）
- `Call<T>`: 不执行适配处理，直接返回`Call<T>`对象！（不推荐）

```java

    /**
     * 其他任意Java类型
     * 将响应体内容适配成一个对应的Java类型对象返回，如果http状态码不是2xx，直接抛错！
     * @param id
     * @return
     */
    @GET("person")
    Result<Person> getPerson(@Query("id") Long id);

    /**
     *  CompletableFuture<T>
     *  将响应体内容适配成CompletableFuture<T>对象返回
     * @param id
     * @return
     */
    @GET("person")
    CompletableFuture<Result<Person>> getPersonCompletableFuture(@Query("id") Long id);

    /**
     * Void
     * 不关注返回类型可以使用Void。如果http状态码不是2xx，直接抛错！
     * @param id
     * @return
     */
    @GET("person")
    Void getPersonVoid(@Query("id") Long id);

    /**
     *  Response<T>
     *  将响应内容适配成Response<T>对象返回
     * @param id
     * @return
     */
    @GET("person")
    Response<Result<Person>> getPersonResponse(@Query("id") Long id);

    /**
     * Call<T>
     * 不执行适配处理，直接返回Call<T>对象
     * @param id
     * @return
     */
    @GET("person")
    Call<Result<Person>> getPersonCall(@Query("id") Long id);

```

**我们也可以通过继承`CallAdapter.Factory`扩展实现自己的`CallAdapter`**！

组件支持通过`retrofit.global-call-adapter-factories`
配置全局调用适配器工厂，工厂实例优先从Spring容器获取，如果没有获取到，则反射创建。默认的全局调用适配器工厂是`[BodyCallAdapterFactory, ResponseCallAdapterFactory]`！

```yaml
retrofit:
  # 全局调用适配器工厂
  global-call-adapter-factories:
    - com.github.lianjiatech.retrofit.spring.boot.core.BodyCallAdapterFactory
    - com.github.lianjiatech.retrofit.spring.boot.core.ResponseCallAdapterFactory
```

针对每个Java接口，还可以通过`@RetrofitClient`注解的`callAdapterFactories()`指定当前接口采用的`CallAdapter.Factory`，指定的工厂实例依然优先从Spring容器获取。

**注意：如果`CallAdapter.Factory`没有`public`的无参构造器，请手动将其配置成`Spring`容器的`Bean`对象**！


### 数据转码器

`Retrofit`使用`Converter`将`@Body`注解标注的对象转换成请求体，将响应体数据转换成一个`Java`对象，可以选用以下几种`Converter`：

- [Gson](https://github.com/google/gson): com.squareup.Retrofit:converter-gson
- [Jackson](https://github.com/FasterXML/jackson): com.squareup.Retrofit:converter-jackson
- [Moshi](https://github.com/square/moshi/): com.squareup.Retrofit:converter-moshi
- [Protobuf](https://developers.google.com/protocol-buffers/): com.squareup.Retrofit:converter-protobuf
- [Wire](https://github.com/square/wire): com.squareup.Retrofit:converter-wire
- [Simple XML](http://simple.sourceforge.net/): com.squareup.Retrofit:converter-simplexml
- [JAXB](https://docs.oracle.com/javase/tutorial/jaxb/intro/index.html): com.squareup.retrofit2:converter-jaxb
- fastJson：com.alibaba.fastjson.support.retrofit.Retrofit2ConverterFactory

组件支持通过`retrofit.global-converter-factories`配置全局数据转换器工厂，转换器工厂实例优先从Spring容器获取，如果没有获取到，则反射创建。
默认的全局数据转换器工厂是`retrofit2.converter.jackson.JacksonConverterFactory`。如果需要修改jackson配置，自行覆盖`JacksonConverterFactory`的`bean`
配置即可。

```yaml
retrofit:
   # 全局转换器工厂
   global-converter-factories:
      - com.github.lianjiatech.retrofit.spring.boot.core.BasicTypeConverterFactory
      - retrofit2.converter.jackson.JacksonConverterFactory
```

针对每个Java接口，还可以通过`@RetrofitClient`注解的`converterFactories()`指定当前接口采用的`Converter.Factory`，指定的转换器工厂实例依然优先从Spring容器获取。

### 元注解

`@RetrofitClient`、`@Retry`、`@Logging`、`@Resilience4jDegrade`等注解支持元注解、继承以及`@AliasFor`。 我们可以随意组合、调整相关注解：

```java

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
@RetrofitClient(baseUrl = "${test.baseUrl}")
@Logging(logLevel = LogLevel.WARN)
@Retry(intervalMs = 200)
public @interface MyRetrofitClient {

   @AliasFor(annotation = RetrofitClient.class, attribute = "converterFactories")
   Class<? extends Converter.Factory>[] converterFactories() default {GsonConverterFactory.class};

   @AliasFor(annotation = Logging.class, attribute = "logStrategy")
   LogStrategy logStrategy() default LogStrategy.BODY;
}
```

## 其他功能示例

### form参数接口调用

```java
 @FormUrlEncoded
@POST("token/verify")
 Object tokenVerify(@Field("source") String source,@Field("signature") String signature,@Field("token") String token);


@FormUrlEncoded
@POST("message")
CompletableFuture<Object> sendMessage(@FieldMap Map<String, Object> param);
```

### 上传文件

#### 构建MultipartBody.Part

```java
// 对文件名使用URLEncoder进行编码
public ResponseEntity importTerminology(MultipartFile file){
        String fileName=URLEncoder.encode(Objects.requireNonNull(file.getOriginalFilename()),"utf-8");
        okhttp3.RequestBody requestBody=okhttp3.RequestBody.create(MediaType.parse("multipart/form-data"),file.getBytes());
        MultipartBody.Part part=MultipartBody.Part.createFormData("file",fileName,requestBody);
        apiService.upload(part);
        return ok().build();
        }
```

#### http上传接口

```java
@POST("upload")
@Multipart
Void upload(@Part MultipartBody.Part file);

```

### 下载文件

#### http下载接口

```java
@RetrofitClient(baseUrl = "https://img.ljcdn.com/hc-picture/")
public interface DownloadApi {

    @GET("{fileKey}")
    Response<ResponseBody> download(@Path("fileKey") String fileKey);
}

```

#### http下载使用

```java
@SpringBootTest(classes = RetrofitTestApplication.class)
@RunWith(SpringRunner.class)
public class DownloadTest {
    @Autowired
    DownloadApi downLoadApi;

    @Test
    public void download() throws Exception {
        String fileKey = "6302d742-ebc8-4649-95cf-62ccf57a1add";
        Response<ResponseBody> response = downLoadApi.download(fileKey);
        ResponseBody responseBody = response.body();
        // 二进制流
        InputStream is = responseBody.byteStream();

        // 具体如何处理二进制流，由业务自行控制。这里以写入文件为例
        File tempDirectory = new File("temp");
        if (!tempDirectory.exists()) {
            tempDirectory.mkdir();
        }
        File file = new File(tempDirectory, UUID.randomUUID().toString());
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        byte[] b = new byte[1024];
        int length;
        while ((length = is.read(b)) > 0) {
            fos.write(b, 0, length);
        }
        is.close();
        fos.close();
    }
}
```

### 动态URL

使用`@url`注解可实现动态URL。

**注意：`@url`必须放在方法参数的第一个位置。原有定义`@GET`、`@POST`等注解上，不需要定义端点路径**！

```java
 @GET
 Map<String, Object> test3(@Url String url,@Query("name") String name);

```

### DELETE请求传请求体

```java
@HTTP(method = "DELETE", path = "/user/delete", hasBody = true)
```

### GET请求传请求体

**okhttp3自身不支持`GET`请求带请求体**。源码如下：

![image](https://user-images.githubusercontent.com/30620547/108949806-0a9f7780-76a0-11eb-9eb4-326d5d546e98.png)

![image](https://user-images.githubusercontent.com/30620547/108949831-1ab75700-76a0-11eb-955c-95d324084580.png)

作者给出了具体原因，可以参考这个issue：https://github.com/square/okhttp/issues/3154

but，如果实在需要这么干，可以使用`@HTTP(method = "get", path = "/user/get", hasBody = true)`。使用小写`get`绕过上述限制。


## 反馈建议

如有任何问题，欢迎提issue或者加QQ群反馈。

群号：806714302

![QQ群图片](https://github.com/LianjiaTech/retrofit-spring-boot-starter/blob/master/qun.png)

