package com.nineplus.localhand.service.impl;

import com.nineplus.localhand.model.ChargeRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Refund;
import com.stripe.param.ChargeCaptureParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentStripeService {

    @Value("${STRIPE_SECRET_KEY}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    public Charge charge(ChargeRequest chargeRequest)
            throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", chargeRequest.getAmount());
        chargeParams.put("currency", chargeRequest.getCurrency());
        chargeParams.put("description", chargeRequest.getDescription());
        chargeParams.put("source", chargeRequest.getStripeToken());
        chargeParams.put("capture", chargeRequest.isCapture());
        return Charge.create(chargeParams);
    }

    public Charge retrieveCharge(String chargeId) throws StripeException {
        return Charge.retrieve(chargeId);
    }

    public Charge capture(Charge charge)
            throws StripeException {
        return charge.capture();
    }

    public Refund createRefund(Charge charge) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put(
                "charge",
                charge.getId()
        );
        return Refund.create(params);
    }

    public Refund retrieveRefund(String refundId) throws StripeException {
        return Refund.retrieve(refundId);
    }

    public Refund cancelRefund(Refund refund) throws StripeException {
        return refund.cancel();
    }
}
