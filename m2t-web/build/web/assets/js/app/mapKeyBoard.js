/* global Mousetrap, MouseEvent */
// https://craig.is/killing/mice
'use strict';

var instanceMouseEvent = function (evt) {
    var instance = new MouseEvent(evt, {
        view: window,
        bubbles: true,
        cancelable: true
    });
    return instance;
};

var bindEvent = function (element, evt) {
    if (element === null || element === undefined) {
        console.log('Comando para abrir o menu de configuracoes nao esta habilidado nessa pagina');
    } else {
        console.dir(element);
        var evtClick = instanceMouseEvent(evt);
        element.dispatchEvent(evtClick);
    }
};


var functions = {
    setCursorTextArea: function () {
        var text = document.getElementById('textDiagram');
        if (text === null || text === undefined) {
            console.log('Comando para focar cursor na caixa de texto nao esta habilitado nessa pagina');
            return false;
        } else {
            text.focus();
            console.dir(text);
        }
    },
    shareDiagram: function () {
        var list = querySelectorAll('.compartilhar');
        console.log(list);
    },
    hotKeysUCUML: {
        addActor: function () {
            var btn = document.getElementById("create-act");
            console.log(btn, "addActor");
            bindEvent(btn, "click");
        },
        addUseCase: function () {
            var btn = document.getElementById("create-uc");
            console.log(btn, "addUseCase");
            bindEvent(btn, "click");
        },
        addRelation: function () {
            var btn = document.getElementById("create-rel");
            console.log(btn, "addRelation");
            bindEvent(btn, "click");
        },
        addIncludeRelation: function () {
            var btn = document.getElementById("create-inc");
            console.log(btn, "addIncludeRelation");
            bindEvent(btn, "click");
        },
        addExtendsRelation: function () {
            var btn = document.getElementById("create-ext");
            console.log(btn, "addExcludeRelation");
            bindEvent(btn, "click");
        },
        addInheritanceRelation: function () {
            var btn = document.getElementById("create-gen");
            console.log(btn, "addInheritanceRelation");
            bindEvent(btn, "click");
        },
        excludeElement: function () {
            var btn = document.getElementById("delete-elm");
            console.log(btn, "excludeElement");
            bindEvent(btn, "click");
        },
        renameElement: function () {
            var btn = document.getElementById("rename-elm");
            console.log(btn, "renameElement");
            bindEvent(btn, "click");
        }
    },
    hotKeysMainMenu: {
        menuConfig: function () {
            var menu = document.querySelector(".dropdown");
            var opt = menu.firstElementChild;

            //evtClick.initMouseEvent('click', true, true, window, 1, 12, 345, 7, 220, false, false, true, false, 0, null );;
            bindEvent(opt, "click");
        },
        menuModalDiagram: function () {
            var menu = document.getElementById("create-diagram");
            bindEvent(menu, "click");
        },
        menuExit: function () {
            var menu = document.getElementById("exit");
            if (menu === null || menu === undefined) {
                console.log('Comando para abrir o menu de configuracoes nao esta habilidado nessa pagina');
            } else {
                var evtClick = instanceMouseEvent();
                menu.dispatchEvent(evtClick);
            }
        }
    }
};



function loadMapKey() {
    var mapKeys = {};

    mapKeys['alt+f'] = mapKeys['ctrl+shift+alt+f'] = functions.setCursorTextArea;
    mapKeys['alt+c'] = mapKeys['ctrl+shift+alt+c'] = functions.hotKeysMainMenu.menuConfig;
    mapKeys['alt+n'] = mapKeys['ctrl+shift+alt+m'] = functions.hotKeysMainMenu.menuModalDiagram;
    mapKeys['alt+e'] = mapKeys['ctrl+shift+alt+e'] = functions.hotKeysMainMenu.menuExit;
    mapKeys['alt+s'] = mapKeys['ctrl+shift+alt+s'] = functions.shareDiagram;

    mapKeys['alt+a'] = mapKeys['ctrl+shift+alt+a'] = functions.hotKeysUCUML.addActor;
    mapKeys['alt+u'] = mapKeys['ctrl+shift+alt+u'] = functions.hotKeysUCUML.addUseCase;
    mapKeys['alt+r'] = mapKeys['ctrl+shift+alt+r'] = functions.hotKeysUCUML.addRelation;
    mapKeys['alt+w'] = functions.hotKeysUCUML.addIncludeRelation;
    mapKeys['alt+shift+e'] = functions.hotKeysUCUML.addExtendsRelation;
    mapKeys['alt+h'] = mapKeys['shift+alt+h'] = functions.hotKeysUCUML.addInheritanceRelation;
    mapKeys['alt+shift+x'] = mapKeys['alt+x'] = functions.hotKeysUCUML.excludeElement;
    mapKeys['alt+shift+m'] = mapKeys['alt+m'] = functions.hotKeysUCUML.renameElement;

    for (var i in mapKeys) {
        Mousetrap.bind(i, mapKeys[i]);
    }
}
window.onload = function () {
    loadMapKey();
};

