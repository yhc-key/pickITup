package com.ssafy.pickitup.domain.user.api;

import static com.ssafy.pickitup.global.api.ApiUtils.success;

import com.ssafy.pickitup.domain.badge.command.BadgeCommandService;
import com.ssafy.pickitup.domain.user.command.service.UserCommandService;
import com.ssafy.pickitup.domain.user.command.service.UserMongoCommandService;
import com.ssafy.pickitup.domain.user.command.service.UserRecommendFacade;
import com.ssafy.pickitup.domain.user.dto.UserRecommendDto;
import com.ssafy.pickitup.domain.user.dto.UserUpdateRequestDto;
import com.ssafy.pickitup.domain.user.query.dto.UserResponseDto;
import com.ssafy.pickitup.global.annotation.AuthID;
import com.ssafy.pickitup.global.api.ApiUtils.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"https://pickitup.online", "http://localhost:3000",
    "http://localhost:8080", "https://spring.pickitup.online"}, exposedHeaders = "*")
@RequestMapping("/users")
public class UserController {

    private final UserCommandService userCommandService;
    private final UserMongoCommandService userMongoCommandService;
    private final UserRecommendFacade userRecommendFacade;
    private final BadgeCommandService badgeCommandService;

    @Operation(summary = "회원 정보 조회 API")
    @GetMapping("/me")
    public ApiResult<UserResponseDto> getUser(@AuthID Integer userId) {
        return success(userCommandService.getUserById(userId));
    }

    @Operation(summary = "닉네임 변경 API")
    @PatchMapping("/nickname")
    public ApiResult<?> changeNickname(@AuthID Integer userId, @RequestBody String nickname) {
        userCommandService.changeNickname(userId, nickname);
        return success("닉네임 변경 성공");
    }

    @Operation(summary = "프로필 사진 변경 PI")
    @PatchMapping("/profile/image")
    public ApiResult<?> changeProfileImage(@AuthID Integer userId, @RequestBody String profileNum) {
        Integer profile = Integer.valueOf(profileNum);
        userCommandService.changeProfile(userId, profile);
        return success("프로필 사진 변경 성공");
    }


    @Operation(summary = "회원 주소 변경 API")
    @PatchMapping("/address")
    public ApiResult<?> changeAddress(@AuthID Integer userId, @RequestBody String address) {
        userCommandService.changeAddress(userId, address);
        return success("주소 변경 성공");
    }

    @Operation(summary = "회원 정보 변경 API")
    @PatchMapping("/me")
    public ApiResult<?> updateUser(@AuthID Integer userId,
        @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        userCommandService.changeUserInfo(userId, userUpdateRequestDto);
        return success("회원 정보 변경 성공");
    }

    @Operation(summary = "회원 추천 채용 공고 조회")
    @GetMapping("/recommend/recruit")
    public ApiResult<?> getUserRecommendRecruits(
        @AuthID Integer authId) {
        List<UserRecommendDto> userRecommendRecruitList = userRecommendFacade.getUserRecommendList(
            authId);
        return success(userRecommendRecruitList);
    }

    @Operation(summary = "회원 뱃지 조회")
    @GetMapping("/badges")
    public ApiResult<?> getBadge(@AuthID Integer userId) {
        return success(badgeCommandService.findMyBadges(userId));
    }

    @Operation(summary = "User 정보 MongoDB로 마이그레이션")
    @GetMapping("/mongo")
    public void toMongo() {
        userMongoCommandService.migration();
    }
}
