var xmlhttp;

function updateGameImage() {
	var enemyName = document.getElementById("enemyName");
	var targetX = document.getElementById("targetX");
	var targetY = document.getElementById("targetY");
	xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = getGameImage;
	xmlhttp
			.open("GET",
					"/games/battleship/gameservlet?requestType=playGame&enemyName="
							+ enemyName.value + "&targetX=" + targetX.value + "&targetY="
							+ targetY.value, true);
	xmlhttp.send();
}

function loadJsonData() {
	xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = getGameImage;
	xmlhttp.open("GET",
			"/games/battleship/gameservlet?requestType=getGameImage", true);
	xmlhttp.send();
}

function getGameImage() {
	var gameImage = "";
	if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
		var responseText = xmlhttp.responseText;
		//alert(responseText);
		var json = JSON.parse(responseText);
		var status = json.status;
		if(status == "end"){
			window.location = "http://localhost:8080/games/battleship/gameplay";
			return;
		}
		var nextPlayer = json.nextPlayer;
		var helloName = document.getElementById("helloName");
		helloName.innerHTML = "Hi! "+nextPlayer;
		var gameData = json.gameData;
		for ( var player in gameData) {
			console.log("playerName: " + player);
			var playerArray = gameData[player];
			gameImage += "<span>player: " + player + "<BR>";
			for (var i = 0; i < playerArray.length; i++) {
				var row = playerArray[i];
				for (var j = 0; j < playerArray.length; j++) {
					console.log(row[j]);
					gameImage += row[j] + " ";
				}
				gameImage += "<BR>";
			}
			gameImage += "</span>";
		}
		var output = document.getElementById("output");
		output.innerHTML = gameImage;
		
		if(gameImage == ""){
			window.location = "http://localhost:8080/games/battleship/gameplay";
		}
	}
	

}
