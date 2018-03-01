$('#add-step').click(function() {
    console.log("step button clicked");
    var index = $('.step-row').length;

    var newRow = '<div class="step-row">' +
                    '<input type="hidden" id="stepsList' + index + '.id" name="stepsList[' + index + '].id" value="' + (index + 1) + '" />' +
                        '<div class="prefix-20 grid-80">' +
                             '<p>' +
                                 '<input  type="text" id="stepsList' + index + '.description" name="stepsList[' + index + '].description" />' +
                             '</p>' +
                        '</div>' +
                    '</div>'

    console.log(newRow)
    $("#add-step-row" ).before(newRow);
    console.log("added new row");
});