package com.bootcamp.msregisterproductclient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bootcamp.msregisterproductclient.entity.DailyBalanceAccount;
import com.bootcamp.msregisterproductclient.repository.IDailyBalanceAccountRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class IDailyBalanceAccountImpl implements IDailyBalanceService{
	
	@Autowired
	private IDailyBalanceAccountRepository dailyBalanceAccountRepo;

	@Override
	public Mono<DailyBalanceAccount> save(DailyBalanceAccount t) {
		// TODO Auto-generated method stub
		return dailyBalanceAccountRepo.save(t);
	}

	@Override
	public Mono<Void> deleteById(String v) {
		// TODO Auto-generated method stub
		return dailyBalanceAccountRepo.deleteById(v);
	}

	@Override
	public Mono<DailyBalanceAccount> findById(String v) {
		// TODO Auto-generated method stub
		return dailyBalanceAccountRepo.findById(v);
	}

	@Override
	public Flux<DailyBalanceAccount> findAll() {
		// TODO Auto-generated method stub
		return dailyBalanceAccountRepo.findAll();
	}

}
