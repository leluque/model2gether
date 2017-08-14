
/**
 * @param elementType Obrigatorio - Tipo de elemento a ser criado ("USE_CASE" ou "ACTOR")
 * @param elementBase Opcional - Elemento Base. Se nao for definido, pegara posicao pelo click
 */
function createElement(elementType, elementBase) {
    if (!elementType)
        return false;
    
    if(elementType === "ACTOR")
        elementType = UMLActor;
    else if(elementType === "USE_CASE")
        elementType = UMLUseCase;
    
    ucDiagram.interaction(false);
    var element;
    
    if (elementBase) {   // Se recebeu elemento base, adicionar elemento a partir do mesmo
        element = new elementType({x: elementBase.x, y: elementBase.y});
        element.setName(elementBase.name);
        element._id = elementBase.id;
        if(elementBase.width)
            element.width(elementBase.width);
        if(elementBase.height)
            element.height(elementBase.height);
        create();
    } else {            // Senao, adicionar evento de click para capturar posicao
        ucDiagram._div.onclick = function (event) {
            // X e Y definidos a partir do click
            var x = event.pageX - ucDiagram._div.offsetLeft;
            var y = event.pageY - ucDiagram._div.offsetTop;
            element = new elementType({x: x, y: y});
            element.setName(element._type);
            // Enviar mensagem somente quando voltar do banco com ID preenchido
            var elementTemp = convertElement(element);
            $.ajax({
                url: "ServletController?action=SaveDiagramItem",
                type: "POST",
                data: {
                    itemToSave: "element",
                    itemType: elementTemp.type,
                    itemJson: JSON.stringify(elementTemp),
                    idDiagram: idDiagram
                },
                dataType: "html",
                success: function (result) {
                    if(!isNaN(result) && result !== "0" && result !== "") {
                        element._id = elementTemp.id= Number(result);
                        // Mensagem para ser enviada para o servidor via socket
                        var message = {
                            msg: "create" + element._type,
                            element: elementTemp
                        };
                        // Metodo em websocket.js que envia a string para o servidor
                        sendText(JSON.stringify(message));
                        create();
                    }
                },
                error: function (err) {
                    console.log("Error: " + err.status);
                    console.log("Error Message: " + err.statusText);
                }
            });
        };
    }

    function create() {
        // Adicionar os Listeners click e drop para enviar notificacao para o servidor
        ucDiagram._div.addEventListener("mousedown", function (event) {
            event.preventDefault();
            var x = event.pageX - ucDiagram._div.offsetLeft;
            var y = event.pageY - ucDiagram._div.offsetTop;
            if (ucDiagram.getElementByPoint(x, y) === element) {
                var message = {
                    msg: "selectElement",
                    element: convertElement(element)
                };
                sendText(JSON.stringify(message));
            }
        });
        ucDiagram._div.addEventListener("mouseup", function (event) {
            event.preventDefault();
            if (element._selected) {
                var message = {
                    msg: "dropElement",
                    element: convertElement(element)
                };
                sendText(JSON.stringify(message));
                modified = true;
            }
        });
        ucDiagram.addElement(element);
        ucDiagram._div.onclick = false;
        ucDiagram.interaction(true);
        ucDiagram.draw();
        diagramBase.elements.push(convertElement(element));
    }
}

/**
 * @param relationType Obrigatorio - Tipo de relacao a ser criada ("COMMUNICATION", "INCLUSION" "EXTENSION", ou "GENERALIZATION")
 * @param relationBase Opcional - Relacao Base. Se nao for definido, pegara posicoes pelo click
 */
