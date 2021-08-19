package com.finance.bank;

import com.finance.bank.logging.LoggingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankApplication {

	public static void main(String[] args) {
		LoggingContext loggingContext = new LoggingContext();

		SpringApplication.run(BankApplication.class, args);
	}

}
