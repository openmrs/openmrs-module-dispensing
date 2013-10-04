package org.openmrs.module.dispensing.descriptor;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Concept;
import org.openmrs.Drug;
import org.openmrs.Obs;
import org.openmrs.api.ConceptService;
import org.openmrs.module.dispensing.DispensedMedication;
import org.openmrs.module.dispensing.DispensingApiConstants;
import org.openmrs.module.dispensing.MedicationDose;
import org.openmrs.module.dispensing.MedicationDuration;
import org.openmrs.module.emrapi.EmrApiConstants;
import org.openmrs.module.emrapi.descriptor.ConceptSetDescriptor;

public class DispensingConceptSetDescriptor extends ConceptSetDescriptor {

    private Concept dispensingSetConcept;
    private Concept medicationOrdersConcept;
    private Concept quantityOfMedicationDispensedConcept;
    private Concept generalDrugFrequencyConcept;
    private Concept quantityOfMedicationPrescribedPerDoseConcept;
    private Concept unitsOfMedicationPrescribedPerDoseConcept;
    private Concept medicationDurationConcept;
    private Concept timeUnitsConcept;

    public DispensingConceptSetDescriptor(ConceptService conceptService) {
        setup(conceptService, EmrApiConstants.EMR_CONCEPT_SOURCE_NAME,
                "dispensingSetConcept", DispensingApiConstants.CONCEPT_CODE_DISPENSING_MEDICATION_CONCEPT_SET,
                "medicationOrdersConcept", DispensingApiConstants.CONCEPT_CODE_MEDICATION_ORDERS,
                "quantityOfMedicationDispensedConcept", DispensingApiConstants.CONCEPT_CODE_QUANTITY_OF_MEDICATION_DISPENSED,
                "generalDrugFrequencyConcept", DispensingApiConstants.CONCEPT_CODE_GENERAL_DRUG_FREQUENCY,
                "quantityOfMedicationPrescribedPerDoseConcept", DispensingApiConstants.CONCEPT_CODE_QUANTITY_OF_MEDICATION_PRESCRIBED_PER_DOSE,
                "unitsOfMedicationPrescribedPerDoseConcept", DispensingApiConstants.CONCEPT_CODE_UNITS_OF_MEDICATION_PRESCRIBED_PER_DOSE,
                "medicationDurationConcept", DispensingApiConstants.CONCEPT_CODE_MEDICATION_DURATION,
                "timeUnitsConcept", DispensingApiConstants.CONCEPT_CODE_TIME_UNITS);
    }

    public DispensingConceptSetDescriptor() {
    }

    public Concept getDispensingSetConcept() {
        return dispensingSetConcept;
    }

    public void setDispensingSetConcept(Concept dispensingSetConcept) {
        this.dispensingSetConcept = dispensingSetConcept;
    }

    public Concept getGeneralDrugFrequencyConcept() {
        return generalDrugFrequencyConcept;
    }

    public void setGeneralDrugFrequencyConcept(Concept generalDrugFrequencyConcept) {
        this.generalDrugFrequencyConcept = generalDrugFrequencyConcept;
    }

    public Concept getMedicationDurationConcept() {
        return medicationDurationConcept;
    }

    public void setMedicationDurationConcept(Concept medicationDurationConcept) {
        this.medicationDurationConcept = medicationDurationConcept;
    }

    public Concept getMedicationOrdersConcept() {
        return medicationOrdersConcept;
    }

    public void setMedicationOrdersConcept(Concept medicationOrdersConcept) {
        this.medicationOrdersConcept = medicationOrdersConcept;
    }

    public Concept getQuantityOfMedicationDispensedConcept() {
        return quantityOfMedicationDispensedConcept;
    }

    public void setQuantityOfMedicationDispensedConcept(Concept quantityOfMedicationDispensedConcept) {
        this.quantityOfMedicationDispensedConcept = quantityOfMedicationDispensedConcept;
    }

