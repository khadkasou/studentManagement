package com.prakashmalla.sms.service.impl;

import com.prakashmalla.sms.core.enums.StatusEnum;
import com.prakashmalla.sms.core.exception.GlobalException;
import com.prakashmalla.sms.core.payload.response.DataPaginationResponse;
import com.prakashmalla.sms.core.payload.response.GlobalResponse;
import com.prakashmalla.sms.core.payload.response.GlobalResponseBuilder;
import com.prakashmalla.sms.core.util.Helper;
import com.prakashmalla.sms.entity.CourseEntity;
import com.prakashmalla.sms.entity.SubjectEntity;
import com.prakashmalla.sms.payload.request.StatusChangeRequest;
import com.prakashmalla.sms.payload.request.SubjectDataRequest;
import com.prakashmalla.sms.payload.request.SubjectRequest;
import com.prakashmalla.sms.payload.response.SubjectResponse;
import com.prakashmalla.sms.repository.SubjectRepository;
import com.prakashmalla.sms.service.SubjectService;
import com.prakashmalla.sms.service.specification.SubjectSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final ModelMapper modelMapper;

    @Override
    public GlobalResponse createSubject(SubjectRequest request) {
        SubjectEntity subject = subjectRepository.findBySubjectCode(request.getSubjectCode());
        if (subject != null) {
            throw new GlobalException("Subject already exists");
        }
        subject = SubjectEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .subjectCode(request.getSubjectCode())
                .build();
        subject.setStatus(StatusEnum.ACTIVE);
        subjectRepository.save(subject);
        return GlobalResponseBuilder.buildSuccessResponse("Subject created");
    }

    @Override
    public GlobalResponse getSubjectById(Long id) {
        SubjectEntity subject = subjectRepository.findById(id)
                .orElseThrow(() -> new GlobalException("Subject not found"));
        SubjectResponse response = modelMapper.map(subject, SubjectResponse.class);
        return GlobalResponseBuilder.buildSuccessResponseWithData("Subject Found", response);
    }

    @Override
    public GlobalResponse getAllSubject() {
        List<SubjectEntity> responses = subjectRepository.findAllByStatusIs(StatusEnum.ACTIVE);
        List<SubjectResponse> subjectResponses = responses.stream().map(m -> modelMapper.map(m, SubjectResponse.class)).toList();
        return GlobalResponseBuilder.buildSuccessResponseWithData("All Subject Found", subjectResponses);
    }

    @Override
    public GlobalResponse findAllSubject(SubjectDataRequest request) {
        Specification<SubjectEntity> specification = SubjectSpecification.subjectFilter(request);
        Page<SubjectEntity> page = subjectRepository.findAll(specification, Helper.getPageable(request));
        List<SubjectResponse> subjectResponses = page.getContent().stream()
                .map(m -> modelMapper.map(m, SubjectResponse.class)).toList();
        DataPaginationResponse response = new DataPaginationResponse(page.getTotalElements(), subjectResponses);
        return GlobalResponseBuilder.buildSuccessResponseWithData("All Subject Found", response);
    }

    @Override
    public GlobalResponse updateSubject(Long id, SubjectRequest request) {
        SubjectEntity subject = subjectRepository.findById(id).orElseThrow(() -> new GlobalException("Subject not found"));
        if (subjectRepository.existsBySubjectCodeAndIdNot(subject.getSubjectCode(), id)) {
            throw new GlobalException("Subject already exists with same code");
        }
        subject.setName(request.getName());
        subject.setDescription(request.getDescription());
        subject.setSubjectCode(request.getSubjectCode());
        subjectRepository.save(subject);
        return GlobalResponseBuilder.buildSuccessResponse("Subject Updated");
    }

    @Override
    public GlobalResponse changeSubjectStatus(Long id, StatusChangeRequest request) {
        SubjectEntity subject = subjectRepository.findById(id)
                .orElseThrow(() -> new GlobalException("Subject not found"));
        subject.setStatus(request.getStatus());
        subjectRepository.save(subject);
        return GlobalResponseBuilder.buildSuccessResponse("Status changed successfully");
    }
}
