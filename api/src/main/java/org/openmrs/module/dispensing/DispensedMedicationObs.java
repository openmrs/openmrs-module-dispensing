package org.openmrs.module.dispensing;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: nlima
 * Date: 10/14/13
 * Time: 3:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class DispensedMedicationObs {
    @JsonProperty
    private String label;

    @JsonProperty
    private String conceptCode;

    @JsonProperty
    private Map<String, String> params;

    private String value;

    public DispensedMedicationObs() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DispensedMedicationObs that = (DispensedMedicationObs) o;

        if (!this.getLabel().equals(that.getLabel())) {
            return false;
        }
        if (!this.getConceptCode().equals(that.conceptCode)) {
            return false;
        }

        if (params != null ? !params.equals(that.params) : that.params != null)  {
            return false;
        }

        return true;
    }


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getConceptCode() {
        return conceptCode;
    }

    public void setConceptCode(String conceptCode) {
        this.conceptCode = conceptCode;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
