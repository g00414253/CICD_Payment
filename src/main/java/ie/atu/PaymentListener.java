package ie.atu;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentListener {

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void processMembershipEvent(MembershipEvent event) {
        System.out.println("Received message from RabbitMQ: " + event);

        // Business logic for processing payment
        if ("NEW_MEMBERSHIP".equals(event.getStatus())) {
            System.out.println("Processing payment for Member ID: " + event.getMemberID());
            // Additional payment processing logic can go here
        } else {
            System.out.println("Unhandled event status: " + event.getStatus());
        }
    }
}
