package ru.itgroup.intouch.service;

import dto.PostSearchDto;
import dto.PostSearchDtoPageable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mappers.PostDtoPageableMapper;
import model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.dto.PostDto;
import ru.itgroup.intouch.mapper.MapperToPostDto;
import ru.itgroup.intouch.repository.PostRepository;
import ru.itgroup.intouch.repository.PostTagRepository;
import ru.itgroup.intouch.repository.UserRepository;
import searchUtils.Filter;
import searchUtils.SpecificationBuilder;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class PostSearchService {

    private final PostRepository postRepository;
    private final filters.PostFilterBuilder filterBuilder;
    private final SpecificationBuilder specificationBuilder;
    private final UserRepository userRepository;
    private final MapperToPostDto mapperToPostDto;
    private final PostDtoPageableMapper dtoPageableMapper;
    private final PostTagRepository tagRepository;

    public Page<PostDto> getPostResponse(PostSearchDtoPageable postSearchDtoPageable, Long userId) {


        PostSearchDto dto = dtoPageableMapper.mapToPostSearchDto(postSearchDtoPageable);
        Pageable pageable = dtoPageableMapper.mapToPageable(postSearchDtoPageable);


        List<Long> authorIds = (dto.getAuthor() != null) ? getUserIdsFromAuthor(dto.getAuthor()) : new ArrayList<>();

        List<Long> postIds = (dto.getTags() != null) ? getPostIdFromTags(dto.getTags()) : new ArrayList<>();

        List<Filter> filter = filterBuilder.createFilter(dto, authorIds, postIds);

        if (filter.isEmpty()) {

            Page<Post> defaultPosts = postRepository.findAllByAuthorId(userId, pageable);

            return defaultPosts.map(mapperToPostDto::getPostDto);
        }

        Specification<model.Post> specification =
                (Specification<model.Post>) specificationBuilder.getSpecificationFromFilters(filter);

        Page<Post> pageResult = postRepository.findAll(specification, pageable);

        return pageResult.map(mapperToPostDto::getPostDto);
    }

    private List<Long> getUserIdsFromAuthor(String author) {

        if (author.isEmpty()) {
            return new ArrayList<>();
        }

        String[] authorNames = author.split(" ");

        return userRepository.findAllIdByNames(authorNames);


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
