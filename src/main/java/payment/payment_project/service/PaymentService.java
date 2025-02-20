package payment.payment_project.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import payment.payment_project.common.constants.PaymentConstants;
import payment.payment_project.common.utils.PaymentDataUtil;
import payment.payment_project.common.utils.valid.ValidationUtil;
import payment.payment_project.controller.response.CardPaymentResponse;
import payment.payment_project.domain.Payment;
import payment.payment_project.repository.PaymentRepository;
import payment.payment_project.service.dto.PaymentDto;

@Service
@AllArgsConstructor
public class PaymentService {

    private PaymentRepository paymentRepository;
    private ModelMapper modelMapper;

    /**
     *  결제
     */
    public CardPaymentResponse createPayment(PaymentDto paymentDto) {

        // paymentDto 유효성 검증
        ValidationUtil.checkBusinessRules(paymentDto);

        // 관리 번호 생성
        String transactionId = PaymentDataUtil.generateTransactionId();

        // 데이터 생성
        String data = PaymentDataUtil.createData(paymentDto);

        // 공통 헤더 생성
        String commonHeader = PaymentDataUtil
            .createCommonHeader(data, PaymentConstants.PAYMENT, transactionId);

        // 카드 사에 보낼 데이터 생성 (공통 헤더 + 기본 데이터)
        String stringData = PaymentDataUtil.generateStringData(commonHeader, data);

        Payment payment = PaymentDto.toEntity(paymentDto, transactionId, stringData);

        paymentRepository.save(payment);

        return new CardPaymentResponse().builder()
            .transactionId(transactionId)
            .stringData(stringData)
            .build();
    }


}
