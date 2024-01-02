package com.bookmyshow.api.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UPIPayment extends Payment{

    public UPIPayment() {
        super(PaymentMethod.UPI);
    }
}
