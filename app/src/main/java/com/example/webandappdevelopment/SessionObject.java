package com.example.webandappdevelopment;


import com.example.webandappdevelopment.ModelDTO.LibrarianDTO;
import com.example.webandappdevelopment.ModelDTO.MemberDTO;

public class SessionObject {

    private final MemberDTO memberDTO;
    private final LibrarianDTO LibrarianDTO;

    private static SessionObject INSTANCE;

    private SessionObject(MemberDTO m, LibrarianDTO l) {
        this.LibrarianDTO = l;
        this.memberDTO = m;
    }

    public synchronized static SessionObject getInstance(MemberDTO m, LibrarianDTO l) {
        if (INSTANCE == null) {
            INSTANCE = new SessionObject(m, l);
        }

        return INSTANCE;
    }

    public synchronized static SessionObject getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SessionObject(null, null);
        }

        return INSTANCE;
    }

    public MemberDTO getMemberDTO() {
        return memberDTO;
    }

    public LibrarianDTO getLibrarianDTO() {
        return LibrarianDTO;
    }

    public synchronized void clear() {
        INSTANCE = null;
    }
}