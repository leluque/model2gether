var initialText = "  Actors\n    \n  Use Cases\n    \n  Relationships\n    ";
var maskBetweenItems = ",\n    ";
var validDiagram = true;

/**
 * @param elementType Obrigatorio - Tipo de elemento a ser criado ("USE_CASE" ou "ACTOR")
 * @param elementBase Opcional - Elemento Base. Se nao estiver definido, abre modal para inserir o nome do elemento a ser criado
 */
function createElement(elementType, elementBase) {
    if (!elementType)
        return false;

    var element, createdElement;

    if (elementBase) {   // Se recebeu elemento base, adicionar elemento a partir do mesmo
        element = new Element();
        element.init(elementBase.id, elementBase.name, elementType, elementBase.x, elementBase.y);
        if (elementType === "ACTOR")
            ucDiagram.actors.push(element);
        else if (elementType === "USE_CASE")
            ucDiagram.useCases.push(element);

        // deslocamento a ser adicionado na posicao dos elementos
        // quando adicionado um elemento acima
        var offset = 0;
        // texto padrao a ser inserido na criacao de um elemento
        // quando ja houver um elemento do mesmo tipo na lista
        var previousElement = "";
        // quando houver algum elemento na mesma lista, irá tirar o ponto (fim da lista) do ultimo elemento
        // para no lugar dele colocar a virgula (separador de elementos)
        var takeOut = 0;
        if (elementType === "ACTOR") {
            createdElement = "Actor";
            if (ucDiagram.actors.length > 1) {
                previousElement = maskBetweenItems;
                takeOut = 1;
            }
            element.start = ucDiagram.lastActor.start = ucDiagram.lastActor.end + previousElement.length;
            element.end = ucDiagram.lastActor.end = element.start + element.name.length + 2;
            offset = element.end - element.start + 1 + previousElement.length - takeOut;
//            ucDiagram.lastUseCase.start += offset;
//            ucDiagram.lastUseCase.end += offset;
//            ucDiagram.lastRelation.start += offset;
//            ucDiagram.lastRelation.end += offset;
        } else if (elementType === "USE_CASE") {
            createdElement = "Use Case";
            if (ucDiagram.useCases.length > 1) {
                previousElement = maskBetweenItems;
                takeOut = 1;
            }
            element.start = ucDiagram.lastUseCase.start = ucDiagram.lastUseCase.end + previousElement.length;
            element.end = ucDiagram.lastUseCase.end = element.start + element.name.length + 2;
            offset = element.end - element.start + 1 + previousElement.length - takeOut;
//            ucDiagram.lastRelation.start += offset;
//            ucDiagram.lastRelation.end += offset;
        }
        updateIndexElementsBelow(element, offset); // TESTAR!!!
        ucDiagram.area.value = spliceString(ucDiagram.area.value, previousElement + "\"" + element.name + "\".", element.start - previousElement.length, takeOut);
        writeMessage("The element was created: " + createdElement, 1, element.start, element.end);
    } else {        // Se nao recebeu elemento base, vai abrir o modal para criar
        var modalInputTitle = document.getElementById("title-criar-elemento");
        if (elementType === "ACTOR") {
            createdElement = "Type in the actor name";
            modalInputTitle.innerHTML = "Create new actor";
        }
        else if (elementType === "USE_CASE") {
            createdElement = "Type in the use case name";
            modalInputTitle.innerHTML = "Create new use case";
        }
        var inputElement = document.getElementById("elementName");
        var modalInput = document.getElementById("modal-novo-elemento");
        inputElement.setAttribute("placeholder", createdElement);
        inputElement.setAttribute("data-element-type", elementType);
        inputElement.value = "";
        $(modalInput).modal().on("shown.bs.modal", function () {
            inputElement.focus();
        });
    }
}

/**
 * @param relationType Obrigatorio - Tipo de relacao a ser criada ("COMMUNICATION", "EXTENSION", "INCLUSION" ou "GENERALIZATION")
 * @param relationBase Opcional - Relacao Base. Se nao estiver definida, abre modal para inserir a relacao a ser criada
 */
