package ge.lightspeed.core.factory.impl;

import ge.lightspeed.core.factory.TableDataFactory;
import ge.lightspeed.core.model.Column;

public class ColumnFactory implements TableDataFactory<Column> {

    @Override
    public Column createTableData() {
        return new Column();
    }
}
