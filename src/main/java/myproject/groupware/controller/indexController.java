package myproject.groupware.controller;

import lombok.RequiredArgsConstructor;
import myproject.groupware.entity.Member;
import myproject.groupware.repository.MemberRepository;
import myproject.groupware.service.MemberService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class indexController {

    private final MemberService memberService;

    @GetMapping("/")
    public String main() {
        return "index";
    }

    @GetMapping("/login")
    public String initialPage() {
        return "login";

    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        return "signup";
    }

    @PostMapping("/signup")
    public String signUpSubmit(Member user, @RequestParam MultipartFile files) throws IOException {
        String baseDir = "C:" + File.separator + "temp";
        String filePath = baseDir + File.separator + files.getOriginalFilename();
        files.transferTo(new File(filePath));

        try {
            memberService.insertUser(user, filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/login";
    }

}
