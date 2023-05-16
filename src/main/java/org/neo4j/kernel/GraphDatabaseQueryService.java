//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.neo4j.kernel;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import cn.DynamicGraph.graphdb.kernel.ContinuousGraph;
import org.neo4j.graphdb.DependencyResolver;
import org.neo4j.graphdb.security.URLAccessValidationError;
import org.neo4j.internal.kernel.api.Transaction;
import org.neo4j.internal.kernel.api.security.LoginContext;
import org.neo4j.kernel.api.dbms.DbmsOperations;
import org.neo4j.kernel.impl.coreapi.InternalTransaction;

public interface GraphDatabaseQueryService {

    ContinuousGraph<Long,Long> getDyGraph();
    DependencyResolver getDependencyResolver();

    InternalTransaction beginTransaction(Transaction.Type var1, LoginContext var2);

    InternalTransaction beginTransaction(Transaction.Type var1, LoginContext var2, long var3, TimeUnit var5);

    URL validateURLAccess(URL var1) throws URLAccessValidationError;

    DbmsOperations getDbmsOperations();
}
