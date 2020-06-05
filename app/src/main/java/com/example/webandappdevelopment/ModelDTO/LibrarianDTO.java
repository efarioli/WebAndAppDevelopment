package com.example.webandappdevelopment.ModelDTO;

import java.io.Serializable;

public class LibrarianDTO implements Serializable
{
    private final int id;
    private final String name;
    private final String username;
    private final String password;

    public LibrarianDTO(int id, String name, String username, String password)
    {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getUsername()
    {
        return username;
    }

    public boolean passwordMatches(String pwd)
    {
        return password.equals(pwd);
    }
}
