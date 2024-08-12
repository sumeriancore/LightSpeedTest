package ge.lightspeed.core.factory;

import ge.lightspeed.core.model.TableData;

public interface TableDataFactory<T extends TableData> {

    T createTableData();
}

