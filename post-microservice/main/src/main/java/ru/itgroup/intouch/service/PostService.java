package ru.itgroup.intouch.service;

import lombok.RequiredArgsConstructor;
import model.Post;
import model.enums.PostType;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.mapper.MapperToPostDto;
import ru.itgroup.intouch.repository.PostRepository;
import ru.itgroup.intouch.dto.PostDto;
import ru.itgroup.intouch.service.enums.Item;
import ru.itgroup.intouch.service.enums.Operator;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final TagService tagService;
    private final PostRepository postsRepository;
    private final MapperToPostDto mapperToPostDto;

    public PostDto getPostById(Long id) {
        Optional<Post> postEntity = postsRepository.findById(id);
        return postEntity.map(mapperToPostDto::getPostDto).orElse(null);
    }

    public PostDto createNewPost(PostDto postDto) {
        Post post = new Post();

        if (tagService.getTags(postDto.getPostTags()).isEmpty()) {
            return null;
        }
        post.setTitle(postDto.getTitle());
        post.setDeleted(false);
        post.setPostText(postDto.getPostText());
        post.setCreatedDate(LocalDateTime.now());
        post.setPublishDate(LocalDateTime.now());
        post.setTimeChanged(LocalDateTime.now());
        post.setPostType(PostType.POSTED);
        post.setPostTags(tagService.getTags(postDto.getPostTags()));
        post.setAuthorId(postDto.getAuthorId());
        post.setImagePath(postDto.getImagePath());
        post.setDeleted(false);
        post.setMyLike(false);
        post.setLikeAmount(postDto.getLikeAmount());
        post.setCommentsCount(postDto.getCommentsCount());
        postsRepository.save(post);
        return mapperToPostDto.getPostDto(post);
    }

    public boolean deletePostById(Long id) {
        Optional<Post> postEntity = postsRepository.findById(id);
        if (postEntity.isEmpty()) {
            return false;
        }
        postEntity.get().setDeleted(true);

        return true;
    }

    public int changeCommentCountOrLikeAmount(Long id, Operator operator, Item item) {

        Optional<Post> postEntity = postsRepository.findById(id);

        if (postEntity.isPresent()) {

            Post post = postEntity.get();

            /*изменение количества комментов */
            if (item == Item.COUNT_COMMENTS) {

                int countComments = (operator == Operator.PLUS) ? post.getCommentsCount() + 1 : post.getCommentsCount() - 1;
                post.setCommentsCount(countComments);
            }
            /*изменение количества лайков*/
            if (item == Item.LIKE_AMOUNT) {

                int likeAmount = (operator == Operator.PLUS) ? post.getLikeAmount() + 1 : post.getLikeAmount() - 1;
                post.setLikeAmount(likeAmount);
            }

            return postsRepository.save(post).getCommentsCount();
        }

        return 0;
    }
}
