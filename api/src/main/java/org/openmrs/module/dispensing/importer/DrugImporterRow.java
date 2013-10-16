package org.openmrs.module.dispensing.importer;

public class DrugImporterRow {

    // OpenBoxes code currently not used

    public static final String[] FIELD_COLUMNS = { "uuid", "openBoxesCode", "productName", "concept" };

    private String uuid;

    private String openBoxesCode;

    private String productName;

    private String concept;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOpenBoxesCode() {
        return openBoxesCode;
    }

    public void setOpenBoxesCode(String openBoxesCode) {
        this.openBoxesCode = openBoxesCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }
}
