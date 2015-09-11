package org.easyrules;

import junit.framework.TestSuite;
import org.easyrules.core.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        RulePriorityThresholdTest.class,
        SkipOnFirstAppliedRuleTest.class,
        SkipOnFirstFailedRuleTest.class,
        RuleListenerTest.class,
        CustomRuleOrderingTest.class,
        CompositeRuleTest.class,
        RuleDefinitionValidatorTest.class,
        DefaultRulesEngineTest.class})
public class EasyRulesTestSuite extends TestSuite {

}
