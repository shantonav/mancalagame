package com.example.mancala.gamemancala.repository;

import com.example.mancala.gamemancala.entity.MancalaGameEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by shantonav on 07/08/2018.
 */
public interface GameRepository extends CrudRepository<MancalaGameEntity,Integer> {
}
