package searchUtils;


import jakarta.persistence.criteria.CriteriaBuilder;
import model.Tag;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Component
public class SpecificationBuilder<T> {

    public Specification<T> getSpecificationFromFilters(List<Filter> filter) {

        Specification<T> specification =
                Specification.where(createSpecification(filter.remove(0)));
        for (Filter input : filter) {

            specification = specification.and(createSpecification(input));
        }
        return specification;
    }

    private Specification<T> createSpecification(Filter input) {
        return switch (input.getOperator()) {
            /* создание спецификаций с выбранными условиями поиска */
            case EQUALS -> (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(input.getField()),                    /* из какого столбца в БД ищем, */
                            castToRequiredType(root.get(input.getField()).getJavaType(), /* приводим строкое значение из запроса */
                                    input.getValue()));                                  /* к типу данных из БД и возвращаем значение */
            case NOT_EQ -> (root, query, criteriaBuilder) ->
                    criteriaBuilder.notEqual(root.get(input.getField()),
                            castToRequiredType(root.get(input.getField()).getJavaType(),
                                    input.getValue()));
            case GREATER_THAN -> (root, query, criteriaBuilder) ->
                    criteriaBuilder.gt(root.get(input.getField()),
                            (Number) castToRequiredType(
                                    root.get(input.getField()).getJavaType(),
                                    input.getValue()));
            case GREATER_THAN_AGE -> (root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get(input.getField()),
                            LocalDateTime.now().minusYears(Integer.parseInt(input.getValue())));
            case GREATER_THAN_DATE -> (root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get(input.getField()),
                            (input.getDate()));
            case LESS_THAN -> (root, query, criteriaBuilder) ->
                    criteriaBuilder.lt(root.get(input.getField()),
                            (Number) castToRequiredType(
                                    root.get(input.getField()).getJavaType(),
                                    input.getValue()));
            case LESS_THAN_AGE -> (root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get(input.getField()),
                            LocalDateTime.now().minusYears(Integer.parseInt(input.getValue())));

            case LESS_THAN_DATE -> (root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get(input.getField()),
                            input.getDate());
            case LIKE -> (root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get(input.getField()),
                            "%" + input.getValue() + "%");
            case IN -> (root, query, criteriaBuilder) ->
                    criteriaBuilder.in(root.get(input.getField()))
                            .value(castToRequiredTypes(
                                    root.get(input.getField()).getJavaType(),
                                    input.getValues()));
            case NOT_IN -> (root, query, criteriaBuilder) ->
                    criteriaBuilder.in(root.get(input.getField()))
                            .value(castToRequiredTypes(
                                    root.get(input.getField()).getJavaType(),
                                    input.getValues())).
                            not();


            default -> throw new RuntimeException("Operator not found");
        };
    }


    private Object castToRequiredType(Class fieldType, Object value) {


        if (fieldType.isAssignableFrom(Long.class)) {
            return Long.valueOf((String) value);
        } else if (fieldType.isAssignableFrom(Integer.class)) {
            return Integer.valueOf((String) value);
        } else if (Enum.class.isAssignableFrom(fieldType)) {
            return Enum.valueOf(fieldType, (String) value);
        }
        return value;
    }

    private Object castToRequiredTypes(Class fieldType, List<String> value) {
        List<Object> lists = new ArrayList<>();
        for (String s : value) {
            lists.add(castToRequiredType(fieldType, s));
        }
        return lists;
    }
}


