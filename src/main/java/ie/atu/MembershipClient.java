package ie.atu;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "membership-service", url = "http://localhost:8082/membership")
public interface MembershipClient {
    @PostMapping("/addMembership/{memberID}/{amount}")
    Membership updateMembership(@PathVariable int memberID, @PathVariable double amount);
}