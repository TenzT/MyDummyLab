package middleware.activemq.springjms.producer;

public interface ProducerService {
    void sendMessage(String message);
}
