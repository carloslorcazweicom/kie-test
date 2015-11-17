# El proyecto contiene 3 procesos BPMN:

- src/main/resources/org.jbpm.RFG-A-A.v1.0.bpmn2 (AA): Consiste en dos BussinesRuleTask que activan el mismo RowFlowGroup 'GrupoA'

- src/main/resources/org.jbpm.RFG-A-B.v1.0.bpmn2 (AB): Consiste en dos BussinesRuleTask que activa el RowFlowGroup 'GrupoA' y luego el RowFlowGroup 'GrupoB'

- src/main/resources/org.jbpm.RFG-B-A.v1.0.bpmn2 (BA): Consiste en dos BussinesRuleTask que activa el RowFlowGroup 'GrupoB' y luego el RowFlowGroup 'GrupoA'

# Archivo de Reglas:

- src/main/resources/rules.drl: Contiene 2 grupos de reglas 'GrupoA' y 'GrupoB' las cuales realizan un print de la regla y se activan con objeto de tipo Message.

# Pruebas 

Se agrega un AgendaEventListener el cual realiza un "fireAllRules" ante el evento "afterRuleFlowGroupActivated"; ademas se agrega un ProcessEventListener el cual se encarga de insertar el hecho WorkflowProcessInstance correspondiente al proceso en el evento "beforeNodeTriggered" y la posterior retractacion del hecho en el evento "beforeNodeLeft".

- src/test/java/org/drools/test/RulesJUnitTest.java : (mvn clean test -Dtest=RulesJUnitTest) Levanta un proceso "AB" y un proceso "BA" de forma concurrente; dos veces.

- src/test/java/org/drools/test/RulesJUnitTest2.java : (mvn clean test -Dtest=RulesJUnitTest2)	Levanta un proceso "AB" y un proceso "BA" de forma concurrente; una vez.

- src/test/java/org/drools/test/RulesJUnitTest3.java : (mvn clean test -Dtest=RulesJUnitTest3) Levanta un proceso "AB" dos veces en paralelo (concurrente).

- src/test/java/org/drools/test/RulesJUnitTestAA.java : (mvn clean test -Dtest=RulesJUnitTestAA) Levanta el proceso "AA" 10 veces de forma concurrente.

# Para todos estos escenarios genera el siguiente error:

Exception in thread "Thread-2" org.jbpm.workflow.instance.WorkflowRuntimeException: [droolsTest.RFGAB:2 - Grupo Reglas A:2] -- null
	at org.jbpm.workflow.instance.impl.NodeInstanceImpl.trigger(NodeInstanceImpl.java:179)
	at org.jbpm.workflow.instance.impl.NodeInstanceImpl.triggerNodeInstance(NodeInstanceImpl.java:366)
	at org.jbpm.workflow.instance.impl.NodeInstanceImpl.triggerCompleted(NodeInstanceImpl.java:325)
	at org.jbpm.workflow.instance.node.StartNodeInstance.triggerCompleted(StartNodeInstance.java:73)
	at org.jbpm.workflow.instance.node.StartNodeInstance.internalTrigger(StartNodeInstance.java:44)
	at org.jbpm.workflow.instance.impl.NodeInstanceImpl.trigger(NodeInstanceImpl.java:173)
	at org.jbpm.ruleflow.instance.RuleFlowProcessInstance.internalStart(RuleFlowProcessInstance.java:35)
	at org.jbpm.process.instance.impl.ProcessInstanceImpl.start(ProcessInstanceImpl.java:236)
	at org.jbpm.workflow.instance.impl.WorkflowProcessInstanceImpl.start(WorkflowProcessInstanceImpl.java:439)
	at org.jbpm.process.instance.ProcessRuntimeImpl.startProcessInstance(ProcessRuntimeImpl.java:208)
	at org.jbpm.process.instance.ProcessRuntimeImpl.startProcess(ProcessRuntimeImpl.java:191)
	at org.jbpm.process.instance.ProcessRuntimeImpl.startProcess(ProcessRuntimeImpl.java:183)
	at org.drools.core.impl.StatefulKnowledgeSessionImpl.startProcess(StatefulKnowledgeSessionImpl.java:1787)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:606)
	at org.jboss.weld.bean.proxy.AbstractBeanInstance.invoke(AbstractBeanInstance.java:38)
	at org.jboss.weld.bean.proxy.ProxyMethodHandler.invoke(ProxyMethodHandler.java:100)
	at org.jboss.weld.proxies.KieSession$1366014918$Proxy$_$$_WeldClientProxy.startProcess(Unknown Source)
	at org.drools.test.RulesJUnitTest3$ThreadAB.run(RulesJUnitTest3.java:146)
