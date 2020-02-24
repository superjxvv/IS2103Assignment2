package ejb.session.stateless;

import entity.MemberEntity;
import java.util.List;
import util.exception.InvalidLoginCredentialException;
import util.exception.MemberNotFoundException;

public interface MemberOperationControllerRemote {

    MemberEntity createNewMember(MemberEntity newMemberEntity);

    List<entity.MemberEntity> retrieveAllMember();

    MemberEntity retrieveMemberByMemberId(long memberId) throws MemberNotFoundException;

    MemberEntity memberLogin(String username, String password) throws InvalidLoginCredentialException;

    void updateMember(MemberEntity memberEntity);

    void deleteMember(long memberId) throws MemberNotFoundException;

    MemberEntity retrieveMemberByIdentityNum(String identityNum) throws MemberNotFoundException;
    
}
