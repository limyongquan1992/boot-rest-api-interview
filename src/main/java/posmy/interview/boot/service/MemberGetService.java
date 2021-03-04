package posmy.interview.boot.service;

import org.springframework.stereotype.Service;
import posmy.interview.boot.entity.MyUser;
import posmy.interview.boot.enums.MyRole;
import posmy.interview.boot.model.request.EmptyRequest;
import posmy.interview.boot.model.response.MemberGetResponse;
import posmy.interview.boot.repos.MyUserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberGetService implements BaseService<EmptyRequest, MemberGetResponse> {

    private final MyUserRepository myUserRepository;

    public MemberGetService(MyUserRepository myUserRepository) {
        this.myUserRepository = myUserRepository;
    }

    @Override
    public MemberGetResponse execute(EmptyRequest request) {
        List<MyUser> members = myUserRepository.findAllByAuthority(MyRole.MEMBER.authority);
        return MemberGetResponse.builder()
                .members(mapToUserDetailsDtos(members))
                .build();
    }

    private List<MemberGetResponse.UserDetailsDto> mapToUserDetailsDtos(List<MyUser> members) {
        List<MemberGetResponse.UserDetailsDto> userDetailsDtoList = new ArrayList<>();
        for (MyUser member : members)
            userDetailsDtoList.add(mapFrom(member));
        return userDetailsDtoList;
    }

    private MemberGetResponse.UserDetailsDto mapFrom(MyUser member) {
        return MemberGetResponse.UserDetailsDto.builder()
                .id(member.getId())
                .username(member.getUsername())
                .email(member.getEmail())
                .build();
    }
}
