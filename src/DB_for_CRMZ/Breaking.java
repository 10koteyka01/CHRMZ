/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB_for_CRMZ;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "breaking")
@NamedQueries({
    @NamedQuery(name = "Breaking.findAll", query = "SELECT b FROM Breaking b"),
    @NamedQuery(name = "Breaking.findByIDbr", query = "SELECT b FROM Breaking b WHERE b.iDbr = :iDbr"),
    @NamedQuery(name = "Breaking.findByBrDate", query = "SELECT b FROM Breaking b WHERE b.brDate = :brDate"),
    @NamedQuery(name = "Breaking.findByDrDateMade", query = "SELECT b FROM Breaking b WHERE b.drDateMade = :drDateMade"),
    @NamedQuery(name = "Breaking.findByBrMesMade", query = "SELECT b FROM Breaking b WHERE b.brMesMade = :brMesMade"),
    @NamedQuery(name = "Breaking.findByBrDateSpare", query = "SELECT b FROM Breaking b WHERE b.brDateSpare = :brDateSpare"),
    @NamedQuery(name = "Breaking.findByBrStat", query = "SELECT b FROM Breaking b WHERE b.brStat = :brStat")})
public class Breaking implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_br")
    private Integer iDbr;
    @Basic(optional = false)
    @Column(name = "br_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date brDate;
    @Lob
    @Column(name = "br_mes")
    private String brMes;
    @Basic(optional = false)
    @Column(name = "dr_date_made")
    @Temporal(TemporalType.TIMESTAMP)
    private Date drDateMade;
    @Column(name = "br_mes_made")
    private String brMesMade;
    @Basic(optional = false)
    @Column(name = "br_date_spare")
    @Temporal(TemporalType.TIMESTAMP)
    private Date brDateSpare;
    @Column(name = "br_stat")
    private Character brStat;
    @JoinColumn(name = "ID_worker_made", referencedColumnName = "ID_worker")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Workers workers;
    @JoinColumn(name = "ID_worker", referencedColumnName = "ID_worker")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Workers workers1;
    @JoinColumn(name = "ID_machine", referencedColumnName = "ID_machine")
    @ManyToOne(fetch = FetchType.EAGER)
    private Machine machine;

    public Breaking() {
    }

    public Breaking(Integer iDbr) {
        this.iDbr = iDbr;
    }

    public Breaking(Integer iDbr, Date brDate, Date drDateMade, Date brDateSpare) {
        this.iDbr = iDbr;
        this.brDate = brDate;
        this.drDateMade = drDateMade;
        this.brDateSpare = brDateSpare;
    }

    public Integer getIDbr() {
        return iDbr;
    }

    public void setIDbr(Integer iDbr) {
        this.iDbr = iDbr;
    }

    public Date getBrDate() {
        return brDate;
    }

    public void setBrDate(Date brDate) {
        this.brDate = brDate;
    }

    public String getBrMes() {
        return brMes;
    }

    public void setBrMes(String brMes) {
        this.brMes = brMes;
    }

    public Date getDrDateMade() {
        return drDateMade;
    }

    public void setDrDateMade(Date drDateMade) {
        this.drDateMade = drDateMade;
    }

    public String getBrMesMade() {
        return brMesMade;
    }

    public void setBrMesMade(String brMesMade) {
        this.brMesMade = brMesMade;
    }

    public Date getBrDateSpare() {
        return brDateSpare;
    }

    public void setBrDateSpare(Date brDateSpare) {
        this.brDateSpare = brDateSpare;
    }

    public Character getBrStat() {
        return brStat;
    }

    public void setBrStat(Character brStat) {
        this.brStat = brStat;
    }

    public Workers getWorkers() {
        return workers;
    }

    public void setWorkers(Workers workers) {
        this.workers = workers;
    }

    public Workers getWorkers1() {
        return workers1;
    }

    public void setWorkers1(Workers workers1) {
        this.workers1 = workers1;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDbr != null ? iDbr.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Breaking)) {
            return false;
        }
        Breaking other = (Breaking) object;
        if ((this.iDbr == null && other.iDbr != null) || (this.iDbr != null && !this.iDbr.equals(other.iDbr))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DB_for_CRMZ.Breaking[ iDbr=" + iDbr + " ]";
    }
    
}
