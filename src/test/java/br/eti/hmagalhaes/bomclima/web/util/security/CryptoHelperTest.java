package br.eti.hmagalhaes.bomclima.web.util.security;

import static org.junit.Assert.*;
import org.junit.Test;

import br.eti.hmagalhaes.bomclima.util.ConfigFacade;
import br.eti.hmagalhaes.bomclima.util.ConfigKey;

import static org.mockito.Mockito.*;

public class CryptoHelperTest {

	@Test
	public void testEncryptWithSHA256() {
		final ConfigFacade configFacadeMock = mock(ConfigFacade.class);
		when(configFacadeMock.getString(ConfigKey.PASSWORD_HASH_ALGORITHM)).thenReturn("SHA-256");

		final CryptoHelper helper = new CryptoHelper(configFacadeMock);

		assertEquals("a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3", helper.encrypt("123"));
	}

	@Test
	public void testEncryptWithSHA1() {
		final ConfigFacade configFacadeMock = mock(ConfigFacade.class);
		when(configFacadeMock.getString(ConfigKey.PASSWORD_HASH_ALGORITHM)).thenReturn("SHA-1");

		final CryptoHelper helper = new CryptoHelper(configFacadeMock);

		assertEquals("40bd001563085fc35165329ea1ff5c5ecbdbbeef", helper.encrypt("123"));
	}
}
