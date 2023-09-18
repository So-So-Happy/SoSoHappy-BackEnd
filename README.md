# SoSoHappy
<img src="https://img.shields.io/badge/Google Cloud Platform-4285F4?style=flat&logo=Google Cloud&logoColor=white"/> <img src="https://img.shields.io/badge/kubernetes-326CE5?style=flat&logo=kubernetes&logoColor=white"/> <img src="https://img.shields.io/badge/Docker-2496ED?style=flat&logo=Docker&logoColor=white"/> <img src="https://img.shields.io/badge/Git-F05032?style=flat&logo=Git&logoColor=white"/> <img src="https://img.shields.io/badge/Github-181717?style=flat&logo=Github&logoColor=white"/> <img src="https://img.shields.io/badge/Jenkins-D24939?style=flat&logo=Jenkins&logoColor=white"/> <img src="https://img.shields.io/badge/Prometheus-E6522C?style=flat&logo=prometheus&logoColor=white"/> <img src="https://img.shields.io/badge/Grafana-F46800?style=flat&logo=grafana&logoColor=white"/> <img src="https://img.shields.io/badge/Apache Kafka-231F20?style=flat&logo=apachekafka&logoColor=white"/> <img src="https://img.shields.io/badge/MariaDB-003545?style=flat&logo=mariadb&logoColor=white"/> <img src="https://img.shields.io/badge/MongoDB-47A248?style=flat&logo=mongodb&logoColor=white"/> <img src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=flat&logo=Databricks&logoColor=white"> <img src="https://img.shields.io/badge/Spring Data MongoDB-6DB33F?style=flat&logo=Databricks&logoColor=white"> <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat&logo=Spring Boot&logoColor=white"/> <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=flat&logo=springsecurity&logoColor=white"/> <img src="https://img.shields.io/badge/Spring WebFlux-6DB33F?style=flat&logo=spring&logoColor=white"/><br><br>
![sosohappy-icon-removebg-preview](https://github.com/So-So-Happy/SoSoHappy-BackEnd/assets/85429793/6c3db330-b80f-4837-b286-95f45d0cd6ae)
- - -
이 문서는 클라이언트 3명, 서버 1명 총 4명의 팀원이 진행한 SNS 성격의 iOS 앱 SoSoHappy의 서버단 구조 및 기능을 설명합니다.
- - -
<br>

# 목차
- [Microservices](#microservices)
  + [구성 서버](#구성-서버)
  + [인증 서버](#인증-서버)
  + [피드 서버](#피드-서버)
  + [채팅 서버](#채팅-서버)
  + [알림 서버](#알림-서버)
- [Load Balancer](#load-balancer)
- [Message Queue](#message-queue)
  + [Topic](#topic)
- [Monitoring](#monitoring)
- [CI/CD](#cicd)
<br>
  
# Microservices
![microservices](https://github.com/So-So-Happy/SoSoHappy-BackEnd/assets/85429793/cc7f1911-f5f6-42d9-87ba-7fc379de7e93)

> 프로젝트의 서버단에 포함된 서비스들의 구조를 나타내는 그림입니다.<br><br>
> 구성, 인증, 피드, 채팅, 알림서버는 Rolling update 및 ReplicaSet 생성을 정의하기 위해 Deployment로 앱을 배포하였습니다.<br>
> Mysql, MongoDB 서버는 마운트한 폴더를 지속적으로 사용하게 위해 StatefulSet으로 앱을 배포하였습니다.<br><br>
> Grafana, Prometheus, Jenkins, Kafka는 워커노드 메모리 이슈로 쿠버네티스에 올리지 않고 VM 내부에서 도커 컨테이너로 실행하였습니다.

<br>

###  구성 서버 
외부에 노출시키면 안되는 property 파일들을 [서브모듈](https://github.com/So-So-Happy/SoSoHappy-BackEnd/tree/master/config-service)에 모아두고 타겟 서버에 전파하기 위해 구현한 서버입니다.<br>
`/actuator/busrefresh` 을 통해 [변경된 property를 한번에 업데이트](#topic) 할 수 있습니다.


###  인증 서버 
소셜 로그인 및 유저 정보 관련 작업을 수행하는 서버입니다.<br>
인증 서버에서 


###  피드 서버 



###  채팅 서버 



###  알림 서버 


# Load Balancer
![loadbalancer](https://github.com/So-So-Happy/SoSoHappy-BackEnd/assets/85429793/d25c9821-b53b-49bb-8acb-06afd2ba599e)
설명.

<br>

# Message Queue
![messagequeue](https://github.com/So-So-Happy/SoSoHappy-BackEnd/assets/85429793/62077e25-83f7-4e26-8600-add515d7b54d)

### Topic

+ springCloubBus

<br>

# CI/CD
![cicd](https://github.com/So-So-Happy/SoSoHappy-BackEnd/assets/85429793/7e6d4bf0-6d35-4a84-b1af-49ca17f3567a)
설명.

<br>

# Monitoring
![monitoring](https://github.com/So-So-Happy/SoSoHappy-BackEnd/assets/85429793/c0684412-8fe0-462e-868d-30522ca77800)

설명.

