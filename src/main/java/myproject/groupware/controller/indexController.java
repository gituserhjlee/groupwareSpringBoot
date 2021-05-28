package myproject.groupware.controller;

import lombok.RequiredArgsConstructor;
import myproject.groupware.dto.FileDto;
import myproject.groupware.entity.Member;
import myproject.groupware.service.FileService;
import myproject.groupware.service.MemberService;
import myproject.groupware.util.MD5Generator;
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
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

@Controller
@RequiredArgsConstructor
public class indexController {

    private final MemberService memberService;
    private final FileService fileService;

    @GetMapping("/")
    public String main(Model model) {
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("member", member);
        String file = member.getFileId().getFilename();
        model.addAttribute("photo", file);
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

        String origFilename = files.getOriginalFilename();
        String fileExt = origFilename.substring(origFilename.lastIndexOf("."));
        String filename = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS",
                Calendar.getInstance());
        filename += System.nanoTime();
        filename += fileExt;
        String savePath = "D:\\바탕화면\\스프링자료\\groupware\\src\\main\\resources\\static\\img\\userphotos";/* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */

        String filePath = savePath + File.separator + filename;
        files.transferTo(new File(filePath));

        FileDto fileDto = new FileDto();
        fileDto.setOrigFilename(origFilename);
        fileDto.setFilename(filename);
        fileDto.setFilePath(filePath);

        myproject.groupware.entity.File file = fileDto.toEntity();
        user.setFileId(file);
        file.setMember(user);
        memberService.insertUser(user);
        fileService.saveFile(file);


        return "redirect:/login";
    }


}
