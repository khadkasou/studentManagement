package com.prakashmalla.sms.service.specification;

import com.prakashmalla.sms.entity.SubjectEntity;
import com.prakashmalla.sms.payload.request.SubjectDataRequest;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class SubjectSpecification {
    private SubjectSpecification() {
    }

    public static Specification<SubjectEntity> subjectFilter(SubjectDataRequest request) {

        return (root, query, criteriaBuilder) -> {

            Predicate finalPredicate = criteriaBuilder.conjunction();

            if (StringUtils.isNotBlank(request.getSearchText())) {
                Predicate searchTextPredicate = criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root
                                .get("name")), likePattern(request.getSearchText()
                                .toLowerCase())));
                finalPredicate = criteriaBuilder.and(finalPredicate, searchTextPredicate);
            }
            return finalPredicate;
        };
    }

    private static String likePattern(String value) {
        return "%" + value + "%";
    }
}
