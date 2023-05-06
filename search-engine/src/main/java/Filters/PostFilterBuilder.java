package Filters;

import dto.PostSearchDto;
import searchUtils.Filter;
import searchUtils.QueryOperator;

import java.util.ArrayList;
import java.util.List;


public class PostFilterBuilder {

    public List<Filter> createFilter(PostSearchDto postSearchDto, List<String> authorIds) {

        List<Filter> filterList = new ArrayList<>();

        /* создание фильтра поиска по АВТОРУ */
        if (postSearchDto.getAuthor() != null) {

            filterList.add(Filter.builder().field("author_id").operator(QueryOperator.IN).values(authorIds).build());
        }
        if (postSearchDto.getTags() != null) {
            filterList.add(Filter.builder().field("tag").operator(QueryOperator.IN).values(postSearchDto.getTags()).build());
        }
        if (postSearchDto.getDateFrom() != null) {
            filterList.add(Filter.builder().field("publishDate").operator(QueryOperator.GREATER_THAN_DATE).date(postSearchDto.getDateFrom()).build());
        }
        if (postSearchDto.getDateTo() != null) {
            filterList.add(Filter.builder().field("publishDate").operator(QueryOperator.LESS_THAN_DATE).date(postSearchDto.getDateTo()).build());
        }


        return filterList;
    }
}
