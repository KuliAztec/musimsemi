package com.example.demo.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.demo.Model.Player;
import com.example.demo.Service.PlayerService;

@RestController
@RequestMapping("/api/players")
public class PlayerController {
    
    private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);
    
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public ResponseEntity<List<Player>> getPlayers() {
        List<Player> players = playerService.getPlayers();
        if (players.isEmpty()) {
            logger.info("GET /api/players returned an empty list");
        } else {
            logger.info("GET /api/players returned {} players", players.size());
        }
        return ResponseEntity.ok(players);
    }

    @GetMapping("/team/{team}")
    public ResponseEntity<List<Player>> getPlayersByTeam(@PathVariable String team) {
        List<Player> players = playerService.getPlayersByTeam(team);
        if (players.isEmpty()) {
            logger.info("GET /api/players/team/{} returned an empty list", team);
            return ResponseEntity.ok(players);  // Changed from notFound() to ok()
        }
        logger.info("GET /api/players/team/{} returned {} players", team, players.size());
        return ResponseEntity.ok(players);
    }

    @GetMapping("/position/{position}")  // Changed from "/api/position/{position}"
    public ResponseEntity<List<Player>> getPlayersByPosition(@PathVariable String position) {
        List<Player> players = playerService.getPlayersByPosition(position);
        if (players.isEmpty()) {
            logger.info("GET /api/players/position/{} returned an empty list", position);
            return ResponseEntity.notFound().build();
        }
        logger.info("GET /api/players/position/{} returned {} players", position, players.size());
        return ResponseEntity.ok(players);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<Player>> searchPlayers(@PathVariable String keyword) {
        List<Player> players = playerService.searchPlayers(keyword);
        logger.info("GET /api/players/search/{} returned {} players", keyword, players.size());
        return ResponseEntity.ok(players);
    }

    @PostMapping
    public ResponseEntity<Player> addPlayer(@RequestBody Player player) {
        Player savedPlayer = playerService.addPlayer(player);
        return ResponseEntity.ok(savedPlayer);
    }

    @PutMapping("/{name}")
    public ResponseEntity<Player> updatePlayer(@PathVariable String name, @RequestBody Player playerNew) {
        Player player = playerService.updatePlayer(playerNew);
        if (player == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(player);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deletePlayer(@PathVariable String name) {
        playerService.deletePlayer(name);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        logger.info("Test endpoint called");
        return ResponseEntity.ok("API is working!");
    }

    @GetMapping("/top/{count}")
    public ResponseEntity<List<Player>> getTopPlayers(
        @PathVariable int count,
        @RequestParam String sortBy) {
        List<Player> players = playerService.getTopPlayers(count, sortBy);
        logger.info("GET /api/players/top/{}?sortBy={} returned top players", count, sortBy);
        return ResponseEntity.ok(players);
    }
}
