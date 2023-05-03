package ru.itgroup.intouch.service;

import Filters.PostFilterBuilder;
import dto.PostSearchDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.repository.PostRepository;
import searchUtils.Filter;
import searchUtils.SpecificationBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostSearchService {

    private final PostRepository postRepository;
    private final PostFilterBuilder filterBuilder;
    private final SpecificationBuilder specificationBuilder;

    public List<Post> getAccountResponse(PostSearchDto dto, Pageable pageable) {

        List<Filter> filter = filterBuilder.createFilter(dto);

        if (filter.isEmpty()) {
            return new ArrayList<>();
        }

        Specification<Post> specification = (Specification<Post>) specificationBuilder.getSpecificationFromFilters(filter);

        Page<Post> pageResult = postRepository.findAll(specification, pageable);

        if (pageResult.hasContent()) {
            return pageResult.getContent();
        } else {
            return new ArrayList<>();
        }

    }
}
