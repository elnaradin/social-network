package Filters;

import dto.AccountSearchDto;
import org.springframework.stereotype.Component;
import searchUtils.Filter;
import searchUtils.QueryOperator;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountFilterBuilder {

    public List<Filter> createFilter(AccountSearchDto accountSearchDto) {

        List<Filter> filterList = new ArrayList<>();

        /* создание фильтра поиска по ИМЕНИ */
        if (accountSearchDto.getFirstName() != null) {
            filterList.add(Filter.builder().field("firstname").operator(QueryOperator.LIKE).value(accountSearchDto.getFirstName()).build());
        }
        /*                     по ФАМИЛИИ */
        if (accountSearchDto.getLastName() != null) {
            filterList.add(Filter.builder().field("lastName").operator(QueryOperator.LIKE).value(accountSearchDto.getLastName()).build());
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
            filterList.add(Filter.builder().field("birthday").operator(QueryOperator.LESS_THAN_AGE).value(String.valueOf(accountSearchDto.getAgeFrom())).build());
        }
        /*                     по ВОЗРАСТУ СТАРШЕ */
        if (accountSearchDto.getAgeTo() != 0) {

            filterList.add(Filter.builder().field("birthday").operator(QueryOperator.GREATER_THAN_AGE).value(String.valueOf(accountSearchDto.getAgeTo())).build());

        }


        return filterList;
    }
}
