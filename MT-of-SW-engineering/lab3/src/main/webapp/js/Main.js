let board;
let points = [];
let serverPoints = [];
let r = document.getElementsByClassName("ui-spinner-input");

$(document).ready(function () {

});

function clickSubmit(x, y) {
    if (checkX(x) && checkY(y) && checkR(r[0].getAttribute("aria-valuenow"))) {
        let xInput = $('.x-value'), yInput = $('.y-value')
        console.log("click submit: " + x + " " + y)
        let prevX = xInput.val(), prevY = yInput.val();
        xInput.val(x.toFixed(2));
        yInput.val(y.toFixed(2));

        document.getElementsByClassName("submit-button")[0].click()
        xInput.val(prevX);
        yInput.val(prevY);
    }
}

function mouseClickListener(event) {
    let coords = globalBoard.getUsrCoordsOfMouse(event);
    if (checkR(r[0].getAttribute("aria-valuenow"))) {
        //createPoint(globalBoard, coords[0].toString(), coords[1].toString(), true)
        clickSubmit(coords[0], coords[1])
    }
}
