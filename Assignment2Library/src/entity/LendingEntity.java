package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import util.enumeration.StatusEnum;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class LendingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn
    @XmlTransient
    private BookEntity book;
    @ManyToOne
    @JoinColumn
    @XmlTransient
    private MemberEntity member;
    @Temporal(TemporalType.TIMESTAMP)
    private Date loanDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date reserveDate;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;

    public LendingEntity() {
    }

    public LendingEntity(BookEntity book, MemberEntity member, Date loanDate, StatusEnum status, Date dueDate) {
        this.book = book;
        this.member = member;
        this.loanDate = loanDate;
        this.status = status;
        this.dueDate = dueDate;
    }

    public LendingEntity(BookEntity book, MemberEntity member, Date reserveDate, StatusEnum status) {
        this.book = book;
        this.member = member;
        this.reserveDate = reserveDate;
        this.status = status;
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
        if (!(object instanceof LendingEntity)) {
            return false;
        }
        LendingEntity other = (LendingEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.LendingEntity[ id=" + id + " ]";
    }

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    public MemberEntity getMember() {
        return member;
    }

    public void setMember(MemberEntity member) {
        this.member = member;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(Date reserveDate) {
        this.reserveDate = reserveDate;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

}
