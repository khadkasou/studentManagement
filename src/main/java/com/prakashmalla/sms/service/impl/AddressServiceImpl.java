package com.prakashmalla.sms.service.impl;

import com.prakashmalla.sms.core.enums.ApiStatusEnum;
import com.prakashmalla.sms.core.payload.response.GlobalResponse;
import com.prakashmalla.sms.entity.AddressEntity;
import com.prakashmalla.sms.mapper.AddressMapper;
import com.prakashmalla.sms.payload.request.AddressRequest;
import com.prakashmalla.sms.payload.response.AddressResponse;
import com.prakashmalla.sms.repository.AddressRepository;
import com.prakashmalla.sms.service.AddressService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Override
    public GlobalResponse createAddress(AddressRequest request) {
        AddressEntity address = addressMapper.toEntity(request);
        address = addressRepository.save(address);
        AddressResponse response = addressMapper.toResponse(address);
        return GlobalResponse.builder()
                .status(ApiStatusEnum.SUCCESS)
                .httpStatus(HttpStatus.CREATED)
                .message("Address created successfully")
                .data(response)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public GlobalResponse getAddressById(Long id) {
        AddressEntity address = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + id));
        AddressResponse response = addressMapper.toResponse(address);
        return GlobalResponse.builder()
                .status(ApiStatusEnum.SUCCESS)
                .message("Address retrieved successfully")
                .data(response)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public GlobalResponse getAllAddresses() {
        List<AddressResponse> responses = addressRepository.findAll().stream()
                .map(addressMapper::toResponse)
                .collect(Collectors.toList());
        return GlobalResponse.builder()
                .status(ApiStatusEnum.SUCCESS)
                .message("Addresses retrieved successfully")
                .data(responses)
                .build();
    }

    @Override
    public GlobalResponse updateAddress(Long id, AddressRequest request) {
        AddressEntity address = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + id));
        
        address.setCity(request.getCity());
        address.setStreet(request.getStreet());
        address.setState(request.getState());
        address.setZip(request.getZip());
        
        address = addressRepository.save(address);
        AddressResponse response = addressMapper.toResponse(address);
        return GlobalResponse.builder()
                .status(ApiStatusEnum.SUCCESS)
                .message("Address updated successfully")
                .data(response)
                .build();
    }

    @Override
    public GlobalResponse deleteAddress(Long id) {
        AddressEntity address = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + id));
        addressRepository.delete(address);
        return GlobalResponse.builder()
                .status(ApiStatusEnum.SUCCESS)
                .message("Address deleted successfully")
                .build();
    }
}
