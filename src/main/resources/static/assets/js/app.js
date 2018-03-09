$('#add-step').click(function() {
    var index = $('.step-row').length;

    var newRow = '<div class="step-row">' +
                    '<input type="hidden" id="steps' + index + '.id" name="steps[' + index + '].id" value="' + (index + 1) + '" />' +
                        '<div class="prefix-20 grid-80">' +
                             '<p>' +
                                 '<input  type="text" id="steps' + index + '.stepName" name="steps[' + index + '].stepName" />' +
                             '</p>' +
                        '</div>' +
                    '</div>'

    $("#add-step-row" ).before(newRow);
});

$('#add-ingredient').click(function() {
    var index = $('.ingredient-row').length;

    var newRow = '<div class="ingredient-row">' +
                    '<input type="hidden" id="ingredients' + index + '.id" name="ingredients[' + index + '].id" value="' + (index + 1) + '" />' +
                        '<div class="prefix-20 grid-30">' +
                            '<p> <input type="text" id="ingredients' + index + '.item" name="ingredients[' + index + '].item" />' +
                            '</p> </div>' +
                        '<div class="grid-30">' +
                            '<p> <input type="text" id="ingredients' + index + '.condition" name="ingredients[' + index + '].condition" />' +
                            '</p> </div>' +
                        '<div class="grid-10 suffix-10">' +
                             '<p> <input type="text" id="ingredients' + index + '.quantity" name="ingredients[' + index + '].quantity" />' +
                             '</p> </div>'
                    '</div>'

    $("#add-ingredient-row" ).before(newRow);
});