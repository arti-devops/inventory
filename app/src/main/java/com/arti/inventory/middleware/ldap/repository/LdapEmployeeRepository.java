package com.arti.inventory.middleware.ldap.repository;

import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import com.arti.inventory.middleware.ldap.model.LdapEmployee;

@Service
public class LdapEmployeeRepository {

    @Autowired
    private LdapTemplate ldapTemplate;

    public List<LdapEmployee> getAllLdapEmployees() {
        return ldapTemplate.search(
                "ou=arti",
                "(objectClass=user)",
                new AttributesMapper<LdapEmployee>() {
                    @Override
                    public LdapEmployee mapFromAttributes(Attributes attrs) throws NamingException {
                        return populateLdapEmployee(attrs);
                    }

                });
    }

    public LdapEmployee getLdapEmployeeByUsername(String samAccountName) {
        if (samAccountName.split("@").length > 1) {
            samAccountName = samAccountName.split("@")[0];
        }
        List<LdapEmployee> Ldapemployees = ldapTemplate.search(
                "ou=arti",
                "(&(objectClass=user)(samAccountName=" + samAccountName + "))",
                new AttributesMapper<LdapEmployee>() {
                    @Override
                    public LdapEmployee mapFromAttributes(Attributes attrs) throws NamingException {
                        return populateLdapEmployee(attrs);
                    }
                });

        if (!Ldapemployees.isEmpty()) {
            return Ldapemployees.get(0);
        } else {
            return null;
        }
    }

    private LdapEmployee populateLdapEmployee(Attributes attrs) {
        LdapEmployee ldapemployee = new LdapEmployee();
        ldapemployee.setMatricule(getCustomAttr("employeeID", attrs));
        ldapemployee.setUsername(getCustomAttr("sAMAccountName", attrs));
        ldapemployee.setFullname(getCustomAttr("cn", attrs));
        ldapemployee.setGender(getCustomAttr("personalTitle", attrs));
        ldapemployee.setCategory(getCustomAttr("employeeType", attrs));
        ldapemployee.setPosition(getCustomAttr("title", attrs));
        ldapemployee.setEmail(getCustomAttr("mail", attrs));
        return ldapemployee;
    }

    private String getCustomAttr(String attr, Attributes attributes) {
        try {
            return attributes.get(attr).get().toString();
        } catch (Exception e) {
            return null;
        }
    }
}
