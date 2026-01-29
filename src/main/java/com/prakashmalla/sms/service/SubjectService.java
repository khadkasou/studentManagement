package com.prakashmalla.sms.service;

import com.prakashmalla.sms.core.payload.response.GlobalResponse;
import com.prakashmalla.sms.payload.request.*;

public interface SubjectService {

    GlobalResponse createSubject(SubjectRequest request);

    GlobalResponse getSubjectById(Long id);

    GlobalResponse getAllSubject();
    GlobalResponse findAllSubject(SubjectDataRequest request);

    GlobalResponse updateSubject(Long id, SubjectRequest request);

    GlobalResponse changeSubjectStatus(Long id, StatusChangeRequest request);
}
