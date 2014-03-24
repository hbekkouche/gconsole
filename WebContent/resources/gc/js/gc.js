var websocket;
var wsUrl;
var appPath = window.location.pathname.split('/')[1];
var host = window.location.hostname;
var port = window.location.port;

if (window.location.protocol === 'https:') {
	port = '8443';
    wsUrl = 'wss://' + host + ':' + port + '/' + appPath + '/gcEndPoint';
} else {
	port = '8000';
    wsUrl = 'ws://' + host + ':' + port + '/' + appPath + '/gcEndPoint';
}

initWebSocket();
function initWebSocket() {
    if (!window.WebSocket) {
        window.location = '/appPath/faces/index.xhtml';
    } else {
        websocket = new WebSocket(wsUrl);
        websocket.onopen = function(evt) {
            onOpen(evt);
        };
        websocket.onclose = function(evt) {
            onClose(evt);
        };
        websocket.onmessage = function(evt) {
            onMessage(evt);
        };
        websocket.onerror = function(evt) {
            onError(evt);
        };
    }
}

function onOpen(evt) {
    console.log("CONNECTED");
}
function onClose(evt) {
    console.log("DISCONNECTED");
}
function onMessage(evt) {
    console.log("RECEIVED: " + evt.data);
    writeToResult(evt.data);
    completeRun();
}

function onError(evt) {
    console.log(evt.data);
}
function doSend() {
    startRun();
    message = editor.getValue();
    console.log("SEND: " + editor.getValue());
    websocket.send(message);
}

function writeToResult(message) {

//var obj = jQuery.parseJSON(message.data);
    var obj = jQuery.parseJSON(message);
    document.getElementById("result").innerHTML = obj.Console.result;
    document.getElementById("output").innerHTML = obj.Console.output;
    document.getElementById("stackTrace").innerHTML = obj.Console.stackTrace;
    if (obj.Console.stackTrace === undefined || obj.Console.stackTrace.length === 0) {
        $('li').removeClass('active');
        $('#p2').removeClass('active');
        $('#hp1').addClass('active');
        $('#p1').addClass('active');
    } else {
        $('li').removeClass('active');
        $('#p1').removeClass('active');
        $('#hp2').addClass('active');
        $('#p2').addClass('active');
    }

}

function startRun() {
    $("#btnrun").addClass("ui-state-disabled");
}
function completeRun() {
    $("#btnrun").removeClass("ui-state-disabled");
}