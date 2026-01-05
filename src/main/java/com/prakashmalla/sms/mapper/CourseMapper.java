package com.prakashmalla.sms.mapper;

import com.prakashmalla.sms.entity.CourseEntity;
import com.prakashmalla.sms.payload.request.CourseRequest;
import com.prakashmalla.sms.payload.response.CourseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseMapper {

    CourseEntity toEntity(CourseRequest request);

    CourseResponse toResponse(CourseEntity entity);
}
