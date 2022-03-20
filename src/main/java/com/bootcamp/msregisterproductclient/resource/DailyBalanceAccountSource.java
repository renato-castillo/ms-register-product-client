package com.bootcamp.msregisterproductclient.resource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bootcamp.msregisterproductclient.dto.DailyBalanceDto;
import com.bootcamp.msregisterproductclient.dto.PersonClientAccountDto;
import com.bootcamp.msregisterproductclient.entity.DailyBalanceAccount;
import com.bootcamp.msregisterproductclient.entity.PersonClientAccount;
import com.bootcamp.msregisterproductclient.service.IDailyBalanceAccountImpl;
import com.bootcamp.msregisterproductclient.service.PersonClientAccountServiceImpl;
import com.bootcamp.msregisterproductclient.util.MapperUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DailyBalanceAccountSource extends MapperUtil{
	
	@Autowired
	private IDailyBalanceAccountImpl dailyBalanceAccountServiceImpl;
	
	@Autowired
	private PersonClientAccountServiceImpl personClientServiceImpl;
	
//	crea balances diarios y los guarda
	public Mono<DailyBalanceDto> createDailyBalance( PersonClientAccountDto personclientdto){
		PersonClientAccount personSelect = map(personClientServiceImpl.findById(personclientdto.getId()), PersonClientAccount.class);
		DailyBalanceAccount db = new DailyBalanceAccount();
		db.setCreatedAt(LocalDateTime.now());
		db.setBalanceDaily(personclientdto.getBalance());
		db.setPersonClientAccount(personSelect);
		db.setUpdatedAt(LocalDateTime.now());
		return dailyBalanceAccountServiceImpl.save(db).map(dba -> map(dba, DailyBalanceDto.class));
	}
//	obtiene el flujo de balances diarios tomando como inicio el primero del mes actual y la fecha actual
	public Flux<DailyBalanceDto> getMonthlyBalance(){
		
		return dailyBalanceAccountServiceImpl.findAll()
				.filter(dba -> dba.getCreatedAt().isBefore(
						LocalDateTime.now()) && 
						dba.getCreatedAt().isAfter(LocalDateTime.now().withDayOfMonth(1))
						)
				.map(d -> map(d, DailyBalanceDto.class));
	}
//	calcula el saldo promedio desde el inicio del presente mes hasta la fecha actual
	public Mono<Float> getAverageMonthlyBalance(Flux<DailyBalanceDto> fluxDailysBalance){
		float sum = 0f;
		fluxDailysBalance.map(c -> c.getBalanceDaily() + sum);
		return  Mono.just(sum/LocalDateTime.now().getDayOfMonth());
	}

}
