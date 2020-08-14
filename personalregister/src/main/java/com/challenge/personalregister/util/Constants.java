package com.challenge.personalregister.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

	public static final String NOT_FOUND = "Not Found";
	public static final String DATA_NOT_FOUND = "Nenhum registro encontrado";
	public static final String BAD_REQUEST = "Bad Request";
	public static final String DATA_EXISTS = "Registro já existe na base";
	public static final String UNAUTHORIZED = "Não autorizado";
	public static final String API_SECRET_KEY = "secret";
	public static final Long TOKEN_VALIDITY = 7200000L;

}
