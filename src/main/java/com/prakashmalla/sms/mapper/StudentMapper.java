package com.prakashmalla.sms.mapper;

import com.prakashmalla.sms.entity.StudentEntity;
import com.prakashmalla.sms.payload.request.StudentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    StudentEntity toUpdate(StudentRequest request, @MappingTarget StudentEntity student);

}
