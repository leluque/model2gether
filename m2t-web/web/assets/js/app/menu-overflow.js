// javasript para carregar o menu que aparece ao apertar o botao 'arquivo'

var loadMenu =  function () {
	var menuLeft = document.getElementById('file');
	var callMenu = document.getElementById('callMenu');

	callMenu.addEventListener('click', function(event){
		console.log(event);
	}, false);
}

