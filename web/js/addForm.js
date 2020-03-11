$(document).ready(function () {
    let achiCoutn = "ACHIEVEMENT_";
    let qualCount = "QUALIFICATIONS_";

    $(document).on("click", ".addButton", function () {
        let but = $(this);
        let oldBl = but.parent().parent();
        let newBl = oldBl.clone();
        newBl.children().children($(":input")).val("");
        if (newBl.children().children($("input")).attr("name").substring(0,6) === "ACHIEV") {
            newBl.children().children($(":input")).prop("name", achiCoutn + but.attr("id"));
        } else {
            newBl.children().children($(":input")).prop("name", qualCount + but.attr("id"));
        }
        oldBl.children().children(but).remove(".addButton");
        newBl.appendTo(oldBl);
    });
});
