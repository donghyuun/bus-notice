package com.example.busnotice.domain.fcmToken;

import com.example.busnotice.domain.fcmToken.req.CreateFCMTokenRequest;
import com.example.busnotice.global.format.ApiResponse;
import com.example.busnotice.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FCMController {

    private final FCMService fcmService;

    @PostMapping("/api/v1/fcm/tokens")
    public ApiResponse<String> createFCMToken(
        @RequestBody CreateFCMTokenRequest createFCMTokenRequest,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        fcmService.createFCMToken(userDetails.getId(), createFCMTokenRequest);
        return ApiResponse.createSuccess("FCM 토큰이 등록되었습니다.");
    }

    @GetMapping("/api/v1/fcm/alarm")
    public ApiResponse<Void> createFCMToken(
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        boolean isSuccess = fcmService.sendTestNotification(userDetails.getId());
        String msg =  isSuccess ? "테스트 알림 전송에 성공했습니다." : "테스트 알림 전송에 실패했습니다.";
        return ApiResponse.createSuccess(msg);
    }
}
