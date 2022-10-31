package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;

//import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.entity.PlayerAccountInfo;
import com.example.demo.entity.TransactionInfo;
import com.example.demo.service.PlayerWalletService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WalletServiceApplicationTests {
	
	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper objectMapper;
	
	@MockBean
	PlayerWalletService service;
	
	PlayerAccountInfo player=new PlayerAccountInfo(10L,"Amudha",8901,250.00,null);
	TransactionInfo transaction=new TransactionInfo(110L,player,10.00,"credit",new java.sql.Date(System.currentTimeMillis()));
	
	/*
	 * @Autowired PlayerAccountRepository playerRepo;
	 * 
	 * @Autowired TransactionRepository transRepo;
	 */
	
	@Test
	@Order(1)
	void getCurrentBalance() throws Exception
	{
		Mockito.when(service.getPlayerById(Mockito.anyLong())).thenReturn(player);
		 RequestBuilder requestBuilder = MockMvcRequestBuilders
	                .get("/player/currentBalance/10")
	                .accept(MediaType.APPLICATION_JSON);

	        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
	        
	        String expected="Current Balance of player 10 - 250.0";
	        
	        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
	        
	        
	}
	
	@Test
	@Order(2)
	void getTransactionHistory() throws Exception
	{
		player.setTransactionDetails(List.of(transaction));
		Mockito.when(service.getPlayerById(Mockito.anyLong())).thenReturn(player);
		 RequestBuilder requestBuilder = MockMvcRequestBuilders
	                .get("/player/transactionHistory/10")
	                .accept(MediaType.APPLICATION_JSON);

	        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
	      
	   
	      assertThat(result.getResponse().getStatus()).isEqualTo(200);
	        
	  
	}

	
	@Test
	@Order(3)
	void debitTransaction() throws Exception
	{
		String status="Debit Transaction Success ";
		
		transaction.setTransactionType("debit");
	
		Mockito.when(service.debitTransaction(Mockito.any())).thenReturn(status);
		 RequestBuilder requestBuilder = MockMvcRequestBuilders
	                .post("/player/debitTransaction")
	                .accept(MediaType.APPLICATION_JSON)
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(transaction));
	        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
	      //  System.out.println("result:"+result);
	       // System.out.println(result.getResponse().getContentAsString());
	        
	       //String expected="[{transactionId:110,amount:10.0,transactionType:credit,transactionDate:2022-10-26}]";
	        
	     //  JSONAssert.assertEquals(status,result.getResponse().getContentAsString(),true);
	       assertThat(result.getResponse().getContentAsString()).isEqualTo(status);
	        
	  
	}

	@Test
	@Order(4)
	void creditTransaction() throws Exception
	{
		String status="Credit Transaction Success ";
		
		transaction.setTransactionType("credit");		
		Mockito.when(service.creditTransaction(Mockito.any())).thenReturn(status);
		 RequestBuilder requestBuilder = MockMvcRequestBuilders
	                .post("/player/creditTransaction")
	                .accept(MediaType.APPLICATION_JSON)
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(transaction));
	        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
	 //  System.out.println(result.getResponse().getContentAsString());
	       assertThat(result.getResponse().getContentAsString()).isEqualTo(status);
	        
	  
	}
	
	
}