function createRelation(relationType, relationBase) {
    if (!relationType)
        return false;

    var relation;

    if (relationBase) { // Se origem e destino estiverem definidas, adicionar relacao a partir dos mesmos
        relation = new Relation();
        relation.init(relationBase.id, relationType);
        relation.source = getElement(relationBase.source.type, relationBase.source.id);
        relation.target = getElement(relationBase.target.type, relationBase.target.id);
        ucDiagram.relations.push(relation);

        var relationText = "\"" + relation.source.name + "\" " + relation.typeString + " \"" + relation.target.name + "\"";
        // texto padrao a ser inserido na criacao de um elemento
        // quando ja houver um elemento do mesmo tipo na lista
        var previousElement = "";
        // quando houver algum elemento na mesma lista, irá tirar o ponto (fim da lista) do ultimo elemento
        // para no lugar dele colocar a virgula (separador de elementos)
        var takeOut = 0;
        if (ucDiagram.relations.length > 1) {
            previousElement = maskBetweenItems;
            takeOut = 1;
        }
        var i = ucDiagram.relations.length - 1;
        ucDiagram.lastRelation.start = ucDiagram.relations[i].start = ucDiagram.area.value.length + previousElement.length - takeOut;
        ucDiagram.lastRelation.end = ucDiagram.relations[i].end = ucDiagram.lastRelation.start + relationText.length;
        var insertString = previousElement + relationText + ".";
        var indexToInsert = ucDiagram.lastRelation.start - previousElement.length;
        ucDiagram.area.value = spliceString(ucDiagram.area.value, insertString, indexToInsert, takeOut);
        writeMessage("The relationship was created", 1, ucDiagram.lastRelation.start, ucDiagram.lastRelation.end);
    } else {
        var selectSource = document.getElementById("elementSource");
        var selectTarget = document.getElementById("elementTarget");
        document.getElementById("titleModalRelation").innerHTML = getNameRelation(relationType);
        var i;
        selectSource.innerHTML = "";
        selectTarget.innerHTML = "";
        for (i = 0; i < ucDiagram.actors.length; i++) {
            var option = document.createElement("option");
            option.setAttribute("value", "{\"id\":" + ucDiagram.actors[i].id + ",\"type\":\"" + ucDiagram.actors[i].type + "\"}");
            option.innerHTML = ucDiagram.actors[i].name;
            selectSource.appendChild(option);
            selectTarget.appendChild(option.cloneNode(true));
        }
        for (i = 0; i < ucDiagram.useCases.length; i++) {
            var option = document.createElement("option");
            option.setAttribute("value", "{\"id\":" + ucDiagram.useCases[i].id + ",\"type\":\"" + ucDiagram.useCases[i].type + "\"}");
            option.innerHTML = ucDiagram.useCases[i].name;
            selectSource.appendChild(option);
            selectTarget.appendChild(option.cloneNode(true));
        }
        var modalInput = document.getElementById("modal-nova-relacao");
        modalInput.setAttribute("data-relation-type", relationType);
        $(modalInput).modal().on("shown.bs.modal", function () {
            selectSource.focus();
        });
    }

    function getNameRelation(type) {
        switch (type) {
            case "COMMUNICATION":
                return "Association";
            case "INCLUSION":
                return "Include";
            case "EXTENSION":
                return "Extend";
            case "GENERALIZATION":
                return "Inheritance";
        }
    }
}

/**
 * @param element Elemento selecionado
 */
function selectElement(element) {
    element = getElement(element.type, element.id);
    writeMessage("The element was selected", 1, element.start, element.end);
}

/**
 * @param element Elemento solto (Metodo vazio para nao dar erro, mas nao faz sentido no diagrama textual...)
 */
function dropElement(element) {
    return false;
    modified = true;
}

/**
 * @param elementBase Elemento Base com os dados para buscar e renomear elemento
 */
