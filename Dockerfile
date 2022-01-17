FROM openjdk:11
EXPOSE 9090
ADD build/libs/workflex.jar workflex.jar
ENTRYPOINT ["java","-jar","/workflex.jar"]