package ru.itgroup.intouch.service;

import dto.PostSearchDtoPageable;
import dto.PostSearchDto;
import lombok.RequiredArgsConstructor;
import mappers.PostDtoPageableMapper;
import model.Post;
import model.account.User;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.dto.PostDto;
import ru.itgroup.intouch.repository.PostRepository;
import ru.itgroup.intouch.repository.PostTagRepository;
import ru.itgroup.intouch.repository.UserRepository;
import searchUtils.Filter;
import searchUtils.SpecificationBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostSearchService {

    private final PostRepository postRepository;
    private final filters.PostFilterBuilder filterBuilder;
    private final SpecificationBuilder specificationBuilder;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PostDtoPageableMapper dtoPageableMapper;
    private final PostTagRepository tagRepository;
    private PostSearchDto dto;

    public Page<PostDto> getAccountResponse(PostSearchDtoPageable postSearchDtoPageable) {

        dto = dtoPageableMapper.mapToPostSearchDto(postSearchDtoPageable);
        Pageable pageable = dtoPageableMapper.mapToPageable(postSearchDtoPageable);

        List<Long> authorIds = (!dto.getAuthor().isEmpty()) ? getUserIdsFromAuthor(dto.getAuthor()) : new ArrayList<>();

        List<Long> postIds = (!dto.getTags().isEmpty()) ? getPostIdFromTags(dto.getTags()) : new ArrayList<>();

        List<Filter> filter = filterBuilder.createFilter(dto, authorIds, postIds);

        if (filter.isEmpty()) {
            return null;
        }

        Specification<model.Post> specification = (Specification<model.Post>) specificationBuilder.getSpecificationFromFilters(filter);

        Page<Post> pageResult = postRepository.findAll(specification, pageable);

        if (pageResult.hasContent()) {

            return pageResult.map(Post -> modelMapper.map(Post, PostDto.class));

        } else {
            return null;
        }

    }

    private List<Long> getUserIdsFromAuthor(String author) {

        String[] authorNames = dto.getAuthor().split(" ");

        List<Long> authors = userRepository.findAllByNames(authorNames);

        return new ArrayList<>(authors);


    }

    private List<Long> getPostIdFromTags(List<String> tagNames) {

        if (tagNames.isEmpty()) {
            return new ArrayList<>();
        }

        String[] names = tagNames.toArray(String[]::new);

        List<Long> tagsIds = tagRepository.findTagIdsByTagNames(names);

        if (tagsIds.isEmpty()) {
            return new ArrayList<>();
        }

        return postRepository.findIdsByTagIds(tagsIds.toArray(Long[]::new));

    }
}
