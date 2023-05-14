package searchUtils;


import org.springframework.data.jpa.domain.Specification;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
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
                    criteriaBuilder.greaterThanOrEqualTo(root.get(input.getField()),
                            Period.between(LocalDate.parse(input.getValue()), LocalDate.now()).getYears());
            case GREATER_THAN_DATE -> (root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get(input.getField()),
                            (input.getValue()));
            case LESS_THAN -> (root, query, criteriaBuilder) ->
                    criteriaBuilder.lt(root.get(input.getField()),
                            (Number) castToRequiredType(
                                    root.get(input.getField()).getJavaType(),
                                    input.getValue()));
            case LESS_THAN_AGE -> (root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get(input.getField()),
                            Period.between(LocalDate.parse(input.getValue()), LocalDate.now()).getYears());
            case LESS_THAN_DATE -> (root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get(input.getField()),
                            input.getValue());
            case LIKE -> (root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get(input.getField()),
                            "%" + input.getValue() + "%");
            case IN -> (root, query, criteriaBuilder) ->
                    criteriaBuilder.in(root.get(input.getField()))
                            .value(castToRequiredType(root.get(input.getField()).getJavaType(), input.getValues().toString()));
            default -> throw new RuntimeException("Operator not found");
        };
    }

    private Object castToRequiredType(Class fieldType, String value) {
        if (fieldType.isAssignableFrom(Long.class)) {
            return Long.valueOf(value);
        } else if (fieldType.isAssignableFrom(Integer.class)) {
            return Integer.valueOf(value);
        } else if (Enum.class.isAssignableFrom(fieldType)) {
            return Enum.valueOf(fieldType, value);
        }
        return null;

    }
}


