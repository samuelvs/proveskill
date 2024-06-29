package com.proveskill.pwebproject.specification;
import org.springframework.data.jpa.domain.Specification;

import com.proveskill.pwebproject.model.Question;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class QuestionSpecifications {

    public static Specification<Question> searchByFields(String search) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (search != null && !search.isEmpty()) {
                String searchLower = search.toLowerCase();

                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + searchLower + "%"));

                Join<Question, String> tagsJoin = root.join("tags", JoinType.INNER);
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(tagsJoin), "%" + searchLower + "%"));

                Join<Question, String> alternativesJoin = root.join("alternatives", JoinType.INNER);
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(alternativesJoin), "%" + searchLower + "%"));;

                Join<Question, String> answerJoin = root.join("answer", JoinType.INNER);
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(answerJoin), "%" + searchLower + "%"));

                Integer level = parseLevel(searchLower);
                if (level != null) {
                    predicates.add(criteriaBuilder.equal(root.get("level"), level));
                }

                Integer type = parseType(searchLower);
                if (type != null) {
                    predicates.add(criteriaBuilder.equal(root.get("type"), type));
                }
            }

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    private static Integer parseLevel(String search) {
        String lowercaseSearch = search.toLowerCase();
        if ("muito facil".contains(lowercaseSearch)) {
            return 1;
        } else if ("facil".contains(lowercaseSearch)) {
            return 2;
        } else if ("medio".contains(lowercaseSearch)) {
            return 3;
        } else if ("dificil".contains(lowercaseSearch)) {
            return 4;
        } else if ("muito dificil".contains(lowercaseSearch)) {
            return 5;
        }
        return null;
    }
    
    private static Integer parseType(String search) {
        String lowercaseSearch = search.toLowerCase();
        if ("aberta".contains(lowercaseSearch)) {
            return 1;
        } else if ("escolha unica".contains(lowercaseSearch)) {
            return 2;
        } else if ("multipla escolha".contains(lowercaseSearch)) {
            return 3;
        } else if ("verdadeiro ou falso".contains(lowercaseSearch)) {
            return 4;
        }
        return null;
    }
}