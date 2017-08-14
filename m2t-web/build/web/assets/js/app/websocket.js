//var websocket;
var address;
var counter = 0;

var storedId;

//Declare the XMLHttpRequest object to be re-used
var _request;
var pollTimer;

function startWebsocket(id) {
    storedId = id;
//    var pathname = document.location.pathname.substring(0, document.location.pathname.lastIndexOf("/"));
//    address = "http://" + document.location.host + pathname + "/SendMessage?id=" + id;
//    console.log("----- Enviando mensagem de abertura para o servidor " + address + "&open=");
//    $.get(address + "&open=")
//            .done(function () {
//                console.log('Mensagem enviada com sucesso e resposta recebida.');
//            });

    initDiagram();
    startReceiving();
}

function sendText(json) {
    var pathname = document.location.pathname.substring(0, document.location.pathname.lastIndexOf("/"));
    var servletGate = "http://" + document.location.host + pathname + "/SendMessage";
    $.ajax(servletGate, {
        type: "POST",
        data: {
            id: storedId,
            msg: json
        },
        success: function (data) {
            console.log("----- Mensagem enviada: " + json);
        },
        error: function () {
            console.log("##### Erro ao enviar mensagem: " + json)
        }
    });
}

function onmessage(data) {
    console.log("----- Mensagem recebida: " + data);
    processMessage(data);
}

window.onbeforeunload = function () {
//    var pathname = document.location.pathname.substring(0, document.location.pathname.lastIndexOf("/"));
//    address = "http://" + document.location.host + pathname + "/Server?id=" + storedId;
//    $.get(address + "&close=")
//            .done(function (data) {
//                console.log('----- Fechei a conexão de escuta com o servidor!');
//            });
    if (_request !== null && typeof (_request) !== "undefined") {
        _request.abort();
    }
};

function startReceiving() {
    clearInterval(pollTimer);
    // Prevents an issue in which previous requests get stuck and new ones then fail.
    if (_request !== null && typeof (_request) !== "undefined") {
        _request.abort();
    }
    // Create a new XML Http Request (cross-browser).
    _request = getXHR();

    var pathname = document.location.pathname.substring(0, document.location.pathname.lastIndexOf("/"));
    var serverAddress = "http://" + document.location.host + pathname + "/Server?id=" + storedId;
    // Start a new async request.
    _request.open("GET", serverAddress, true);
    _request.onreadystatechange = updateProgress;
    _request.progress = updateProgress;
    _request.load = transferComplete;
    _request.setRequestHeader("Content-type", "application/json; charset=utf-8");
    _request.timeout = 20000;
    _request.ontimeout = function () {
        console.log("----- Request timed out. Establishing a new one.");
        startReceiving();
    };
    _request.send();
    pollTimer = setInterval(updateProgress, 400);

    var prevDataLength = 0;
    function updateProgress() {
        if (_request.readyState !== null && (_request.readyState < 3 || _request.status !== 200)) {
            return;
        } else if (_request.readyState === XMLHttpRequest.DONE) {
            startReceiving();
        } else if (_request.responseText === null) {
            return;
        }
        // Process incremental data found in get.responseText
        if (prevDataLength !== _request.responseText.length) {
            var recentData = _request.responseText.substr(prevDataLength);
            prevDataLength = _request.responseText.length;
            onmessage(recentData);
        }
    }

    function transferComplete() {
        console.log("##### A transferência completou!");
        clearInterval(pollTimer);
        _request.abort();
        startReceiving();
    }

    function transferAbort(error) {
        console.log(_request.status);
        clearInterval(pollTimer);
        console.log("##### A transferência falhou: " + _request.statusText);
//        _request.abort();
//        startReceiving();
    }

}

/** 
 * Gets an XMLHttpRequest. For Internet Explorer 6, attempts to use MSXML 6.0,
 * then falls back to MXSML 3.0.
 * Returns null if the object could not be created. 
 * @return {XMLHttpRequest or equivalent ActiveXObject} 
 */
function getXHR() {
    if (window.XMLHttpRequest) {
        // Chrome, Firefox, IE7+, Opera, Safari
        return new XMLHttpRequest();
    }
    // IE6.
    try {
        // The latest stable version. It has the best security, performance, 
        // reliability, and W3C conformance. Ships with Vista, and available 
        // with other OS's via downloads and updates. 
        return new ActiveXObject('MSXML2.XMLHTTP.6.0');
    } catch (e) {
        try {
            // The fallback.
            return new ActiveXObject('MSXML2.XMLHTTP.3.0');
        } catch (e) {
            alert('This browser is not AJAX enabled.');
            return null;
        }
    }
}