package com.prakashmalla.sms.repository;

import com.prakashmalla.sms.core.enums.StatusEnum;
import com.prakashmalla.sms.core.repsoitory.BaseRepository;
import com.prakashmalla.sms.entity.CourseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends BaseRepository<CourseEntity,Long> {

    CourseEntity findByShortName(String shortName);
    List<CourseEntity> findAllByStatusIs(StatusEnum status);
}
