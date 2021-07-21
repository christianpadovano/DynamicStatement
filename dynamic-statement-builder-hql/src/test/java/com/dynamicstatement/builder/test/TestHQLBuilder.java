package com.dynamicstatement.builder.test;


import com.dynamicstatement.builder.impl.hql.HqlBuilderStatement;
import com.dynamicstatement.builder.impl.hql.JoinConditions;
import com.dynamicstatement.builder.impl.hql.JoinType;
import com.dynamicstatement.builder.interf.IBuilderStatement;
import org.junit.Assert;
import org.junit.Test;

import static com.dynamicstatement.builder.operator.JoinTypeEnum.FETCH;

public class TestHQLBuilder {

    private static final String HQL1=" from DCampagnePartecipate cp where sysdate BETWEEN  cp.dCampagne.dataInizioValidita and cp.dCampagne.dataFineValidita or sysdate > cp.dCampagne.dataInizioValidita and cp.dCampagne.dataFineValidita is null  order by upper(cp.dPartecipate.descPartecipata)";

    private static final String HQL1_DYNAMIC="from DCampagnePartecipate cp where sysdate>cp.dCampagne.dataInizioValidita  and  cp.dCampagne.dataFineValidita is null order by upper(cp.dPartecipate.descPartecipata) asc";


    private static final String HQL2="select cp.dCampagne from DCampagnePartecipate cp join fetch cp.dCampagne  where cp.dPartecipate.sequIdPartecipata= :sequIdPartecipata";

    private static final String HQL3_COUNT="select  count(r.loads)  from Requests r where r.loads.loadsState.idStato in  (:stato)" +
            " and r.numProgrCaricamento= (" +
            "select max(numProgrCaricamento) from Requests where" +
            " annoSomministrazione= r.annoSomministrazione and numSomministrazione= r.numSomministrazione and flagIsRequestActive is not null" +
            " ) and r.flagIsRequestActive is not null  and r.loads.loadsType= 'C'";

    /* dynamic sql statement */
    private static final String HQL3_DYNAMIC_WHERE="select  count(r.loads)  from Requests r  where r.loads.loadsState.idStato in (:stato)" +
            " and flagIsRequestActive is not null and r.loads.loadsType = 'C'";


    private static final String HQL_DYN_TABLE_COUNT="select  count(emp.books.id)  from Employe emp join fetch emp.books b where emp.active= :employe_is_active";
    private static final String HQL_DYN_TABLE_SELECT="select b from Employe emp join fetch emp.books b join fetch b.publisher where emp.active= :employe_is_active "  +
             "order by emp.books.id, emp.books.name";

    private static final String SQL_HAVING=
            "select p.id_service_point, count(del)  from Service_Point p,Other_stuff del " +
                    "where p.typepoint= :typeValue and p.id_service_point= del.id_service_point and del.typepoint= :typeValue " +
                    "group by p.id_service_point " +
                    "having  count(del) > 1 and  count(del) < 7";

    private static final String SQL_DYNAMIC_HAVING=
            "select p.id_service_point from Service_Point p,Other_stuff del " +
                    "where p.typepoint= :typeValue and p.id_service_point= del.id_service_point and del.typepoint= :typeValue " +
                    "group by p.id_service_point " +
                    "having  count(del) > 1 ";


    private static final String SQL_DYNAMIC_NO_HAVING=
            "select p.id_service_point from Service_Point p,Other_stuff del " +
                    "where p.typepoint= :typeValue and p.id_service_point= del.id_service_point and del.typepoint= :typeValue " +
                    "group by p.id_service_point";

    private static final JoinConditions JOIN_FETCH=new JoinConditions(new JoinType(FETCH));


    @Test
    public void testHQLParametricTablesCount() throws IllegalAccessException, InstantiationException  {


        IBuilderStatement s = createDynamicStatement(true);

        String hqlQuery = s.build();

        System.out.println("Original query : " + HQL_DYN_TABLE_COUNT);
        System.out.println("HQL query      : " + hqlQuery);


        Assert.assertEquals("The named query HQL_DYN_TABLE_COUNT it's different from the generated one",
                hqlQuery.trim(),HQL_DYN_TABLE_COUNT.trim());


        System.out.println(hqlQuery);


    }

