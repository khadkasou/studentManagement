package com.prakashmalla.sms.repository;

import com.prakashmalla.sms.core.repsoitory.BaseRepository;
import com.prakashmalla.sms.entity.CourseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends BaseRepository<CourseEntity,Long> {
}
