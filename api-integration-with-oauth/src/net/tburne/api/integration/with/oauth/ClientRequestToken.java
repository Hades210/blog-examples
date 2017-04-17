package net.tburne.api.integration.with.oauth;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;

public class ClientRequestToken {

	public static void main(String[] args) {
		try {
			ServiceBuilder builder = new ServiceBuilder();
			OAuth20Service service = builder.apiKey("654832567742-6udg14sfuif69g84c78ss3s3msdlf33l.apps.googleusercontent.com").apiSecret("SDFwrgfsdSeQnFtSDFSDFzHd").callback("https://tburne.net/oauth/index.html").build(GoogleApi20.instance());
			OAuth2AccessToken tokens = service.getAccessToken("8/-rgkcuBQUIDFSDdsss_xsuErf4KIYId7KSDFCCSse5NUYc#");
			System.out.println("Access token is " + tokens.getAccessToken());
			System.out.println("Refresh token is " + tokens.getRefreshToken());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
	}
	
}
