package payment.payment_project.service;

import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import payment.payment_project.common.constants.PaymentConstants;
import payment.payment_project.common.utils.DataFormatUtil;
import payment.payment_project.common.utils.EncryptUtil;
import payment.payment_project.common.utils.valid.ValidationUtil;
import payment.payment_project.controller.response.RetrievePaymentResponse;
import payment.payment_project.controller.response.CardPaymentResponse;
import payment.payment_project.domain.Payment;
import payment.payment_project.repository.PaymentRepository;
import payment.payment_project.service.dto.PaymentDto;

@Service
@AllArgsConstructor
public class PaymentService {

    private PaymentRepository paymentRepository;

    /**
     *  결제
     */
    public CardPaymentResponse createPayment(PaymentDto paymentDto) {
        // paymentDto 유효성 검증
        ValidationUtil.checkBusinessRules(paymentDto);

        // 관리 번호 생성
        String transactionId = generateTransactionId();

        // 데이터 생성
        String data = generateData(paymentDto);

        // 공통 헤더 생성
        String commonHeader = generateCommonHeader(data, PaymentConstants.PAYMENT, transactionId);

        // 카드 사에 보낼 데이터 생성 (공통 헤더 + 기본 데이터)
        String stringData = generateStringData(commonHeader, data);

        Payment payment = PaymentDto.toEntity(paymentDto, transactionId, stringData);

        paymentRepository.save(payment);

        return new CardPaymentResponse().builder()
            .transactionId(transactionId)
            .stringData(stringData)
            .build();
    }

    /**
     * 관리 번호 생성 메서드
     */
    private String generateTransactionId() {
        return UUID.randomUUID()
            .toString()
            .replace("-", "")
            .substring(0, 20);
    }

    /**
     * 데이터 생성 메서드
     */
    private String generateData(PaymentDto paymentDto) {
        StringBuilder builder = new StringBuilder();
        builder.append(DataFormatUtil.stringToLongFormat(paymentDto.getCardNumber(), 20, PaymentConstants.NUMBER_LEFT))
            .append(DataFormatUtil.stringToLongFormat(paymentDto.getInstallmentMonths(), 2, PaymentConstants.NUMBER_ZERO))
            .append(DataFormatUtil.stringToLongFormat(paymentDto.getExpiryDate(), 4, PaymentConstants.NUMBER_LEFT))
            .append(DataFormatUtil.stringToLongFormat(paymentDto.getCvc(), 3, PaymentConstants.NUMBER_LEFT))
            .append(DataFormatUtil.formatNumber(paymentDto.getTransactionAmount(), 10, PaymentConstants.NUMBER_BASIC))
            .append(DataFormatUtil.formatNumber(paymentDto.getVat(), 10, PaymentConstants.NUMBER_ZERO))
            .append(DataFormatUtil.formatString(paymentDto.getTransactionId(), 20))
            .append(DataFormatUtil.formatString(paymentDto.getEncryptCardInfo(), 300))
            .append(DataFormatUtil.formatString(paymentDto.getEtc(), 47));

        return builder.toString();
    }

    /**
     * 공통 헤더 생성 메서드
     */
    private String generateCommonHeader(String data, String dataType, String transactionId) {
        int dataLength = DataFormatUtil.getByteLength(data) + DataFormatUtil.getByteLength(dataType) + DataFormatUtil.getByteLength(transactionId);

        return new StringBuilder()
            .append(DataFormatUtil.formatNumber(Long.valueOf((long) dataLength), 4, PaymentConstants.NUMBER_BASIC))
            .append(DataFormatUtil.formatString(dataType, 10))
            .append(DataFormatUtil.formatString(transactionId, 20))
            .toString();
    }

    /**
     * 카드 사로 전송할 stringData 생성 메서드 (commonheader + data)
     */
    private String generateStringData(String commonHeader, String data) {
        return commonHeader.concat(data);
    }

    public RetrievePaymentResponse getPaymentByTransactionId(String id) {
        return paymentRepository.findByTransactionId(id)
            .map(payment -> {

                String encryptedCardInfo = payment.getEncryptedCard();

                String[] decryptedCardInfo = new String[3];

                try {
                    decryptedCardInfo = EncryptUtil.decrypt(encryptedCardInfo).split("|");

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return RetrievePaymentResponse.builder()
                    .cardNumber(decryptedCardInfo[0])
                    .expiryDate(decryptedCardInfo[1])
                    .cvc(decryptedCardInfo[2])
                    .type(payment.getType())
                    .transactionAmount(payment.getTransactionAmount())
                    .vat(payment.getVat())
                    .build();
            })
            .orElseThrow(() -> new RuntimeException("TransactionId not found"));

    }


}
