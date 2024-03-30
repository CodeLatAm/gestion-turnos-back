package com.getion.turnos.service;

import com.getion.turnos.enums.PaymentEnum;
import com.getion.turnos.exception.PersonalizedIllegalArgumentException;
import com.getion.turnos.model.entity.Payment;
import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.model.entity.Voucher;
import com.getion.turnos.repository.PaymentRepository;
import com.getion.turnos.service.injectionDependency.MercadoPagoService;
import com.getion.turnos.service.injectionDependency.UserService;
import com.getion.turnos.service.injectionDependency.VoucherService;
import com.mercadopago.MercadoPagoConfig;;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.resources.preference.PreferenceItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class MercadoPagoServiceImpl implements MercadoPagoService {

    @Value("${mercado.pago.access-token}")
    private String accessToken;
    @Value("${server.url}")
    private String serverUrl;
    private final PaymentRepository paymentRepository;
    private final UserService userService;
    private final VoucherService voucherService;

    @Override
    public String createOrderPayment(Payment order) throws MPException, MPApiException {
        MercadoPagoConfig.setAccessToken(accessToken);
        log.info("Entrando al metodo createOrderPayment() en MercadoPagoServiceImpl");
        // Crea un objeto de preferencia
        PreferenceClient client = new PreferenceClient();
        PreferenceItemRequest preferenceItemRequest = PreferenceItemRequest.builder()
                .currencyId("ARS")
                .title("Plan pro")
                .description("Activando plan pro")
                .pictureUrl("src/main/resources/static/logo.png")
                .quantity(1)
                .unitPrice(order.getTotal())
                .build();
        // Agrego informacion del usuario
        PreferencePayerRequest payerRequest = PreferencePayerRequest.builder()
                .name(order.getUser().getName())
                .email(order.getUser().getUsername())
                .surname(order.getUser().getLastname())
                .build();
        // Agrego url de respuestas
        PreferenceBackUrlsRequest backUrlsRequest = PreferenceBackUrlsRequest.builder()
                .success("http://localhost:4200/home/payments") // <-- Aquí se cambia la URL de éxito a la URL de respuesta genérica
                .failure("http://localhost:4200/home/payments") // <-- También se cambia la URL de fallo a la URL de respuesta genérica
                .pending("http://localhost:4200/home/payments") // <-- También se cambia la URL de pendiente a la URL de respuesta genérica
                .build();

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(Collections.singletonList(preferenceItemRequest))  // Utiliza Collections.singletonList para crear una lista con un solo elemento
                .payer(payerRequest)
                .backUrls(backUrlsRequest)
                .metadata(Map.of(
                        "purchase_reference", order.getOrderReferenceExternal()  // Corregido el nombre de la clave
                ))
                .marketplace("Agenda de turnos API")
                .notificationUrl(serverUrl + "/mercado-pago/notify?source_news=webhooks")
                .binaryMode(true)
                .expires(true)
                .expirationDateFrom(OffsetDateTime.now())
                .expirationDateTo(OffsetDateTime.now().plus(Duration.ofHours(24)))
                //.autoReturn("approved")
                .build();
        Preference preference = client.create(preferenceRequest);
        this.createLogsForPaymentOrder(preference);

        return preference.getInitPoint();
    }

    private void createLogsForPaymentOrder(Preference preference) {
        log.info("Info de la preferencia del pago:");
        log.info("ID: " + preference.getId());
        log.info("Items:");
        for (PreferenceItem item : preference.getItems()) {
            log.info("  - ID: " + item.getId());
            // Loguear otros campos de PreferenceItem según sea necesario
        }
        log.info("Payer: " + preference.getPayer());
        log.info("Client ID: " + preference.getClientId());
        log.info("Payment Methods: " + preference.getPaymentMethods());
        log.info("Back URLs: " + preference.getBackUrls());
        log.info("Shipments: " + preference.getShipments());
        log.info("Notification URL: " + preference.getNotificationUrl());
        log.info("Statement Descriptor: " + preference.getStatementDescriptor());
        log.info("External Reference: " + preference.getExternalReference());
        log.info("Expires: " + preference.getExpires());
        log.info("Date of Expiration: " + preference.getDateOfExpiration());
        log.info("Expiration Date From: " + preference.getExpirationDateFrom());
        log.info("Expiration Date To: " + preference.getExpirationDateTo());
        log.info("Collector ID: " + preference.getCollectorId());
        log.info("Marketplace: " + preference.getMarketplace());
        log.info("Marketplace Fee: " + preference.getMarketplaceFee());
        log.info("Additional Info: " + preference.getAdditionalInfo());
        log.info("Auto Return: " + preference.getAutoReturn());
        log.info("Operation Type: " + preference.getOperationType());
        log.info("Differential Pricing: " + preference.getDifferentialPricing());
        log.info("Processing Modes: " + preference.getProcessingModes());
        log.info("Binary Mode: " + preference.getBinaryMode());
        log.info("Taxes: " + preference.getTaxes());
        log.info("Tracks: " + preference.getTracks());
        log.info("Metadata: " + preference.getMetadata());
        log.info("Init Point: " + preference.getInitPoint());
        log.info("Sandbox Init Point: " + preference.getSandboxInitPoint());
        log.info("Date Created: " + preference.getDateCreated());
    }

    @Override
    public boolean processNotificationWebhook(Map<String, Object> data) {
        log.info("Metodo processNotificationWebhook():");
        if (isValidNotificationData(data)) {
            String idPayment = extractPaymentId(data);
            log.info("idPayment: " + idPayment);
            if (idPayment != null) {
                try {
                    com.mercadopago.resources.payment.Payment payment = getPaymentById(idPayment);
                    this.createLogsProcessNotificationWebhook(payment);

                    if (isPaymentApproved(payment)) {
                        String userEmail = payment.getPayer().getEmail();
                        log.info("Email en processNotificationWebhook():" + userEmail);
                        Optional<UserEntity> user = userService.findByUsername(userEmail);

                        Voucher voucher = this.createVoucher(payment);
                        voucher.setUser(user.get());
                        voucherService.save(voucher);
                        updateOrderStatus(payment);
                        return true;
                    }
                } catch (MPException | MPApiException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return false;
    }

    private Voucher createVoucher(com.mercadopago.resources.payment.Payment payment) {
        return Voucher.builder()
                .idTransaccion(payment.getId())
                .creationDateTime(payment.getDateCreated())
                .approvalDateTime(payment.getDateApproved())
                .statusDetail(payment.getStatusDetail())
                .status(payment.getStatus())
                .currencyId(payment.getCurrencyId())
                .transactionAmount(payment.getTransactionAmount())
                .installments(payment.getInstallments())
                .paymentTypeId(payment.getPaymentTypeId())
                .description(payment.getDescription())
                .build();
    }

    private void createLogsProcessNotificationWebhook(com.mercadopago.resources.payment.Payment payment) {
        log.info("Metodo processNotificationWebhook():");
        log.info("Información del pago:");
        log.info("ID: " + payment.getId());//este
        log.info("Fecha de creación: " + payment.getDateCreated());//este
        log.info("Fecha de aprobación: " + payment.getDateApproved());//este
        log.info("Última fecha de actualización: " + payment.getDateLastUpdated());
        log.info("Fecha de expiración: " + payment.getDateOfExpiration());
        log.info("Fecha de liberación del dinero: " + payment.getMoneyReleaseDate());
        log.info("Esquema de liberación de dinero: " + payment.getMoneyReleaseSchema());
        log.info("Tipo de operación: " + payment.getOperationType());
        log.info("ID del emisor: " + payment.getIssuerId());
        log.info("ID del método de pago: " + payment.getPaymentMethodId());
        log.info("ID del tipo de pago: " + payment.getPaymentTypeId());
        log.info("Estado del pago: " + payment.getStatus());//este
        log.info("Detalles del estado: " + payment.getStatusDetail());//este
        log.info("ID de la moneda: " + payment.getCurrencyId());//este
        log.info("Descripción: " + payment.getDescription());
        log.info("Modo en vivo: " + payment.isLiveMode());
        log.info("ID del patrocinador: " + payment.getSponsorId());
        log.info("Código de autorización: " + payment.getAuthorizationCode());
        log.info("ID del integrador: " + payment.getIntegratorId());
        log.info("ID de la plataforma: " + payment.getPlatformId());
        log.info("ID de la corporación: " + payment.getCorporationId());
        log.info("ID del colector: " + payment.getCollectorId());
        log.info("Datos del pagador:");
        log.info("Nombre: " + payment.getPayer().getFirstName());
        log.info("Nombre: " + payment.getPayer().getLastName());
        log.info("Correo electrónico: " + payment.getPayer().getEmail());
        log.info("Metadatos: " + payment.getMetadata());
        log.info("Información adicional: " + payment.getAdditionalInfo());
        log.info("Orden asociada: " + payment.getOrder());
        log.info("Referencia externa: " + payment.getExternalReference());
        log.info("Monto de la transacción: " + payment.getTransactionAmount());//este
        log.info("Monto de la transacción devuelto: " + payment.getTransactionAmountRefunded());
        log.info("Monto del cupón: " + payment.getCouponAmount());
        log.info("ID de diferenciación de precios: " + payment.getDifferentialPricingId());
        log.info("Cuotas: " + payment.getInstallments());//este
        log.info("Detalles de la transacción: " + payment.getTransactionDetails());
        log.info("Detalles de la tarifa: " + payment.getFeeDetails());
        log.info("Capturado: " + payment.isCaptured());
        log.info("Modo binario: " + payment.isBinaryMode());
        log.info("ID de llamada para autorización: " + payment.getCallForAuthorizeId());
        log.info("Descriptor de declaración: " + payment.getStatementDescriptor());
        log.info("Tarjeta: " + payment.getCard().toString());//este
        log.info("URL de notificación: " + payment.getNotificationUrl());
        log.info("URL de callback: " + payment.getCallbackUrl());
        log.info("Modo de procesamiento: " + payment.getProcessingMode());
        log.info("ID de la cuenta del comerciante: " + payment.getMerchantAccountId());
        log.info("Número del comerciante: " + payment.getMerchantNumber());
        log.info("Código de cupón: " + payment.getCouponCode());
        log.info("Monto neto: " + payment.getNetAmount());
        log.info("ID de la opción del método de pago: " + payment.getPaymentMethodOptionId());
        log.info("Impuestos: " + payment.getTaxes());
        log.info("Monto de impuestos: " + payment.getTaxesAmount());
        log.info("Moneda contraria: " + payment.getCounterCurrency());
        log.info("Monto de envío: " + payment.getShippingAmount());
        log.info("ID de POS: " + payment.getPosId());
        log.info("ID de tienda: " + payment.getStoreId());
        log.info("Esquema de deducción: " + payment.getDeductionSchema());
        log.info("Reembolsos: " + payment.getRefunds());
        log.info("Punto de interacción: " + payment.getPointOfInteraction());
        log.info("Método de pago: " + payment.getPaymentMethod().toString());//este
        log.info("Información de 3DS: " + payment.getThreeDSInfo());
        log.info("Metadatos internos: " + payment.getInternalMetadata());
    }

    private boolean isValidNotificationData(Map<String, Object> data) {
        return data.containsKey("type") && data.containsKey("action") && data.containsKey("data");
    }

    private String extractPaymentId(Map<String, Object> data) {
        Object dataObject = data.get("data");
        if (dataObject instanceof Map) {
            Map<String, Object> dataValues = (Map<String, Object>) dataObject;
            return (String) dataValues.get("id");
        }
        return null;
    }

    private com.mercadopago.resources.payment.Payment getPaymentById(String idPayment) throws MPException, MPApiException {
        MercadoPagoConfig.setAccessToken(accessToken);
        PaymentClient paymentClient = new PaymentClient();
        return paymentClient.get(Long.valueOf(idPayment));
    }

    private boolean isPaymentApproved(com.mercadopago.resources.payment.Payment payment) {
        return payment.getStatus().equals("approved") && payment.getStatusDetail().equals("accredited")
                || payment.getStatus().equals("rejected")
                || payment.getStatus().equals("cancelled");
    }
    private boolean isPaymentRejected(com.mercadopago.resources.payment.Payment payment){
        return payment.getStatus().equals("rejected") && payment.getStatusDetail().equals("cc_rejected_other_reason");
    }

    private void updateOrderStatus(com.mercadopago.resources.payment.Payment payment) {
        String purchaseReference = (String) payment.getMetadata().get("purchase_reference");
        Payment order = paymentRepository.findByOrderReferenceExternal(purchaseReference)
                .orElseThrow(() -> new RuntimeException("An error occurred trying to find an order with external reference " + purchaseReference));

        if (payment.getStatus().equals("approved")) {
            order.setPaymentStatus(PaymentEnum.APROBADO);
            log.info("El pago ha sido aprobado y acreditado.");
            order.setLastUpdate(LocalDate.now());
            order.updateStatus();
            paymentRepository.save(order);
        }
        if (payment.getStatus().equals("pending")) {
            log.info("El usuario no ha concluido el proceso de pago (por ejemplo, al generar un pago mediante boleto, éste concluirá en el momento en que el usuario realiza el pago en el lugar seleccionado).");
            throw new PersonalizedIllegalArgumentException("El usuario no ha concluido el proceso de pago (por ejemplo, al generar un pago mediante boleto, éste concluirá en el momento en que el usuario realiza el pago en el lugar seleccionado");
            //order.setPaymentStatus(PaymentEnum.PENDIENTE);
            //order.setLastUpdate(LocalDate.now());
            //paymentRepository.save(order);
        }
        if (payment.getStatus().equals("rejected")) {
            log.info("El pago fue rechazado (el usuario puede intentar pagar nuevamente).");
            throw new PersonalizedIllegalArgumentException("El pago fue rechazado (el usuario puede intentar pagar nuevamente).");

        }
        if (payment.getStatus().equals("cancelled")){
            log.info("El pago fue rechazado (el usuario puede intentar pagar nuevamente).");
            throw new PersonalizedIllegalArgumentException("El pago fue rechazado (el usuario puede intentar pagar nuevamente).");
        }


    }
}
