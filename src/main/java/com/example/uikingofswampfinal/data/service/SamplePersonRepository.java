package com.example.uikingofswampfinal.data.service;

import com.example.uikingofswampfinal.data.entity.SamplePlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SamplePersonRepository
		extends
		JpaRepository<SamplePlayer, Long>,
		JpaSpecificationExecutor<SamplePlayer> {
	
}
