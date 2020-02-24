package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class MemberEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "member")
    @XmlTransient
    private List<LendingEntity> lendings;
    @OneToMany
    private List<FineEntity> fines;
    private String firstName;
    private String lastName;
    private String gender;
    private Integer age;
    @Column(unique = true)
    private String identityNum;
    private String phone;
    private String address;
    private String securityCode;

    public MemberEntity() {
    }

    public MemberEntity(List<LendingEntity> lendings, List<FineEntity> fines, String firstName, String lastName, String gender, Integer age, String identityNum, String phone, String address, String securityCode) {
        this.lendings = lendings;
        this.fines = fines;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.identityNum = identityNum;
        this.phone = phone;
        this.address = address;
        this.securityCode = securityCode;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MemberEntity)) {
            return false;
        }
        MemberEntity other = (MemberEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.MemberEntity[ id=" + id + " ]";
    }

    public List<LendingEntity> getLendings() {
        return lendings;
    }

    public void setLendings(List<LendingEntity> lendings) {
        this.lendings = lendings;
    }

    public List<FineEntity> getFines() {
        return fines;
    }

    public void setFines(List<FineEntity> fines) {
        this.fines = fines;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIdentityNum() {
        return identityNum;
    }

    public void setIdentityNum(String identityNum) {
        this.identityNum = identityNum;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addFines(FineEntity fine) {
        if (!this.fines.contains(fine)) {
            this.fines.add(fine);
        } else {
            System.out.println("Fine already exist");
        }
    }

    public void removeFine(FineEntity fine) {
        if (this.fines.contains(fine)) {
            this.fines.remove(fine);
        } else {
            System.out.println("Fine missing");
        }
    }

    public void addLending(LendingEntity lending) {
        if (!this.lendings.contains(lending)) {
            this.lendings.add(lending);
        } else {
            System.out.println("Lending already exist");
        }
    }

    public void removeLending(LendingEntity lending) {
        if (this.lendings.contains(lending)) {
            this.lendings.remove(lending);
        } else {
            System.out.println("Lending missing");
        }
    }
}