function createRelation(relationType, relationBase) {
    if (!relationType)
        return false;

    if(relationType === "COMMUNICATION")
        relationType = UMLCommunication;
    else if(relationType === "INCLUSION")
        relationType = UMLInclude;
    else if(relationType === "EXTENSION")
        relationType = UMLExtend;
    else if(relationType === "GENERALIZATION")
        relationType = UMLGeneralization;

    ucDiagram.interaction(false);
    var source, target;

    if (relationBase) { // Se posicoes estiverem definidas, adicionar relacao a partir das mesmas
        // Origem e destino buscados a partir da relacao base
        source = getElement(relationBase.source.type, relationBase.source.id);
        target = getElement(relationBase.target.type, relationBase.target.id);
        create();
    } else {        // Se posicao nao estiver definida, adicionar evento de click para capturar posicao
        function doubleClick(createFunction) {
            var firstClick = true;
            var x1 = 0, y1 = 0;
            ucDiagram._div.onclick = function (event) {
                // X e Y definidos a partir do click
                var x2 = event.pageX - ucDiagram._div.offsetLeft;
                var y2 = event.pageY - ucDiagram._div.offsetTop;
                if (firstClick) {
                    firstClick = false;
                    x1 = x2;
                    y1 = y2;
                } else {
                    source = ucDiagram.getElementByPoint(x1, y1);
                    target = ucDiagram.getElementByPoint(x2, y2);
                    var relation = createFunction();
                    if(relation) {
                        // Enviar mensagem somente quando voltar do banco com ID preenchido
                        var relationTemp = convertElement(relation);
                        relationTemp.source = convertElement(source);
                        relationTemp.target = convertElement(target);
                        $.ajax({
                            url: "ServletController?action=SaveDiagramItem",
                            type: "POST",
                            data: {
                                itemToSave: "relation",
                                itemType: relationTemp.type,
                                itemJson: JSON.stringify(relationTemp),
                                idDiagram: idDiagram
                            },
                            dataType: "html",
                            success: function (result) {
                                if (!isNaN(result) && result !== "0" && result !== "") {
                                    relation._id = relationTemp.id = Number(result);
                                    // Mensagem para ser enviada para o servidor via socket
                                    var message = {
                                        msg: "add" + relation._type,
                                        id: result,
                                        source: relationTemp.source,
                                        target: relationTemp.target
                                    };
                                    // Metodo em websocket.js que envia a string para o servidor
                                    sendText(JSON.stringify(message));
                                    create();
                                }
                            },
                            error: function (err) {
                                console.log("Error: " + err.status);
                                console.log("Error Message: " + err.statusText);
                            }
                        });
                    }
                }
            };
        };
        doubleClick(create);
    }

    function create() {
        var relation;
        //  AVALIAR, POIS SOMENTE ASSIM ELE PERMITE CRIAR UMA RELACAO ENTRE UM ELEMENTO E OUTRA RELACAO
        if (source && target) {
            relation = new relationType({a: source, b: target});
            if(relationBase)
                relation._id = relationBase.id;
            ucDiagram.addElement(relation);
        } else {
            new Dialog({text: "Elemento Inválido"}).show();
            return false;
        }
        ucDiagram._div.addEventListener("mousedown", function (event) {
            event.preventDefault();
            var x = event.pageX - ucDiagram._div.offsetLeft;
            var y = event.pageY - ucDiagram._div.offsetTop;
            if (ucDiagram.getElementByPoint(x, y) === relation) {
                var message = {
                    msg: "selectElement",
                    element: convertElement(relation)
                };
                sendText(JSON.stringify(message));
            }
        });
        ucDiagram._div.addEventListener("mouseup", function (event) {
            event.preventDefault();
            if (relation._selected === 0) {
                var message = {
                    msg: "dropElement",
                    element: convertElement(relation)
                };
                sendText(JSON.stringify(message));
            }
        });
        ucDiagram._div.onclick = false;
        ucDiagram._clearMotion();
        ucDiagram.interaction(true);
        ucDiagram.draw();

        return relation;
    }
}

/**
 * @param element Elemento selecionado
 */
function selectElement(element) {
    element = getElement(element.type, element.id);
    ucDiagram._drawMotion(element);
    ucDiagram.draw();
}

/**
 * @param element Elemento solto
 */
function dropElement(element) {
    var diagramElement = getElement(element.type, element.id);
    if(element.type === "ACTOR" || element.type === "USE_CASE") {
        diagramElement.position(element.x, element.y);
        diagramElement._width = element.width;
        diagramElement._height = element.height;
    }
    diagramElement.notifyChange();
    ucDiagram._clearMotion();
    ucDiagram.draw();
    modified = true;
}

/**
 * @param elementBase Elemento Base com os dados para buscar e renomear elemento
 */
function renameElement(elementBase) {
    
    if(elementBase) {
        var element = getElement(elementBase.type, elementBase.id);
        element.setName(elementBase.name);
        element._width = elementBase.width;
        element._height = elementBase.height;
        element.notifyChange();
        ucDiagram.draw();
        modified = true;
    } else {
        ucDiagram._div.onclick = function (event) {
            // X e Y definidos a partir do click
            var x = event.pageX - ucDiagram._div.offsetLeft;
            var y = event.pageY - ucDiagram._div.offsetTop;
            element = ucDiagram.getElementByPoint(x, y);
            ucDiagram._div.onclick = false;
            if(element && element._components[1].showDialog)
                element._components[1].showDialog();
            else
                new Dialog({text: "Elemento Inválido"}).show();
        };
    }
}

/**
 * @param elementBase Opcional - Elemento base. Se nao for definido, pegara posicao pelo click
 */
