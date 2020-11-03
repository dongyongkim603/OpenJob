package com.JohnHaney.OpenJob.Exceptions;

public class ForignKeyDeletionException extends Exception {

	public ForignKeyDeletionException() {
		super("Cannot remove target from database. Entry has a forign key that must be removed.");
	}
}
