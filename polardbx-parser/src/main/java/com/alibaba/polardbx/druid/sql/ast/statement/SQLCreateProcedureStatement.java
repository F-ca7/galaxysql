/*
 * Copyright 1999-2017 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.polardbx.druid.sql.ast.statement;

import com.alibaba.polardbx.druid.sql.ast.SQLName;
import com.alibaba.polardbx.druid.sql.ast.SQLParameter;
import com.alibaba.polardbx.druid.sql.ast.SQLStatement;
import com.alibaba.polardbx.druid.sql.ast.SQLStatementImpl;
import com.alibaba.polardbx.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.polardbx.druid.sql.visitor.SQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

public class SQLCreateProcedureStatement extends SQLStatementImpl implements SQLCreateStatement {

    private SQLName definer;

    private boolean create = true;
    private boolean orReplace;
    private SQLName name;
    private SQLStatement block;
    private List<SQLParameter> parameters = new ArrayList<SQLParameter>();

    // for oracle
    private String javaCallSpec;

    private SQLName authid;

    // for mysql
    private boolean deterministic;
    private SqlDataAccess sqlDataAccess = SqlDataAccess.CONTAINS_SQL;
    private boolean languageSql;

    private String wrappedSource;

    private SQLCharExpr comment;

    private SqlSecurity sqlSecurity = SqlSecurity.DEFINER;

    @Override
    public void accept0(SQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, definer);
            acceptChild(visitor, name);
            acceptChild(visitor, parameters);
            acceptChild(visitor, block);
            acceptChild(visitor, comment);
        }
        visitor.endVisit(this);
    }

    public List<SQLParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<SQLParameter> parameters) {
        this.parameters = parameters;
    }

    public SQLName getName() {
        return name;
    }

    public void setName(SQLName name) {
        this.name = name;
    }

    public SQLStatement getBlock() {
        return block;
    }

    public void setBlock(SQLStatement block) {
        if (block != null) {
            block.setParent(this);
        }
        this.block = block;
    }

    public SqlSecurity getSqlSecurity() {
        return sqlSecurity;
    }

    public void setSqlSecurity(SqlSecurity sqlSecurity) {
        this.sqlSecurity = sqlSecurity;
    }

    public SQLName getAuthid() {
        return authid;
    }

    public void setAuthid(SQLName authid) {
        if (authid != null) {
            authid.setParent(this);
        }
        this.authid = authid;
    }

    public boolean isOrReplace() {
        return orReplace;
    }

    public void setOrReplace(boolean orReplace) {
        this.orReplace = orReplace;
    }

    public SQLName getDefiner() {
        return definer;
    }

    public void setDefiner(SQLName definer) {
        this.definer = definer;
    }

    public boolean isCreate() {
        return create;
    }

    public void setCreate(boolean create) {
        this.create = create;
    }

    public String getJavaCallSpec() {
        return javaCallSpec;
    }

    public void setJavaCallSpec(String javaCallSpec) {
        this.javaCallSpec = javaCallSpec;
    }

    public boolean isDeterministic() {
        return deterministic;
    }

    public void setDeterministic(boolean deterministic) {
        this.deterministic = deterministic;
    }


    public SQLParameter findParameter(long hash) {
        for (SQLParameter param : this.parameters) {
            if (param.getName().nameHashCode64() == hash) {
                return param;
            }
        }

        return null;
    }

    public boolean isLanguageSql() {
        return languageSql;
    }

    public void setLanguageSql(boolean languageSql) {
        this.languageSql = languageSql;
    }

    public SQLCharExpr getComment() {
        return comment;
    }

    public void setComment(SQLCharExpr comment) {
        if (comment != null) {
            comment.setParent(this);
        }
        this.comment = comment;
    }

    public String getWrappedSource() {
        return wrappedSource;
    }

    public void setWrappedSource(String wrappedSource) {
        this.wrappedSource = wrappedSource;
    }

    public SqlDataAccess getSqlDataAccess() {
        return sqlDataAccess;
    }

    public void setSqlDataAccess(SqlDataAccess sqlDataAccess) {
        this.sqlDataAccess = sqlDataAccess;
    }
}
