# Spring Remote Config

Sample spring cloud remote config project to demonstrate issue with context refresh for immutable
@ConfigurationProperties created with @ConstructorBinding.

Linked issue at [https://github.com/spring-cloud/spring-cloud-config/issues/1547.](https://github.com/spring-cloud/spring-cloud-config/issues/1547)

The Spring application uses an embedded spring-cloud-config-server with JDBC repository as its backend.

In the main application context, 2 @ConfigurationProperties are defined.
1. A regular mutable data class
   [`com.example.demo.app.DemoMutableConfigurationProperties`](/src/main/kotlin/com/example/demo/app/DemoMutableConfigurationProperties.kt)
2. An immutable data class annotated with @ConstructorBinding
   [`com.example.demo.app.DemoImmutableConfigurationProperties`](/src/main/kotlin/com/example/demo/app/DemoImmutableConfigurationProperties.kt)

Each @ConfigurationProperties contains a single String field named `value` with a default value of '1' which is defined
in the `bootstrap.yaml` as
```yaml
demo:
  mutable:
    value: 1
  immutable:
    value: 1
```

A REST controller [`com.example.demo.app.DemoRestController`](/src/main/kotlin/com/example/demo/app/DemoRestController.kt)
exposes the values from the above @ConfigurationProperties and provides a POST endpoint for modifying the values.

The Actuator refresh endpoint is also exposed for trigerring the refresh of the Spring Context.

A SpringBoot test is defined in [`com.example.demo.app.ConfigurationRefreshTest`](/src/test/kotlin/com/example/demo/app/ConfigurationRefreshTest.kt)
which verifies the context refresh for both @ConfigurationProperties by calling the REST endpoints.

## Spring Versions

- Spring Boot: **2.2.4.RELEASE**
- Spring Cloud: **Hoxton.SR1**

## Git Clone
```bash
$ git clone https://github.com/Kiongku/spring-remote-config.git
$ cd spring-remote-config
```

## Run Test
```bash
$ ./gradlew check
```