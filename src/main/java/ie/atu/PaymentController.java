package ie.atu;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final MemberClient memberClient;
    private final MembershipClient membershipClient;

    // Constructor-based injection
    public PaymentController(MemberClient memberClient, MembershipClient membershipClient) {
        this.memberClient = memberClient;
        this.membershipClient = membershipClient;
    }

    @PostMapping("/processPayment/{memberID}/{amount}")
    public String processPayment(@PathVariable int memberID, @PathVariable double amount) {
        try {
            if (amount > 0) {
                // Fetch member details from Member Service
                Member member = memberClient.getMemberById(memberID);

                // Delegate membership updates to Membership Service
                Membership updatedMembership = membershipClient.updateMembership(memberID, amount);

                return String.format(
                        "Payment Successful for %s (Member ID: %d). Membership updated: %d months added. Status: %s.",
                        member.getName(),
                        memberID,
                        updatedMembership.getMembershipDuration(),
                        updatedMembership.getMembershipStatus()
                );
            } else {
                return String.format(
                        "Payment Failed for Member ID: %d. Amount must be greater than zero.",
                        memberID
                );
            }
        } catch (Exception e) {
            // Escape % characters in exception message
            String escapedMessage = e.getMessage().replace("%", "%%");
            return String.format(
                    "Error processing payment for Member ID: %d. Details: %s",
                    memberID,
                    escapedMessage
            );
        }
    }
}

/*  TESTING TALEND API ENTRIES
    http://localhost:8081/payment/processPayment/3/50.0
 */