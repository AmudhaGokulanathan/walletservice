package com.example.demo.controller;

import java.util.List;

import org.hibernate.exception.LockAcquisitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.entity.PlayerAccountInfo;
import com.example.demo.entity.TransactionInfo;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.PlayerWalletService;

@RestController
@RequestMapping("")
public class WalletServiceController {

	@Autowired
	PlayerWalletService service;

	@GetMapping(path = "/players",produces= {"application/json"})
		public ResponseEntity<List<PlayerAccountInfo>> findPlayerDetails() {
		List<PlayerAccountInfo> players=service.getPlayers();
		
		
		return new ResponseEntity<>(players,HttpStatus.OK);
	}
	
	@PostMapping(path="/player",produces= {"application/json"},consumes= {"application/json"})
	public ResponseEntity<PlayerAccountInfo> addPlayer(@RequestBody PlayerAccountInfo player)
	{
		PlayerAccountInfo newPlayer= service.addPlayer(player);
		return new ResponseEntity<>(newPlayer,HttpStatus.CREATED);
		
	}
	
	@GetMapping(path = "/player/currentBalance/{id}",produces= {"application/json"})
	public ResponseEntity<String> findPlayerBalance(@PathVariable("id") Long id) throws ResourceNotFoundException {
		PlayerAccountInfo player=service.getPlayerById(id);
				
	return new ResponseEntity<>("Current Balance of player "+id+" - "+player.getBalance(),HttpStatus.OK);
}
	
	@GetMapping(path = "/player/transactionHistory/{id}",produces= {"application/json"})
	public ResponseEntity<List<TransactionInfo>> findTransactionHistory(@PathVariable("id") Long id) throws ResourceNotFoundException {
		PlayerAccountInfo player=service.getPlayerById(id);
				
	return new ResponseEntity<>(player.getTransactionDetails(),HttpStatus.OK);
}
	
	@PostMapping(path = "/player/creditTransaction",consumes= {"application/json"},produces= {"application/json"})
	public ResponseEntity<String> creditTransaction( @RequestBody TransactionInfo transInfo) throws Exception {
		
		String status=null;
		if(transInfo!=null) {
			try {
		 status=service.creditTransaction(transInfo);
			}
			catch(Exception e)
			{
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Pls. try again after sometime. ");
			}
			
		}
		else
		{
			throw new ResponseStatusException(HttpStatus.CONFLICT,"Transaction Information cannot be null");
		}
		
	return new ResponseEntity<>(status,HttpStatus.OK);
}
	
	@PostMapping(path = "/player/debitTransaction",consumes= {"application/json"},produces= {"application/json"})
	public ResponseEntity<String> debitTransaction( @RequestBody TransactionInfo transInfo) throws Exception {
		
		String status=null;
		if(transInfo!=null) {
			try {
				 status=service.debitTransaction(transInfo);
					}
					catch(Exception e)
					{
						throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Pls. try again after sometime. ");
					}
				}
		else
		{
			throw new ResponseStatusException(HttpStatus.CONFLICT,"Transaction Information cannot be null");
		}
		
	return new ResponseEntity<>(status,HttpStatus.OK);
}
	
}
