var descData ="";
$("#bodyTable").on("click", "td", function () {
    var row = $(this).closest("tr");

    var rowData = {
        id: $(row).find("td:eq(0)").text(),
        name: $(row).find("td:eq(1)").text(),
        date: $(row).find("td:eq(2)").text(),
        time: $(row).find("td:eq(3)").text(),
        description: $(row).find("td:eq(4)").text()
    };
    console.log(rowData.name,"id")
    descData = rowData.description;
    $("#popupUpdate").css("display", "block");
    $("#cancelBtn").css("display", "block");

    $("#editId").val(rowData.id);
    $("#editId2").val(rowData.id);
    $("#editMessage").val(rowData.description);
    $("#editMessage2").val(rowData.description);
    $("#name2").val(rowData.name);
});

$("#cancelBtn").on("click",function (){
    $("#popupUpdate").css("display", "none");
});

$("#reply").off('input').on('input', function() {
    var currentValue = $(this).val().trim();
    console.log(currentValue)
    console.log(descData)
    if (descData === currentValue) {
        $("#submitbtn").hide();
        $("#deleteBtn").show();
    } else {
        $("#submitbtn").show();
        $("#deleteBtn").hide();
    }
});

function sendMail(){

}