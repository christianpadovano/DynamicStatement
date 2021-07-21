package com.dynamicstatement.builder.test;

import com.dynamicstatement.builder.impl.sql.JoinConditions;
import com.dynamicstatement.builder.impl.sql.JoinType;
import com.dynamicstatement.builder.impl.sql.SqlBuilderStatement;
import com.dynamicstatement.builder.interf.IBuilderStatement;
import com.dynamicstatement.builder.operator.JoinTypeEnum;
import org.junit.Assert;
import org.junit.Test;

import static com.dynamicstatement.builder.operator.JoinTypeEnum.INNER;
import static com.dynamicstatement.builder.operator.JoinTypeEnum.LEFT;

public class TestSQLBuilder {

    private static final String SQL_STATEMENT_1 =
                    "SELECT " +
                    "AN007.AN007_IDCF AS cf," +
                    "RLLIS.DESCR_LIST AS list," +
                    "NVL(RLVDP.RLVDP_RLTSV_COD_STATO, '---') AS codiceEsito," +
                    "NVL(RLTSV.RLTSV_DESCR_STATO, 'NO WORKING') AS descrEsitoVerifica," +
                    "TO_CHAR(RLVDP.RLVDP_DATE_VRFY, 'DD/MM/YYYY HH24:MI') AS dataOraVerifica," +
                    "AN007.AN007_COD_SEDECOMP AS codiceSede," +
                    "AN007.AN007_DESCM_SEDECOMP AS descrSede" +
                    " FROM " +
                    "TB_WORKING_LIST_REG RLILL " +
                    "JOIN TB_WORKINGLIST RLLIS ON (RLLIS.LIST_CODE_ID = RLILL.RLILL_LIST_CODE_ID) " +
                    "LEFT JOIN BUDGET_VERIFY RLVDP ON (RLILL.RLILL_BDPS_ID_VA_PK = RLVDP.RLVDP_RLILL_BDPS_ID_VA_PK AND RLILL.RLILL_LIST_CODE_ID = RLVDP.RLVDP_RLILL_COD_LISTA_PK AND RLILL.YEAR_PK = RLVDP.RLVDP_YEAR_PK) " +
                    "LEFT JOIN TB_RLTSV_TYPESTATEVER_CT RLTSV ON (RLTSV.RLTSV_COD_STATO_PK = RLVDP.RLVDP_RLTSV_COD_STATO) " +
                    "LEFT JOIN VW_AN007_PERSONAFIS AN007 ON (AN007.AN007_PEOPLE = RLILL.RLILL_BDPS_ID_VA_PK)" +
                    " WHERE " +
                    "(RLVDP.FLAG_STATUS= 'A' OR RLVDP.FLAG_STATUS IS NULL ) " +
                    "AND RLILL.YEAR_PK= :year";


    private static final String SQL_STATEMENT_2 =
        "select studentID,FullName,sat_score " +
        "from student "  +
        "where (studentID between  :start and :end"  +
        " or studentID= :id"  +
        " or FullName like '%Maximo%')"  +
        " and sat_score NOT in  (1000,1400)"  +
        " order by FullName DESC";

    private static final String SQL_STATEMENT_2_SEARCH_BY_LIKE =
            "select studentID,FullName " +
                    "from student "  +
                    "where"+
                    " FullName like '%Maximo%'" +
                    " order by FullName DESC";


    private static final String SQL_STATEMENT_2_SEARCHBY_SATSCORE =
            "select studentID,FullName,sat_score " +
                    "from student "  +
                    "where"  +
                    " sat_score NOT in  (1000,1400)"  +
                    " order by FullName, sat_score";


    private static final String SQL_HAVING=
            "select p.id_service_point, count(del)  from Service_Point p,Other_stuff del " +
            "where p.typepoint= :typeValue and p.id_service_point= del.id_service_point and del.typepoint= :typeValue " +
            "group by p.id_service_point " +
            "having  count(del) > 1 " +
            "ORDER BY  COUNT(del) DESC";


    private static final String SQL_ESISTS="SELECT SupplierName " +
            "FROM Suppliers s " +
            "WHERE  EXISTS (SELECT ProductName FROM Products p WHERE p.SupplierID= s.supplierID AND p.Price < 20)";

    private static final String SQL_ORDERBY_COUNT=
            "SELECT salesperson, count(product_id) " +
                    " FROM product_details " +
                    "GROUP BY salesperson " +
                    "ORDER BY  count(product_id) DESC";

