package ie.atu;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final MemberClient memberClient;

    // Constructor-based injection
    public PaymentController(MemberClient memberClient) {
        this.memberClient = memberClient;
    }

    @PostMapping("/processPayment/{memberID}/{amount}")
    public String processPayment(@PathVariable int memberID, @PathVariable double amount) {
        // Fetch member details from Member Service
        Member member = memberClient.getMemberById(memberID);

        if (amount > 0) {
            return "Payment Successful for " + member.getName() + " (Member ID: " + memberID + ") with amount: " + amount;
        } else {
            return "Payment Failed for " + member.getName() + " (Member ID: " + memberID + ")";
        }
    }
}