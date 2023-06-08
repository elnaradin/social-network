package ru.itgroup.intouch.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import ru.itgroup.intouch.dto.FriendDto;
import ru.itgroup.intouch.dto.FriendSearchDto;
import ru.itgroup.intouch.dto.FriendSearchPageableDto;
import ru.itgroup.intouch.dto.response.ApiResponse;

import java.util.List;

@FeignClient(name = "friends-service", url = "${SN_FRIENDS_HOST}:${SN_FRIENDS_PORT}",
        path = "/api/v1")
public interface FriendsServiceClient {

    @PutMapping("/friends/{id}/approve")
    ResponseEntity<ApiResponse<FriendDto>> feignApproveFriendByIdHandle(@PathVariable Long id);

    @PutMapping("/friends/block/{id}")
    ResponseEntity<ApiResponse<FriendDto>> feignBlockFriendByIdHandle(@PathVariable Long id);

    @PostMapping("/friends/{id}/request")
    ResponseEntity<ApiResponse<FriendDto>> feignRequestOnFriendByIdHandle(@PathVariable Long id);

    @PostMapping("/friends/subscribe/{id}")
    ResponseEntity<ApiResponse<FriendDto>> feignSubscribeOnFriendByIdHandle(@PathVariable Long id);

    @GetMapping("/friends")
    ResponseEntity<ApiResponse<Page<FriendDto>>> feignGetFriendsByRequestHandle(
            @SpringQueryMap FriendSearchPageableDto friendSearchPageableDto);

    @GetMapping("/friends/{id}")
    ResponseEntity<ApiResponse<FriendDto>> feignGetFriendByIdHandle(@PathVariable Long id);

    @DeleteMapping("/friends/{id}")
    ResponseEntity<ApiResponse<FriendDto>> feignDeleteFriendByIdHandle(@PathVariable Long id);

    @GetMapping("/friends/recommendations")
    ResponseEntity<ApiResponse<Page<FriendDto>>> feignGetRecommendationsHandle(
            @SpringQueryMap FriendSearchDto friendSearchDto);

    @GetMapping("/friends/friendId")
    ResponseEntity<ApiResponse<List<Long>>> feignGetFriendIdHandle();

    @GetMapping("/friends/count")
    ResponseEntity<ApiResponse<Integer>> feignGetCountRequestHandle();

    @GetMapping("/friends/blockFriendId")
    ResponseEntity<ApiResponse<List<Long>>> feignGetBlockFriendIdHandle();
}
