package com.prakashmalla.sms.core.util;

import com.prakashmalla.sms.core.payload.request.PaginationRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Helper {
    public static Pageable getPageable(PaginationRequest request) {
        int pageNo = Math.max(request.getPageNo(), 0);
        int pageSize = request.getPageSize() > 0 ? request.getPageSize() : 5;
        String sortBy = Optional.ofNullable(request.getSortBy())
                .filter(s -> !s.isBlank())
                .orElse("createdDate");
        Sort.Direction direction =
                "asc" .equalsIgnoreCase(request.getDirection())
                        ? Sort.Direction.ASC
                        : Sort.Direction.DESC;
        return PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
    }
}


