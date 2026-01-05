package com.prakashmalla.sms.mapper;

import com.prakashmalla.sms.entity.StudentEntity;
import com.prakashmalla.sms.payload.request.StudentRequest;
import com.prakashmalla.sms.payload.response.StudentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.IGNORE, 
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = {CourseMapper.class, AddressMapper.class}
)
public interface StudentMapper {

    @Mapping(target = "courses", ignore = true)
    @Mapping(target = "studentCode", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "version", ignore = true)
    StudentEntity toEntity(StudentRequest request);

    StudentResponse toResponse(StudentEntity entity);
}