    @Test
    public void testHQLParametricTablesSelectFields() throws IllegalAccessException, InstantiationException  {



        //We will use the same stement to build a select with fields and orderby
        IBuilderStatement s = createDynamicStatement(false);

        String hqlQuery = s.build();

        System.out.println("Original query : " + HQL_DYN_TABLE_SELECT);
        System.out.println("HQL query      : " + hqlQuery);


        Assert.assertEquals("The named query HQL_DYN_TABLE_SELCT it's different from the generated one",
                hqlQuery.trim(),HQL_DYN_TABLE_SELECT.trim());
    }



    private IBuilderStatement createDynamicStatement(boolean isCountType) throws InstantiationException, IllegalAccessException {
        IBuilderStatement s =HqlBuilderStatement.create();

        //   private final String HQL_DYN_TABLE_COUNT="select count(emp.books.id) from Employe emp join fetch emp.books where emp.active= :employe_is_active ";
        //   select b from Employe emp join fetch emp.books b join fetch b.publisher where emp.active= :employe_is_active"  +
        //             "order by emp.books.id , emp.books.name
        s.select().count("emp.books.id",isCountType). //if isCountType=true add the count statement
                   field("b",!isCountType);           //else add the field name to the select
        s.from().
                table("Employe","emp").
                table(JOIN_FETCH,"emp.books","b").
                table(JOIN_FETCH,"b.publisher",!isCountType);
        s.where().
                eq("emp.active",":employe_is_active");
        s.orderby().
                field("emp.books.id",true,!isCountType).
                field("emp.books.name",true,!isCountType);
        return s;
    }

    @Test
    public void testHQL_MultipleTables() throws IllegalAccessException, InstantiationException  {

        IBuilderStatement s =HqlBuilderStatement.create();

        s.select().field("cp.dCampagne");
        s.from().
                table("DCampagnePartecipate","cp").
                table(JOIN_FETCH, "cp.dCampagne", "");
        s.where().
                eq("cp.dPartecipate.sequIdPartecipata",":sequIdPartecipata");


        String hqlQuery = s.build();


        Assert.assertEquals("The named query HQL2 it's different table the generated one",
                hqlQuery,HQL2);
        System.out.println(hqlQuery);
    }

    @Test
    public void testHQL3_subselect() throws IllegalAccessException, InstantiationException  {

        //Sub select statement creation
        IBuilderStatement subSelect = HqlBuilderStatement.create();
                //new HqlBuilderStatement();
        subSelect.select().
                field("max(numProgrCaricamento)");
        subSelect.from().
                table("Requests",null);
        subSelect.where().
                eq("annoSomministrazione","r.annoSomministrazione").and().
                eq("numSomministrazione","r.numSomministrazione").and().
                isNotNull("flagIsRequestActive");


        System.out.println("subselect : " + subSelect.build());

        IBuilderStatement mainQuery = HqlBuilderStatement.create();
        mainQuery.select().
                count("r.loads");
        mainQuery.from().
                table("Requests","r");
        mainQuery.where().
                in("r.loads.loadsState.idStato", new String[]{":stato"}).and().
                eq("r.numProgrCaricamento", subSelect.getStatement()).and().
                isNotNull("r.flagIsRequestActive").and().
                eq("r.loads.loadsType","'C'");


        String hqlQuery = mainQuery.build();

        System.out.println("Original query : " + HQL3_COUNT);
        System.out.println("HQL query      : " + hqlQuery);


        Assert.assertEquals("The named query HQL3_COUNT it's different from the generated one",
                HQL3_COUNT,hqlQuery);

    }

    @Test
    public void testDynamicHqlBuilding() throws IllegalAccessException, InstantiationException {

        IBuilderStatement s = HqlBuilderStatement.create();
        s.select().allFields();
        s.from().table("DCampagnePartecipate","cp");
        s.where().
             between("sysdate","cp.dCampagne.dataInizioValidita","cp.dCampagne.dataFineValidita").
             or().
             greater("sysdate","cp.dCampagne.dataInizioValidita").
             and().
             isNull("cp.dCampagne.dataFineValidita");
        s.orderby().
                field("upper(cp.dPartecipate.descPartecipata)",true);

        String hqlQuery =   s.build();


        System.out.println("Original query : " + HQL1);
        System.out.println("HQL query      : " + hqlQuery);

        Assert.assertEquals("The named query HQL1 it's different from the generated one",
                HQL1.trim(),hqlQuery.trim());
        System.out.println(hqlQuery);
    }


