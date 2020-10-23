package com.opentext.otmm.sc.jobmodeler.helpers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.artesia.common.exception.BaseTeamsException;
import com.artesia.security.SecuritySession;
import com.artesia.security.session.services.LocalAuthenticationServices;

public class SecurityHelper {
	private static Log log = LogFactory.getLog(SecurityHelper.class);

	public static SecuritySession getAdminSession() {
		SecuritySession session = null;
		try {
			session = LocalAuthenticationServices.getInstance().createLocalSession("tsuper");
		} catch (BaseTeamsException e) {
			log.error("Error while trying to create a SecuritySession for \"tsuper\": " + e.getStackTraceString());
		}
		return session;
	}

	public static SecuritySession getUserSession(String userName) {
		SecuritySession session = null;
		try {
			log.info("Trying to create a session for " + userName);
			session = LocalAuthenticationServices.getInstance().createLocalSession(userName);
		} catch (BaseTeamsException e) {
			log.error("Error while trying to create a SecuritySession for \"" + userName + "\": "
					+ e.getStackTraceString());
		}
		return session;
	}
}
