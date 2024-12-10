package ie.atu;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "member-service", url = "http://localhost:8080/member")
public interface MemberClient {

    @GetMapping("/getMember/{memberID}")
    Member getMemberById(@PathVariable("memberID") int memberID);
}
