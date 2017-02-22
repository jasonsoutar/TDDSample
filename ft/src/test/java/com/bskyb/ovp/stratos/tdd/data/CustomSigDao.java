package com.bskyb.ovp.stratos.tdd.data;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import javax.inject.Inject;
import java.util.Arrays;

public class CustomSigDao {

    private Session session;

    @Inject
    public CustomSigDao(Session session) {
        this.session = session;
    }

    public void setCustomSig(String name, String signature) {

        session.execute(QueryBuilder.insertInto("siggen", "customsig").values(Arrays.asList("name", "customsig"), Arrays.asList(name, signature)));
    }


}
