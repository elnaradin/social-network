package ru.itgroup.intouch.aggregator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itgroup.intouch.client.FriendsServiceClient;
import ru.itgroup.intouch.dto.FriendDto;
import ru.itgroup.intouch.dto.FriendSearchDto;
import ru.itgroup.intouch.dto.FriendSearchPageableDto;
import ru.itgroup.intouch.dto.response.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FriendsController {
    private final FriendsServiceClient client;

    @PutMapping("/friends/{id}/approve")
    public ResponseEntity<ApiResponse<FriendDto>> approveFriendByIdHandle(@PathVariable Long id) {
        return client.feignApproveFriendByIdHandle(id);
    }

    @PutMapping("/friends/block/{id}")
    public ResponseEntity<ApiResponse<FriendDto>> blockFriendByIdHandle(@PathVariable Long id) {
        return client.feignBlockFriendByIdHandle(id);
    }

    @PostMapping("/friends/{id}/request")
    public ResponseEntity<ApiResponse<FriendDto>> requestOnFriendByIdHandle(@PathVariable Long id) {
        return client.feignRequestOnFriendByIdHandle(id);
    }

    @PostMapping("/friends/subscribe/{id}")
    public ResponseEntity<ApiResponse<FriendDto>> subscribeOnFriendByIdHandle(@PathVariable Long id) {
        return client.feignSubscribeOnFriendByIdHandle(id);
    }

    @GetMapping("/friends")
    public ResponseEntity<ApiResponse<Page<FriendDto>>> getFriendsByRequestHandle(
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
        FriendSearchPageableDto friendSearchPageableDto = new FriendSearchPageableDto();
        friendSearchPageableDto.setId(id);
        friendSearchPageableDto.setIsDeleted(isDeleted);
        friendSearchPageableDto.setIds(ids);
        friendSearchPageableDto.setIdFrom(idFrom);
        friendSearchPageableDto.setStatusCode(statusCode);
        friendSearchPageableDto.setIdTo(idTo);
        friendSearchPageableDto.setFirstName(firstName);
        friendSearchPageableDto.setBirthDateFrom(birthDateFrom);
        friendSearchPageableDto.setCity(city);
        friendSearchPageableDto.setCountry(country);
        friendSearchPageableDto.setAgeFrom(ageFrom);
        friendSearchPageableDto.setAgeTo(ageTo);
        friendSearchPageableDto.setPreviousStatusCode(previousStatusCode);
        friendSearchPageableDto.setPage(page);
        friendSearchPageableDto.setSize(size);
        friendSearchPageableDto.setSort(sort);
        return client.feignGetFriendsByRequestHandle(friendSearchPageableDto);
    }

    @GetMapping("/friends/{id}")
    public ResponseEntity<ApiResponse<FriendDto>> getFriendByIdHandle(@PathVariable Long id) {
        return client.feignGetFriendByIdHandle(id);
    }

    @DeleteMapping("/friends/{id}")
    public ResponseEntity<ApiResponse<FriendDto>> deleteFriendByIdHandle(@PathVariable Long id) {
        return client.feignDeleteFriendByIdHandle(id);
    }

    @GetMapping("/friends/recommendations")
    public ResponseEntity<ApiResponse<Page<FriendDto>>> getRecommendationsHandle(
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
        return client.feignGetRecommendationsHandle(friendSearchDto);
    }

    @GetMapping("/friends/friendId")
    public ResponseEntity<List<Long>> getFriendIdHandle() {
        return modifyResponse(client.feignGetFriendIdHandle());
    }

    @GetMapping("/friends/count")
    public ResponseEntity<Integer> getCountRequestHandle() {
        return modifyResponse(client.feignGetCountRequestHandle());
    }

    @GetMapping("/friends/blockFriendId")
    public ResponseEntity<List<Long>> getBlockFriendIdHandle() {
        return modifyResponse(client.feignGetBlockFriendIdHandle());
    }

    private <T> ResponseEntity<T> modifyResponse(ResponseEntity<ApiResponse<T>> response){
        ResponseEntity<T> resultResponse;
        if (response.getBody() == null) {
            resultResponse = new ResponseEntity<>(response.getStatusCode());
        } else {
            resultResponse = new ResponseEntity<>(response.getBody().getData(), response.getStatusCode());
        }
        return resultResponse;
    }
}
