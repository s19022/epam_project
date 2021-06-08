package com.example.InspectionBoard.model.mockedClasses;

import com.example.InspectionBoard.model.dao.AccountDao;
import com.example.InspectionBoard.model.dto.SaveEnrolleeDto;
import com.example.InspectionBoard.model.dto.db.DbAccountDto;
import com.example.InspectionBoard.model.enums.AccountRole;

import java.sql.Connection;
import java.util.*;

import static com.example.InspectionBoard.model.service.ServiceUtility.hash;

public class MockAccountDao implements AccountDao {
    private final Set<DbAccountDto> accountDtoSet = new HashSet<>();
    private final Map<String, String> loginPassword = new HashMap<>();
    public MockAccountDao(){
        init();
    }

    @Override
    public Optional<DbAccountDto> findByLoginAndPassword(String login, String password){
        if (!loginPassword.containsKey(login)){
            return Optional.empty();
        }
        if(!loginPassword.get(login).equals(password)){
            return Optional.empty();
        }
        return accountDtoSet.stream().filter(i -> i.getLogin().equals(login)).findFirst();
    }

    @Override
    public void blockEnrollee(String login){

    }

    @Override
    public void unblockEnrollee(String login){

    }

    @Override
    public void insertEnrollee(SaveEnrolleeDto enrollee, int id){

    }

    @Override
    public int insertAccount(SaveEnrolleeDto enrollee){
        return 0;
    }

    @Override
    public Optional<DbAccountDto> findByLogin(String login){
        return Optional.empty();
    }


    @Override
    public Connection getConnection() {
        return null;
    }

    @Override
    public void close(){
    }

    private void init(){
        loginPassword.put("enrollee1Blocked", hash("enrollee1Blocked"));
        DbAccountDto enrollee1Blocked = new DbAccountDto(1, AccountRole.ENROLLEE, true, "enrollee1Blocked");
        accountDtoSet.add(enrollee1Blocked);

        loginPassword.put("enrollee2", hash("enrollee2"));
        DbAccountDto enrollee2 = new DbAccountDto(2, AccountRole.ENROLLEE, false, "enrollee2");
        accountDtoSet.add(enrollee2);

        loginPassword.put("admin3", hash("admin3"));
        DbAccountDto admin3 = new DbAccountDto(2, AccountRole.ADMIN, false, "admin3");
        accountDtoSet.add(admin3);
    }
}
