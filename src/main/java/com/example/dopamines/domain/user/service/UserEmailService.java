package com.example.dopamines.domain.user.service;

import com.example.dopamines.domain.user.model.entity.UserEmailVerify;
import com.example.dopamines.domain.user.model.request.UserEmailVerifyReq;
import com.example.dopamines.domain.user.model.request.UserSignupReq;
import com.example.dopamines.domain.user.repository.UserEmailVerifyRepository;
import com.example.dopamines.domain.user.repository.UserRepository;
import com.example.dopamines.global.common.BaseException;
import com.example.dopamines.global.common.BaseResponseStatus;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEmailService {

    private final UserRepository userRepository;

    private final UserEmailVerifyRepository userEmailRepository;

    private final JavaMailSender emailSender;

    // 메일을 보내고, UUID를 생성한다.
    public void sendEmailAndSave(String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[도파민즈 사이트] 인증 메일 요청");

        String uuid = UUID.randomUUID().toString().substring(0,6).toUpperCase();
        System.out.println(uuid);
//        message.setText("가입을 완료하시려면 아래 링크를 클릭해주세요."
//                + "http://3.36.126.48:8080/user/active?email=" + request.getEmail()+"&uuid="+uuid);  //Todo

        message.setText("가입을 완료하시려면 회원가입 창으로 돌아간 후 아래 정보를 입력해주세요.\n" + uuid);

        //이메일 보내고, DB에 저장
        emailSender.send(message);
        save(email,uuid);

    }

    public void save(String email, String uuid) {
        UserEmailVerify emailVerify = UserEmailVerify.builder()
                .email(email)
                .uuid(uuid)
                .build();

        UserEmailVerify result = userEmailRepository.save(emailVerify);
        if(result == null){
            throw new BaseException(BaseResponseStatus.USER_INVALID_MAIL_INFO);
        }
    }

    public boolean verifyUuid(UserEmailVerifyReq request) {
        Optional<UserEmailVerify> result = userEmailRepository.findByEmailAndUuid(request.getEmail(),
                request.getUuid());
        if(!result.isPresent()){
            return false;
        }
        return true;
    }

    public void verifyUser(String email, String uuid) {
        Optional<UserEmailVerify> getEmailVerifyUser = userEmailRepository.findByEmailAndUuid(email,uuid);
        //요청받은 이메일과 uuid 가 일치하면 계정 활성화
        if(!getEmailVerifyUser.isPresent()){

        }
    }


}
