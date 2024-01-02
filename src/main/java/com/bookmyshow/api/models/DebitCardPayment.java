package com.bookmyshow.api.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DebitCardPayment extends Payment{
    private String cardNumber;

    public DebitCardPayment() {
        super(PaymentMethod.DEBIT_CARD);
    }
}