function deleteElement(elementBase) {
    ucDiagram.interaction(false);
    var element;

    if (elementBase) {   // Se recebeu elemento base, buscar elemento para exclusao a partir do mesmo
        // elemento buscado a partir do elemento base
//        element = ucDiagram.getElementByPoint(elementBase.x, elementBase.y);
        element = getElement(elementBase.type, elementBase.id);
        excluir();
    } else {            // Senao, adicionar evento de click para capturar posicao
        ucDiagram._div.onclick = function (event) {
            // X e Y definidos a partir do click
            var x = event.pageX - ucDiagram._div.offsetLeft;
            var y = event.pageY - ucDiagram._div.offsetTop;
            element = ucDiagram.getElementByPoint(x, y);
            $.ajax({
                url: "ServletController?action=DeleteDiagramItem",
                type: "POST",
                data: {
                    itemType: convertType(element._type),
                    itemId: element._id
                },
                dataType: "html",
                success: function (result) {
                    if (result === "1") {
                        console.log("Item Excluido!");
                        // mensagem para ser enviada para o servidor via socket
                        var message = {
                            msg: "deleteElement",
                            element: convertElement(element)
                        };
                        // metodo em websocket.js que envia a string para o servidor
                        sendText(JSON.stringify(message));
                        excluir();
                    }
                },
                error: function (err) {
                    console.log("Error: " + err.status);
                    console.log("Error Message: " + err.statusText);
                }
            });
        };
    }

    function excluir() {
        if (element) {
            ucDiagram.delElement(element);
        } else {
            new Dialog({text: "Elemento Inválido"}).show();
        }
        ucDiagram._div.onclick = false;
        ucDiagram.interaction(true);
        ucDiagram.draw();
        modified = true;
    }
}

/**
 * @param diagram Diagrama a ser aberto - recebido pelo Websocket
 */
function openDiagram(diagram) {
    ucDiagram = new UMLUseCaseDiagram({id: "umlUC", width: 938, height: 498}); // Id da <div> do diagrama, largura, altura. VERIFICAR PARAMETROS
    ucDiagram.setName(diagram.name || ucDiagram.getName());
    ucDiagram.setId(diagram.id || idDiagram);
    var i;
    for (i in diagram.elements) {
        createElement(diagram.elements[i].type, diagram.elements[i]);
    }
    for (i in diagram.relations) {
        createRelation(diagram.relations[i].type, diagram.relations[i]);
    }
    ucDiagram.draw();
}

/**
 * @param diagram Novo nome do diagrama
 */
function renameDiagram(diagram) {
    ucDiagram.setName(diagram.newName);
    modified = true;
}

/**
 * @param type Obrigatorio - Tipo do elemento buscado ("ACTOR"" ou "USE_CASE")
 * @param id Obrigatorio - ID do elemento buscado
 */
function getElement(type, id) {
    var i;
    if(type === "ACTOR" || type === "USE_CASE") {
        for (i in ucDiagram._nodes)
            if (ucDiagram._nodes[i]._id === id && type === convertType(ucDiagram._nodes[i]._type))
                return ucDiagram._nodes[i];
    } else if(type === "COMMUNICATION" || type === "INCLUSION" ||
            type === "EXTENSION" || type === "GENERALIZATION") {
        for (i in ucDiagram._relations)
            if (ucDiagram._relations[i]._id === id && type === convertType(ucDiagram._relations[i]._type))
                return ucDiagram._relations[i];
    }
}

/**
 * Converter um elemento no formato jsUML2 para um elemento no formato JSON
 * @param elementBase Elemento no formato jsUML2
 * @returns elemento no formato JSON
 */
function convertElement(elementBase) {
    var element = {};
    if(elementBase._type === "UMLActor" || elementBase._type === "UMLUseCase") {
        element = {
            id: elementBase._id,
            name: elementBase.getName(),
            width: elementBase._width,
            height: elementBase._height,
            x: elementBase._x,
            y: elementBase._y,
            type: convertType(elementBase._type)
        };
    } else if(elementBase._type === "UMLCommunication" || elementBase._type === "UMLInclude" ||
            elementBase._type === "UMLExtend" || elementBase._type === "UMLGeneralization") {
        var sourceType = convertType(elementBase._elemA._type);
        var sourceId = elementBase._elemA._id;
        var targetType = convertType(elementBase._elemB._type);
        var targetId = elementBase._elemB._id;
        element = {
            id: elementBase._id,
            type: convertType(elementBase._type),
            source: convertElement(getElement(sourceType, sourceId)),
            target: convertElement(getElement(targetType, targetId))
        };
    }
    
    return element;
}

/**
 * Converte o tipo de um elemento do formato jsUML2 para o padrao dos elementos do Java
 * @param type tipo do elemento no formato jsUML2
 * @returns tipo do elemento no padrao dos elementos Java
 */
