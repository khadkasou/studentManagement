package com.prakashmalla.sms.repository;

import com.prakashmalla.sms.core.enums.StatusEnum;
import com.prakashmalla.sms.core.repsoitory.BaseRepository;
import com.prakashmalla.sms.entity.SubjectEntity;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SubjectRepository extends BaseRepository<SubjectEntity, Long> {
    SubjectEntity findBySubjectCode(String subjectCode);
    List<SubjectEntity> findAllByStatusIs(StatusEnum status);
    boolean existsBySubjectCodeAndIdNot(String subjectCode, Long id);

}
