package com.anverm.javalab.game.battleshipgame.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.anverm.javalab.game.Game;
import com.anverm.javalab.game.battleshipgame.BattleShipGame;
import com.anverm.javalab.game.battleshipgame.collection.BattleShipGameCount;
import com.anverm.javalab.game.collection.GameCount;
import com.anverm.javalab.game.exception.GameInitializationException;
import com.anverm.javalab.game.exception.GamePlayException;
import com.anverm.javalab.game.joystick.BattleShipGameBotJoyStick;
import com.anverm.javalab.game.joystick.BattleShipGameServletJoyStick;
import com.anverm.javalab.game.joystick.GameJoyStick;
import com.anverm.javalab.game.logger.GameLogger;
import com.anverm.javalab.game.logger.status.GameLoggerStatus;

/**
 * Servlet implementation class BattleShipGameServlet
 */
@WebServlet("/gameservlet")
public class BattleShipGameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger("BattleShipGameServlet");
    private GameJoyStick servlet;
    private GameJoyStick bot;
    private Game game;
	
	@Override
	public void init() throws ServletException {
		game = new BattleShipGame();
		servlet = new BattleShipGameServletJoyStick(game);
		bot = new BattleShipGameBotJoyStick(game);
		bot.setInputSouce(new Random());
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BattleShipGameServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		try {
			System.out.println("running...");
			String requestType = request.getParameter("requestType");
			if ("gameinit".equals(requestType)) {
				gameinit(request, response);

			} else if ("playerinit".equals(requestType)) {
				initPlayer(request, response);
			} else if ("battleshipinit".equals(requestType)) {
				initBattleShip(request, response);
			} else if ("playGame".equals(requestType)) {
				System.out.println("running...cool");
				playGame(request, response);
			} else if("getGameImage".equals(requestType)){
				response.setContentType("application/json");
				PrintWriter out = response.getWriter();
				out.print(getGameImage("running", (String)request.getSession().getAttribute("playerName")));
				out.flush();
			}
		} catch (GameInitializationException e) {
			session.setAttribute("errorMessage", e.getMessage());
			response.sendRedirect("gameinit");
		} catch (GamePlayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void gameinit(HttpServletRequest request, HttpServletResponse response) throws GameInitializationException{
		game = new BattleShipGame();
		servlet = new BattleShipGameServletJoyStick(game);
		bot = new BattleShipGameBotJoyStick(game);
		bot.setInputSouce(new Random());
		
		HttpSession session = null;
		try{
			servlet.setInputSouce(request);
			GameCount gameCount = servlet.generateGameInitInput();
			
			session = request.getSession();
			session.setAttribute("gameCount", gameCount);
			
			GameCount inActionCount = new BattleShipGameCount(0, 0, 0);
			session.setAttribute("inActionCount", inActionCount);
			
			session.setAttribute("humansCount", gameCount.getPlayersCount()-gameCount.getBotsCount());
			session.setAttribute("botsCount", gameCount.getBotsCount());
			session.setAttribute("battleShipCount", gameCount.getDeployablesCount());
			
			session.setAttribute("humansInAction", 0);
			session.setAttribute("botsInAction", 0);
			session.setAttribute("battleShipsInAction", 0);
			
			session.setAttribute("actionMetaData", new HashMap<String, Integer>());
			
			initBots(request, response);
			response.sendRedirect("playerinit");
		}catch (GameInitializationException e) {
			logger.log(Level.SEVERE, "NumberFormatException while initializing game", e);
			throw e;		
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Internal Server Error whilie forwarding to next page", e);
			throw new GameInitializationException("Internal Server Error whilie forwarding to next page", e);
		} 
	}

	private void initPlayer(HttpServletRequest request, HttpServletResponse response) throws GameInitializationException{
		String playerName = null;
		HttpSession session = null;
		try{
			session = request.getSession();
			GameCount gameCount = (GameCount) session.getAttribute("gameCount");
			GameCount inActionCount = (GameCount) session.getAttribute("inActionCount");
			if (gameCount == null || inActionCount == null) {
				throw new GameInitializationException("Game was not initialized. Probably the page was accessed out of transaction");
			}
			
			int humansCount = gameCount.getHumansCount();
			int humansInAction = inActionCount.getHumansCount();

			//Initialise player if one is not initialise yet other wise redirect to 
			//game play page.
			if (humansInAction < humansCount) {
				//Human player name initialisation.
				servlet.setInputSouce(request);
				playerName = servlet.generatePlayerInitInput();
				servlet.generatePlatformInitInput(playerName);
				humansInAction += 1;
				inActionCount.setHumansCount(humansInAction);
				session.setAttribute("inActionCount", inActionCount);
			} else {
				response.sendRedirect("gameplay");
			}

			@SuppressWarnings("unchecked")
			Map<String, Integer> actionMetaData = (Map<String, Integer>) session.getAttribute("actionMetaData");
			
			//Initialise all players with zero battleships deployed.
			actionMetaData.put(playerName, 0);
			session.setAttribute("actionMetaData", actionMetaData);
			
			System.out.println("playerName:"+playerName);

			session.setAttribute("playerName", playerName);
			response.sendRedirect("battleshipinit");
		}catch(GameInitializationException e){
			throw e;
		} catch (IOException e) {
			throw new GameInitializationException("Internal Server error whilie preparing for battleships input", e);
		}
	}
	
	private void initBattleShip(HttpServletRequest request, HttpServletResponse response) throws GameInitializationException {
		// player name known
		// initialise battleship for name till limit reached.
		// once limit is reached, forward to player init page, if humansInAction
		// not reached limit.
		HttpSession session = request.getSession();
		GameCount gameCount = (GameCount) session.getAttribute("gameCount");
		GameCount inActionCount = (GameCount) session.getAttribute("inActionCount");
		
		int humansCount = gameCount.getHumansCount();
		int battleShipsCount = gameCount.getDeployablesCount();
		int humansInAction = inActionCount.getHumansCount();
		int battleShipsInAction = inActionCount.getDeployablesCount();
		
		String playerName = (String) session.getAttribute("playerName");
		try {
			servlet.setInputSouce(request);
			servlet.generateDeployableInitInput(playerName);
			battleShipsInAction += 1;
			inActionCount.setDeployablesCount(battleShipsInAction);
			session.setAttribute("inActionCount", inActionCount);
			System.out.println("here is player name: " + playerName);

			if (battleShipsInAction < battleShipsCount) {
				response.sendRedirect("battleshipinit");
			} else if (humansInAction < humansCount) {
				inActionCount.setDeployablesCount(0);
				session.setAttribute("inActionCount", inActionCount);
				response.sendRedirect("playerinit");
			} else {
				Map<String, Integer> actionMetaData = (Map<String, Integer>) session.getAttribute("actionMetaData");
				actionMetaData.put(playerName, battleShipsInAction);
				session.setAttribute("actionMetaData", actionMetaData);
				response.sendRedirect("gameplay");
			}
		} catch (GameInitializationException e) {
			session.setAttribute("errorMessage", e.getMessage());
			try {
				response.sendRedirect("battleshipinit");
			} catch (IOException e1) {
				throw new GameInitializationException("Internal Server error while battleship initialisation exception: "+e.getMessage(), e1);
			}
		} catch (IOException e) {
			throw new GameInitializationException("Internal Server error whilie preparing for battleships input", e);
		}
	}
	
	private void playGame(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, GamePlayException{
		//TODO: implementation pending.
		System.out.println("running...playing");
		String status = null;
		String nextPlayer = null;
		HttpSession session = request.getSession();
		//session.setAttribute("gameImage", arg1);
		String playerName = (String) session.getAttribute("playerName");
		Map<String, Integer> actionMetaData = (Map<String, Integer>) session.getAttribute("actionMetaData");
		
		servlet.setInputSouce(request);
		GameLogger gameLogger = servlet.generateGamePlayInput(playerName);

		if(gameLogger.getStatus() == GameLoggerStatus.END){
			session.setAttribute("playerName", gameLogger.getWinner());
			nextPlayer = gameLogger.getNextPlayer();
			status = "end";
			session.setAttribute("gameStatus", "end");
		}else if("bot".equals(gameLogger.getNextPlayer())){
			//Considering only one bot allowed in game.
			gameLogger = bot.generateGamePlayInput(gameLogger.getNextPlayer());
			session.setAttribute("playerName", gameLogger.getNextPlayer());
			nextPlayer = gameLogger.getNextPlayer();
			status = "running";
		}else if(gameLogger.getStatus() == GameLoggerStatus.END){
			session.setAttribute("playerName", gameLogger.getWinner());
			nextPlayer = gameLogger.getNextPlayer();
			status = "end";
			session.setAttribute("gameStatus", "end");
		} else{
			session.setAttribute("playerName", gameLogger.getNextPlayer());
			nextPlayer = gameLogger.getNextPlayer();
			status = "running";
			session.setAttribute("gameStatus", "running");
		}
		
		session.setAttribute("actionMetaData", actionMetaData);
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(getGameImage(status, nextPlayer));
		out.flush();		
		//actionMetaData.put(playerName, battleShipsInAction);
	}
	
	/**
	 * Player wise platForm image contains following characters. 
	 * V: Vacant
	 * D: Deployed
	 * A: Affected
	 * M: Miss Hit
	 * @param status TODO
	 * @param nextPlayer TODO
	 */
	private JSONObject getGameImage(String status, String nextPlayer){
		Map<String, Character[][]> gameImage = game.getGameImage();
		JSONObject gameJson = new JSONObject(); 
		
		Iterator<Entry<String, Character[][]>> it = gameImage.entrySet().iterator();
		
		while(it.hasNext()){
			Entry<String, Character[][]> entry = it.next();
			String playerName = entry.getKey();
			Character[][] platFormImage = entry.getValue();
			int size = platFormImage[0].length;
			JSONArray rows = new JSONArray();
			for(int i = 0; i<size; i++){
				JSONArray columns = new JSONArray();
				for(int j = 0; j<size; j++){
					columns.add(platFormImage[i][j]);
					System.out.print(platFormImage[i][j]);
				}
				System.out.println("");
				rows.add(columns);
			}
			gameJson.put(playerName, rows);
		}
		
		JSONObject data = new JSONObject();
		data.put("status", status);
		data.put("nextPlayer", nextPlayer);
		data.put("gameData", gameJson);
		return data;
	}

	private void initBots(HttpServletRequest request, HttpServletResponse response) throws GameInitializationException {
		HttpSession session = request.getSession();
		GameCount gameCount = (GameCount) session.getAttribute("gameCount");
		
		@SuppressWarnings("unchecked")
		Map<String, Integer> actionMetaData = (Map<String, Integer>) session.getAttribute("actionMetaData");
		int botsCount = gameCount.getBotsCount();
		int battleShipsCount = gameCount.getDeployablesCount();
		for (int i = 0; i < botsCount; i++) {
			String playerName = bot.generatePlayerInitInput();
			bot.generatePlatformInitInput(playerName);
			for(int j = 0; j<battleShipsCount; j++){
				bot.generateDeployableInitInput(playerName);
			}
			actionMetaData.put(playerName, battleShipsCount);
		}
		session.setAttribute("actionMetaData", actionMetaData);
	}
	
}
