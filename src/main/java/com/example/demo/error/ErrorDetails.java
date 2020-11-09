package com.example.demo.error;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ErrorDetails {
	 private String title;
	 private int status;
	 private String detail;
	 private OffsetDateTime timestamp;
	 private String developerMessage;
}
