package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.entity.PlayerAccountInfo;
import com.example.demo.entity.TransactionInfo;
import com.example.demo.repository.PlayerAccountRepository;
import com.example.demo.repository.TransactionRepository;

@Service
@Transactional
public class PlayerWalletService {
	@Autowired
	PlayerAccountRepository accountRepo;
	@Autowired
	TransactionRepository transactionRepo;
	
	@Transactional(propagation =Propagation.REQUIRES_NEW )
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public PlayerAccountInfo addPlayer(PlayerAccountInfo player)
	{
		PlayerAccountInfo newPlayer=null;
		
		if(player!=null) {
			if( player.getTransactionDetails()!=null && player.getTransactionDetails().size()>0)
				player.getTransactionDetails().forEach(transaction-> checkTransactionExist(transaction.getTransactionId()));
		newPlayer=accountRepo.save(player);
		}
		return  newPlayer ;
	}
	
/*	public TransactionInfo addtransaction(TransactionInfo transaction)
	{
		if(transaction!=null)
			checkTransactionExist(transaction.transactionId);
		if(transaction.player!=null )
			 getPlayerById(transaction.player.id);
		return transactionRepo.save(transaction);
	}
	
	public void deletePlayer(Long playerId)
	{
		accountRepo.deleteById(playerId);
	}
	
	
	public void deleteTransaction(Long transactionId)
	{
		
		transactionRepo.deleteById(transactionId);
		//transactionRepo.flush();
	}
*/
	
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED)
	public List<PlayerAccountInfo> getPlayers() {

		return accountRepo.findAll();
	}

	/*public List<TransactionInfo> getTransactions() {
		return transactionRepo.findAll();
	}
	*/

	@Transactional(propagation=Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ)
	public	PlayerAccountInfo getPlayerById(Long id) throws ResponseStatusException {
		Optional<PlayerAccountInfo> optionalPlayer = accountRepo.findById(id);
		PlayerAccountInfo player = optionalPlayer.orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found with this id:" + id));

		return player;
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW )
		@Lock(LockModeType.PESSIMISTIC_WRITE)
	public String debitTransaction(TransactionInfo transInfo) throws Exception {
	
		Long playerId = transInfo.getPlayer().getId();
		PlayerAccountInfo player = null;
		if (playerId != null) {
			player = getPlayerById(playerId);
		}
		checkTransactionExist(transInfo.getTransactionId());

		if (player.getBalance() >= transInfo.getAmount()) {
		
			player.setBalance(player.getBalance() - transInfo.getAmount());
			
			transInfo.setPlayer(player);
			transactionRepo.save(transInfo);

		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient Balance with player -" + player.getId());
		}

		return "Debit Transaction Success";
	}

	//@Transactional(propagation=Propagation.REQUIRED,isolation  = Isolation.REPEATABLE_READ)
	private void checkTransactionExist(Long id) {
		Optional<TransactionInfo> oldTrans = transactionRepo.findById(id);
		if (oldTrans.isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " Transaction_Id must be unique "+oldTrans.get().getTransactionId());
	}

	@Transactional(propagation =Propagation.REQUIRES_NEW )
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public String creditTransaction(TransactionInfo transInfo) throws Exception {

		Long playerId = transInfo.getPlayer().getId();
		PlayerAccountInfo player = null;
		if (playerId != null) {
			player = getPlayerById(playerId);
		}

		checkTransactionExist(transInfo.getTransactionId());
		
		player.setBalance(player.getBalance() + transInfo.getAmount());
		
		transInfo.setPlayer(player);
		transactionRepo.save(transInfo);

		return "Credit Transaction Success";
	}
	
	
}
