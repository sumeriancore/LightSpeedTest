package ge.lightspeed.core.factory.impl;

import ge.lightspeed.core.factory.TableDataFactory;
import ge.lightspeed.core.model.Table;

public class TableFactory implements TableDataFactory<Table> {

    @Override
    public Table createTableData() {
        return new Table();
    }
}
