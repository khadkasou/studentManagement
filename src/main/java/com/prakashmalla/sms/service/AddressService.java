package com.prakashmalla.sms.service;

import com.prakashmalla.sms.core.payload.response.GlobalResponse;
import com.prakashmalla.sms.payload.request.AddressRequest;

public interface AddressService {

    GlobalResponse createAddress(AddressRequest request);

    GlobalResponse getAddressById(Long id);

    GlobalResponse getAllAddresses();

    GlobalResponse updateAddress(Long id, AddressRequest request);

    GlobalResponse deleteAddress(Long id);
}
