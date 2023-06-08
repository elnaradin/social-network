package filters;

import dto.AccountSearchDto;
import org.springframework.stereotype.Component;
import searchUtils.Filter;
import searchUtils.QueryOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class AccountFilterBuilder {

    public List<Filter> createFilter(AccountSearchDto accountSearchDto, List<Long> authorIds) {

        List<Filter> filterList = new ArrayList<>();

        /* создание фильтра поиска по ИМЕНИ */
        if (accountSearchDto.getFirstName() != null) {
            filterList.add(Filter.builder().field("firstName").operator(QueryOperator.EQUALS).value(accountSearchDto.getFirstName()).build());
        }
        /*                     по ФАМИЛИИ */
        if (accountSearchDto.getLastName() != null) {
            filterList.add(Filter.builder().field("lastName").operator(QueryOperator.EQUALS).value(accountSearchDto.getLastName()).build());
        }
        /* по АВТОРУ */
        if (!authorIds.isEmpty()) {
            filterList.add(Filter.builder().field("id").operator(QueryOperator.IN).values(authorIds.stream().map(Objects::toString).toList()).build());
        }
        /*                     по ГОРОДУ */
        if (accountSearchDto.getCity() != null) {
            filterList.add(Filter.builder().field("city").operator(QueryOperator.EQUALS).value(accountSearchDto.getCity()).build());
        }
        /*                     по СТРАНЕ */
        if (accountSearchDto.getCountry() != null) {
            filterList.add(Filter.builder().field("country").operator(QueryOperator.EQUALS).value(accountSearchDto.getCountry()).build());
        }
        /*                     по ВОЗРАСТУ МЛАДШЕ */
        if (accountSearchDto.getAgeFrom() != 0) {
            filterList.add(Filter.builder().field("birthDate").operator(QueryOperator.LESS_THAN_AGE).value(String.valueOf(accountSearchDto.getAgeFrom())).build());
        }
        /*                     по ВОЗРАСТУ СТАРШЕ */
        if (accountSearchDto.getAgeTo() != 0) {

            filterList.add(Filter.builder().field("birthDate").operator(QueryOperator.GREATER_THAN_AGE).value(String.valueOf(accountSearchDto.getAgeTo())).build());

        }


        return filterList;
    }
}
