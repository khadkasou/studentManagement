package com.prakashmalla.sms.controller;

import com.prakashmalla.sms.core.payload.response.GlobalResponse;
import com.prakashmalla.sms.payload.request.AddressRequest;
import com.prakashmalla.sms.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<GlobalResponse> createAddress(@RequestBody AddressRequest request) {
        GlobalResponse response = addressService.createAddress(request);
        return ResponseEntity.status(response.getHttpStatus())
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GlobalResponse> getAddressById(@PathVariable Long id) {
        GlobalResponse response = addressService.getAddressById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<GlobalResponse> getAllAddresses() {
        GlobalResponse response = addressService.getAllAddresses();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GlobalResponse> updateAddress(
            @PathVariable Long id,
            @RequestBody AddressRequest request) {
        GlobalResponse response = addressService.updateAddress(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalResponse> deleteAddress(@PathVariable Long id) {
        GlobalResponse response = addressService.deleteAddress(id);
        return ResponseEntity.ok(response);
    }
}
