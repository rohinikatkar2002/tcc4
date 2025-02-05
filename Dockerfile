FROM ubuntu
RUN apt update
RUN apt install openjdk-21-jdk -y
CMD ["java", "-version"]