function renameElement(elementBase) {

    if (elementBase) {
        var element = getElement(elementBase.type, elementBase.id);
        var difference = elementBase.name.length - element.name.length;
        var oldNameSize = element.name.length + 2;
        element.name = elementBase.name;
        element.end += difference;
        updateIndexElementsBelow(element, difference);
        ucDiagram.area.value = spliceString(ucDiagram.area.value, "\"" + element.name + "\"", element.start, oldNameSize);
        var relationsToUpdate = findRelations(element);
        for (var i in relationsToUpdate) {
            relationsToUpdate[i].end += difference;
            updateIndexElementsBelow(relationsToUpdate[i], difference);
            var relationText = "\"" + relationsToUpdate[i].source.name + "\" " + relationsToUpdate[i].typeString + " \"" + relationsToUpdate[i].target.name + "\"";
            ucDiagram.area.value = spliceString(ucDiagram.area.value, relationText, relationsToUpdate[i].start, relationsToUpdate[i].end - relationsToUpdate[i].start - difference);
        }
        writeMessage("The element was renamed", 1, element.start, element.end);
        modified = true;
    } else {
        document.getElementById("elementNewName").value = "";
        var selectElementRename = document.getElementById("elementToRename");
        var i;
        selectElementRename.innerHTML = "";
        var optionActors = document.createElement("option");
        optionActors.setAttribute("disabled", true);
        optionActors.innerHTML = "--Actors--";
        selectElementRename.appendChild(optionActors);
        for (i = 0; i < ucDiagram.actors.length; i++) {
            var option = document.createElement("option");
            option.setAttribute("value", "{\"id\":" + ucDiagram.actors[i].id + ",\"type\":\"" + ucDiagram.actors[i].type + "\"}");
            option.innerHTML = ucDiagram.actors[i].name;
            selectElementRename.appendChild(option);
        }
        var optionUseCases = document.createElement("option");
        optionUseCases.setAttribute("disabled", true);
        optionUseCases.innerHTML = "--Use Cases--";
        selectElementRename.appendChild(optionUseCases);
        for (i = 0; i < ucDiagram.useCases.length; i++) {
            var option = document.createElement("option");
            option.setAttribute("value", "{\"id\":" + ucDiagram.useCases[i].id + ",\"type\":\"" + ucDiagram.useCases[i].type + "\"}");
            option.innerHTML = ucDiagram.useCases[i].name;
            selectElementRename.appendChild(option);
        }
        $("#modal-renomear-elemento").modal().on("shown.bs.modal", function () {
            selectElementRename.focus();
        });
    }
}

/**
 * @param elementBase Opcional - Elemento base. Se nao for definido, ...
 */
