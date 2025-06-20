package org.complainManaget.model;

public class ActiveData {
    private static ActiveData instance;
    private String user;
    private String email;
    private ActiveData(){

    }
    public static ActiveData getInstance(){
        if (instance == null){
            instance =  new ActiveData();
        }
        return instance;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
