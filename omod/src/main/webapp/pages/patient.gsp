<%
    ui.decorateWith("appui", "standardEmrPage")
    ui.includeJavascript("uicommons", "datatables/jquery.dataTables.min.js")
%>

<script type="text/javascript">
    var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${ ui.message("dispensing.app.label") }", link: "${ ui.pageLink("dispensing", "findPatient") }" },
        { label: "${ ui.format(patient.patient.familyName) }, ${ ui.format(patient.patient.givenName) }" , link: '${ui.pageLink("coreapps", "patientdashboard/patientDashboard", [patientId: patient.id])}'},
    ];

    var medicationListSize = "${ dispensedMedicationList != null ? dispensedMedicationList.size() : 0 }";
</script>

${ ui.includeFragment("coreapps", "patientHeader", [ patient: patient.patient ]) }

<script type="text/javascript">
    jq(function() {
        jq('#actions .cancel').click(function() {
            emr.navigateTo({
                provider: "dispensing",
                page: "findPatient"
            });
        });
        jq('#actions .confirm').click(function() {
            emr.navigateTo({
                provider: "htmlformentryui",
                page: "htmlform/enterHtmlFormWithStandardUi",
                query: {
                    patientId: "${ patient.id }",
                    visitId: "${ visit?.id }",
                    definitionUiResource: "dispensing:htmlforms/dispensing.xml",
                    returnUrl: "${ ui.escapeJs(ui.pageLink("dispensing", "findPatient")) }",
                    breadcrumbOverride: "${ ui.escapeJs(breadcrumbOverride) }"
                }
            });
        });
        jq('#actions button').first().focus();
    });
</script>
<style>
#existing-encounters {
    margin-top: 2em;
}
</style>

<% if (emrContext.activeVisit) { %>

<div class="container">

    <h1>${ ui.message("coreapps.vitals.confirmPatientQuestion") }</h1>

    <div id="actions" class="half-width">
        <button class="confirm big right">
            <i class="icon-arrow-right"></i>
            ${ ui.message("dispensing.findpatient.confirm.yes") }
        </button>

        <button class="cancel big">
            <i class="icon-arrow-left"></i>
            ${ ui.message("coreapps.vitals.confirm.no") }
        </button>
    </div>

    <div id="medication-list">
        <h3>${ ui.message("dispensing.medication.lastDispensedByPatient") }</h3>
        <table id="medicationTable">
            <thead>
            <tr>
                <th>${ ui.message("coreapps.patientDashBoard.date") }</th>
                <th>${ ui.message("dispensing.medication.name") }</th>
                <th>${ ui.message("dispensing.medication.dose") }</th>
                <th>${ ui.message("dispensing.medication.frequency") }</th>
                <th>${ ui.message("dispensing.medication.duration") }</th>
                <th>${ ui.message("dispensing.medication.dispensed") }</th>
                <th>${ ui.message("dispensing.medication.origin") }</th>

            </tr>
            </thead>
            <tbody>
            <% if ( (dispensedMedicationList == null)
                    || (dispensedMedicationList!= null && dispensedMedicationList.size() == 0)) { %>
            <tr>
                <td colspan="7">${ ui.message("coreapps.none") }</td>
            </tr>
            <% } %>
            <% dispensedMedicationList.each { medication ->
                // def minutesAgo = (long) ((System.currentTimeMillis() - enc.encounterDatetime.time) / 1000 / 60)
            %>
            <tr>
                <td>${ ui.format(medication.dispensedDateTime) }</td>
                <td>${ ui.format(medication.drug.displayName) }</td>
                <td>${ ui.format(medication.medicationDose.dose) + " " + medication.medicationDose.units }</td>
                <td>${ ui.format(medication.prescribedFrequency) }</td>
                <td>${ ui.format(medication.medicationDuration.duration) + " " + medication.medicationDuration.timeUnits }</td>
                <td>${ ui.format(medication.quantityDispensed) }</td>
                <td>${ ui.format(medication.timingOfHospitalPrescription)  + " - " + medication.dischargeLocation}  </td>
            </tr>
            <% } %>
            </tbody>
        </table>


        ${ ui.includeFragment("uicommons", "widget/dataTable", [ object: "#medicationTable",
                options: [
                        bFilter: true,
                        bJQueryUI: true,
                        bLengthChange: false,
                        iDisplayLength: 10,
                        sPaginationType: '\"full_numbers\"',
                        bSort: false,
                        sDom: '\'ft<\"fg-toolbar ui-toolbar ui-corner-bl ui-corner-br ui-helper-clearfix datatables-info-and-pg \"ip>\''
                ]
        ]) }

    </div>

</div>

<% } else { %>

<h1>
    ${ ui.message("coreapps.noActiveVisit") }
</h1>

<div id="actions">
    <button class="cancel big">
        <i class="icon-arrow-left"></i>
        ${ ui.message("mirebalias.outpatientVitals.noVisit.findAnotherPatient") }
    </button>
</div>

<% } %>