FROM springcloudbaseimage:latest

COPY eureka.jar /eureka.jar

ENTRYPOINT [ "java", "-Dfile.encoding=UTF8", "-Duser.timezone=GMT+08", "-jar", "/eureka.jar"]
