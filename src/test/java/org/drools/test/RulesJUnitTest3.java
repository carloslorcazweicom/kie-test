/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import junit.framework.TestCase;

import org.drools.test.Message;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.cdi.KSession;
import org.kie.api.event.process.DefaultProcessEventListener;
import org.kie.api.event.process.ProcessEventManager;
import org.kie.api.event.process.ProcessNodeLeftEvent;
import org.kie.api.event.process.ProcessNodeTriggeredEvent;
import org.kie.api.event.rule.DefaultAgendaEventListener;
import org.kie.api.event.rule.RuleFlowGroupActivatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventManager;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.WorkflowProcessInstance;
import org.kie.api.runtime.rule.FactHandle;


/**
 *
 * @author salaboy
 */
@RunWith(Arquillian.class)
public class RulesJUnitTest3 extends TestCase {

	private static final int THREADS = 2;
	
	private static final int PROCESS = 1;
	
	public RulesJUnitTest3() {
	}

	@Deployment
	public static JavaArchive createDeployment() {

		JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
		// System.out.println(jar.toString(true));
		return jar;
	}

	@Inject
	@KSession
	private KieSession kSession;
	
	@Test
	public void testConcurrent() {
		Assert.assertNotNull(kSession);
		RuleRuntimeEventManager ruleRuntimeEventManager = (RuleRuntimeEventManager) kSession;
		ruleRuntimeEventManager.addEventListener(new DefaultAgendaEventListener() {
			@Override
			public void afterRuleFlowGroupActivated(
					RuleFlowGroupActivatedEvent event) {

				kSession.fireAllRules();

			}
		});
	
		ProcessEventManager processEventManager = (ProcessEventManager) kSession;
		
		processEventManager.addEventListener(new DefaultProcessEventListener() {
			@Override
			public void beforeNodeTriggered(ProcessNodeTriggeredEvent event) {
				if (event.getNodeInstance().getNode() instanceof org.jbpm.workflow.core.node.RuleSetNode) {

					WorkflowProcessInstance p = (WorkflowProcessInstance) event
							.getProcessInstance();
					p.setVariable("ruleLocation", "N:"
							+ event.getNodeInstance().getNode().getName() + "-P:"
							+ event.getProcessInstance().getProcessId());

					FactHandle fh = event.getKieRuntime().insert(p);
					p.setVariable("processHandle", fh);

				}
			}
			
			@Override
			public void beforeNodeLeft(ProcessNodeLeftEvent event) {

				if (event.getNodeInstance().getNode() instanceof org.jbpm.workflow.core.node.RuleSetNode) {
					WorkflowProcessInstance p = (WorkflowProcessInstance) event
							.getProcessInstance();


					event.getKieRuntime().delete(
							(FactHandle) (p.getVariable("processHandle")));
				}

			}
			
		});
		
		for (int i = 0; i < THREADS;i++) {
			ThreadAB t = new ThreadAB(i,PROCESS);
			t.start();
			
		}
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("FIN");
	}
	
	private class ThreadAB extends Thread {

		private Integer i_;
		
		private Integer totalCalls;

		public ThreadAB(Integer i_, Integer totalCalls) {
			super();
			this.i_ = i_;
			this.totalCalls = totalCalls;

		}

		public void run() {
			for (int i = 0;i < totalCalls;i++) {
				Map<String, Object> paramAB = new HashMap<String, Object>();
				paramAB.put("message", new Message("AB" + i_ + "_" + i, new Date()));
				kSession.startProcess("droolsTest.RFGAB", paramAB);
			}
		}
	}
}
