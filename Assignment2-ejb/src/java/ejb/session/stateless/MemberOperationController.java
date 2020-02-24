package ejb.session.stateless;

import entity.MemberEntity;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.InvalidLoginCredentialException;
import util.exception.MemberNotFoundException;

@Stateless
@Local(MemberOperationControllerLocal.class)
@Remote(MemberOperationControllerRemote.class)
public class MemberOperationController implements MemberOperationControllerRemote, MemberOperationControllerLocal {

    @PersistenceContext(unitName = "Assignment2-ejbPU")
    private EntityManager em;

    @Override
    public MemberEntity createNewMember(MemberEntity newMemberEntity) {
        em.persist(newMemberEntity);
        em.flush();

        return newMemberEntity;
    }

    @Override
    public List<entity.MemberEntity> retrieveAllMember() {
        Query query = em.createQuery("SELECT m FROM MemberEntity m");

        return query.getResultList();
    }

    @Override
    public MemberEntity retrieveMemberByMemberId(long memberId) throws MemberNotFoundException {
        MemberEntity memberEntity = em.find(MemberEntity.class, memberId);

        if (memberEntity != null) {
            memberEntity.getFines().size();
            memberEntity.getLendings().size();
            return memberEntity;
        } else {
            throw new MemberNotFoundException("Member ID " + memberId + " does not exist!");
        }
    }

    @Override
    public MemberEntity retrieveMemberByIdentityNum(String identityNum) throws MemberNotFoundException {
        Query query = em.createQuery("SELECT m FROM MemberEntity m WHERE m.identityNum = :inIdentityNum");
        query.setParameter("inIdentityNum", identityNum);

        try {
            MemberEntity member = (MemberEntity) query.getSingleResult();
            member.getFines().size();
            member.getLendings().size();
            return member;
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new MemberNotFoundException("Member Username " + identityNum + " does not exist!");
        }
    }

    @Override
    public MemberEntity memberLogin(String identityNum, String securityCode) throws InvalidLoginCredentialException {
        try {
            MemberEntity memberEntity = retrieveMemberByIdentityNum(identityNum);

            if (memberEntity.getSecurityCode().equals(securityCode)) {
                memberEntity.getLendings().size();
                memberEntity.getFines().size();
                return memberEntity;
            } else {
                throw new InvalidLoginCredentialException("Identity Number does not exist or invalid password!");
            }
        } catch (MemberNotFoundException ex) {
            throw new InvalidLoginCredentialException("Identity Number does not exist or invalid password!");
        }
    }

    @Override
    public void updateMember(MemberEntity memberEntity) {
        em.merge(memberEntity);
    }

    @Override
    public void deleteMember(long memberId) throws MemberNotFoundException {
        MemberEntity memberEntityToRemove = retrieveMemberByMemberId(memberId);
        em.remove(memberEntityToRemove);
    }

}
