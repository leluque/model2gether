var ucDiagram;
var modified = false;
document.followUpdates = true;
document.beepChanges = true;

function init() {
    "use strict";

    startWebsocket(idDiagram);

    // Itens da tela para a criacao e exclusao de elementos
    var create_act = document.getElementById("create-act");
    var create_uc = document.getElementById("create-uc");
    var relation = document.getElementById("create-rel");
    var include = document.getElementById("create-inc");
    var extend = document.getElementById("create-ext");
    var generalization = document.getElementById("create-gen");
    var delete_elm = document.getElementById("delete-elm");
    var rename_elm = document.getElementById("rename-elm");

    // Insercao dos listeners para criacao e exclusao de elementos
    create_act.addEventListener("click", function (event) {
        event.preventDefault();
        if (!ucDiagram)
            return false;
        createElement("ACTOR");
    });
    create_uc.addEventListener("click", function (event) {
        event.preventDefault();
        if (!ucDiagram)
            return false;
        createElement("USE_CASE");
    });
    relation.addEventListener("click", function (event) {
        event.preventDefault();
        if (!ucDiagram)
            return false;
        createRelation("COMMUNICATION");
    });
    include.addEventListener("click", function (event) {
        event.preventDefault();
        if (!ucDiagram)
            return false;
        createRelation("INCLUSION");
    });
    extend.addEventListener("click", function (event) {
        event.preventDefault();
        if (!ucDiagram)
            return false;
        createRelation("EXTENSION");
    });
    generalization.addEventListener("click", function (event) {
        ;
        event.preventDefault();
        if (!ucDiagram)
            return false;
        createRelation("GENERALIZATION");
    });
    delete_elm.addEventListener("click", function (event) {
        event.preventDefault();
        if (!ucDiagram)
            return false;
        deleteElement();
    });
    rename_elm.addEventListener("click", function (event) {
        event.preventDefault();
        if (!ucDiagram)
            return false;
        renameElement();
    });
}

/**
 * vai construir o diagrama que veio do Banco
 */
function initDiagram() {
    diagramBase.name = diagramBase.name || "Use case diagram";
    var message = {
        msg: "initDiagram",
        diagram: diagramBase
    };
    sendText(JSON.stringify(message));
}

document.messagesHistory = [];

function updateMessages() {
    while (document.messagesHistory.length > 0) {
        processMessage(document.messagesHistory[0]);
        document.messagesHistory.splice(0, 1);
    }
}

/**
 * @param msg Mensagem recebida pelo servidor em websocket.js
 */
function processMessage(msg) {
    // Se ServerEndPoint enviar mensagem que nao for no formato JSON, nao executa nada
   // console.log("Parse: " + msg);
    var element;
    try {
        element = JSON.parse(msg);
    } catch (e) {
        console.log(e);
        return;
    }

    // Se o usuário quiser ouvir as atualizações.
    if (!document.followUpdates) {
        document.messagesHistory.push(msg);
    }
    function beep() {
        var snd = new Audio("data:audio/wav;base64,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qJrx4KOQjahZxWbcZgztj2c49nKmkId44S71j0c8eV9yDK6uPRzx5X18eDvjvQ6yKo9ZSS6l//8elePK/Lf//IInrOF/FvDoADYAGBMGb7FtErm5MXMlmPAJQVgWta7Zx2go+8xJ0UiCb8LHHdftWyLJE0QIAIsI+UbXu67dZMjmgDGCGl1H+vpF4NSDckSIkk7Vd+sxEhBQMRU8j/12UIRhzSaUdQ+rQU5kGeFxm+hb1oh6pWWmv3uvmReDl0UnvtapVaIzo1jZbf/pD6ElLqSX+rUmOQNpJFa/r+sa4e/pBlAABoAAAAA3CUgShLdGIxsY7AUABPRrgCABdDuQ5GC7DqPQCgbbJUAoRSUj+NIEig0YfyWUho1VBBBA//uQZB4ABZx5zfMakeAAAAmwAAAAF5F3P0w9GtAAACfAAAAAwLhMDmAYWMgVEG1U0FIGCBgXBXAtfMH10000EEEEEECUBYln03TTTdNBDZopopYvrTTdNa325mImNg3TTPV9q3pmY0xoO6bv3r00y+IDGid/9aaaZTGMuj9mpu9Mpio1dXrr5HERTZSmqU36A3CumzN/9Robv/Xx4v9ijkSRSNLQhAWumap82WRSBUqXStV/YcS+XVLnSS+WLDroqArFkMEsAS+eWmrUzrO0oEmE40RlMZ5+ODIkAyKAGUwZ3mVKmcamcJnMW26MRPgUw6j+LkhyHGVGYjSUUKNpuJUQoOIAyDvEyG8S5yfK6dhZc0Tx1KI/gviKL6qvvFs1+bWtaz58uUNnryq6kt5RzOCkPWlVqVX2a/EEBUdU1KrXLf40GoiiFXK///qpoiDXrOgqDR38JB0bw7SoL+ZB9o1RCkQjQ2CBYZKd/+VJxZRRZlqSkKiws0WFxUyCwsKiMy7hUVFhIaCrNQsKkTIsLivwKKigsj8XYlwt/WKi2N4d//uQRCSAAjURNIHpMZBGYiaQPSYyAAABLAAAAAAAACWAAAAApUF/Mg+0aohSIRobBAsMlO//Kk4soosy1JSFRYWaLC4qZBYWFRGZdwqKiwkNBVmoWFSJkWFxX4FFRQWR+LsS4W/rFRb/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////VEFHAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAU291bmRib3kuZGUAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAMjAwNGh0dHA6Ly93d3cuc291bmRib3kuZGUAAAAAAAAAACU=");
        snd.play();
    }

    // Avaliar msg para processar dados
    switch (element.msg) {
        case "openDiagram":
            openDiagram(element.diagram);
            break;
        case "createUMLActor":
            createElement("ACTOR", element.element);
            if (document.beepChanges) {
                beep();
            }
            break;
        case "createUMLUseCase":
            createElement("USE_CASE", element.element);
            if (document.beepChanges) {
                beep();
            }
            break;
        case "selectElement":
            selectElement(element.element);
            break;
        case "dropElement":
            dropElement(element.element);
            if (document.beepChanges) {
                beep();
            }
            break;
        case "deleteElement":
            deleteElement(element.element);
            if (document.beepChanges) {
                beep();
            }
            break;
        case "renameElement":
            renameElement(element.element);
            break;
        case "addUMLCommunication":
            createRelation("COMMUNICATION", element);
            if (document.beepChanges) {
                beep();
            }
            break;
        case "addUMLInclude":
            createRelation("INCLUSION", element);
            if (document.beepChanges) {
                beep();
            }
            break;
        case "addUMLExtend":
            createRelation("EXTENSION", element);
            if (document.beepChanges) {
                beep();
            }
            break;
        case "addUMLGeneralization":
            createRelation("GENERALIZATION", element);
            if (document.beepChanges) {
                beep();
            }
            break;
        case "renameDiagram":
            renameDiagram(element);
            break;
    }
}

function updateDiagram(diagram) {
    $.ajax({
        url: "ServletController?action=UpdateDiagram",
        type: "POST",
        data: {
            diagram: JSON.stringify(diagram)
        },
        dataType: "html",
        success: function (result) {
            if (result === "1") {
                console.log("Diagrama Salvo!");
                modified = false;
            }
        },
        error: function (err) {
            console.log("Error: " + err.status);
            console.log("Error Message: " + err.statusText);
        }
    });
}

init();
