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

</script>


<h1>
    ${ ui.message("dispensing.app.label") }
</h1>


${ ui.includeFragment("emr", "widget/findPatient", [
        targetPageProvider: "dispensing",
        targetPage: "patient"
]) }