function deleteElement(elementBase) {

    if (elementBase) {
        var element = getElement(elementBase.type, elementBase.id);
        var elementText = findText(element);
        var offset = (element.end - element.start) + maskBetweenItems.length;
        var index = 0;
        if (element === ucDiagram.actors[0] || element === ucDiagram.useCases[0] || element === ucDiagram.relations[0]) {
            index = element.start - maskBetweenItems.length + 1;
            if ((element === ucDiagram.actors[0] && ucDiagram.actors.length === 1) ||
                    (element === ucDiagram.useCases[0] && ucDiagram.useCases.length === 1) ||
                    (element === ucDiagram.relations[0] && ucDiagram.relations.length === 1)) {
                index += maskBetweenItems.length - 1;
                offset -= maskBetweenItems.length - 1;
                element.end = element.start;
            }
        } else {
            index = element.start - maskBetweenItems.length;
        }
        updateIndexElementsBelow(element, -offset);
        ucDiagram.area.value = spliceString(ucDiagram.area.value, "", index, offset);
        del(element);
        writeMessage("The element was deleted: " + elementText, 1);
    } else {
        var selectElementExclude = document.getElementById("elementToExclude");
        var i;
        selectElementExclude.innerHTML = "";
        var optionActors = document.createElement("option");
        optionActors.setAttribute("disabled", true);
        optionActors.innerHTML = "--Actors--";
        selectElementExclude.appendChild(optionActors);
        for (i = 0; i < ucDiagram.actors.length; i++) {
            var option = document.createElement("option");
            option.setAttribute("value", "{\"id\":" + ucDiagram.actors[i].id + ",\"type\":\"" + ucDiagram.actors[i].type + "\"}");
            option.innerHTML = ucDiagram.actors[i].name;
            selectElementExclude.appendChild(option);
        }
        var optionUseCases = document.createElement("option");
        optionUseCases.setAttribute("disabled", true);
        optionUseCases.innerHTML = "--Use Cases--";
        selectElementExclude.appendChild(optionUseCases);
        for (i = 0; i < ucDiagram.useCases.length; i++) {
            var option = document.createElement("option");
            option.setAttribute("value", "{\"id\":" + ucDiagram.useCases[i].id + ",\"type\":\"" + ucDiagram.useCases[i].type + "\"}");
            option.innerHTML = ucDiagram.useCases[i].name;
            selectElementExclude.appendChild(option);
        }
        var optionRelations = document.createElement("option");
        optionRelations.setAttribute("disabled", true);
        optionRelations.innerHTML = "--Relationships--";
        selectElementExclude.appendChild(optionRelations);
        for (i = 0; i < ucDiagram.relations.length; i++) {
            var option = document.createElement("option");
            option.setAttribute("value", "{\"id\":" + ucDiagram.relations[i].id + ",\"type\":\"" + ucDiagram.relations[i].type + "\"}");
            option.innerHTML = findText(ucDiagram.relations[i]);
            selectElementExclude.appendChild(option);
        }
        $("#modal-excluir-elemento").modal().on("shown.bs.modal", function () {
            selectElementExclude.focus();
        });
    }

    function del(element) {
        var i, j;
        if (element.type === "ACTOR") {
            for (i in ucDiagram.actors)
                if (ucDiagram.actors[i].id === element.id) {
                    var deleteRelations = findRelations(element);
                    for (j in deleteRelations)
                        deleteElement(deleteRelations[j]);
                    ucDiagram.actors.splice(i, 1);
                    return;
                }
        } else if (element.type === "USE_CASE") {
            for (i in ucDiagram.useCases)
                if (ucDiagram.useCases[i].id === element.id) {
                    var deleteRelations = findRelations(element);
                    for (j in deleteRelations)
                        deleteElement(deleteRelations[j]);
                    ucDiagram.useCases.splice(i, 1);
                    return;
                }
        } else if (element.type === "COMMUNICATION" || element.type === "INCLUSION" ||
                element.type === "EXTENSION" || element.type === "GENERALIZATION") {
            for (i in ucDiagram.relations)
                if (ucDiagram.relations[i].id === element.id) {
                    ucDiagram.relations.splice(i, 1);
                    return;
                }
        }
    }
    function findText(element) {
        if (element.type === "ACTOR" || element.type === "USE_CASE") {
            return element.name;
        } else if (element.type === "COMMUNICATION" || element.type === "INCLUSION" ||
                element.type === "EXTENSION" || element.type === "GENERALIZATION") {
            return element.source.name + " " + element.typeString + " " + element.target.name;
        }
    }
}

/**
 * @param diagram Diagrama a ser aberto - recebido pelo Websocket
 */
function openDiagram(diagram) {
    ucDiagram = {
        area: {},
        messages: {},
        id: diagram.id || idDiagram,
        name: diagram.name || "Use case diagram",
        actors: [],
        useCases: [],
        relations: [],
        lastActor: {start: 0, end: 0},
        lastUseCase: {start: 0, end: 0},
        lastRelation: {start: 0, end: 0}
    };
    ucDiagram.area = document.getElementById("textDiagram");
    ucDiagram.area.value = "Use Case Diagram \"" + ucDiagram.name + "\"\n" + initialText;
    // 25 caracteres (contando espacos e quebras de linha) mais o tamanho do nome do diagrama
    // eh a posicao inicial para inserir o ator no texto
    var pos = ucDiagram.name.length + 33;///25;
    ucDiagram.lastActor.start = ucDiagram.lastActor.end = pos;
    // 20 caracteres (contando espacos e quebras de linha) mais posicao calculada anteriormente
    // eh a posicao inicial para inserir o caso de uso no texto
    ucDiagram.lastUseCase.start = ucDiagram.lastUseCase.end = pos + 17; //20;
    // A relacao sera inserida no fim do texto
    ucDiagram.lastRelation.start = ucDiagram.lastRelation.end = ucDiagram.area.value.length;
    ucDiagram.messages = document.getElementById("msgRecebida");
    writeMessage("The diagram was created", 2);

    for (var i in diagram.elements) {
        var element = new Element();
        element.init(diagram.elements[i].id, diagram.elements[i].name, diagram.elements[i].type, diagram.elements[i].x, diagram.elements[i].y);
        createElement(diagram.elements[i].type, element);
    }
    for (i in diagram.relations) {
        createRelation(diagram.relations[i].type, diagram.relations[i]);
    }
}

/**
 * @param diagram Novo nome do diagrama
 */
