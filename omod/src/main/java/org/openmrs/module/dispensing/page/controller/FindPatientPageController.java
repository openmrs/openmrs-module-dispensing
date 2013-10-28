package org.openmrs.module.dispensing.page.controller;

import org.openmrs.Location;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.dispensing.DispensedMedication;
import org.openmrs.module.dispensing.api.DispensingService;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nlima
 * Date: 10/25/13
 * Time: 1:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class FindPatientPageController {

    public Location currentLocation;

    public void controller(UiSessionContext emrContext,
                           PageModel model, @SpringBean("dispensingService") DispensingService dispensingService){


        currentLocation = emrContext.getSessionLocation();
        List<Location> locationList = new ArrayList<Location>();
        locationList.add(currentLocation);

        Calendar cal = Calendar.getInstance();
        Date currentTime = cal.getTime();
        cal.add(Calendar.MONTH, -6);
        Date sixMonthsAgo = cal.getTime();

        List<DispensedMedication> dispensedMedicationList = dispensingService.getDispensedMedication(null,locationList,sixMonthsAgo, currentTime,null,null);

        model.addAttribute("dispensedMedicationListbyLocation", dispensedMedicationList);



    }


}