    @Test
    public void testHQLParametricStatementGeneration_AndOr() {

        String HQL_PARAMETRIC_STATEMENT_2=
                "SELECT ind FROM DIndirizziFilialiBanche ind WHERE "
                //        + "ind.indiIndirizzo = :indiIndirizzo AND "
                        + "ind.indiCap= :indiCap ";
                //        + " ind.indiLocalita = :indiLocalita  ";


        String indiIndirizzo=null;
        Long indiCap=80900L;
        String indiLocalita=null;


        IBuilderStatement builder = HqlBuilderStatement.create();
        builder.select().field("ind");
        builder.from().
                table("DIndirizziFilialiBanche","ind");
        builder.where().
                eq("ind.indiIndirizzo",":indiIndirizzo",indiIndirizzo!=null).and().
                eq("ind.indiCap",":indiCap",indiCap!=null).or().
                eq("ind.indiLocalita",":indiLocalita",indiLocalita!=null);

        String hqlQuery =  builder.build();

        System.out.println("Original query : " + HQL_PARAMETRIC_STATEMENT_2.trim());
        System.out.println("HQL query      : " + hqlQuery);


        Assert.assertEquals("The named query HQL_PARAMETRIC_STATEMENT_2 it's different table the generated one",
                HQL_PARAMETRIC_STATEMENT_2.trim().toUpperCase(),hqlQuery.trim().toUpperCase());
    }



    @Test
    public void testHQLParametricStatementGeneration_AND() {

        String HQL_PARAMETRIC_STATEMENT_3=
                "SELECT ind FROM DIndirizziFilialiBanche ind WHERE "
                        //        + "ind.indiIndirizzo = :indiIndirizzo AND "
                        + "ind.indiCap= :indiCap and "
                        + "ind.indiLocalita= :indiLocalita and "
                        + "ind.tel= :tel";


        String indiIndirizzo=null;
        Long indiCap=80900L;
        String country="italy";
        String telnumber="+3906234234";


        IBuilderStatement builder = HqlBuilderStatement.create();
        builder.select().field("ind");
        builder.from().
                table("DIndirizziFilialiBanche","ind");
        builder.where().
                eq("ind.indiIndirizzo",":indiIndirizzo",indiIndirizzo!=null).and().
                eq("ind.indiCap",":indiCap",indiCap!=null).and().
                eq("ind.indiLocalita",":indiLocalita",country!=null).and().
                eq("ind.tel",":tel",telnumber!=null);

        String hqlQuery =  builder.build();

        System.out.println("Original query : " + HQL_PARAMETRIC_STATEMENT_3.trim());
        System.out.println("HQL query      : " + hqlQuery);


        Assert.assertEquals("The named query HQL_PARAMETRIC_STATEMENT_2 it's different table the generated one",
                hqlQuery.trim().toUpperCase(),HQL_PARAMETRIC_STATEMENT_3.trim().toUpperCase());
    }

    @Test
    public void testHQLParametricStatementGeneration_OR() {

        String HQL_PARAMETRIC_STATEMENT_2=
                "SELECT ind FROM DIndirizziFilialiBanche ind WHERE "
                        //        + "ind.indiIndirizzo = :indiIndirizzo OR "
                        + "ind.indiCap= :indiCap";
        //        + " ind.indiLocalita = :indiLocalita  ";


        String indiIndirizzo=null;
        Long indiCap=80900L;
        String indiLocalita=null;

        IBuilderStatement builder = HqlBuilderStatement.create();
        builder.select().
                field("ind");
        builder.from().
                table("DIndirizziFilialiBanche","ind");
        builder.
             where().
                eq("ind.indiIndirizzo",":indiIndirizzo",indiIndirizzo!=null).or().
                eq("ind.indiCap",":indiCap",indiCap!=null).or().
                eq("ind.indiLocalita",":indiLocalita",indiLocalita!=null);
        String hqlQuery =  builder.build();


        System.out.println("Original query : " + HQL_PARAMETRIC_STATEMENT_2.trim());
        System.out.println("HQL query      : " + hqlQuery);


        Assert.assertEquals("The named query HQL_PARAMETRIC_STATEMENT_2 it's different table the generated one",
                hqlQuery.trim().toUpperCase(),HQL_PARAMETRIC_STATEMENT_2.trim().toUpperCase());
    }

