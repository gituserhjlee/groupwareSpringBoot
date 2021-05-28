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

        return memberRepository.findByEmployeeNum(employeeNum)
                .orElseThrow(()->new UsernameNotFoundException(employeeNum));
    }

    public Member insertUser(Member user) {
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuth(Auth.준회원);
        Date now=new Date();
        user.setEnteredDate(now);
        return memberRepository.save(user);

    }
}
