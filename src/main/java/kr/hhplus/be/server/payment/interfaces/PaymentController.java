package kr.hhplus.be.server.payment.interfaces;

import kr.hhplus.be.server.payment.application.facade.PaymentFacade;
import kr.hhplus.be.server.payment.interfaces.request.PaymentRequest;
import kr.hhplus.be.server.payment.interfaces.response.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentFacade paymentFacade;

    // 예약 금액 지불
    @PostMapping("/{id}/reservations/payments")
    public ResponseEntity<PaymentResponse> reservationPayment(@PathVariable("id") Long id, @RequestBody PaymentRequest request, @RequestHeader(value = "Token") String token) {
        return new ResponseEntity<>(new PaymentResponse(paymentFacade.createPayment(id, request, token)), HttpStatus.OK);
    }
}
