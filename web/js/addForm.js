$(document).ready(function () {

    $(document).on("click", ":button", function () {
        let but = $(this);
        let oldBl = $("#QUALIFICATIONS");
        let newBl = oldBl.clone();
        oldBl.children(but).remove(":button");
        newBl.appendTo(oldBl);

        let oldBl2 = $("#EXPERIENCE");
        let newBl2 = oldBl2.clone();
        oldBl2.children(but).remove(":button");
        newBl2.appendTo(oldBl2);
    });
});
