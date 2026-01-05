package com.prakashmalla.sms.repository;

import com.prakashmalla.sms.core.repsoitory.BaseRepository;
import com.prakashmalla.sms.entity.AddressEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends BaseRepository<AddressEntity,Long> {
}
