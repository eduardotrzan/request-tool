package com.request.tool.model.service.interfaces;

import java.util.Locale;

import javax.mail.MessagingException;

public interface EmailService {
	
	void sendTextMail(final String recipientName, final String recipientEmail, final Locale locale) throws MessagingException;

}