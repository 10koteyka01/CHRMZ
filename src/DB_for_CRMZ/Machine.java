/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB_for_CRMZ;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "machine")
@NamedQueries({
    @NamedQuery(name = "Machine.findAll", query = "SELECT m FROM Machine m"),
    @NamedQuery(name = "Machine.findByDepartment", query = "SELECT m FROM Machine m WHERE m.department = :department"),
    @NamedQuery(name = "Machine.findByProducer", query = "SELECT m FROM Machine m WHERE m.producer = :producer"),
    @NamedQuery(name = "Machine.findByNameMachine", query = "SELECT m FROM Machine m WHERE m.nameMachine = :nameMachine"),
    @NamedQuery(name = "Machine.findByMachineNumber", query = "SELECT m FROM Machine m WHERE m.machineNumber = :machineNumber"),
    @NamedQuery(name = "Machine.findByIDmachine", query = "SELECT m FROM Machine m WHERE m.iDmachine = :iDmachine")})
public class Machine implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "department")
    private int department;
    @Column(name = "producer")
    private String producer;
    @Column(name = "name_machine")
    private String nameMachine;
    @Column(name = "machine_number")
    private Integer machineNumber;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_machine")
    private Integer iDmachine;
    @OneToMany(mappedBy = "machine", fetch = FetchType.EAGER)
    private Set<Breaking> breakingSet;

    public Machine() {
    }

    public Machine(Integer iDmachine) {
        this.iDmachine = iDmachine;
    }

    public Machine(Integer iDmachine, int department) {
        this.iDmachine = iDmachine;
        this.department = department;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        int oldDepartment = this.department;
        this.department = department;
        changeSupport.firePropertyChange("department", oldDepartment, department);
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        String oldProducer = this.producer;
        this.producer = producer;
        changeSupport.firePropertyChange("producer", oldProducer, producer);
    }

    public String getNameMachine() {
        return nameMachine;
    }

    public void setNameMachine(String nameMachine) {
        String oldNameMachine = this.nameMachine;
        this.nameMachine = nameMachine;
        changeSupport.firePropertyChange("nameMachine", oldNameMachine, nameMachine);
    }

    public Integer getMachineNumber() {
        return machineNumber;
    }

    public void setMachineNumber(Integer machineNumber) {
        Integer oldMachineNumber = this.machineNumber;
        this.machineNumber = machineNumber;
        changeSupport.firePropertyChange("machineNumber", oldMachineNumber, machineNumber);
    }

    public Integer getIDmachine() {
        return iDmachine;
    }

    public void setIDmachine(Integer iDmachine) {
        Integer oldIDmachine = this.iDmachine;
        this.iDmachine = iDmachine;
        changeSupport.firePropertyChange("IDmachine", oldIDmachine, iDmachine);
    }

    public Set<Breaking> getBreakingSet() {
        return breakingSet;
    }

    public void setBreakingSet(Set<Breaking> breakingSet) {
        this.breakingSet = breakingSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDmachine != null ? iDmachine.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Machine)) {
            return false;
        }
        Machine other = (Machine) object;
        if ((this.iDmachine == null && other.iDmachine != null) || (this.iDmachine != null && !this.iDmachine.equals(other.iDmachine))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DB_for_CRMZ.Machine[ iDmachine=" + iDmachine + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
