package sosohappy.feedservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

@Configuration
public class UtilConfig {

    @Bean
    ConcurrentHashMap<String, Integer> StringAndIntegerMap(){
        return new ConcurrentHashMap<>();
    }

    @Bean
    ConcurrentHashMap<Integer, String> IntegerAndStringMap(){
        return new ConcurrentHashMap<>();
    }

    @Bean
    AtomicReference<List<List<Integer>>> twoDimensionIntegerList(){
        return new AtomicReference<>();
    }
}