package com.example.uikingofswampfinal.data.service;

import com.example.uikingofswampfinal.data.entity.SamplePlayer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SamplePlayerService {
	
	private final SamplePersonRepository repository;
	
	public SamplePlayerService(SamplePersonRepository repository) {
		this.repository = repository;
	}
	
	public Optional<SamplePlayer> get(Long id) {
		return repository.findById(id);
	}
	
	public SamplePlayer update(SamplePlayer entity) {
		return repository.save(entity);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public Page<SamplePlayer> list(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	public Page<SamplePlayer> list(Pageable pageable, Specification<SamplePlayer> filter) {
		return repository.findAll(filter, pageable);
	}
	
	public int count() {
		return (int) repository.count();
	}
	
}
