package com.project.mallapi.service;

import com.project.mallapi.domain.Member;
import com.project.mallapi.dto.MemberDTO;
import com.project.mallapi.dto.MemberModifyDTO;
import jakarta.transaction.Transactional;
import java.util.stream.Collectors;

@Transactional
public interface MemberService {

    MemberDTO getKakaoMember(String accessToken);

    void modifyMember(MemberModifyDTO memberModifyDTO);

    default MemberDTO entityToDTO(Member member) {

        MemberDTO dto = new MemberDTO(
                member.getEmail(),
                member.getPw(),
                member.getNickname(),
                member.isSocial(),
                member.getMemberRoleList().stream().map(memberRole -> memberRole.name()).collect(Collectors.toList())
        );
        return dto;
    }

}
