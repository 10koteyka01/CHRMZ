/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB_for_CRMZ;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "workers")
@NamedQueries({
    @NamedQuery(name = "Workers.findAll", query = "SELECT w FROM Workers w"),
    @NamedQuery(name = "Workers.findByFamily", query = "SELECT w FROM Workers w WHERE w.family = :family"),
    @NamedQuery(name = "Workers.findByName", query = "SELECT w FROM Workers w WHERE w.name = :name"),
    @NamedQuery(name = "Workers.findByPatronymic", query = "SELECT w FROM Workers w WHERE w.patronymic = :patronymic"),
    @NamedQuery(name = "Workers.findByTelephone", query = "SELECT w FROM Workers w WHERE w.telephone = :telephone"),
    @NamedQuery(name = "Workers.findByIDworker", query = "SELECT w FROM Workers w WHERE w.iDworker = :iDworker")})
public class Workers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "Family")
    private String family;
    @Column(name = "Name")
    private String name;
    @Column(name = "Patronymic")
    private String patronymic;
    @Column(name = "Telephone")
    private String telephone;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_worker")
    private Integer iDworker;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workers", fetch = FetchType.EAGER)
    private Set<Breaking> breakingSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workers1", fetch = FetchType.EAGER)
    private Set<Breaking> breakingSet1;

    public Workers() {
    }

    public Workers(Integer iDworker) {
        this.iDworker = iDworker;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getIDworker() {
        return iDworker;
    }

    public void setIDworker(Integer iDworker) {
        this.iDworker = iDworker;
    }

    public Set<Breaking> getBreakingSet() {
        return breakingSet;
    }

    public void setBreakingSet(Set<Breaking> breakingSet) {
        this.breakingSet = breakingSet;
    }

    public Set<Breaking> getBreakingSet1() {
        return breakingSet1;
    }

    public void setBreakingSet1(Set<Breaking> breakingSet1) {
        this.breakingSet1 = breakingSet1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDworker != null ? iDworker.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Workers)) {
            return false;
        }
        Workers other = (Workers) object;
        if ((this.iDworker == null && other.iDworker != null) || (this.iDworker != null && !this.iDworker.equals(other.iDworker))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DB_for_CRMZ.Workers[ iDworker=" + iDworker + " ]";
    }
    
}
