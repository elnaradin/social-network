package ru.itgroup.intouch.aggregator.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.itgroup.intouch.aggregator.config.security.jwt.JWTUtil;
import ru.itgroup.intouch.aggregator.utils.CookieUtil;
import ru.itgroup.intouch.client.AccountServiceClient;
import ru.itgroup.intouch.client.MessageServiceClient;
import ru.itgroup.intouch.dto.EmailDto;
import ru.itgroup.intouch.dto.dialog.ResponseDialogDTO;
import ru.itgroup.intouch.dto.message.MessageStatusUpdateResponseDto;
import ru.itgroup.intouch.dto.message.ResponseDialogMessageDTO;
import ru.itgroup.intouch.dto.message.UnreadMessageResponseDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dialogs")
public class DialogController {
    private final MessageServiceClient client;
    private final CookieUtil cookieUtil;
    private final JWTUtil jwtUtil;

    @GetMapping
    public ResponseDialogDTO getAllDialogs(HttpServletRequest request,
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @RequestParam(required = false, defaultValue = "20") Integer itemPerPage
    ) {
        Integer userId = getAccountId(request);
        return client.feignGetAllDialogs(offset, itemPerPage, userId);
    }

    @GetMapping("/unreaded")
    public UnreadMessageResponseDTO getUnreadMessageCount(HttpServletRequest request) {
        Integer userId = getAccountId(request);
        return client.feignGetUnreadMessageCount(userId);
    }

    @GetMapping("/messages")
    public ResponseDialogMessageDTO getAllMessages(HttpServletRequest request,
            @RequestParam String companionId, @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "20") Integer itemPerPage) {
        Integer userId = getAccountId(request);
        return client.feignGetAllMessages(companionId, offset, itemPerPage, userId);
    }

    @PutMapping("/{companionId}")
    public MessageStatusUpdateResponseDto setStatusMessageRead(HttpServletRequest request,
                                                               @PathVariable String companionId){
        Integer userId = getAccountId(request);
        return client.feignSetStatusMessageRead(companionId, userId);
    }

    private Integer getAccountId(HttpServletRequest request){
        return jwtUtil.extractUserId(cookieUtil.getJwt(request));
    }
}
