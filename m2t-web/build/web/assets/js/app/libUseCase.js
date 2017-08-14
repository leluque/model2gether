;(function (window) {
    //new UMLUseCaseDiagram({id: 'umlUC', width: 900, height: 200});
    'use strict';
    var umlUC = function (element) {

        if (!(this instanceof umlUC)) {
            return new umlUC(element);
        }

        if (typeof element == "string") {
            //console.log(element, document.querySelector(element) , document.querySelector('.ud_diagram_div'));
            if (document.querySelector(element) !== null)
                this._element = document.querySelector(element);
            else {
                console.log(document.querySelector(element), 'Diagrama ja foi criado');
                return null;
            }

        }

        else if (typeof element == "object") {
            console.log('object');
            this._element = element;
        }

        else {
            console.log('parÃ¢metro indefinido');
            return;
        }


        this._diagram = {};
        this._name = "";
        this._actors = [];
        this._communications = [];
        this._extesions = [];
        this._useCases = [];
        this._interactiveDiagram = true;
        this._updateHeightCanvas = false;
        this._elementID = 1;

        this._canvasBase = {
            name: "Diagrama UML UC",
            x: 900,
            y: 100
        },
        this._parameters = {
            id: this._element.id,
            width: 698,
            height: 498
        },
        this.addRelationship = function (diagrama, x1, y1, x2, y2, type) {
            'use strict';
//            var div = document.getElementById("umlUC");
//            var diagram = this._diagram;
//            var comm = this._communications;
//
//            var interacionDoubleClick = function (funcion) {
//                diagram.interaction(false);
//                var primeiroClick = true;
//                var x1 = 0, y1 = 0;
//                var funcionCaptura = function (event) {
//                    var x2 = event.pageX - div.offsetLeft;
//                    var y2 = event.pageY - div.offsetTop;
//                    if (primeiroClick) {
//                        primeiroClick = false;
//                        x1 = x2;
//                        y1 = y2;
//                    } else {
//                        funcion(diagram, x1, y1, x2, y2);
//                        div.onclick = false;
//                        diagram.interaction(true);
//                    }
//                };
//                div.onclick = funcionCaptura;
//            };
//
//            function add(diagrama, x1, y1, x2, y2) {
                var elemento1 = diagrama.getElementByPoint(x1, y1);
                var elemento2 = diagrama.getElementByPoint(x2, y2);

                if (elemento1 && elemento2) {
                    var relationship;
                    var objRel = {a: elemento1, b: elemento2};
                    switch (type) {
                        case "UMLCommunication":
                            relationship = new UMLCommunication(objRel);
                            break;
                        case "UMLInclude":
                            relationship = new UMLInclude(objRel);
                            break;
                        case "UMLExtend":
                            relationship = new UMLExtend(objRel);
                            break;
                    }
                    ;
                    diagrama.addElement(relationship);
//                    comm.push(relationship);
                    relationship.setDiagram(diagrama);
                    diagrama.draw();
                } else {
                    new Dialog({text: "Elemento InvÃ¡lido"}).show();
                }
//            }
//            ;
//            interacionDoubleClick(add);
        },
        this.addExtesion = function () {

        },
        this.addActor = function (coords) {
            'use strict';
            var px = Math.floor(this._parameters.width / 2);
            var py = Math.floor(this._parameters.height / 2);

            var ator = {
                x: px,
                y: py
            };
            if(coords)
                ator = coords;

            var el = new UMLActor(ator);

            el.setName("Ator");
            el.resizable = true;
            if(!coords)
                el._id = this._elementID;

            this._actors.push(el);
//            el.setDiagram(this._diagram); // Ele adiciona sozinho essa relacao
            this._diagram.addElement(el);

            this._elementID++;

            return el;
        },
        this.addUC = function (coords) {
            'use strict';

            var px = Math.floor(this._parameters.width / 2);
            var py = Math.floor(this._parameters.height / 2);

            var casoDeUso = {
                x: px,
                y: py
            };

            if(coords)
                casoDeUso = coords;

            var el = new UMLUseCase(casoDeUso);
            console.dir(el);

            el.setName("Use Case");
            el.resizable = true;
            if(!coords)
                el._id = this._elementID;

            this._useCases.push(el);
            el.setDiagram(this._diagram);
            this._diagram.addElement(el);

            this._elementID++;

            return el;
        },
        this.delElement = function (diagrama, x, y) {
            'use strict';
//            var div = document.getElementById("umlUC");
//            var diagram = this._diagram;
//
//            var interacionSingleClick = function (funcion) {
//                diagram.interaction(false);
//                var funcionCaptura = function (event) {
//                    var x = event.pageX - div.offsetLeft;
//                    var y = event.pageY - div.offsetTop;
//                    funcion(diagram, x, y);
//                    div.onclick = false;
//                    diagram.interaction(true);
//                };
//                div.onclick = funcionCaptura;
//            };
//
//            function del(diagrama, x, y, elemento) {
                var elemento = diagrama.getElementByPoint(x, y);

                if (elemento) {
                    diagrama.delElement(elemento);
                    diagrama.draw();
                } else {
                    new Dialog({text: "Invalid Element!"}).show();
                }
//            }
//            ;
//            interacionSingleClick(del);
        },
        this.exportToXML = function () {
            'use strict';
            var xml = this._diagram.getXML();
            var str = this._diagram.getXMLString();
            console.log(xml, typeof xml);
            console.log(str);
            return str;
        },
        this.importFromXMLText = function (XMLString) {
            'use strict';
            var _prototype = new Array();
            XMLString = XMLString.replace(/\n\t/gi, "");
            XMLString = XMLString.replace("ÃÂ«extendÃÂ»", "Â«extendÂ»");
            XMLString = XMLString.replace("ÃÂ«includeÃÂ»", "Â«includeÂ»");
            var a = (new DOMParser()).parseFromString(XMLString, "text/xml");
            var sucess = this._diagram.setXML(a, _prototype);
            this._diagram.draw();
            return sucess;
        },
        this.generatePNG = function () {
            var e = document.querySelector('.ud_diagram_canvas');
            //console.dir(e);
            var url = e.toDataURL('image/png');
            console.dir(url);
            window.open(url, '_blank');
        };
    };


    umlUC.fn = umlUC.prototype = {
        init: function () {
            'use strict';
            //var canvas = document.getElementById('umlUC');
            //console.log(canvas.id, canvas.clientWidth, canvas.clientHeight);
            /*
             var e = {
             id: canvas.id,
             _width: canvas.clientWidth,
             _height: canvas.clientHeight
             };
             */
            //console.dir(this._parameters);

/*
            requirejs(['jsuml2/UDCore', 'jsuml2/UDModules'], function(core, modules){
                console.log(core, modules);
            });
*/
            var base = new UMLUseCaseDiagram(this._parameters);


            this._diagram = base;
            //this._diagram._height = e._height;
            //this._diagram._width = e._width;

            //console.log(base);

            base.interaction(true);
            // setando um nome
            base.setName('Use Case Diagram');
            // diagrama altera seu tamanho conforme os componentes vÃ£o sendo inserids
            // 	base.setUpdateHeightCanvas(false);

            // desenhando o diagrama
            //base.draw();
            //console.dir(this._diagram);

            return this;
        },
        renderize: function () {
            console.dir(this._diagram);
            //this._diagram.draw();
        }

    };


//    window.$ = umlUC,
    window.umlUC = umlUC;
/*
    define(function() {
        return {
            umlUC: umlUC
        }
    });
*/
})(window);


