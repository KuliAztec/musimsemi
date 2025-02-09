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
    @Transactional
    public void deletePlayer(String name) {
        playerRepository.deleteByName(name);
    }
}