function renameDiagram(diagram) {
    var difference = diagram.newName.length - ucDiagram.name.length;
    var oldNameSize = ucDiagram.name.length + 2;
    ucDiagram.name = diagram.newName;
    updateIndexElementsBelow(null, difference);
    //ucDiagram.area.value = spliceString(ucDiagram.area.value, "\"" + ucDiagram.name + "\"", 9, oldNameSize);
    ucDiagram.area.value = spliceString(ucDiagram.area.value, "\"" + ucDiagram.name + "\"", 17, oldNameSize);
    modified = true;
}

/**
 * @param type Obrigatorio - Tipo do elemento buscado ("ACTOR" ou "USE_CASE")
 * @param id Obrigatorio - ID do elemento buscado
 */
function getElement(type, id) {
    var i;
    if (type === "ACTOR") {
        for (i in ucDiagram.actors)
            if (ucDiagram.actors[i].id === id)
                return ucDiagram.actors[i];
    } else if (type === "USE_CASE") {
        for (i in ucDiagram.useCases)
            if (ucDiagram.useCases[i].id === id)
                return ucDiagram.useCases[i];
    } else if (type === "COMMUNICATION" || type === "INCLUSION" ||
            type === "EXTENSION" || type === "GENERALIZATION") {
        for (i in ucDiagram.relations)
            if (ucDiagram.relations[i].id === id)
                return ucDiagram.relations[i];
    }
}

/**
 * @param element Elemento para buscar as relacaoes
 * @returns Relacoes que contem o elemento passado por parametro
 */
function findRelations(element) {
    var relations = [], i;
    for (i in ucDiagram.relations) {
        if (ucDiagram.relations[i].source === element || ucDiagram.relations[i].target === element)
            relations.push(ucDiagram.relations[i]);
    }
    return relations;
}

/**
 * Atualiza os indices (inicio e fim) dos elementos abaixo no diagrama textual
 * @param element
 * @param updateValue
 */
function updateIndexElementsBelow(element, updateValue) {
    var i, j;
    if (element && element.type === "ACTOR") {
        ucDiagram.actors.sort(compare);
        for (i = 0; i < ucDiagram.actors.length; i++) {
            if (ucDiagram.actors[i].id === element.id) {
                for (j = i + 1; j < ucDiagram.actors.length; j++) {
                    ucDiagram.actors[j].start += updateValue;
                    ucDiagram.actors[j].end += updateValue;
                }
                if (i === ucDiagram.actors.length - 1) {
                    ucDiagram.lastActor.start = element.start;
                    ucDiagram.lastActor.end = element.end;
                } else {
                    ucDiagram.lastActor.start += updateValue;
                    ucDiagram.lastActor.end += updateValue;
                }
                break;
            }
        }
        for (i in ucDiagram.useCases) {
            ucDiagram.useCases[i].start += updateValue;
            ucDiagram.useCases[i].end += updateValue;
        }
        for (i in ucDiagram.relations) {
            ucDiagram.relations[i].start += updateValue;
            ucDiagram.relations[i].end += updateValue;
        }
        ucDiagram.lastUseCase.start += updateValue;
        ucDiagram.lastUseCase.end += updateValue;
        ucDiagram.lastRelation.start += updateValue;
        ucDiagram.lastRelation.end += updateValue;
    } else if (element && element.type === "USE_CASE") {
        ucDiagram.useCases.sort(compare);
        for (i = 0; i < ucDiagram.useCases.length; i++) {
            if (ucDiagram.useCases[i].id === element.id) {
                for (j = i + 1; j < ucDiagram.useCases.length; j++) {
                    ucDiagram.useCases[j].start += updateValue;
                    ucDiagram.useCases[j].end += updateValue;
                }
                if (i === ucDiagram.useCases.length - 1) {
                    ucDiagram.lastUseCase.start = element.start;
                    ucDiagram.lastUseCase.end = element.end;
                } else {
                    ucDiagram.lastUseCase.start += updateValue;
                    ucDiagram.lastUseCase.end += updateValue;
                }
                break;
            }
        }
        for (i in ucDiagram.relations) {
            ucDiagram.relations[i].start += updateValue;
            ucDiagram.relations[i].end += updateValue;
        }
        ucDiagram.lastRelation.start += updateValue;
        ucDiagram.lastRelation.end += updateValue;
    } else if (element && (element.type === "COMMUNICATION" || element.type === "INCLUSION" ||
            element.type === "EXTENSION" || element.type === "GENERALIZATION")) {
        ucDiagram.relations.sort(compare);
        for (i = 0; i < ucDiagram.relations.length; i++) {
            if (ucDiagram.relations[i].id === element.id) {
                for (j = i + 1; j < ucDiagram.relations.length; j++) {
                    ucDiagram.relations[j].start += updateValue;
                    ucDiagram.relations[j].end += updateValue;
                }
                if (i === ucDiagram.relations.length - 1) {
                    ucDiagram.lastRelation.start = element.start;
                    ucDiagram.lastRelation.end = element.end;
                } else {
                    ucDiagram.lastRelation.start += updateValue;
                    ucDiagram.lastRelation.end += updateValue;
                }
                break;
            }
        }
    } else if (!element) {
        for (i = 0; i < ucDiagram.actors.length; i++) {
            ucDiagram.actors[i].start += updateValue;
            ucDiagram.actors[i].end += updateValue;
        }
        for (i in ucDiagram.useCases) {
            ucDiagram.useCases[i].start += updateValue;
            ucDiagram.useCases[i].end += updateValue;
        }
        for (i in ucDiagram.relations) {
            ucDiagram.relations[i].start += updateValue;
            ucDiagram.relations[i].end += updateValue;
        }
        ucDiagram.lastActor.start += updateValue;
        ucDiagram.lastActor.end += updateValue;
        ucDiagram.lastUseCase.start += updateValue;
        ucDiagram.lastUseCase.end += updateValue;
        ucDiagram.lastRelation.start += updateValue;
        ucDiagram.lastRelation.end += updateValue;
    }
}

