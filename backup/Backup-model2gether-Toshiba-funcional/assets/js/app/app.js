var ucDiagram;
var modified = false;

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
    generalization.addEventListener("click", function (event) {;
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

/**
 * @param msg Mensagem recebida pelo servidor em websocket.js
 */
function processMessage(msg) {
    // Se ServerEndPoint enviar mensagem que nao for no formato JSON, nao executa nada
    var element;
    try {
        element = JSON.parse(msg);
    } catch (e) {
        console.log(e);
        return;
    }

    // Avaliar msg para processar dados
    switch (element.msg) {
        case "openDiagram":
            openDiagram(element.diagram);
            break;
        case "createUMLActor":
            createElement("ACTOR", element.element);
            break;
        case "createUMLUseCase":
            createElement("USE_CASE", element.element);
            break;
        case "selectElement":
            selectElement(element.element);
            break;
        case "dropElement":
            dropElement(element.element);
            break;
        case "deleteElement":
            deleteElement(element.element);
            break;
        case "renameElement":
            renameElement(element.element);
            break;
        case "addUMLCommunication":
            createRelation("COMMUNICATION", element);
            break;
        case "addUMLInclude":
            createRelation("INCLUSION", element);
            break;
        case "addUMLExtend":
            createRelation("EXTENSION", element);
            break;
        case "addUMLGeneralization":
            createRelation("GENERALIZATION", element);
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
