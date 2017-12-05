package com.gismat.test.service.shared;

import com.gismat.test.config.firebase.FirebaseTokenHolder;
import com.gismat.test.service.exception.FirebaseTokenException;
import com.gismat.test.service.exception.FirebaseTokenExceptionMessages;
import com.gismat.test.service.exception.FirebaseTokenInvalidException;
import com.gismat.test.web.rest.util.StringUtil;
import com.google.api.core.ApiFuture;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.tasks.Task;
import com.google.firebase.tasks.Tasks;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.ExecutionException;


public class FirebaseParser {

    @Async
	public FirebaseTokenHolder parseToken(String idToken)  {
		if (StringUtil.isBlank(idToken)) {
			throw new FirebaseTokenException(FirebaseTokenExceptionMessages.TOKEN_HEADER_NOT_FOUND);
		}else{
            FirebaseToken decodedToken = null;
            try {
                if(idToken!=null)
                {
                    // todo catch token expired error
                    // TODO: 05.12.2017 catch token is not valid
                    decodedToken = FirebaseAuth.getInstance().verifyIdTokenAsync(idToken).get();
                }
                else
                {
                    throw new FirebaseException("Token is null");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (FirebaseException e) {
                e.printStackTrace();
            }
            return new FirebaseTokenHolder(decodedToken);
        }

	}
}