    @Test
    public void testComplexSqlQuery() {

        IBuilderStatement s =
                 SqlBuilderStatement.create();

        s.select().
           field("AN007.AN007_IDCF","cf").
           field("RLLIS.DESCR_LIST","list").
           field("NVL(RLVDP.RLVDP_RLTSV_COD_STATO, '---')","codiceEsito").
           field("NVL(RLTSV.RLTSV_DESCR_STATO, 'NO WORKING')","descrEsitoVerifica").
           field("TO_CHAR(RLVDP.RLVDP_DATE_VRFY, 'DD/MM/YYYY HH24:MI')","dataOraVerifica").
           field("AN007.AN007_COD_SEDECOMP","codiceSede").
           field("AN007.AN007_DESCM_SEDECOMP","descrSede");
        s.from().
           table("  TB_WORKING_LIST_REG","RLILL").
           table( new JoinConditions(JoinTypeEnum.INNER).
                           conditions("RLLIS.LIST_CODE_ID = RLILL.RLILL_LIST_CODE_ID") ,
                   "TB_WORKINGLIST","RLLIS").
           table(  new JoinConditions(LEFT).
                           conditions("RLILL.RLILL_BDPS_ID_VA_PK = RLVDP.RLVDP_RLILL_BDPS_ID_VA_PK").and().
                           conditions("RLILL.RLILL_LIST_CODE_ID = RLVDP.RLVDP_RLILL_COD_LISTA_PK").and().
                           conditions("RLILL.YEAR_PK = RLVDP.RLVDP_YEAR_PK"),
                   "BUDGET_VERIFY","RLVDP").
            table( new JoinConditions(JoinTypeEnum.LEFT).
                        conditions("RLTSV.RLTSV_COD_STATO_PK = RLVDP.RLVDP_RLTSV_COD_STATO") ,
                    "TB_RLTSV_TYPESTATEVER_CT","RLTSV").
            table( new JoinConditions(LEFT).
                                conditions("AN007.AN007_PEOPLE = RLILL.RLILL_BDPS_ID_VA_PK") ,
                        " VW_AN007_PERSONAFIS","AN007");

        s.where().
           bracket(true).eq("RLVDP.FLAG_STATUS","'A'").or().isNull("RLVDP.FLAG_STATUS").bracket(false).
           and().
           eq("RLILL.YEAR_PK",":year");


        String sqlQuery =  s.build();

        System.out.println("Original query : " + SQL_STATEMENT_1.trim());
        System.out.println("SQL query      : " + sqlQuery);


        Assert.assertEquals("The named query SQL_STATEMENT_1 it's different from the generated one",
                sqlQuery.trim().toUpperCase(),SQL_STATEMENT_1.trim().toUpperCase());

    }

    @Test
    public void testSearchStudents() {
        IBuilderStatement s =
                SqlBuilderStatement.create();

        s.select().
                field("studentID").
                field("FullName").
                field("sat_score");
        s.from().
                table("student","");
        s.where().
                bracket(true).between("studentID",":start",":end").or().
                eq("studentID",":id").or().
                like("FullName","%Maximo%").bracket(false).and().
                notin("sat_score",new String[]{"1000","1400"});
        s.orderby().field("FullName",false);


        String sqlQuery =  s.build();

        System.out.println("Original query : " + SQL_STATEMENT_2.trim());
        System.out.println("SQL query      : " + sqlQuery);


        Assert.assertEquals("The named query SQL_STATEMENT_2 it's different from the generated one",
                sqlQuery.trim().toUpperCase(),SQL_STATEMENT_2.trim().toUpperCase());
    }

    /**
     * Now we are going to assembling the SQL statement evaluating a specific condition while
     * the building process in running..
     */
    @Test
    public void searchMaximo() {
        //if this flag is true the sql become a seach for like operator
        boolean isSeachingForMaximo=true;

        IBuilderStatement s =
                SqlBuilderStatement.create();
        //the query transformation is beginning...
        s.select().
                field("studentID",isSeachingForMaximo).
                field("FullName", isSeachingForMaximo).
                field("sat_score",!isSeachingForMaximo);  //we don't need it
        s.from().
                table("student","");
        s.where().
                bracket(true,!isSeachingForMaximo).          //we don't need it
                 between("studentID",":start",":end",!isSeachingForMaximo).or().  //we don't need it
                 eq("studentID",":id",!isSeachingForMaximo).or().  //we don't need it
                 like("FullName","%Maximo%",isSeachingForMaximo).
                bracket(false,!isSeachingForMaximo).and().                 //we don't need it
                notin("sat_score",new String[]{"1000","1400"},!isSeachingForMaximo); //we don't need it
        s.orderby().field("FullName",false,isSeachingForMaximo);


        String sqlQuery =  s.build();

        System.out.println("Original query : " + SQL_STATEMENT_2_SEARCH_BY_LIKE.trim());
        System.out.println("SQL query      : " + sqlQuery);


        Assert.assertEquals("The named query SQL_STATEMENT_2 it's different from the generated one",
                sqlQuery.trim().toUpperCase(),SQL_STATEMENT_2_SEARCH_BY_LIKE.trim().toUpperCase());

    }

