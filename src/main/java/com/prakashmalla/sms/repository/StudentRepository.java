package com.prakashmalla.sms.repository;

import com.prakashmalla.sms.core.enums.StatusEnum;
import com.prakashmalla.sms.core.repsoitory.BaseRepository;
import com.prakashmalla.sms.entity.StudentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends BaseRepository<StudentEntity,Long> {

    List<StudentEntity> findAllByStatusIs(StatusEnum status);

    StudentEntity findByCitizenshipNumber(String citizenshipNumber);

    @Query("select s.studentCode from StudentEntity s")
    List<String> getAllStudentCode();
}
