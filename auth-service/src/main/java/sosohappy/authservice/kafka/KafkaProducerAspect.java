package sosohappy.authservice.kafka;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class KafkaProducerAspect {

    private final KafkaTemplate<byte[], byte[]> kafkaTemplate;

    @AfterReturning(value = "@annotation(kafkaProducer)", returning = "result")
    public void handleKafkaProducer(JoinPoint joinPoint, KafkaProducer kafkaProducer, Object result){

        // topic : "accessToken"
        if(result instanceof String){
            String email = (String) joinPoint.getArgs()[0];
            kafkaTemplate.send(kafkaProducer.topic(), email.getBytes(), ((String) result).getBytes());
        }
    }

}
