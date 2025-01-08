package ie.atu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentControllerTest {

    private PaymentController paymentController;

    private MemberClient memberClient;
    private MembershipClient membershipClient;

    @BeforeEach
    void setUp() {
        memberClient = mock(MemberClient.class);
        membershipClient = mock(MembershipClient.class);
        paymentController = new PaymentController(memberClient, membershipClient);
    }

    @Test
    void testProcessPayment_ValidData() {
        // Mock member details
        Member mockMember = new Member(1, "John Doe", "john.doe@example.com", 30, "Active", 12);
        when(memberClient.getMemberById(1)).thenReturn(mockMember);

        // Mock membership update
        Membership mockMembership = new Membership(1, 1, "Active", 6, "2025-01-01", "2025-06-30");
        when(membershipClient.updateMembership(1, 50)).thenReturn(mockMembership);

        // Call the method
        String response = paymentController.processPayment(1, 50);

        // Assertions
        assertNotNull(response);
        assertTrue(response.contains("Payment Successful for John Doe (Member ID: 1)."));
        assertTrue(response.contains("6 months added. Status: Active."));

        // Verify the external calls
        verify(memberClient, times(1)).getMemberById(1);
        verify(membershipClient, times(1)).updateMembership(1, 50);
    }

    @Test
    void testProcessPayment_InvalidAmount() {
        // Call the method with an invalid amount
        String response = paymentController.processPayment(1, -10);

        // Print the response to debug (optional)
        System.out.println("Actual Response: " + response);

        // Assertions
        assertNotNull(response);
        assertTrue(response.contains("Payment Failed for Member ID: 1. Amount must be greater than zero."));
    }


    @Test
    void testProcessPayment_NonexistentMember() {
        // Mock MemberClient to throw an exception
        when(memberClient.getMemberById(999)).thenThrow(new RuntimeException("Member not found"));

        // Call the method
        String response = paymentController.processPayment(999, 50);

        // Assertions
        assertNotNull(response);
        assertTrue(response.contains("Error processing payment for Member ID: 999."));

        // Verify the member service was called, but membership service was not
        verify(memberClient, times(1)).getMemberById(999);
        verifyNoInteractions(membershipClient);
    }

    @Test
    void testProcessPayment_MembershipUpdateFails() {
        // Mock member details
        Member mockMember = new Member(1, "John Doe", "john.doe@example.com", 30, "Active", 12);
        when(memberClient.getMemberById(1)).thenReturn(mockMember);

        // Mock MembershipClient to throw an exception
        when(membershipClient.updateMembership(1, 50)).thenThrow(new RuntimeException("Membership update failed"));

        // Call the method
        String response = paymentController.processPayment(1, 50);

        // Assertions
        assertNotNull(response);
        assertTrue(response.contains("Error processing payment for Member ID: 1."));

        // Verify both services were called
        verify(memberClient, times(1)).getMemberById(1);
        verify(membershipClient, times(1)).updateMembership(1, 50);
    }
}
