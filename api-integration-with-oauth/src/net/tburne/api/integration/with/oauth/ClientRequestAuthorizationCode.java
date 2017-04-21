package net.tburne.api.integration.with.oauth;

import java.util.Hashtable;
import java.util.UUID;

import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;

public class ClientRequestAuthorizationCode {

	public static void main(String[] args) {
		ServiceBuilder builder = new ServiceBuilder();
		UUID state = UUID.randomUUID();
		OAuth20Service service = builder.state(state.toString()).scope("https://www.googleapis.com/auth/gmail.send").apiKey("654832567742-6udg14sfuif69g84c78ss3s3msdlf33l.apps.googleusercontent.com").apiSecret("SDFwrgfsdSeQnFtSDFSDFzHd").callback("https://tburne.net/oauth/index.html").build(GoogleApi20.instance());
		Hashtable<String, String> params = new Hashtable<String, String>();
		params.put("access_type", "offline");
		params.put("approval_prompt", "force");
		System.out.println("Redirect Resource Owner to " + service.getAuthorizationUrl(params) + " with state " + state.toString());	
	}
	
}
