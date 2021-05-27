package myproject.groupware.service;

import lombok.RequiredArgsConstructor;
import myproject.groupware.entity.Auth;
import myproject.groupware.entity.Member;
import myproject.groupware.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public Member loadUserByUsername(String employeeNum) throws UsernameNotFoundException {
        if (memberRepository.findByEmployeeNum(employeeNum)!=null){
            System.out.println("널아님");
        }else{
            System.out.println("널");
        }
        return memberRepository.findByEmployeeNum(employeeNum)
                .orElseThrow(()->new UsernameNotFoundException(employeeNum));
    }

    public void insertUser(Member user, String filePath) {
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setSaveFileName(filePath);
        user.setAuth(Auth.준회원);
        Date now=new Date();
        user.setEnteredDate(now);
        memberRepository.save(user);
    }
}
