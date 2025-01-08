package ie.atu;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentListener {

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void processMembershipEvent(MembershipEvent event) {
        System.out.println("Received message: " + event);

        // Business logic to process payment
        if ("NEW_MEMBERSHIP".equals(event.getStatus())) {
            System.out.println("Processing payment for Member ID: " + event.getMemberID());
        }
    }
}
