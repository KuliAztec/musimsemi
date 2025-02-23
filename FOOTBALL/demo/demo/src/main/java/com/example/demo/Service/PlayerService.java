package com.example.demo.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.Repository.PlayerRepository;

import jakarta.transaction.Transactional;

import com.example.demo.Model.Player;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getPlayers() {
        return playerRepository.findAll();
    }

    public List<Player> getPlayersByTeam(String team) {
        return playerRepository.findByTeam(team);
    }

    public List<Player> getPlayersByPosition(String position) {
        return playerRepository.findByPosition(position);
    }

    public List<Player> searchPlayers(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            return playerRepository.findAll();
        }
        
        // Convert search text to lowercase for case-insensitive search
        String searchLower = searchText.toLowerCase();
        
        return playerRepository.findAll().stream()
            .filter(player -> 
                player.getName().toLowerCase().contains(searchLower) ||
                player.getTeam().toLowerCase().contains(searchLower) ||
                player.getPosition().toLowerCase().contains(searchLower))
            .collect(Collectors.toList());
    }

    public Player addPlayer(Player player) {
        return playerRepository.save(player);
    }

    public Player updatePlayer (Player Playernew){
        Optional<Player> playerthen = playerRepository.findByName(Playernew.getName());

        if(playerthen.isPresent()){
            Player theChosenOne = playerthen.get();
            theChosenOne.setNation(Playernew.getNation());
            theChosenOne.setPosition(Playernew.getPosition());
            theChosenOne.setAge(Playernew.getAge());
            theChosenOne.setMatch_Played(Playernew.getMatch_Played());
            theChosenOne.setStarts(Playernew.getStarts());
            theChosenOne.setMinutes(Playernew.getMinutes());
            theChosenOne.setGoals(Playernew.getGoals());
            theChosenOne.setAssist(Playernew.getAssist());
            theChosenOne.setPenalties(Playernew.getPenalties());
            theChosenOne.setCardYellow(Playernew.getCardYellow());
            theChosenOne.setCardRed(Playernew.getCardRed());
            theChosenOne.setxG(Playernew.getxG());
            theChosenOne.setxAG(Playernew.getxAG());
            theChosenOne.setTeam(Playernew.getTeam());

            return playerRepository.save(theChosenOne);

        } else {
            return null;
        }
    }

    // Add the new method for retrieving top players sorted by a numeric field in descending order
    public List<Player> getTopPlayers(int count, String sortBy) {
        return playerRepository.findAll().stream()
            .sorted((p1, p2) -> {
                switch(sortBy) {
                    case "age":
                        return Integer.compare(p2.getAge(), p1.getAge());
                    case "match_Played":
                        return Integer.compare(p2.getMatch_Played(), p1.getMatch_Played());
                    case "starts":
                        return Integer.compare(p2.getStarts(), p1.getStarts());
                    case "minutes":
                        return Integer.compare(p2.getMinutes(), p1.getMinutes());
                    case "goals":
                        return Integer.compare(p2.getGoals(), p1.getGoals());
                    case "assist":
                        return Integer.compare(p2.getAssist(), p1.getAssist());
                    case "penalties":
                        return Integer.compare(p2.getPenalties(), p1.getPenalties());
                    case "cardYellow":
                        return Integer.compare(p2.getCardYellow(), p1.getCardYellow());
                    case "cardRed":
                        return Integer.compare(p2.getCardRed(), p1.getCardRed());
                    case "xG":
                        return Double.compare(p2.getxG(), p1.getxG());
                    case "xAG":
                        return Double.compare(p2.getxAG(), p1.getxAG());
                    default:
                        return 0;
                }
            })
            .limit(count)
            .collect(Collectors.toList());
    }

    @Transactional
    public void deletePlayer(String name) {
        playerRepository.deleteByName(name);
    }
}