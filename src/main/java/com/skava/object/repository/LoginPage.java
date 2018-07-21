package com.skava.object.repository;

import org.openqa.selenium.By;

public class LoginPage 
{	
	public static final By txtEmailId = By.id("sk_tabSignInEmailAddr_id");
	public static final By txtPassword = By.id("sk_tabSignInPassWrdInput_id");
	public static final By btnLogIn = By.className("sk_tabSignInBtn");
	public static final By lblSignInSuccess = By.className("sk_tabSignInSuccessLbl");
	public static final By loginPopUpClose = By.id("id_sk_tabMyAccountModelCloseIcon");	
}