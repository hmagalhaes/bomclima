package br.eti.hmagalhaes.bomclima.web.util.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.eti.hmagalhaes.bomclima.util.ConfigFacade;
import br.eti.hmagalhaes.bomclima.util.ConfigKey;

@ApplicationScoped
public class CryptoHelper {

	private final ConfigFacade configFacade;

	@Inject
	public CryptoHelper(ConfigFacade configFacade) {
		this.configFacade = configFacade;
	}

	public String encrypt(final String clearPass) {
		final String algorithm = configFacade.getString(ConfigKey.PASSWORD_HASH_ALGORITHM);
		try {
			final MessageDigest digest = MessageDigest.getInstance(algorithm);
			final byte[] hashBytes = digest.digest(clearPass.getBytes("UTF-8"));

			final StringBuilder sb = new StringBuilder();
			for (byte b : hashBytes) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Algorítmo de hashing inválido => " + algorithm, e);
		}
	}
}
