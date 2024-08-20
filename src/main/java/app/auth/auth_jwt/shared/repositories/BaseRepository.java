package app.auth.auth_jwt.shared.repositories;

import app.auth.auth_jwt.shared.error.BadFilterException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable>
        extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    default Page<T> filterByAttributes(Map<String, String> filters, Pageable pageable) {
           Specification<T> spec = (root, query, builder) -> {
               List<Predicate> predicates =  filter(filters, (Root<T>) root, builder);
            return builder.and( predicates.toArray(new Predicate[0]));
        };
        return findAll(spec, pageable);
    } default Page<T> filterByAttributesOr(Map<String, String> filters, Pageable pageable) {
        Specification<T> spec = (root, query, builder) -> {
            List<Predicate> predicates =  filter(filters, (Root<T>) root, builder);
            return builder.or( predicates.toArray(new Predicate[0]));
        };
        return findAll(spec, pageable);
    }

    private List<Predicate> filter(Map<String, String> filters, Root<T> root, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        for (String attribute : filters.keySet()) {
            String filterValue = filters.get(attribute);
            if (filterValue != null) {
                predicates.add(createPredicate(root, builder, attribute, filterValue));
            }
        }

        return predicates;
    }

    private Predicate createPredicate(Root<T> root, CriteriaBuilder builder,
                                      String attribute, String filterValue) {
        try {
            String[] parts = filterValue.split(":");
            String operator = parts.length > 1 ? parts[0] : "like";
            String value = parts.length > 1 ? parts[1] : filterValue;
            List<String> ins = List.of(value.split(","));
            return switch (operator) {
                case "gt" -> builder.greaterThan(root.get(attribute), value);
                case "gte" -> builder.greaterThanOrEqualTo(root.get(attribute), value);
                case "lt" -> builder.lessThan(root.get(attribute), value);
                case "lte" -> builder.lessThanOrEqualTo(root.get(attribute), value);
                case "e" -> builder.equal(root.get(attribute), value);
                case "ne" -> builder.notEqual(root.get(attribute), value);
                case "notin" -> builder.not(root.get(attribute)).in(List.of(value.split(",")));
                case "in" -> root.get(attribute).in(List.of(value.split(",")));
                case "like" -> builder.like(root.get(attribute), "%" + value + "%");
                case "startsWith" -> builder.like(root.get(attribute), value + "%");
                case "endsWith" -> builder.like(root.get(attribute), "%" + value);
                default -> throw new BadFilterException("Invalid operator: " + operator);
            };
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new BadFilterException("Invalid operator: " + e.getMessage());
        }
    }
}
