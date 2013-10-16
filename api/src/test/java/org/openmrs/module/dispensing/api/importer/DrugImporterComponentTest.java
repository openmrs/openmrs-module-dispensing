package org.openmrs.module.dispensing.api.importer;

import org.junit.Test;
import org.openmrs.api.ConceptService;
import org.openmrs.module.dispensing.importer.DrugImporter;
import org.openmrs.module.dispensing.importer.ImportNotes;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class DrugImporterComponentTest extends BaseModuleContextSensitiveTest {

    @Autowired
    private DrugImporter drugImporter;

    @Autowired
    private ConceptService conceptService;


    @Test
    public void importSpreadsheet() throws Exception {

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("drug-list-component-test.csv");
        InputStreamReader reader = new InputStreamReader(inputStream);

        ImportNotes notes = drugImporter.importSpreadsheet(reader);
        assertFalse(notes.hasErrors());

        assertNotNull(conceptService.getDrug("Triomune-30, some other"));
        assertNotNull(conceptService.getDrug("Aspirin, 5 mg, tablet"));
        assertNotNull(conceptService.getDrug("NYQUIL, 125 mg/ 5 mL, 100 mL bottle"));

    }


}
