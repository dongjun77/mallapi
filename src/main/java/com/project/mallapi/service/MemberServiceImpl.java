package com.project.mallapi.service;

import com.project.mallapi.domain.Member;
import com.project.mallapi.domain.MemberRole;
import com.project.mallapi.dto.MemberDTO;
import com.project.mallapi.dto.MemberModifyDTO;
import com.project.mallapi.repository.MemberRepository;
import java.util.LinkedHashMap;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberDTO getKakaoMember(String accessToken) {

        // 카카오 연동 닉네임 -- 이메일 주소에 해당
        // accessToken을 이용해서 사용자 정보 가져오기
        String nickname = getEmailFromKakaoAccessToken(accessToken);

        // 기존에 DB에 회원 정보가 있는 경우 / 없는 경우

        Optional<Member> result = memberRepository.findById(nickname);

        if(result.isPresent()) {

            MemberDTO memberDTO = entityToDTO(result.get());

            log.info("existed................." + memberDTO);

            return memberDTO;
            //return
        }

        Member socialMember = makeSocialMember(nickname);

        memberRepository.save(socialMember);

        MemberDTO memberDTO = entityToDTO(socialMember);

        return memberDTO;
    }

    @Override
    public void modifyMember(MemberModifyDTO memberModifyDTO) {

        Optional<Member> result = memberRepository.findById(memberModifyDTO.getEmail());

        Member member = result.orElseThrow();

        member.changeNickname(memberModifyDTO.getNickname());
        member.changeSocial(false);
        member.changePw(passwordEncoder.encode(memberModifyDTO.getPw()));

        memberRepository.save(member);
    }

    private Member makeSocialMember(String email) {
        String tempPassword = makeTempPassword();

        log.info("tempPassword:"+tempPassword);

        Member member = Member.builder()
                .email(email)
                .pw(passwordEncoder.encode(tempPassword))
                .nickname("Social Member")
                .social(true)
                .build();

        member.addRole(MemberRole.USER);

        return member;
    }

    private String getEmailFromKakaoAccessToken(String accessToken){

        String kakaoGetUserURL = "https://kapi.kakao.com/v2/user/me";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(kakaoGetUserURL).build();

        ResponseEntity<LinkedHashMap> response =
                restTemplate.exchange(uriBuilder.toUri(), HttpMethod.GET, entity, LinkedHashMap.class);

        log.info("response-----------------------------------");
        log.info(response);

        LinkedHashMap<String, LinkedHashMap> bodyMap = response.getBody();

        LinkedHashMap<String, String> kakaoAccount = bodyMap.get("properties");
        log.info("kakaoAccount: " + kakaoAccount);

        String nickname = kakaoAccount.get("nickname");
        log.info("nickname: " + nickname);

        return nickname;

    }

    private String makeTempPassword() {
        StringBuffer buffer = new StringBuffer();

        for(int i=0; i<10; i++){
            buffer.append((char)((int)(Math.random()*55)+65));
        }
        return buffer.toString();
    }
}