/**
 * Funcao de comparacao para ordenar os elementos do diagrama pela sua posicao inicial
 * @param a
 * @param b
 */
function compare(a, b) {
    return a.start - b.start;
}

/**
 * @param str String inicial
 * @param add String a ser inserida
 * @param index Posicao pra inserir a nova String
 * @param count Quantidade de caracteres a remover da String inicial. Se omitido, nao remove nenhum caractere
 * @return Nova String
 */
function spliceString(str, add, index, count) {
    if (!count)
        count = 0;
    var ar = str.split('');
    ar.splice(index, count, add);
    return ar.join('');
}

/**
 * Escrever a mensagem recebida no input para ser lida pelo leitor de tela. Apos o tempo de duracao, retorna a selecao para a posicao no diagrama passada
 * @param message Mensagem a ser escrita no input
 * @param time Duracao em segundos que o input vai ficar focado
 * @param selectionStart Inicio da selecaoao retornar para o diagrama
 * @param selectionEnd Fim da selecao ao retornar para o diagrama
 */
function writeMessage(message, time, selectionStart, selectionEnd) {
    ucDiagram.messages.value = message;
    ucDiagram.messages.focus();
    setTimeout(function () {
        // Se qualquer um dos dois nao for definido, posiciona o cursor no inicio de textArea
        if (!selectionStart || !selectionEnd)
            selectionStart = selectionEnd = 0;
        selectInterval(ucDiagram.area, selectionStart, selectionEnd);
    }, time * 1000);
}

/**
 * Setar selecao em um input
 * @param input Elemento para a selecao.
 * @param selectionStart Posicao do cursor no inicio da selecao.
 * @param selectionEnd Posicao do cursor no fim da selecao. Se nao definido, eh setado igual ao inicio.
 */
function selectInterval(input, selectionStart, selectionEnd) {
    if (!selectionEnd)
        selectionEnd = selectionStart;
    if (input.setSelectionRange) {
        input.focus();
        input.setSelectionRange(selectionStart, selectionEnd);
    }
    else if (input.createTextRange) {
        var range = input.createTextRange();
        range.collapse(true);
        range.moveEnd('character', selectionEnd);
        range.moveStart('character', selectionStart);
        range.select();
    }
}

