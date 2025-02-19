package payment.payment_project.service;

import java.util.UUID;
import org.springframework.stereotype.Service;
import payment.payment_project.common.DataFormatter;
import payment.payment_project.common.constants.PaymentConstants;
import payment.payment_project.controller.response.CardPaymentResponse;
import payment.payment_project.domain.Payment;
import payment.payment_project.service.dto.PaymentDto;

@Service
public class PaymentService {

    public CardPaymentResponse createPayment(PaymentDto paymentDto) {

        // TODO validation

        String transactionId = generateTransactionId();

        String data = DataFormatter.createData(paymentDto);

        String commonHeader = DataFormatter.createCommonHeader(data, PaymentConstants.PAYMENT, transactionId);

        String stringData = DataFormatter.generateStringData(commonHeader, data);


        return new CardPaymentResponse();
    }

    private String generateTransactionId() {
        return UUID.randomUUID()
            .toString()
            .replace("-", "")
            .substring(0, 20);
    }
}
