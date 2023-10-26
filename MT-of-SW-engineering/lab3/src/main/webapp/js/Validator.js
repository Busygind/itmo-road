let validationInfo = '';

function checkX(x) {
    let xIsValid = false;
    if (x === null || x === undefined || x < -4 || x > 4) {
        //validationInfo += "<span>X is undefined!</span>";
    } else {
        xIsValid = true;
    }
    return xIsValid;
}

function checkY(y) {
    let yIsValid = false;
    if (y.toString().trim() !== "") {
        if (/^(-?\d*\.\d+|-?\d*|-?\d*(\.\d+)?e[-\+]?\d+)$/.test(y.toString().trim())) {
            if ((parseInt(y) >= -5) && (parseInt(y) <= 3)) {
                yIsValid = true;
            } //else validationInfo += "<span>Y must be in interval (-3..5)!</span>";
        } //else validationInfo += "<span>Y must be a number!</span>";
    } //else validationInfo += "<span>Y is undefined!</span>";
    return yIsValid;
}

function checkR(r) {
    let rIsValid = false;
    if (r === '' || r === undefined) {
        //validationInfo += "<span>R is undefined!</span>";
    } else if (r <= 3 && r >= 1) {
        rIsValid = true;
    }
    return rIsValid;
}

function validateVariables(x, y, r) {
    const validationInfoPanel = document.querySelector('.validationInfo');
    validationInfoPanel.classList.remove("show");
    validationInfo = ''

    let xIsCorrect = checkX(x);
    let yIsCorrect = checkY(y);
    let rIsCorrect = checkR(r);

    let dataIsValid = xIsCorrect && yIsCorrect && rIsCorrect;
    if (!dataIsValid) {
        $(".validationInfo").html(validationInfo);
        validationInfoPanel.classList.add("show");
    }
    return dataIsValid;
}