function initElements() {

    /* Submit form criar elemento */
    var formElement = document.getElementById("formCreateElement");
    formElement.addEventListener("submit", function (event) {
        event.preventDefault();
        var element = new Element();
        var inputElement = document.getElementById("elementName");
        var elementName = inputElement.value;
        var elementType = inputElement.getAttribute("data-element-type");
        element.init(0, elementName, elementType);
        // Enviar mensagem somente quando voltar do banco com ID preenchido
        $.ajax({
            url: "ServletController?action=SaveDiagramItem",
            type: "POST",
            data: {
                itemToSave: "element",
                itemType: element.type,
                itemJson: JSON.stringify(element),
                idDiagram: idDiagram
            },
            dataType: "html",
            success: function (result) {
                if (!isNaN(result) && result !== "0" && result !== "") {
                    element.id = Number(result);
                    createElement(elementType, element);
                    inputElement.value = "";
                    // Mensagem para ser enviada para o servidor via socket
                    var message = {
                        msg: "create" + convertType(element.type),
                        element: element
                    };
                    // Metodo em websocket.js que envia a string para o servidor
                    sendText(JSON.stringify(message));
                    $("#modal-novo-elemento").modal("hide");
                }
            },
            error: function (err) {
                console.log("Error: " + err.status);
                console.log("Error Message: " + err.statusText);
            }
        });
    });
    /* Submit form criar elemento */

    /* Submit form criar relacao */
    var formRelation = document.getElementById("formCreateRelation");
    formRelation.addEventListener("submit", function (event) {
        event.preventDefault();
        var modal = document.getElementById("modal-nova-relacao");
        var sourceElement = JSON.parse(document.getElementById("elementSource").value);
        var targetElement = JSON.parse(document.getElementById("elementTarget").value);
        var relationType = modal.getAttribute("data-relation-type");

        var relation = new Relation();
        relation.init(0, relationType);
        relation.source = getElement(sourceElement.type, sourceElement.id);
        relation.target = getElement(targetElement.type, targetElement.id);
        // Enviar mensagem somente quando voltar do banco com ID preenchido
        $.ajax({
            url: "ServletController?action=SaveDiagramItem",
            type: "POST",
            data: {
                itemToSave: "relation",
                itemType: relation.type,
                itemJson: JSON.stringify(relation),
                idDiagram: idDiagram
            },
            dataType: "html",
            success: function (result) {
                if (!isNaN(result) && result !== "0" && result !== "") {
                    relation.id = Number(result);
                    createRelation(relationType, relation);
                    // Mensagem para ser enviada para o servidor via socket
                    var message = {
                        msg: "add" + convertType(relationType),
                        id: result,
                        source: relation.source,
                        target: relation.target
                    };
                    // Metodo em websocket.js que envia a string para o servidor
                    sendText(JSON.stringify(message));
                    $(modal).modal("hide");
                } else {
                    console.log("Erro", result);
                }
            },
            error: function (err) {
                console.log("Error: " + err.status);
                console.log("Error Message: " + err.statusText);
            }
        });
    });
    /* Submit form criar relacao */

    /* Submit form excluir elemento */
    var formExcludeElement = document.getElementById("formExcludeElement");
    formExcludeElement.addEventListener("submit", function (event) {
        event.preventDefault();
        var elementToExclude = JSON.parse(document.getElementById("elementToExclude").value);
        elementToExclude = getElement(elementToExclude.type, elementToExclude.id);

        $.ajax({
            url: "ServletController?action=DeleteDiagramItem",
            type: "POST",
            data: {
                itemType: elementToExclude.type,
                itemId: elementToExclude.id
            },
            dataType: "html",
            success: function (result) {
                if (result === "1") {
                    console.log("Item Excluido!");
                    $("#modal-excluir-elemento").modal("hide");
                    deleteElement(elementToExclude);
                    // mensagem para ser enviada para o servidor via socket
                    var message = {
                        msg: "deleteElement",
                        element: elementToExclude
                    };
                    // metodo em websocket.js que envia a string para o servidor
                    sendText(JSON.stringify(message));
                }
            },
            error: function (err) {
                console.log("Error: " + err.status);
                console.log("Error Message: " + err.statusText);
            }
        });
    });
    /* Submit form excluir elemento */

    /* Submit form renomear elemento */
    var formRenameElement = document.getElementById("formRenameElement");
    formRenameElement.addEventListener("submit", function (event) {
        event.preventDefault();
        var elementToRename = JSON.parse(document.getElementById("elementToRename").value);
        var diagramElement = getElement(elementToRename.type, elementToRename.id);
        var oldName = diagramElement.name;
        elementToRename.name = document.getElementById("elementNewName").value;
        console.log("Item Renomeado!");
        $("#modal-renomear-elemento").modal("hide");
        renameElement(elementToRename);
        // mensagem para ser enviada para o servidor via socket
        var message = {
            msg: "renameElement",
            oldName: oldName,
            element: elementToRename
        };
        // metodo em websocket.js que envia a string para o servidor
        sendText(JSON.stringify(message));
    });
    /* Submit form renomear elemento */

    function convertType(type) {
        switch (type) {
            case "ACTOR":
                return "UMLActor";
            case "USE_CASE":
                return "UMLUseCase";
            case "COMMUNICATION":
                return "UMLCommunication";
            case "INCLUSION":
                return "UMLInclude";
            case "EXTENSION":
                return "UMLExtend";
            case "GENERALIZATION":
                return "UMLGeneralization";
        }
    }
}

