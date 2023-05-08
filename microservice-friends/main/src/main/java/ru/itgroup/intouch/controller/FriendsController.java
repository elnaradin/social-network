package ru.itgroup.intouch.controller;

import model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itgroup.intouch.dto.FriendDto;
import ru.itgroup.intouch.dto.FriendListDto;
import ru.itgroup.intouch.dto.FriendSearchDto;
import ru.itgroup.intouch.dto.Pageable;
import ru.itgroup.intouch.response.ApiResponse;
import ru.itgroup.intouch.service.FriendsService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class FriendsController {

    private final FriendsService friendsService;

    @Autowired
    public FriendsController(FriendsService friendsService) {
        this.friendsService = friendsService;
    }

    @PutMapping("/friends/{id}/approve")
    public ResponseEntity<ApiResponse<FriendDto>> approveFriendByIdHandle(
            @PathVariable Long id) throws Exception {
        return getApiResponse(friendsService.approveFriendById(id, new Account()));
    }

    @PutMapping("/friends/block/{id}")
    public ResponseEntity<ApiResponse<FriendDto>> blockFriendByIdHandle(
            @PathVariable Long id) throws Exception {
        return getApiResponse(friendsService.blockFriendById(id, new Account()));
    }

    @PostMapping("/friends/{id}/request")
    public ResponseEntity<ApiResponse<FriendDto>> requestOnFriendByIdHandle(
            @PathVariable Long id) throws Exception {
        return getApiResponse(friendsService.requestOnFriendById(id, new Account()));
    }

    @PostMapping("/friends/subscribe/{id}")
    public ResponseEntity<ApiResponse<FriendDto>> subscribeOnFriendByIdHandle(
            @PathVariable Long id) throws Exception {
        return getApiResponse(friendsService.subscribeOnFriendById(id, new Account()));
    }

    @GetMapping("/friends")
    public ResponseEntity<ApiResponse<FriendListDto>> getFriendsByRequestHandle(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Boolean isDeleted,
            @RequestParam(required = false) List<Long> ids,
            @RequestParam(required = false) Long idFrom,
            @RequestParam(required = false) String statusCode,
            @RequestParam(required = false) Long idTo,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String birthDateFrom,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) Long ageFrom,
            @RequestParam(required = false) Long ageTo,
            @RequestParam(required = false) String previousStatusCode,
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "5", required = false) Integer size,
            @RequestParam(required = false) List<String> sort) {
        FriendSearchDto friendSearchDto = FriendSearchDto.builder()
                .id(id)
                .isDeleted(isDeleted)
                .ids(ids)
                .idFrom(idFrom)
                .statusCode(statusCode)
                .idTo(idTo)
                .firstName(firstName)
                .birthDateFrom(birthDateFrom)
                .city(city)
                .country(country)
                .ageFrom(ageFrom)
                .ageTo(ageTo)
                .previousStatusCode(previousStatusCode)
                .build();
        Pageable pageable = Pageable.builder()
                .page(page)
                .size(size)
                .sort(sort)
                .build();
        return getApiResponse(friendsService.getFriendsByRequest(friendSearchDto, pageable, new Account()));
    }

    @GetMapping("/friends/{id}")
    public ResponseEntity<ApiResponse<FriendDto>> getFriendByIdHandle(@PathVariable Long id)
            throws Exception {
        return getApiResponse(friendsService.getFriendById(id, new Account()));
    }

    @DeleteMapping("/friends/{id}")
    public ResponseEntity<ApiResponse<FriendDto>> deleteFriendByIdHandle(@PathVariable Long id)
            throws Exception {
        return getApiResponse(friendsService.deleteFriendById(id, new Account()));
    }

    @GetMapping("/friends/recommendations")
    public ResponseEntity<ApiResponse<FriendListDto>> getRecommendationsHandle(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Boolean isDeleted,
            @RequestParam(required = false) List<Long> ids,
            @RequestParam(required = false) Long idFrom,
            @RequestParam(required = false) String statusCode,
            @RequestParam(required = false) Long idTo,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String birthDateFrom,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) Long ageFrom,
            @RequestParam(required = false) Long ageTo,
            @RequestParam(required = false) String previousStatusCode) {
        FriendSearchDto friendSearchDto = FriendSearchDto.builder()
                .id(id)
                .isDeleted(isDeleted)
                .ids(ids)
                .idFrom(idFrom)
                .statusCode(statusCode)
                .idTo(idTo)
                .firstName(firstName)
                .birthDateFrom(birthDateFrom)
                .city(city)
                .country(country)
                .ageFrom(ageFrom)
                .ageTo(ageTo)
                .previousStatusCode(previousStatusCode)
                .build();
        return getApiResponse(friendsService.getRecommendations(friendSearchDto, new Account()));
    }

    @GetMapping("/friends/friendId")
    public ResponseEntity<ApiResponse<List<Long>>> getFriendIdHandle() {
        return getApiResponse(friendsService.getFriendIds(new Account()));
    }

    @GetMapping("/friends/count")
    public ResponseEntity<ApiResponse<Integer>> getCountRequestHandle() {
        return getApiResponse(friendsService.getCountRequest(new Account()));
    }

    @GetMapping("/friends/blockFriendId")
    public ResponseEntity<ApiResponse<List<Long>>> getBlockFriendIdHandle() {
        return getApiResponse(friendsService.getBlockFriendId(new Account()));
    }

    private <T>ResponseEntity<ApiResponse<T>> getApiResponse(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setData(data);
        return ResponseEntity.ok(response);
    }
}