    @Test
    public void searchBySatScoreNotIn1000_1400() {
        //if this flag is true the sql become a seach for like operator
        boolean isSeachingForMaximo=false;
        //if this flag is true would be enabled only the 'not in' statement
        boolean isSearchingForSatScore=true;

        IBuilderStatement s =
                SqlBuilderStatement.create();

        //the query transformation is beginning...
        s.select().
                field("studentID",isSeachingForMaximo || isSearchingForSatScore).
                field("FullName", isSeachingForMaximo || isSearchingForSatScore).
                field("sat_score",isSearchingForSatScore);
        s.from().
                table("student","");
        s.where().
                bracket(true,!isSeachingForMaximo && !isSearchingForSatScore).                       //we don't need it
                between("studentID",":start",":end",!isSeachingForMaximo && !isSearchingForSatScore).or().  //we don't need it
                eq("studentID",":id",!isSeachingForMaximo && !isSearchingForSatScore).or().  //we don't need it
                like("FullName","%Maximo%",isSeachingForMaximo).                                             //we don't need it
                bracket(false,!isSeachingForMaximo && !isSearchingForSatScore).and().                 //we don't need it
                notin("sat_score",new String[]{"1000","1400"},isSearchingForSatScore); //we don't need it
        s.orderby().
                field("FullName",true,isSeachingForMaximo || isSearchingForSatScore).
                field("sat_score",true,!isSeachingForMaximo || isSearchingForSatScore);


        String sqlQuery =  s.build();

        System.out.println("Original query : " + SQL_STATEMENT_2_SEARCHBY_SATSCORE.trim());
        System.out.println("SQL query      : " + sqlQuery);


        Assert.assertEquals("The named query SQL_STATEMENT_2_SEARCHBY_SATSCORE it's different from the generated one",
                sqlQuery.trim().toUpperCase(),SQL_STATEMENT_2_SEARCHBY_SATSCORE.trim().toUpperCase());
    }

    //TODO implementare i test per  ogni condition dell'HAVING
    @Test
    public void havingCount_test() {


        IBuilderStatement s =
                SqlBuilderStatement.create();

        s.select().
                field("p.id_service_point").
                count("del");
        s.from().
                table("Service_Point","p").
                table("Other_stuff","del");
        s.where().
                eq("p.typepoint",":typeValue").and().
                eq("p.id_service_point","del.id_service_point").and().
                eq("del.typepoint",":typeValue");
        s.groupBy().
           field("p.id_service_point").
          having().
                count("del").greaterThan("1");
        s.orderby().
                count("del",false);




        String sqlQuery =  s.build();

        System.out.println("Original query : " + SQL_HAVING.trim());
        System.out.println("SQL query      : " + sqlQuery);


        Assert.assertEquals("The named query SQL_HAVING it's different from the generated one",
                sqlQuery.trim().toUpperCase(),SQL_HAVING.trim().toUpperCase());
    }

    @Test
    public void exists_test() throws IllegalAccessException {
        IBuilderStatement s =
                SqlBuilderStatement.create();

        IBuilderStatement existsQuery =
                SqlBuilderStatement.create();
        existsQuery.select().
                field("ProductName");
        existsQuery.from().
                table("Products","p");
        existsQuery.where().
                eq("p.SupplierID","s.supplierID").and().
                lessthan("p.Price","20");
        //create the statement after that the "Statement" istance will be available
        existsQuery.build();
        //SELECT ProductName FROM Products p  WHERE p.SupplierID = s.supplierID AND p.Price < 20


        s.select().field("SupplierName");
        s.from().table("Suppliers","s");
        s.where().exists(existsQuery.getStatement());

        String sqlQuery =  s.build();

        System.out.println("Original query : " + SQL_ESISTS.trim());
        System.out.println("SQL query      : " + sqlQuery);


        Assert.assertEquals("The named query SQL_ESISTS it's different from the generated one",
                sqlQuery.trim().toUpperCase(),SQL_ESISTS.trim().toUpperCase());
    }

    @Test
    public void orderByCountTest() {
        IBuilderStatement s =
                SqlBuilderStatement.create();

        s.select().
                field("salesperson").
                count("product_id");
        s.from().
                table("product_details","");
        s.groupBy().
                field("salesperson");
        s.orderby().
                count("product_id",false);


        String sqlQuery =  s.build();

        System.out.println("Original query : " + SQL_ORDERBY_COUNT.trim());
        System.out.println("SQL query      : " + sqlQuery);


        Assert.assertEquals("The named query SQL_ORDERBY_COUNT it's different from the generated one",
                sqlQuery.trim().toUpperCase(),SQL_ORDERBY_COUNT.trim().toUpperCase());
    }

}


//TODO verificare implementazione di
//  having count(*) > 80 e order by count(*) DESC;




