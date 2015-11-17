# kie-test

El proyecto contiene 3 procesos BPMN:

- src/main/resources/org.jbpm.RFG-A-A.v1.0.bpmn2: Consiste en dos BussinesRuleTask que activan el mismo RowFlowGroup 'GrupoA'

- src/main/resources/org.jbpm.RFG-A-B.v1.0.bpmn2: Consiste en dos BussinesRuleTask que activa el RowFlowGroup 'GrupoA' y luego el RowFlowGroup 'GrupoB'

- src/main/resources/org.jbpm.RFG-B-A.v1.0.bpmn2: Consiste en dos BussinesRuleTask que activa el RowFlowGroup 'GrupoB' y luego el RowFlowGroup 'GrupoA'

Archivo de Reglas:

- src/main/resources/rules.drl: Contiene 2 grupos de reglas 'GrupoA' y 'GrupoB'

Pruebas

- src/test/java/org/drools/test/RulesJUnitTest.java : (mvn clean test -Dtest=RulesJUnitTest) 

- src/test/java/org/drools/test/RulesJUnitTest2.java : (mvn clean test -Dtest=RulesJUnitTest2)

- src/test/java/org/drools/test/RulesJUnitTest3.java : (mvn clean test -Dtest=RulesJUnitTest3)

- src/test/java/org/drools/test/RulesJUnitTestAA.java : (mvn clean test -Dtest=RulesJUnitTestAA)