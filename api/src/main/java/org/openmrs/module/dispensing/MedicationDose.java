package org.openmrs.module.dispensing;

public class MedicationDose {

    Integer dose;
    String units;

    public MedicationDose() {
    }

    public MedicationDose(Integer dose, String units) {
        this.dose = dose;
        this.units = units;
    }

    public Integer getDose() {
        return dose;
    }

    public void setDose(Integer dose) {
        this.dose = dose;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
}
