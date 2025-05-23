package Caprish;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy(exposeProxy = true)
public class AppConfig {


}
