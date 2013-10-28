package org.openmrs.module.dispensing.page.controller;

import org.databene.benerator.gui.Setup;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.mockito.Matchers;
import org.openmrs.Patient;
import org.openmrs.api.PatientService;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.dispensing.DispensedMedication;
import org.openmrs.module.dispensing.api.DispensingService;
import org.openmrs.module.emrapi.adt.AdtService;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.Location;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nlima
 * Date: 10/25/13
 * Time: 3:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class FindPatientPageControllerTest {

    public FindPatientPageController findPatientController;
    public PageModel model;
    public UiSessionContext emrContext;
    public DispensingService dispensingService;
    public List<DispensedMedication> dispensedMedicationList;


    @Before
    public void setUp() {
        model = mock(PageModel.class);
        emrContext = mock(UiSessionContext.class);
        dispensingService =mock(DispensingService.class);

        findPatientController = new FindPatientPageController();

        Location location = new Location();
        when(emrContext.getSessionLocation()).thenReturn(location);
        findPatientController.controller(emrContext,model,dispensingService);
    }

    @Test
    public void shouldAddMedicationListAtModel() {
        Location location = mock(Location.class);
        Calendar cal = Calendar.getInstance();
        Date currentTime = cal.getTime();
        cal.add(Calendar.MONTH, -6);
        Date sixMonthsAgo = cal.getTime();
        dispensedMedicationList = new ArrayList<DispensedMedication>();

        when(emrContext.getSessionLocation()).thenReturn(location);
        when(dispensingService.getDispensedMedication(isNull(Patient.class), Matchers.anyListOf(Location.class), eq( sixMonthsAgo) , eq( currentTime), isNull(Integer.class), isNull(Integer.class))).thenReturn(dispensedMedicationList);

        findPatientController.controller(emrContext, model, dispensingService);

        verify(model, atLeastOnce()).addAttribute("dispensedMedicationListbyLocation", new ArrayList<DispensedMedication>());
    }
}