Caused by: java.util.NoSuchElementException
	at java.util.LinkedList.getLast(LinkedList.java:255)
	at org.drools.core.common.DefaultAgenda.getNextFocus(DefaultAgenda.java:527)
	at org.drools.core.common.DefaultAgenda.fireNextItem(DefaultAgenda.java:964)
	at org.drools.core.common.DefaultAgenda.fireAllRules(DefaultAgenda.java:1292)
	at org.drools.core.impl.StatefulKnowledgeSessionImpl.internalFireAllRules(StatefulKnowledgeSessionImpl.java:1294)
	at org.drools.core.impl.StatefulKnowledgeSessionImpl.fireAllRules(StatefulKnowledgeSessionImpl.java:1281)
	at org.drools.core.impl.StatefulKnowledgeSessionImpl.fireAllRules(StatefulKnowledgeSessionImpl.java:1260)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:606)
	at org.jboss.weld.bean.proxy.AbstractBeanInstance.invoke(AbstractBeanInstance.java:38)
	at org.jboss.weld.bean.proxy.ProxyMethodHandler.invoke(ProxyMethodHandler.java:100)
	at org.jboss.weld.proxies.KieSession$1366014918$Proxy$_$$_WeldClientProxy.fireAllRules(Unknown Source)
	at org.drools.test.RulesJUnitTest3$1.afterRuleFlowGroupActivated(RulesJUnitTest3.java:74)
	at org.drools.core.event.AgendaEventSupport.fireAfterRuleFlowGroupActivated(AgendaEventSupport.java:152)
	at org.drools.core.common.DefaultAgenda.activateRuleFlowGroup(DefaultAgenda.java:703)
	at org.drools.core.common.DefaultAgenda.activateRuleFlowGroup(DefaultAgenda.java:691)
	at org.jbpm.workflow.instance.node.RuleSetNodeInstance.internalTrigger(RuleSetNodeInstance.java:87)
	at org.jbpm.workflow.instance.impl.NodeInstanceImpl.trigger(NodeInstanceImpl.java:173)
	... 20 more
	
# Print proceso

El siguiente tipo de print aparece en los procesos: 

Thread-2 : INIT P:AB G:A [Message [message=AB1_0, date=Tue Nov 17 16:12:23 CLST 2015]]

"Thread-2" hilo
"INIT P:AB G:A" Proceso AB (P:AB) inicia BussinesRuleTask con ruleflowgroup "GrupoA" (G:A)
"[Message [message=AB1_0, date=Tue Nov 17 16:12:23 CLST 2015]]" Imprime informacion del objeto, indicando (AB1_0) hilo 1 primera iteracion 0.

Thread-2 : END P:AB G:A [Message [message=AB0_0, date=Tue Nov 17 16:12:23 CLST 2015]]

"Thread-2" hilo
"INIT P:AB G:A" Proceso AB (P:AB) termina BussinesRuleTask con ruleflowgroup "GrupoA" (G:A)
"[Message [message=AB1_0, date=Tue Nov 17 16:12:23 CLST 2015]]" Imprime informacion del objeto, indicando (AB1_0) hilo 1 primera iteracion 0.

# Print Reglas

El siguiente tipo de print aparece en la ejecución de reglas:

Thread-2 RN - Ejecucion Grupo B/GrupoB [Message [message=AB0_0, date=Tue Nov 17 16:12:23 CLST 2015]]

"Thread-2" hilo 
"RN - Ejecucion Grupo B/GrupoB" Ejecucion de regla "Ejecucion Grupo B" y ruleflowgroup "GrupoB"
"[Message [message=AB0_0, date=Tue Nov 17 16:12:23 CLST 2015]]" Imprime informacion del objeto, indicando (AB0_0) hilo 0 primera iteracion 0.