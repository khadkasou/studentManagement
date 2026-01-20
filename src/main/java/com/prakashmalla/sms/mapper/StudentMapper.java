package com.prakashmalla.sms.mapper;

import com.prakashmalla.sms.entity.StudentEntity;
import com.prakashmalla.sms.payload.request.StudentRequest;
import com.prakashmalla.sms.payload.response.StudentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper {

    StudentEntity toEntity(StudentRequest request);

    StudentResponse toResponse(StudentEntity entity);
}