function convertType(type) {
    if (type === "UMLActor")
        return "ACTOR";
    else if (type === "UMLUseCase")
        return "USE_CASE";
    else if (type === "UMLCommunication")
        return "COMMUNICATION";
    else if (type === "UMLInclude")
        return "INCLUSION";
    else if (type === "UMLExtend")
        return "EXTENSION";
    else if (type === "UMLGeneralization")
        return "GENERALIZATION";
}

websocket.onclose = function() {
    console.log("Websocket fechado em: " + new Date());
};

// Modificar metodo para renomear item
function modifyMethods() {
    TextArea.prototype.showDialog = function () {
        var c = this;
        this._active = true;
        var f = document.createElement("div");
        var b = document.createElement("form");
        var e = document.createElement("input");
        var a = document.createElement("input");
        var d = document.createElement("input");
        f.className = "ud_popup";
        e.setAttribute("type", "text");
        e.setAttribute("style", "width: 85%;");
        e.value = this._text;
        a.setAttribute("type", "submit");
        a.setAttribute("value", "ok");
        d.setAttribute("type", "submit");
        d.setAttribute("value", "no");
        this.changeText = function (g) {
            if (c._active) {
                var oldName = c._parent.getName();
                c.setText(e.value);
                document.body.removeChild(f);
                c._active = false;
                c.notifyChange();
                var message = {
                    msg: "renameElement",
                    oldName: oldName,
                    element: convertElement(c._parent)
                };
                sendText(JSON.stringify(message));
                modified = true;
            }
        };
        this.closeDialog = function (g) {
            if (c._active) {
                document.body.removeChild(f);
                c._active = false;
                c.notifyChange();
            }
        };
        b.onsubmit = function () {
            return false;
        };
        a.addEventListener("click", this.changeText, false);
        d.addEventListener("click", this.closeDialog, false);
        b.appendChild(e);
        b.appendChild(a);
        b.appendChild(d);
        f.appendChild(b);
        document.body.appendChild(f);
        e.focus();
        f.style.top = (window.innerHeight - b.offsetHeight) / 2 + "px";
        f.style.left = (window.innerWidth - b.offsetWidth) / 2 + "px";
    };
    
    TextBox.prototype.showDialog = function () {
        if (this.active) {
            return;
        }
        var d = this;
        this.active = true;
        var f = document.createElement("div");
        var c = document.createElement("form");
        var a = document.createElement("input");
        var b = document.createElement("input");
        f.className = "ud_popup";
        a.setAttribute("type", "text");
        a.setAttribute("value", this.decode(this.getValue()));
        b.setAttribute("type", "submit");
        b.setAttribute("value", "ok");
        this.changeText = function (g) {
            if (d.active) {
                d.setText(d.encode(a.value));
                document.body.removeChild(f);
                d.active = false;
                d.notifyChange();
                if(d._parent === ucDiagram) {
                    var message = {
                        msg: "renameDiagram",
                        newName: a.value
                    };
                    sendText(JSON.stringify(message));
                    modified = true;
                }
            }
        };
        this.closeDialog = function (g) {
            if (d.active) {
                document.body.removeChild(f);
                d.active = false;
                d.notifyChange();
            }
        };
        c.onsubmit = function () {
            return false;
        };
        b.addEventListener("click", this.changeText, false);
        c.appendChild(a);
        c.appendChild(b);
        if (this.deletable) {
            var e = document.createElement("input");
            e.setAttribute("type", "submit");
            e.setAttribute("value", "delete");
            this.deleteDialog = function (g) {
                if (d.active) {
                    document.body.removeChild(f);
                    d.active = false;
                    d.notifyDelete();
                    d.notifyChange();
                }
            };
            e.addEventListener("click", this.deleteDialog, false);
            c.appendChild(e);
        }
        f.appendChild(c);
        document.body.appendChild(f);
        a.focus();
        f.style.top = (window.innerHeight - c.offsetHeight) / 2 + "px";
        f.style.left = (window.innerWidth - c.offsetWidth) / 2 + "px";
    };
}

function save() {
    console.log("MODIFICADO?" + modified);
    if(modified) {
        var i;
        var diagramTemp = {id: 0, name: "", elements: [], relations: []};
        diagramTemp.id = ucDiagram.getId();
        diagramTemp.name = ucDiagram.getName();
        for(i in ucDiagram._nodes) {
            diagramTemp.elements.push(convertElement(ucDiagram._nodes[i]));
        }
        for(i in ucDiagram._relations) {
            diagramTemp.relations.push(convertElement(ucDiagram._relations[i]));
        }
        updateDiagram(diagramTemp);
    }
    setTimeout(save, 15000);
}
modifyMethods();
save();