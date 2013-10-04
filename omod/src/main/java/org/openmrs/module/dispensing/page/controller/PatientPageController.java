package org.openmrs.module.dispensing.page.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Form;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.api.FormService;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.dispensing.DispensedMedication;
import org.openmrs.module.dispensing.api.DispensingService;
import org.openmrs.module.emrapi.adt.AdtService;
import org.openmrs.module.emrapi.patient.PatientDomainWrapper;
import org.openmrs.module.emrapi.visit.VisitDomainWrapper;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.InjectBeans;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

public class PatientPageController {

    private static final Log log = LogFactory.getLog(PatientPageController.class);

    public void controller(@RequestParam("patientId") Patient patient,
                           UiUtils ui,
                           UiSessionContext emrContext,
                           PageModel model,
                           @SpringBean("formService") FormService formService,
                           @SpringBean("adtService") AdtService adtService,
                           @SpringBean("dispensingService") DispensingService dispensingService,
                           @InjectBeans PatientDomainWrapper patientDomainWrapper) {

        patientDomainWrapper.setPatient(patient);
        SimpleObject appHomepageBreadcrumb = SimpleObject.create("label", ui.message("dispensing.app.dispensing.title"),
                "link", ui.pageLink("dispensing", "findPatient?app=dispensing.app"));
        SimpleObject patientPageBreadcrumb = SimpleObject.create("label",
                patient.getFamilyName() + ", " + patient.getGivenName(), "link", ui.thisUrlWithContextPath());

        Form dispensingForm = formService.getFormByUuid("ba22eda5-148d-456e-8adc-f36247d1f7c3");

        Location visitLocation = adtService.getLocationThatSupportsVisits(emrContext.getSessionLocation());
        VisitDomainWrapper activeVisit = adtService.getActiveVisit(patient, visitLocation);

        //get all dispensing encounters
        List<DispensedMedication> dispensedMedicationList = dispensingService.getDispensedMedication(patient, null, null, null, null, null);
        if (dispensedMedicationList != null && dispensedMedicationList.size() > 0 ){
            for (DispensedMedication dispensedMedication : dispensedMedicationList) {
                log.error("drugName: " + dispensedMedication.getDrug().getDisplayName());
                log.error("dose: " + dispensedMedication.getMedicationDose().getDose().toString() + " " + dispensedMedication.getMedicationDose().getUnits());
                log.error("frequency: " + dispensedMedication.getPrescribedFrequency());
                log.error("duration: " + dispensedMedication.getMedicationDuration().getDuration().toString() + " " + dispensedMedication.getMedicationDuration().getTimeUnits());
                log.error("quantity dispensed: " + dispensedMedication.getQuantityDispensed().toString());
            }
        }
        model.addAttribute("dispensedMedicationList", dispensedMedicationList);

        model.addAttribute("visit", activeVisit != null ? activeVisit.getVisit() : null);
        model.addAttribute("patient", patientDomainWrapper);
        model.addAttribute("breadcrumbOverride", ui.toJson(Arrays.asList(appHomepageBreadcrumb, patientPageBreadcrumb)));

    }
}
