package payment.payment_project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import payment.payment_project.controller.request.CreateCardPaymentRequest;
import payment.payment_project.controller.response.CardPaymentResponse;
import payment.payment_project.service.PaymentService;
import payment.payment_project.service.dto.PaymentDto;

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
     * 카드 정보와 금액 정보를 입력 받아 카드사와 협의된 string 데이터로 저장
     *
     * @param createCardPaymentRequest
     * @return
     */
    @PostMapping("/card")
    public ResponseEntity<Object> processCardPayment(@RequestBody @Valid CreateCardPaymentRequest createCardPaymentRequest) {

        PaymentDto paymentDto = PaymentDto.from(createCardPaymentRequest);

        CardPaymentResponse paymentResponse = paymentService.createPayment(paymentDto);
        HttpHeaders headers = createCommonHeader(paymentResponse.getTransactionId());

        return ResponseEntity.ok().header(String.valueOf(headers)).body(paymentResponse);
    }

    public void cancel() {
        // TODO
    }

    public void getPayment() {
        // TODO
    }


    private HttpHeaders createCommonHeader(String id) {
        // FIXME
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("data_length", "446");
        headers.add("data_type", "PAYMENT");
        headers.add("data_id", id);

        return headers;
    }
}
