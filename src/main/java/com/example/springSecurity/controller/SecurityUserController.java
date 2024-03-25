package com.example.springSecurity.controller;

import java.io.File;

import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.springSecurity.entity.SecurityUser;
import com.example.springSecurity.service.SecurityUserService;
import com.example.springSecurity.util.ImageUtil;

import lombok.RequiredArgsConstructor;

@Controller     //@Controller 어노테이션은 이 클래스가 컨트롤러임을 나타냅니다.
@RequestMapping("/user")        //@RequestMapping("/user") 어노테이션은 "/user" 경로로 들어오는 요청을 이 클래스에서 처리한다는 것을 의미합니다.
@RequiredArgsConstructor
public class SecurityUserController {
    private final SecurityUserService securityService;
    private final BCryptPasswordEncoder bCryptEncoder;
    private final ImageUtil imageUtil;
    @Value("${spring.servlet.multipart.location}") private String uploadDir;

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/register")            //@GetMapping("/register"): 회원가입 폼을 보여주는 메서드입니다.
    public String registerForm() {
        return "user/register";
    }

    @PostMapping("/register")           //@PostMapping("/register"): 회원가입을 처리하는 메서드입니다. 사용자가 입력한 정보를 받아서 회원가입을 진행하고, 이미지 파일을 업로드하는 부분도 포함되어 있습니다.
    public String registerProc(String uid, String pwd, String pwd2, String uname,
                               String email, MultipartHttpServletRequest req, Model model) {
        String filename = null;
        MultipartFile filePart = req.getFile("picture");

        SecurityUser securityUser = securityService.getUserByUid(uid);
        if (securityUser != null) {
            model.addAttribute("msg", "사용자 ID가 중복되었습니다.");
            model.addAttribute("url", "/ss/user/register");
            return "common/alertMsg";
        }
        if (pwd == null || !pwd.equals(pwd2)) {
            model.addAttribute("msg", "패스워드 입력이 잘못되었습니다.");
            model.addAttribute("url", "/ss/user/register");
            return "common/alertMsg";
        }
        if (filePart.getContentType().contains("image")) {
            filename = filePart.getOriginalFilename();
            String path = uploadDir + "profile/" + filename;
            try {
                filePart.transferTo(new File(path));
            } catch (Exception e) {
                e.printStackTrace();
            }
            filename = imageUtil.squareImage(uid, filename);
        }
        String hashedPwd = bCryptEncoder.encode(pwd);
        securityUser = SecurityUser.builder()
                .uid(uid).pwd(hashedPwd).uname(uname).email(email).provider("ck world")
                .picture("/ss/file/download/profile/" + filename)
                .build();
        securityService.insertSecurityUser(securityUser);
        model.addAttribute("msg", "등록을 마쳤습니다. 로그인하세요.");
        model.addAttribute("url", "/ss/user/login");
        return "common/alertMsg";
    }

    @ResponseBody
    @GetMapping("/loginSuccess")            //@ResponseBody @GetMapping("/loginSuccess"): 로그인 성공 후 실행되는 메서드입니다. SecurityContextHolder를 사용하여 현재 인증된 사용자의 정보를 가져와서 로그인한 사용자의 ID를 반환합니다.
    public String loginSuccess(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 세션의 현재 사용자 아이디를 알기 위해서 사용하는 코드
        String uid = authentication.getName(); // 로그인을 한 현재 사용자

        return "loginSuccess - " + uid;
    }

    //위의 코드를 통해 사용자의 로그인, 회원가입 기능이 구현되어 있습니다. 또한 로그인 성공 후에는 해당 사용자의 ID를 반환하여 처리할 수 있도록 구현되어 있습니다.
}