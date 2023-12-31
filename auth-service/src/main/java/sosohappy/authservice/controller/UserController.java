package sosohappy.authservice.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import sosohappy.authservice.model.dto.*;
import sosohappy.authservice.service.UserService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/checkDuplicateNickname")
    public DuplicateDto checkDuplicateNickname(@Valid NicknameDto nicknameDto){
        return userService.checkDuplicateNickname(nicknameDto.getNickname());
    }

    @PostMapping("/setProfile")
    public SetProfileDto setProfile(@Valid UserRequestDto userRequestDto){
        return userService.setProfile(userRequestDto);
    }

    @PostMapping("/resign")
    public ResignDto resign(@Valid EmailDto emailDto){
        return userService.resign(emailDto.getEmail());
    }

    @PostMapping("/findProfileImg")
    public UserResponseDto findProfileImg(@Valid NicknameDto nicknameDto) {
        return userService.findProfileImg(nicknameDto.getNickname());
    }

    @PostMapping(value = "/findIntroduction")
    public Map<String, String> findIntroduction(@Valid NicknameDto nicknameDto) {
        return userService.findIntroduction(nicknameDto.getNickname());
    }

    @PostMapping("/signIn")
    public NicknameDto signInWithPKCE(@ModelAttribute @Valid SignInDto signInDto, HttpServletResponse httpServletResponse) {
        return userService.signInWithPKCE(signInDto, httpServletResponse);
    }

    @PostMapping("/getAuthorizeCode")
    public Map<String, String> getAuthorizeCode(@Valid CodeChallengeDto codeChallengeDto){
        return userService.getAuthorizeCode(codeChallengeDto.getCodeChallenge());
    }
}