function Element() {
    this.id;
    this.name;
    this.type;
    this.start;
    this.end;
    this.width = 0;
    this.height = 0;
    this.x = 0;
    this.y = 0;

    Element.prototype.init = function (id, name, type, x, y) {
        this.id = id;
        this.name = name;
        this.type = type;
        if (type === "ACTOR") {
            this.width = 50;
            this.height = 70;
        } else if (type === "USE_CASE") {
            this.width = 70;
            this.height = 30;
        }
        if (x)
            this.x = x;
        if (y)
            this.y = y;
    };
}

function Relation() {
    this.id;
    this.type;
    this.typeString;
    this.source;
    this.target;
    this.start;
    this.end;

    Relation.prototype.init = function (id, type) {
        this.id = id;
        this.type = type;
        switch (type) {
            case "COMMUNICATION":
                this.typeString = "associates with";
                break;
            case "EXTENSION":
                this.typeString = "extend";
                break;
            case "INCLUSION":
                this.typeString = "include";
                break;
            case "GENERALIZATION":
                this.typeString = "inherits from";
                break;
        }
    };
}

function save() {
    console.log("MODIFICADO?" + modified);
    validateTextualDiagram();
    if (modified && validDiagram) {
        var i;
        var diagramTemp = {id: 0, name: "", elements: [], relations: []};
        diagramTemp.id = ucDiagram.id;
        diagramTemp.name = ucDiagram.name;
        for (i in ucDiagram.actors) {
            diagramTemp.elements.push(ucDiagram.actors[i]);
        }
        for (i in ucDiagram.useCases) {
            diagramTemp.elements.push(ucDiagram.useCases[i]);
        }
        diagramTemp.relations = ucDiagram.relations;
        updateDiagram(diagramTemp);
    }
    setTimeout(save, 15000);
}

function validateTextualDiagram() {
    if (modified) {
        console.log("Validate!");
        validDiagram = false;
        $.ajax({
            url: "ServletController?action=ValidateTextualDiagram",
            type: "POST",
            data: {
                diagram: ucDiagram.area.value
            },
            dataType: "html",
            success: function (result) {
                try {
                    var errors = JSON.parse(result);
                    var errorsArea = document.getElementById("errorsArea");
                    if (errors.length > 0) {
                        $(errorsArea).removeClass("hidden");
                        errorsArea.value = "";
                        for (var i in errors) {
                            errorsArea.value += errors[i] + "\n";
                        }
                        errorsArea.value = errorsArea.value.substring(0, errorsArea.value.length - 1);
                        errorsArea.setAttribute("rows", errors.length);
                        validDiagram = false;
                    } else {
                        $(errorsArea).addClass("hidden");
                        errorsArea.value = "";
                        validDiagram = true;
                    }
                    modified = false;
                    setTimeout(validateTextualDiagram(), 5000);
                } catch (e) {
                    console.log("Error: " + e);
                }
            },
            error: function (err) {
                console.log("Error: " + err.status);
                console.log("Error Message: " + err.statusText);
            }
        });
    }
}

save();

window.onload = function () {
    var text = document.getElementById("textDiagram");
    // explicacao sobre event bublling and captirin
    // http://javascript.info/tutorial/bubbling-and-capturing
    //
    loadMapKey();
    initElements();

    text.addEventListener("keypress", function (evt) {
        console.log(evt);
        modified = true;
    }, true);

};

