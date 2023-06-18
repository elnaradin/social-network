package ru.itgroup.intouch.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.itgroup.intouch.dto.PostDto;
import ru.itgroup.intouch.service.PostService;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PostController.class)
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private PostService postService;
    @Test
    void getFoundPost() throws Exception {
        PostDto postDto = postDtoToRead();
        given(postService.getPostById(1L)).willReturn(postDto);
        mvc
                .perform(get("/api/v1/post/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(true)))
                .andExpect(jsonPath("$.message", is("Пост найден")))
                .andExpect(jsonPath("$.postDto.id", is(1)))
                .andExpect(jsonPath("$.postDto.publishDate", is("2023-03-16T00:00:00")))
                .andExpect(jsonPath("$.postDto.time", is("2023-03-20T00:00:00")))
                .andExpect(jsonPath("$.postDto.timeChanged", is("2023-03-20T00:00:00")))
                .andExpect(jsonPath("$.postDto.authorId", is(6)))
                .andExpect(jsonPath("$.postDto.title", is("odio curabitur convallis duis consequat dui nec")))
                .andExpect(jsonPath("$.postDto.type", is("POSTED")))
                .andExpect(jsonPath("$.postDto.postText", containsString("mauris viverra diam vitae quam suspendisse potenti")))
                .andExpect(jsonPath("$.postDto.commentsCounts", nullValue()))
                .andExpect(jsonPath("$.postDto.tags", containsInAnyOrder("Мотивация", "Для ценителей прекрасного", "Для детей", "Счастливый брак")))
                .andExpect(jsonPath("$.postDto.likeAmount", is(27)))
                .andExpect(jsonPath("$.postDto.myLike", is(false)))
                .andExpect(jsonPath("$.postDto.deleted", is(false)))
                .andExpect(jsonPath("$.postDto.blocked", is(false)));
    }
    @Test
    void getNotFoundPost() throws Exception {
        mvc
                .perform(get("/api/v1/post/10000").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.result", is(false)))
                .andExpect(jsonPath("$.message", is("Пост не найден")));
    }
    @Test
    void createPost() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        PostDto postDto = postDtoToWrite();
        given(postService.createNewPost(postDto, 9L)).willReturn(postDto);
        mvc.
                perform(post("/api/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk());
    }

    private PostDto postDtoToRead() {
        PostDto postDto = new PostDto();
        postDto.setId(1);
        postDto.setPublishDate(ZonedDateTime.from(LocalDateTime.parse("2023-03-16T00:00:00")));
        postDto.setTime(ZonedDateTime.from(LocalDateTime.parse("2023-03-20T00:00:00")));
        postDto.setTimeChanged(ZonedDateTime.from(LocalDateTime.parse("2023-03-20T00:00:00")));
        postDto.setAuthorId(6L);
        postDto.setTitle("odio curabitur convallis duis consequat dui nec");
        postDto.setPostType("POSTED");
        postDto.setPostText("mauris viverra diam vitae quam suspendisse potenti " +
                "nullam porttitor lacus at turpis donec posuere metus vitae ipsum " +
                "aliquam non mauris morbi non lectus aliquam sit amet diam in magna " +
                "bibendum imperdiet nullam orci pede venenatis non sodales sed tincidunt " +
                "eu felis fusce posuere felis sed lacus morbi sem mauris laoreet ut " +
                "rhoncus aliquet pulvinar sed nisl nunc rhoncus dui vel sem sed sagittis " +
                "nam congue risus semper porta volutpat quam pede lobortis ligula " +
                "sit amet eleifend pede libero quis orci nullam molestie nibh " +
                "in lectus pellentesque at nulla suspendisse potenti cras " +
                "in purus eu magna vulputate luctus cum sociis natoque penatibus et magnis " +
                "dis parturient montes nascetur ridiculus mus vivamus vestibulum sagittis " +
                "sapien cum sociis natoque penatibus");
        postDto.setCommentsCount(0);

        List<String> tags = new ArrayList<>();
        tags.add("Мотивация"); tags.add("Счастливый брак"); tags.add("Для детей"); tags.add("Для ценителей прекрасного");

        postDto.setPostTags(tags);
        postDto.setLikeAmount(27);
        postDto.setMyLike(false);
        postDto.setDeleted(false);
        postDto.setBlocked(false);

        return postDto;
    }
    private PostDto postDtoToWrite() {
        PostDto postDto = new PostDto();
        postDto.setDeleted(false);
        postDto.setAuthorId(12L);
        postDto.setTitle("Продуктовая разработка");
        postDto.setPostText("Продуктовая разработка — это создание и вывод на рынок продукта. Это может " +
                "быть приложение, сайт, программа или новая функция. Продуктовые разработчики на первое место ставят пользу и " +
                "ценность продукта. Чтобы понять, в том ли месте они ее ищут, они много исследуют, выдвигают гипотезы и тестируют их.");
        postDto.setPostType("POSTED");
        List<String> tags = new ArrayList<>();
        tags.add("Для взрослых"); tags.add("Распродажа"); tags.add("Разбитое сердце"); tags.add("Совершенное тело");

        return postDto;


    }
}