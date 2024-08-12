package ge.lightspeed.core.factory;

import ge.lightspeed.core.model.DataSources;

public interface TableDataFactory<T extends DataSources> {

    T createTableData();
}

