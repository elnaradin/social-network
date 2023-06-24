package ru.itgroup.intouch.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Post;
import model.enums.PostType;
import org.springframework.stereotype.Service;

import ru.itgroup.intouch.mapper.MapperToPostDto;
import ru.itgroup.intouch.repository.PostRepository;
import ru.itgroup.intouch.dto.PostDto;

import ru.itgroup.intouch.service.enums.Item;
import ru.itgroup.intouch.service.enums.Operator;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final TagService tagService;
    private final PostRepository postsRepository;
    private final MapperToPostDto mapperToPostDto;


    public PostDto getPostById(Long id) {
        Optional<Post> postEntity = postsRepository.findById(id);
        return postEntity.map(mapperToPostDto::getPostDto).orElse(null);
    }

    public PostDto createNewPost(PostDto postDto, Long userId) {

        Post post = new Post();

        post.setTitle(postDto.getTitle());
        post.setDeleted(false);
        post.setPostText(postDto.getPostText());
        post.setCreatedDate(getDateTime(postDto.getTime()));
        post.setPublishDate(getDateTime(postDto.getPublishDate()));
        post.setTimeChanged(getDateTime(postDto.getTimeChanged()));
        post.setPostType(getPostType(postDto.getPostType()));
        post.setPostTags(tagService.getTags(postDto.getPostTags()));
        post.setAuthorId(getAuthorId(postDto.getAuthorId(), userId));
        post.setImagePath(postDto.getImagePath());
        post.setMyLike(postDto.isMyLike());
        post.setLikeAmount(getLikesAmount(postDto.getLikeAmount()));
        post.setCommentsCount(getCommentsCount(postDto.getCommentsCount()));
        Post newPost = postsRepository.save(post);
        return mapperToPostDto.getPostDto(newPost);
    }

    public boolean deletePostById(Long id) {
        Optional<Post> postEntity = postsRepository.findById(id);
        if (postEntity.isEmpty()) {
            return false;
        }
        postEntity.get().setDeleted(true);

        return true;
    }

    public void changeCommentCountOrLikeAmount(Long id, Operator operator, Item item, Long userId) {

        Optional<Post> postEntity = postsRepository.findById(id);

        if (postEntity.isPresent()) {

            Post post = postEntity.get();

            /*изменение количества комментов */
            if (item == Item.COUNT_COMMENTS) {

                int countComments = (operator == Operator.PLUS) ? getCommentsCount(post.getCommentsCount()) + 1 : post.getCommentsCount() - 1;
                post.setCommentsCount(countComments);
            }
            /*изменение количества лайков*/
            if (item == Item.LIKE_AMOUNT) {

                int likeAmount = (operator == Operator.PLUS) ? getLikesAmount(post.getLikeAmount()) + 1 : post.getLikeAmount() - 1;
                post.setLikeAmount(likeAmount);
                if (Objects.equals(post.getAuthorId(), userId)) {
                    boolean myLike = !post.isMyLike();
                    post.setMyLike(myLike);
                }

                postsRepository.save(post);
            }
        }

    }

    private LocalDateTime getDateTime(ZonedDateTime time) {

        return (time == null) ? LocalDateTime.now() : time.toLocalDateTime();
    }

    private PostType getPostType(String postType) {

        return (postType == null) ? PostType.POSTED : PostType.valueOf(postType);
    }

    private Long getAuthorId(Long authorId, Long userId) {

        return (authorId == null) ? userId : authorId;
    }

    private Integer getLikesAmount(Integer likeAmount) {

        if (likeAmount == null) {
            likeAmount = 0;
        }
        return likeAmount;
    }

    private Integer getCommentsCount(Integer commentCount) {

        if (commentCount == null) {
            commentCount = 0;
        }
        return commentCount;
    }
}
