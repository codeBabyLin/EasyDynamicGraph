//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.neo4j.graphdb.facade.embedded;

import java.io.File;
import java.util.Map;
import org.neo4j.graphdb.facade.GraphDatabaseDependencies;
import org.neo4j.graphdb.facade.GraphDatabaseFacadeFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.graphdb.factory.module.edition.CommunityEditionModule;
import org.neo4j.helpers.collection.Iterables;
import org.neo4j.kernel.configuration.Config;
import org.neo4j.kernel.impl.factory.DatabaseInfo;
import org.neo4j.kernel.impl.factory.GraphDatabaseFacade;

public class EmbeddedGraphDatabase extends GraphDatabaseFacade {
    public EmbeddedGraphDatabase(File storeDir, Map<String, String> params, GraphDatabaseFacadeFactory.Dependencies dependencies) {
        this.create(storeDir, params, dependencies);
    }

    protected EmbeddedGraphDatabase(File storeDir, Config config, GraphDatabaseFacadeFactory.Dependencies dependencies) {
        this.create(storeDir, config, dependencies);
    }

    protected void create(File storeDir, Map<String, String> params, GraphDatabaseFacadeFactory.Dependencies dependencies) {
        GraphDatabaseDependencies newDependencies = GraphDatabaseDependencies.newDependencies(dependencies).settingsClasses(Iterables.asList(Iterables.append(GraphDatabaseSettings.class, dependencies.settingsClasses())));
        (new GraphDatabaseFacadeFactory(DatabaseInfo.COMMUNITY, CommunityEditionModule::new)).initFacade(storeDir, params, newDependencies, this);
    }

    protected void create(File storeDir, Config config, GraphDatabaseFacadeFactory.Dependencies dependencies) {
        GraphDatabaseDependencies newDependencies = GraphDatabaseDependencies.newDependencies(dependencies).settingsClasses(Iterables.asList(Iterables.append(GraphDatabaseSettings.class, dependencies.settingsClasses())));
        (new GraphDatabaseFacadeFactory(DatabaseInfo.COMMUNITY, CommunityEditionModule::new)).initFacade(storeDir, config, newDependencies, this);
    }
}
