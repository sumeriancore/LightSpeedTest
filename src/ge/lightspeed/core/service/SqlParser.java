package ge.lightspeed.core.service;

import ge.lightspeed.core.model.*;

public interface SqlParser {

    Query parseSqlQuery(String sqlQuery);
}
