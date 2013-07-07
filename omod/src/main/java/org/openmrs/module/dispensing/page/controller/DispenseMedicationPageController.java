/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.dispensing.page.controller;

import org.codehaus.jackson.JsonNode;
import org.openmrs.Person;
import org.openmrs.api.ProviderService;
import org.openmrs.module.appframework.domain.AppDescriptor;
import org.openmrs.module.providermanagement.Provider;
import org.openmrs.module.providermanagement.ProviderRole;
import org.openmrs.module.providermanagement.api.ProviderManagementService;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;


/**
 *
 */
public class DispenseMedicationPageController {
	
	public void get(PageModel model, @RequestParam("appId") AppDescriptor app,
                    @SpringBean("providerManagementService") ProviderManagementService providerManagementService) {
        JsonNode prescriberRoleUuids = app.getConfig().get("prescriberRoleUuids");
        List<ProviderRole> prescriberRoles = new ArrayList<ProviderRole>();
        for (JsonNode prescriberRoleUuid: prescriberRoleUuids) {
            ProviderRole prescriberRole = providerManagementService.getProviderRoleByUuid(prescriberRoleUuid.getTextValue());
            prescriberRoles.add(prescriberRole);
        }
        List<Provider> prescribers = providerManagementService.getProvidersByRoles(prescriberRoles);

        model.addAttribute("prescribers", prescribers);
    }
}