    public Concept getQuantityOfMedicationPrescribedPerDoseConcept() {
        return quantityOfMedicationPrescribedPerDoseConcept;
    }

    public void setQuantityOfMedicationPrescribedPerDoseConcept(Concept quantityOfMedicationPrescribedPerDoseConcept) {
        this.quantityOfMedicationPrescribedPerDoseConcept = quantityOfMedicationPrescribedPerDoseConcept;
    }

    public Concept getTimeUnitsConcept() {
        return timeUnitsConcept;
    }

    public void setTimeUnitsConcept(Concept timeUnitsConcept) {
        this.timeUnitsConcept = timeUnitsConcept;
    }

    public Concept getUnitsOfMedicationPrescribedPerDoseConcept() {
        return unitsOfMedicationPrescribedPerDoseConcept;
    }

    public void setUnitsOfMedicationPrescribedPerDoseConcept(Concept unitsOfMedicationPrescribedPerDoseConcept) {
        this.unitsOfMedicationPrescribedPerDoseConcept = unitsOfMedicationPrescribedPerDoseConcept;
    }

    public boolean isDispensedMedication(Obs obsGroup){
        return (obsGroup != null) ? obsGroup.getConcept().equals(dispensingSetConcept) : false;
    }

    public DispensedMedication toDispensedMedication(Obs obsGroup){
        if (!isDispensedMedication(obsGroup)) {
            throw new IllegalArgumentException("Not an obs group for a dispensed diagnosis" + obsGroup);
        }
        DispensedMedication dispensedMedication = new DispensedMedication();
        dispensedMedication.setDispensedDateTime(obsGroup.getEncounter().getEncounterDatetime());
        Obs obs = findMember(obsGroup, medicationOrdersConcept);
        if (obs == null){
            throw new IllegalArgumentException("Obs group does not contain a drug observation: " + obsGroup);
        }
        Drug drug = obs.getValueDrug();
        if (drug == null ){
            throw new IllegalArgumentException("Obs group does not contain a drug: " + obs);
        }
        dispensedMedication.setDrug(drug);
        obs = findMember(obsGroup, quantityOfMedicationPrescribedPerDoseConcept);
        Integer dose = null;
        if (obs != null) {
            Double valueNumeric = obs.getValueNumeric();
            if (valueNumeric != null) {
                dose = new Integer(valueNumeric.intValue());
            }
        }
        obs = findMember(obsGroup, unitsOfMedicationPrescribedPerDoseConcept);
        String units = null;
        if (obs != null){
            units = obs.getValueText();
        }
        if (dose != null && StringUtils.isNotBlank(units)){
            dispensedMedication.setMedicationDose(new MedicationDose(dose, units));
        }
        obs = findMember(obsGroup, generalDrugFrequencyConcept);
        if (obs != null){
            String frequency = obs.getValueText();
            if (StringUtils.isNotEmpty(frequency)){
                dispensedMedication.setPrescribedFrequency(frequency);
            }
        }
        obs = findMember(obsGroup, medicationDurationConcept);
        Integer duration = null;
        if (obs != null) {
            Double valueNumeric = obs.getValueNumeric();
            if (valueNumeric != null){
                duration = new Integer(valueNumeric.intValue());
            }
        }
        obs = findMember(obsGroup, timeUnitsConcept);
        if (obs != null) {
            units = obs.getValueCoded().getName().getName();
        }
        if (duration != null && StringUtils.isNotEmpty(units)){
            dispensedMedication.setMedicationDuration(new MedicationDuration(duration, units));
        }
        obs = findMember(obsGroup, quantityOfMedicationDispensedConcept);
        if (obs != null) {
            Double valueNumeric = obs.getValueNumeric();
            if (valueNumeric != null) {
                dispensedMedication.setQuantityDispensed(new Integer(valueNumeric.intValue()));
            }
        }
        return dispensedMedication;

    }

}
