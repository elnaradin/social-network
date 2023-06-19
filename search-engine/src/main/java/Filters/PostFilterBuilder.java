package filters;

import dto.PostSearchDto;
import searchUtils.Filter;
import searchUtils.QueryOperator;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class PostFilterBuilder {

    public List<Filter> createFilter(PostSearchDto postSearchDto, List<Long> authorIds, List<Long> postIds) {

        List<Filter> filterList = new ArrayList<>();

        /* создание фильтра поиска по АВТОРУ */
        if (!authorIds.isEmpty()) {

            filterList.add(Filter.builder().field("authorId").operator(QueryOperator.IN).values(authorIds.stream().map(Objects::toString).toList()).build());
        }
        /* по ID авторов */
        if (!postIds.isEmpty()) {
            filterList.add(Filter.builder().field("id").operator(QueryOperator.IN).values(postIds.stream().map(Objects::toString).toList()).build());
        }
        /* по дате публикации позднее*/
        if (postSearchDto.getDateFrom() != null) {
            filterList.add(Filter.builder().field("publishDate").operator(QueryOperator.GREATER_THAN_DATE).date(postSearchDto.getDateFrom().toLocalDateTime()).build());
        }
        /* по дата публикации ранее*/
        if (postSearchDto.getDateTo() != null) {
            filterList.add(Filter.builder().field("publishDate").operator(QueryOperator.LESS_THAN_DATE).date(postSearchDto.getDateTo().toLocalDateTime()).build());
        }
        /* по ID авторов за исключением*/
        if (postSearchDto.getBlockedIds() != null) {
            filterList.add(Filter.builder().field("authorId").operator(QueryOperator.NOT_IN).values(postSearchDto.getBlockedIds().stream().map(Objects::toString).toList()).build());
        }
        /* по авторам*/
        if (postSearchDto.getAccountIds() != null) {
            filterList.add(Filter.builder().field("authorId").operator(QueryOperator.IN).values(postSearchDto.getAccountIds().stream().map(Objects::toString).toList()).build());
        }
        /* среди постов*/
        if (postSearchDto.getIds() != null) {
            filterList.add(Filter.builder().field("id").operator(QueryOperator.IN).values(postSearchDto.getIds().stream().map(Objects::toString).toList()).build());
        }

        return filterList;
    }
}
