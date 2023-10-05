package sosohappy.noticeservice.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import sosohappy.noticeservice.data.LikeNotice;
import sosohappy.noticeservice.data.Notice;
import sosohappy.noticeservice.service.NoticeService;
import sosohappy.noticeservice.util.Utils;

import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final NoticeService noticeService;
    private final Utils utils;
    private final ConcurrentHashMap<String, String> emailAndTokenMap;

    @KafkaListener(topics = "accessToken", groupId = "notice-service-accessToken-0000")
    public void addAccessToken(ConsumerRecord<byte[], byte[]> record){
        String email = new String(record.key());
        String accessToken = new String(record.value());

        emailAndTokenMap.put(email, accessToken);
    }

    @KafkaListener(topics = "expired", groupId = "notice-service-expired-0000")
    public void handleExpiredToken(ConsumerRecord<byte[], byte[]> record){

        String email = new String(record.key());

        emailAndTokenMap.remove(email);
    }

    @KafkaListener(topics = "noticeLike", groupId = "notice-service-noticeLike-0000")
    public void noticeLike(ConsumerRecord<byte[], byte[]> record){
        String liker = new String(record.key());
        String[] nicknameAndDateStr = new String(record.value()).split(",");
        String nickname = nicknameAndDateStr[0];
        Long date = Long.parseLong(nicknameAndDateStr[1]);

        noticeService.sendNotice(
                nickname,
                utils.objectToString(
                        Notice.builder()
                                .topic("like")
                                .data(
                                        LikeNotice.builder()
                                                .liker(liker)
                                                .date(date)
                                                .build()
                                )
                                .build()
                )
        );
    }

    @KafkaListener(topics = "resign", groupId = "notice-service-resign-0000")
    public void handleResignedUser(ConsumerRecord<byte[], byte[]> record){

        String email = new String(record.key());
        String nickname = new String(record.value());

        emailAndTokenMap.remove(email);
        noticeService.closeSession(nickname);
    }





}
