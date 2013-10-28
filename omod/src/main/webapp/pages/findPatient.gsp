<%
    if (emrContext.authenticated && !emrContext.currentProvider) {
        throw new IllegalStateException("Logged-in user is not a Provider")
    }

    ui.decorateWith("appui", "standardEmrPage")
%>

<script type="text/javascript">
    var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${ ui.message("dispensing.app.label") }", link: "${ ui.pageLink("dispensing", "findPatient") }" }
    ];
</script>

<script type="text/javascript">
    var medicationListSize = "${ dispensedMedicationListbyLocation != null ? dispensedMedicationListbyLocation.size() : 0 }";
</script>

<script type="text/javascript">
    jq(function() {

        if (parseInt (medicationListSize, 10) > 0 ) {
            jq("#medicationTable").dataTable({
                bFilter: false,
                bJQueryUI: true,
                bLengthChange: false,
                iDisplayLength: 10,
                sPaginationType: "full_numbers",
                bSort: false,
                bAutoWidth: false,
                aoColumns: [
                    {"sWidth": "13%"},
                    {"sWidth": "20%"},
                    {"sWidth": "7%"},
                    {"sWidth": "12%"},
                    {"sWidth": "10%"},
                    {"sWidth": "12%"},
                    {"sWidth": "26%"}
                ],
                sDom: 't<"fg-toolbar ui-toolbar ui-corner-bl ui-corner-br ui-helper-clearfix datatables-info-and-pg"ip>'
            });
        }
    });
</script>

<h1>
    ${ ui.message("dispensing.app.label") }
</h1>


${ ui.includeFragment("emr", "widget/findPatient", [
        targetPageProvider: "dispensing",
        targetPage: "patient"
]) }


<div class="container">

    <div id="medication-list">
        <h3>${ ui.message("dispensing.medication.lastDispensedByLocation") }</h3>
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
            <% if ( (dispensedMedicationListbyLocation == null)
                    || (dispensedMedicationListbyLocation!= null && dispensedMedicationListbyLocation.size() == 0)) { %>
            <tr>
                <td colspan="7">${ ui.message("coreapps.none") }</td>
            </tr>
            <% } %>
            <% dispensedMedicationListbyLocation.each { medication ->
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
    </div>

</div>

