package ru.itgroup.intouch.service;

import Filters.PostFilterBuilder;
import dto.PostSearchDto;
import lombok.RequiredArgsConstructor;
import model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.repository.PostRepository;
import ru.itgroup.intouch.repository.UserRepository;
import searchUtils.Filter;
import searchUtils.SpecificationBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostSearchService {

    private final PostRepository postRepository;
    private final PostFilterBuilder filterBuilder;
    private final SpecificationBuilder specificationBuilder;
    private final UserRepository userRepository;

    public List<Post> getAccountResponse(PostSearchDto dto, Pageable pageable) {

        List<String> authorIds = !dto.getAuthor().isEmpty() ? userRepository.findUserIdByName(dto.getAuthor()).stream().
                map(Object::toString).collect(Collectors.toList()) : new ArrayList<>();

        List<Filter> filter = filterBuilder.createFilter(dto, authorIds);

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
