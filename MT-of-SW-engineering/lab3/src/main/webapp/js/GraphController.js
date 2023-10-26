var globalBoard;

function createRectangle(board, r) {
    let pt1 = board.create('point', [0, 0], {name: '', fixed: true, size: 0});
    let pt2 = board.create('point', [-r, 0], {name: '', fixed: true, size: 0});
    let pt3 = board.create('point', [-r, r], {name: '', fixed: true, size: 0});
    let pt4 = board.create('point', [0, r], {name: '', fixed: true, size: 0});
    return board.create('polygon', [pt1, pt2, pt3, pt4],
        {fillColor: '#fabf4d', fillOpacity: 1, borders: {strokeColor: 'black'}});
}

function createTriangle(board, r) {
    let pt1 = board.create('point', [0, 0], {name: '', fixed: true, size: 0});
    let pt2 = board.create('point', [-r, 0], {name: '', fixed: true, size: 0});
    let pt3 = board.create('point', [0, -r], {name: '', fixed: true, size: 0});
    return board.create('polygon', [pt1, pt2, pt3], {
        fillColor: '#fabf4d',
        fillOpacity: 1,
        borders: {strokeColor: 'black'}
    });
}

function createCircle(board, r) {
    let cen = board.create('point', [0, 0], {name: '', fixed: true, size: 0});
    let pt1 = board.create('point', [0, -r / 2], {name: '', fixed: true, size: 0});
    let pt2 = board.create('point', [r / 2, 0], {name: '', fixed: true, size: 0});
    return board.create('sector', [cen, pt1, pt2], {
        name: '',
        fillColor: '#fabf4d',
        fillOpacity: 1,
        strokeColor: 'black'
    });
}

$(document).ready(function () {
    drawBoard()
});

function drawObjects(drawnObjects, board, r) {
    for (const object of drawnObjects) {
        let points = object.ancestors;
        for (const point in points) board.removeObject(point);
        board.removeObject(object);
    }
    let rectangle = createRectangle(board, r);
    let triangle = createTriangle(board, r);
    let circle = createCircle(board, r);
    return [rectangle, triangle, circle];
}

function drawBoard() {

    let board = JXG.JSXGraph.initBoard('jxgbox', {boundingbox: [-6, 6, 6, -6], axis: true, showCopyright: false});
    globalBoard = board;
    let drawnObjects = []
    var els = document.getElementsByClassName("ui-spinner-input");
    console.log(els[0].getAttribute("aria-valuenow"))
    drawnObjects = drawObjects(drawnObjects, board, 1);

    document.getElementById('jxgbox').addEventListener("click", mouseClickListener);

    $('.ui-spinner-input').change(function () {
        console.log(els[0].getAttribute("aria-valuenow"))
        if (checkR(els[0].getAttribute("aria-valuenow"))) {
            drawnObjects = drawObjects(drawnObjects, board, els[0].getAttribute("aria-valuenow"));
            console.log(points);
            console.log("r change")
        }
    });


}

function drawPointsByR(r) {
    console.log("draw points by r")
    for (let i = 0; i < serverPoints.length; i++) {
        let serverPoint = serverPoints[i];
        if (parseFloat(r) === serverPoint.r) {
            let point = createPoint(globalBoard, serverPoint.x, serverPoint.y, serverPoint.success);
            points.push(point);
            console.log(points);
            console.log("dots by r drew");
        }
    }
}

function createPoint(board, x, y, success) {
    if (success) {
        return board.create("point", [x, y], {name: '', fixed: true, size: 1, fillColor: '#179a02', strokeColor: '#179a02'});
    } else {
        return board.create("point", [x, y], {name: '', fixed: true, size: 1, fillColor: '#bb1010', strokeColor: '#bb1010'});
    }
}

function clearPoints() {
    console.log("clear dots")
    console.log(points);
    for (const point of points) {
        globalBoard.removeObject(point);
    }
    points = []
}