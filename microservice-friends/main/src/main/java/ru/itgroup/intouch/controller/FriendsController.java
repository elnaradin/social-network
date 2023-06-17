package ru.itgroup.intouch.controller;

import lombok.extern.slf4j.Slf4j;
import model.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itgroup.intouch.dto.FriendDto;
import ru.itgroup.intouch.dto.FriendSearchDto;
import ru.itgroup.intouch.dto.FriendSearchPageableDto;
import ru.itgroup.intouch.dto.response.ApiResponse;
import ru.itgroup.intouch.service.FriendsService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class FriendsController {

    private final FriendsService friendsService;

    @Autowired
    public FriendsController(FriendsService friendsService) {
        this.friendsService = friendsService;
    }

    @PutMapping("/friends/{id}/approve")
    public ResponseEntity<ApiResponse<FriendDto>> approveFriendByIdHandle(
            @PathVariable Long id, @RequestHeader("currentEmail") String currentEmail) throws Exception {
        return getApiResponse(friendsService.approveFriendById(id, createAccount(currentEmail)));
    }

    @PutMapping("/friends/block/{id}")
    public ResponseEntity<ApiResponse<FriendDto>> blockFriendByIdHandle(
            @PathVariable Long id, @RequestHeader("currentEmail") String currentEmail) throws Exception {
        return getApiResponse(friendsService.blockFriendById(id, createAccount(currentEmail)));
    }

    @PostMapping("/friends/{id}/request")
    public ResponseEntity<ApiResponse<FriendDto>> requestOnFriendByIdHandle(
            @PathVariable Long id, @RequestHeader("currentEmail") String currentEmail) throws Exception {
        return getApiResponse(friendsService.requestOnFriendById(id, createAccount(currentEmail)));
    }

    @PostMapping("/friends/subscribe/{id}")
    public ResponseEntity<ApiResponse<FriendDto>> subscribeOnFriendByIdHandle(
            @PathVariable Long id, @RequestHeader("currentEmail") String currentEmail) throws Exception {
        return getApiResponse(friendsService.subscribeOnFriendById(id, createAccount(currentEmail)));
    }

    @GetMapping("/friends")
    public ResponseEntity<ApiResponse<Page<FriendDto>>> getFriendsByRequestHandle(
            @SpringQueryMap FriendSearchPageableDto friendSearchPageableDto,
            @RequestHeader("currentEmail") String currentEmail) {
        return getApiResponse(friendsService.getFriendsByRequest(friendSearchPageableDto, createAccount(currentEmail)));
    }

    @GetMapping("/friends/{id}")
    public ResponseEntity<ApiResponse<FriendDto>> getFriendByIdHandle(
            @PathVariable Long id, @RequestHeader("currentEmail") String currentEmail) throws Exception {
        return getApiResponse(friendsService.getFriendById(id, createAccount(currentEmail)));
    }

    @DeleteMapping("/friends/{id}")
    public ResponseEntity<ApiResponse<FriendDto>> deleteFriendByIdHandle(
            @PathVariable Long id, @RequestHeader("currentEmail") String currentEmail) throws Exception {
        return getApiResponse(friendsService.deleteFriendById(id, createAccount(currentEmail)));
    }

    @GetMapping("/friends/recommendations")
    public ResponseEntity<ApiResponse<Page<FriendDto>>> getRecommendationsHandle(
            @SpringQueryMap FriendSearchDto friendSearchDto, @RequestHeader("currentEmail") String currentEmail) {
        return getApiResponse(friendsService.getRecommendations(friendSearchDto, createAccount(currentEmail)));
    }

    @GetMapping("/friends/friendId")
    public ResponseEntity<ApiResponse<List<Long>>> getFriendIdHandle(
            @RequestHeader("currentEmail") String currentEmail) {
        return getApiResponse(friendsService.getFriendIds(createAccount(currentEmail)));
    }

    @GetMapping("/friends/count")
    public ResponseEntity<ApiResponse<Integer>> getCountRequestHandle(
            @RequestHeader("currentEmail") String currentEmail) {
        return getApiResponse(friendsService.getCountRequest(createAccount(currentEmail)));
    }

    @GetMapping("/friends/blockFriendId")
    public ResponseEntity<ApiResponse<List<Long>>> getBlockFriendIdHandle(
            @RequestHeader("currentEmail") String currentEmail) {
        return getApiResponse(friendsService.getBlockFriendId(createAccount(currentEmail)));
    }

    private <T> ResponseEntity<ApiResponse<T>> getApiResponse(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setData(data);
        return ResponseEntity.ok(response);
    }

    private Account createAccount(String email) {
        Account account = new Account();
        account.setEmail(email);
        return account;
    }
}
