package com.prakashmalla.sms.mapper;

import com.prakashmalla.sms.entity.AddressEntity;
import com.prakashmalla.sms.payload.request.AddressRequest;
import com.prakashmalla.sms.payload.response.AddressResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {

    AddressEntity toEntity(AddressRequest request);

    AddressResponse toResponse(AddressEntity entity);
}
