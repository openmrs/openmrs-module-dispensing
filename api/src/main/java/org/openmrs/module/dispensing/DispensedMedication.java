package org.openmrs.module.dispensing;

import org.openmrs.Drug;
import org.openmrs.Obs;
import java.util.Date;
import java.util.List;

public class DispensedMedication {

    Date dispensedDateTime;
    Drug drug;
    MedicationDose medicationDose;
    String prescribedFrequency;
    Integer quantityDispensed;
    MedicationDuration medicationDuration;
    List<DispensedMedicationObs> additionalObs;
    Obs existingObs;

    public DispensedMedication() {
    }

    public DispensedMedication(Date dispensedDateTime, Drug drug, Obs existingObs, MedicationDose medicationDose, MedicationDuration medicationDuration, String prescribedFrequency, Integer quantityDispensed, List<DispensedMedicationObs> additonalObs) {
        this.dispensedDateTime = dispensedDateTime;
        this.drug = drug;
        this.existingObs = existingObs;
        this.medicationDose = medicationDose;
        this.medicationDuration = medicationDuration;
        this.prescribedFrequency = prescribedFrequency;
        this.quantityDispensed = quantityDispensed;
        this.additionalObs = additonalObs;


    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public Obs getExistingObs() {
        return existingObs;
    }

    public void setExistingObs(Obs existingObs) {
        this.existingObs = existingObs;
    }

    public MedicationDose getMedicationDose() {
        return medicationDose;
    }

    public void setMedicationDose(MedicationDose medicationDose) {
        this.medicationDose = medicationDose;
    }

    public MedicationDuration getMedicationDuration() {
        return medicationDuration;
    }

    public void setMedicationDuration(MedicationDuration medicationDuration) {
        this.medicationDuration = medicationDuration;
    }

    public String getPrescribedFrequency() {
        return prescribedFrequency;
    }

    public void setPrescribedFrequency(String prescribedFrequency) {
        this.prescribedFrequency = prescribedFrequency;
    }

    public Integer getQuantityDispensed() {
        return quantityDispensed;
    }

    public void setQuantityDispensed(Integer quantityDispensed) {
        this.quantityDispensed = quantityDispensed;
    }

    public Date getDispensedDateTime() {
        return dispensedDateTime;
    }

    public void setDispensedDateTime(Date dispensedDateTime) {
        this.dispensedDateTime = dispensedDateTime;
    }

    public List<DispensedMedicationObs> getAdditionalObs() {
        return additionalObs;
    }

    public void setAdditionalObs(List<DispensedMedicationObs> additionalObs) {
        this.additionalObs = additionalObs;
    }
}
