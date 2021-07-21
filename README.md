# I hate JPA Criteria , IHJC for the friends

Welcome , if you hate the ugly criterias builder for JPA and you are searching for a different point of view 
well you are in the right place.

## Introduction

 How was born this library ? 
 It's very simple to substitute the CriteriaBuilder in some projects where we need to build
 dynamic and complex parametric queries without to be forced  to use the string concatenation or the ugly and unreadable 
 objects (Root, Predicates) of the CriteriasBuilder library. 

 In my opinion it works for simple query statements but it becomes completely unreadable where the query is more complicated.

 Anyway this simple library don't wont to be a solution more advanced of the JPA criterias 
 but it should be a more human readble  alternative to work with complex and dynamic queries. 

 Anyway that's only my opinion!

 This library is only an SQL/HQL query statement builder it doesn't check any syntax error because 
 that's the scope of the Database engine.
 

## Documentation
 Give a look inside the src/test (sql and hql) where you will find all the stuff that you need to work with this library
 
 I hope that it will be usefull to you as it has been to me.

## JUnit Test 

 All tests provided here are based on the comparation of an "original" sql statement with that builded by the 
 library.


## Starting using the library



###JDK supported 

Tested and compiled under 1.8 and jdk 11

