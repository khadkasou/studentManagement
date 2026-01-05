package com.prakashmalla.sms.repository;

import com.prakashmalla.sms.core.repsoitory.BaseRepository;
import com.prakashmalla.sms.entity.StudentEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends BaseRepository<StudentEntity,Long> {
}
