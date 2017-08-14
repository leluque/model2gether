var websocket;

function startWebsocket(id) {
    var pathname = document.location.pathname.substring(0, document.location.pathname.lastIndexOf("/"));
    //var wsUri = "ws://" + document.location.host + pathname + "/umldiagram_endpoint/";
    var wsUri = "ws://model2gether.com:6223/umldiagram_endpoint/";
    websocket = new WebSocket(wsUri + id);
    websocket.onmessage = onMessage;
    websocket.onopen = function() {
        initDiagram();
    };
}

function sendText(json) {
    console.log("sending text: " + json);
    websocket.send(json);
}

function onMessage(evt) {
    console.log("received: " + evt.data);
    processMessage(evt.data);
}