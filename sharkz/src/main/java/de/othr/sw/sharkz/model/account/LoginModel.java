package de.othr.sw.sharkz.model.account;

import de.othr.sw.sharkz.entity.Account;
import de.othr.sw.sharkz.entity.Administrator;
import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.model.insertion.CreateConversationModel;
import de.othr.sw.sharkz.service.AccountService;
import de.othr.sw.sharkz.service.InsertionService;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@ViewScoped
@Named("login")
public class LoginModel implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="Attributes">
    
    Account account;
    
    // Form inputs
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    
    // For messages
    FacesContext context;
    
    // Register/Login toggle fields
    private String loginButtonText = "Login";
    private String registerButtonText = "Registrieren";
    private boolean isLogin = true;
    
    // If login is required from insertion/create.xhtml
    private Insertion insertion;
    
    // Models & Services
    @Inject private InsertionService insertionService;
    @Inject private AccountService accountService;
    @Inject private AccountModel accountModel;
    @Inject private Logger logger;
    @Inject private CreateConversationModel createModel;

    //</editor-fold>
    
    /**
     * Login to the sharkz website.
     * @return 
     */
    public String login() {
        
        // Validate the form data
        if (!validateData())
            return "login";
        
        // Process is a login
        if (isLogin) {
            if (account.geteMail() != null && 
                    accountService.checkPassword(account.geteMail(), password)) {
                accountModel.setIsLoggedIn(true);
                accountModel.setUser(account);
            } else {
                context.addMessage(null, new FacesMessage(
                    "Falsche E-Mail oder falsches Passwort!"));
                return "";
            }
        }
        
        // Process is a registration
        else if (!isLogin) {
            createCustomer();
            
            accountModel.setIsLoggedIn(true);
            accountModel.setUser(account);
        }
        
        // Set name depending on type of account
        if (account instanceof Customer) {
            accountModel.setName(accountService.getNameByID(
                    account.getID()));
            
            accountModel.init();
            
        } else if (account instanceof Administrator) {
            accountModel.setName("Administrator");
        }
        
        logger.info("User with ID " + account.getID() + " " + this.lastName
                + " logged in!");
        
        // Normal login
        if (createModel.getConversation().isTransient())
            return "index";
        
        else
        
        return "upload";
    }
    
    /**
     * Validates if the login data is correct.
     * @return <b>true</b> if data is correct
     *         <b>false</b> otherwise
     */
    public boolean validateData() {
        context = FacesContext.getCurrentInstance();
        
        validateEmail();
        validatePassword();
        
        return context.getMessageList().isEmpty();
    }
    
    /**
     * Creates and persists a new customer
     */
    private void createCustomer() {
        Customer customer = new Customer();
            
        customer.seteMail(email);
        customer.setPassword(password);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);

        System.out.println("Kunde angelegt");
        System.out.println(customer.getFirstName() + " " + customer.getLastName());

        long customerId = accountService.createCustomer(customer);
        
        account = accountService.findCustomer(customerId);
    }

    /**
     * Validates the email entered for login or registration
     */
    private void validateEmail() {
        if (email == null || email.equals("")) {
            context.addMessage(null, new FacesMessage(
                    "Bitte tragen Sie Ihre E-Mail-Addresse ein!"));
        }
        
        // Login: If email does not exist -> account does not exist
        if (isLogin) {
            account = accountService.getAccountByEmail(email);
            if (account == null)
                context.addMessage(null, new FacesMessage(
                        "Falsche E-Mail oder falsches Passwort!"));
            
        // Registration: If account exists -> choose other email
        } else {
            
                account = accountService.getAccountByEmail(email);
            
            if (account != null)
                context.addMessage(null, new FacesMessage(
                        "Diese E-Mail Addresse ist bereits registriert!"));
        }
    }
    
    /**
     * Validates if a password chosen for registration meets the password
     * criteria
     */
    private void validatePassword() {
        // No password entered
        if (password == null || password.equals("")) {
            context.addMessage(null, new FacesMessage(
                    "Bitte tragen Sie Ihr Passwort ein!"));
        }

        if (!isLogin) {
            // Other unfullfilled criteria
            if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{5,}$"))
                context.addMessage(null, new FacesMessage(
                        "Ihr Passwort muss aus mindestens 5 Zeichen, "
                                + "Groß- und Kleinbuchstaben und Sonderzeichen "
                                + "(@#$%^&+=) bestehen und darf keinen "
                                + "Whitespace enthalten!"));
        }
    }
    
    /**
     * Toggles between login and registration functionality.
     */
    public void toggleAction() {
        if (isLogin) {
            loginButtonText = "Registrieren";
            registerButtonText = "Einloggen";
        } else {
            loginButtonText = "Login";
            registerButtonText = "Registrieren";
        }
        
        isLogin = !isLogin;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getter & Setter">
    public String getEmail() {
    return email;
    }

    public void setEmail(String email) {
    this.email = email;
    }

    public String getPassword() {
    return password;
    }

    public void setPassword(String password) {
    this.password = password;
    }

    public String getLoginButtonText() {
    return loginButtonText;
    }

    public void setLoginButtonText(String loginButtonText) {
    this.loginButtonText = loginButtonText;
    }

    public String getRegisterButtonText() {
    return registerButtonText;
    }

    public void setRegisterButtonText(String registerButtonText) {
    this.registerButtonText = registerButtonText;
    }

    public boolean isLoginAction() {
    return isLogin;
    }

    public void setLoginAction(boolean loginAction) {
    this.isLogin = loginAction;
    }

    public String getFirstName() {
    return firstName;
    }

    public void setFirstName(String firstName) {
    this.firstName = firstName;
    }

    public String getLastName() {
    return lastName;
    }

    public void setLastName(String lastName) {
    this.lastName = lastName;
    }

    public boolean isIsLogin() {
    return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
    this.isLogin = isLogin;
    }
    
     public Insertion getInsertion() {
        return insertion;
    }

    public void setInsertion(Insertion insertion) {
        this.insertion = insertion;
    }
    //</editor-fold>

   
}
