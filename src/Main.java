import ge.lightspeed.core.model.*;
import ge.lightspeed.core.service.SqlParser;
import ge.lightspeed.core.service.impl.DefaultFormattedSqlParser;
import ge.lightspeed.core.service.impl.DefaultRawSqlParser;

public class Main {

    public static void main(String[] args) {
        SqlParser sqlParser = new DefaultRawSqlParser();
        SqlParser sqlParserForFormattedQuery = new DefaultFormattedSqlParser();

        String sqlQuery = """
                SELECT au.name as NAME, count(book.id) as BOOK_COUNT, sum(book.cost) as BOOK_COST_SUM\s
                FROM author as au, book b\s
                LEFT JOIN book ON (au.id = book.author_id)\s
                RIGHT JOIN book as b ON (book.id = au.book_id)\s
                WHERE au.name LIKE '%author%'\s
                GROUP BY au.name, au.surname\s
                ORDER BY au.name, au.surname DESC\s
                HAVING COUNT(*) > 1 AND SUM(book.cost) > 500
                LIMIT 10
                OFFSET 15;
                """;

        String rawSqlQuery = """
                select au.name as NAME, count(book.id) as BOOK_COUNT, sum(book.cost) as BOOK_COST_SUM           
                            FROM author as au, book b
                LEFT    
                    JOIN book ON (au.id = book.author_id)
                right 
                    join book as b ON (book.id = au.book_id)
                
                
                where au.name LIKE '%author%'
                GROUP BY 
                        au.name, au.surname
                ORDER BY au.name, au.surname ASC
                HAVING COUNT(*) > 1 AND SUM(book.cost) > 500
                LIMIT 10
                OFFSET 15;
                """;

        Query formattedQuery = sqlParserForFormattedQuery.parseSqlQuery(sqlQuery);

        Query query = sqlParser.parseSqlQuery(rawSqlQuery);

        System.out.println(formattedQuery);

        System.out.println(query);
    }
}