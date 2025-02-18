package payment.payment_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import payment.payment_project.controller.request.CardPaymentRequest;
import payment.payment_project.service.PaymentService;

/**
 * Payment Controller
 *
 * @author  winnie
 * @version 1.0
 * @since   2025-02-18
 */

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private PaymentService paymentService;

    /**
     * 카드 결제 API
     *
     * @param cardPaymentRequest
     * @return
     */
    @PostMapping("/card")
    public void processCardPayment(@RequestBody CardPaymentRequest cardPaymentRequest) {

    }

    public void cancel() {
        // TODO
    }

    public void getPayment() {
        // TODO
    }

}
