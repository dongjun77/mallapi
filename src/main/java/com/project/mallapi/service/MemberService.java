package com.project.mallapi.service;

import com.project.mallapi.dto.MemberDTO;
import jakarta.transaction.Transactional;

@Transactional
public interface MemberService {

    MemberDTO getKakaoMember(String accessToken);

}