    @Test
    public void havingTest1() {

        IBuilderStatement s =
                HqlBuilderStatement.create();

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
                count("del").greaterThan("1").and().
                count("del").lessThan("7");




        String sqlQuery =  s.build();

        System.out.println("Original query : " + SQL_HAVING.trim());
        System.out.println("SQL query      : " + sqlQuery);


        Assert.assertEquals("The named query SQL_HAVING it's different from the generated one",
                sqlQuery.trim().toUpperCase(),SQL_HAVING.trim().toUpperCase());
    }

    @Test
    public void dynamicSqlWithhavingTest() {

        boolean customCondition=false;

        IBuilderStatement s =
                HqlBuilderStatement.create();

        s.select().
                field("p.id_service_point").
                count("del",customCondition);
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
                count("del").greaterThan("1").and(customCondition).
                count("del",customCondition).lessThan("7",customCondition);




        String sqlQuery =  s.build();

        System.out.println("Original query : " + SQL_DYNAMIC_HAVING.trim());
        System.out.println("SQL query      : " + sqlQuery);


        Assert.assertEquals("The named query SQL_DYNAMIC_HAVING it's different from the generated one",
                sqlQuery.trim().toUpperCase(),SQL_DYNAMIC_HAVING.trim().toUpperCase());
    }


    @Test
    public void dynamicSqlWithNoHavingCondition() {

        boolean customCondition=false;

        IBuilderStatement s =
                HqlBuilderStatement.create();

        s.select().
                field("p.id_service_point").
                count("del",customCondition);
        s.from().
                table("Service_Point","p").
                table("Other_stuff","del");
        s.where().
                eq("p.typepoint",":typeValue").and().
                eq("p.id_service_point","del.id_service_point").and().
                eq("del.typepoint",":typeValue");
        s.groupBy().
                field("p.id_service_point").
        having(customCondition).
                 count("del").greaterThan("1").and().
                 count("del").lessThan("7");


        String sqlQuery =  s.build();

        System.out.println("Original query : " + SQL_DYNAMIC_NO_HAVING.trim());
        System.out.println("SQL query      : " + sqlQuery);


        Assert.assertEquals("The named query SQL_DYNAMIC_NO_HAVING it's different from the generated one",
                sqlQuery.trim().toUpperCase(),SQL_DYNAMIC_NO_HAVING.trim().toUpperCase());
    }
}


/*
Query q = em.createQuery("SELECT a FROM AltriDatiAc a INNER JOIN FETCH a.puntoServizio ps " +
							" LEFT JOIN FETCH ps.contattos c " +
							" LEFT JOIN FETCH c.tipoContattoBean tc " +
							" LEFT JOIN FETCH c.tipoUfficioBean ctu " +
							" LEFT JOIN FETCH ps.immagines i " +
							" LEFT JOIN FETCH i.tipoImmagineBean ti " +
							" LEFT JOIN FETCH ps.orarios o " +
							" LEFT JOIN FETCH o.tipoUfficioBean otu " +
							" LEFT JOIN FETCH ps.tipoPoiBean tp " +
							" LEFT JOIN FETCH ps.tipoVisibilitaBean tv " +
							" LEFT JOIN FETCH ps.tipoServizios srv " +
							" LEFT JOIN FETCH ps.tipoStatoDatiBean tsd " +
							" LEFT JOIN FETCH ps.tipoStatoPdsBean tsp " +
							" LEFT JOIN FETCH ps.associazioneRappresentantes ar " +
							" LEFT JOIN FETCH ar.rappresentante r " +
							" LEFT JOIN FETCH r.contattoRappresentantes " +
							" LEFT JOIN FETCH r.tipoRuoloBean " +
							" WHERE ps.id.idPuntoServizio = :id " +
							" AND ps.id.tipoPoi = :tipoPoi "
					, AltriDatiAc.class);
			q.setParameter("id", idPuntoServizio);
			q.setParameter("tipoPoi", TipoPoiEnum.AUTOMOBILE_CLUB.getTipoPoi());







 */
